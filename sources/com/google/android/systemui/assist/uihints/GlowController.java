package com.google.android.systemui.assist.uihints;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.util.MathUtils;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import com.android.systemui.Dependency;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.assist.ui.EdgeLight;
import com.android.systemui.navigationbar.NavigationModeController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsListener;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;
import com.google.android.systemui.assist.uihints.edgelights.mode.FulfillBottom;
import com.google.android.systemui.assist.uihints.edgelights.mode.FullListening;
import com.google.android.systemui.assist.uihints.edgelights.mode.Gone;
import com.google.android.systemui.assist.uihints.input.TouchInsideRegion;
import java.util.Optional;

public final class GlowController implements NgaMessageHandler.AudioInfoListener, NgaMessageHandler.CardInfoListener, EdgeLightsListener, TouchInsideRegion {
    private ValueAnimator mAnimator = null;
    private boolean mCardVisible = false;
    private final Context mContext;
    private EdgeLight[] mEdgeLights = null;
    private EdgeLightsView.Mode mEdgeLightsMode = null;
    private final GlowView mGlowView;
    private int mGlowsY = 0;
    private int mGlowsYDestination = 0;
    private boolean mInvocationCompleting = false;
    private float mMedianLightness;
    private int mNavigationMode;
    private final ViewGroup mParent;
    private RollingAverage mSpeechRolling = new RollingAverage(3);
    private VisibilityListener mVisibilityListener;

    /* access modifiers changed from: private */
    public enum GlowState {
        SHORT_DARK_BACKGROUND,
        SHORT_LIGHT_BACKGROUND,
        TALL_DARK_BACKGROUND,
        TALL_LIGHT_BACKGROUND,
        GONE
    }

    private float getGlowWidthToViewWidth() {
        return 0.55f;
    }

    private long getMaxYAnimationDuration() {
        return 400;
    }

    GlowController(Context context, ViewGroup viewGroup, TouchInsideHandler touchInsideHandler) {
        this.mContext = context;
        this.mParent = viewGroup;
        this.mNavigationMode = ((NavigationModeController) Dependency.get(NavigationModeController.class)).addListener(new GlowController$$ExternalSyntheticLambda2(this));
        GlowView glowView = (GlowView) viewGroup.findViewById(R$id.glow);
        this.mGlowView = glowView;
        int i = this.mGlowsY;
        glowView.setGlowsY(i, i, null);
        glowView.setOnClickListener(touchInsideHandler);
        glowView.setOnTouchListener(touchInsideHandler);
        glowView.setGlowsY(getMinTranslationY(), getMinTranslationY(), null);
        glowView.setGlowWidthRatio(getGlowWidthToViewWidth());
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i) {
        this.mNavigationMode = i;
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.AudioInfoListener
    public void onAudioInfo(float f, float f2) {
        this.mSpeechRolling.add(f2);
        maybeAnimateForSpeechConfidence();
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.CardInfoListener
    public void onCardInfo(boolean z, int i, boolean z2, boolean z3) {
        this.mCardVisible = z;
    }

    @Override // com.google.android.systemui.assist.uihints.edgelights.EdgeLightsListener
    public void onModeStarted(EdgeLightsView.Mode mode) {
        boolean z = mode instanceof Gone;
        if (!z || this.mEdgeLightsMode != null) {
            this.mInvocationCompleting = !z;
            this.mEdgeLightsMode = mode;
            if (z) {
                this.mSpeechRolling = new RollingAverage(3);
            }
            animateGlowTranslationY(getMinTranslationY());
            return;
        }
        this.mEdgeLightsMode = mode;
    }

    @Override // com.google.android.systemui.assist.uihints.edgelights.EdgeLightsListener
    public void onAssistLightsUpdated(EdgeLightsView.Mode mode, EdgeLight[] edgeLightArr) {
        int i;
        if (!getTranslationYProportionalToEdgeLights()) {
            this.mEdgeLights = null;
            this.mGlowView.distributeEvenly();
            return;
        }
        this.mEdgeLights = edgeLightArr;
        if ((this.mInvocationCompleting && (mode instanceof Gone)) || !(mode instanceof FullListening)) {
            return;
        }
        if (edgeLightArr == null || edgeLightArr.length != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected 4 lights, have ");
            if (edgeLightArr == null) {
                i = 0;
            } else {
                i = edgeLightArr.length;
            }
            sb.append(i);
            Log.e("GlowController", sb.toString());
            return;
        }
        maybeAnimateForSpeechConfidence();
    }

    /* access modifiers changed from: package-private */
    public void setVisibilityListener(VisibilityListener visibilityListener) {
        this.mVisibilityListener = visibilityListener;
    }

    /* access modifiers changed from: package-private */
    public void setInvocationProgress(float f) {
        if (this.mEdgeLightsMode instanceof Gone) {
            setVisibility(f > 0.0f ? 0 : 8);
            this.mGlowView.setBlurRadius(getInvocationBlurRadius(f));
            int invocationTranslationY = getInvocationTranslationY(f);
            this.mGlowsY = invocationTranslationY;
            this.mGlowsYDestination = invocationTranslationY;
            this.mGlowView.setGlowsY(invocationTranslationY, invocationTranslationY, null);
            this.mGlowView.distributeEvenly();
        }
    }

    /* access modifiers changed from: package-private */
    public void setMedianLightness(float f) {
        this.mGlowView.setGlowsBlendMode(f <= 0.4f ? PorterDuff.Mode.LIGHTEN : PorterDuff.Mode.SRC_OVER);
        this.mMedianLightness = f;
    }

    @Override // com.google.android.systemui.assist.uihints.input.TouchInsideRegion
    public Optional<Region> getTouchInsideRegion() {
        if (this.mGlowView.getVisibility() != 0) {
            return Optional.empty();
        }
        Rect rect = new Rect();
        this.mGlowView.getBoundsOnScreen(rect);
        rect.top = rect.bottom - getMaxTranslationY();
        return Optional.of(new Region(rect));
    }

    public boolean isVisible() {
        return this.mGlowView.getVisibility() == 0;
    }

    private boolean shouldAnimateForSpeechConfidence() {
        EdgeLightsView.Mode mode = this.mEdgeLightsMode;
        if (!(mode instanceof FullListening) && !(mode instanceof FulfillBottom)) {
            return false;
        }
        if (this.mSpeechRolling.getAverage() >= 0.30000001192092896d || this.mGlowsYDestination > getMinTranslationY()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void maybeAnimateForSpeechConfidence() {
        if (shouldAnimateForSpeechConfidence()) {
            animateGlowTranslationY((int) MathUtils.lerp((float) getMinTranslationY(), (float) getMaxTranslationY(), (float) this.mSpeechRolling.getAverage()));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private GlowState getState() {
        EdgeLightsView.Mode mode = this.mEdgeLightsMode;
        boolean z = true;
        boolean z2 = (mode instanceof FulfillBottom) && !((FulfillBottom) mode).isListening();
        EdgeLightsView.Mode mode2 = this.mEdgeLightsMode;
        if ((mode2 instanceof Gone) || mode2 == null || z2) {
            return GlowState.GONE;
        }
        boolean z3 = this.mCardVisible;
        if (this.mMedianLightness >= 0.4f) {
            z = false;
        }
        if (z) {
            return GlowState.TALL_DARK_BACKGROUND;
        }
        return GlowState.TALL_LIGHT_BACKGROUND;
    }

    private int getInvocationBlurRadius(float f) {
        return (int) MathUtils.lerp((float) getBlurRadius(), (float) this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_tall_blur), Math.min(1.0f, f * 5.0f));
    }

    private int getInvocationTranslationY(float f) {
        return (int) MathUtils.min((int) MathUtils.lerp((float) getMinTranslationY(), (float) this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_tall_min_y), f), this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_invocation_max));
    }

    private int getBlurRadius() {
        if (getState() == GlowState.GONE) {
            return this.mGlowView.getBlurRadius();
        }
        if (getState() == GlowState.SHORT_DARK_BACKGROUND || getState() == GlowState.SHORT_LIGHT_BACKGROUND) {
            return this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_short_blur);
        }
        if (getState() == GlowState.TALL_DARK_BACKGROUND || getState() == GlowState.TALL_LIGHT_BACKGROUND) {
            return this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_tall_blur);
        }
        return 0;
    }

    private int getMinTranslationY() {
        if (getState() == GlowState.SHORT_DARK_BACKGROUND || getState() == GlowState.SHORT_LIGHT_BACKGROUND) {
            return this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_short_min_y);
        }
        if (getState() == GlowState.TALL_DARK_BACKGROUND || getState() == GlowState.TALL_LIGHT_BACKGROUND) {
            return this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_tall_min_y);
        }
        return this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_gone_min_y);
    }

    private int getMaxTranslationY() {
        if (getState() == GlowState.SHORT_DARK_BACKGROUND || getState() == GlowState.SHORT_LIGHT_BACKGROUND) {
            return this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_short_max_y);
        }
        if (getState() == GlowState.TALL_DARK_BACKGROUND || getState() == GlowState.TALL_LIGHT_BACKGROUND) {
            return this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_tall_max_y);
        }
        return this.mContext.getResources().getDimensionPixelSize(R$dimen.glow_gone_max_y);
    }

    private boolean getTranslationYProportionalToEdgeLights() {
        return this.mEdgeLightsMode instanceof FullListening;
    }

    private long getYAnimationDuration(float f) {
        return (long) Math.min((float) getMaxYAnimationDuration(), Math.abs(f) / ((float) (((long) Math.abs(getMaxTranslationY() - getMinTranslationY())) / getMaxYAnimationDuration())));
    }

    private void animateGlowTranslationY(int i) {
        animateGlowTranslationY(i, getYAnimationDuration((float) (i - this.mGlowsY)));
    }

    private void animateGlowTranslationY(int i, long j) {
        if (i == this.mGlowsYDestination) {
            this.mGlowView.setGlowsY(this.mGlowsY, getMinTranslationY(), getTranslationYProportionalToEdgeLights() ? this.mEdgeLights : null);
            return;
        }
        this.mGlowsYDestination = i;
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(this.mGlowsY, i);
        this.mAnimator = ofInt;
        ofInt.addUpdateListener(new GlowController$$ExternalSyntheticLambda0(this));
        this.mAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.google.android.systemui.assist.uihints.GlowController.AnonymousClass1 */

            public void onAnimationEnd(Animator animator) {
                GlowController.this.mAnimator = null;
                if (GlowState.GONE.equals(GlowController.this.getState())) {
                    GlowController.this.setVisibility(8);
                } else {
                    GlowController.this.maybeAnimateForSpeechConfidence();
                }
            }
        });
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.setDuration(j);
        this.mAnimator.addUpdateListener(new GlowController$$ExternalSyntheticLambda1(this, this.mGlowView.getBlurRadius(), getBlurRadius()));
        float glowWidthRatio = this.mGlowView.getGlowWidthRatio();
        this.mGlowView.setGlowWidthRatio(glowWidthRatio + ((getGlowWidthToViewWidth() - glowWidthRatio) * 1.0f));
        if (this.mGlowView.getVisibility() != 0) {
            setVisibility(0);
        }
        this.mAnimator.start();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$animateGlowTranslationY$1(ValueAnimator valueAnimator) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mGlowsY = intValue;
        this.mGlowView.setGlowsY(intValue, getMinTranslationY(), getTranslationYProportionalToEdgeLights() ? this.mEdgeLights : null);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$animateGlowTranslationY$2(int i, int i2, ValueAnimator valueAnimator) {
        this.mGlowView.setBlurRadius((int) MathUtils.lerp((float) i, (float) i2, valueAnimator.getAnimatedFraction()));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setVisibility(int i) {
        this.mGlowView.setVisibility(i);
        if ((i == 0) != isVisible()) {
            VisibilityListener visibilityListener = this.mVisibilityListener;
            if (visibilityListener != null) {
                visibilityListener.onVisibilityChanged(i);
            }
            if (!isVisible()) {
                this.mGlowView.clearCaches();
            }
        }
    }
}

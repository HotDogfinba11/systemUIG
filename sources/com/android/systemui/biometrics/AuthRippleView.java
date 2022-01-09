package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.charging.RippleShader;
import kotlin.Unit;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AuthRippleView.kt */
public final class AuthRippleView extends View {
    private long alphaInDuration;
    private float aodDwellAlpha = 0.8f;
    private long aodDwellAlphaDuration = 50;
    private long aodDwellExpandDuration = (1200 - 50);
    private final long aodDwellPulseDuration = 50;
    private final float dwellAlpha = 1.0f;
    private final long dwellAlphaDuration = 50;
    private final long dwellExpandDuration = (1200 - 50);
    private final long dwellPulseDuration = 50;
    private Animator dwellPulseOutAnimator;
    private PointF origin;
    private float radius;
    private Animator retractAnimator;
    private final long retractDuration = 400;
    private final PathInterpolator retractInterpolator = new PathInterpolator(0.05f, 0.93f, 0.1f, 1.0f);
    private final Paint ripplePaint;
    private final RippleShader rippleShader;
    private boolean unlockedRippleInProgress;

    /* JADX DEBUG: Multi-variable search result rejected for r6v2, resolved type: android.graphics.Paint */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.statusbar.charging.RippleShader, android.graphics.Shader] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AuthRippleView(android.content.Context r5, android.util.AttributeSet r6) {
        /*
            r4 = this;
            r4.<init>(r5, r6)
            android.view.animation.PathInterpolator r5 = new android.view.animation.PathInterpolator
            r6 = 1028443341(0x3d4ccccd, float:0.05)
            r0 = 1064178811(0x3f6e147b, float:0.93)
            r1 = 1036831949(0x3dcccccd, float:0.1)
            r2 = 1065353216(0x3f800000, float:1.0)
            r5.<init>(r6, r0, r1, r2)
            r4.retractInterpolator = r5
            r5 = 50
            r4.dwellPulseDuration = r5
            r4.dwellAlphaDuration = r5
            r4.dwellAlpha = r2
            r0 = 1200(0x4b0, double:5.93E-321)
            long r2 = r0 - r5
            r4.dwellExpandDuration = r2
            r4.aodDwellPulseDuration = r5
            r4.aodDwellAlphaDuration = r5
            r2 = 1061997773(0x3f4ccccd, float:0.8)
            r4.aodDwellAlpha = r2
            long r0 = r0 - r5
            r4.aodDwellExpandDuration = r0
            r5 = 400(0x190, double:1.976E-321)
            r4.retractDuration = r5
            com.android.systemui.statusbar.charging.RippleShader r5 = new com.android.systemui.statusbar.charging.RippleShader
            r5.<init>()
            r4.rippleShader = r5
            android.graphics.Paint r6 = new android.graphics.Paint
            r6.<init>()
            r4.ripplePaint = r6
            android.graphics.PointF r0 = new android.graphics.PointF
            r0.<init>()
            r4.origin = r0
            r0 = -1
            r5.setColor(r0)
            r0 = 0
            r5.setProgress(r0)
            r0 = 1053609165(0x3ecccccd, float:0.4)
            r5.setSparkleStrength(r0)
            r6.setShader(r5)
            r5 = 8
            r4.setVisibility(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.AuthRippleView.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    private final void setRadius(float f) {
        this.rippleShader.setRadius(f);
        this.radius = f;
    }

    private final void setOrigin(PointF pointF) {
        this.rippleShader.setOrigin(pointF);
        this.origin = pointF;
    }

    public final void setSensorLocation(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "location");
        setOrigin(pointF);
        setRadius(ComparisonsKt___ComparisonsJvmKt.maxOf(pointF.x, pointF.y, ((float) getWidth()) - pointF.x, ((float) getHeight()) - pointF.y));
    }

    public final void setAlphaInDuration(long j) {
        this.alphaInDuration = j;
    }

    public final void retractRipple() {
        Animator animator = this.retractAnimator;
        Boolean bool = null;
        Boolean valueOf = animator == null ? null : Boolean.valueOf(animator.isRunning());
        Boolean bool2 = Boolean.TRUE;
        if (!Intrinsics.areEqual(valueOf, bool2)) {
            Animator animator2 = this.dwellPulseOutAnimator;
            if (animator2 != null) {
                bool = Boolean.valueOf(animator2.isRunning());
            }
            if (Intrinsics.areEqual(bool, bool2)) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.rippleShader.getProgress(), 0.0f);
                ofFloat.setInterpolator(this.retractInterpolator);
                ofFloat.setDuration(this.retractDuration);
                ofFloat.addUpdateListener(new AuthRippleView$retractRipple$retractRippleAnimator$1$1(this));
                ValueAnimator ofInt = ValueAnimator.ofInt(255, 0);
                ofInt.setInterpolator(Interpolators.LINEAR);
                ofInt.setDuration(this.retractDuration);
                ofInt.addUpdateListener(new AuthRippleView$retractRipple$retractAlphaAnimator$1$1(this));
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofFloat, ofInt);
                animatorSet.addListener(new AuthRippleView$retractRipple$1$1(this));
                animatorSet.start();
                Unit unit = Unit.INSTANCE;
                this.retractAnimator = animatorSet;
            }
        }
    }

    public final void startDwellRipple(float f, float f2, float f3, boolean z) {
        if (!this.unlockedRippleInProgress) {
            Animator animator = this.dwellPulseOutAnimator;
            if (!Intrinsics.areEqual(animator == null ? null : Boolean.valueOf(animator.isRunning()), Boolean.TRUE)) {
                float f4 = this.radius;
                float f5 = (f / f4) / 4.0f;
                float f6 = (f2 / f4) / 4.0f;
                float f7 = (f3 / f4) / 4.0f;
                float f8 = z ? this.aodDwellAlpha : this.dwellAlpha;
                float f9 = (float) 255;
                int i = (int) (f9 * f8);
                int min = Math.min((int) (f9 * (f8 + 0.25f)), 255);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(f5, f6);
                Interpolator interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
                ofFloat.setInterpolator(interpolator);
                ofFloat.setDuration(z ? this.aodDwellPulseDuration : this.dwellPulseDuration);
                ofFloat.addUpdateListener(new AuthRippleView$startDwellRipple$dwellPulseOutRippleAnimator$1$1(this));
                ValueAnimator ofInt = ValueAnimator.ofInt(0, i);
                Interpolator interpolator2 = Interpolators.LINEAR;
                ofInt.setInterpolator(interpolator2);
                ofInt.setDuration(z ? this.aodDwellAlphaDuration : this.dwellAlphaDuration);
                ofInt.addUpdateListener(new AuthRippleView$startDwellRipple$dwellPulseOutAlphaAnimator$1$1(this));
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(f6, f7);
                ofFloat2.setInterpolator(interpolator);
                ofFloat2.setDuration(z ? this.aodDwellExpandDuration : this.dwellExpandDuration);
                ofFloat2.addUpdateListener(new AuthRippleView$startDwellRipple$expandDwellRippleAnimator$1$1(this));
                ValueAnimator ofInt2 = ValueAnimator.ofInt(i, min);
                ofInt2.setInterpolator(interpolator2);
                ofInt2.setDuration(z ? this.aodDwellExpandDuration : this.dwellExpandDuration);
                ofInt2.addUpdateListener(new AuthRippleView$startDwellRipple$expandDwellAlphaAnimator$1$1(this));
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofFloat, ofInt);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(ofFloat2, ofInt2);
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.playSequentially(animatorSet, animatorSet2);
                animatorSet3.addListener(new AuthRippleView$startDwellRipple$1$1(this));
                animatorSet3.start();
                Unit unit = Unit.INSTANCE;
                this.dwellPulseOutAnimator = animatorSet3;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0030, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r4, r5) != false) goto L_0x0032;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void startUnlockedRipple(java.lang.Runnable r10) {
        /*
        // Method dump skipped, instructions count: 162
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.AuthRippleView.startUnlockedRipple(java.lang.Runnable):void");
    }

    public final void resetRippleAlpha() {
        RippleShader rippleShader2 = this.rippleShader;
        rippleShader2.setColor(ColorUtils.setAlphaComponent(rippleShader2.getColor(), 255));
    }

    public final void setColor(int i) {
        this.rippleShader.setColor(i);
        resetRippleAlpha();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f = (float) 1;
        float progress = (f - (((f - this.rippleShader.getProgress()) * (f - this.rippleShader.getProgress())) * (f - this.rippleShader.getProgress()))) * this.radius * 2.0f;
        if (canvas != null) {
            PointF pointF = this.origin;
            canvas.drawCircle(pointF.x, pointF.y, progress, this.ripplePaint);
        }
    }
}

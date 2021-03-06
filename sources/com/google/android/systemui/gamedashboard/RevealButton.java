package com.google.android.systemui.gamedashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.IntProperty;
import android.util.Property;
import android.view.View;
import com.android.systemui.R$drawable;

@SuppressLint({"AppCompatCustomView"})
public class RevealButton extends View {
    public static final IntProperty<RevealButton> BACKGROUND_HEIGHT = new IntProperty<RevealButton>("backgroundHeight") {
        /* class com.google.android.systemui.gamedashboard.RevealButton.AnonymousClass2 */

        public void setValue(RevealButton revealButton, int i) {
            revealButton.setBackgroundHeight(i);
        }

        public Integer get(RevealButton revealButton) {
            return Integer.valueOf(revealButton.getBackgroundHeight());
        }
    };
    public static final IntProperty<RevealButton> BACKGROUND_WIDTH = new IntProperty<RevealButton>("backgroundWidth") {
        /* class com.google.android.systemui.gamedashboard.RevealButton.AnonymousClass1 */

        public void setValue(RevealButton revealButton, int i) {
            revealButton.setBackgroundWidth(i);
        }

        public Integer get(RevealButton revealButton) {
            return Integer.valueOf(revealButton.getBackgroundWidth());
        }
    };
    public static final IntProperty<RevealButton> ICON_ALPHA = new IntProperty<RevealButton>("iconAlpha") {
        /* class com.google.android.systemui.gamedashboard.RevealButton.AnonymousClass3 */

        public void setValue(RevealButton revealButton, int i) {
            revealButton.setIconAlpha(i);
        }

        public Integer get(RevealButton revealButton) {
            return Integer.valueOf(revealButton.getIconAlpha());
        }
    };
    private final RevealButtonBackground mBackground;
    private int mIconAlpha = 255;
    private boolean mIsRecording = false;
    private final Drawable mLeftArrow;
    private final Drawable mLeftRecordingDot;
    private final Drawable mRightArrow;
    private final Drawable mRightRecordingDot;
    private boolean mRightSide = true;
    private ValueAnimator mValueAnimator;

    public RevealButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        RevealButtonBackground revealButtonBackground = new RevealButtonBackground(getContext());
        this.mBackground = revealButtonBackground;
        setWillNotDraw(false);
        Resources resources = getResources();
        Resources.Theme theme = context.getTheme();
        this.mLeftArrow = resources.getDrawable(R$drawable.ic_wide_arrow_left, theme);
        this.mRightArrow = resources.getDrawable(R$drawable.ic_wide_arrow_right, theme);
        this.mLeftRecordingDot = resources.getDrawable(R$drawable.ic_recording_dot_left, theme);
        this.mRightRecordingDot = resources.getDrawable(R$drawable.ic_recording_dot_right, theme);
        setBackground(revealButtonBackground);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mBackground.setBounds(0, 0, getWidth(), getHeight());
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Rect bounds = this.mBackground.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (this.mRightSide) {
            drawDrawable(this.mIsRecording ? this.mLeftRecordingDot : this.mLeftArrow, canvas, width, height, bounds.left, bounds.top);
        } else {
            drawDrawable(this.mIsRecording ? this.mRightRecordingDot : this.mRightArrow, canvas, width, height, bounds.left + (width / 2), bounds.top);
        }
        canvas.restore();
    }

    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void setSide(boolean z) {
        this.mRightSide = z;
        invalidate();
    }

    public void setIsRecording(boolean z) {
        this.mIsRecording = z;
        invalidate();
    }

    public boolean isOnTheRight() {
        return this.mRightSide;
    }

    public void bounce(boolean z) {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        final Rect copyBounds = this.mBackground.copyBounds();
        int width = copyBounds.width();
        int height = copyBounds.height();
        float[] fArr = new float[3];
        fArr[0] = 1.0f;
        fArr[1] = z ? 1.25f : 0.75f;
        fArr[2] = 1.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        this.mValueAnimator = ofFloat;
        ofFloat.addUpdateListener(new RevealButton$$ExternalSyntheticLambda0(this, width, copyBounds, height));
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.google.android.systemui.gamedashboard.RevealButton.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                RevealButton.this.mBackground.setBounds(copyBounds);
            }
        });
        this.mValueAnimator.setDuration(300L);
        this.mValueAnimator.start();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$bounce$0(int i, Rect rect, int i2, ValueAnimator valueAnimator) {
        float floatValue = (((Float) valueAnimator.getAnimatedValue()).floatValue() * ((float) i)) / 2.0f;
        int centerX = (int) (((float) rect.centerX()) + floatValue);
        float f = ((float) i2) / 2.0f;
        this.mBackground.setBounds((int) (((float) rect.centerX()) - floatValue), (int) (((float) rect.centerY()) - f), centerX, (int) (((float) rect.centerY()) + f));
    }

    public void slideIn() {
        int width = ((View) getParent()).getWidth();
        if (!this.mRightSide) {
            width = -getWidth();
        }
        float width2 = (float) (getWidth() / 2);
        Property property = View.TRANSLATION_X;
        float[] fArr = new float[2];
        float f = (float) width;
        fArr[0] = f;
        fArr[1] = this.mRightSide ? f - width2 : f + width2;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, property, fArr);
        ofFloat.setDuration(200L);
        ofFloat.start();
    }

    private void drawDrawable(Drawable drawable, Canvas canvas, int i, int i2, int i3, int i4) {
        int i5 = i / 2;
        drawable.setBounds(0, 0, i5, i5);
        canvas.translate((float) i3, (float) (i4 + ((i2 - drawable.getBounds().width()) / 2)));
        drawable.setAlpha(this.mIconAlpha);
        drawable.draw(canvas);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setBackgroundWidth(int i) {
        Rect bounds = this.mBackground.getBounds();
        RevealButtonBackground revealButtonBackground = this.mBackground;
        int i2 = bounds.left;
        revealButtonBackground.setBounds(i2, bounds.top, i + i2, bounds.bottom);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setBackgroundHeight(int i) {
        Rect bounds = this.mBackground.getBounds();
        int i2 = i / 2;
        this.mBackground.setBounds(bounds.left, bounds.centerY() - i2, bounds.right, bounds.centerY() + i2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setIconAlpha(int i) {
        this.mIconAlpha = i;
        invalidate();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getBackgroundWidth() {
        return this.mBackground.getBounds().width();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getBackgroundHeight() {
        return this.mBackground.getBounds().height();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getIconAlpha() {
        return this.mIconAlpha;
    }
}

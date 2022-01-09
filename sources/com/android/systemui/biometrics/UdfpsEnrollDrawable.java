package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.R$color;
import com.android.systemui.R$drawable;

public class UdfpsEnrollDrawable extends UdfpsDrawable {
    private final Paint mBlueFill;
    float mCurrentScale = 1.0f;
    float mCurrentX;
    float mCurrentY;
    private AnimatorSet mEdgeHintAnimatorSet;
    private ValueAnimator mEdgeHintColorAnimator;
    private final ValueAnimator.AnimatorUpdateListener mEdgeHintColorUpdateListener;
    private final Paint mEdgeHintPaint;
    private final Animator.AnimatorListener mEdgeHintPulseListener;
    private ValueAnimator mEdgeHintWidthAnimator;
    private final ValueAnimator.AnimatorUpdateListener mEdgeHintWidthUpdateListener;
    private UdfpsEnrollHelper mEnrollHelper;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final int mHintColorFaded;
    private final int mHintColorHighlight;
    private final float mHintMaxWidthPx;
    private final float mHintPaddingPx;
    private final Drawable mMovingTargetFpIcon;
    private final Paint mSensorOutlinePaint;
    private RectF mSensorRect;
    private boolean mShouldShowEdgeHint = false;
    private boolean mShouldShowTipHint = false;
    private final Animator.AnimatorListener mTargetAnimListener;
    AnimatorSet mTargetAnimatorSet;
    private AnimatorSet mTipHintAnimatorSet;
    private ValueAnimator mTipHintColorAnimator;
    private final ValueAnimator.AnimatorUpdateListener mTipHintColorUpdateListener;
    private final Paint mTipHintPaint;
    private final Animator.AnimatorListener mTipHintPulseListener;
    private ValueAnimator mTipHintWidthAnimator;
    private final ValueAnimator.AnimatorUpdateListener mTipHintWidthUpdateListener;

    UdfpsEnrollDrawable(Context context) {
        super(context);
        Paint paint = new Paint(0);
        this.mSensorOutlinePaint = paint;
        paint.setAntiAlias(true);
        Context context2 = this.mContext;
        int i = R$color.udfps_enroll_icon;
        paint.setColor(context2.getColor(i));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        Paint paint2 = new Paint(0);
        this.mBlueFill = paint2;
        paint2.setAntiAlias(true);
        paint2.setColor(context.getColor(R$color.udfps_moving_target_fill));
        paint2.setStyle(Paint.Style.FILL);
        Drawable drawable = context.getResources().getDrawable(R$drawable.ic_fingerprint, null);
        this.mMovingTargetFpIcon = drawable;
        drawable.setTint(-1);
        drawable.mutate();
        this.mFingerprintDrawable.setTint(this.mContext.getColor(i));
        int hintColorFaded = getHintColorFaded(context);
        this.mHintColorFaded = hintColorFaded;
        this.mHintColorHighlight = context.getColor(R$color.udfps_enroll_progress);
        this.mHintMaxWidthPx = Utils.dpToPixels(context, 6.0f);
        this.mHintPaddingPx = Utils.dpToPixels(context, 10.0f);
        this.mTargetAnimListener = new Animator.AnimatorListener() {
            /* class com.android.systemui.biometrics.UdfpsEnrollDrawable.AnonymousClass1 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                UdfpsEnrollDrawable.this.updateTipHintVisibility();
            }
        };
        Paint paint3 = new Paint(0);
        this.mTipHintPaint = paint3;
        paint3.setAntiAlias(true);
        paint3.setColor(hintColorFaded);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStrokeWidth(0.0f);
        this.mTipHintColorUpdateListener = new UdfpsEnrollDrawable$$ExternalSyntheticLambda2(this);
        this.mTipHintWidthUpdateListener = new UdfpsEnrollDrawable$$ExternalSyntheticLambda3(this);
        this.mTipHintPulseListener = new Animator.AnimatorListener() {
            /* class com.android.systemui.biometrics.UdfpsEnrollDrawable.AnonymousClass2 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                UdfpsEnrollDrawable.this.mHandler.postDelayed(new UdfpsEnrollDrawable$2$$ExternalSyntheticLambda0(this), 233);
            }

            /* access modifiers changed from: public */
            private /* synthetic */ void lambda$onAnimationEnd$0() {
                UdfpsEnrollDrawable udfpsEnrollDrawable = UdfpsEnrollDrawable.this;
                udfpsEnrollDrawable.mTipHintColorAnimator = ValueAnimator.ofArgb(udfpsEnrollDrawable.mTipHintPaint.getColor(), UdfpsEnrollDrawable.this.mHintColorFaded);
                UdfpsEnrollDrawable.this.mTipHintColorAnimator.setInterpolator(new LinearInterpolator());
                UdfpsEnrollDrawable.this.mTipHintColorAnimator.setDuration(517L);
                UdfpsEnrollDrawable.this.mTipHintColorAnimator.addUpdateListener(UdfpsEnrollDrawable.this.mTipHintColorUpdateListener);
                UdfpsEnrollDrawable.this.mTipHintColorAnimator.start();
            }
        };
        Paint paint4 = new Paint(0);
        this.mEdgeHintPaint = paint4;
        paint4.setAntiAlias(true);
        paint4.setColor(hintColorFaded);
        paint4.setStyle(Paint.Style.STROKE);
        paint4.setStrokeCap(Paint.Cap.ROUND);
        paint4.setStrokeWidth(0.0f);
        this.mEdgeHintColorUpdateListener = new UdfpsEnrollDrawable$$ExternalSyntheticLambda0(this);
        this.mEdgeHintWidthUpdateListener = new UdfpsEnrollDrawable$$ExternalSyntheticLambda1(this);
        this.mEdgeHintPulseListener = new Animator.AnimatorListener() {
            /* class com.android.systemui.biometrics.UdfpsEnrollDrawable.AnonymousClass3 */

            /* renamed from: $r8$lambda$Gb_5hWykSQBqwM6ILeH3xp-M_38 */
            public static /* synthetic */ void m84$r8$lambda$Gb_5hWykSQBqwM6ILeH3xpM_38(AnonymousClass3 r0) {
                r0.lambda$onAnimationEnd$0();
            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                UdfpsEnrollDrawable.this.mHandler.postDelayed(new UdfpsEnrollDrawable$3$$ExternalSyntheticLambda0(this), 233);
            }

            private /* synthetic */ void lambda$onAnimationEnd$0() {
                UdfpsEnrollDrawable udfpsEnrollDrawable = UdfpsEnrollDrawable.this;
                udfpsEnrollDrawable.mEdgeHintColorAnimator = ValueAnimator.ofArgb(udfpsEnrollDrawable.mEdgeHintPaint.getColor(), UdfpsEnrollDrawable.this.mHintColorFaded);
                UdfpsEnrollDrawable.this.mEdgeHintColorAnimator.setInterpolator(new LinearInterpolator());
                UdfpsEnrollDrawable.this.mEdgeHintColorAnimator.setDuration(517L);
                UdfpsEnrollDrawable.this.mEdgeHintColorAnimator.addUpdateListener(UdfpsEnrollDrawable.this.mEdgeHintColorUpdateListener);
                UdfpsEnrollDrawable.this.mEdgeHintColorAnimator.start();
            }
        };
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        this.mTipHintPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        invalidateSelf();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$1(ValueAnimator valueAnimator) {
        this.mTipHintPaint.setStrokeWidth(((Float) valueAnimator.getAnimatedValue()).floatValue());
        invalidateSelf();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$2(ValueAnimator valueAnimator) {
        this.mEdgeHintPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        invalidateSelf();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$3(ValueAnimator valueAnimator) {
        this.mEdgeHintPaint.setStrokeWidth(((Float) valueAnimator.getAnimatedValue()).floatValue());
        invalidateSelf();
    }

    private static int getHintColorFaded(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842803, typedValue, true);
        int i = (int) (typedValue.getFloat() * 255.0f);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843817});
        try {
            return ColorUtils.setAlphaComponent(obtainStyledAttributes.getColor(0, context.getColor(R$color.white_disabled)), i);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void setEnrollHelper(UdfpsEnrollHelper udfpsEnrollHelper) {
        this.mEnrollHelper = udfpsEnrollHelper;
    }

    @Override // com.android.systemui.biometrics.UdfpsDrawable
    public void onSensorRectUpdated(RectF rectF) {
        super.onSensorRectUpdated(rectF);
        this.mSensorRect = rectF;
    }

    @Override // com.android.systemui.biometrics.UdfpsDrawable
    public void updateFingerprintIconBounds(Rect rect) {
        super.updateFingerprintIconBounds(rect);
        this.mMovingTargetFpIcon.setBounds(rect);
        invalidateSelf();
    }

    public void onEnrollmentProgress(int i, int i2) {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        if (udfpsEnrollHelper != null) {
            if (!udfpsEnrollHelper.isCenterEnrollmentStage()) {
                AnimatorSet animatorSet = this.mTargetAnimatorSet;
                if (animatorSet != null && animatorSet.isRunning()) {
                    this.mTargetAnimatorSet.end();
                }
                PointF nextGuidedEnrollmentPoint = this.mEnrollHelper.getNextGuidedEnrollmentPoint();
                float f = this.mCurrentX;
                float f2 = nextGuidedEnrollmentPoint.x;
                if (f == f2 && this.mCurrentY == nextGuidedEnrollmentPoint.y) {
                    updateTipHintVisibility();
                } else {
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
                    ofFloat.addUpdateListener(new UdfpsEnrollDrawable$$ExternalSyntheticLambda5(this));
                    ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.mCurrentY, nextGuidedEnrollmentPoint.y);
                    ofFloat2.addUpdateListener(new UdfpsEnrollDrawable$$ExternalSyntheticLambda6(this));
                    long j = (nextGuidedEnrollmentPoint.x > 0.0f ? 1 : (nextGuidedEnrollmentPoint.x == 0.0f ? 0 : -1)) == 0 && (nextGuidedEnrollmentPoint.y > 0.0f ? 1 : (nextGuidedEnrollmentPoint.y == 0.0f ? 0 : -1)) == 0 ? 600 : 800;
                    ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 3.1415927f);
                    ofFloat3.setDuration(j);
                    ofFloat3.addUpdateListener(new UdfpsEnrollDrawable$$ExternalSyntheticLambda4(this));
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.mTargetAnimatorSet = animatorSet2;
                    animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
                    this.mTargetAnimatorSet.setDuration(j);
                    this.mTargetAnimatorSet.addListener(this.mTargetAnimListener);
                    this.mTargetAnimatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
                    this.mTargetAnimatorSet.start();
                }
            } else {
                updateTipHintVisibility();
            }
            updateEdgeHintVisibility();
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onEnrollmentProgress$4(ValueAnimator valueAnimator) {
        this.mCurrentX = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onEnrollmentProgress$5(ValueAnimator valueAnimator) {
        this.mCurrentY = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onEnrollmentProgress$6(ValueAnimator valueAnimator) {
        this.mCurrentScale = (((float) Math.sin((double) ((Float) valueAnimator.getAnimatedValue()).floatValue())) * 0.25f) + 1.0f;
        invalidateSelf();
    }

    private void updateTipHintVisibility() {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        boolean z = udfpsEnrollHelper != null && udfpsEnrollHelper.isTipEnrollmentStage();
        if (this.mShouldShowTipHint != z) {
            this.mShouldShowTipHint = z;
            ValueAnimator valueAnimator = this.mTipHintWidthAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mTipHintWidthAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mTipHintPaint.getStrokeWidth(), z ? this.mHintMaxWidthPx : 0.0f);
            this.mTipHintWidthAnimator = ofFloat;
            ofFloat.setDuration(233L);
            this.mTipHintWidthAnimator.addUpdateListener(this.mTipHintWidthUpdateListener);
            if (z) {
                startTipHintPulseAnimation();
            } else {
                this.mTipHintWidthAnimator.start();
            }
        }
    }

    private void updateEdgeHintVisibility() {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        boolean z = udfpsEnrollHelper != null && udfpsEnrollHelper.isEdgeEnrollmentStage();
        if (this.mShouldShowEdgeHint != z) {
            this.mShouldShowEdgeHint = z;
            ValueAnimator valueAnimator = this.mEdgeHintWidthAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mEdgeHintWidthAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mEdgeHintPaint.getStrokeWidth(), z ? this.mHintMaxWidthPx : 0.0f);
            this.mEdgeHintWidthAnimator = ofFloat;
            ofFloat.setDuration(233L);
            this.mEdgeHintWidthAnimator.addUpdateListener(this.mEdgeHintWidthUpdateListener);
            if (z) {
                startEdgeHintPulseAnimation();
            } else {
                this.mEdgeHintWidthAnimator.start();
            }
        }
    }

    private void startTipHintPulseAnimation() {
        this.mHandler.removeCallbacksAndMessages(null);
        AnimatorSet animatorSet = this.mTipHintAnimatorSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.mTipHintAnimatorSet.cancel();
        }
        ValueAnimator valueAnimator = this.mTipHintColorAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTipHintColorAnimator.cancel();
        }
        ValueAnimator ofArgb = ValueAnimator.ofArgb(this.mTipHintPaint.getColor(), this.mHintColorHighlight);
        this.mTipHintColorAnimator = ofArgb;
        ofArgb.setDuration(233L);
        this.mTipHintColorAnimator.addUpdateListener(this.mTipHintColorUpdateListener);
        this.mTipHintColorAnimator.addListener(this.mTipHintPulseListener);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mTipHintAnimatorSet = animatorSet2;
        animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mTipHintAnimatorSet.playTogether(this.mTipHintColorAnimator, this.mTipHintWidthAnimator);
        this.mTipHintAnimatorSet.start();
    }

    private void startEdgeHintPulseAnimation() {
        this.mHandler.removeCallbacksAndMessages(null);
        AnimatorSet animatorSet = this.mEdgeHintAnimatorSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.mEdgeHintAnimatorSet.cancel();
        }
        ValueAnimator valueAnimator = this.mEdgeHintColorAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mEdgeHintColorAnimator.cancel();
        }
        ValueAnimator ofArgb = ValueAnimator.ofArgb(this.mEdgeHintPaint.getColor(), this.mHintColorHighlight);
        this.mEdgeHintColorAnimator = ofArgb;
        ofArgb.setDuration(233L);
        this.mEdgeHintColorAnimator.addUpdateListener(this.mEdgeHintColorUpdateListener);
        this.mEdgeHintColorAnimator.addListener(this.mEdgeHintPulseListener);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mEdgeHintAnimatorSet = animatorSet2;
        animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mEdgeHintAnimatorSet.playTogether(this.mEdgeHintColorAnimator, this.mEdgeHintWidthAnimator);
        this.mEdgeHintAnimatorSet.start();
    }

    private boolean isTipHintVisible() {
        return this.mTipHintPaint.getStrokeWidth() > 0.0f;
    }

    private boolean isEdgeHintVisible() {
        return this.mEdgeHintPaint.getStrokeWidth() > 0.0f;
    }

    public void draw(Canvas canvas) {
        if (!isIlluminationShowing()) {
            UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
            if (udfpsEnrollHelper == null || udfpsEnrollHelper.isCenterEnrollmentStage()) {
                RectF rectF = this.mSensorRect;
                if (rectF != null) {
                    canvas.drawOval(rectF, this.mSensorOutlinePaint);
                }
                this.mFingerprintDrawable.draw(canvas);
                this.mFingerprintDrawable.setAlpha(this.mAlpha);
                this.mSensorOutlinePaint.setAlpha(this.mAlpha);
            } else {
                canvas.save();
                canvas.translate(this.mCurrentX, this.mCurrentY);
                RectF rectF2 = this.mSensorRect;
                if (rectF2 != null) {
                    float f = this.mCurrentScale;
                    canvas.scale(f, f, rectF2.centerX(), this.mSensorRect.centerY());
                    canvas.drawOval(this.mSensorRect, this.mBlueFill);
                }
                this.mMovingTargetFpIcon.draw(canvas);
                canvas.restore();
            }
            if (isTipHintVisible() || isEdgeHintVisible()) {
                canvas.save();
                canvas.rotate(-90.0f, this.mSensorRect.centerX(), this.mSensorRect.centerY());
                RectF rectF3 = this.mSensorRect;
                RectF rectF4 = this.mSensorRect;
                float f2 = this.mHintPaddingPx;
                float abs = (Math.abs(rectF4.right - rectF4.left) / 2.0f) + f2;
                float abs2 = (Math.abs(rectF3.bottom - rectF3.top) / 2.0f) + f2;
                if (isTipHintVisible()) {
                    canvas.drawArc(this.mSensorRect.centerX() - abs, this.mSensorRect.centerY() - abs2, this.mSensorRect.centerX() + abs, this.mSensorRect.centerY() + abs2, -20.0f, 40.0f, false, this.mTipHintPaint);
                }
                if (isEdgeHintVisible()) {
                    canvas.rotate(-90.0f, this.mSensorRect.centerX(), this.mSensorRect.centerY());
                    canvas.drawArc(this.mSensorRect.centerX() - abs, this.mSensorRect.centerY() - abs2, this.mSensorRect.centerX() + abs, this.mSensorRect.centerY() + abs2, -20.0f, 40.0f, false, this.mEdgeHintPaint);
                    canvas.rotate(180.0f, this.mSensorRect.centerX(), this.mSensorRect.centerY());
                    canvas.drawArc(this.mSensorRect.centerX() - abs, this.mSensorRect.centerY() - abs2, this.mSensorRect.centerX() + abs, this.mSensorRect.centerY() + abs2, -20.0f, 40.0f, false, this.mEdgeHintPaint);
                }
                canvas.restore();
            }
        }
    }

    @Override // com.android.systemui.biometrics.UdfpsDrawable
    public void setAlpha(int i) {
        super.setAlpha(i);
        this.mSensorOutlinePaint.setAlpha(i);
        this.mBlueFill.setAlpha(i);
        this.mMovingTargetFpIcon.setAlpha(i);
        invalidateSelf();
    }
}

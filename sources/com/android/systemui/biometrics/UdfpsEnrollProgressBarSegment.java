package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import com.android.systemui.R$color;

public class UdfpsEnrollProgressBarSegment {
    private float mAnimatedProgress = 0.0f;
    private final Paint mBackgroundPaint;
    private final Rect mBounds;
    private ValueAnimator mFillColorAnimator;
    private final ValueAnimator.AnimatorUpdateListener mFillColorUpdateListener;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final int mHelpColor;
    private final Runnable mInvalidateRunnable;
    private boolean mIsShowingHelp = false;
    private final float mMaxOverSweepAngle;
    private float mOverSweepAngle = 0.0f;
    private final Runnable mOverSweepAnimationRunnable;
    private ValueAnimator mOverSweepAnimator;
    private ValueAnimator mOverSweepReverseAnimator;
    private final ValueAnimator.AnimatorUpdateListener mOverSweepUpdateListener;
    private float mProgress = 0.0f;
    private ValueAnimator mProgressAnimator;
    private final int mProgressColor;
    private final Paint mProgressPaint;
    private final ValueAnimator.AnimatorUpdateListener mProgressUpdateListener;
    private final float mStartAngle;
    private final float mStrokeWidthPx;
    private final float mSweepAngle;

    /* renamed from: $r8$lambda$H_1nPNkd3w-7a6NmRfSOYfOJU8s */
    public static /* synthetic */ void m86$r8$lambda$H_1nPNkd3w7a6NmRfSOYfOJU8s(UdfpsEnrollProgressBarSegment udfpsEnrollProgressBarSegment) {
        udfpsEnrollProgressBarSegment.lambda$new$3();
    }

    public UdfpsEnrollProgressBarSegment(Context context, Rect rect, float f, float f2, float f3, Runnable runnable) {
        this.mBounds = rect;
        this.mInvalidateRunnable = runnable;
        this.mStartAngle = f;
        this.mSweepAngle = f2;
        this.mMaxOverSweepAngle = f3;
        float dpToPixels = Utils.dpToPixels(context, 12.0f);
        this.mStrokeWidthPx = dpToPixels;
        int color = context.getColor(R$color.udfps_enroll_progress);
        this.mProgressColor = color;
        this.mHelpColor = context.getColor(R$color.udfps_enroll_progress_help);
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        paint.setStrokeWidth(dpToPixels);
        paint.setColor(context.getColor(R$color.white_disabled));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843817});
        paint.setColor(obtainStyledAttributes.getColor(0, paint.getColor()));
        obtainStyledAttributes.recycle();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842803, typedValue, true);
        paint.setAlpha((int) (typedValue.getFloat() * 255.0f));
        Paint paint2 = new Paint();
        this.mProgressPaint = paint2;
        paint2.setStrokeWidth(dpToPixels);
        paint2.setColor(color);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        this.mProgressUpdateListener = new UdfpsEnrollProgressBarSegment$$ExternalSyntheticLambda0(this);
        this.mFillColorUpdateListener = new UdfpsEnrollProgressBarSegment$$ExternalSyntheticLambda1(this);
        this.mOverSweepUpdateListener = new UdfpsEnrollProgressBarSegment$$ExternalSyntheticLambda2(this);
        this.mOverSweepAnimationRunnable = new UdfpsEnrollProgressBarSegment$$ExternalSyntheticLambda3(this);
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        this.mAnimatedProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.mInvalidateRunnable.run();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$1(ValueAnimator valueAnimator) {
        this.mProgressPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        this.mInvalidateRunnable.run();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$2(ValueAnimator valueAnimator) {
        this.mOverSweepAngle = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.mInvalidateRunnable.run();
    }

    private /* synthetic */ void lambda$new$3() {
        ValueAnimator valueAnimator = this.mOverSweepAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mOverSweepAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mOverSweepAngle, this.mMaxOverSweepAngle);
        this.mOverSweepAnimator = ofFloat;
        ofFloat.setDuration(200L);
        this.mOverSweepAnimator.addUpdateListener(this.mOverSweepUpdateListener);
        this.mOverSweepAnimator.start();
    }

    public void draw(Canvas canvas) {
        float f = this.mStrokeWidthPx / 2.0f;
        if (this.mAnimatedProgress < 1.0f) {
            Rect rect = this.mBounds;
            canvas.drawArc(f, f, ((float) rect.right) - f, ((float) rect.bottom) - f, this.mStartAngle, this.mSweepAngle, false, this.mBackgroundPaint);
        }
        float f2 = this.mAnimatedProgress;
        if (f2 > 0.0f) {
            Rect rect2 = this.mBounds;
            canvas.drawArc(f, f, ((float) rect2.right) - f, ((float) rect2.bottom) - f, this.mStartAngle, (this.mSweepAngle * f2) + this.mOverSweepAngle, false, this.mProgressPaint);
        }
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void updateProgress(float f) {
        updateProgress(f, 400);
    }

    private void updateProgress(float f, long j) {
        if (this.mProgress != f) {
            this.mProgress = f;
            ValueAnimator valueAnimator = this.mProgressAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mProgressAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mAnimatedProgress, f);
            this.mProgressAnimator = ofFloat;
            ofFloat.setDuration(j);
            this.mProgressAnimator.addUpdateListener(this.mProgressUpdateListener);
            this.mProgressAnimator.start();
        }
    }

    public void updateFillColor(boolean z) {
        if (this.mIsShowingHelp != z) {
            this.mIsShowingHelp = z;
            ValueAnimator valueAnimator = this.mFillColorAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mFillColorAnimator.cancel();
            }
            ValueAnimator ofArgb = ValueAnimator.ofArgb(this.mProgressPaint.getColor(), z ? this.mHelpColor : this.mProgressColor);
            this.mFillColorAnimator = ofArgb;
            ofArgb.setDuration(200L);
            this.mFillColorAnimator.addUpdateListener(this.mFillColorUpdateListener);
            this.mFillColorAnimator.start();
        }
    }

    public void startCompletionAnimation() {
        boolean hasCallbacks = this.mHandler.hasCallbacks(this.mOverSweepAnimationRunnable);
        if (hasCallbacks || this.mOverSweepAngle >= this.mMaxOverSweepAngle) {
            Log.d("UdfpsProgressBarSegment", "startCompletionAnimation skipped: hasCallback = " + hasCallbacks + ", mOverSweepAngle = " + this.mOverSweepAngle);
            return;
        }
        ValueAnimator valueAnimator = this.mOverSweepReverseAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mOverSweepReverseAnimator.cancel();
            this.mOverSweepAngle = 0.0f;
        }
        if (this.mAnimatedProgress < 1.0f) {
            updateProgress(1.0f, 200);
            updateFillColor(false);
        }
        this.mHandler.postDelayed(this.mOverSweepAnimationRunnable, 200);
    }

    public void cancelCompletionAnimation() {
        this.mHandler.removeCallbacks(this.mOverSweepAnimationRunnable);
        ValueAnimator valueAnimator = this.mOverSweepAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mOverSweepAnimator.cancel();
        }
        if (this.mOverSweepAngle > 0.0f) {
            ValueAnimator valueAnimator2 = this.mOverSweepReverseAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.mOverSweepReverseAnimator.cancel();
            }
            float f = this.mOverSweepAngle;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(f, 0.0f);
            this.mOverSweepReverseAnimator = ofFloat;
            ofFloat.setDuration((long) ((f / this.mMaxOverSweepAngle) * 200.0f));
            this.mOverSweepReverseAnimator.addUpdateListener(this.mOverSweepUpdateListener);
            this.mOverSweepReverseAnimator.start();
        }
    }
}

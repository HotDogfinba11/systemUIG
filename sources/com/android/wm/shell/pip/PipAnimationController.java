package com.android.wm.shell.pip;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.TaskInfo;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.RotationUtils;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.wm.shell.animation.Interpolators;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import java.util.Objects;

public class PipAnimationController {
    private PipTransitionAnimator mCurrentAnimator;
    private final ThreadLocal<AnimationHandler> mSfAnimationHandlerThreadLocal = ThreadLocal.withInitial(PipAnimationController$$ExternalSyntheticLambda0.INSTANCE);
    private final PipSurfaceTransactionHelper mSurfaceTransactionHelper;

    public static class PipAnimationCallback {
        public void onPipAnimationCancel(TaskInfo taskInfo, PipTransitionAnimator pipTransitionAnimator) {
            throw null;
        }

        public void onPipAnimationEnd(TaskInfo taskInfo, SurfaceControl.Transaction transaction, PipTransitionAnimator pipTransitionAnimator) {
            throw null;
        }

        public void onPipAnimationStart(TaskInfo taskInfo, PipTransitionAnimator pipTransitionAnimator) {
            throw null;
        }
    }

    public static class PipTransactionHandler {
        public boolean handlePipTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect) {
            throw null;
        }
    }

    public static boolean isInPipDirection(int i) {
        return i == 2;
    }

    public static boolean isOutPipDirection(int i) {
        return i == 3 || i == 4;
    }

    public static boolean isRemovePipDirection(int i) {
        return i == 5;
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ AnimationHandler lambda$new$0() {
        AnimationHandler animationHandler = new AnimationHandler();
        animationHandler.setProvider(new SfVsyncFrameCallbackProvider());
        return animationHandler;
    }

    public PipAnimationController(PipSurfaceTransactionHelper pipSurfaceTransactionHelper) {
        this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
    }

    @VisibleForTesting
    public PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, float f, float f2) {
        PipTransitionAnimator pipTransitionAnimator = this.mCurrentAnimator;
        if (pipTransitionAnimator == null) {
            this.mCurrentAnimator = setupPipTransitionAnimator(PipTransitionAnimator.ofAlpha(taskInfo, surfaceControl, rect, f, f2));
        } else if (pipTransitionAnimator.getAnimationType() != 1 || !Objects.equals(rect, this.mCurrentAnimator.getDestinationBounds()) || !this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.cancel();
            this.mCurrentAnimator = setupPipTransitionAnimator(PipTransitionAnimator.ofAlpha(taskInfo, surfaceControl, rect, f, f2));
        } else {
            this.mCurrentAnimator.updateEndValue(Float.valueOf(f2));
        }
        return this.mCurrentAnimator;
    }

    @VisibleForTesting
    public PipTransitionAnimator getAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i, float f, int i2) {
        PipTransitionAnimator pipTransitionAnimator = this.mCurrentAnimator;
        if (pipTransitionAnimator == null) {
            this.mCurrentAnimator = setupPipTransitionAnimator(PipTransitionAnimator.ofBounds(taskInfo, surfaceControl, rect2, rect2, rect3, rect4, i, 0.0f, i2));
        } else if (pipTransitionAnimator.getAnimationType() == 1 && this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.setDestinationBounds(rect3);
        } else if (this.mCurrentAnimator.getAnimationType() != 0 || !this.mCurrentAnimator.isRunning()) {
            this.mCurrentAnimator.cancel();
            this.mCurrentAnimator = setupPipTransitionAnimator(PipTransitionAnimator.ofBounds(taskInfo, surfaceControl, rect, rect2, rect3, rect4, i, f, i2));
        } else {
            this.mCurrentAnimator.setDestinationBounds(rect3);
            this.mCurrentAnimator.updateEndValue(new Rect(rect3));
        }
        return this.mCurrentAnimator;
    }

    /* access modifiers changed from: package-private */
    public PipTransitionAnimator getCurrentAnimator() {
        return this.mCurrentAnimator;
    }

    private PipTransitionAnimator setupPipTransitionAnimator(PipTransitionAnimator pipTransitionAnimator) {
        pipTransitionAnimator.setSurfaceTransactionHelper(this.mSurfaceTransactionHelper);
        pipTransitionAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        pipTransitionAnimator.setFloatValues(0.0f, 1.0f);
        pipTransitionAnimator.setAnimationHandler(this.mSfAnimationHandlerThreadLocal.get());
        return pipTransitionAnimator;
    }

    public static abstract class PipTransitionAnimator<T> extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        private final int mAnimationType;
        private T mBaseValue;
        protected SurfaceControl mContentOverlay;
        protected T mCurrentValue;
        private final Rect mDestinationBounds;
        private T mEndValue;
        private final SurfaceControl mLeash;
        private PipAnimationCallback mPipAnimationCallback;
        private PipTransactionHandler mPipTransactionHandler;
        protected T mStartValue;
        private float mStartingAngle;
        private PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;
        private PipSurfaceTransactionHelper mSurfaceTransactionHelper;
        private final TaskInfo mTaskInfo;
        private int mTransitionDirection;

        /* access modifiers changed from: package-private */
        public abstract void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f);

        public void onAnimationRepeat(Animator animator) {
        }

        /* access modifiers changed from: package-private */
        public void onEndTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, int i) {
        }

        /* access modifiers changed from: package-private */
        public void onStartTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
        }

        private PipTransitionAnimator(TaskInfo taskInfo, SurfaceControl surfaceControl, int i, Rect rect, T t, T t2, T t3, float f) {
            Rect rect2 = new Rect();
            this.mDestinationBounds = rect2;
            this.mTaskInfo = taskInfo;
            this.mLeash = surfaceControl;
            this.mAnimationType = i;
            rect2.set(rect);
            this.mBaseValue = t;
            this.mStartValue = t2;
            this.mEndValue = t3;
            this.mStartingAngle = f;
            addListener(this);
            addUpdateListener(this);
            this.mSurfaceControlTransactionFactory = PipAnimationController$PipTransitionAnimator$$ExternalSyntheticLambda0.INSTANCE;
            this.mTransitionDirection = 0;
        }

        public void onAnimationStart(Animator animator) {
            this.mCurrentValue = this.mStartValue;
            onStartTransaction(this.mLeash, newSurfaceControlTransaction());
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationStart(this.mTaskInfo, this);
            }
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            applySurfaceControlTransaction(this.mLeash, newSurfaceControlTransaction(), valueAnimator.getAnimatedFraction());
        }

        public void onAnimationEnd(Animator animator) {
            this.mCurrentValue = this.mEndValue;
            SurfaceControl.Transaction newSurfaceControlTransaction = newSurfaceControlTransaction();
            onEndTransaction(this.mLeash, newSurfaceControlTransaction, this.mTransitionDirection);
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationEnd(this.mTaskInfo, newSurfaceControlTransaction, this);
            }
            this.mTransitionDirection = 0;
        }

        public void onAnimationCancel(Animator animator) {
            PipAnimationCallback pipAnimationCallback = this.mPipAnimationCallback;
            if (pipAnimationCallback != null) {
                pipAnimationCallback.onPipAnimationCancel(this.mTaskInfo, this);
            }
            this.mTransitionDirection = 0;
        }

        @VisibleForTesting
        public int getAnimationType() {
            return this.mAnimationType;
        }

        @VisibleForTesting
        public PipTransitionAnimator<T> setPipAnimationCallback(PipAnimationCallback pipAnimationCallback) {
            this.mPipAnimationCallback = pipAnimationCallback;
            return this;
        }

        /* access modifiers changed from: package-private */
        public PipTransitionAnimator<T> setPipTransactionHandler(PipTransactionHandler pipTransactionHandler) {
            this.mPipTransactionHandler = pipTransactionHandler;
            return this;
        }

        /* access modifiers changed from: package-private */
        public boolean handlePipTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect) {
            PipTransactionHandler pipTransactionHandler = this.mPipTransactionHandler;
            if (pipTransactionHandler != null) {
                return pipTransactionHandler.handlePipTransaction(surfaceControl, transaction, rect);
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public SurfaceControl getContentOverlay() {
            return this.mContentOverlay;
        }

        /* access modifiers changed from: package-private */
        public PipTransitionAnimator<T> setUseContentOverlay(Context context) {
            SurfaceControl.Transaction newSurfaceControlTransaction = newSurfaceControlTransaction();
            SurfaceControl surfaceControl = this.mContentOverlay;
            if (surfaceControl != null) {
                newSurfaceControlTransaction.remove(surfaceControl);
                newSurfaceControlTransaction.apply();
            }
            SurfaceControl build = new SurfaceControl.Builder(new SurfaceSession()).setCallsite("PipAnimation").setName("PipContentOverlay").setColorLayer().build();
            this.mContentOverlay = build;
            newSurfaceControlTransaction.show(build);
            newSurfaceControlTransaction.setLayer(this.mContentOverlay, Integer.MAX_VALUE);
            newSurfaceControlTransaction.setColor(this.mContentOverlay, getContentOverlayColor(context));
            newSurfaceControlTransaction.setAlpha(this.mContentOverlay, 0.0f);
            newSurfaceControlTransaction.reparent(this.mContentOverlay, this.mLeash);
            newSurfaceControlTransaction.apply();
            return this;
        }

        private float[] getContentOverlayColor(Context context) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16842801});
            try {
                int color = obtainStyledAttributes.getColor(0, 0);
                return new float[]{((float) Color.red(color)) / 255.0f, ((float) Color.green(color)) / 255.0f, ((float) Color.blue(color)) / 255.0f};
            } finally {
                obtainStyledAttributes.recycle();
            }
        }

        /* access modifiers changed from: package-private */
        public void clearContentOverlay() {
            this.mContentOverlay = null;
        }

        @VisibleForTesting
        public int getTransitionDirection() {
            return this.mTransitionDirection;
        }

        @VisibleForTesting
        public PipTransitionAnimator<T> setTransitionDirection(int i) {
            if (i != 1) {
                this.mTransitionDirection = i;
            }
            return this;
        }

        /* access modifiers changed from: package-private */
        public T getStartValue() {
            return this.mStartValue;
        }

        /* access modifiers changed from: package-private */
        public T getBaseValue() {
            return this.mBaseValue;
        }

        @VisibleForTesting
        public T getEndValue() {
            return this.mEndValue;
        }

        /* access modifiers changed from: package-private */
        public Rect getDestinationBounds() {
            return this.mDestinationBounds;
        }

        /* access modifiers changed from: package-private */
        public void setDestinationBounds(Rect rect) {
            this.mDestinationBounds.set(rect);
            if (this.mAnimationType == 1) {
                onStartTransaction(this.mLeash, newSurfaceControlTransaction());
            }
        }

        /* access modifiers changed from: package-private */
        public void setCurrentValue(T t) {
            this.mCurrentValue = t;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldApplyCornerRadius() {
            return !PipAnimationController.isOutPipDirection(this.mTransitionDirection);
        }

        /* access modifiers changed from: package-private */
        public boolean inScaleTransition() {
            if (this.mAnimationType != 0) {
                return false;
            }
            int transitionDirection = getTransitionDirection();
            if (PipAnimationController.isInPipDirection(transitionDirection) || PipAnimationController.isOutPipDirection(transitionDirection)) {
                return false;
            }
            return true;
        }

        public void updateEndValue(T t) {
            this.mEndValue = t;
        }

        /* access modifiers changed from: protected */
        public SurfaceControl.Transaction newSurfaceControlTransaction() {
            SurfaceControl.Transaction transaction = this.mSurfaceControlTransactionFactory.getTransaction();
            transaction.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
            return transaction;
        }

        @VisibleForTesting
        public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
            this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
        }

        /* access modifiers changed from: package-private */
        public PipSurfaceTransactionHelper getSurfaceTransactionHelper() {
            return this.mSurfaceTransactionHelper;
        }

        /* access modifiers changed from: package-private */
        public void setSurfaceTransactionHelper(PipSurfaceTransactionHelper pipSurfaceTransactionHelper) {
            this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
        }

        static PipTransitionAnimator<Float> ofAlpha(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, float f, float f2) {
            return new PipTransitionAnimator<Float>(taskInfo, surfaceControl, 1, rect, Float.valueOf(f), Float.valueOf(f), Float.valueOf(f2), 0.0f) {
                /* class com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator.AnonymousClass1 */

                /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator$1 */
                /* JADX WARN: Multi-variable type inference failed */
                /* access modifiers changed from: package-private */
                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f) {
                    float floatValue = (((Float) getStartValue()).floatValue() * (1.0f - f)) + (((Float) getEndValue()).floatValue() * f);
                    setCurrentValue(Float.valueOf(floatValue));
                    getSurfaceTransactionHelper().alpha(transaction, surfaceControl, floatValue).round(transaction, surfaceControl, shouldApplyCornerRadius());
                    transaction.apply();
                }

                /* access modifiers changed from: package-private */
                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public void onStartTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
                    if (getTransitionDirection() != 5) {
                        getSurfaceTransactionHelper().resetScale(transaction, surfaceControl, getDestinationBounds()).crop(transaction, surfaceControl, getDestinationBounds()).round(transaction, surfaceControl, shouldApplyCornerRadius());
                        transaction.show(surfaceControl);
                        transaction.apply();
                    }
                }

                public void updateEndValue(Float f) {
                    super.updateEndValue((Object) f);
                    this.mStartValue = this.mCurrentValue;
                }
            };
        }

        static PipTransitionAnimator<Rect> ofBounds(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, final Rect rect3, final Rect rect4, final int i, final float f, final int i2) {
            final Rect rect5;
            final Rect rect6;
            final Rect rect7;
            final Rect rect8;
            final boolean isOutPipDirection = PipAnimationController.isOutPipDirection(i);
            if (isOutPipDirection) {
                rect5 = new Rect(rect3);
            } else {
                rect5 = new Rect(rect);
            }
            if (i2 == 1 || i2 == 3) {
                Rect rect9 = new Rect(rect3);
                Rect rect10 = new Rect(rect3);
                RotationUtils.rotateBounds(rect10, rect5, i2);
                rect6 = rect9;
                rect7 = rect10;
                rect8 = isOutPipDirection ? rect10 : rect5;
            } else {
                rect7 = null;
                rect6 = null;
                rect8 = rect5;
            }
            final Rect rect11 = rect4 == null ? null : new Rect(rect4.left - rect8.left, rect4.top - rect8.top, rect8.right - rect4.right, rect8.bottom - rect4.bottom);
            final Rect rect12 = new Rect(0, 0, 0, 0);
            return new PipTransitionAnimator<Rect>(taskInfo, surfaceControl, 0, new Rect(rect), new Rect(rect2), new Rect(rect3), f, rect3) {
                /* class com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator.AnonymousClass2 */
                private final RectEvaluator mInsetsEvaluator = new RectEvaluator(new Rect());
                private final RectEvaluator mRectEvaluator = new RectEvaluator(new Rect());

                /* JADX DEBUG: Multi-variable search result rejected for r9v0, resolved type: com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator$2 */
                /* JADX WARN: Multi-variable type inference failed */
                /* access modifiers changed from: package-private */
                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public void applySurfaceControlTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, float f) {
                    Rect rect = (Rect) getBaseValue();
                    Rect rect2 = (Rect) getStartValue();
                    Rect rect3 = (Rect) getEndValue();
                    SurfaceControl surfaceControl2 = this.mContentOverlay;
                    if (surfaceControl2 != null) {
                        transaction.setAlpha(surfaceControl2, f < 0.5f ? 0.0f : (f - 0.5f) * 2.0f);
                    }
                    if (rect7 != null) {
                        applyRotation(transaction, surfaceControl, f, rect2, rect3);
                        return;
                    }
                    Rect evaluate = this.mRectEvaluator.evaluate(f, rect2, rect3);
                    float f2 = (1.0f - f) * f;
                    setCurrentValue(evaluate);
                    if (!inScaleTransition() && rect4 != null) {
                        Rect computeInsets = computeInsets(f);
                        getSurfaceTransactionHelper().scaleAndCrop(transaction, surfaceControl, rect5, evaluate, computeInsets);
                        if (shouldApplyCornerRadius()) {
                            Rect rect4 = new Rect(evaluate);
                            rect4.inset(computeInsets);
                            getSurfaceTransactionHelper().round(transaction, surfaceControl, rect8, rect4);
                        }
                    } else if (isOutPipDirection) {
                        getSurfaceTransactionHelper().scale(transaction, surfaceControl, rect3, evaluate);
                    } else {
                        getSurfaceTransactionHelper().scale(transaction, surfaceControl, rect, evaluate, f2).round(transaction, surfaceControl, rect, evaluate);
                    }
                    if (!handlePipTransaction(surfaceControl, transaction, evaluate)) {
                        transaction.apply();
                    }
                }

                /* JADX DEBUG: Multi-variable search result rejected for r17v0, resolved type: com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator$2 */
                /* JADX WARN: Multi-variable type inference failed */
                private void applyRotation(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, float f, Rect rect, Rect rect2) {
                    float f2;
                    float f3;
                    int i;
                    int i2;
                    if (!rect2.equals(rect6)) {
                        rect7.set(rect3);
                        RotationUtils.rotateBounds(rect7, rect5, i2);
                        rect6.set(rect2);
                    }
                    Rect evaluate = this.mRectEvaluator.evaluate(f, rect, rect7);
                    setCurrentValue(evaluate);
                    Rect computeInsets = computeInsets(f);
                    if (i2 == 1) {
                        f3 = 90.0f * f;
                        int i3 = rect2.right;
                        int i4 = rect.left;
                        f2 = (((float) (i3 - i4)) * f) + ((float) i4);
                        i = rect2.top;
                        i2 = rect.top;
                    } else {
                        f3 = -90.0f * f;
                        int i5 = rect2.left;
                        int i6 = rect.left;
                        f2 = (((float) (i5 - i6)) * f) + ((float) i6);
                        i = rect2.bottom;
                        i2 = rect.top;
                    }
                    getSurfaceTransactionHelper().rotateAndScaleWithCrop(transaction, surfaceControl, rect8, evaluate, computeInsets, f3, f2, (f * ((float) (i - i2))) + ((float) i2), isOutPipDirection, i2 == 3).round(transaction, surfaceControl, rect8, evaluate);
                    transaction.apply();
                }

                private Rect computeInsets(float f) {
                    Rect rect = rect11;
                    if (rect == null) {
                        return rect12;
                    }
                    boolean z = isOutPipDirection;
                    Rect rect2 = z ? rect : rect12;
                    if (z) {
                        rect = rect12;
                    }
                    return this.mInsetsEvaluator.evaluate(f, rect2, rect);
                }

                /* access modifiers changed from: package-private */
                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public void onStartTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
                    getSurfaceTransactionHelper().alpha(transaction, surfaceControl, 1.0f).round(transaction, surfaceControl, shouldApplyCornerRadius());
                    if (PipAnimationController.isInPipDirection(i)) {
                        transaction.setWindowCrop(surfaceControl, (Rect) getStartValue());
                    }
                    transaction.show(surfaceControl);
                    transaction.apply();
                }

                /* access modifiers changed from: package-private */
                @Override // com.android.wm.shell.pip.PipAnimationController.PipTransitionAnimator
                public void onEndTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, int i) {
                    Rect destinationBounds = getDestinationBounds();
                    getSurfaceTransactionHelper().resetScale(transaction, surfaceControl, destinationBounds);
                    if (PipAnimationController.isOutPipDirection(i)) {
                        transaction.setMatrix(surfaceControl, 1.0f, 0.0f, 0.0f, 1.0f);
                        transaction.setPosition(surfaceControl, 0.0f, 0.0f);
                        transaction.setWindowCrop(surfaceControl, 0, 0);
                        return;
                    }
                    getSurfaceTransactionHelper().crop(transaction, surfaceControl, destinationBounds);
                }

                public void updateEndValue(Rect rect) {
                    T t;
                    super.updateEndValue((Object) rect);
                    T t2 = this.mStartValue;
                    if (t2 != null && (t = this.mCurrentValue) != null) {
                        t2.set(t);
                    }
                }
            };
        }
    }
}

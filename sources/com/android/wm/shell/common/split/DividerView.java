package com.android.wm.shell.common.split;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceControlViewHost;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.android.wm.shell.R;
import com.android.wm.shell.animation.Interpolators;

public class DividerView extends FrameLayout implements View.OnTouchListener {
    private View mBackground;
    private GestureDetector mDoubleTapDetector;
    private DividerHandleView mHandle;
    private boolean mInteractive;
    private boolean mMoving;
    private SplitLayout mSplitLayout;
    private int mStartPos;
    private int mTouchElevation;
    private final int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private VelocityTracker mVelocityTracker;
    private SurfaceControlViewHost mViewHost;

    public DividerView(Context context) {
        super(context);
    }

    public DividerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DividerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DividerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setup(SplitLayout splitLayout, SurfaceControlViewHost surfaceControlViewHost) {
        this.mSplitLayout = splitLayout;
        this.mViewHost = surfaceControlViewHost;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHandle = (DividerHandleView) findViewById(R.id.docked_divider_handle);
        this.mBackground = findViewById(R.id.docked_divider_background);
        this.mTouchElevation = getResources().getDimensionPixelSize(R.dimen.docked_stack_divider_lift_elevation);
        this.mDoubleTapDetector = new GestureDetector(getContext(), new DoubleTapListener());
        this.mInteractive = true;
        setOnTouchListener(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0033, code lost:
        if (r6 != 3) goto L_0x00af;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
        /*
        // Method dump skipped, instructions count: 177
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    private void setTouching() {
        setSlippery(false);
        this.mHandle.setTouching(true, true);
        if (isLandscape()) {
            this.mBackground.animate().scaleX(1.4f);
        } else {
            this.mBackground.animate().scaleY(1.4f);
        }
        ViewPropertyAnimator animate = this.mBackground.animate();
        Interpolator interpolator = Interpolators.TOUCH_RESPONSE;
        animate.setInterpolator(interpolator).setDuration(150).translationZ((float) this.mTouchElevation).start();
        this.mHandle.animate().setInterpolator(interpolator).setDuration(150).translationZ((float) this.mTouchElevation).start();
    }

    private void releaseTouching() {
        setSlippery(true);
        this.mHandle.setTouching(false, true);
        ViewPropertyAnimator animate = this.mBackground.animate();
        Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
        animate.setInterpolator(interpolator).setDuration(200).translationZ(0.0f).scaleX(1.0f).scaleY(1.0f).start();
        this.mHandle.animate().setInterpolator(interpolator).setDuration(200).translationZ(0.0f).start();
    }

    private void setSlippery(boolean z) {
        if (this.mViewHost != null) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) getLayoutParams();
            int i = layoutParams.flags;
            if (((i & 536870912) != 0) != z) {
                if (z) {
                    layoutParams.flags = i | 536870912;
                } else {
                    layoutParams.flags = -536870913 & i;
                }
                this.mViewHost.relayout(layoutParams);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setInteractive(boolean z) {
        if (z != this.mInteractive) {
            this.mInteractive = z;
            releaseTouching();
            this.mHandle.setVisibility(this.mInteractive ? 0 : 4);
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == 2;
    }

    private class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        private DoubleTapListener() {
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (DividerView.this.mSplitLayout == null) {
                return true;
            }
            DividerView.this.mSplitLayout.onDoubleTappedDivider();
            return true;
        }
    }
}

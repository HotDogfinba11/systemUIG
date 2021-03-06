package com.android.systemui.accessibility;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import java.util.Collections;

public class MagnificationModeSwitch implements MagnificationGestureDetector.OnGestureListener {
    static final int DEFAULT_FADE_OUT_ANIMATION_DELAY_MS;
    static final long FADING_ANIMATION_DURATION_MS;
    private final AccessibilityManager mAccessibilityManager;
    private final Context mContext;
    final Rect mDraggableWindowBounds;
    private final Runnable mFadeInAnimationTask;
    private final Runnable mFadeOutAnimationTask;
    private final MagnificationGestureDetector mGestureDetector;
    private final ImageView mImageView;
    boolean mIsFadeOutAnimating;
    private boolean mIsVisible;
    private int mMagnificationMode;
    private final WindowManager.LayoutParams mParams;
    private final SfVsyncFrameCallbackProvider mSfVsyncFrameProvider;
    private boolean mSingleTapDetected;
    private boolean mToLeftScreenEdge;
    private int mUiTimeout;
    private final Runnable mWindowInsetChangeRunnable;
    private final WindowManager mWindowManager;

    MagnificationModeSwitch(Context context) {
        this(context, createView(context), new SfVsyncFrameCallbackProvider());
    }

    MagnificationModeSwitch(Context context, ImageView imageView, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider) {
        this.mIsFadeOutAnimating = false;
        this.mMagnificationMode = 0;
        this.mDraggableWindowBounds = new Rect();
        this.mIsVisible = false;
        this.mSingleTapDetected = false;
        this.mToLeftScreenEdge = false;
        this.mContext = context;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mSfVsyncFrameProvider = sfVsyncFrameCallbackProvider;
        this.mParams = createLayoutParams(context);
        this.mImageView = imageView;
        imageView.setOnTouchListener(new MagnificationModeSwitch$$ExternalSyntheticLambda2(this));
        imageView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            /* class com.android.systemui.accessibility.MagnificationModeSwitch.AnonymousClass1 */

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setStateDescription(MagnificationModeSwitch.this.formatStateDescription());
                accessibilityNodeInfo.setContentDescription(MagnificationModeSwitch.this.mContext.getResources().getString(R$string.magnification_mode_switch_description));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), MagnificationModeSwitch.this.mContext.getResources().getString(R$string.magnification_mode_switch_click_label)));
                accessibilityNodeInfo.setClickable(true);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_up, MagnificationModeSwitch.this.mContext.getString(R$string.accessibility_control_move_up)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_down, MagnificationModeSwitch.this.mContext.getString(R$string.accessibility_control_move_down)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_left, MagnificationModeSwitch.this.mContext.getString(R$string.accessibility_control_move_left)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_right, MagnificationModeSwitch.this.mContext.getString(R$string.accessibility_control_move_right)));
            }

            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (performA11yAction(i)) {
                    return true;
                }
                return super.performAccessibilityAction(view, i, bundle);
            }

            private boolean performA11yAction(int i) {
                Rect bounds = MagnificationModeSwitch.this.mWindowManager.getCurrentWindowMetrics().getBounds();
                if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                    MagnificationModeSwitch.this.handleSingleTap();
                    return true;
                } else if (i == R$id.accessibility_action_move_up) {
                    MagnificationModeSwitch.this.moveButton(0.0f, (float) (-bounds.height()));
                    return true;
                } else if (i == R$id.accessibility_action_move_down) {
                    MagnificationModeSwitch.this.moveButton(0.0f, (float) bounds.height());
                    return true;
                } else if (i == R$id.accessibility_action_move_left) {
                    MagnificationModeSwitch.this.moveButton((float) (-bounds.width()), 0.0f);
                    return true;
                } else if (i != R$id.accessibility_action_move_right) {
                    return false;
                } else {
                    MagnificationModeSwitch.this.moveButton((float) bounds.width(), 0.0f);
                    return true;
                }
            }
        });
        this.mWindowInsetChangeRunnable = new MagnificationModeSwitch$$ExternalSyntheticLambda7(this);
        imageView.setOnApplyWindowInsetsListener(new MagnificationModeSwitch$$ExternalSyntheticLambda1(this));
        this.mFadeInAnimationTask = new MagnificationModeSwitch$$ExternalSyntheticLambda3(this);
        this.mFadeOutAnimationTask = new MagnificationModeSwitch$$ExternalSyntheticLambda5(this);
        this.mGestureDetector = new MagnificationGestureDetector(context, context.getMainThreadHandler(), this);
    }

    /* access modifiers changed from: public */
    private /* synthetic */ WindowInsets lambda$new$0(View view, WindowInsets windowInsets) {
        if (!this.mImageView.getHandler().hasCallbacks(this.mWindowInsetChangeRunnable)) {
            this.mImageView.getHandler().post(this.mWindowInsetChangeRunnable);
        }
        return view.onApplyWindowInsets(windowInsets);
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$1() {
        this.mImageView.animate().alpha(1.0f).setDuration(FADING_ANIMATION_DURATION_MS).start();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$3() {
        this.mImageView.animate().alpha(0.0f).setDuration(FADING_ANIMATION_DURATION_MS).withEndAction(new MagnificationModeSwitch$$ExternalSyntheticLambda6(this)).start();
        this.mIsFadeOutAnimating = true;
    }

    private CharSequence formatStateDescription() {
        int i;
        if (this.mMagnificationMode == 2) {
            i = R$string.magnification_mode_switch_state_window;
        } else {
            i = R$string.magnification_mode_switch_state_full_screen;
        }
        return this.mContext.getResources().getString(i);
    }

    private void applyResourcesValuesWithDensityChanged() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.magnification_switch_button_size);
        WindowManager.LayoutParams layoutParams = this.mParams;
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        if (this.mIsVisible) {
            stickToScreenEdge(this.mToLeftScreenEdge);
            lambda$new$2();
            showButton(this.mMagnificationMode, false);
        }
    }

    /* access modifiers changed from: public */
    private boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.mIsVisible) {
            return false;
        }
        return this.mGestureDetector.onTouch(motionEvent);
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onSingleTap() {
        this.mSingleTapDetected = true;
        handleSingleTap();
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onDrag(float f, float f2) {
        moveButton(f, f2);
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onStart(float f, float f2) {
        stopFadeOutAnimation();
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onFinish(float f, float f2) {
        if (this.mIsVisible) {
            boolean z = this.mParams.x < this.mWindowManager.getCurrentWindowMetrics().getBounds().width() / 2;
            this.mToLeftScreenEdge = z;
            stickToScreenEdge(z);
        }
        if (!this.mSingleTapDetected) {
            showButton(this.mMagnificationMode);
        }
        this.mSingleTapDetected = false;
        return true;
    }

    private void stickToScreenEdge(boolean z) {
        this.mParams.x = z ? this.mDraggableWindowBounds.left : this.mDraggableWindowBounds.right;
        updateButtonViewLayoutIfNeeded();
    }

    private void moveButton(float f, float f2) {
        this.mSfVsyncFrameProvider.postFrameCallback(new MagnificationModeSwitch$$ExternalSyntheticLambda0(this, f, f2));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$moveButton$4(float f, float f2, long j) {
        WindowManager.LayoutParams layoutParams = this.mParams;
        layoutParams.x = (int) (((float) layoutParams.x) + f);
        layoutParams.y = (int) (((float) layoutParams.y) + f2);
        updateButtonViewLayoutIfNeeded();
    }

    /* renamed from: removeButton */
    public void lambda$new$2() {
        if (this.mIsVisible) {
            this.mImageView.removeCallbacks(this.mFadeInAnimationTask);
            this.mImageView.removeCallbacks(this.mFadeOutAnimationTask);
            this.mImageView.animate().cancel();
            this.mIsFadeOutAnimating = false;
            this.mImageView.setAlpha(0.0f);
            this.mWindowManager.removeView(this.mImageView);
            this.mIsVisible = false;
        }
    }

    public void showButton(int i) {
        showButton(i, true);
    }

    private void showButton(int i, boolean z) {
        if (this.mMagnificationMode != i) {
            this.mMagnificationMode = i;
            this.mImageView.setImageResource(getIconResId(i));
        }
        if (!this.mIsVisible) {
            if (z) {
                this.mDraggableWindowBounds.set(getDraggableWindowBounds());
                WindowManager.LayoutParams layoutParams = this.mParams;
                Rect rect = this.mDraggableWindowBounds;
                layoutParams.x = rect.right;
                layoutParams.y = rect.bottom;
                this.mToLeftScreenEdge = false;
            }
            this.mWindowManager.addView(this.mImageView, this.mParams);
            setSystemGestureExclusion();
            this.mIsVisible = true;
            this.mImageView.postOnAnimation(this.mFadeInAnimationTask);
            this.mUiTimeout = this.mAccessibilityManager.getRecommendedTimeoutMillis(DEFAULT_FADE_OUT_ANIMATION_DELAY_MS, 5);
        }
        stopFadeOutAnimation();
        this.mImageView.postOnAnimationDelayed(this.mFadeOutAnimationTask, (long) this.mUiTimeout);
    }

    private void stopFadeOutAnimation() {
        this.mImageView.removeCallbacks(this.mFadeOutAnimationTask);
        if (this.mIsFadeOutAnimating) {
            this.mImageView.animate().cancel();
            this.mImageView.setAlpha(1.0f);
            this.mIsFadeOutAnimating = false;
        }
    }

    public void onConfigurationChanged(int i) {
        if ((i & 128) != 0) {
            Rect rect = new Rect(this.mDraggableWindowBounds);
            this.mDraggableWindowBounds.set(getDraggableWindowBounds());
            float height = ((float) (this.mParams.y - rect.top)) / ((float) rect.height());
            this.mParams.y = ((int) (height * ((float) this.mDraggableWindowBounds.height()))) + this.mDraggableWindowBounds.top;
            stickToScreenEdge(this.mToLeftScreenEdge);
        } else if ((i & 4096) != 0) {
            applyResourcesValuesWithDensityChanged();
        } else if ((i & 4) != 0) {
            updateAccessibilityWindowTitle();
        }
    }

    /* access modifiers changed from: public */
    private void onWindowInsetChanged() {
        Rect draggableWindowBounds = getDraggableWindowBounds();
        if (!this.mDraggableWindowBounds.equals(draggableWindowBounds)) {
            this.mDraggableWindowBounds.set(draggableWindowBounds);
            stickToScreenEdge(this.mToLeftScreenEdge);
        }
    }

    private void updateButtonViewLayoutIfNeeded() {
        if (this.mIsVisible) {
            WindowManager.LayoutParams layoutParams = this.mParams;
            int i = layoutParams.x;
            Rect rect = this.mDraggableWindowBounds;
            layoutParams.x = MathUtils.constrain(i, rect.left, rect.right);
            WindowManager.LayoutParams layoutParams2 = this.mParams;
            int i2 = layoutParams2.y;
            Rect rect2 = this.mDraggableWindowBounds;
            layoutParams2.y = MathUtils.constrain(i2, rect2.top, rect2.bottom);
            this.mWindowManager.updateViewLayout(this.mImageView, this.mParams);
        }
    }

    private void updateAccessibilityWindowTitle() {
        this.mParams.accessibilityTitle = getAccessibilityWindowTitle(this.mContext);
        if (this.mIsVisible) {
            this.mWindowManager.updateViewLayout(this.mImageView, this.mParams);
        }
    }

    private void toggleMagnificationMode() {
        int i = this.mMagnificationMode ^ 3;
        this.mMagnificationMode = i;
        this.mImageView.setImageResource(getIconResId(i));
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_mode", i, -2);
    }

    private void handleSingleTap() {
        lambda$new$2();
        toggleMagnificationMode();
    }

    private static ImageView createView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setClickable(true);
        imageView.setFocusable(true);
        imageView.setAlpha(0.0f);
        return imageView;
    }

    static int getIconResId(int i) {
        if (i == 1) {
            return R$drawable.ic_open_in_new_window;
        }
        return R$drawable.ic_open_in_new_fullscreen;
    }

    private static WindowManager.LayoutParams createLayoutParams(Context context) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.magnification_switch_button_size);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, dimensionPixelSize, 2039, 8, -2);
        layoutParams.gravity = 51;
        layoutParams.accessibilityTitle = getAccessibilityWindowTitle(context);
        layoutParams.layoutInDisplayCutoutMode = 3;
        return layoutParams;
    }

    private Rect getDraggableWindowBounds() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.magnification_switch_button_margin);
        WindowMetrics currentWindowMetrics = this.mWindowManager.getCurrentWindowMetrics();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        Rect rect = new Rect(currentWindowMetrics.getBounds());
        rect.offsetTo(0, 0);
        WindowManager.LayoutParams layoutParams = this.mParams;
        rect.inset(0, 0, layoutParams.width, layoutParams.height);
        rect.inset(insetsIgnoringVisibility);
        rect.inset(dimensionPixelSize, dimensionPixelSize);
        return rect;
    }

    private static String getAccessibilityWindowTitle(Context context) {
        return context.getString(17039652);
    }

    private void setSystemGestureExclusion() {
        this.mImageView.post(new MagnificationModeSwitch$$ExternalSyntheticLambda4(this));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$setSystemGestureExclusion$5() {
        this.mImageView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, this.mImageView.getWidth(), this.mImageView.getHeight())));
    }
}

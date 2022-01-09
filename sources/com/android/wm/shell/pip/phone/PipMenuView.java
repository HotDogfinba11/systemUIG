package com.android.wm.shell.pip.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.wm.shell.R;
import com.android.wm.shell.animation.Interpolators;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.PipUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PipMenuView extends FrameLayout {
    private AccessibilityManager mAccessibilityManager;
    private final List<RemoteAction> mActions = new ArrayList();
    private LinearLayout mActionsGroup;
    private boolean mAllowMenuTimeout = true;
    private boolean mAllowTouches = true;
    private Drawable mBackgroundDrawable;
    private int mBetweenActionPaddingLand;
    private PhonePipMenuController mController;
    private boolean mDidLastShowMenuResize;
    protected View mDismissButton;
    private int mDismissFadeOutDurationMs;
    private final Runnable mHideMenuRunnable = new PipMenuView$$ExternalSyntheticLambda7(this);
    private ShellExecutor mMainExecutor;
    private Handler mMainHandler;
    private ValueAnimator.AnimatorUpdateListener mMenuBgUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        /* class com.android.wm.shell.pip.phone.PipMenuView.AnonymousClass1 */

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            PipMenuView.this.mBackgroundDrawable.setAlpha((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 0.3f * 255.0f));
        }
    };
    private View mMenuContainer;
    private AnimatorSet mMenuContainerAnimator;
    private int mMenuState;
    protected PipMenuIconsAlgorithm mPipMenuIconsAlgorithm;
    protected View mResizeHandle;
    protected View mSettingsButton;
    protected View mTopEndContainer;
    protected View mViewRoot;

    /* access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateActionViews$4(View view, MotionEvent motionEvent) {
        return true;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public PipMenuView(Context context, PhonePipMenuController phonePipMenuController, ShellExecutor shellExecutor, Handler handler) {
        super(context, null, 0);
        ((FrameLayout) this).mContext = context;
        this.mController = phonePipMenuController;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        FrameLayout.inflate(context, R.layout.pip_menu, this);
        Drawable drawable = ((FrameLayout) this).mContext.getDrawable(R.drawable.pip_menu_background);
        this.mBackgroundDrawable = drawable;
        drawable.setAlpha(0);
        View findViewById = findViewById(R.id.background);
        this.mViewRoot = findViewById;
        findViewById.setBackground(this.mBackgroundDrawable);
        View findViewById2 = findViewById(R.id.menu_container);
        this.mMenuContainer = findViewById2;
        findViewById2.setAlpha(0.0f);
        this.mTopEndContainer = findViewById(R.id.top_end_container);
        View findViewById3 = findViewById(R.id.settings);
        this.mSettingsButton = findViewById3;
        findViewById3.setAlpha(0.0f);
        this.mSettingsButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda3(this));
        View findViewById4 = findViewById(R.id.dismiss);
        this.mDismissButton = findViewById4;
        findViewById4.setAlpha(0.0f);
        this.mDismissButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda4(this));
        findViewById(R.id.expand_button).setOnClickListener(new PipMenuView$$ExternalSyntheticLambda2(this));
        View findViewById5 = findViewById(R.id.resize_handle);
        this.mResizeHandle = findViewById5;
        findViewById5.setAlpha(0.0f);
        this.mActionsGroup = (LinearLayout) findViewById(R.id.actions_group);
        this.mBetweenActionPaddingLand = getResources().getDimensionPixelSize(R.dimen.pip_between_action_padding_land);
        PipMenuIconsAlgorithm pipMenuIconsAlgorithm = new PipMenuIconsAlgorithm(((FrameLayout) this).mContext);
        this.mPipMenuIconsAlgorithm = pipMenuIconsAlgorithm;
        pipMenuIconsAlgorithm.bindViews((ViewGroup) this.mViewRoot, (ViewGroup) this.mTopEndContainer, this.mResizeHandle, this.mSettingsButton, this.mDismissButton);
        this.mDismissFadeOutDurationMs = context.getResources().getInteger(R.integer.config_pipExitAnimationDuration);
        initAccessibility();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getAlpha() != 0.0f) {
            showSettings();
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        dismissPip();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (this.mMenuContainer.getAlpha() != 0.0f) {
            expandPip();
        }
    }

    private void initAccessibility() {
        setAccessibilityDelegate(new View.AccessibilityDelegate() {
            /* class com.android.wm.shell.pip.phone.PipMenuView.AnonymousClass2 */

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, PipMenuView.this.getResources().getString(R.string.pip_menu_title)));
            }

            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i == 16 && PipMenuView.this.mMenuState == 1) {
                    PipMenuView.this.mController.showMenu();
                }
                return super.performAccessibilityAction(view, i, bundle);
            }
        });
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 111) {
            return super.onKeyUp(i, keyEvent);
        }
        hideMenu();
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.mAllowTouches) {
            return false;
        }
        if (this.mAllowMenuTimeout) {
            repostDelayedHide(2000);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mAllowMenuTimeout) {
            repostDelayedHide(2000);
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    /* access modifiers changed from: package-private */
    public void showMenu(final int i, Rect rect, final boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAllowMenuTimeout = z;
        this.mDidLastShowMenuResize = z2;
        int i2 = this.mMenuState;
        if (i2 != i) {
            this.mAllowTouches = !(z2 && (i2 == 2 || i == 2));
            cancelDelayedHide();
            AnimatorSet animatorSet = this.mMenuContainerAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            this.mMenuContainerAnimator = new AnimatorSet();
            View view = this.mMenuContainer;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, view.getAlpha(), 1.0f);
            ofFloat.addUpdateListener(this.mMenuBgUpdateListener);
            View view2 = this.mSettingsButton;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view2, View.ALPHA, view2.getAlpha(), 1.0f);
            View view3 = this.mDismissButton;
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view3, View.ALPHA, view3.getAlpha(), 1.0f);
            View view4 = this.mResizeHandle;
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view4, View.ALPHA, view4.getAlpha(), 0.0f);
            if (i == 2) {
                this.mMenuContainerAnimator.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4);
            } else {
                this.mMenuContainerAnimator.playTogether(ofFloat3, ofFloat4);
            }
            this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_IN);
            this.mMenuContainerAnimator.setDuration(125L);
            this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() {
                /* class com.android.wm.shell.pip.phone.PipMenuView.AnonymousClass3 */

                public void onAnimationEnd(Animator animator) {
                    PipMenuView.this.mAllowTouches = true;
                    PipMenuView.this.notifyMenuStateChangeFinish(i);
                    if (z) {
                        PipMenuView.this.repostDelayedHide(3500);
                    }
                }

                public void onAnimationCancel(Animator animator) {
                    PipMenuView.this.mAllowTouches = true;
                }
            });
            if (z3) {
                notifyMenuStateChangeStart(i, z2, new PipMenuView$$ExternalSyntheticLambda8(this));
            } else {
                notifyMenuStateChangeStart(i, z2, null);
                setVisibility(0);
                this.mMenuContainerAnimator.start();
            }
            updateActionViews(i, rect);
        } else if (z) {
            repostDelayedHide(2000);
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$showMenu$3() {
        AnimatorSet animatorSet = this.mMenuContainerAnimator;
        if (animatorSet != null) {
            animatorSet.setStartDelay(30);
            setVisibility(0);
            this.mMenuContainerAnimator.start();
        }
    }

    /* access modifiers changed from: package-private */
    public void fadeOutMenu() {
        this.mMenuContainer.setAlpha(0.0f);
        this.mSettingsButton.setAlpha(0.0f);
        this.mDismissButton.setAlpha(0.0f);
        this.mResizeHandle.setAlpha(0.0f);
    }

    /* access modifiers changed from: package-private */
    public void pokeMenu() {
        cancelDelayedHide();
    }

    /* access modifiers changed from: package-private */
    public void updateMenuLayout(Rect rect) {
        this.mPipMenuIconsAlgorithm.onBoundsChanged(rect);
    }

    /* access modifiers changed from: package-private */
    public void hideMenu() {
        hideMenu(null);
    }

    /* access modifiers changed from: package-private */
    public void hideMenu(Runnable runnable) {
        hideMenu(runnable, true, this.mDidLastShowMenuResize, 1);
    }

    /* access modifiers changed from: package-private */
    public void hideMenu(boolean z, int i) {
        hideMenu(null, true, z, i);
    }

    /* access modifiers changed from: package-private */
    public void hideMenu(final Runnable runnable, final boolean z, boolean z2, int i) {
        if (this.mMenuState != 0) {
            cancelDelayedHide();
            if (z) {
                notifyMenuStateChangeStart(0, z2, null);
            }
            this.mMenuContainerAnimator = new AnimatorSet();
            View view = this.mMenuContainer;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, view.getAlpha(), 0.0f);
            ofFloat.addUpdateListener(this.mMenuBgUpdateListener);
            View view2 = this.mSettingsButton;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view2, View.ALPHA, view2.getAlpha(), 0.0f);
            View view3 = this.mDismissButton;
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view3, View.ALPHA, view3.getAlpha(), 0.0f);
            View view4 = this.mResizeHandle;
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view4, View.ALPHA, view4.getAlpha(), 0.0f);
            this.mMenuContainerAnimator.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4);
            this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_OUT);
            this.mMenuContainerAnimator.setDuration(getFadeOutDuration(i));
            this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() {
                /* class com.android.wm.shell.pip.phone.PipMenuView.AnonymousClass4 */

                public void onAnimationEnd(Animator animator) {
                    PipMenuView.this.setVisibility(8);
                    if (z) {
                        PipMenuView.this.notifyMenuStateChangeFinish(0);
                    }
                    Runnable runnable = runnable;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
            this.mMenuContainerAnimator.start();
        }
    }

    /* access modifiers changed from: package-private */
    public Size getEstimatedMinMenuSize() {
        return new Size(Math.max(2, this.mActions.size()) * getResources().getDimensionPixelSize(R.dimen.pip_action_size), getResources().getDimensionPixelSize(R.dimen.pip_expand_action_size) + getResources().getDimensionPixelSize(R.dimen.pip_action_padding) + getResources().getDimensionPixelSize(R.dimen.pip_expand_container_edge_margin));
    }

    /* access modifiers changed from: package-private */
    public void setActions(Rect rect, List<RemoteAction> list) {
        this.mActions.clear();
        this.mActions.addAll(list);
        int i = this.mMenuState;
        if (i == 2) {
            updateActionViews(i, rect);
        }
    }

    private void updateActionViews(int i, Rect rect) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.expand_container);
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.actions_container);
        viewGroup2.setOnTouchListener(PipMenuView$$ExternalSyntheticLambda5.INSTANCE);
        viewGroup.setVisibility(i == 2 ? 0 : 4);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        if (!this.mActions.isEmpty()) {
            boolean z = true;
            if (!(i == 1 || i == 0)) {
                viewGroup2.setVisibility(0);
                if (this.mActionsGroup != null) {
                    LayoutInflater from = LayoutInflater.from(((FrameLayout) this).mContext);
                    while (this.mActionsGroup.getChildCount() < this.mActions.size()) {
                        this.mActionsGroup.addView((PipMenuActionView) from.inflate(R.layout.pip_menu_action, (ViewGroup) this.mActionsGroup, false));
                    }
                    int i2 = 0;
                    while (i2 < this.mActionsGroup.getChildCount()) {
                        this.mActionsGroup.getChildAt(i2).setVisibility(i2 < this.mActions.size() ? 0 : 8);
                        i2++;
                    }
                    if (rect == null || rect.width() <= rect.height()) {
                        z = false;
                    }
                    int i3 = 0;
                    while (i3 < this.mActions.size()) {
                        RemoteAction remoteAction = this.mActions.get(i3);
                        PipMenuActionView pipMenuActionView = (PipMenuActionView) this.mActionsGroup.getChildAt(i3);
                        remoteAction.getIcon().loadDrawableAsync(((FrameLayout) this).mContext, new PipMenuView$$ExternalSyntheticLambda0(pipMenuActionView), this.mMainHandler);
                        pipMenuActionView.setContentDescription(remoteAction.getContentDescription());
                        if (remoteAction.isEnabled()) {
                            pipMenuActionView.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda1(remoteAction));
                        }
                        pipMenuActionView.setEnabled(remoteAction.isEnabled());
                        pipMenuActionView.setAlpha(remoteAction.isEnabled() ? 1.0f : 0.54f);
                        ((LinearLayout.LayoutParams) pipMenuActionView.getLayoutParams()).leftMargin = (!z || i3 <= 0) ? 0 : this.mBetweenActionPaddingLand;
                        i3++;
                    }
                }
                layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.pip_action_padding);
                layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.pip_expand_container_edge_margin);
                viewGroup.requestLayout();
            }
        }
        viewGroup2.setVisibility(4);
        layoutParams.topMargin = 0;
        layoutParams.bottomMargin = 0;
        viewGroup.requestLayout();
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateActionViews$5(PipMenuActionView pipMenuActionView, Drawable drawable) {
        if (drawable != null) {
            drawable.setTint(-1);
            pipMenuActionView.setImageDrawable(drawable);
        }
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateActionViews$6(RemoteAction remoteAction, View view) {
        try {
            remoteAction.getActionIntent().send();
        } catch (PendingIntent.CanceledException e) {
            Log.w("PipMenuView", "Failed to send action", e);
        }
    }

    private void notifyMenuStateChangeStart(int i, boolean z, Runnable runnable) {
        this.mController.onMenuStateChangeStart(i, z, runnable);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyMenuStateChangeFinish(int i) {
        this.mMenuState = i;
        this.mController.onMenuStateChangeFinish(i);
    }

    private void expandPip() {
        PhonePipMenuController phonePipMenuController = this.mController;
        Objects.requireNonNull(phonePipMenuController);
        hideMenu(new PipMenuView$$ExternalSyntheticLambda6(phonePipMenuController), false, true, 1);
    }

    private void dismissPip() {
        if (this.mMenuState != 0) {
            this.mController.onPipDismiss();
        }
    }

    private void showSettings() {
        Pair<ComponentName, Integer> topPipActivity = PipUtils.getTopPipActivity(((FrameLayout) this).mContext);
        if (topPipActivity.first != null) {
            Intent intent = new Intent("android.settings.PICTURE_IN_PICTURE_SETTINGS", Uri.fromParts("package", ((ComponentName) topPipActivity.first).getPackageName(), null));
            intent.setFlags(268468224);
            ((FrameLayout) this).mContext.startActivityAsUser(intent, UserHandle.of(((Integer) topPipActivity.second).intValue()));
        }
    }

    private void cancelDelayedHide() {
        this.mMainExecutor.removeCallbacks(this.mHideMenuRunnable);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void repostDelayedHide(int i) {
        int recommendedTimeoutMillis = this.mAccessibilityManager.getRecommendedTimeoutMillis(i, 5);
        this.mMainExecutor.removeCallbacks(this.mHideMenuRunnable);
        this.mMainExecutor.executeDelayed(this.mHideMenuRunnable, (long) recommendedTimeoutMillis);
    }

    private long getFadeOutDuration(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 125;
        }
        if (i == 2) {
            return (long) this.mDismissFadeOutDurationMs;
        }
        throw new IllegalStateException("Invalid animation type " + i);
    }
}

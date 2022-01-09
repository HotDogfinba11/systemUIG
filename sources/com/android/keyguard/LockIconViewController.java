package com.android.keyguard;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.media.AudioAttributes;
import android.os.Process;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.Dumpable;
import com.android.systemui.R$anim;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

public class LockIconViewController extends ViewController<LockIconView> implements Dumpable {
    private static final AudioAttributes VIBRATION_SONIFICATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    private static final float sDefaultDensity;
    private static final float sDistAboveKgBottomAreaPx;
    private static final int sLockIconRadiusPx;
    private final View.AccessibilityDelegate mAccessibilityDelegate = new View.AccessibilityDelegate() {
        /* class com.android.keyguard.LockIconViewController.AnonymousClass1 */
        private final AccessibilityNodeInfo.AccessibilityAction mAccessibilityAuthenticateHint;
        private final AccessibilityNodeInfo.AccessibilityAction mAccessibilityEnterHint;

        {
            this.mAccessibilityAuthenticateHint = new AccessibilityNodeInfo.AccessibilityAction(16, r4.getResources().getString(R$string.accessibility_authenticate_hint));
            this.mAccessibilityEnterHint = new AccessibilityNodeInfo.AccessibilityAction(16, r4.getResources().getString(R$string.accessibility_enter_hint));
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            if (!LockIconViewController.this.isClickable()) {
                return;
            }
            if (LockIconViewController.this.mShowLockIcon) {
                accessibilityNodeInfo.addAction(this.mAccessibilityAuthenticateHint);
            } else if (LockIconViewController.this.mShowUnlockIcon) {
                accessibilityNodeInfo.addAction(this.mAccessibilityEnterHint);
            }
        }
    };
    private final AccessibilityManager mAccessibilityManager;
    private LottieAnimationView mAodFp;
    private final AuthController mAuthController;
    private final AuthController.Callback mAuthControllerCallback = new AuthController.Callback() {
        /* class com.android.keyguard.LockIconViewController.AnonymousClass7 */

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public void onAllAuthenticatorsRegistered() {
            LockIconViewController.this.updateIsUdfpsEnrolled();
            LockIconViewController.this.updateConfiguration();
        }
    };
    private final AuthRippleController mAuthRippleController;
    private int mBottomPadding;
    private boolean mCanDismissLockScreen;
    private Runnable mCancelDelayedUpdateVisibilityRunnable;
    private final ConfigurationController mConfigurationController;
    private final ConfigurationController.ConfigurationListener mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        /* class com.android.keyguard.LockIconViewController.AnonymousClass5 */

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onUiModeChanged() {
            LockIconViewController.this.updateColors();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onThemeChanged() {
            LockIconViewController.this.updateColors();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onOverlayChanged() {
            LockIconViewController.this.updateColors();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onConfigChanged(Configuration configuration) {
            LockIconViewController.this.updateConfiguration();
            LockIconViewController.this.updateColors();
        }
    };
    private boolean mDetectedLongPress;
    private boolean mDownDetected;
    private final DelayableExecutor mExecutor;
    private final FalsingManager mFalsingManager;
    private final AnimatedVectorDrawable mFpToUnlockIcon;
    private final GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        /* class com.android.keyguard.LockIconViewController.AnonymousClass6 */

        public boolean onDown(MotionEvent motionEvent) {
            LockIconViewController.this.mDetectedLongPress = false;
            if (!LockIconViewController.this.isClickable()) {
                LockIconViewController.this.mDownDetected = false;
                return false;
            }
            if (LockIconViewController.this.mVibrator != null && !LockIconViewController.this.mDownDetected) {
                LockIconViewController.this.mVibrator.vibrate(Process.myUid(), LockIconViewController.this.getContext().getOpPackageName(), UdfpsController.EFFECT_CLICK, "lockIcon-onDown", LockIconViewController.VIBRATION_SONIFICATION_ATTRIBUTES);
            }
            LockIconViewController.this.mDownDetected = true;
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            if (wasClickableOnDownEvent()) {
                LockIconViewController.this.mDetectedLongPress = true;
                if (onAffordanceClick() && LockIconViewController.this.mVibrator != null) {
                    LockIconViewController.this.mVibrator.vibrate(Process.myUid(), LockIconViewController.this.getContext().getOpPackageName(), UdfpsController.EFFECT_CLICK, "lockIcon-onLongPress", LockIconViewController.VIBRATION_SONIFICATION_ATTRIBUTES);
                }
            }
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (!wasClickableOnDownEvent()) {
                return false;
            }
            onAffordanceClick();
            return true;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!wasClickableOnDownEvent()) {
                return false;
            }
            onAffordanceClick();
            return true;
        }

        private boolean wasClickableOnDownEvent() {
            return LockIconViewController.this.mDownDetected;
        }

        private boolean onAffordanceClick() {
            if (LockIconViewController.this.mFalsingManager.isFalseTouch(14)) {
                return false;
            }
            LockIconViewController.this.mIsBouncerShowing = true;
            if (LockIconViewController.this.mUdfpsSupported && LockIconViewController.this.mShowUnlockIcon && LockIconViewController.this.mAuthRippleController != null) {
                LockIconViewController.this.mAuthRippleController.showRipple(BiometricSourceType.FINGERPRINT);
            }
            LockIconViewController.this.updateVisibility();
            if (LockIconViewController.this.mOnGestureDetectedRunnable != null) {
                LockIconViewController.this.mOnGestureDetectedRunnable.run();
            }
            LockIconViewController.this.mKeyguardViewController.showBouncer(true);
            return true;
        }
    });
    private float mHeightPixels;
    private float mInterpolatedDarkAmount;
    private boolean mIsBouncerShowing;
    private boolean mIsDozing;
    private boolean mIsKeyguardShowing;
    private final KeyguardStateController.Callback mKeyguardStateCallback = new KeyguardStateController.Callback() {
        /* class com.android.keyguard.LockIconViewController.AnonymousClass4 */

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public void onUnlockedChanged() {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mCanDismissLockScreen = lockIconViewController.mKeyguardStateController.canDismissLockScreen();
            LockIconViewController.this.updateKeyguardShowing();
            LockIconViewController.this.updateVisibility();
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public void onKeyguardShowingChanged() {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mCanDismissLockScreen = lockIconViewController.mKeyguardStateController.canDismissLockScreen();
            LockIconViewController.this.updateKeyguardShowing();
            if (LockIconViewController.this.mIsKeyguardShowing) {
                LockIconViewController lockIconViewController2 = LockIconViewController.this;
                lockIconViewController2.mUserUnlockedWithBiometric = lockIconViewController2.mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser());
            }
            LockIconViewController.this.updateIsUdfpsEnrolled();
            LockIconViewController.this.updateVisibility();
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public void onKeyguardFadingAwayChanged() {
            LockIconViewController.this.updateKeyguardShowing();
            LockIconViewController.this.updateVisibility();
        }
    };
    private final KeyguardStateController mKeyguardStateController;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
        /* class com.android.keyguard.LockIconViewController.AnonymousClass3 */

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardVisibilityChanged(boolean z) {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mIsBouncerShowing = lockIconViewController.mKeyguardViewController.isBouncerShowing();
            LockIconViewController.this.updateVisibility();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardBouncerChanged(boolean z) {
            LockIconViewController.this.mIsBouncerShowing = z;
            LockIconViewController.this.updateVisibility();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mUserUnlockedWithBiometric = lockIconViewController.mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser());
            if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                LockIconViewController.this.mRunningFPS = z;
                if (!LockIconViewController.this.mRunningFPS) {
                    if (LockIconViewController.this.mCancelDelayedUpdateVisibilityRunnable != null) {
                        LockIconViewController.this.mCancelDelayedUpdateVisibilityRunnable.run();
                    }
                    LockIconViewController lockIconViewController2 = LockIconViewController.this;
                    lockIconViewController2.mCancelDelayedUpdateVisibilityRunnable = lockIconViewController2.mExecutor.executeDelayed(new LockIconViewController$3$$ExternalSyntheticLambda0(this), 50);
                    return;
                }
                LockIconViewController.this.updateVisibility();
            }
        }

        /* access modifiers changed from: public */
        private /* synthetic */ void lambda$onBiometricRunningStateChanged$0() {
            LockIconViewController.this.updateVisibility();
        }
    };
    private final KeyguardViewController mKeyguardViewController;
    private final Drawable mLockIcon;
    private final AnimatedVectorDrawable mLockToUnlockIcon;
    private CharSequence mLockedLabel;
    private final int mMaxBurnInOffsetX;
    private final int mMaxBurnInOffsetY;
    private Runnable mOnGestureDetectedRunnable;
    private boolean mQsExpanded;
    private boolean mRunningFPS;
    private final Rect mSensorTouchLocation = new Rect();
    private boolean mShowAODFpIcon;
    private boolean mShowLockIcon;
    private boolean mShowUnlockIcon;
    private int mStatusBarState;
    private final StatusBarStateController mStatusBarStateController;
    private StatusBarStateController.StateListener mStatusBarStateListener = new StatusBarStateController.StateListener() {
        /* class com.android.keyguard.LockIconViewController.AnonymousClass2 */

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onDozeAmountChanged(float f, float f2) {
            LockIconViewController.this.mInterpolatedDarkAmount = f2;
            LockIconViewController.this.updateBurnInOffsets();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onDozingChanged(boolean z) {
            LockIconViewController.this.mIsDozing = z;
            LockIconViewController.this.updateBurnInOffsets();
            LockIconViewController.this.updateIsUdfpsEnrolled();
            LockIconViewController.this.updateVisibility();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onStateChanged(int i) {
            LockIconViewController.this.mStatusBarState = i;
            LockIconViewController.this.updateVisibility();
        }
    };
    private boolean mUdfpsEnrolled;
    private boolean mUdfpsSupported;
    private final Drawable mUnlockIcon;
    private CharSequence mUnlockedLabel;
    private boolean mUserUnlockedWithBiometric;
    private final Vibrator mVibrator;
    private float mWidthPixels;

    static {
        float f = ((float) DisplayMetrics.DENSITY_DEVICE_STABLE) / 160.0f;
        sDefaultDensity = f;
        sLockIconRadiusPx = (int) (36.0f * f);
        sDistAboveKgBottomAreaPx = f * 12.0f;
    }

    public LockIconViewController(LockIconView lockIconView, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, AuthController authController, DumpManager dumpManager, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DelayableExecutor delayableExecutor, Vibrator vibrator, AuthRippleController authRippleController) {
        super(lockIconView);
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mAuthController = authController;
        this.mKeyguardViewController = keyguardViewController;
        this.mKeyguardStateController = keyguardStateController;
        this.mFalsingManager = falsingManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mConfigurationController = configurationController;
        this.mExecutor = delayableExecutor;
        this.mVibrator = vibrator;
        this.mAuthRippleController = authRippleController;
        Context context = lockIconView.getContext();
        this.mAodFp = (LottieAnimationView) ((LockIconView) this.mView).findViewById(R$id.lock_udfps_aod_fp);
        this.mMaxBurnInOffsetX = context.getResources().getDimensionPixelSize(R$dimen.udfps_burn_in_offset_x);
        this.mMaxBurnInOffsetY = context.getResources().getDimensionPixelSize(R$dimen.udfps_burn_in_offset_y);
        this.mUnlockIcon = ((LockIconView) this.mView).getContext().getResources().getDrawable(R$drawable.ic_unlock, ((LockIconView) this.mView).getContext().getTheme());
        Resources resources = ((LockIconView) this.mView).getContext().getResources();
        int i = R$anim.lock_to_unlock;
        this.mLockIcon = resources.getDrawable(i, ((LockIconView) this.mView).getContext().getTheme());
        this.mFpToUnlockIcon = (AnimatedVectorDrawable) ((LockIconView) this.mView).getContext().getResources().getDrawable(R$anim.fp_to_unlock, ((LockIconView) this.mView).getContext().getTheme());
        this.mLockToUnlockIcon = (AnimatedVectorDrawable) ((LockIconView) this.mView).getContext().getResources().getDrawable(i, ((LockIconView) this.mView).getContext().getTheme());
        this.mUnlockedLabel = context.getResources().getString(R$string.accessibility_unlock_button);
        this.mLockedLabel = context.getResources().getString(R$string.accessibility_lock_icon);
        dumpManager.registerDumpable("LockIconViewController", this);
    }

    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        ((LockIconView) this.mView).setAccessibilityDelegate(this.mAccessibilityDelegate);
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        updateIsUdfpsEnrolled();
        updateConfiguration();
        updateKeyguardShowing();
        this.mUserUnlockedWithBiometric = false;
        this.mIsBouncerShowing = this.mKeyguardViewController.isBouncerShowing();
        this.mIsDozing = this.mStatusBarStateController.isDozing();
        this.mInterpolatedDarkAmount = this.mStatusBarStateController.getDozeAmount();
        this.mRunningFPS = this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning();
        this.mCanDismissLockScreen = this.mKeyguardStateController.canDismissLockScreen();
        this.mStatusBarState = this.mStatusBarStateController.getState();
        updateColors();
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mAuthController.addCallback(this.mAuthControllerCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        this.mKeyguardStateController.addCallback(this.mKeyguardStateCallback);
        this.mDownDetected = false;
        updateBurnInOffsets();
        updateVisibility();
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mAuthController.removeCallback(this.mAuthControllerCallback);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        this.mKeyguardStateController.removeCallback(this.mKeyguardStateCallback);
        Runnable runnable = this.mCancelDelayedUpdateVisibilityRunnable;
        if (runnable != null) {
            runnable.run();
            this.mCancelDelayedUpdateVisibilityRunnable = null;
        }
    }

    public float getTop() {
        return ((LockIconView) this.mView).getLocationTop();
    }

    public void setQsExpanded(boolean z) {
        this.mQsExpanded = z;
        updateVisibility();
    }

    private void updateVisibility() {
        Runnable runnable = this.mCancelDelayedUpdateVisibilityRunnable;
        if (runnable != null) {
            runnable.run();
            this.mCancelDelayedUpdateVisibilityRunnable = null;
        }
        if (this.mIsKeyguardShowing || this.mIsDozing) {
            boolean z = true;
            boolean z2 = this.mUdfpsEnrolled && !this.mShowUnlockIcon && !this.mShowLockIcon;
            boolean z3 = this.mShowLockIcon;
            boolean z4 = this.mShowUnlockIcon;
            this.mShowLockIcon = !this.mCanDismissLockScreen && !this.mUserUnlockedWithBiometric && isLockScreen() && (!this.mUdfpsEnrolled || !this.mRunningFPS);
            this.mShowUnlockIcon = this.mCanDismissLockScreen && isLockScreen();
            if (!this.mIsDozing || !this.mUdfpsEnrolled || this.mRunningFPS) {
                z = false;
            }
            this.mShowAODFpIcon = z;
            CharSequence contentDescription = ((LockIconView) this.mView).getContentDescription();
            if (this.mShowLockIcon) {
                ((LockIconView) this.mView).setImageDrawable(this.mLockIcon);
                ((LockIconView) this.mView).setVisibility(0);
                ((LockIconView) this.mView).setContentDescription(this.mLockedLabel);
            } else if (this.mShowUnlockIcon) {
                if (!z4) {
                    if (z2) {
                        ((LockIconView) this.mView).setImageDrawable(this.mFpToUnlockIcon);
                        this.mFpToUnlockIcon.forceAnimationOnUI();
                        this.mFpToUnlockIcon.start();
                    } else if (z3) {
                        ((LockIconView) this.mView).setImageDrawable(this.mLockToUnlockIcon);
                        this.mLockToUnlockIcon.forceAnimationOnUI();
                        this.mLockToUnlockIcon.start();
                    } else {
                        ((LockIconView) this.mView).setImageDrawable(this.mUnlockIcon);
                    }
                }
                ((LockIconView) this.mView).setVisibility(0);
                ((LockIconView) this.mView).setContentDescription(this.mUnlockedLabel);
            } else if (this.mShowAODFpIcon) {
                ((LockIconView) this.mView).setImageDrawable(null);
                ((LockIconView) this.mView).setContentDescription(null);
                this.mAodFp.setVisibility(0);
                this.mAodFp.setContentDescription(this.mCanDismissLockScreen ? this.mUnlockedLabel : this.mLockedLabel);
                ((LockIconView) this.mView).setVisibility(0);
            } else {
                ((LockIconView) this.mView).setVisibility(4);
                ((LockIconView) this.mView).setContentDescription(null);
            }
            if (!this.mShowAODFpIcon) {
                this.mAodFp.setVisibility(4);
                this.mAodFp.setContentDescription(null);
            }
            if (!Objects.equals(contentDescription, ((LockIconView) this.mView).getContentDescription()) && ((LockIconView) this.mView).getContentDescription() != null) {
                T t = this.mView;
                ((LockIconView) t).announceForAccessibility(((LockIconView) t).getContentDescription());
                return;
            }
            return;
        }
        ((LockIconView) this.mView).setVisibility(4);
    }

    private boolean isLockScreen() {
        return !this.mIsDozing && !this.mIsBouncerShowing && !this.mQsExpanded && this.mStatusBarState == 1;
    }

    private void updateKeyguardShowing() {
        this.mIsKeyguardShowing = this.mKeyguardStateController.isShowing() && !this.mKeyguardStateController.isKeyguardGoingAway();
    }

    private void updateColors() {
        ((LockIconView) this.mView).updateColorAndBackgroundVisibility();
    }

    private void updateConfiguration() {
        DisplayMetrics displayMetrics = ((LockIconView) this.mView).getContext().getResources().getDisplayMetrics();
        this.mWidthPixels = (float) displayMetrics.widthPixels;
        this.mHeightPixels = (float) displayMetrics.heightPixels;
        this.mBottomPadding = ((LockIconView) this.mView).getContext().getResources().getDimensionPixelSize(R$dimen.lock_icon_margin_bottom);
        this.mUnlockedLabel = ((LockIconView) this.mView).getContext().getResources().getString(R$string.accessibility_unlock_button);
        this.mLockedLabel = ((LockIconView) this.mView).getContext().getResources().getString(R$string.accessibility_lock_icon);
        updateLockIconLocation();
    }

    private void updateLockIconLocation() {
        if (this.mUdfpsSupported) {
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.mAuthController.getUdfpsProps().get(0);
            ((LockIconView) this.mView).setCenterLocation(new PointF((float) fingerprintSensorPropertiesInternal.sensorLocationX, (float) fingerprintSensorPropertiesInternal.sensorLocationY), fingerprintSensorPropertiesInternal.sensorRadius);
        } else {
            float f = (this.mHeightPixels - ((float) this.mBottomPadding)) - sDistAboveKgBottomAreaPx;
            int i = sLockIconRadiusPx;
            ((LockIconView) this.mView).setCenterLocation(new PointF(this.mWidthPixels / 2.0f, f - ((float) i)), i);
        }
        ((LockIconView) this.mView).getHitRect(this.mSensorTouchLocation);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("mUdfpsSupported: " + this.mUdfpsSupported);
        printWriter.println("mUdfpsEnrolled: " + this.mUdfpsEnrolled);
        printWriter.println("mIsKeyguardShowing: " + this.mIsKeyguardShowing);
        printWriter.println(" mShowUnlockIcon: " + this.mShowUnlockIcon);
        printWriter.println(" mShowLockIcon: " + this.mShowLockIcon);
        printWriter.println(" mShowAODFpIcon: " + this.mShowAODFpIcon);
        printWriter.println("  mIsDozing: " + this.mIsDozing);
        printWriter.println("  mIsBouncerShowing: " + this.mIsBouncerShowing);
        printWriter.println("  mUserUnlockedWithBiometric: " + this.mUserUnlockedWithBiometric);
        printWriter.println("  mRunningFPS: " + this.mRunningFPS);
        printWriter.println("  mCanDismissLockScreen: " + this.mCanDismissLockScreen);
        printWriter.println("  mStatusBarState: " + StatusBarState.toShortString(this.mStatusBarState));
        printWriter.println("  mQsExpanded: " + this.mQsExpanded);
        printWriter.println("  mInterpolatedDarkAmount: " + this.mInterpolatedDarkAmount);
        T t = this.mView;
        if (t != null) {
            ((LockIconView) t).dump(fileDescriptor, printWriter, strArr);
        }
    }

    public void dozeTimeTick() {
        updateBurnInOffsets();
    }

    private void updateBurnInOffsets() {
        float lerp = MathUtils.lerp(0.0f, (float) (BurnInHelperKt.getBurnInOffset(this.mMaxBurnInOffsetX * 2, true) - this.mMaxBurnInOffsetX), this.mInterpolatedDarkAmount);
        float lerp2 = MathUtils.lerp(0.0f, (float) (BurnInHelperKt.getBurnInOffset(this.mMaxBurnInOffsetY * 2, false) - this.mMaxBurnInOffsetY), this.mInterpolatedDarkAmount);
        float lerp3 = MathUtils.lerp(0.0f, BurnInHelperKt.getBurnInProgressOffset(), this.mInterpolatedDarkAmount);
        this.mAodFp.setTranslationX(lerp);
        this.mAodFp.setTranslationY(lerp2);
        this.mAodFp.setProgress(lerp3);
        this.mAodFp.setAlpha(this.mInterpolatedDarkAmount * 255.0f);
    }

    private void updateIsUdfpsEnrolled() {
        boolean z = this.mUdfpsSupported;
        boolean z2 = this.mUdfpsEnrolled;
        boolean z3 = this.mAuthController.getUdfpsSensorLocation() != null;
        this.mUdfpsSupported = z3;
        ((LockIconView) this.mView).setUseBackground(z3);
        boolean isUdfpsEnrolled = this.mKeyguardUpdateMonitor.isUdfpsEnrolled();
        this.mUdfpsEnrolled = isUdfpsEnrolled;
        if (z != this.mUdfpsSupported || z2 != isUdfpsEnrolled) {
            updateVisibility();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent, Runnable runnable) {
        if (this.mSensorTouchLocation.contains((int) motionEvent.getX(), (int) motionEvent.getY()) && (((LockIconView) this.mView).getVisibility() == 0 || this.mAodFp.getVisibility() == 0)) {
            this.mOnGestureDetectedRunnable = runnable;
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        if (!this.mDownDetected) {
            return false;
        }
        if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
            this.mDownDetected = false;
        }
        return true;
    }

    private boolean isClickable() {
        return this.mUdfpsSupported || this.mShowUnlockIcon;
    }

    public void setAlpha(float f) {
        ((LockIconView) this.mView).setAlpha(f);
    }
}

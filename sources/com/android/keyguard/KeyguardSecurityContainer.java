package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R$bool;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import java.util.ArrayList;
import java.util.List;

public class KeyguardSecurityContainer extends FrameLayout {
    private int mActivePointerId;
    private AlertDialog mAlertDialog;
    private boolean mDisappearAnimRunning;
    private boolean mIsDragging;
    private boolean mIsSecurityViewLeftAligned;
    private float mLastTouchY;
    private final List<Gefingerpoken> mMotionEventListeners;
    private boolean mOneHandedMode;
    private ViewPropertyAnimator mRunningOneHandedAnimator;
    private KeyguardSecurityModel.SecurityMode mSecurityMode;
    KeyguardSecurityViewFlipper mSecurityViewFlipper;
    private final SpringAnimation mSpringAnimation;
    private float mStartTouchY;
    private SwipeListener mSwipeListener;
    private boolean mSwipeUpToRetry;
    private final VelocityTracker mVelocityTracker;
    private final ViewConfiguration mViewConfiguration;
    private final WindowInsetsAnimation.Callback mWindowInsetsAnimationCallback;

    public interface SecurityCallback {
        boolean dismiss(boolean z, int i, boolean z2);

        void finish(boolean z, int i);

        void onCancelClicked();

        void onSecurityModeChanged(KeyguardSecurityModel.SecurityMode securityMode, boolean z);

        void reset();

        void userActivity();
    }

    public interface SwipeListener {
        void onSwipeUp();
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public enum BouncerUiEvent implements UiEventLogger.UiEventEnum {
        UNKNOWN(0),
        BOUNCER_DISMISS_EXTENDED_ACCESS(413),
        BOUNCER_DISMISS_BIOMETRIC(414),
        BOUNCER_DISMISS_NONE_SECURITY(415),
        BOUNCER_DISMISS_PASSWORD(416),
        BOUNCER_DISMISS_SIM(417),
        BOUNCER_PASSWORD_SUCCESS(418),
        BOUNCER_PASSWORD_FAILURE(419);
        
        private final int mId;

        private BouncerUiEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardSecurityContainer(Context context) {
        this(context, null, 0);
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mMotionEventListeners = new ArrayList();
        this.mLastTouchY = -1.0f;
        this.mActivePointerId = -1;
        this.mStartTouchY = -1.0f;
        this.mIsSecurityViewLeftAligned = true;
        this.mOneHandedMode = false;
        this.mSecurityMode = KeyguardSecurityModel.SecurityMode.Invalid;
        this.mWindowInsetsAnimationCallback = new WindowInsetsAnimation.Callback(0) {
            /* class com.android.keyguard.KeyguardSecurityContainer.AnonymousClass1 */
            private final Rect mFinalBounds = new Rect();
            private final Rect mInitialBounds = new Rect();

            public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                KeyguardSecurityContainer.this.mSecurityViewFlipper.getBoundsOnScreen(this.mInitialBounds);
            }

            public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                if (!KeyguardSecurityContainer.this.mDisappearAnimRunning) {
                    KeyguardSecurityContainer.this.beginJankInstrument(17);
                } else {
                    KeyguardSecurityContainer.this.beginJankInstrument(20);
                }
                KeyguardSecurityContainer.this.mSecurityViewFlipper.getBoundsOnScreen(this.mFinalBounds);
                return bounds;
            }

            public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                int i;
                if (KeyguardSecurityContainer.this.mDisappearAnimRunning) {
                    i = -(this.mFinalBounds.bottom - this.mInitialBounds.bottom);
                } else {
                    i = this.mInitialBounds.bottom - this.mFinalBounds.bottom;
                }
                float f = (float) i;
                float f2 = KeyguardSecurityContainer.this.mDisappearAnimRunning ? -(((float) (this.mFinalBounds.bottom - this.mInitialBounds.bottom)) * 0.75f) : 0.0f;
                int i2 = 0;
                float f3 = 1.0f;
                for (WindowInsetsAnimation windowInsetsAnimation : list) {
                    if ((windowInsetsAnimation.getTypeMask() & WindowInsets.Type.ime()) != 0) {
                        f3 = windowInsetsAnimation.getInterpolatedFraction();
                        i2 += (int) MathUtils.lerp(f, f2, f3);
                    }
                }
                KeyguardSecurityContainer keyguardSecurityContainer = KeyguardSecurityContainer.this;
                keyguardSecurityContainer.mSecurityViewFlipper.animateForIme(i2, f3, !keyguardSecurityContainer.mDisappearAnimRunning);
                return windowInsets;
            }

            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                if (!KeyguardSecurityContainer.this.mDisappearAnimRunning) {
                    KeyguardSecurityContainer.this.endJankInstrument(17);
                    KeyguardSecurityContainer.this.mSecurityViewFlipper.animateForIme(0, 1.0f, true);
                    return;
                }
                KeyguardSecurityContainer.this.endJankInstrument(20);
            }
        };
        this.mSpringAnimation = new SpringAnimation(this, DynamicAnimation.Y);
        this.mViewConfiguration = ViewConfiguration.get(context);
    }

    /* access modifiers changed from: package-private */
    public void onResume(KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
        this.mSecurityMode = securityMode;
        this.mSecurityViewFlipper.setWindowInsetsAnimationCallback(this.mWindowInsetsAnimationCallback);
        updateBiometricRetry(securityMode, z);
        updateLayoutForSecurityMode(securityMode);
    }

    /* access modifiers changed from: package-private */
    public void updateLayoutForSecurityMode(KeyguardSecurityModel.SecurityMode securityMode) {
        this.mSecurityMode = securityMode;
        boolean canUseOneHandedBouncer = canUseOneHandedBouncer();
        this.mOneHandedMode = canUseOneHandedBouncer;
        if (canUseOneHandedBouncer) {
            this.mIsSecurityViewLeftAligned = isOneHandedKeyguardLeftAligned(((FrameLayout) this).mContext);
        }
        updateSecurityViewGravity();
        updateSecurityViewLocation(false);
    }

    public void updateKeyguardPosition(float f) {
        if (this.mOneHandedMode) {
            moveBouncerForXCoordinate(f, false);
        }
    }

    private boolean canUseOneHandedBouncer() {
        if (getResources().getBoolean(17891532) && KeyguardSecurityModel.isSecurityViewOneHanded(this.mSecurityMode)) {
            return getResources().getBoolean(R$bool.can_use_one_handed_bouncer);
        }
        return false;
    }

    private boolean isOneHandedKeyguardLeftAligned(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), "one_handed_keyguard_side") == 0;
        } catch (Settings.SettingNotFoundException unused) {
            return true;
        }
    }

    private void updateSecurityViewGravity() {
        KeyguardSecurityViewFlipper findKeyguardSecurityView = findKeyguardSecurityView();
        if (findKeyguardSecurityView != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) findKeyguardSecurityView.getLayoutParams();
            if (this.mOneHandedMode) {
                layoutParams.gravity = 83;
            } else {
                layoutParams.gravity = 1;
            }
            findKeyguardSecurityView.setLayoutParams(layoutParams);
        }
    }

    private void updateSecurityViewLocation(boolean z) {
        KeyguardSecurityViewFlipper findKeyguardSecurityView = findKeyguardSecurityView();
        if (findKeyguardSecurityView != null) {
            if (!this.mOneHandedMode) {
                findKeyguardSecurityView.setTranslationX(0.0f);
                return;
            }
            ViewPropertyAnimator viewPropertyAnimator = this.mRunningOneHandedAnimator;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
                this.mRunningOneHandedAnimator = null;
            }
            int measuredWidth = this.mIsSecurityViewLeftAligned ? 0 : (int) (((float) getMeasuredWidth()) / 2.0f);
            if (z) {
                ViewPropertyAnimator translationX = findKeyguardSecurityView.animate().translationX((float) measuredWidth);
                this.mRunningOneHandedAnimator = translationX;
                translationX.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                this.mRunningOneHandedAnimator.setListener(new AnimatorListenerAdapter() {
                    /* class com.android.keyguard.KeyguardSecurityContainer.AnonymousClass2 */

                    public void onAnimationEnd(Animator animator) {
                        KeyguardSecurityContainer.this.mRunningOneHandedAnimator = null;
                    }
                });
                this.mRunningOneHandedAnimator.setDuration(360);
                this.mRunningOneHandedAnimator.start();
                return;
            }
            findKeyguardSecurityView.setTranslationX((float) measuredWidth);
        }
    }

    private KeyguardSecurityViewFlipper findKeyguardSecurityView() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (isKeyguardSecurityView(childAt)) {
                return (KeyguardSecurityViewFlipper) childAt;
            }
        }
        return null;
    }

    private boolean isKeyguardSecurityView(View view) {
        return view instanceof KeyguardSecurityViewFlipper;
    }

    public void onPause() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mAlertDialog = null;
        }
        this.mSecurityViewFlipper.setWindowInsetsAnimationCallback(null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        if (r3 != 3) goto L_0x007c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r6) {
        /*
        // Method dump skipped, instructions count: 125
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityContainer.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x007c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
        // Method dump skipped, instructions count: 163
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityContainer.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: package-private */
    public void addMotionEventListener(Gefingerpoken gefingerpoken) {
        this.mMotionEventListeners.add(gefingerpoken);
    }

    /* access modifiers changed from: package-private */
    public void removeMotionEventListener(Gefingerpoken gefingerpoken) {
        this.mMotionEventListeners.remove(gefingerpoken);
    }

    private void handleTap(MotionEvent motionEvent) {
        if (this.mOneHandedMode) {
            moveBouncerForXCoordinate(motionEvent.getX(), true);
        }
    }

    private void moveBouncerForXCoordinate(float f, boolean z) {
        if ((this.mIsSecurityViewLeftAligned && f > ((float) getWidth()) / 2.0f) || (!this.mIsSecurityViewLeftAligned && f < ((float) getWidth()) / 2.0f)) {
            this.mIsSecurityViewLeftAligned = !this.mIsSecurityViewLeftAligned;
            Settings.Global.putInt(((FrameLayout) this).mContext.getContentResolver(), "one_handed_keyguard_side", !this.mIsSecurityViewLeftAligned ? 1 : 0);
            updateSecurityViewLocation(z);
        }
    }

    /* access modifiers changed from: package-private */
    public void setSwipeListener(SwipeListener swipeListener) {
        this.mSwipeListener = swipeListener;
    }

    private void startSpringAnimation(float f) {
        ((SpringAnimation) this.mSpringAnimation.setStartVelocity(f)).animateToFinalPosition(0.0f);
    }

    public void startDisappearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        this.mDisappearAnimRunning = true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void beginJankInstrument(int i) {
        KeyguardInputView securityView = this.mSecurityViewFlipper.getSecurityView();
        if (securityView != null) {
            InteractionJankMonitor.getInstance().begin(securityView, i);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void endJankInstrument(int i) {
        InteractionJankMonitor.getInstance().end(i);
    }

    private void updateBiometricRetry(KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
        this.mSwipeUpToRetry = (!z || securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk || securityMode == KeyguardSecurityModel.SecurityMode.None) ? false : true;
    }

    public CharSequence getTitle() {
        return this.mSecurityViewFlipper.getTitle();
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSecurityViewFlipper = (KeyguardSecurityViewFlipper) findViewById(R$id.view_flipper);
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int max = Integer.max(windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom, windowInsets.getInsets(WindowInsets.Type.ime()).bottom);
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), max);
        return windowInsets.inset(0, 0, 0, max);
    }

    private void showDialog(String str, String str2) {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        AlertDialog create = new AlertDialog.Builder(((FrameLayout) this).mContext).setTitle(str).setMessage(str2).setCancelable(false).setNeutralButton(R$string.ok, (DialogInterface.OnClickListener) null).create();
        this.mAlertDialog = create;
        if (!(((FrameLayout) this).mContext instanceof Activity)) {
            create.getWindow().setType(2009);
        }
        this.mAlertDialog.show();
    }

    /* access modifiers changed from: package-private */
    public void showTimeoutDialog(int i, int i2, LockPatternUtils lockPatternUtils, KeyguardSecurityModel.SecurityMode securityMode) {
        int i3;
        int i4 = i2 / 1000;
        int i5 = AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()];
        if (i5 == 1) {
            i3 = R$string.kg_too_many_failed_pattern_attempts_dialog_message;
        } else if (i5 == 2) {
            i3 = R$string.kg_too_many_failed_pin_attempts_dialog_message;
        } else if (i5 != 3) {
            i3 = 0;
        } else {
            i3 = R$string.kg_too_many_failed_password_attempts_dialog_message;
        }
        if (i3 != 0) {
            showDialog(null, ((FrameLayout) this).mContext.getString(i3, Integer.valueOf(lockPatternUtils.getCurrentFailedPasswordAttempts(i)), Integer.valueOf(i4)));
        }
    }

    /* renamed from: com.android.keyguard.KeyguardSecurityContainer$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.android.keyguard.KeyguardSecurityModel$SecurityMode[] r0 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.android.keyguard.KeyguardSecurityContainer.AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = r0
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.Pattern     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = com.android.keyguard.KeyguardSecurityContainer.AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode     // Catch:{ NoSuchFieldError -> 0x001d }
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.PIN     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = com.android.keyguard.KeyguardSecurityContainer.AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.Password     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = com.android.keyguard.KeyguardSecurityContainer.AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.Invalid     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = com.android.keyguard.KeyguardSecurityContainer.AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode     // Catch:{ NoSuchFieldError -> 0x003e }
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.None     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = com.android.keyguard.KeyguardSecurityContainer.AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.SimPin     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = com.android.keyguard.KeyguardSecurityContainer.AnonymousClass3.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.android.keyguard.KeyguardSecurityModel$SecurityMode r1 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.SimPuk     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecurityContainer.AnonymousClass3.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i) / 2, View.MeasureSpec.getMode(i));
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < getChildCount(); i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                if (!this.mOneHandedMode || !isKeyguardSecurityView(childAt)) {
                    measureChildWithMargins(childAt, i, 0, i2, 0);
                } else {
                    measureChildWithMargins(childAt, makeMeasureSpec, 0, i2, 0);
                }
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                int max = Math.max(i3, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                i4 = Math.max(i4, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                i3 = max;
                i5 = FrameLayout.combineMeasuredStates(i5, childAt.getMeasuredState());
            }
        }
        setMeasuredDimension(FrameLayout.resolveSizeAndState(Math.max(i3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, i5), FrameLayout.resolveSizeAndState(Math.max(i4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, i5 << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateSecurityViewLocation(false);
    }

    /* access modifiers changed from: package-private */
    public void showAlmostAtWipeDialog(int i, int i2, int i3) {
        String str;
        if (i3 == 1) {
            str = ((FrameLayout) this).mContext.getString(R$string.kg_failed_attempts_almost_at_wipe, Integer.valueOf(i), Integer.valueOf(i2));
        } else if (i3 == 2) {
            str = ((FrameLayout) this).mContext.getString(R$string.kg_failed_attempts_almost_at_erase_profile, Integer.valueOf(i), Integer.valueOf(i2));
        } else if (i3 != 3) {
            str = null;
        } else {
            str = ((FrameLayout) this).mContext.getString(R$string.kg_failed_attempts_almost_at_erase_user, Integer.valueOf(i), Integer.valueOf(i2));
        }
        showDialog(null, str);
    }

    /* access modifiers changed from: package-private */
    public void showWipeDialog(int i, int i2) {
        String str;
        if (i2 == 1) {
            str = ((FrameLayout) this).mContext.getString(R$string.kg_failed_attempts_now_wiping, Integer.valueOf(i));
        } else if (i2 == 2) {
            str = ((FrameLayout) this).mContext.getString(R$string.kg_failed_attempts_now_erasing_profile, Integer.valueOf(i));
        } else if (i2 != 3) {
            str = null;
        } else {
            str = ((FrameLayout) this).mContext.getString(R$string.kg_failed_attempts_now_erasing_user, Integer.valueOf(i));
        }
        showDialog(null, str);
    }

    public void reset() {
        this.mDisappearAnimRunning = false;
    }
}

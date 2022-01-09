package com.android.keyguard;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.Utils;
import com.android.systemui.R$id;
import com.android.systemui.R$plurals;
import com.android.systemui.R$string;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.util.ViewController;
import java.util.List;

public class KeyguardPatternViewController extends KeyguardInputViewController<KeyguardPatternView> {
    private Runnable mCancelPatternRunnable = new Runnable() {
        /* class com.android.keyguard.KeyguardPatternViewController.AnonymousClass2 */

        public void run() {
            KeyguardPatternViewController.this.mLockPatternView.clearPattern();
        }
    };
    private CountDownTimer mCountdownTimer;
    private EmergencyButtonController.EmergencyButtonCallback mEmergencyButtonCallback = new EmergencyButtonController.EmergencyButtonCallback() {
        /* class com.android.keyguard.KeyguardPatternViewController.AnonymousClass1 */

        @Override // com.android.keyguard.EmergencyButtonController.EmergencyButtonCallback
        public void onEmergencyButtonClickedWhenInCall() {
            KeyguardPatternViewController.this.getKeyguardSecurityCallback().reset();
        }
    };
    private final EmergencyButtonController mEmergencyButtonController;
    private final FalsingCollector mFalsingCollector;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private final LatencyTracker mLatencyTracker;
    private final LockPatternUtils mLockPatternUtils;
    private LockPatternView mLockPatternView;
    private KeyguardMessageAreaController mMessageAreaController;
    private final KeyguardMessageAreaController.Factory mMessageAreaControllerFactory;
    private AsyncTask<?, ?, ?> mPendingLockCheck;

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return false;
    }

    /* access modifiers changed from: private */
    public class UnlockPatternListener implements LockPatternView.OnPatternListener {
        public void onPatternCleared() {
        }

        private UnlockPatternListener() {
            KeyguardPatternViewController.this = r1;
        }

        public void onPatternStart() {
            KeyguardPatternViewController.this.mLockPatternView.removeCallbacks(KeyguardPatternViewController.this.mCancelPatternRunnable);
            KeyguardPatternViewController.this.mMessageAreaController.setMessage("");
        }

        public void onPatternCellAdded(List<LockPatternView.Cell> list) {
            KeyguardPatternViewController.this.getKeyguardSecurityCallback().userActivity();
            KeyguardPatternViewController.this.getKeyguardSecurityCallback().onUserInput();
        }

        public void onPatternDetected(List<LockPatternView.Cell> list) {
            KeyguardPatternViewController.this.mKeyguardUpdateMonitor.setCredentialAttempted();
            KeyguardPatternViewController.this.mLockPatternView.disableInput();
            if (KeyguardPatternViewController.this.mPendingLockCheck != null) {
                KeyguardPatternViewController.this.mPendingLockCheck.cancel(false);
            }
            final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            if (list.size() < 4) {
                if (list.size() == 1) {
                    KeyguardPatternViewController.this.mFalsingCollector.updateFalseConfidence(FalsingClassifier.Result.falsed(0.7d, UnlockPatternListener.class.getSimpleName(), "empty pattern input"));
                }
                KeyguardPatternViewController.this.mLockPatternView.enableInput();
                onPatternChecked(currentUser, false, 0, false);
                return;
            }
            KeyguardPatternViewController.this.mLatencyTracker.onActionStart(3);
            KeyguardPatternViewController.this.mLatencyTracker.onActionStart(4);
            KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
            keyguardPatternViewController.mPendingLockCheck = LockPatternChecker.checkCredential(keyguardPatternViewController.mLockPatternUtils, LockscreenCredential.createPattern(list), currentUser, new LockPatternChecker.OnCheckCallback() {
                /* class com.android.keyguard.KeyguardPatternViewController.UnlockPatternListener.AnonymousClass1 */

                public void onEarlyMatched() {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(3);
                    UnlockPatternListener.this.onPatternChecked(currentUser, true, 0, true);
                }

                public void onChecked(boolean z, int i) {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(4);
                    KeyguardPatternViewController.this.mLockPatternView.enableInput();
                    KeyguardPatternViewController.this.mPendingLockCheck = null;
                    if (!z) {
                        UnlockPatternListener.this.onPatternChecked(currentUser, false, i, true);
                    }
                }

                public void onCancelled() {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(4);
                }
            });
            if (list.size() > 2) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().userActivity();
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().onUserInput();
            }
        }

        private void onPatternChecked(int i, boolean z, int i2, boolean z2) {
            boolean z3 = KeyguardUpdateMonitor.getCurrentUser() == i;
            if (z) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().reportUnlockAttempt(i, true, 0);
                if (z3) {
                    KeyguardPatternViewController.this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                    KeyguardPatternViewController.this.mLatencyTracker.onActionStart(11);
                    KeyguardPatternViewController.this.getKeyguardSecurityCallback().dismiss(true, i);
                    return;
                }
                return;
            }
            KeyguardPatternViewController.this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            if (z2) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().reportUnlockAttempt(i, false, i2);
                if (i2 > 0) {
                    KeyguardPatternViewController.this.handleAttemptLockout(KeyguardPatternViewController.this.mLockPatternUtils.setLockoutAttemptDeadline(i, i2));
                }
            }
            if (i2 == 0) {
                KeyguardPatternViewController.this.mMessageAreaController.setMessage(R$string.kg_wrong_pattern);
                KeyguardPatternViewController.this.mLockPatternView.postDelayed(KeyguardPatternViewController.this.mCancelPatternRunnable, 2000);
            }
        }
    }

    protected KeyguardPatternViewController(KeyguardPatternView keyguardPatternView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory) {
        super(keyguardPatternView, securityMode, keyguardSecurityCallback, emergencyButtonController);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLockPatternUtils = lockPatternUtils;
        this.mLatencyTracker = latencyTracker;
        this.mFalsingCollector = falsingCollector;
        this.mEmergencyButtonController = emergencyButtonController;
        this.mMessageAreaControllerFactory = factory;
        this.mMessageAreaController = factory.create(KeyguardMessageArea.findSecurityMessageDisplay(this.mView));
        this.mLockPatternView = ((KeyguardPatternView) this.mView).findViewById(R$id.lockPatternView);
    }

    @Override // com.android.systemui.util.ViewController, com.android.keyguard.KeyguardInputViewController
    public void onInit() {
        super.onInit();
        this.mMessageAreaController.init();
    }

    @Override // com.android.systemui.util.ViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
        this.mLockPatternView.setOnPatternListener(new UnlockPatternListener());
        this.mLockPatternView.setSaveEnabled(false);
        this.mLockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(KeyguardUpdateMonitor.getCurrentUser()));
        this.mLockPatternView.setTactileFeedbackEnabled(this.mLockPatternUtils.isTactileFeedbackEnabled());
        this.mLockPatternView.setOnTouchListener(new KeyguardPatternViewController$$ExternalSyntheticLambda1(this));
        this.mEmergencyButtonController.setEmergencyButtonCallback(this.mEmergencyButtonCallback);
        View findViewById = ((KeyguardPatternView) this.mView).findViewById(R$id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(new KeyguardPatternViewController$$ExternalSyntheticLambda0(this));
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ boolean lambda$onViewAttached$0(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() != 0) {
            return false;
        }
        this.mFalsingCollector.avoidGesture();
        return false;
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onViewAttached$1(View view) {
        getKeyguardSecurityCallback().reset();
        getKeyguardSecurityCallback().onCancelClicked();
    }

    @Override // com.android.systemui.util.ViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewDetached() {
        super.onViewDetached();
        this.mLockPatternView.setOnPatternListener((LockPatternView.OnPatternListener) null);
        this.mLockPatternView.setOnTouchListener((View.OnTouchListener) null);
        this.mEmergencyButtonController.setEmergencyButtonCallback(null);
        View findViewById = ((KeyguardPatternView) this.mView).findViewById(R$id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(null);
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void reset() {
        this.mLockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(KeyguardUpdateMonitor.getCurrentUser()));
        this.mLockPatternView.enableInput();
        this.mLockPatternView.setEnabled(true);
        this.mLockPatternView.clearPattern();
        long lockoutAttemptDeadline = this.mLockPatternUtils.getLockoutAttemptDeadline(KeyguardUpdateMonitor.getCurrentUser());
        if (lockoutAttemptDeadline != 0) {
            handleAttemptLockout(lockoutAttemptDeadline);
        } else {
            displayDefaultSecurityMessage();
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void reloadColors() {
        super.reloadColors();
        this.mMessageAreaController.reloadColors();
        int defaultColor = Utils.getColorAttr(this.mLockPatternView.getContext(), 16842806).getDefaultColor();
        this.mLockPatternView.setColors(defaultColor, defaultColor, Utils.getColorError(this.mLockPatternView.getContext()).getDefaultColor());
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        super.onPause();
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountdownTimer = null;
        }
        AsyncTask<?, ?, ?> asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
            this.mPendingLockCheck = null;
        }
        displayDefaultSecurityMessage();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void showPromptReason(int i) {
        if (i == 0) {
            return;
        }
        if (i == 1) {
            this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_restart_pattern);
        } else if (i == 2) {
            this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_timeout_pattern);
        } else if (i == 3) {
            this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_device_admin);
        } else if (i == 4) {
            this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_user_request);
        } else if (i != 6) {
            this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_timeout_pattern);
        } else {
            this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_timeout_pattern);
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void showMessage(CharSequence charSequence, ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.mMessageAreaController.setNextMessageColor(colorStateList);
        }
        this.mMessageAreaController.setMessage(charSequence);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void startAppearAnimation() {
        super.startAppearAnimation();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public boolean startDisappearAnimation(Runnable runnable) {
        return ((KeyguardPatternView) this.mView).startDisappearAnimation(this.mKeyguardUpdateMonitor.needsSlowUnlockTransition(), runnable);
    }

    private void displayDefaultSecurityMessage() {
        this.mMessageAreaController.setMessage("");
    }

    private void handleAttemptLockout(long j) {
        this.mLockPatternView.clearPattern();
        this.mLockPatternView.setEnabled(false);
        this.mCountdownTimer = new CountDownTimer(((long) Math.ceil(((double) (j - SystemClock.elapsedRealtime())) / 1000.0d)) * 1000, 1000) {
            /* class com.android.keyguard.KeyguardPatternViewController.AnonymousClass3 */

            public void onTick(long j) {
                int round = (int) Math.round(((double) j) / 1000.0d);
                KeyguardPatternViewController.this.mMessageAreaController.setMessage(((KeyguardPatternView) ((ViewController) KeyguardPatternViewController.this).mView).getResources().getQuantityString(R$plurals.kg_too_many_failed_attempts_countdown, round, Integer.valueOf(round)));
            }

            public void onFinish() {
                KeyguardPatternViewController.this.mLockPatternView.setEnabled(true);
                KeyguardPatternViewController.this.displayDefaultSecurityMessage();
            }
        }.start();
    }
}

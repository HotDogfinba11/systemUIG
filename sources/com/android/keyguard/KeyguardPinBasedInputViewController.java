package com.android.keyguard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardPinBasedInputView;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R$id;
import com.android.systemui.classifier.FalsingCollector;

public abstract class KeyguardPinBasedInputViewController<T extends KeyguardPinBasedInputView> extends KeyguardAbsKeyInputViewController<T> {
    private final View.OnTouchListener mActionButtonTouchListener = new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda4(this);
    private final FalsingCollector mFalsingCollector;
    private final LiftToActivateListener mLiftToActivateListener;
    private final View.OnKeyListener mOnKeyListener = new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(this);
    protected PasswordTextView mPasswordEntry;

    /* access modifiers changed from: public */
    private /* synthetic */ boolean lambda$new$0(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            return ((KeyguardPinBasedInputView) this.mView).onKeyDown(i, keyEvent);
        }
        return false;
    }

    private /* synthetic */ boolean lambda$new$1(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() != 0) {
            return false;
        }
        ((KeyguardPinBasedInputView) this.mView).doHapticKeyClick();
        return false;
    }

    protected KeyguardPinBasedInputViewController(T t, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector) {
        super(t, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController);
        this.mLiftToActivateListener = liftToActivateListener;
        this.mFalsingCollector = falsingCollector;
        T t2 = this.mView;
        this.mPasswordEntry = (PasswordTextView) ((KeyguardPinBasedInputView) t2).findViewById(((KeyguardPinBasedInputView) t2).getPasswordTextViewId());
    }

    @Override // com.android.systemui.util.ViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
        for (NumPadKey numPadKey : ((KeyguardPinBasedInputView) this.mView).getButtons()) {
            numPadKey.setOnTouchListener(new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3(this));
        }
        this.mPasswordEntry.setOnKeyListener(this.mOnKeyListener);
        this.mPasswordEntry.setUserActivityListener(new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda5(this));
        View findViewById = ((KeyguardPinBasedInputView) this.mView).findViewById(R$id.delete_button);
        findViewById.setOnTouchListener(this.mActionButtonTouchListener);
        findViewById.setOnClickListener(new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0(this));
        findViewById.setOnLongClickListener(new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda2(this));
        View findViewById2 = ((KeyguardPinBasedInputView) this.mView).findViewById(R$id.key_enter);
        if (findViewById2 != null) {
            findViewById2.setOnTouchListener(this.mActionButtonTouchListener);
            findViewById2.setOnClickListener(new View.OnClickListener() {
                /* class com.android.keyguard.KeyguardPinBasedInputViewController.AnonymousClass1 */

                public void onClick(View view) {
                    if (KeyguardPinBasedInputViewController.this.mPasswordEntry.isEnabled()) {
                        KeyguardPinBasedInputViewController.this.verifyPasswordAndUnlock();
                    }
                }
            });
            findViewById2.setOnHoverListener(this.mLiftToActivateListener);
        }
    }

    private /* synthetic */ boolean lambda$onViewAttached$2(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() != 0) {
            return false;
        }
        this.mFalsingCollector.avoidGesture();
        return false;
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onViewAttached$3(View view) {
        if (this.mPasswordEntry.isEnabled()) {
            this.mPasswordEntry.deleteLastChar();
        }
    }

    private /* synthetic */ boolean lambda$onViewAttached$4(View view) {
        if (this.mPasswordEntry.isEnabled()) {
            ((KeyguardPinBasedInputView) this.mView).resetPasswordText(true, true);
        }
        ((KeyguardPinBasedInputView) this.mView).doHapticKeyClick();
        return true;
    }

    @Override // com.android.systemui.util.ViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewDetached() {
        super.onViewDetached();
        for (NumPadKey numPadKey : ((KeyguardPinBasedInputView) this.mView).getButtons()) {
            numPadKey.setOnTouchListener(null);
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        this.mPasswordEntry.requestFocus();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        ((KeyguardPinBasedInputView) this.mView).setPasswordEntryEnabled(true);
    }
}

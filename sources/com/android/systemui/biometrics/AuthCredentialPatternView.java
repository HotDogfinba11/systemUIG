package com.android.systemui.biometrics;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.systemui.R$id;
import java.util.List;

public class AuthCredentialPatternView extends AuthCredentialView {
    private LockPatternView mLockPatternView;

    public class UnlockPatternListener implements LockPatternView.OnPatternListener {
        public void onPatternCellAdded(List<LockPatternView.Cell> list) {
        }

        public void onPatternCleared() {
        }

        public void onPatternStart() {
        }

        private UnlockPatternListener() {
            AuthCredentialPatternView.this = r1;
        }

        public void onPatternDetected(List<LockPatternView.Cell> list) {
            AsyncTask<?, ?, ?> asyncTask = AuthCredentialPatternView.this.mPendingLockCheck;
            if (asyncTask != null) {
                asyncTask.cancel(false);
            }
            AuthCredentialPatternView.this.mLockPatternView.setEnabled(false);
            if (list.size() < 4) {
                onPatternVerified(VerifyCredentialResponse.ERROR, 0);
                return;
            }
            LockscreenCredential createPattern = LockscreenCredential.createPattern(list);
            try {
                AuthCredentialPatternView authCredentialPatternView = AuthCredentialPatternView.this;
                authCredentialPatternView.mPendingLockCheck = LockPatternChecker.verifyCredential(authCredentialPatternView.mLockPatternUtils, createPattern, authCredentialPatternView.mEffectiveUserId, 1, new AuthCredentialPatternView$UnlockPatternListener$$ExternalSyntheticLambda0(this));
                if (createPattern != null) {
                    createPattern.close();
                    return;
                }
                return;
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
            throw th;
        }

        /* access modifiers changed from: public */
        private void onPatternVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
            AuthCredentialPatternView.this.onCredentialVerified(verifyCredentialResponse, i);
            if (i > 0) {
                AuthCredentialPatternView.this.mLockPatternView.setEnabled(false);
            } else {
                AuthCredentialPatternView.this.mLockPatternView.setEnabled(true);
            }
        }
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView
    public void onErrorTimeoutFinish() {
        super.onErrorTimeoutFinish();
        this.mLockPatternView.setEnabled(true);
    }

    public AuthCredentialPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LockPatternView findViewById = findViewById(R$id.lockPattern);
        this.mLockPatternView = findViewById;
        findViewById.setOnPatternListener(new UnlockPatternListener());
        this.mLockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(this.mUserId));
        this.mLockPatternView.setTactileFeedbackEnabled(this.mLockPatternUtils.isTactileFeedbackEnabled());
    }
}

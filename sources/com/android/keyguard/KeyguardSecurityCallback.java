package com.android.keyguard;

public interface KeyguardSecurityCallback {
    void dismiss(boolean z, int i);

    void dismiss(boolean z, int i, boolean z2);

    default void onCancelClicked() {
    }

    void onUserInput();

    void reportUnlockAttempt(int i, boolean z, int i2);

    void reset();

    void userActivity();
}

package com.android.keyguard;

import com.android.keyguard.PasswordTextView;

public final /* synthetic */ class KeyguardPinBasedInputViewController$$ExternalSyntheticLambda5 implements PasswordTextView.UserActivityListener {
    public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardPinBasedInputViewController$$ExternalSyntheticLambda5(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController) {
        this.f$0 = keyguardPinBasedInputViewController;
    }

    @Override // com.android.keyguard.PasswordTextView.UserActivityListener
    public final void onUserActivity() {
        this.f$0.onUserInput();
    }
}

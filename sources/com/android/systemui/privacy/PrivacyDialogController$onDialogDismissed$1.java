package com.android.systemui.privacy;

import com.android.systemui.privacy.PrivacyDialog;

/* compiled from: PrivacyDialogController.kt */
public final class PrivacyDialogController$onDialogDismissed$1 implements PrivacyDialog.OnDialogDismissed {
    final /* synthetic */ PrivacyDialogController this$0;

    PrivacyDialogController$onDialogDismissed$1(PrivacyDialogController privacyDialogController) {
        this.this$0 = privacyDialogController;
    }

    @Override // com.android.systemui.privacy.PrivacyDialog.OnDialogDismissed
    public void onDialogDismissed() {
        this.this$0.privacyLogger.logPrivacyDialogDismissed();
        this.this$0.uiEventLogger.log(PrivacyDialogEvent.PRIVACY_DIALOG_DISMISSED);
        this.this$0.dialog = null;
    }
}

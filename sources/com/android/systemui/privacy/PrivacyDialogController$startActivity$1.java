package com.android.systemui.privacy;

import android.app.ActivityManager;
import android.app.Dialog;
import com.android.systemui.plugins.ActivityStarter;

/* access modifiers changed from: package-private */
/* compiled from: PrivacyDialogController.kt */
public final class PrivacyDialogController$startActivity$1 implements ActivityStarter.Callback {
    final /* synthetic */ PrivacyDialogController this$0;

    PrivacyDialogController$startActivity$1(PrivacyDialogController privacyDialogController) {
        this.this$0 = privacyDialogController;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.Callback
    public final void onActivityStarted(int i) {
        if (ActivityManager.isStartResultSuccessful(i)) {
            this.this$0.dismissDialog();
            return;
        }
        Dialog dialog = this.this$0.dialog;
        if (dialog != null) {
            dialog.show();
        }
    }
}

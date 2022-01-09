package com.google.android.systemui.face;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.util.Log;
import com.android.systemui.R$string;
import com.android.systemui.statusbar.phone.SystemUIDialog;

class FaceNotificationDialogFactory {
    public static /* synthetic */ void $r8$lambda$321klq9YWrru4DkOFAEv8LwAp8s(DialogInterface dialogInterface, int i) {
        lambda$createReenrollFailureDialog$2(dialogInterface, i);
    }

    public static /* synthetic */ void lambda$createReenrollDialog$1(DialogInterface dialogInterface, int i) {
    }

    private static /* synthetic */ void lambda$createReenrollFailureDialog$2(DialogInterface dialogInterface, int i) {
    }

    static Dialog createReenrollDialog(Context context) {
        SystemUIDialog systemUIDialog = new SystemUIDialog(context);
        systemUIDialog.setTitle(context.getString(R$string.face_reenroll_dialog_title));
        systemUIDialog.setMessage(context.getString(R$string.face_reenroll_dialog_content));
        systemUIDialog.setPositiveButton(R$string.face_reenroll_dialog_confirm, new FaceNotificationDialogFactory$$ExternalSyntheticLambda0(context));
        systemUIDialog.setNegativeButton(R$string.face_reenroll_dialog_cancel, FaceNotificationDialogFactory$$ExternalSyntheticLambda2.INSTANCE);
        return systemUIDialog;
    }

    public static Dialog createReenrollFailureDialog(Context context) {
        SystemUIDialog systemUIDialog = new SystemUIDialog(context);
        systemUIDialog.setMessage(context.getText(R$string.face_reenroll_failure_dialog_content));
        systemUIDialog.setPositiveButton(R$string.ok, FaceNotificationDialogFactory$$ExternalSyntheticLambda1.INSTANCE);
        return systemUIDialog;
    }

    public static void onReenrollDialogConfirm(final Context context) {
        FaceManager faceManager = (FaceManager) context.getSystemService(FaceManager.class);
        if (faceManager == null) {
            Log.e("FaceNotificationDialogF", "Not launching enrollment. Face manager was null!");
            createReenrollFailureDialog(context).show();
            return;
        }
        faceManager.remove(new Face("", 0, 0), context.getUserId(), new FaceManager.RemovalCallback() {
            /* class com.google.android.systemui.face.FaceNotificationDialogFactory.AnonymousClass1 */
            boolean mDidShowFailureDialog;

            public void onRemovalError(Face face, int i, CharSequence charSequence) {
                Log.e("FaceNotificationDialogF", "Not launching enrollment. Failed to remove existing face(s).");
                if (!this.mDidShowFailureDialog) {
                    this.mDidShowFailureDialog = true;
                    FaceNotificationDialogFactory.createReenrollFailureDialog(context).show();
                }
            }

            public void onRemovalSucceeded(Face face, int i) {
                if (!this.mDidShowFailureDialog && i == 0) {
                    Intent intent = new Intent("android.settings.BIOMETRIC_ENROLL");
                    intent.setPackage("com.android.settings");
                    intent.setFlags(268435456);
                    context.startActivity(intent);
                }
            }
        });
    }
}

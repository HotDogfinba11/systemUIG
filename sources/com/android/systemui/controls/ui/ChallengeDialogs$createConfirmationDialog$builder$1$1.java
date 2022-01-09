package com.android.systemui.controls.ui;

import android.content.DialogInterface;
import android.service.controls.actions.ControlAction;

/* access modifiers changed from: package-private */
/* compiled from: ChallengeDialogs.kt */
public final class ChallengeDialogs$createConfirmationDialog$builder$1$1 implements DialogInterface.OnClickListener {
    final /* synthetic */ ControlViewHolder $cvh;
    final /* synthetic */ ControlAction $lastAction;

    ChallengeDialogs$createConfirmationDialog$builder$1$1(ControlViewHolder controlViewHolder, ControlAction controlAction) {
        this.$cvh = controlViewHolder;
        this.$lastAction = controlAction;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.$cvh.action(ChallengeDialogs.access$addChallengeValue(ChallengeDialogs.INSTANCE, this.$lastAction, "true"));
        dialogInterface.dismiss();
    }
}

package com.android.systemui.controls.ui;

import android.content.DialogInterface;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* access modifiers changed from: package-private */
/* compiled from: ChallengeDialogs.kt */
public final class ChallengeDialogs$createPinDialog$2$2 implements DialogInterface.OnClickListener {
    final /* synthetic */ Function0<Unit> $onCancel;

    ChallengeDialogs$createPinDialog$2$2(Function0<Unit> function0) {
        this.$onCancel = function0;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.$onCancel.invoke();
        dialogInterface.cancel();
    }
}

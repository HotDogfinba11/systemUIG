package com.google.android.systemui.gamedashboard;

import android.content.DialogInterface;

public final /* synthetic */ class GameMenuActivity$$ExternalSyntheticLambda1 implements DialogInterface.OnClickListener {
    public final /* synthetic */ GameMenuActivity f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ GameMenuActivity$$ExternalSyntheticLambda1(GameMenuActivity gameMenuActivity, int i, String str) {
        this.f$0 = gameMenuActivity;
        this.f$1 = i;
        this.f$2 = str;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.f$0.lambda$showRestartDialog$17(this.f$1, this.f$2, dialogInterface, i);
    }
}

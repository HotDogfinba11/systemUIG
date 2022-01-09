package com.google.android.systemui.gamedashboard;

import android.app.PendingIntent;
import android.view.View;

public final /* synthetic */ class PlayGamesWidget$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ PlayGamesWidget f$0;
    public final /* synthetic */ PendingIntent f$1;

    public /* synthetic */ PlayGamesWidget$$ExternalSyntheticLambda0(PlayGamesWidget playGamesWidget, PendingIntent pendingIntent) {
        this.f$0 = playGamesWidget;
        this.f$1 = pendingIntent;
    }

    public final void onClick(View view) {
        this.f$0.lambda$updateFromData$1(this.f$1, view);
    }
}

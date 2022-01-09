package com.android.systemui;

import android.app.PendingIntent;
import android.view.View;
import dagger.Lazy;
import java.util.function.Consumer;

public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ PendingIntent f$0;
    public final /* synthetic */ Runnable f$1;
    public final /* synthetic */ View f$2;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda4(PendingIntent pendingIntent, Runnable runnable, View view) {
        this.f$0 = pendingIntent;
        this.f$1 = runnable;
        this.f$2 = view;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityStarterDelegate.m38$r8$lambda$6tLSiD24UE4d3HlFYy4ZUs6FY(this.f$0, this.f$1, this.f$2, (Lazy) obj);
    }
}

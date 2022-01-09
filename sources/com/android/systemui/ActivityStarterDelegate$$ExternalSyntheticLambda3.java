package com.android.systemui;

import android.app.PendingIntent;
import dagger.Lazy;
import java.util.function.Consumer;

public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ PendingIntent f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda3(PendingIntent pendingIntent, Runnable runnable) {
        this.f$0 = pendingIntent;
        this.f$1 = runnable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityStarterDelegate.$r8$lambda$FBf0zedluqDHJZZwHcw2AxX0srs(this.f$0, this.f$1, (Lazy) obj);
    }
}

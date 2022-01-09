package com.android.systemui;

import android.app.PendingIntent;
import dagger.Lazy;
import java.util.function.Consumer;

public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ PendingIntent f$0;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda1(PendingIntent pendingIntent) {
        this.f$0 = pendingIntent;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityStarterDelegate.$r8$lambda$dmHqE3zn4WygtrRMHKyegXFWE54(this.f$0, (Lazy) obj);
    }
}

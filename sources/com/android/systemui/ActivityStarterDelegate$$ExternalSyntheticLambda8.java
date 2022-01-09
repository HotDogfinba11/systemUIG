package com.android.systemui;

import android.content.Intent;
import dagger.Lazy;
import java.util.function.Consumer;

public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ Intent f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda8(Intent intent, boolean z) {
        this.f$0 = intent;
        this.f$1 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityStarterDelegate.lambda$startActivity$5(this.f$0, this.f$1, (Lazy) obj);
    }
}

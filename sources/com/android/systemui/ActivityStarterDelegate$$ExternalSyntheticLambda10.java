package com.android.systemui;

import android.content.Intent;
import com.android.systemui.plugins.ActivityStarter;
import dagger.Lazy;
import java.util.function.Consumer;

public final /* synthetic */ class ActivityStarterDelegate$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ Intent f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ ActivityStarter.Callback f$2;

    public /* synthetic */ ActivityStarterDelegate$$ExternalSyntheticLambda10(Intent intent, boolean z, ActivityStarter.Callback callback) {
        this.f$0 = intent;
        this.f$1 = z;
        this.f$2 = callback;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityStarterDelegate.$r8$lambda$KLVw4PbQur0YwmyYTDp4Bw0foyg(this.f$0, this.f$1, this.f$2, (Lazy) obj);
    }
}

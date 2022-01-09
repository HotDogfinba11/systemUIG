package com.google.android.systemui.input;

import android.os.IBinder;

public final /* synthetic */ class TouchContextService$$ExternalSyntheticLambda0 implements IBinder.DeathRecipient {
    public final /* synthetic */ TouchContextService f$0;
    public final /* synthetic */ IBinder f$1;

    public /* synthetic */ TouchContextService$$ExternalSyntheticLambda0(TouchContextService touchContextService, IBinder iBinder) {
        this.f$0 = touchContextService;
        this.f$1 = iBinder;
    }

    public final void binderDied() {
        this.f$0.lambda$getTouchContextService$1(this.f$1);
    }
}

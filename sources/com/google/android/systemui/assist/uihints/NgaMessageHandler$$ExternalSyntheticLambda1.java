package com.google.android.systemui.assist.uihints;

import android.os.Bundle;

public final /* synthetic */ class NgaMessageHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ NgaMessageHandler f$0;
    public final /* synthetic */ Bundle f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ NgaMessageHandler$$ExternalSyntheticLambda1(NgaMessageHandler ngaMessageHandler, Bundle bundle, Runnable runnable) {
        this.f$0 = ngaMessageHandler;
        this.f$1 = bundle;
        this.f$2 = runnable;
    }

    public final void run() {
        this.f$0.lambda$processBundle$1(this.f$1, this.f$2);
    }
}

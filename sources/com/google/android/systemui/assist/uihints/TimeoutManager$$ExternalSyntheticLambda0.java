package com.google.android.systemui.assist.uihints;

import dagger.Lazy;

public final /* synthetic */ class TimeoutManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TimeoutManager f$0;
    public final /* synthetic */ Lazy f$1;

    public /* synthetic */ TimeoutManager$$ExternalSyntheticLambda0(TimeoutManager timeoutManager, Lazy lazy) {
        this.f$0 = timeoutManager;
        this.f$1 = lazy;
    }

    public final void run() {
        this.f$0.lambda$new$0(this.f$1);
    }
}

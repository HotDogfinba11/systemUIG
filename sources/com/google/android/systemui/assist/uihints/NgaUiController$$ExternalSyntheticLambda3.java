package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.TimeoutManager;

public final /* synthetic */ class NgaUiController$$ExternalSyntheticLambda3 implements TimeoutManager.TimeoutCallback {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ NgaUiController$$ExternalSyntheticLambda3(Runnable runnable) {
        this.f$0 = runnable;
    }

    @Override // com.google.android.systemui.assist.uihints.TimeoutManager.TimeoutCallback
    public final void onTimeout() {
        this.f$0.run();
    }
}

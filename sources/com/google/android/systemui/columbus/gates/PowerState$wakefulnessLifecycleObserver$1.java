package com.google.android.systemui.columbus.gates;

import com.android.systemui.keyguard.WakefulnessLifecycle;

/* compiled from: PowerState.kt */
public final class PowerState$wakefulnessLifecycleObserver$1 implements WakefulnessLifecycle.Observer {
    final /* synthetic */ PowerState this$0;

    PowerState$wakefulnessLifecycleObserver$1(PowerState powerState) {
        this.this$0 = powerState;
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onFinishedGoingToSleep() {
        this.this$0.updateBlocking();
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedWakingUp() {
        this.this$0.updateBlocking();
    }
}

package com.google.android.systemui.columbus.sensors;

import com.android.systemui.keyguard.WakefulnessLifecycle;

/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor$wakefulnessLifecycleObserver$1 implements WakefulnessLifecycle.Observer {
    final /* synthetic */ CHREGestureSensor this$0;

    CHREGestureSensor$wakefulnessLifecycleObserver$1(CHREGestureSensor cHREGestureSensor) {
        this.this$0 = cHREGestureSensor;
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedWakingUp() {
        this.this$0.handleWakefullnessChanged(false);
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onFinishedWakingUp() {
        this.this$0.handleWakefullnessChanged(true);
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedGoingToSleep() {
        this.this$0.handleWakefullnessChanged(false);
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onFinishedGoingToSleep() {
        this.this$0.handleWakefullnessChanged(false);
    }
}

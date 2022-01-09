package com.google.android.systemui.columbus.actions;

import com.android.systemui.keyguard.WakefulnessLifecycle;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction$wakefulnessLifecycleObserver$1 implements WakefulnessLifecycle.Observer {
    final /* synthetic */ UserSelectedAction this$0;

    UserSelectedAction$wakefulnessLifecycleObserver$1(UserSelectedAction userSelectedAction) {
        this.this$0 = userSelectedAction;
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onFinishedGoingToSleep() {
        this.this$0.updateAvailable();
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedWakingUp() {
        this.this$0.updateAvailable();
    }
}

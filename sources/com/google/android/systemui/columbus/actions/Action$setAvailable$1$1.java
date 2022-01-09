package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.actions.Action;

/* access modifiers changed from: package-private */
/* compiled from: Action.kt */
public final class Action$setAvailable$1$1 implements Runnable {
    final /* synthetic */ Action.Listener $it;
    final /* synthetic */ Action this$0;

    Action$setAvailable$1$1(Action.Listener listener, Action action) {
        this.$it = listener;
        this.this$0 = action;
    }

    public final void run() {
        this.$it.onActionAvailabilityChanged(this.this$0);
    }
}

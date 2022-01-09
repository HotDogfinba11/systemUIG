package com.google.android.systemui.columbus.actions;

/* access modifiers changed from: package-private */
/* compiled from: Action.kt */
public final class Action$setAvailable$2 implements Runnable {
    final /* synthetic */ Action this$0;

    Action$setAvailable$2(Action action) {
        this.this$0 = action;
    }

    public final void run() {
        Action.updateFeedbackEffects$default(this.this$0, 0, null, 2, null);
    }
}

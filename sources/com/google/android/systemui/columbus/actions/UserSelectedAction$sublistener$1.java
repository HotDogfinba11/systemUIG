package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.actions.Action;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction$sublistener$1 implements Action.Listener {
    final /* synthetic */ UserSelectedAction this$0;

    UserSelectedAction$sublistener$1(UserSelectedAction userSelectedAction) {
        this.this$0 = userSelectedAction;
    }

    @Override // com.google.android.systemui.columbus.actions.Action.Listener
    public void onActionAvailabilityChanged(Action action) {
        Intrinsics.checkNotNullParameter(action, "action");
        if (Intrinsics.areEqual(this.this$0.currentAction, action)) {
            this.this$0.updateAvailable();
        }
    }
}

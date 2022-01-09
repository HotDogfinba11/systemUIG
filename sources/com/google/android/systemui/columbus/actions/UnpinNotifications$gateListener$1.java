package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.gates.Gate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UnpinNotifications.kt */
public final class UnpinNotifications$gateListener$1 implements Gate.Listener {
    final /* synthetic */ UnpinNotifications this$0;

    UnpinNotifications$gateListener$1(UnpinNotifications unpinNotifications) {
        this.this$0 = unpinNotifications;
    }

    @Override // com.google.android.systemui.columbus.gates.Gate.Listener
    public void onGateChanged(Gate gate) {
        Intrinsics.checkNotNullParameter(gate, "gate");
        if (this.this$0.silenceAlertsDisabled.isBlocking()) {
            this.this$0.onSilenceAlertsDisabled();
        } else {
            this.this$0.onSilenceAlertsEnabled();
        }
    }
}

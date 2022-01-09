package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.gates.Gate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeskClockAction.kt */
public final class DeskClockAction$gateListener$1 implements Gate.Listener {
    final /* synthetic */ DeskClockAction this$0;

    DeskClockAction$gateListener$1(DeskClockAction deskClockAction) {
        this.this$0 = deskClockAction;
    }

    @Override // com.google.android.systemui.columbus.gates.Gate.Listener
    public void onGateChanged(Gate gate) {
        Intrinsics.checkNotNullParameter(gate, "gate");
        this.this$0.updateBroadcastReceiver();
    }
}

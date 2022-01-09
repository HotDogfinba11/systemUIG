package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.gates.Gate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SilenceCall.kt */
public final class SilenceCall$gateListener$1 implements Gate.Listener {
    final /* synthetic */ SilenceCall this$0;

    SilenceCall$gateListener$1(SilenceCall silenceCall) {
        this.this$0 = silenceCall;
    }

    @Override // com.google.android.systemui.columbus.gates.Gate.Listener
    public void onGateChanged(Gate gate) {
        Intrinsics.checkNotNullParameter(gate, "gate");
        this.this$0.updatePhoneStateListener();
    }
}

package com.google.android.systemui.columbus.sensors;

import com.google.android.systemui.columbus.gates.Gate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GestureController.kt */
public final class GestureController$softGateListener$1 implements Gate.Listener {
    @Override // com.google.android.systemui.columbus.gates.Gate.Listener
    public void onGateChanged(Gate gate) {
        Intrinsics.checkNotNullParameter(gate, "gate");
    }

    GestureController$softGateListener$1() {
    }
}

package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.gates.Gate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CameraVisibility.kt */
public final class CameraVisibility$gateListener$1 implements Gate.Listener {
    final /* synthetic */ CameraVisibility this$0;

    CameraVisibility$gateListener$1(CameraVisibility cameraVisibility) {
        this.this$0 = cameraVisibility;
    }

    @Override // com.google.android.systemui.columbus.gates.Gate.Listener
    public void onGateChanged(Gate gate) {
        Intrinsics.checkNotNullParameter(gate, "gate");
        this.this$0.updateHandler.post(new CameraVisibility$gateListener$1$onGateChanged$1(this.this$0));
    }
}

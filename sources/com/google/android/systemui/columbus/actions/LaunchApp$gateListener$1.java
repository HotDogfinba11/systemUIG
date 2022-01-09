package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.gates.Gate;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$gateListener$1 implements Gate.Listener {
    final /* synthetic */ LaunchApp this$0;

    LaunchApp$gateListener$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    @Override // com.google.android.systemui.columbus.gates.Gate.Listener
    public void onGateChanged(Gate gate) {
        Intrinsics.checkNotNullParameter(gate, "gate");
        if (!gate.isBlocking()) {
            this.this$0.updateAvailableAppsAndShortcutsAsync();
        }
    }
}

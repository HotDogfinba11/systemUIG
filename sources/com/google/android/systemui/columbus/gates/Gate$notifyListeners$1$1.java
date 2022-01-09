package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.gates.Gate;

/* access modifiers changed from: package-private */
/* compiled from: Gate.kt */
public final class Gate$notifyListeners$1$1 implements Runnable {
    final /* synthetic */ Gate.Listener $it;
    final /* synthetic */ Gate this$0;

    Gate$notifyListeners$1$1(Gate.Listener listener, Gate gate) {
        this.$it = listener;
        this.this$0 = gate;
    }

    public final void run() {
        this.$it.onGateChanged(this.this$0);
    }
}

package com.google.android.systemui.columbus.gates;

import android.service.vr.IVrStateCallbacks;

/* compiled from: VrMode.kt */
public final class VrMode$vrStateCallbacks$1 extends IVrStateCallbacks.Stub {
    final /* synthetic */ VrMode this$0;

    VrMode$vrStateCallbacks$1(VrMode vrMode) {
        this.this$0 = vrMode;
    }

    public void onVrStateChanged(boolean z) {
        this.this$0.inVrMode = z;
        this.this$0.updateBlocking();
    }
}

package com.google.android.systemui.elmyra.sensors;

import com.google.android.systemui.elmyra.SnapshotController;
import com.google.android.systemui.elmyra.proto.nano.SnapshotProtos$SnapshotHeader;

public final /* synthetic */ class CHREGestureSensor$$ExternalSyntheticLambda0 implements SnapshotController.Listener {
    public final /* synthetic */ CHREGestureSensor f$0;

    public /* synthetic */ CHREGestureSensor$$ExternalSyntheticLambda0(CHREGestureSensor cHREGestureSensor) {
        this.f$0 = cHREGestureSensor;
    }

    @Override // com.google.android.systemui.elmyra.SnapshotController.Listener
    public final void onSnapshotRequested(SnapshotProtos$SnapshotHeader snapshotProtos$SnapshotHeader) {
        this.f$0.lambda$new$0(snapshotProtos$SnapshotHeader);
    }
}

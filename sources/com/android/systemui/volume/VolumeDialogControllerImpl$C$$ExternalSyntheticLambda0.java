package com.android.systemui.volume;

import com.android.systemui.volume.VolumeDialogControllerImpl;
import java.util.Map;

public final /* synthetic */ class VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Map.Entry f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Boolean f$2;

    public /* synthetic */ VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0(Map.Entry entry, boolean z, Boolean bool) {
        this.f$0 = entry;
        this.f$1 = z;
        this.f$2 = bool;
    }

    public final void run() {
        VolumeDialogControllerImpl.C.lambda$onCaptionComponentStateChanged$0(this.f$0, this.f$1, this.f$2);
    }
}

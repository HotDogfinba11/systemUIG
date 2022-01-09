package com.android.systemui.statusbar.policy;

import com.android.systemui.controls.controller.ControlsController;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: DeviceControlsControllerImpl.kt */
public final class DeviceControlsControllerImpl$checkMigrationToQs$1 implements Consumer<ControlsController> {
    final /* synthetic */ DeviceControlsControllerImpl this$0;

    DeviceControlsControllerImpl$checkMigrationToQs$1(DeviceControlsControllerImpl deviceControlsControllerImpl) {
        this.this$0 = deviceControlsControllerImpl;
    }

    public final void accept(ControlsController controlsController) {
        Intrinsics.checkNotNullParameter(controlsController, "it");
        if (!controlsController.getFavorites().isEmpty()) {
            this.this$0.setPosition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(3);
            this.this$0.fireControlsUpdate();
        }
    }
}

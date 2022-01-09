package com.android.systemui.controls.controller;

import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsController.kt */
public final class ControlsControllerKt {
    public static /* synthetic */ ControlsController.LoadData createLoadDataObject$default(List list, List list2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return createLoadDataObject(list, list2, z);
    }

    public static final ControlsController.LoadData createLoadDataObject(List<ControlStatus> list, List<String> list2, boolean z) {
        Intrinsics.checkNotNullParameter(list, "allControls");
        Intrinsics.checkNotNullParameter(list2, "favorites");
        return new ControlsControllerKt$createLoadDataObject$1(list, list2, z);
    }
}

package com.android.systemui.controls.controller;

import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.List;

/* compiled from: ControlsController.kt */
public final class ControlsControllerKt$createLoadDataObject$1 implements ControlsController.LoadData {
    final /* synthetic */ List<ControlStatus> $allControls;
    final /* synthetic */ boolean $error;
    final /* synthetic */ List<String> $favorites;
    private final List<ControlStatus> allControls;
    private final boolean errorOnLoad;
    private final List<String> favoritesIds;

    ControlsControllerKt$createLoadDataObject$1(List<ControlStatus> list, List<String> list2, boolean z) {
        this.$allControls = list;
        this.$favorites = list2;
        this.$error = z;
        this.allControls = list;
        this.favoritesIds = list2;
        this.errorOnLoad = z;
    }

    @Override // com.android.systemui.controls.controller.ControlsController.LoadData
    public List<ControlStatus> getAllControls() {
        return this.allControls;
    }

    @Override // com.android.systemui.controls.controller.ControlsController.LoadData
    public List<String> getFavoritesIds() {
        return this.favoritesIds;
    }

    @Override // com.android.systemui.controls.controller.ControlsController.LoadData
    public boolean getErrorOnLoad() {
        return this.errorOnLoad;
    }
}

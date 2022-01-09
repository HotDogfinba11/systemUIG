package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.view.View;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.StructureInfo;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsFavoritingActivity.kt */
final class ControlsFavoritingActivity$bindButtons$2$1 implements View.OnClickListener {
    final /* synthetic */ ControlsFavoritingActivity this$0;

    ControlsFavoritingActivity$bindButtons$2$1(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    public final void onClick(View view) {
        if (ControlsFavoritingActivity.access$getComponent$p(this.this$0) != null) {
            List<StructureContainer> access$getListOfStructures$p = ControlsFavoritingActivity.access$getListOfStructures$p(this.this$0);
            ControlsFavoritingActivity controlsFavoritingActivity = this.this$0;
            for (StructureContainer structureContainer : access$getListOfStructures$p) {
                List<ControlInfo> favorites = structureContainer.getModel().getFavorites();
                ControlsControllerImpl access$getController$p = ControlsFavoritingActivity.access$getController$p(controlsFavoritingActivity);
                ComponentName access$getComponent$p = ControlsFavoritingActivity.access$getComponent$p(controlsFavoritingActivity);
                Intrinsics.checkNotNull(access$getComponent$p);
                access$getController$p.replaceFavoritesForStructure(new StructureInfo(access$getComponent$p, structureContainer.getStructureName(), favorites));
            }
            ControlsFavoritingActivity.access$animateExitAndFinish(this.this$0);
            ControlsFavoritingActivity.access$openControlsOrigin(this.this$0);
        }
    }
}

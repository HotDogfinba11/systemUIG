package com.android.systemui.controls.ui;

import android.view.View;
import android.widget.AdapterView;
import com.android.systemui.globalactions.GlobalActionsPopupMenu;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$createDropDown$2$onClick$1$1 implements AdapterView.OnItemClickListener {
    final /* synthetic */ GlobalActionsPopupMenu $this_apply;
    final /* synthetic */ ControlsUiControllerImpl this$0;

    ControlsUiControllerImpl$createDropDown$2$onClick$1$1(ControlsUiControllerImpl controlsUiControllerImpl, GlobalActionsPopupMenu globalActionsPopupMenu) {
        this.this$0 = controlsUiControllerImpl;
        this.$this_apply = globalActionsPopupMenu;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intrinsics.checkNotNullParameter(adapterView, "parent");
        Intrinsics.checkNotNullParameter(view, "view");
        Object itemAtPosition = adapterView.getItemAtPosition(i);
        Objects.requireNonNull(itemAtPosition, "null cannot be cast to non-null type com.android.systemui.controls.ui.SelectionItem");
        this.this$0.switchAppOrStructure((SelectionItem) itemAtPosition);
        this.$this_apply.dismiss();
    }
}

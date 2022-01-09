package com.android.systemui.globalactions;

import android.view.View;
import android.widget.AdapterView;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

public final /* synthetic */ class GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6 implements AdapterView.OnItemLongClickListener {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite) {
        this.f$0 = actionsDialogLite;
    }

    @Override // android.widget.AdapterView.OnItemLongClickListener
    public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
        return this.f$0.lambda$createPowerOverflowPopup$1(adapterView, view, i, j);
    }
}

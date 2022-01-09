package com.android.systemui.qs;

import com.android.systemui.qs.QSHost;

public final /* synthetic */ class QSPanelControllerBase$$ExternalSyntheticLambda0 implements QSHost.Callback {
    public final /* synthetic */ QSPanelControllerBase f$0;

    public /* synthetic */ QSPanelControllerBase$$ExternalSyntheticLambda0(QSPanelControllerBase qSPanelControllerBase) {
        this.f$0 = qSPanelControllerBase;
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public final void onTilesChanged() {
        this.f$0.setTiles();
    }
}

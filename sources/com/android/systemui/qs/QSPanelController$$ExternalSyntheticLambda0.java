package com.android.systemui.qs;

import android.view.View;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;

public final /* synthetic */ class QSPanelController$$ExternalSyntheticLambda0 implements BrightnessMirrorController.BrightnessMirrorListener {
    public final /* synthetic */ QSPanelController f$0;

    public /* synthetic */ QSPanelController$$ExternalSyntheticLambda0(QSPanelController qSPanelController) {
        this.f$0 = qSPanelController;
    }

    @Override // com.android.systemui.statusbar.policy.BrightnessMirrorController.BrightnessMirrorListener
    public final void onBrightnessMirrorReinflated(View view) {
        this.f$0.lambda$new$0((QSPanelController) view);
    }
}

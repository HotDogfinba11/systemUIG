package com.android.systemui.qs;

import com.android.systemui.qs.carrier.QSCarrierGroupController;

public final /* synthetic */ class QuickStatusBarHeaderController$$ExternalSyntheticLambda1 implements QSCarrierGroupController.OnSingleCarrierChangedListener {
    public final /* synthetic */ QuickStatusBarHeader f$0;

    public /* synthetic */ QuickStatusBarHeaderController$$ExternalSyntheticLambda1(QuickStatusBarHeader quickStatusBarHeader) {
        this.f$0 = quickStatusBarHeader;
    }

    @Override // com.android.systemui.qs.carrier.QSCarrierGroupController.OnSingleCarrierChangedListener
    public final void onSingleCarrierChanged(boolean z) {
        this.f$0.setIsSingleCarrier(z);
    }
}

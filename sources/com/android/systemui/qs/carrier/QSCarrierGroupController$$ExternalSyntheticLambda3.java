package com.android.systemui.qs.carrier;

import com.android.keyguard.CarrierTextManager;
import java.util.function.Consumer;

public final /* synthetic */ class QSCarrierGroupController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ QSCarrierGroupController f$0;

    public /* synthetic */ QSCarrierGroupController$$ExternalSyntheticLambda3(QSCarrierGroupController qSCarrierGroupController) {
        this.f$0 = qSCarrierGroupController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.handleUpdateCarrierInfo((CarrierTextManager.CarrierTextCallbackInfo) obj);
    }
}

package com.android.systemui.qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import java.util.function.Function;

public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ InternetDialogController f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda1(InternetDialogController internetDialogController) {
        this.f$0 = internetDialogController;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.lambda$getUniqueSubscriptionDisplayNames$1((SubscriptionInfo) obj);
    }
}

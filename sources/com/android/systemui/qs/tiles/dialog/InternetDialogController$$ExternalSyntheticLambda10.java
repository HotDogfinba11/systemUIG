package com.android.systemui.qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import java.util.function.Predicate;

public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda10 implements Predicate {
    public static final /* synthetic */ InternetDialogController$$ExternalSyntheticLambda10 INSTANCE = new InternetDialogController$$ExternalSyntheticLambda10();

    private /* synthetic */ InternetDialogController$$ExternalSyntheticLambda10() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return InternetDialogController.lambda$getUniqueSubscriptionDisplayNames$0((SubscriptionInfo) obj);
    }
}

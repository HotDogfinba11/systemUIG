package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import java.util.Set;
import java.util.function.Supplier;

public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda13 implements Supplier {
    public final /* synthetic */ Supplier f$0;
    public final /* synthetic */ Set f$1;
    public final /* synthetic */ Context f$2;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda13(Supplier supplier, Set set, Context context) {
        this.f$0 = supplier;
        this.f$1 = set;
        this.f$2 = context;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return InternetDialogController.lambda$getUniqueSubscriptionDisplayNames$6(this.f$0, this.f$1, this.f$2);
    }
}

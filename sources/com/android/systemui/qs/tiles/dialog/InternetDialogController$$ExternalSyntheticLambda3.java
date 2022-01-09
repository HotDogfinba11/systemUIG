package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import java.util.Set;
import java.util.function.Function;

public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ Set f$0;
    public final /* synthetic */ Context f$1;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda3(Set set, Context context) {
        this.f$0 = set;
        this.f$1 = context;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return InternetDialogController.lambda$getUniqueSubscriptionDisplayNames$5(this.f$0, this.f$1, (InternetDialogController.AnonymousClass1DisplayInfo) obj);
    }
}

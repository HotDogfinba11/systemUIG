package com.android.systemui.qs.tiles.dialog;

import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import java.util.Set;
import java.util.function.Predicate;

public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda9 implements Predicate {
    public final /* synthetic */ Set f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda9(Set set) {
        this.f$0 = set;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return InternetDialogController.lambda$getUniqueSubscriptionDisplayNames$3(this.f$0, (InternetDialogController.AnonymousClass1DisplayInfo) obj);
    }
}

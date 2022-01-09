package com.android.systemui.qs.tiles.dialog;

import com.android.wifitrackerlib.WifiEntry;
import java.util.function.Predicate;

public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda11 implements Predicate {
    public static final /* synthetic */ InternetDialogController$$ExternalSyntheticLambda11 INSTANCE = new InternetDialogController$$ExternalSyntheticLambda11();

    private /* synthetic */ InternetDialogController$$ExternalSyntheticLambda11() {
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return InternetDialogController.lambda$onAccessPointsChanged$13((WifiEntry) obj);
    }
}

package com.google.android.systemui.smartspace;

import android.content.ContentResolver;
import android.net.Uri;
import java.util.function.Function;

public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ BcSmartspaceCardDoorbell f$0;
    public final /* synthetic */ ContentResolver f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2(BcSmartspaceCardDoorbell bcSmartspaceCardDoorbell, ContentResolver contentResolver, int i, float f) {
        this.f$0 = bcSmartspaceCardDoorbell;
        this.f$1 = contentResolver;
        this.f$2 = i;
        this.f$3 = f;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return BcSmartspaceCardDoorbell.m691$r8$lambda$BLDdJkDrhhwYM8HQXzENpCl6Gg(this.f$0, this.f$1, this.f$2, this.f$3, (Uri) obj);
    }
}

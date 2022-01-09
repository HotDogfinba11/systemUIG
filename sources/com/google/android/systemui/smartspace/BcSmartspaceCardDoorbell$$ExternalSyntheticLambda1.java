package com.google.android.systemui.smartspace;

import android.content.ContentResolver;
import android.net.Uri;
import java.util.function.Function;

public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ ContentResolver f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1(ContentResolver contentResolver, int i, float f) {
        this.f$0 = contentResolver;
        this.f$1 = i;
        this.f$2 = f;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return BcSmartspaceCardDoorbell.$r8$lambda$6eX6sQTs5MPUIWePViuUlSg5_4g(this.f$0, this.f$1, this.f$2, (Uri) obj);
    }
}

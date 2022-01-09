package com.android.systemui.qs.external;

import android.graphics.drawable.Drawable;
import java.util.function.Supplier;

public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda3 implements Supplier {
    public final /* synthetic */ Drawable f$0;

    public /* synthetic */ CustomTile$$ExternalSyntheticLambda3(Drawable drawable) {
        this.f$0 = drawable;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CustomTile.lambda$handleUpdateState$1(this.f$0);
    }
}

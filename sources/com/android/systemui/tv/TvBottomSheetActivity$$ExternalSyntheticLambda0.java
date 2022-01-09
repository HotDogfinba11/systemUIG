package com.android.systemui.tv;

import java.util.function.Consumer;

public final /* synthetic */ class TvBottomSheetActivity$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ TvBottomSheetActivity f$0;

    public /* synthetic */ TvBottomSheetActivity$$ExternalSyntheticLambda0(TvBottomSheetActivity tvBottomSheetActivity) {
        this.f$0 = tvBottomSheetActivity;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.onBlurChanged(((Boolean) obj).booleanValue());
    }
}

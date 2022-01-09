package com.google.android.systemui.assist.uihints;

import android.widget.ImageView;
import com.google.android.systemui.assist.uihints.BlurProvider;
import java.util.function.Consumer;

public final /* synthetic */ class GlowView$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ GlowView f$0;
    public final /* synthetic */ BlurProvider.BlurResult f$1;

    public /* synthetic */ GlowView$$ExternalSyntheticLambda2(GlowView glowView, BlurProvider.BlurResult blurResult) {
        this.f$0 = glowView;
        this.f$1 = blurResult;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        GlowView.m643$r8$lambda$5m90GCvBsxectjDnv7V3tvtDC0(this.f$0, this.f$1, (ImageView) obj);
    }
}

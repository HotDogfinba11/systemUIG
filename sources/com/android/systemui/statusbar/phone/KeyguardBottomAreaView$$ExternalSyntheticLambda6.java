package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.IntentButtonProvider;
import java.util.function.Consumer;

public final /* synthetic */ class KeyguardBottomAreaView$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ KeyguardBottomAreaView f$0;

    public /* synthetic */ KeyguardBottomAreaView$$ExternalSyntheticLambda6(KeyguardBottomAreaView keyguardBottomAreaView) {
        this.f$0 = keyguardBottomAreaView;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$onAttachedToWindow$5((IntentButtonProvider.IntentButton) obj);
    }
}

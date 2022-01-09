package com.google.android.systemui.smartspace;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.function.Consumer;

public final /* synthetic */ class BcSmartspaceDataProvider$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ BcSmartspaceDataProvider f$0;

    public /* synthetic */ BcSmartspaceDataProvider$$ExternalSyntheticLambda0(BcSmartspaceDataProvider bcSmartspaceDataProvider) {
        this.f$0 = bcSmartspaceDataProvider;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$onTargetsAvailable$0((BcSmartspaceDataPlugin.SmartspaceTargetListener) obj);
    }
}

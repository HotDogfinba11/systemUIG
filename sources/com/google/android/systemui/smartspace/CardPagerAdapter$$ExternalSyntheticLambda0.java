package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTargetEvent;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

public final /* synthetic */ class CardPagerAdapter$$ExternalSyntheticLambda0 implements BcSmartspaceDataPlugin.SmartspaceEventNotifier {
    public final /* synthetic */ BcSmartspaceDataPlugin f$0;

    public /* synthetic */ CardPagerAdapter$$ExternalSyntheticLambda0(BcSmartspaceDataPlugin bcSmartspaceDataPlugin) {
        this.f$0 = bcSmartspaceDataPlugin;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
        this.f$0.notifySmartspaceEvent(smartspaceTargetEvent);
    }
}

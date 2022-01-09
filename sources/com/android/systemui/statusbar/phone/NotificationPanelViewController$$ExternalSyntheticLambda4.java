package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.qs.QS;

public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda4 implements QS.ScrollListener {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda4(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    @Override // com.android.systemui.plugins.qs.QS.ScrollListener
    public final void onQsPanelScrollChanged(int i) {
        this.f$0.lambda$new$10(i);
    }
}

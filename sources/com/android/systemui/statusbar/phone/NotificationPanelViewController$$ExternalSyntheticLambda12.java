package com.android.systemui.statusbar.phone;

import java.util.function.Consumer;

public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda12(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.onStackYChanged(((Boolean) obj).booleanValue());
    }
}

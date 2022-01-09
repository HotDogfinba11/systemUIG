package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;

public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda1 implements NotifLifetimeExtender.OnEndLifetimeExtensionCallback {
    public final /* synthetic */ NotifCollection f$0;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda1(NotifCollection notifCollection) {
        this.f$0 = notifCollection;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender.OnEndLifetimeExtensionCallback
    public final void onEndLifetimeExtension(NotifLifetimeExtender notifLifetimeExtender, NotificationEntry notificationEntry) {
        this.f$0.onEndLifetimeExtension(notifLifetimeExtender, notificationEntry);
    }
}

package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.service.notification.StatusBarNotification;
import android.view.View;
import com.android.systemui.statusbar.notification.row.NotificationInfo;

public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda6 implements NotificationInfo.OnSettingsClickListener {
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ NotificationGuts f$1;
    public final /* synthetic */ StatusBarNotification f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ ExpandableNotificationRow f$4;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda6(NotificationGutsManager notificationGutsManager, NotificationGuts notificationGuts, StatusBarNotification statusBarNotification, String str, ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = notificationGutsManager;
        this.f$1 = notificationGuts;
        this.f$2 = statusBarNotification;
        this.f$3 = str;
        this.f$4 = expandableNotificationRow;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInfo.OnSettingsClickListener
    public final void onClick(View view, NotificationChannel notificationChannel, int i) {
        this.f$0.lambda$initializeNotificationInfo$3(this.f$1, this.f$2, this.f$3, this.f$4, view, notificationChannel, i);
    }
}

package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.android.systemui.R$string;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BatteryStateNotifier.kt */
public final class BatteryStateNotifier implements BatteryController.BatteryStateChangeCallback {
    private final Context context;
    private final BatteryController controller;
    private final DelayableExecutor delayableExecutor;
    private final NotificationManager noMan;
    private boolean stateUnknown;

    public BatteryStateNotifier(BatteryController batteryController, NotificationManager notificationManager, DelayableExecutor delayableExecutor2, Context context2) {
        Intrinsics.checkNotNullParameter(batteryController, "controller");
        Intrinsics.checkNotNullParameter(notificationManager, "noMan");
        Intrinsics.checkNotNullParameter(delayableExecutor2, "delayableExecutor");
        Intrinsics.checkNotNullParameter(context2, "context");
        this.controller = batteryController;
        this.noMan = notificationManager;
        this.delayableExecutor = delayableExecutor2;
        this.context = context2;
    }

    public final NotificationManager getNoMan() {
        return this.noMan;
    }

    public final boolean getStateUnknown() {
        return this.stateUnknown;
    }

    public final void startListening() {
        this.controller.addCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public void onBatteryUnknownStateChanged(boolean z) {
        this.stateUnknown = z;
        if (z) {
            NotificationChannel notificationChannel = new NotificationChannel("battery_status", "Battery status", 3);
            this.noMan.createNotificationChannel(notificationChannel);
            this.noMan.notify("BatteryStateNotifier", 666, new Notification.Builder(this.context, notificationChannel.getId()).setAutoCancel(false).setContentTitle(this.context.getString(R$string.battery_state_unknown_notification_title)).setContentText(this.context.getString(R$string.battery_state_unknown_notification_text)).setSmallIcon(17303566).setContentIntent(PendingIntent.getActivity(this.context, 0, new Intent("android.intent.action.VIEW", Uri.parse(this.context.getString(R$string.config_batteryStateUnknownUrl))), 67108864)).setAutoCancel(true).setOngoing(true).build());
            return;
        }
        scheduleNotificationCancel();
    }

    private final void scheduleNotificationCancel() {
        this.delayableExecutor.executeDelayed(new BatteryStateNotifierKt$sam$java_lang_Runnable$0(new BatteryStateNotifier$scheduleNotificationCancel$r$1(this)), 14400000);
    }
}

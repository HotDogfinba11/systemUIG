package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.content.DialogInterface;

/* access modifiers changed from: package-private */
/* compiled from: ChannelEditorDialogController.kt */
public final class ChannelEditorDialogController$initDialog$1$3 implements DialogInterface.OnShowListener {
    final /* synthetic */ ChannelEditorListView $listView;
    final /* synthetic */ ChannelEditorDialogController this$0;

    ChannelEditorDialogController$initDialog$1$3(ChannelEditorDialogController channelEditorDialogController, ChannelEditorListView channelEditorListView) {
        this.this$0 = channelEditorDialogController;
        this.$listView = channelEditorListView;
    }

    public final void onShow(DialogInterface dialogInterface) {
        for (NotificationChannel notificationChannel : this.this$0.providedChannels) {
            ChannelEditorListView channelEditorListView = this.$listView;
            if (channelEditorListView != null) {
                channelEditorListView.highlightChannel(notificationChannel);
            }
        }
    }
}

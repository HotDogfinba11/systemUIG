package com.android.systemui.media.dialog;

import android.content.Context;
import android.media.session.MediaSessionManager;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.phone.ShadeController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaOutputDialogFactory.kt */
public final class MediaOutputDialogFactory {
    public static final Companion Companion = new Companion(null);
    private static MediaOutputDialog mediaOutputDialog;
    private final Context context;
    private final LocalBluetoothManager lbm;
    private final MediaSessionManager mediaSessionManager;
    private final NotificationEntryManager notificationEntryManager;
    private final ShadeController shadeController;
    private final ActivityStarter starter;
    private final UiEventLogger uiEventLogger;

    public MediaOutputDialogFactory(Context context2, MediaSessionManager mediaSessionManager2, LocalBluetoothManager localBluetoothManager, ShadeController shadeController2, ActivityStarter activityStarter, NotificationEntryManager notificationEntryManager2, UiEventLogger uiEventLogger2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(mediaSessionManager2, "mediaSessionManager");
        Intrinsics.checkNotNullParameter(shadeController2, "shadeController");
        Intrinsics.checkNotNullParameter(activityStarter, "starter");
        Intrinsics.checkNotNullParameter(notificationEntryManager2, "notificationEntryManager");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        this.context = context2;
        this.mediaSessionManager = mediaSessionManager2;
        this.lbm = localBluetoothManager;
        this.shadeController = shadeController2;
        this.starter = activityStarter;
        this.notificationEntryManager = notificationEntryManager2;
        this.uiEventLogger = uiEventLogger2;
    }

    /* compiled from: MediaOutputDialogFactory.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MediaOutputDialog getMediaOutputDialog() {
            return MediaOutputDialogFactory.mediaOutputDialog;
        }

        public final void setMediaOutputDialog(MediaOutputDialog mediaOutputDialog) {
            MediaOutputDialogFactory.mediaOutputDialog = mediaOutputDialog;
        }
    }

    public final void create(String str, boolean z) {
        Intrinsics.checkNotNullParameter(str, "packageName");
        Companion companion = Companion;
        MediaOutputDialog mediaOutputDialog2 = companion.getMediaOutputDialog();
        if (mediaOutputDialog2 != null) {
            mediaOutputDialog2.dismiss();
        }
        companion.setMediaOutputDialog(new MediaOutputDialog(this.context, z, new MediaOutputController(this.context, str, z, this.mediaSessionManager, this.lbm, this.shadeController, this.starter, this.notificationEntryManager, this.uiEventLogger), this.uiEventLogger));
    }

    public final void dismiss() {
        Companion companion = Companion;
        MediaOutputDialog mediaOutputDialog2 = companion.getMediaOutputDialog();
        if (mediaOutputDialog2 != null) {
            mediaOutputDialog2.dismiss();
        }
        companion.setMediaOutputDialog(null);
    }
}

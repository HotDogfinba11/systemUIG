package com.android.systemui.media;

import android.app.Notification;
import android.app.PendingIntent;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$loadMediaDataInBg$runnable$1 implements Runnable {
    final /* synthetic */ Notification.Action $action;
    final /* synthetic */ MediaDataManager this$0;

    MediaDataManager$loadMediaDataInBg$runnable$1(Notification.Action action, MediaDataManager mediaDataManager) {
        this.$action = action;
        this.this$0 = mediaDataManager;
    }

    public final void run() {
        if (this.$action.isAuthenticationRequired()) {
            ActivityStarter activityStarter = this.this$0.activityStarter;
            final MediaDataManager mediaDataManager = this.this$0;
            final Notification.Action action = this.$action;
            activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() {
                /* class com.android.systemui.media.MediaDataManager$loadMediaDataInBg$runnable$1.AnonymousClass1 */

                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    MediaDataManager mediaDataManager = mediaDataManager;
                    PendingIntent pendingIntent = action.actionIntent;
                    Intrinsics.checkNotNullExpressionValue(pendingIntent, "action.actionIntent");
                    return mediaDataManager.sendPendingIntent(pendingIntent);
                }
            }, AnonymousClass2.INSTANCE, true);
            return;
        }
        MediaDataManager mediaDataManager2 = this.this$0;
        PendingIntent pendingIntent = this.$action.actionIntent;
        Intrinsics.checkNotNullExpressionValue(pendingIntent, "action.actionIntent");
        boolean unused = mediaDataManager2.sendPendingIntent(pendingIntent);
    }
}

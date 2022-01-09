package com.android.systemui.statusbar.notification.icon;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: IconManager.kt */
public final class IconManager$sensitivityListener$1 implements NotificationEntry.OnSensitivityChangedListener {
    final /* synthetic */ IconManager this$0;

    IconManager$sensitivityListener$1(IconManager iconManager) {
        this.this$0 = iconManager;
    }

    @Override // com.android.systemui.statusbar.notification.collection.NotificationEntry.OnSensitivityChangedListener
    public final void onSensitivityChanged(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        this.this$0.updateIconsSafe(notificationEntry);
    }
}

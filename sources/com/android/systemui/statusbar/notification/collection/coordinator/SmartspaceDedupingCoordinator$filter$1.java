package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public final class SmartspaceDedupingCoordinator$filter$1 extends NotifFilter {
    final /* synthetic */ SmartspaceDedupingCoordinator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SmartspaceDedupingCoordinator$filter$1(SmartspaceDedupingCoordinator smartspaceDedupingCoordinator) {
        super("SmartspaceDedupingFilter");
        this.this$0 = smartspaceDedupingCoordinator;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
    public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        return (this.this$0.isOnLockscreen) && (this.this$0.isDupedWithSmartspaceContent(notificationEntry));
    }
}

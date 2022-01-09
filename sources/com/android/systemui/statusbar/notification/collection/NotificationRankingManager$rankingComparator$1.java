package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;
import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: NotificationRankingManager.kt */
public final class NotificationRankingManager$rankingComparator$1 implements Comparator<NotificationEntry> {
    final /* synthetic */ NotificationRankingManager this$0;

    NotificationRankingManager$rankingComparator$1(NotificationRankingManager notificationRankingManager) {
        this.this$0 = notificationRankingManager;
    }

    public final int compare(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        StatusBarNotification sbn = notificationEntry.getSbn();
        Intrinsics.checkNotNullExpressionValue(sbn, "a.sbn");
        StatusBarNotification sbn2 = notificationEntry2.getSbn();
        Intrinsics.checkNotNullExpressionValue(sbn2, "b.sbn");
        int rank = notificationEntry.getRanking().getRank();
        int rank2 = notificationEntry2.getRanking().getRank();
        Intrinsics.checkNotNullExpressionValue(notificationEntry, "a");
        boolean z = NotificationRankingManagerKt.isColorizedForegroundService(notificationEntry);
        Intrinsics.checkNotNullExpressionValue(notificationEntry2, "b");
        boolean z2 = NotificationRankingManagerKt.isColorizedForegroundService(notificationEntry2);
        boolean z3 = NotificationRankingManagerKt.isImportantCall(notificationEntry);
        boolean z4 = NotificationRankingManagerKt.isImportantCall(notificationEntry2);
        int i = this.this$0.getPeopleNotificationType(notificationEntry);
        int i2 = this.this$0.getPeopleNotificationType(notificationEntry2);
        boolean z5 = this.this$0.isImportantMedia(notificationEntry);
        boolean z6 = this.this$0.isImportantMedia(notificationEntry2);
        boolean z7 = NotificationRankingManagerKt.isSystemMax(notificationEntry);
        boolean z8 = NotificationRankingManagerKt.isSystemMax(notificationEntry2);
        boolean isRowHeadsUp = notificationEntry.isRowHeadsUp();
        boolean isRowHeadsUp2 = notificationEntry2.isRowHeadsUp();
        boolean z9 = this.this$0.isHighPriority(notificationEntry);
        boolean z10 = this.this$0.isHighPriority(notificationEntry2);
        if (isRowHeadsUp != isRowHeadsUp2) {
            if (!isRowHeadsUp) {
                return 1;
            }
        } else if (isRowHeadsUp) {
            return this.this$0.headsUpManager.compare(notificationEntry, notificationEntry2);
        } else {
            if (z != z2) {
                if (!z) {
                    return 1;
                }
            } else if (z3 != z4) {
                if (!z3) {
                    return 1;
                }
            } else if ((this.this$0.getUsePeopleFiltering()) && i != i2) {
                return this.this$0.peopleNotificationIdentifier.compareTo(i, i2);
            } else {
                if (z5 != z6) {
                    if (!z5) {
                        return 1;
                    }
                } else if (z7 != z8) {
                    if (!z7) {
                        return 1;
                    }
                } else if (z9 != z10) {
                    return Intrinsics.compare(z9 ? 1 : 0, z10 ? 1 : 0) * -1;
                } else {
                    if (rank != rank2) {
                        return rank - rank2;
                    }
                    return Intrinsics.compare(sbn2.getNotification().when, sbn.getNotification().when);
                }
            }
        }
        return -1;
    }
}

package com.android.systemui.statusbar.notification.people;

import android.app.NotificationChannel;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PeopleNotificationIdentifier.kt */
public final class PeopleNotificationIdentifierImpl implements PeopleNotificationIdentifier {
    private final GroupMembershipManager groupManager;
    private final NotificationPersonExtractor personExtractor;

    public PeopleNotificationIdentifierImpl(NotificationPersonExtractor notificationPersonExtractor, GroupMembershipManager groupMembershipManager) {
        Intrinsics.checkNotNullParameter(notificationPersonExtractor, "personExtractor");
        Intrinsics.checkNotNullParameter(groupMembershipManager, "groupManager");
        this.personExtractor = notificationPersonExtractor;
        this.groupManager = groupMembershipManager;
    }

    @Override // com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier
    public int getPeopleNotificationType(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        NotificationListenerService.Ranking ranking = notificationEntry.getRanking();
        Intrinsics.checkNotNullExpressionValue(ranking, "entry.ranking");
        int personTypeInfo = getPersonTypeInfo(ranking);
        if (personTypeInfo == 3) {
            return 3;
        }
        StatusBarNotification sbn = notificationEntry.getSbn();
        Intrinsics.checkNotNullExpressionValue(sbn, "entry.sbn");
        int upperBound = upperBound(personTypeInfo, extractPersonTypeInfo(sbn));
        if (upperBound == 3) {
            return 3;
        }
        return upperBound(upperBound, getPeopleTypeOfSummary(notificationEntry));
    }

    @Override // com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier
    public int compareTo(int i, int i2) {
        return Intrinsics.compare(i2, i);
    }

    private final int upperBound(int i, int i2) {
        return Math.max(i, i2);
    }

    private final int getPersonTypeInfo(NotificationListenerService.Ranking ranking) {
        if (!ranking.isConversation()) {
            return 0;
        }
        if (ranking.getConversationShortcutInfo() == null) {
            return 1;
        }
        NotificationChannel channel = ranking.getChannel();
        return Intrinsics.areEqual(channel == null ? null : Boolean.valueOf(channel.isImportantConversation()), Boolean.TRUE) ? 3 : 2;
    }

    private final int extractPersonTypeInfo(StatusBarNotification statusBarNotification) {
        return this.personExtractor.isPersonNotification(statusBarNotification) ? 1 : 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0031  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int getPeopleTypeOfSummary(com.android.systemui.statusbar.notification.collection.NotificationEntry r3) {
        /*
            r2 = this;
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager r0 = r2.groupManager
            boolean r0 = r0.isGroupSummary(r3)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager r0 = r2.groupManager
            java.util.List r3 = r0.getChildren(r3)
            r0 = 0
            if (r3 != 0) goto L_0x0014
            goto L_0x0024
        L_0x0014:
            kotlin.sequences.Sequence r3 = kotlin.collections.CollectionsKt.asSequence(r3)
            if (r3 != 0) goto L_0x001b
            goto L_0x0024
        L_0x001b:
            com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl$getPeopleTypeOfSummary$childTypes$1 r0 = new com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl$getPeopleTypeOfSummary$childTypes$1
            r0.<init>(r2)
            kotlin.sequences.Sequence r0 = kotlin.sequences.SequencesKt.map(r3, r0)
        L_0x0024:
            if (r0 != 0) goto L_0x0027
            return r1
        L_0x0027:
            java.util.Iterator r3 = r0.iterator()
        L_0x002b:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x0042
            java.lang.Object r0 = r3.next()
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
            int r1 = r2.upperBound(r1, r0)
            r0 = 3
            if (r1 != r0) goto L_0x002b
        L_0x0042:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl.getPeopleTypeOfSummary(com.android.systemui.statusbar.notification.collection.NotificationEntry):int");
    }
}

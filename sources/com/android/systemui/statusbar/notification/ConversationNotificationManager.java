package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.view.View;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager {
    public static final Companion Companion = new Companion(null);
    private final Context context;
    private final Handler mainHandler;
    private boolean notifPanelCollapsed = true;
    private final NotificationEntryManager notificationEntryManager;
    private final NotificationGroupManagerLegacy notificationGroupManager;
    private final ConcurrentHashMap<String, ConversationState> states = new ConcurrentHashMap<>();

    public ConversationNotificationManager(NotificationEntryManager notificationEntryManager2, NotificationGroupManagerLegacy notificationGroupManagerLegacy, Context context2, Handler handler) {
        Intrinsics.checkNotNullParameter(notificationEntryManager2, "notificationEntryManager");
        Intrinsics.checkNotNullParameter(notificationGroupManagerLegacy, "notificationGroupManager");
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(handler, "mainHandler");
        this.notificationEntryManager = notificationEntryManager2;
        this.notificationGroupManager = notificationGroupManagerLegacy;
        this.context = context2;
        this.mainHandler = handler;
        notificationEntryManager2.addNotificationEntryListener(new NotificationEntryListener(this) {
            /* class com.android.systemui.statusbar.notification.ConversationNotificationManager.AnonymousClass1 */
            final /* synthetic */ ConversationNotificationManager this$0;

            {
                this.this$0 = r1;
            }

            /* access modifiers changed from: private */
            public static final Sequence<View> onNotificationRankingUpdated$getLayouts(NotificationContentView notificationContentView) {
                return SequencesKt__SequencesKt.sequenceOf(notificationContentView.getContractedChild(), notificationContentView.getExpandedChild(), notificationContentView.getHeadsUpChild());
            }

            @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
            public void onNotificationRankingUpdated(NotificationListenerService.RankingMap rankingMap) {
                Sequence<ConversationLayout> sequence;
                Sequence sequence2;
                NotificationContentView[] layouts;
                Intrinsics.checkNotNullParameter(rankingMap, "rankingMap");
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                Set keySet = this.this$0.states.keySet();
                Intrinsics.checkNotNullExpressionValue(keySet, "states.keys");
                for (NotificationEntry notificationEntry : SequencesKt___SequencesKt.mapNotNull(CollectionsKt___CollectionsKt.asSequence(keySet), new ConversationNotificationManager$1$onNotificationRankingUpdated$activeConversationEntries$1(this.this$0))) {
                    if (rankingMap.getRanking(notificationEntry.getSbn().getKey(), ranking) && ranking.isConversation()) {
                        boolean isImportantConversation = ranking.getChannel().isImportantConversation();
                        ExpandableNotificationRow row = notificationEntry.getRow();
                        Sequence sequence3 = null;
                        Sequence sequence4 = (row == null || (layouts = row.getLayouts()) == null) ? null : ArraysKt___ArraysKt.asSequence(layouts);
                        if (!(sequence4 == null || (sequence2 = SequencesKt___SequencesKt.flatMap(sequence4, ConversationNotificationManager$1$onNotificationRankingUpdated$1.INSTANCE)) == null)) {
                            sequence3 = SequencesKt___SequencesKt.mapNotNull(sequence2, ConversationNotificationManager$1$onNotificationRankingUpdated$2.INSTANCE);
                        }
                        boolean z = false;
                        if (!(sequence3 == null || (sequence = SequencesKt___SequencesKt.filterNot(sequence3, new ConversationNotificationManager$1$onNotificationRankingUpdated$3(isImportantConversation))) == null)) {
                            ConversationNotificationManager conversationNotificationManager = this.this$0;
                            boolean z2 = false;
                            for (ConversationLayout conversationLayout : sequence) {
                                if (!isImportantConversation || !notificationEntry.isMarkedForUserTriggeredMovement()) {
                                    conversationLayout.setIsImportantConversation(isImportantConversation, false);
                                } else {
                                    conversationNotificationManager.mainHandler.postDelayed(new ConversationNotificationManager$1$onNotificationRankingUpdated$4$1(conversationLayout, isImportantConversation), 960);
                                }
                                z2 = true;
                            }
                            z = z2;
                        }
                        if (z) {
                            this.this$0.notificationGroupManager.updateIsolation(notificationEntry);
                        }
                    }
                }
            }

            @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
            public void onEntryInflated(NotificationEntry notificationEntry) {
                Intrinsics.checkNotNullParameter(notificationEntry, "entry");
                if (notificationEntry.getRanking().isConversation()) {
                    ExpandableNotificationRow row = notificationEntry.getRow();
                    if (row != null) {
                        row.setOnExpansionChangedListener(new ConversationNotificationManager$1$onEntryInflated$1(notificationEntry, this.this$0));
                    }
                    ConversationNotificationManager conversationNotificationManager = this.this$0;
                    ExpandableNotificationRow row2 = notificationEntry.getRow();
                    onEntryInflated$updateCount(conversationNotificationManager, notificationEntry, Intrinsics.areEqual(row2 == null ? null : Boolean.valueOf(row2.isExpanded()), Boolean.TRUE));
                }
            }

            /* access modifiers changed from: private */
            public static final void onEntryInflated$updateCount(ConversationNotificationManager conversationNotificationManager, NotificationEntry notificationEntry, boolean z) {
                if (!z) {
                    return;
                }
                if (!conversationNotificationManager.notifPanelCollapsed || notificationEntry.isPinnedAndExpanded()) {
                    String key = notificationEntry.getKey();
                    Intrinsics.checkNotNullExpressionValue(key, "entry.key");
                    conversationNotificationManager.resetCount(key);
                    ExpandableNotificationRow row = notificationEntry.getRow();
                    if (row != null) {
                        conversationNotificationManager.resetBadgeUi(row);
                    }
                }
            }

            @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
            public void onEntryReinflated(NotificationEntry notificationEntry) {
                Intrinsics.checkNotNullParameter(notificationEntry, "entry");
                onEntryInflated(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
            public void onEntryRemoved(NotificationEntry notificationEntry, NotificationVisibility notificationVisibility, boolean z, int i) {
                Intrinsics.checkNotNullParameter(notificationEntry, "entry");
                this.this$0.removeTrackedEntry(notificationEntry);
            }
        });
    }

    /* access modifiers changed from: private */
    public final boolean shouldIncrementUnread(ConversationState conversationState, Notification.Builder builder) {
        if ((conversationState.getNotification().flags & 8) != 0) {
            return false;
        }
        return Notification.areStyledNotificationsVisiblyDifferent(Notification.Builder.recoverBuilder(this.context, conversationState.getNotification()), builder);
    }

    public final int getUnreadCount(NotificationEntry notificationEntry, Notification.Builder builder) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        Intrinsics.checkNotNullParameter(builder, "recoveredBuilder");
        ConversationState compute = this.states.compute(notificationEntry.getKey(), new ConversationNotificationManager$getUnreadCount$1(notificationEntry, this, builder));
        Intrinsics.checkNotNull(compute);
        return compute.getUnreadCount();
    }

    public final void onNotificationPanelExpandStateChanged(boolean z) {
        this.notifPanelCollapsed = z;
        if (!z) {
            Map map = MapsKt__MapsKt.toMap(SequencesKt___SequencesKt.mapNotNull(MapsKt___MapsKt.asSequence(this.states), new ConversationNotificationManager$onNotificationPanelExpandStateChanged$expanded$1(this)));
            this.states.replaceAll(new ConversationNotificationManager$onNotificationPanelExpandStateChanged$1(map));
            for (ExpandableNotificationRow expandableNotificationRow : SequencesKt___SequencesKt.mapNotNull(CollectionsKt___CollectionsKt.asSequence(map.values()), ConversationNotificationManager$onNotificationPanelExpandStateChanged$2.INSTANCE)) {
                resetBadgeUi(expandableNotificationRow);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void resetCount(String str) {
        this.states.compute(str, ConversationNotificationManager$resetCount$1.INSTANCE);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void removeTrackedEntry(NotificationEntry notificationEntry) {
        this.states.remove(notificationEntry.getKey());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void resetBadgeUi(ExpandableNotificationRow expandableNotificationRow) {
        NotificationContentView[] layouts = expandableNotificationRow.getLayouts();
        Sequence sequence = layouts == null ? null : ArraysKt___ArraysKt.asSequence(layouts);
        if (sequence == null) {
            sequence = SequencesKt__SequencesKt.emptySequence();
        }
        for (ConversationLayout conversationLayout : SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(sequence, ConversationNotificationManager$resetBadgeUi$1.INSTANCE), ConversationNotificationManager$resetBadgeUi$2.INSTANCE)) {
            conversationLayout.setUnreadCount(0);
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: ConversationNotifications.kt */
    public static final class ConversationState {
        private final Notification notification;
        private final int unreadCount;

        public static /* synthetic */ ConversationState copy$default(ConversationState conversationState, int i, Notification notification2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = conversationState.unreadCount;
            }
            if ((i2 & 2) != 0) {
                notification2 = conversationState.notification;
            }
            return conversationState.copy(i, notification2);
        }

        public final ConversationState copy(int i, Notification notification2) {
            Intrinsics.checkNotNullParameter(notification2, "notification");
            return new ConversationState(i, notification2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConversationState)) {
                return false;
            }
            ConversationState conversationState = (ConversationState) obj;
            return this.unreadCount == conversationState.unreadCount && Intrinsics.areEqual(this.notification, conversationState.notification);
        }

        public int hashCode() {
            return (Integer.hashCode(this.unreadCount) * 31) + this.notification.hashCode();
        }

        public String toString() {
            return "ConversationState(unreadCount=" + this.unreadCount + ", notification=" + this.notification + ')';
        }

        public ConversationState(int i, Notification notification2) {
            Intrinsics.checkNotNullParameter(notification2, "notification");
            this.unreadCount = i;
            this.notification = notification2;
        }

        public final Notification getNotification() {
            return this.notification;
        }

        public final int getUnreadCount() {
            return this.unreadCount;
        }
    }

    /* compiled from: ConversationNotifications.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

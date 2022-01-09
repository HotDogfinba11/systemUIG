package com.android.systemui.statusbar.notification.collection;

import android.service.notification.NotificationListenerService;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.NotificationEntryManagerLogger;
import com.android.systemui.statusbar.notification.NotificationFilter;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.legacy.LegacyNotificationRanker;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: NotificationRankingManager.kt */
public class NotificationRankingManager implements LegacyNotificationRanker {
    private final NotificationGroupManagerLegacy groupManager;
    private final HeadsUpManager headsUpManager;
    private final HighPriorityProvider highPriorityProvider;
    private final NotificationEntryManager.KeyguardEnvironment keyguardEnvironment;
    private final NotificationEntryManagerLogger logger;
    private final Lazy mediaManager$delegate = LazyKt.lazy(new NotificationRankingManager$mediaManager$2(this));
    private final dagger.Lazy<NotificationMediaManager> mediaManagerLazy;
    private final NotificationFilter notifFilter;
    private final PeopleNotificationIdentifier peopleNotificationIdentifier;
    private final Comparator<NotificationEntry> rankingComparator = new NotificationRankingManager$rankingComparator$1(this);
    private NotificationListenerService.RankingMap rankingMap;
    private final NotificationSectionsFeatureManager sectionsFeatureManager;

    public NotificationRankingManager(dagger.Lazy<NotificationMediaManager> lazy, NotificationGroupManagerLegacy notificationGroupManagerLegacy, HeadsUpManager headsUpManager2, NotificationFilter notificationFilter, NotificationEntryManagerLogger notificationEntryManagerLogger, NotificationSectionsFeatureManager notificationSectionsFeatureManager, PeopleNotificationIdentifier peopleNotificationIdentifier2, HighPriorityProvider highPriorityProvider2, NotificationEntryManager.KeyguardEnvironment keyguardEnvironment2) {
        Intrinsics.checkNotNullParameter(lazy, "mediaManagerLazy");
        Intrinsics.checkNotNullParameter(notificationGroupManagerLegacy, "groupManager");
        Intrinsics.checkNotNullParameter(headsUpManager2, "headsUpManager");
        Intrinsics.checkNotNullParameter(notificationFilter, "notifFilter");
        Intrinsics.checkNotNullParameter(notificationEntryManagerLogger, "logger");
        Intrinsics.checkNotNullParameter(notificationSectionsFeatureManager, "sectionsFeatureManager");
        Intrinsics.checkNotNullParameter(peopleNotificationIdentifier2, "peopleNotificationIdentifier");
        Intrinsics.checkNotNullParameter(highPriorityProvider2, "highPriorityProvider");
        Intrinsics.checkNotNullParameter(keyguardEnvironment2, "keyguardEnvironment");
        this.mediaManagerLazy = lazy;
        this.groupManager = notificationGroupManagerLegacy;
        this.headsUpManager = headsUpManager2;
        this.notifFilter = notificationFilter;
        this.logger = notificationEntryManagerLogger;
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.peopleNotificationIdentifier = peopleNotificationIdentifier2;
        this.highPriorityProvider = highPriorityProvider2;
        this.keyguardEnvironment = keyguardEnvironment2;
    }

    @Override // com.android.systemui.statusbar.notification.collection.legacy.LegacyNotificationRanker
    public NotificationListenerService.RankingMap getRankingMap() {
        return this.rankingMap;
    }

    /* access modifiers changed from: protected */
    public void setRankingMap(NotificationListenerService.RankingMap rankingMap2) {
        this.rankingMap = rankingMap2;
    }

    private final NotificationMediaManager getMediaManager() {
        return (NotificationMediaManager) this.mediaManager$delegate.getValue();
    }

    /* access modifiers changed from: private */
    public final boolean getUsePeopleFiltering() {
        return this.sectionsFeatureManager.isFilteringEnabled();
    }

    @Override // com.android.systemui.statusbar.notification.collection.legacy.LegacyNotificationRanker
    public List<NotificationEntry> updateRanking(NotificationListenerService.RankingMap rankingMap2, Collection<NotificationEntry> collection, String str) {
        List<NotificationEntry> filterAndSortLocked;
        Intrinsics.checkNotNullParameter(collection, "entries");
        Intrinsics.checkNotNullParameter(str, "reason");
        if (rankingMap2 != null) {
            setRankingMap(rankingMap2);
            updateRankingForEntries(collection);
        }
        synchronized (this) {
            filterAndSortLocked = filterAndSortLocked(collection, str);
        }
        return filterAndSortLocked;
    }

    @Override // com.android.systemui.statusbar.notification.collection.legacy.LegacyNotificationRanker
    public boolean isNotificationForCurrentProfiles(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        return this.keyguardEnvironment.isNotificationForCurrentProfiles(notificationEntry.getSbn());
    }

    private final List<NotificationEntry> filterAndSortLocked(Collection<NotificationEntry> collection, String str) {
        this.logger.logFilterAndSort(str);
        List<NotificationEntry> list = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.sortedWith(SequencesKt___SequencesKt.filterNot(CollectionsKt___CollectionsKt.asSequence(collection), new NotificationRankingManager$filterAndSortLocked$filtered$1(this)), this.rankingComparator));
        for (T t : collection) {
            t.setBucket(getBucketForEntry(t));
        }
        return list;
    }

    /* access modifiers changed from: private */
    public final boolean filter(NotificationEntry notificationEntry) {
        boolean shouldFilterOut = this.notifFilter.shouldFilterOut(notificationEntry);
        if (shouldFilterOut) {
            notificationEntry.resetInitializationTime();
        }
        return shouldFilterOut;
    }

    private final int getBucketForEntry(NotificationEntry notificationEntry) {
        boolean access$isImportantCall = NotificationRankingManagerKt.access$isImportantCall(notificationEntry);
        boolean isRowHeadsUp = notificationEntry.isRowHeadsUp();
        boolean isImportantMedia = isImportantMedia(notificationEntry);
        boolean access$isSystemMax = NotificationRankingManagerKt.access$isSystemMax(notificationEntry);
        if (NotificationRankingManagerKt.access$isColorizedForegroundService(notificationEntry) || access$isImportantCall) {
            return 3;
        }
        if (!getUsePeopleFiltering() || !isConversation(notificationEntry)) {
            return (isRowHeadsUp || isImportantMedia || access$isSystemMax || isHighPriority(notificationEntry)) ? 5 : 6;
        }
        return 4;
    }

    private final void updateRankingForEntries(Iterable<NotificationEntry> iterable) {
        NotificationListenerService.RankingMap rankingMap2 = getRankingMap();
        if (rankingMap2 != null) {
            synchronized (iterable) {
                for (NotificationEntry notificationEntry : iterable) {
                    NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                    if (rankingMap2.getRanking(notificationEntry.getKey(), ranking)) {
                        notificationEntry.setRanking(ranking);
                        String overrideGroupKey = ranking.getOverrideGroupKey();
                        if (!Objects.equals(notificationEntry.getSbn().getOverrideGroupKey(), overrideGroupKey)) {
                            String groupKey = notificationEntry.getSbn().getGroupKey();
                            boolean isGroup = notificationEntry.getSbn().isGroup();
                            boolean isGroupSummary = notificationEntry.getSbn().getNotification().isGroupSummary();
                            notificationEntry.getSbn().setOverrideGroupKey(overrideGroupKey);
                            this.groupManager.onEntryUpdated(notificationEntry, groupKey, isGroup, isGroupSummary);
                        }
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean isImportantMedia(NotificationEntry notificationEntry) {
        return Intrinsics.areEqual(notificationEntry.getKey(), getMediaManager().getMediaNotificationKey()) && notificationEntry.getImportance() > 1;
    }

    private final boolean isConversation(NotificationEntry notificationEntry) {
        return getPeopleNotificationType(notificationEntry) != 0;
    }

    /* access modifiers changed from: private */
    public final int getPeopleNotificationType(NotificationEntry notificationEntry) {
        return this.peopleNotificationIdentifier.getPeopleNotificationType(notificationEntry);
    }

    /* access modifiers changed from: private */
    public final boolean isHighPriority(NotificationEntry notificationEntry) {
        return this.highPriorityProvider.isHighPriority(notificationEntry);
    }
}

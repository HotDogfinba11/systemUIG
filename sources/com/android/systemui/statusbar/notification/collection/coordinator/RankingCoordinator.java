package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;

public class RankingCoordinator implements Coordinator {
    private final NodeController mAlertingHeaderController;
    private final NotifSectioner mAlertingNotifSectioner = new NotifSectioner("Alerting") {
        /* class com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.AnonymousClass1 */

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            return RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return RankingCoordinator.this.mAlertingHeaderController;
        }
    };
    private final NotifFilter mDndVisualEffectsFilter = new NotifFilter("DndSuppressingVisualEffects") {
        /* class com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.AnonymousClass4 */

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (RankingCoordinator.this.mStatusBarStateController.isDozing() && notificationEntry.shouldSuppressAmbient()) {
                return true;
            }
            if (RankingCoordinator.this.mStatusBarStateController.isDozing() || !notificationEntry.shouldSuppressNotificationList()) {
                return false;
            }
            return true;
        }
    };
    private final HighPriorityProvider mHighPriorityProvider;
    private final NodeController mSilentHeaderController;
    private final NotifSectioner mSilentNotifSectioner = new NotifSectioner("Silent") {
        /* class com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.AnonymousClass2 */

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            return !RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return RankingCoordinator.this.mSilentHeaderController;
        }
    };
    private final StatusBarStateController.StateListener mStatusBarStateCallback = new StatusBarStateController.StateListener() {
        /* class com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.AnonymousClass5 */

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onDozingChanged(boolean z) {
            RankingCoordinator.this.mDndVisualEffectsFilter.invalidateList();
        }
    };
    private final StatusBarStateController mStatusBarStateController;
    private final NotifFilter mSuspendedFilter = new NotifFilter("IsSuspendedFilter") {
        /* class com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.AnonymousClass3 */

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return notificationEntry.getRanking().isSuspended();
        }
    };

    public RankingCoordinator(StatusBarStateController statusBarStateController, HighPriorityProvider highPriorityProvider, NodeController nodeController, NodeController nodeController2) {
        this.mStatusBarStateController = statusBarStateController;
        this.mHighPriorityProvider = highPriorityProvider;
        this.mAlertingHeaderController = nodeController;
        this.mSilentHeaderController = nodeController2;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mStatusBarStateController.addCallback(this.mStatusBarStateCallback);
        notifPipeline.addPreGroupFilter(this.mSuspendedFilter);
        notifPipeline.addPreGroupFilter(this.mDndVisualEffectsFilter);
    }

    public NotifSectioner getAlertingSectioner() {
        return this.mAlertingNotifSectioner;
    }

    public NotifSectioner getSilentSectioner() {
        return this.mSilentNotifSectioner;
    }
}

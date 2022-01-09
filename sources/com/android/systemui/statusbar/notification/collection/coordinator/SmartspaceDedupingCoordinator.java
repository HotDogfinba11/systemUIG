package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public final class SmartspaceDedupingCoordinator implements Coordinator {
    private final SystemClock clock;
    private final SmartspaceDedupingCoordinator$collectionListener$1 collectionListener = new SmartspaceDedupingCoordinator$collectionListener$1(this);
    private final DelayableExecutor executor;
    private final SmartspaceDedupingCoordinator$filter$1 filter = new SmartspaceDedupingCoordinator$filter$1(this);
    private boolean isOnLockscreen;
    private final NotifPipeline notifPipeline;
    private final NotificationEntryManager notificationEntryManager;
    private final NotificationLockscreenUserManager notificationLockscreenUserManager;
    private final LockscreenSmartspaceController smartspaceController;
    private final SysuiStatusBarStateController statusBarStateController;
    private final SmartspaceDedupingCoordinator$statusBarStateListener$1 statusBarStateListener = new SmartspaceDedupingCoordinator$statusBarStateListener$1(this);
    private Map<String, TrackedSmartspaceTarget> trackedSmartspaceTargets = new LinkedHashMap();

    public SmartspaceDedupingCoordinator(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenSmartspaceController lockscreenSmartspaceController, NotificationEntryManager notificationEntryManager2, NotificationLockscreenUserManager notificationLockscreenUserManager2, NotifPipeline notifPipeline2, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        Intrinsics.checkNotNullParameter(sysuiStatusBarStateController, "statusBarStateController");
        Intrinsics.checkNotNullParameter(lockscreenSmartspaceController, "smartspaceController");
        Intrinsics.checkNotNullParameter(notificationEntryManager2, "notificationEntryManager");
        Intrinsics.checkNotNullParameter(notificationLockscreenUserManager2, "notificationLockscreenUserManager");
        Intrinsics.checkNotNullParameter(notifPipeline2, "notifPipeline");
        Intrinsics.checkNotNullParameter(delayableExecutor, "executor");
        Intrinsics.checkNotNullParameter(systemClock, "clock");
        this.statusBarStateController = sysuiStatusBarStateController;
        this.smartspaceController = lockscreenSmartspaceController;
        this.notificationEntryManager = notificationEntryManager2;
        this.notificationLockscreenUserManager = notificationLockscreenUserManager2;
        this.notifPipeline = notifPipeline2;
        this.executor = delayableExecutor;
        this.clock = systemClock;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline2) {
        Intrinsics.checkNotNullParameter(notifPipeline2, "pipeline");
        notifPipeline2.addPreGroupFilter(this.filter);
        notifPipeline2.addCollectionListener(this.collectionListener);
        this.statusBarStateController.addCallback(this.statusBarStateListener);
        this.smartspaceController.addListener(new SmartspaceDedupingCoordinator$attach$1(this));
        this.notificationLockscreenUserManager.addKeyguardNotificationSuppressor(new SmartspaceDedupingCoordinator$attach$2(this));
        recordStatusBarState(this.statusBarStateController.getState());
    }

    /* access modifiers changed from: private */
    public final boolean isDupedWithSmartspaceContent(NotificationEntry notificationEntry) {
        TrackedSmartspaceTarget trackedSmartspaceTarget = this.trackedSmartspaceTargets.get(notificationEntry.getKey());
        if (trackedSmartspaceTarget == null) {
            return false;
        }
        return trackedSmartspaceTarget.getShouldFilter();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0071  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onNewSmartspaceTargets(java.util.List<? extends android.os.Parcelable> r6) {
        /*
        // Method dump skipped, instructions count: 128
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator.onNewSmartspaceTargets(java.util.List):void");
    }

    /* access modifiers changed from: private */
    public final boolean updateFilterStatus(TrackedSmartspaceTarget trackedSmartspaceTarget) {
        boolean shouldFilter = trackedSmartspaceTarget.getShouldFilter();
        NotificationEntry entry = this.notifPipeline.getEntry(trackedSmartspaceTarget.getKey());
        if (entry != null) {
            updateAlertException(trackedSmartspaceTarget, entry);
            trackedSmartspaceTarget.setShouldFilter(!hasRecentlyAlerted(entry));
        }
        if (trackedSmartspaceTarget.getShouldFilter() == shouldFilter || !this.isOnLockscreen) {
            return false;
        }
        return true;
    }

    private final void updateAlertException(TrackedSmartspaceTarget trackedSmartspaceTarget, NotificationEntry notificationEntry) {
        long currentTimeMillis = this.clock.currentTimeMillis();
        long lastAudiblyAlertedMillis = notificationEntry.getRanking().getLastAudiblyAlertedMillis() + SmartspaceDedupingCoordinatorKt.access$getALERT_WINDOW$p();
        if (lastAudiblyAlertedMillis != trackedSmartspaceTarget.getAlertExceptionExpires() && lastAudiblyAlertedMillis > currentTimeMillis) {
            Runnable cancelTimeoutRunnable = trackedSmartspaceTarget.getCancelTimeoutRunnable();
            if (cancelTimeoutRunnable != null) {
                cancelTimeoutRunnable.run();
            }
            trackedSmartspaceTarget.setAlertExceptionExpires(lastAudiblyAlertedMillis);
            trackedSmartspaceTarget.setCancelTimeoutRunnable(this.executor.executeDelayed(new SmartspaceDedupingCoordinator$updateAlertException$1(trackedSmartspaceTarget, this), lastAudiblyAlertedMillis - currentTimeMillis));
        }
    }

    /* access modifiers changed from: private */
    public final void cancelExceptionTimeout(TrackedSmartspaceTarget trackedSmartspaceTarget) {
        Runnable cancelTimeoutRunnable = trackedSmartspaceTarget.getCancelTimeoutRunnable();
        if (cancelTimeoutRunnable != null) {
            cancelTimeoutRunnable.run();
        }
        trackedSmartspaceTarget.setCancelTimeoutRunnable(null);
        trackedSmartspaceTarget.setAlertExceptionExpires(0);
    }

    /* access modifiers changed from: private */
    public final void recordStatusBarState(int i) {
        boolean z = this.isOnLockscreen;
        boolean z2 = true;
        if (i != 1) {
            z2 = false;
        }
        this.isOnLockscreen = z2;
        if (z2 != z) {
            this.filter.invalidateList();
        }
    }

    private final boolean hasRecentlyAlerted(NotificationEntry notificationEntry) {
        return this.clock.currentTimeMillis() - notificationEntry.getRanking().getLastAudiblyAlertedMillis() <= SmartspaceDedupingCoordinatorKt.access$getALERT_WINDOW$p();
    }
}

package com.android.systemui.statusbar.notification.init;

import android.service.notification.StatusBarNotification;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.FeatureFlags;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.NotificationListController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.NotificationRankingManager;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.interruption.HeadsUpController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.phone.NotificationGroupAlertTransferHelper;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Optional;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationsControllerImpl.kt */
public final class NotificationsControllerImpl implements NotificationsController {
    private final AnimatedImageNotificationManager animatedImageNotificationManager;
    private final NotificationClicker.Builder clickerBuilder;
    private final DeviceProvisionedController deviceProvisionedController;
    private final NotificationEntryManager entryManager;
    private final FeatureFlags featureFlags;
    private final NotificationGroupAlertTransferHelper groupAlertTransferHelper;
    private final Lazy<NotificationGroupManagerLegacy> groupManagerLegacy;
    private final HeadsUpController headsUpController;
    private final HeadsUpManager headsUpManager;
    private final HeadsUpViewBinder headsUpViewBinder;
    private final NotificationRankingManager legacyRanker;
    private final Lazy<NotifPipelineInitializer> newNotifPipeline;
    private final NotifBindPipelineInitializer notifBindPipelineInitializer;
    private final Lazy<NotifPipeline> notifPipeline;
    private final NotificationListener notificationListener;
    private final NotificationRowBinderImpl notificationRowBinder;
    private final PeopleSpaceWidgetManager peopleSpaceWidgetManager;
    private final RemoteInputUriController remoteInputUriController;
    private final TargetSdkResolver targetSdkResolver;

    public NotificationsControllerImpl(FeatureFlags featureFlags2, NotificationListener notificationListener2, NotificationEntryManager notificationEntryManager, NotificationRankingManager notificationRankingManager, Lazy<NotifPipeline> lazy, TargetSdkResolver targetSdkResolver2, Lazy<NotifPipelineInitializer> lazy2, NotifBindPipelineInitializer notifBindPipelineInitializer2, DeviceProvisionedController deviceProvisionedController2, NotificationRowBinderImpl notificationRowBinderImpl, RemoteInputUriController remoteInputUriController2, Lazy<NotificationGroupManagerLegacy> lazy3, NotificationGroupAlertTransferHelper notificationGroupAlertTransferHelper, HeadsUpManager headsUpManager2, HeadsUpController headsUpController2, HeadsUpViewBinder headsUpViewBinder2, NotificationClicker.Builder builder, AnimatedImageNotificationManager animatedImageNotificationManager2, PeopleSpaceWidgetManager peopleSpaceWidgetManager2) {
        Intrinsics.checkNotNullParameter(featureFlags2, "featureFlags");
        Intrinsics.checkNotNullParameter(notificationListener2, "notificationListener");
        Intrinsics.checkNotNullParameter(notificationEntryManager, "entryManager");
        Intrinsics.checkNotNullParameter(notificationRankingManager, "legacyRanker");
        Intrinsics.checkNotNullParameter(lazy, "notifPipeline");
        Intrinsics.checkNotNullParameter(targetSdkResolver2, "targetSdkResolver");
        Intrinsics.checkNotNullParameter(lazy2, "newNotifPipeline");
        Intrinsics.checkNotNullParameter(notifBindPipelineInitializer2, "notifBindPipelineInitializer");
        Intrinsics.checkNotNullParameter(deviceProvisionedController2, "deviceProvisionedController");
        Intrinsics.checkNotNullParameter(notificationRowBinderImpl, "notificationRowBinder");
        Intrinsics.checkNotNullParameter(remoteInputUriController2, "remoteInputUriController");
        Intrinsics.checkNotNullParameter(lazy3, "groupManagerLegacy");
        Intrinsics.checkNotNullParameter(notificationGroupAlertTransferHelper, "groupAlertTransferHelper");
        Intrinsics.checkNotNullParameter(headsUpManager2, "headsUpManager");
        Intrinsics.checkNotNullParameter(headsUpController2, "headsUpController");
        Intrinsics.checkNotNullParameter(headsUpViewBinder2, "headsUpViewBinder");
        Intrinsics.checkNotNullParameter(builder, "clickerBuilder");
        Intrinsics.checkNotNullParameter(animatedImageNotificationManager2, "animatedImageNotificationManager");
        Intrinsics.checkNotNullParameter(peopleSpaceWidgetManager2, "peopleSpaceWidgetManager");
        this.featureFlags = featureFlags2;
        this.notificationListener = notificationListener2;
        this.entryManager = notificationEntryManager;
        this.legacyRanker = notificationRankingManager;
        this.notifPipeline = lazy;
        this.targetSdkResolver = targetSdkResolver2;
        this.newNotifPipeline = lazy2;
        this.notifBindPipelineInitializer = notifBindPipelineInitializer2;
        this.deviceProvisionedController = deviceProvisionedController2;
        this.notificationRowBinder = notificationRowBinderImpl;
        this.remoteInputUriController = remoteInputUriController2;
        this.groupManagerLegacy = lazy3;
        this.groupAlertTransferHelper = notificationGroupAlertTransferHelper;
        this.headsUpManager = headsUpManager2;
        this.headsUpController = headsUpController2;
        this.headsUpViewBinder = headsUpViewBinder2;
        this.clickerBuilder = builder;
        this.animatedImageNotificationManager = animatedImageNotificationManager2;
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager2;
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void initialize(StatusBar statusBar, Optional<Bubbles> optional, NotificationPresenter notificationPresenter, NotificationListContainer notificationListContainer, NotificationActivityStarter notificationActivityStarter, NotificationRowBinderImpl.BindRowCallback bindRowCallback) {
        Intrinsics.checkNotNullParameter(statusBar, "statusBar");
        Intrinsics.checkNotNullParameter(optional, "bubblesOptional");
        Intrinsics.checkNotNullParameter(notificationPresenter, "presenter");
        Intrinsics.checkNotNullParameter(notificationListContainer, "listContainer");
        Intrinsics.checkNotNullParameter(notificationActivityStarter, "notificationActivityStarter");
        Intrinsics.checkNotNullParameter(bindRowCallback, "bindRowCallback");
        this.notificationListener.registerAsSystemService();
        new NotificationListController(this.entryManager, notificationListContainer, this.deviceProvisionedController).bind();
        this.notificationRowBinder.setNotificationClicker(this.clickerBuilder.build(Optional.of(statusBar), optional, notificationActivityStarter));
        this.notificationRowBinder.setUpWithPresenter(notificationPresenter, notificationListContainer, bindRowCallback);
        this.headsUpViewBinder.setPresenter(notificationPresenter);
        this.notifBindPipelineInitializer.initialize();
        this.animatedImageNotificationManager.bind();
        if (this.featureFlags.isNewNotifPipelineEnabled()) {
            this.newNotifPipeline.get().initialize(this.notificationListener, this.notificationRowBinder, notificationListContainer);
        }
        if (this.featureFlags.isNewNotifPipelineRenderingEnabled()) {
            TargetSdkResolver targetSdkResolver2 = this.targetSdkResolver;
            NotifPipeline notifPipeline2 = this.notifPipeline.get();
            Intrinsics.checkNotNullExpressionValue(notifPipeline2, "notifPipeline.get()");
            targetSdkResolver2.initialize(notifPipeline2);
        } else {
            this.targetSdkResolver.initialize(this.entryManager);
            this.remoteInputUriController.attach(this.entryManager);
            this.groupAlertTransferHelper.bind(this.entryManager, this.groupManagerLegacy.get());
            this.headsUpManager.addListener(this.groupManagerLegacy.get());
            this.headsUpManager.addListener(this.groupAlertTransferHelper);
            this.headsUpController.attach(this.entryManager, this.headsUpManager);
            this.groupManagerLegacy.get().setHeadsUpManager(this.headsUpManager);
            this.groupAlertTransferHelper.setHeadsUpManager(this.headsUpManager);
            this.entryManager.setRanker(this.legacyRanker);
            this.entryManager.attach(this.notificationListener);
        }
        this.peopleSpaceWidgetManager.attach(this.notificationListener);
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        if (z) {
            this.entryManager.dump(printWriter, "  ");
        }
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void requestNotificationUpdate(String str) {
        Intrinsics.checkNotNullParameter(str, "reason");
        this.entryManager.updateNotifications(str);
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void resetUserExpandedStates() {
        for (NotificationEntry notificationEntry : this.entryManager.getVisibleNotifications()) {
            notificationEntry.resetUserExpansion();
        }
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void setNotificationSnoozed(StatusBarNotification statusBarNotification, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        Intrinsics.checkNotNullParameter(statusBarNotification, "sbn");
        Intrinsics.checkNotNullParameter(snoozeOption, "snoozeOption");
        if (snoozeOption.getSnoozeCriterion() != null) {
            this.notificationListener.snoozeNotification(statusBarNotification.getKey(), snoozeOption.getSnoozeCriterion().getId());
        } else {
            this.notificationListener.snoozeNotification(statusBarNotification.getKey(), ((long) (snoozeOption.getMinutesToSnoozeFor() * 60)) * 1000);
        }
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public int getActiveNotificationsCount() {
        return this.entryManager.getActiveNotificationsCount();
    }
}

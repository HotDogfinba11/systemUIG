package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import android.view.LayoutInflater;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.media.KeyguardMediaController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.FeatureFlags;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.ForegroundServiceDismissalFeatureController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.tuner.TunerService;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationStackScrollLayoutController_Factory implements Factory<NotificationStackScrollLayoutController> {
    private final Provider<Boolean> allowLongPressProvider;
    private final Provider<SysuiColorExtractor> colorExtractorProvider;
    private final Provider<ConfigurationController> configurationControllerProvider;
    private final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    private final Provider<DynamicPrivacyController> dynamicPrivacyControllerProvider;
    private final Provider<FalsingCollector> falsingCollectorProvider;
    private final Provider<FalsingManager> falsingManagerProvider;
    private final Provider<FeatureFlags> featureFlagsProvider;
    private final Provider<ForegroundServiceDismissalFeatureController> fgFeatureControllerProvider;
    private final Provider<ForegroundServiceSectionController> fgServicesSectionControllerProvider;
    private final Provider<GroupExpansionManager> groupManagerProvider;
    private final Provider<HeadsUpManagerPhone> headsUpManagerProvider;
    private final Provider<IStatusBarService> iStatusBarServiceProvider;
    private final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    private final Provider<KeyguardMediaController> keyguardMediaControllerProvider;
    private final Provider<LayoutInflater> layoutInflaterProvider;
    private final Provider<NotificationGroupManagerLegacy> legacyGroupManagerProvider;
    private final Provider<LockscreenShadeTransitionController> lockscreenShadeTransitionControllerProvider;
    private final Provider<NotificationLockscreenUserManager> lockscreenUserManagerProvider;
    private final Provider<MetricsLogger> metricsLoggerProvider;
    private final Provider<NotifCollection> notifCollectionProvider;
    private final Provider<NotifPipeline> notifPipelineProvider;
    private final Provider<NotificationEntryManager> notificationEntryManagerProvider;
    private final Provider<NotificationGutsManager> notificationGutsManagerProvider;
    private final Provider<NotificationRoundnessManager> notificationRoundnessManagerProvider;
    private final Provider<NotificationSwipeHelper.Builder> notificationSwipeHelperBuilderProvider;
    private final Provider<NotificationRemoteInputManager> remoteInputManagerProvider;
    private final Provider<Resources> resourcesProvider;
    private final Provider<ScrimController> scrimControllerProvider;
    private final Provider<ShadeController> shadeControllerProvider;
    private final Provider<SectionHeaderController> silentHeaderControllerProvider;
    private final Provider<StatusBar> statusBarProvider;
    private final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    private final Provider<TunerService> tunerServiceProvider;
    private final Provider<UiEventLogger> uiEventLoggerProvider;
    private final Provider<VisualStabilityManager> visualStabilityManagerProvider;
    private final Provider<ZenModeController> zenModeControllerProvider;

    public NotificationStackScrollLayoutController_Factory(Provider<Boolean> provider, Provider<NotificationGutsManager> provider2, Provider<HeadsUpManagerPhone> provider3, Provider<NotificationRoundnessManager> provider4, Provider<TunerService> provider5, Provider<DeviceProvisionedController> provider6, Provider<DynamicPrivacyController> provider7, Provider<ConfigurationController> provider8, Provider<SysuiStatusBarStateController> provider9, Provider<KeyguardMediaController> provider10, Provider<KeyguardBypassController> provider11, Provider<ZenModeController> provider12, Provider<SysuiColorExtractor> provider13, Provider<NotificationLockscreenUserManager> provider14, Provider<MetricsLogger> provider15, Provider<FalsingCollector> provider16, Provider<FalsingManager> provider17, Provider<Resources> provider18, Provider<NotificationSwipeHelper.Builder> provider19, Provider<StatusBar> provider20, Provider<ScrimController> provider21, Provider<NotificationGroupManagerLegacy> provider22, Provider<GroupExpansionManager> provider23, Provider<SectionHeaderController> provider24, Provider<FeatureFlags> provider25, Provider<NotifPipeline> provider26, Provider<NotifCollection> provider27, Provider<NotificationEntryManager> provider28, Provider<LockscreenShadeTransitionController> provider29, Provider<IStatusBarService> provider30, Provider<UiEventLogger> provider31, Provider<ForegroundServiceDismissalFeatureController> provider32, Provider<ForegroundServiceSectionController> provider33, Provider<LayoutInflater> provider34, Provider<NotificationRemoteInputManager> provider35, Provider<VisualStabilityManager> provider36, Provider<ShadeController> provider37) {
        this.allowLongPressProvider = provider;
        this.notificationGutsManagerProvider = provider2;
        this.headsUpManagerProvider = provider3;
        this.notificationRoundnessManagerProvider = provider4;
        this.tunerServiceProvider = provider5;
        this.deviceProvisionedControllerProvider = provider6;
        this.dynamicPrivacyControllerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.statusBarStateControllerProvider = provider9;
        this.keyguardMediaControllerProvider = provider10;
        this.keyguardBypassControllerProvider = provider11;
        this.zenModeControllerProvider = provider12;
        this.colorExtractorProvider = provider13;
        this.lockscreenUserManagerProvider = provider14;
        this.metricsLoggerProvider = provider15;
        this.falsingCollectorProvider = provider16;
        this.falsingManagerProvider = provider17;
        this.resourcesProvider = provider18;
        this.notificationSwipeHelperBuilderProvider = provider19;
        this.statusBarProvider = provider20;
        this.scrimControllerProvider = provider21;
        this.legacyGroupManagerProvider = provider22;
        this.groupManagerProvider = provider23;
        this.silentHeaderControllerProvider = provider24;
        this.featureFlagsProvider = provider25;
        this.notifPipelineProvider = provider26;
        this.notifCollectionProvider = provider27;
        this.notificationEntryManagerProvider = provider28;
        this.lockscreenShadeTransitionControllerProvider = provider29;
        this.iStatusBarServiceProvider = provider30;
        this.uiEventLoggerProvider = provider31;
        this.fgFeatureControllerProvider = provider32;
        this.fgServicesSectionControllerProvider = provider33;
        this.layoutInflaterProvider = provider34;
        this.remoteInputManagerProvider = provider35;
        this.visualStabilityManagerProvider = provider36;
        this.shadeControllerProvider = provider37;
    }

    @Override // javax.inject.Provider
    public NotificationStackScrollLayoutController get() {
        return newInstance(this.allowLongPressProvider.get().booleanValue(), this.notificationGutsManagerProvider.get(), this.headsUpManagerProvider.get(), this.notificationRoundnessManagerProvider.get(), this.tunerServiceProvider.get(), this.deviceProvisionedControllerProvider.get(), this.dynamicPrivacyControllerProvider.get(), this.configurationControllerProvider.get(), this.statusBarStateControllerProvider.get(), this.keyguardMediaControllerProvider.get(), this.keyguardBypassControllerProvider.get(), this.zenModeControllerProvider.get(), this.colorExtractorProvider.get(), this.lockscreenUserManagerProvider.get(), this.metricsLoggerProvider.get(), this.falsingCollectorProvider.get(), this.falsingManagerProvider.get(), this.resourcesProvider.get(), this.notificationSwipeHelperBuilderProvider.get(), this.statusBarProvider.get(), this.scrimControllerProvider.get(), this.legacyGroupManagerProvider.get(), this.groupManagerProvider.get(), this.silentHeaderControllerProvider.get(), this.featureFlagsProvider.get(), this.notifPipelineProvider.get(), this.notifCollectionProvider.get(), this.notificationEntryManagerProvider.get(), this.lockscreenShadeTransitionControllerProvider.get(), this.iStatusBarServiceProvider.get(), this.uiEventLoggerProvider.get(), this.fgFeatureControllerProvider.get(), this.fgServicesSectionControllerProvider.get(), this.layoutInflaterProvider.get(), this.remoteInputManagerProvider.get(), this.visualStabilityManagerProvider.get(), this.shadeControllerProvider.get());
    }

    public static NotificationStackScrollLayoutController_Factory create(Provider<Boolean> provider, Provider<NotificationGutsManager> provider2, Provider<HeadsUpManagerPhone> provider3, Provider<NotificationRoundnessManager> provider4, Provider<TunerService> provider5, Provider<DeviceProvisionedController> provider6, Provider<DynamicPrivacyController> provider7, Provider<ConfigurationController> provider8, Provider<SysuiStatusBarStateController> provider9, Provider<KeyguardMediaController> provider10, Provider<KeyguardBypassController> provider11, Provider<ZenModeController> provider12, Provider<SysuiColorExtractor> provider13, Provider<NotificationLockscreenUserManager> provider14, Provider<MetricsLogger> provider15, Provider<FalsingCollector> provider16, Provider<FalsingManager> provider17, Provider<Resources> provider18, Provider<NotificationSwipeHelper.Builder> provider19, Provider<StatusBar> provider20, Provider<ScrimController> provider21, Provider<NotificationGroupManagerLegacy> provider22, Provider<GroupExpansionManager> provider23, Provider<SectionHeaderController> provider24, Provider<FeatureFlags> provider25, Provider<NotifPipeline> provider26, Provider<NotifCollection> provider27, Provider<NotificationEntryManager> provider28, Provider<LockscreenShadeTransitionController> provider29, Provider<IStatusBarService> provider30, Provider<UiEventLogger> provider31, Provider<ForegroundServiceDismissalFeatureController> provider32, Provider<ForegroundServiceSectionController> provider33, Provider<LayoutInflater> provider34, Provider<NotificationRemoteInputManager> provider35, Provider<VisualStabilityManager> provider36, Provider<ShadeController> provider37) {
        return new NotificationStackScrollLayoutController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31, provider32, provider33, provider34, provider35, provider36, provider37);
    }

    public static NotificationStackScrollLayoutController newInstance(boolean z, NotificationGutsManager notificationGutsManager, HeadsUpManagerPhone headsUpManagerPhone, NotificationRoundnessManager notificationRoundnessManager, TunerService tunerService, DeviceProvisionedController deviceProvisionedController, DynamicPrivacyController dynamicPrivacyController, ConfigurationController configurationController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardMediaController keyguardMediaController, KeyguardBypassController keyguardBypassController, ZenModeController zenModeController, SysuiColorExtractor sysuiColorExtractor, NotificationLockscreenUserManager notificationLockscreenUserManager, MetricsLogger metricsLogger, FalsingCollector falsingCollector, FalsingManager falsingManager, Resources resources, Object obj, StatusBar statusBar, ScrimController scrimController, NotificationGroupManagerLegacy notificationGroupManagerLegacy, GroupExpansionManager groupExpansionManager, SectionHeaderController sectionHeaderController, FeatureFlags featureFlags, NotifPipeline notifPipeline, NotifCollection notifCollection, NotificationEntryManager notificationEntryManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, IStatusBarService iStatusBarService, UiEventLogger uiEventLogger, ForegroundServiceDismissalFeatureController foregroundServiceDismissalFeatureController, ForegroundServiceSectionController foregroundServiceSectionController, LayoutInflater layoutInflater, NotificationRemoteInputManager notificationRemoteInputManager, VisualStabilityManager visualStabilityManager, ShadeController shadeController) {
        return new NotificationStackScrollLayoutController(z, notificationGutsManager, headsUpManagerPhone, notificationRoundnessManager, tunerService, deviceProvisionedController, dynamicPrivacyController, configurationController, sysuiStatusBarStateController, keyguardMediaController, keyguardBypassController, zenModeController, sysuiColorExtractor, notificationLockscreenUserManager, metricsLogger, falsingCollector, falsingManager, resources, (NotificationSwipeHelper.Builder) obj, statusBar, scrimController, notificationGroupManagerLegacy, groupExpansionManager, sectionHeaderController, featureFlags, notifPipeline, notifCollection, notificationEntryManager, lockscreenShadeTransitionController, iStatusBarService, uiEventLogger, foregroundServiceDismissalFeatureController, foregroundServiceSectionController, layoutInflater, notificationRemoteInputManager, visualStabilityManager, shadeController);
    }
}

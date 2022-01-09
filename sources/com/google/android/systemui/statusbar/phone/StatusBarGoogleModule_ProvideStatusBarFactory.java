package com.google.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.InitController;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.recents.ScreenPinningRequest;
import com.android.systemui.settings.brightness.BrightnessSlider;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.FeatureFlags;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.SuperStatusBarViewFactory;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.charging.WiredChargingRippleController;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.BypassHeadsUpNotifier;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.KeyguardLiftController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightsOutNotifController;
import com.android.systemui.statusbar.phone.LockscreenWallpaper;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.dagger.StatusBarComponent;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.wm.shell.startingsurface.StartingSurface;
import com.google.android.systemui.LiveWallpaperScrimController;
import com.google.android.systemui.reversecharging.ReverseChargingViewController;
import com.google.android.systemui.smartspace.SmartSpaceController;
import com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyClient;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class StatusBarGoogleModule_ProvideStatusBarFactory implements Factory<StatusBarGoogle> {
    private final Provider<AccessibilityFloatingMenuController> accessibilityFloatingMenuControllerProvider;
    private final Provider<SystemStatusAnimationScheduler> animationSchedulerProvider;
    private final Provider<AssistManager> assistManagerLazyProvider;
    private final Provider<AutoHideController> autoHideControllerProvider;
    private final Provider<BatteryController> batteryControllerProvider;
    private final Provider<BiometricUnlockController> biometricUnlockControllerLazyProvider;
    private final Provider<BrightnessSlider.Factory> brightnessSliderFactoryProvider;
    private final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    private final Provider<Optional<BubblesManager>> bubblesManagerOptionalProvider;
    private final Provider<Optional<Bubbles>> bubblesOptionalProvider;
    private final Provider<BypassHeadsUpNotifier> bypassHeadsUpNotifierProvider;
    private final Provider<SysuiColorExtractor> colorExtractorProvider;
    private final Provider<CommandQueue> commandQueueProvider;
    private final Provider<ConfigurationController> configurationControllerProvider;
    private final Provider<Context> contextProvider;
    private final Provider<DemoModeController> demoModeControllerProvider;
    private final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    private final Provider<DismissCallbackRegistry> dismissCallbackRegistryProvider;
    private final Provider<DisplayMetrics> displayMetricsProvider;
    private final Provider<DozeParameters> dozeParametersProvider;
    private final Provider<DozeScrimController> dozeScrimControllerProvider;
    private final Provider<DozeServiceHost> dozeServiceHostProvider;
    private final Provider<DynamicPrivacyController> dynamicPrivacyControllerProvider;
    private final Provider<ExtensionController> extensionControllerProvider;
    private final Provider<FalsingCollector> falsingCollectorProvider;
    private final Provider<FalsingManager> falsingManagerProvider;
    private final Provider<FeatureFlags> featureFlagsProvider;
    private final Provider<HeadsUpManagerPhone> headsUpManagerPhoneProvider;
    private final Provider<InitController> initControllerProvider;
    private final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    private final Provider<KeyguardDismissUtil> keyguardDismissUtilProvider;
    private final Provider<KeyguardIndicationControllerGoogle> keyguardIndicationControllerProvider;
    private final Provider<KeyguardLiftController> keyguardLiftControllerProvider;
    private final Provider<KeyguardStateController> keyguardStateControllerProvider;
    private final Provider<KeyguardUnlockAnimationController> keyguardUnlockAnimationControllerProvider;
    private final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    private final Provider<KeyguardViewMediator> keyguardViewMediatorProvider;
    private final Provider<LightBarController> lightBarControllerProvider;
    private final Provider<LightsOutNotifController> lightsOutNotifControllerProvider;
    private final Provider<StatusBarLocationPublisher> locationPublisherProvider;
    private final Provider<NotificationLockscreenUserManager> lockScreenUserManagerProvider;
    private final Provider<LockscreenShadeTransitionController> lockscreenShadeTransitionControllerProvider;
    private final Provider<LockscreenWallpaper> lockscreenWallpaperLazyProvider;
    private final Provider<MetricsLogger> metricsLoggerProvider;
    private final Provider<NavigationBarController> navigationBarControllerProvider;
    private final Provider<NetworkController> networkControllerProvider;
    private final Provider<NotificationGutsManager> notificationGutsManagerProvider;
    private final Provider<NotificationIconAreaController> notificationIconAreaControllerProvider;
    private final Provider<NotificationInterruptStateProvider> notificationInterruptionStateProvider;
    private final Provider<NotificationLogger> notificationLoggerProvider;
    private final Provider<NotificationMediaManager> notificationMediaManagerProvider;
    private final Provider<NotificationShadeDepthController> notificationShadeDepthControllerLazyProvider;
    private final Provider<NotificationShadeWindowController> notificationShadeWindowControllerProvider;
    private final Provider<NotificationViewHierarchyManager> notificationViewHierarchyManagerProvider;
    private final Provider<NotificationWakeUpCoordinator> notificationWakeUpCoordinatorProvider;
    private final Provider<NotificationsController> notificationsControllerProvider;
    private final Provider<OngoingCallController> ongoingCallControllerProvider;
    private final Provider<PhoneStatusBarPolicy> phoneStatusBarPolicyProvider;
    private final Provider<PluginDependencyProvider> pluginDependencyProvider;
    private final Provider<PluginManager> pluginManagerProvider;
    private final Provider<PowerManager> powerManagerProvider;
    private final Provider<PulseExpansionHandler> pulseExpansionHandlerProvider;
    private final Provider<NotificationRemoteInputManager> remoteInputManagerProvider;
    private final Provider<RemoteInputQuickSettingsDisabler> remoteInputQuickSettingsDisablerProvider;
    private final Provider<Optional<ReverseChargingViewController>> reverseChargingViewControllerOptionalProvider;
    private final Provider<ScreenLifecycle> screenLifecycleProvider;
    private final Provider<ScreenPinningRequest> screenPinningRequestProvider;
    private final Provider<LiveWallpaperScrimController> scrimControllerProvider;
    private final Provider<ShadeController> shadeControllerProvider;
    private final Provider<StatusBarSignalPolicy> signalPolicyProvider;
    private final Provider<SmartSpaceController> smartSpaceControllerProvider;
    private final Provider<Optional<LegacySplitScreen>> splitScreenOptionalProvider;
    private final Provider<Optional<StartingSurface>> startingSurfaceOptionalProvider;
    private final Provider<StatusBarComponent.Builder> statusBarComponentBuilderProvider;
    private final Provider<StatusBarIconController> statusBarIconControllerProvider;
    private final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    private final Provider<StatusBarNotificationActivityStarter.Builder> statusBarNotificationActivityStarterBuilderProvider;
    private final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    private final Provider<StatusBarTouchableRegionManager> statusBarTouchableRegionManagerProvider;
    private final Provider<SuperStatusBarViewFactory> superStatusBarViewFactoryProvider;
    private final Provider<Handler> timeTickHandlerProvider;
    private final Provider<Executor> uiBgExecutorProvider;
    private final Provider<UnlockedScreenOffAnimationController> unlockedScreenOffAnimationControllerProvider;
    private final Provider<UserInfoControllerImpl> userInfoControllerImplProvider;
    private final Provider<UserSwitcherController> userSwitcherControllerProvider;
    private final Provider<VibratorHelper> vibratorHelperProvider;
    private final Provider<ViewMediatorCallback> viewMediatorCallbackProvider;
    private final Provider<VisualStabilityManager> visualStabilityManagerProvider;
    private final Provider<Optional<NotificationVoiceReplyClient>> voiceReplyClientProvider;
    private final Provider<VolumeComponent> volumeComponentProvider;
    private final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
    private final Provider<WallpaperNotifier> wallpaperNotifierProvider;
    private final Provider<WiredChargingRippleController> wiredChargingRippleControllerProvider;

    public StatusBarGoogleModule_ProvideStatusBarFactory(Provider<SmartSpaceController> provider, Provider<WallpaperNotifier> provider2, Provider<Optional<ReverseChargingViewController>> provider3, Provider<Context> provider4, Provider<NotificationsController> provider5, Provider<LightBarController> provider6, Provider<AutoHideController> provider7, Provider<KeyguardUpdateMonitor> provider8, Provider<StatusBarSignalPolicy> provider9, Provider<PulseExpansionHandler> provider10, Provider<NotificationWakeUpCoordinator> provider11, Provider<KeyguardBypassController> provider12, Provider<KeyguardStateController> provider13, Provider<HeadsUpManagerPhone> provider14, Provider<DynamicPrivacyController> provider15, Provider<BypassHeadsUpNotifier> provider16, Provider<FalsingManager> provider17, Provider<FalsingCollector> provider18, Provider<BroadcastDispatcher> provider19, Provider<RemoteInputQuickSettingsDisabler> provider20, Provider<NotificationGutsManager> provider21, Provider<NotificationLogger> provider22, Provider<NotificationInterruptStateProvider> provider23, Provider<NotificationViewHierarchyManager> provider24, Provider<KeyguardViewMediator> provider25, Provider<DisplayMetrics> provider26, Provider<MetricsLogger> provider27, Provider<Executor> provider28, Provider<NotificationMediaManager> provider29, Provider<NotificationLockscreenUserManager> provider30, Provider<NotificationRemoteInputManager> provider31, Provider<UserSwitcherController> provider32, Provider<NetworkController> provider33, Provider<BatteryController> provider34, Provider<SysuiColorExtractor> provider35, Provider<ScreenLifecycle> provider36, Provider<WakefulnessLifecycle> provider37, Provider<SysuiStatusBarStateController> provider38, Provider<VibratorHelper> provider39, Provider<Optional<BubblesManager>> provider40, Provider<Optional<Bubbles>> provider41, Provider<VisualStabilityManager> provider42, Provider<DeviceProvisionedController> provider43, Provider<NavigationBarController> provider44, Provider<AccessibilityFloatingMenuController> provider45, Provider<AssistManager> provider46, Provider<ConfigurationController> provider47, Provider<NotificationShadeWindowController> provider48, Provider<DozeParameters> provider49, Provider<LiveWallpaperScrimController> provider50, Provider<KeyguardLiftController> provider51, Provider<LockscreenWallpaper> provider52, Provider<BiometricUnlockController> provider53, Provider<NotificationShadeDepthController> provider54, Provider<DozeServiceHost> provider55, Provider<PowerManager> provider56, Provider<ScreenPinningRequest> provider57, Provider<DozeScrimController> provider58, Provider<VolumeComponent> provider59, Provider<CommandQueue> provider60, Provider<StatusBarComponent.Builder> provider61, Provider<PluginManager> provider62, Provider<Optional<LegacySplitScreen>> provider63, Provider<LightsOutNotifController> provider64, Provider<StatusBarNotificationActivityStarter.Builder> provider65, Provider<ShadeController> provider66, Provider<SuperStatusBarViewFactory> provider67, Provider<StatusBarKeyguardViewManager> provider68, Provider<ViewMediatorCallback> provider69, Provider<InitController> provider70, Provider<Handler> provider71, Provider<PluginDependencyProvider> provider72, Provider<KeyguardDismissUtil> provider73, Provider<ExtensionController> provider74, Provider<UserInfoControllerImpl> provider75, Provider<PhoneStatusBarPolicy> provider76, Provider<KeyguardIndicationControllerGoogle> provider77, Provider<DismissCallbackRegistry> provider78, Provider<DemoModeController> provider79, Provider<StatusBarTouchableRegionManager> provider80, Provider<NotificationIconAreaController> provider81, Provider<BrightnessSlider.Factory> provider82, Provider<WiredChargingRippleController> provider83, Provider<OngoingCallController> provider84, Provider<SystemStatusAnimationScheduler> provider85, Provider<StatusBarLocationPublisher> provider86, Provider<StatusBarIconController> provider87, Provider<LockscreenShadeTransitionController> provider88, Provider<FeatureFlags> provider89, Provider<Optional<NotificationVoiceReplyClient>> provider90, Provider<KeyguardUnlockAnimationController> provider91, Provider<UnlockedScreenOffAnimationController> provider92, Provider<Optional<StartingSurface>> provider93) {
        this.smartSpaceControllerProvider = provider;
        this.wallpaperNotifierProvider = provider2;
        this.reverseChargingViewControllerOptionalProvider = provider3;
        this.contextProvider = provider4;
        this.notificationsControllerProvider = provider5;
        this.lightBarControllerProvider = provider6;
        this.autoHideControllerProvider = provider7;
        this.keyguardUpdateMonitorProvider = provider8;
        this.signalPolicyProvider = provider9;
        this.pulseExpansionHandlerProvider = provider10;
        this.notificationWakeUpCoordinatorProvider = provider11;
        this.keyguardBypassControllerProvider = provider12;
        this.keyguardStateControllerProvider = provider13;
        this.headsUpManagerPhoneProvider = provider14;
        this.dynamicPrivacyControllerProvider = provider15;
        this.bypassHeadsUpNotifierProvider = provider16;
        this.falsingManagerProvider = provider17;
        this.falsingCollectorProvider = provider18;
        this.broadcastDispatcherProvider = provider19;
        this.remoteInputQuickSettingsDisablerProvider = provider20;
        this.notificationGutsManagerProvider = provider21;
        this.notificationLoggerProvider = provider22;
        this.notificationInterruptionStateProvider = provider23;
        this.notificationViewHierarchyManagerProvider = provider24;
        this.keyguardViewMediatorProvider = provider25;
        this.displayMetricsProvider = provider26;
        this.metricsLoggerProvider = provider27;
        this.uiBgExecutorProvider = provider28;
        this.notificationMediaManagerProvider = provider29;
        this.lockScreenUserManagerProvider = provider30;
        this.remoteInputManagerProvider = provider31;
        this.userSwitcherControllerProvider = provider32;
        this.networkControllerProvider = provider33;
        this.batteryControllerProvider = provider34;
        this.colorExtractorProvider = provider35;
        this.screenLifecycleProvider = provider36;
        this.wakefulnessLifecycleProvider = provider37;
        this.statusBarStateControllerProvider = provider38;
        this.vibratorHelperProvider = provider39;
        this.bubblesManagerOptionalProvider = provider40;
        this.bubblesOptionalProvider = provider41;
        this.visualStabilityManagerProvider = provider42;
        this.deviceProvisionedControllerProvider = provider43;
        this.navigationBarControllerProvider = provider44;
        this.accessibilityFloatingMenuControllerProvider = provider45;
        this.assistManagerLazyProvider = provider46;
        this.configurationControllerProvider = provider47;
        this.notificationShadeWindowControllerProvider = provider48;
        this.dozeParametersProvider = provider49;
        this.scrimControllerProvider = provider50;
        this.keyguardLiftControllerProvider = provider51;
        this.lockscreenWallpaperLazyProvider = provider52;
        this.biometricUnlockControllerLazyProvider = provider53;
        this.notificationShadeDepthControllerLazyProvider = provider54;
        this.dozeServiceHostProvider = provider55;
        this.powerManagerProvider = provider56;
        this.screenPinningRequestProvider = provider57;
        this.dozeScrimControllerProvider = provider58;
        this.volumeComponentProvider = provider59;
        this.commandQueueProvider = provider60;
        this.statusBarComponentBuilderProvider = provider61;
        this.pluginManagerProvider = provider62;
        this.splitScreenOptionalProvider = provider63;
        this.lightsOutNotifControllerProvider = provider64;
        this.statusBarNotificationActivityStarterBuilderProvider = provider65;
        this.shadeControllerProvider = provider66;
        this.superStatusBarViewFactoryProvider = provider67;
        this.statusBarKeyguardViewManagerProvider = provider68;
        this.viewMediatorCallbackProvider = provider69;
        this.initControllerProvider = provider70;
        this.timeTickHandlerProvider = provider71;
        this.pluginDependencyProvider = provider72;
        this.keyguardDismissUtilProvider = provider73;
        this.extensionControllerProvider = provider74;
        this.userInfoControllerImplProvider = provider75;
        this.phoneStatusBarPolicyProvider = provider76;
        this.keyguardIndicationControllerProvider = provider77;
        this.dismissCallbackRegistryProvider = provider78;
        this.demoModeControllerProvider = provider79;
        this.statusBarTouchableRegionManagerProvider = provider80;
        this.notificationIconAreaControllerProvider = provider81;
        this.brightnessSliderFactoryProvider = provider82;
        this.wiredChargingRippleControllerProvider = provider83;
        this.ongoingCallControllerProvider = provider84;
        this.animationSchedulerProvider = provider85;
        this.locationPublisherProvider = provider86;
        this.statusBarIconControllerProvider = provider87;
        this.lockscreenShadeTransitionControllerProvider = provider88;
        this.featureFlagsProvider = provider89;
        this.voiceReplyClientProvider = provider90;
        this.keyguardUnlockAnimationControllerProvider = provider91;
        this.unlockedScreenOffAnimationControllerProvider = provider92;
        this.startingSurfaceOptionalProvider = provider93;
    }

    @Override // javax.inject.Provider
    public StatusBarGoogle get() {
        return provideStatusBar(this.smartSpaceControllerProvider.get(), this.wallpaperNotifierProvider.get(), this.reverseChargingViewControllerOptionalProvider.get(), this.contextProvider.get(), this.notificationsControllerProvider.get(), this.lightBarControllerProvider.get(), this.autoHideControllerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.signalPolicyProvider.get(), this.pulseExpansionHandlerProvider.get(), this.notificationWakeUpCoordinatorProvider.get(), this.keyguardBypassControllerProvider.get(), this.keyguardStateControllerProvider.get(), this.headsUpManagerPhoneProvider.get(), this.dynamicPrivacyControllerProvider.get(), this.bypassHeadsUpNotifierProvider.get(), this.falsingManagerProvider.get(), this.falsingCollectorProvider.get(), this.broadcastDispatcherProvider.get(), this.remoteInputQuickSettingsDisablerProvider.get(), this.notificationGutsManagerProvider.get(), this.notificationLoggerProvider.get(), this.notificationInterruptionStateProvider.get(), this.notificationViewHierarchyManagerProvider.get(), this.keyguardViewMediatorProvider.get(), this.displayMetricsProvider.get(), this.metricsLoggerProvider.get(), this.uiBgExecutorProvider.get(), this.notificationMediaManagerProvider.get(), this.lockScreenUserManagerProvider.get(), this.remoteInputManagerProvider.get(), this.userSwitcherControllerProvider.get(), this.networkControllerProvider.get(), this.batteryControllerProvider.get(), this.colorExtractorProvider.get(), this.screenLifecycleProvider.get(), this.wakefulnessLifecycleProvider.get(), this.statusBarStateControllerProvider.get(), this.vibratorHelperProvider.get(), this.bubblesManagerOptionalProvider.get(), this.bubblesOptionalProvider.get(), this.visualStabilityManagerProvider.get(), this.deviceProvisionedControllerProvider.get(), this.navigationBarControllerProvider.get(), this.accessibilityFloatingMenuControllerProvider.get(), DoubleCheck.lazy(this.assistManagerLazyProvider), this.configurationControllerProvider.get(), this.notificationShadeWindowControllerProvider.get(), this.dozeParametersProvider.get(), this.scrimControllerProvider.get(), this.keyguardLiftControllerProvider.get(), DoubleCheck.lazy(this.lockscreenWallpaperLazyProvider), DoubleCheck.lazy(this.biometricUnlockControllerLazyProvider), DoubleCheck.lazy(this.notificationShadeDepthControllerLazyProvider), this.dozeServiceHostProvider.get(), this.powerManagerProvider.get(), this.screenPinningRequestProvider.get(), this.dozeScrimControllerProvider.get(), this.volumeComponentProvider.get(), this.commandQueueProvider.get(), this.statusBarComponentBuilderProvider, this.pluginManagerProvider.get(), this.splitScreenOptionalProvider.get(), this.lightsOutNotifControllerProvider.get(), this.statusBarNotificationActivityStarterBuilderProvider.get(), this.shadeControllerProvider.get(), this.superStatusBarViewFactoryProvider.get(), this.statusBarKeyguardViewManagerProvider.get(), this.viewMediatorCallbackProvider.get(), this.initControllerProvider.get(), this.timeTickHandlerProvider.get(), this.pluginDependencyProvider.get(), this.keyguardDismissUtilProvider.get(), this.extensionControllerProvider.get(), this.userInfoControllerImplProvider.get(), this.phoneStatusBarPolicyProvider.get(), this.keyguardIndicationControllerProvider.get(), this.dismissCallbackRegistryProvider.get(), this.demoModeControllerProvider.get(), this.statusBarTouchableRegionManagerProvider.get(), this.notificationIconAreaControllerProvider.get(), this.brightnessSliderFactoryProvider.get(), this.wiredChargingRippleControllerProvider.get(), this.ongoingCallControllerProvider.get(), this.animationSchedulerProvider.get(), this.locationPublisherProvider.get(), this.statusBarIconControllerProvider.get(), this.lockscreenShadeTransitionControllerProvider.get(), this.featureFlagsProvider.get(), DoubleCheck.lazy(this.voiceReplyClientProvider), this.keyguardUnlockAnimationControllerProvider.get(), this.unlockedScreenOffAnimationControllerProvider.get(), this.startingSurfaceOptionalProvider.get());
    }

    public static StatusBarGoogleModule_ProvideStatusBarFactory create(Provider<SmartSpaceController> provider, Provider<WallpaperNotifier> provider2, Provider<Optional<ReverseChargingViewController>> provider3, Provider<Context> provider4, Provider<NotificationsController> provider5, Provider<LightBarController> provider6, Provider<AutoHideController> provider7, Provider<KeyguardUpdateMonitor> provider8, Provider<StatusBarSignalPolicy> provider9, Provider<PulseExpansionHandler> provider10, Provider<NotificationWakeUpCoordinator> provider11, Provider<KeyguardBypassController> provider12, Provider<KeyguardStateController> provider13, Provider<HeadsUpManagerPhone> provider14, Provider<DynamicPrivacyController> provider15, Provider<BypassHeadsUpNotifier> provider16, Provider<FalsingManager> provider17, Provider<FalsingCollector> provider18, Provider<BroadcastDispatcher> provider19, Provider<RemoteInputQuickSettingsDisabler> provider20, Provider<NotificationGutsManager> provider21, Provider<NotificationLogger> provider22, Provider<NotificationInterruptStateProvider> provider23, Provider<NotificationViewHierarchyManager> provider24, Provider<KeyguardViewMediator> provider25, Provider<DisplayMetrics> provider26, Provider<MetricsLogger> provider27, Provider<Executor> provider28, Provider<NotificationMediaManager> provider29, Provider<NotificationLockscreenUserManager> provider30, Provider<NotificationRemoteInputManager> provider31, Provider<UserSwitcherController> provider32, Provider<NetworkController> provider33, Provider<BatteryController> provider34, Provider<SysuiColorExtractor> provider35, Provider<ScreenLifecycle> provider36, Provider<WakefulnessLifecycle> provider37, Provider<SysuiStatusBarStateController> provider38, Provider<VibratorHelper> provider39, Provider<Optional<BubblesManager>> provider40, Provider<Optional<Bubbles>> provider41, Provider<VisualStabilityManager> provider42, Provider<DeviceProvisionedController> provider43, Provider<NavigationBarController> provider44, Provider<AccessibilityFloatingMenuController> provider45, Provider<AssistManager> provider46, Provider<ConfigurationController> provider47, Provider<NotificationShadeWindowController> provider48, Provider<DozeParameters> provider49, Provider<LiveWallpaperScrimController> provider50, Provider<KeyguardLiftController> provider51, Provider<LockscreenWallpaper> provider52, Provider<BiometricUnlockController> provider53, Provider<NotificationShadeDepthController> provider54, Provider<DozeServiceHost> provider55, Provider<PowerManager> provider56, Provider<ScreenPinningRequest> provider57, Provider<DozeScrimController> provider58, Provider<VolumeComponent> provider59, Provider<CommandQueue> provider60, Provider<StatusBarComponent.Builder> provider61, Provider<PluginManager> provider62, Provider<Optional<LegacySplitScreen>> provider63, Provider<LightsOutNotifController> provider64, Provider<StatusBarNotificationActivityStarter.Builder> provider65, Provider<ShadeController> provider66, Provider<SuperStatusBarViewFactory> provider67, Provider<StatusBarKeyguardViewManager> provider68, Provider<ViewMediatorCallback> provider69, Provider<InitController> provider70, Provider<Handler> provider71, Provider<PluginDependencyProvider> provider72, Provider<KeyguardDismissUtil> provider73, Provider<ExtensionController> provider74, Provider<UserInfoControllerImpl> provider75, Provider<PhoneStatusBarPolicy> provider76, Provider<KeyguardIndicationControllerGoogle> provider77, Provider<DismissCallbackRegistry> provider78, Provider<DemoModeController> provider79, Provider<StatusBarTouchableRegionManager> provider80, Provider<NotificationIconAreaController> provider81, Provider<BrightnessSlider.Factory> provider82, Provider<WiredChargingRippleController> provider83, Provider<OngoingCallController> provider84, Provider<SystemStatusAnimationScheduler> provider85, Provider<StatusBarLocationPublisher> provider86, Provider<StatusBarIconController> provider87, Provider<LockscreenShadeTransitionController> provider88, Provider<FeatureFlags> provider89, Provider<Optional<NotificationVoiceReplyClient>> provider90, Provider<KeyguardUnlockAnimationController> provider91, Provider<UnlockedScreenOffAnimationController> provider92, Provider<Optional<StartingSurface>> provider93) {
        return new StatusBarGoogleModule_ProvideStatusBarFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31, provider32, provider33, provider34, provider35, provider36, provider37, provider38, provider39, provider40, provider41, provider42, provider43, provider44, provider45, provider46, provider47, provider48, provider49, provider50, provider51, provider52, provider53, provider54, provider55, provider56, provider57, provider58, provider59, provider60, provider61, provider62, provider63, provider64, provider65, provider66, provider67, provider68, provider69, provider70, provider71, provider72, provider73, provider74, provider75, provider76, provider77, provider78, provider79, provider80, provider81, provider82, provider83, provider84, provider85, provider86, provider87, provider88, provider89, provider90, provider91, provider92, provider93);
    }

    public static StatusBarGoogle provideStatusBar(SmartSpaceController smartSpaceController, WallpaperNotifier wallpaperNotifier, Optional<ReverseChargingViewController> optional, Context context, NotificationsController notificationsController, LightBarController lightBarController, AutoHideController autoHideController, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarSignalPolicy statusBarSignalPolicy, PulseExpansionHandler pulseExpansionHandler, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, KeyguardStateController keyguardStateController, HeadsUpManagerPhone headsUpManagerPhone, DynamicPrivacyController dynamicPrivacyController, BypassHeadsUpNotifier bypassHeadsUpNotifier, FalsingManager falsingManager, FalsingCollector falsingCollector, BroadcastDispatcher broadcastDispatcher, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, NotificationGutsManager notificationGutsManager, NotificationLogger notificationLogger, NotificationInterruptStateProvider notificationInterruptStateProvider, NotificationViewHierarchyManager notificationViewHierarchyManager, KeyguardViewMediator keyguardViewMediator, DisplayMetrics displayMetrics, MetricsLogger metricsLogger, Executor executor, NotificationMediaManager notificationMediaManager, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationRemoteInputManager notificationRemoteInputManager, UserSwitcherController userSwitcherController, NetworkController networkController, BatteryController batteryController, SysuiColorExtractor sysuiColorExtractor, ScreenLifecycle screenLifecycle, WakefulnessLifecycle wakefulnessLifecycle, SysuiStatusBarStateController sysuiStatusBarStateController, VibratorHelper vibratorHelper, Optional<BubblesManager> optional2, Optional<Bubbles> optional3, VisualStabilityManager visualStabilityManager, DeviceProvisionedController deviceProvisionedController, NavigationBarController navigationBarController, AccessibilityFloatingMenuController accessibilityFloatingMenuController, Lazy<AssistManager> lazy, ConfigurationController configurationController, NotificationShadeWindowController notificationShadeWindowController, DozeParameters dozeParameters, LiveWallpaperScrimController liveWallpaperScrimController, KeyguardLiftController keyguardLiftController, Lazy<LockscreenWallpaper> lazy2, Lazy<BiometricUnlockController> lazy3, Lazy<NotificationShadeDepthController> lazy4, DozeServiceHost dozeServiceHost, PowerManager powerManager, ScreenPinningRequest screenPinningRequest, DozeScrimController dozeScrimController, VolumeComponent volumeComponent, CommandQueue commandQueue, Provider<StatusBarComponent.Builder> provider, PluginManager pluginManager, Optional<LegacySplitScreen> optional4, LightsOutNotifController lightsOutNotifController, StatusBarNotificationActivityStarter.Builder builder, ShadeController shadeController, SuperStatusBarViewFactory superStatusBarViewFactory, StatusBarKeyguardViewManager statusBarKeyguardViewManager, ViewMediatorCallback viewMediatorCallback, InitController initController, Handler handler, PluginDependencyProvider pluginDependencyProvider2, KeyguardDismissUtil keyguardDismissUtil, ExtensionController extensionController, UserInfoControllerImpl userInfoControllerImpl, PhoneStatusBarPolicy phoneStatusBarPolicy, KeyguardIndicationControllerGoogle keyguardIndicationControllerGoogle, DismissCallbackRegistry dismissCallbackRegistry, DemoModeController demoModeController, StatusBarTouchableRegionManager statusBarTouchableRegionManager, NotificationIconAreaController notificationIconAreaController, BrightnessSlider.Factory factory, WiredChargingRippleController wiredChargingRippleController, OngoingCallController ongoingCallController, SystemStatusAnimationScheduler systemStatusAnimationScheduler, StatusBarLocationPublisher statusBarLocationPublisher, StatusBarIconController statusBarIconController, LockscreenShadeTransitionController lockscreenShadeTransitionController, FeatureFlags featureFlags, Lazy<Optional<NotificationVoiceReplyClient>> lazy5, KeyguardUnlockAnimationController keyguardUnlockAnimationController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, Optional<StartingSurface> optional5) {
        return (StatusBarGoogle) Preconditions.checkNotNullFromProvides(StatusBarGoogleModule.provideStatusBar(smartSpaceController, wallpaperNotifier, optional, context, notificationsController, lightBarController, autoHideController, keyguardUpdateMonitor, statusBarSignalPolicy, pulseExpansionHandler, notificationWakeUpCoordinator, keyguardBypassController, keyguardStateController, headsUpManagerPhone, dynamicPrivacyController, bypassHeadsUpNotifier, falsingManager, falsingCollector, broadcastDispatcher, remoteInputQuickSettingsDisabler, notificationGutsManager, notificationLogger, notificationInterruptStateProvider, notificationViewHierarchyManager, keyguardViewMediator, displayMetrics, metricsLogger, executor, notificationMediaManager, notificationLockscreenUserManager, notificationRemoteInputManager, userSwitcherController, networkController, batteryController, sysuiColorExtractor, screenLifecycle, wakefulnessLifecycle, sysuiStatusBarStateController, vibratorHelper, optional2, optional3, visualStabilityManager, deviceProvisionedController, navigationBarController, accessibilityFloatingMenuController, lazy, configurationController, notificationShadeWindowController, dozeParameters, liveWallpaperScrimController, keyguardLiftController, lazy2, lazy3, lazy4, dozeServiceHost, powerManager, screenPinningRequest, dozeScrimController, volumeComponent, commandQueue, provider, pluginManager, optional4, lightsOutNotifController, builder, shadeController, superStatusBarViewFactory, statusBarKeyguardViewManager, viewMediatorCallback, initController, handler, pluginDependencyProvider2, keyguardDismissUtil, extensionController, userInfoControllerImpl, phoneStatusBarPolicy, keyguardIndicationControllerGoogle, dismissCallbackRegistry, demoModeController, statusBarTouchableRegionManager, notificationIconAreaController, factory, wiredChargingRippleController, ongoingCallController, systemStatusAnimationScheduler, statusBarLocationPublisher, statusBarIconController, lockscreenShadeTransitionController, featureFlags, lazy5, keyguardUnlockAnimationController, unlockedScreenOffAnimationController, optional5));
    }
}

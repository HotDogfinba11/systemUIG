package com.google.android.systemui.theme;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.FeatureFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ThemeOverlayControllerGoogle_Factory implements Factory<ThemeOverlayControllerGoogle> {
    private final Provider<Executor> bgExecutorProvider;
    private final Provider<Handler> bgHandlerProvider;
    private final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    private final Provider<ConfigurationController> configurationControllerProvider;
    private final Provider<Context> contextProvider;
    private final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    private final Provider<DumpManager> dumpManagerProvider;
    private final Provider<FeatureFlags> featureFlagsProvider;
    private final Provider<Executor> mainExecutorProvider;
    private final Provider<Resources> resourcesProvider;
    private final Provider<SecureSettings> secureSettingsProvider;
    private final Provider<SystemPropertiesHelper> systemPropertiesProvider;
    private final Provider<ThemeOverlayApplier> themeOverlayApplierProvider;
    private final Provider<UserManager> userManagerProvider;
    private final Provider<UserTracker> userTrackerProvider;
    private final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
    private final Provider<WallpaperManager> wallpaperManagerProvider;

    public ThemeOverlayControllerGoogle_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<Handler> provider3, Provider<Executor> provider4, Provider<Executor> provider5, Provider<ThemeOverlayApplier> provider6, Provider<SecureSettings> provider7, Provider<SystemPropertiesHelper> provider8, Provider<Resources> provider9, Provider<WallpaperManager> provider10, Provider<UserManager> provider11, Provider<DumpManager> provider12, Provider<DeviceProvisionedController> provider13, Provider<UserTracker> provider14, Provider<FeatureFlags> provider15, Provider<WakefulnessLifecycle> provider16, Provider<ConfigurationController> provider17) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.bgHandlerProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.bgExecutorProvider = provider5;
        this.themeOverlayApplierProvider = provider6;
        this.secureSettingsProvider = provider7;
        this.systemPropertiesProvider = provider8;
        this.resourcesProvider = provider9;
        this.wallpaperManagerProvider = provider10;
        this.userManagerProvider = provider11;
        this.dumpManagerProvider = provider12;
        this.deviceProvisionedControllerProvider = provider13;
        this.userTrackerProvider = provider14;
        this.featureFlagsProvider = provider15;
        this.wakefulnessLifecycleProvider = provider16;
        this.configurationControllerProvider = provider17;
    }

    @Override // javax.inject.Provider
    public ThemeOverlayControllerGoogle get() {
        return newInstance(this.contextProvider.get(), this.broadcastDispatcherProvider.get(), this.bgHandlerProvider.get(), this.mainExecutorProvider.get(), this.bgExecutorProvider.get(), this.themeOverlayApplierProvider.get(), this.secureSettingsProvider.get(), this.systemPropertiesProvider.get(), this.resourcesProvider.get(), this.wallpaperManagerProvider.get(), this.userManagerProvider.get(), this.dumpManagerProvider.get(), this.deviceProvisionedControllerProvider.get(), this.userTrackerProvider.get(), this.featureFlagsProvider.get(), this.wakefulnessLifecycleProvider.get(), this.configurationControllerProvider.get());
    }

    public static ThemeOverlayControllerGoogle_Factory create(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<Handler> provider3, Provider<Executor> provider4, Provider<Executor> provider5, Provider<ThemeOverlayApplier> provider6, Provider<SecureSettings> provider7, Provider<SystemPropertiesHelper> provider8, Provider<Resources> provider9, Provider<WallpaperManager> provider10, Provider<UserManager> provider11, Provider<DumpManager> provider12, Provider<DeviceProvisionedController> provider13, Provider<UserTracker> provider14, Provider<FeatureFlags> provider15, Provider<WakefulnessLifecycle> provider16, Provider<ConfigurationController> provider17) {
        return new ThemeOverlayControllerGoogle_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public static ThemeOverlayControllerGoogle newInstance(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler, Executor executor, Executor executor2, ThemeOverlayApplier themeOverlayApplier, SecureSettings secureSettings, SystemPropertiesHelper systemPropertiesHelper, Resources resources, WallpaperManager wallpaperManager, UserManager userManager, DumpManager dumpManager, DeviceProvisionedController deviceProvisionedController, UserTracker userTracker, FeatureFlags featureFlags, WakefulnessLifecycle wakefulnessLifecycle, ConfigurationController configurationController) {
        return new ThemeOverlayControllerGoogle(context, broadcastDispatcher, handler, executor, executor2, themeOverlayApplier, secureSettings, systemPropertiesHelper, resources, wallpaperManager, userManager, dumpManager, deviceProvisionedController, userTracker, featureFlags, wakefulnessLifecycle, configurationController);
    }
}

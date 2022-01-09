package com.google.android.systemui.columbus.actions;

import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.Handler;
import android.os.UserManager;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.gates.KeyguardVisibility;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class LaunchApp_Factory implements Factory<LaunchApp> {
    private final Provider<IActivityManager> activityManagerServiceProvider;
    private final Provider<ActivityStarter> activityStarterProvider;
    private final Provider<Executor> bgExecutorProvider;
    private final Provider<Handler> bgHandlerProvider;
    private final Provider<ColumbusSettings> columbusSettingsProvider;
    private final Provider<Context> contextProvider;
    private final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    private final Provider<KeyguardVisibility> keyguardVisibilityProvider;
    private final Provider<LauncherApps> launcherAppsProvider;
    private final Provider<Handler> mainHandlerProvider;
    private final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    private final Provider<UiEventLogger> uiEventLoggerProvider;
    private final Provider<UserManager> userManagerProvider;
    private final Provider<UserTracker> userTrackerProvider;

    public LaunchApp_Factory(Provider<Context> provider, Provider<LauncherApps> provider2, Provider<ActivityStarter> provider3, Provider<StatusBarKeyguardViewManager> provider4, Provider<IActivityManager> provider5, Provider<UserManager> provider6, Provider<ColumbusSettings> provider7, Provider<KeyguardVisibility> provider8, Provider<KeyguardUpdateMonitor> provider9, Provider<Handler> provider10, Provider<Handler> provider11, Provider<Executor> provider12, Provider<UiEventLogger> provider13, Provider<UserTracker> provider14) {
        this.contextProvider = provider;
        this.launcherAppsProvider = provider2;
        this.activityStarterProvider = provider3;
        this.statusBarKeyguardViewManagerProvider = provider4;
        this.activityManagerServiceProvider = provider5;
        this.userManagerProvider = provider6;
        this.columbusSettingsProvider = provider7;
        this.keyguardVisibilityProvider = provider8;
        this.keyguardUpdateMonitorProvider = provider9;
        this.mainHandlerProvider = provider10;
        this.bgHandlerProvider = provider11;
        this.bgExecutorProvider = provider12;
        this.uiEventLoggerProvider = provider13;
        this.userTrackerProvider = provider14;
    }

    @Override // javax.inject.Provider
    public LaunchApp get() {
        return newInstance(this.contextProvider.get(), this.launcherAppsProvider.get(), this.activityStarterProvider.get(), this.statusBarKeyguardViewManagerProvider.get(), this.activityManagerServiceProvider.get(), this.userManagerProvider.get(), this.columbusSettingsProvider.get(), this.keyguardVisibilityProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.mainHandlerProvider.get(), this.bgHandlerProvider.get(), this.bgExecutorProvider.get(), this.uiEventLoggerProvider.get(), this.userTrackerProvider.get());
    }

    public static LaunchApp_Factory create(Provider<Context> provider, Provider<LauncherApps> provider2, Provider<ActivityStarter> provider3, Provider<StatusBarKeyguardViewManager> provider4, Provider<IActivityManager> provider5, Provider<UserManager> provider6, Provider<ColumbusSettings> provider7, Provider<KeyguardVisibility> provider8, Provider<KeyguardUpdateMonitor> provider9, Provider<Handler> provider10, Provider<Handler> provider11, Provider<Executor> provider12, Provider<UiEventLogger> provider13, Provider<UserTracker> provider14) {
        return new LaunchApp_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14);
    }

    public static LaunchApp newInstance(Context context, LauncherApps launcherApps, ActivityStarter activityStarter, StatusBarKeyguardViewManager statusBarKeyguardViewManager, IActivityManager iActivityManager, UserManager userManager, ColumbusSettings columbusSettings, KeyguardVisibility keyguardVisibility, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler, Handler handler2, Executor executor, UiEventLogger uiEventLogger, UserTracker userTracker) {
        return new LaunchApp(context, launcherApps, activityStarter, statusBarKeyguardViewManager, iActivityManager, userManager, columbusSettings, keyguardVisibility, keyguardUpdateMonitor, handler, handler2, executor, uiEventLogger, userTracker);
    }
}

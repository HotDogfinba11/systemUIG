package com.android.systemui.biometrics;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AuthRippleController_Factory implements Factory<AuthRippleController> {
    private final Provider<AuthController> authControllerProvider;
    private final Provider<BiometricUnlockController> biometricUnlockControllerProvider;
    private final Provider<KeyguardBypassController> bypassControllerProvider;
    private final Provider<CommandRegistry> commandRegistryProvider;
    private final Provider<ConfigurationController> configurationControllerProvider;
    private final Provider<KeyguardStateController> keyguardStateControllerProvider;
    private final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    private final Provider<NotificationShadeWindowController> notificationShadeWindowControllerProvider;
    private final Provider<AuthRippleView> rippleViewProvider;
    private final Provider<StatusBar> statusBarProvider;
    private final Provider<StatusBarStateController> statusBarStateControllerProvider;
    private final Provider<Context> sysuiContextProvider;
    private final Provider<UdfpsController> udfpsControllerProvider;
    private final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public AuthRippleController_Factory(Provider<StatusBar> provider, Provider<Context> provider2, Provider<AuthController> provider3, Provider<ConfigurationController> provider4, Provider<KeyguardUpdateMonitor> provider5, Provider<KeyguardStateController> provider6, Provider<WakefulnessLifecycle> provider7, Provider<CommandRegistry> provider8, Provider<NotificationShadeWindowController> provider9, Provider<KeyguardBypassController> provider10, Provider<BiometricUnlockController> provider11, Provider<UdfpsController> provider12, Provider<StatusBarStateController> provider13, Provider<AuthRippleView> provider14) {
        this.statusBarProvider = provider;
        this.sysuiContextProvider = provider2;
        this.authControllerProvider = provider3;
        this.configurationControllerProvider = provider4;
        this.keyguardUpdateMonitorProvider = provider5;
        this.keyguardStateControllerProvider = provider6;
        this.wakefulnessLifecycleProvider = provider7;
        this.commandRegistryProvider = provider8;
        this.notificationShadeWindowControllerProvider = provider9;
        this.bypassControllerProvider = provider10;
        this.biometricUnlockControllerProvider = provider11;
        this.udfpsControllerProvider = provider12;
        this.statusBarStateControllerProvider = provider13;
        this.rippleViewProvider = provider14;
    }

    @Override // javax.inject.Provider
    public AuthRippleController get() {
        return newInstance(this.statusBarProvider.get(), this.sysuiContextProvider.get(), this.authControllerProvider.get(), this.configurationControllerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.keyguardStateControllerProvider.get(), this.wakefulnessLifecycleProvider.get(), this.commandRegistryProvider.get(), this.notificationShadeWindowControllerProvider.get(), this.bypassControllerProvider.get(), this.biometricUnlockControllerProvider.get(), this.udfpsControllerProvider, this.statusBarStateControllerProvider.get(), this.rippleViewProvider.get());
    }

    public static AuthRippleController_Factory create(Provider<StatusBar> provider, Provider<Context> provider2, Provider<AuthController> provider3, Provider<ConfigurationController> provider4, Provider<KeyguardUpdateMonitor> provider5, Provider<KeyguardStateController> provider6, Provider<WakefulnessLifecycle> provider7, Provider<CommandRegistry> provider8, Provider<NotificationShadeWindowController> provider9, Provider<KeyguardBypassController> provider10, Provider<BiometricUnlockController> provider11, Provider<UdfpsController> provider12, Provider<StatusBarStateController> provider13, Provider<AuthRippleView> provider14) {
        return new AuthRippleController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14);
    }

    public static AuthRippleController newInstance(StatusBar statusBar, Context context, AuthController authController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, WakefulnessLifecycle wakefulnessLifecycle, CommandRegistry commandRegistry, NotificationShadeWindowController notificationShadeWindowController, KeyguardBypassController keyguardBypassController, BiometricUnlockController biometricUnlockController, Provider<UdfpsController> provider, StatusBarStateController statusBarStateController, AuthRippleView authRippleView) {
        return new AuthRippleController(statusBar, context, authController, configurationController, keyguardUpdateMonitor, keyguardStateController, wakefulnessLifecycle, commandRegistry, notificationShadeWindowController, keyguardBypassController, biometricUnlockController, provider, statusBarStateController, authRippleView);
    }
}

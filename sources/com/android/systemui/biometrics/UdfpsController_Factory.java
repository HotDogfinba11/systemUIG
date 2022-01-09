package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class UdfpsController_Factory implements Factory<UdfpsController> {
    private final Provider<AccessibilityManager> accessibilityManagerProvider;
    private final Provider<ConfigurationController> configurationControllerProvider;
    private final Provider<Context> contextProvider;
    private final Provider<DisplayManager> displayManagerProvider;
    private final Provider<DumpManager> dumpManagerProvider;
    private final Provider<Execution> executionProvider;
    private final Provider<FalsingManager> falsingManagerProvider;
    private final Provider<DelayableExecutor> fgExecutorProvider;
    private final Provider<FingerprintManager> fingerprintManagerProvider;
    private final Provider<Optional<UdfpsHbmProvider>> hbmProvider;
    private final Provider<LayoutInflater> inflaterProvider;
    private final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    private final Provider<KeyguardStateController> keyguardStateControllerProvider;
    private final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    private final Provider<LockscreenShadeTransitionController> lockscreenShadeTransitionControllerProvider;
    private final Provider<Handler> mainHandlerProvider;
    private final Provider<PowerManager> powerManagerProvider;
    private final Provider<ScreenLifecycle> screenLifecycleProvider;
    private final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    private final Provider<StatusBar> statusBarProvider;
    private final Provider<StatusBarStateController> statusBarStateControllerProvider;
    private final Provider<SystemClock> systemClockProvider;
    private final Provider<UdfpsHapticsSimulator> udfpsHapticsSimulatorProvider;
    private final Provider<UnlockedScreenOffAnimationController> unlockedScreenOffAnimationControllerProvider;
    private final Provider<Vibrator> vibratorProvider;
    private final Provider<WindowManager> windowManagerProvider;

    public UdfpsController_Factory(Provider<Context> provider, Provider<Execution> provider2, Provider<LayoutInflater> provider3, Provider<FingerprintManager> provider4, Provider<WindowManager> provider5, Provider<StatusBarStateController> provider6, Provider<DelayableExecutor> provider7, Provider<StatusBar> provider8, Provider<StatusBarKeyguardViewManager> provider9, Provider<DumpManager> provider10, Provider<KeyguardUpdateMonitor> provider11, Provider<FalsingManager> provider12, Provider<PowerManager> provider13, Provider<AccessibilityManager> provider14, Provider<LockscreenShadeTransitionController> provider15, Provider<ScreenLifecycle> provider16, Provider<Vibrator> provider17, Provider<UdfpsHapticsSimulator> provider18, Provider<Optional<UdfpsHbmProvider>> provider19, Provider<KeyguardStateController> provider20, Provider<KeyguardBypassController> provider21, Provider<DisplayManager> provider22, Provider<Handler> provider23, Provider<ConfigurationController> provider24, Provider<SystemClock> provider25, Provider<UnlockedScreenOffAnimationController> provider26) {
        this.contextProvider = provider;
        this.executionProvider = provider2;
        this.inflaterProvider = provider3;
        this.fingerprintManagerProvider = provider4;
        this.windowManagerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.fgExecutorProvider = provider7;
        this.statusBarProvider = provider8;
        this.statusBarKeyguardViewManagerProvider = provider9;
        this.dumpManagerProvider = provider10;
        this.keyguardUpdateMonitorProvider = provider11;
        this.falsingManagerProvider = provider12;
        this.powerManagerProvider = provider13;
        this.accessibilityManagerProvider = provider14;
        this.lockscreenShadeTransitionControllerProvider = provider15;
        this.screenLifecycleProvider = provider16;
        this.vibratorProvider = provider17;
        this.udfpsHapticsSimulatorProvider = provider18;
        this.hbmProvider = provider19;
        this.keyguardStateControllerProvider = provider20;
        this.keyguardBypassControllerProvider = provider21;
        this.displayManagerProvider = provider22;
        this.mainHandlerProvider = provider23;
        this.configurationControllerProvider = provider24;
        this.systemClockProvider = provider25;
        this.unlockedScreenOffAnimationControllerProvider = provider26;
    }

    @Override // javax.inject.Provider
    public UdfpsController get() {
        return newInstance(this.contextProvider.get(), this.executionProvider.get(), this.inflaterProvider.get(), this.fingerprintManagerProvider.get(), this.windowManagerProvider.get(), this.statusBarStateControllerProvider.get(), this.fgExecutorProvider.get(), this.statusBarProvider.get(), this.statusBarKeyguardViewManagerProvider.get(), this.dumpManagerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.falsingManagerProvider.get(), this.powerManagerProvider.get(), this.accessibilityManagerProvider.get(), this.lockscreenShadeTransitionControllerProvider.get(), this.screenLifecycleProvider.get(), this.vibratorProvider.get(), this.udfpsHapticsSimulatorProvider.get(), this.hbmProvider.get(), this.keyguardStateControllerProvider.get(), this.keyguardBypassControllerProvider.get(), this.displayManagerProvider.get(), this.mainHandlerProvider.get(), this.configurationControllerProvider.get(), this.systemClockProvider.get(), this.unlockedScreenOffAnimationControllerProvider.get());
    }

    public static UdfpsController_Factory create(Provider<Context> provider, Provider<Execution> provider2, Provider<LayoutInflater> provider3, Provider<FingerprintManager> provider4, Provider<WindowManager> provider5, Provider<StatusBarStateController> provider6, Provider<DelayableExecutor> provider7, Provider<StatusBar> provider8, Provider<StatusBarKeyguardViewManager> provider9, Provider<DumpManager> provider10, Provider<KeyguardUpdateMonitor> provider11, Provider<FalsingManager> provider12, Provider<PowerManager> provider13, Provider<AccessibilityManager> provider14, Provider<LockscreenShadeTransitionController> provider15, Provider<ScreenLifecycle> provider16, Provider<Vibrator> provider17, Provider<UdfpsHapticsSimulator> provider18, Provider<Optional<UdfpsHbmProvider>> provider19, Provider<KeyguardStateController> provider20, Provider<KeyguardBypassController> provider21, Provider<DisplayManager> provider22, Provider<Handler> provider23, Provider<ConfigurationController> provider24, Provider<SystemClock> provider25, Provider<UnlockedScreenOffAnimationController> provider26) {
        return new UdfpsController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26);
    }

    public static UdfpsController newInstance(Context context, Execution execution, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, StatusBarStateController statusBarStateController, DelayableExecutor delayableExecutor, StatusBar statusBar, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, FalsingManager falsingManager, PowerManager powerManager, AccessibilityManager accessibilityManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ScreenLifecycle screenLifecycle, Vibrator vibrator, UdfpsHapticsSimulator udfpsHapticsSimulator, Optional<UdfpsHbmProvider> optional, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, DisplayManager displayManager, Handler handler, ConfigurationController configurationController, SystemClock systemClock, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        return new UdfpsController(context, execution, layoutInflater, fingerprintManager, windowManager, statusBarStateController, delayableExecutor, statusBar, statusBarKeyguardViewManager, dumpManager, keyguardUpdateMonitor, falsingManager, powerManager, accessibilityManager, lockscreenShadeTransitionController, screenLifecycle, vibrator, udfpsHapticsSimulator, optional, keyguardStateController, keyguardBypassController, displayManager, handler, configurationController, systemClock, unlockedScreenOffAnimationController);
    }
}

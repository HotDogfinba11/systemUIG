package com.google.android.systemui.columbus.sensors;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.google.android.systemui.columbus.gates.Gate;
import dagger.internal.Factory;
import java.util.Set;
import javax.inject.Provider;

public final class GestureController_Factory implements Factory<GestureController> {
    private final Provider<CommandRegistry> commandRegistryProvider;
    private final Provider<GestureSensor> gestureSensorProvider;
    private final Provider<Set<Gate>> softGatesProvider;
    private final Provider<UiEventLogger> uiEventLoggerProvider;

    public GestureController_Factory(Provider<GestureSensor> provider, Provider<Set<Gate>> provider2, Provider<CommandRegistry> provider3, Provider<UiEventLogger> provider4) {
        this.gestureSensorProvider = provider;
        this.softGatesProvider = provider2;
        this.commandRegistryProvider = provider3;
        this.uiEventLoggerProvider = provider4;
    }

    @Override // javax.inject.Provider
    public GestureController get() {
        return newInstance(this.gestureSensorProvider.get(), this.softGatesProvider.get(), this.commandRegistryProvider.get(), this.uiEventLoggerProvider.get());
    }

    public static GestureController_Factory create(Provider<GestureSensor> provider, Provider<Set<Gate>> provider2, Provider<CommandRegistry> provider3, Provider<UiEventLogger> provider4) {
        return new GestureController_Factory(provider, provider2, provider3, provider4);
    }

    public static GestureController newInstance(GestureSensor gestureSensor, Set<Gate> set, CommandRegistry commandRegistry, UiEventLogger uiEventLogger) {
        return new GestureController(gestureSensor, set, commandRegistry, uiEventLogger);
    }
}

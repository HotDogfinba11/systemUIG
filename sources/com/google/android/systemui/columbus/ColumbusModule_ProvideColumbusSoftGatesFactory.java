package com.google.android.systemui.columbus;

import com.google.android.systemui.columbus.gates.ChargingState;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.gates.ScreenTouch;
import com.google.android.systemui.columbus.gates.SystemKeyPress;
import com.google.android.systemui.columbus.gates.UsbState;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Set;
import javax.inject.Provider;

public final class ColumbusModule_ProvideColumbusSoftGatesFactory implements Factory<Set<Gate>> {
    private final Provider<ChargingState> chargingStateProvider;
    private final Provider<ScreenTouch> screenTouchProvider;
    private final Provider<SystemKeyPress> systemKeyPressProvider;
    private final Provider<UsbState> usbStateProvider;

    public ColumbusModule_ProvideColumbusSoftGatesFactory(Provider<ChargingState> provider, Provider<UsbState> provider2, Provider<SystemKeyPress> provider3, Provider<ScreenTouch> provider4) {
        this.chargingStateProvider = provider;
        this.usbStateProvider = provider2;
        this.systemKeyPressProvider = provider3;
        this.screenTouchProvider = provider4;
    }

    @Override // javax.inject.Provider
    public Set<Gate> get() {
        return provideColumbusSoftGates(this.chargingStateProvider.get(), this.usbStateProvider.get(), this.systemKeyPressProvider.get(), this.screenTouchProvider.get());
    }

    public static ColumbusModule_ProvideColumbusSoftGatesFactory create(Provider<ChargingState> provider, Provider<UsbState> provider2, Provider<SystemKeyPress> provider3, Provider<ScreenTouch> provider4) {
        return new ColumbusModule_ProvideColumbusSoftGatesFactory(provider, provider2, provider3, provider4);
    }

    public static Set<Gate> provideColumbusSoftGates(ChargingState chargingState, UsbState usbState, SystemKeyPress systemKeyPress, ScreenTouch screenTouch) {
        return (Set) Preconditions.checkNotNullFromProvides(ColumbusModule.provideColumbusSoftGates(chargingState, usbState, systemKeyPress, screenTouch));
    }
}

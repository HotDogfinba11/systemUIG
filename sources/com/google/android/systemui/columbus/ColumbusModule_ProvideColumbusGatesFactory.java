package com.google.android.systemui.columbus;

import com.google.android.systemui.columbus.gates.CameraVisibility;
import com.google.android.systemui.columbus.gates.FlagEnabled;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.gates.KeyguardProximity;
import com.google.android.systemui.columbus.gates.PowerSaveState;
import com.google.android.systemui.columbus.gates.PowerState;
import com.google.android.systemui.columbus.gates.SetupWizard;
import com.google.android.systemui.columbus.gates.TelephonyActivity;
import com.google.android.systemui.columbus.gates.VrMode;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Set;
import javax.inject.Provider;

public final class ColumbusModule_ProvideColumbusGatesFactory implements Factory<Set<Gate>> {
    private final Provider<CameraVisibility> cameraVisibilityProvider;
    private final Provider<FlagEnabled> flagEnabledProvider;
    private final Provider<KeyguardProximity> keyguardProximityProvider;
    private final Provider<PowerSaveState> powerSaveStateProvider;
    private final Provider<PowerState> powerStateProvider;
    private final Provider<SetupWizard> setupWizardProvider;
    private final Provider<TelephonyActivity> telephonyActivityProvider;
    private final Provider<VrMode> vrModeProvider;

    public ColumbusModule_ProvideColumbusGatesFactory(Provider<FlagEnabled> provider, Provider<KeyguardProximity> provider2, Provider<SetupWizard> provider3, Provider<TelephonyActivity> provider4, Provider<VrMode> provider5, Provider<CameraVisibility> provider6, Provider<PowerSaveState> provider7, Provider<PowerState> provider8) {
        this.flagEnabledProvider = provider;
        this.keyguardProximityProvider = provider2;
        this.setupWizardProvider = provider3;
        this.telephonyActivityProvider = provider4;
        this.vrModeProvider = provider5;
        this.cameraVisibilityProvider = provider6;
        this.powerSaveStateProvider = provider7;
        this.powerStateProvider = provider8;
    }

    @Override // javax.inject.Provider
    public Set<Gate> get() {
        return provideColumbusGates(this.flagEnabledProvider.get(), this.keyguardProximityProvider.get(), this.setupWizardProvider.get(), this.telephonyActivityProvider.get(), this.vrModeProvider.get(), this.cameraVisibilityProvider.get(), this.powerSaveStateProvider.get(), this.powerStateProvider.get());
    }

    public static ColumbusModule_ProvideColumbusGatesFactory create(Provider<FlagEnabled> provider, Provider<KeyguardProximity> provider2, Provider<SetupWizard> provider3, Provider<TelephonyActivity> provider4, Provider<VrMode> provider5, Provider<CameraVisibility> provider6, Provider<PowerSaveState> provider7, Provider<PowerState> provider8) {
        return new ColumbusModule_ProvideColumbusGatesFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static Set<Gate> provideColumbusGates(FlagEnabled flagEnabled, KeyguardProximity keyguardProximity, SetupWizard setupWizard, TelephonyActivity telephonyActivity, VrMode vrMode, CameraVisibility cameraVisibility, PowerSaveState powerSaveState, PowerState powerState) {
        return (Set) Preconditions.checkNotNullFromProvides(ColumbusModule.provideColumbusGates(flagEnabled, keyguardProximity, setupWizard, telephonyActivity, vrMode, cameraVisibility, powerSaveState, powerState));
    }
}

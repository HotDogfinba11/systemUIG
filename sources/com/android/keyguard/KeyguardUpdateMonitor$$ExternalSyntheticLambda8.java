package com.android.keyguard;

import java.util.function.Consumer;

public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ KeyguardUpdateMonitor f$0;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda8(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.f$0 = keyguardUpdateMonitor;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.notifyStrongAuthStateChanged(((Integer) obj).intValue());
    }
}

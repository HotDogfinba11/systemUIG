package com.android.keyguard;

import java.util.function.Supplier;

public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda10 implements Supplier {
    public final /* synthetic */ KeyguardUpdateMonitor f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda10(KeyguardUpdateMonitor keyguardUpdateMonitor, int i) {
        this.f$0 = keyguardUpdateMonitor;
        this.f$1 = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f$0.lambda$updateFaceEnrolled$6(this.f$1);
    }
}

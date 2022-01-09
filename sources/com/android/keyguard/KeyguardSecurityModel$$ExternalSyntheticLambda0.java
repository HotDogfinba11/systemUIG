package com.android.keyguard;

import java.util.function.Supplier;

public final /* synthetic */ class KeyguardSecurityModel$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ KeyguardSecurityModel f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ KeyguardSecurityModel$$ExternalSyntheticLambda0(KeyguardSecurityModel keyguardSecurityModel, int i) {
        this.f$0 = keyguardSecurityModel;
        this.f$1 = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f$0.lambda$getSecurityMode$0(this.f$1);
    }
}

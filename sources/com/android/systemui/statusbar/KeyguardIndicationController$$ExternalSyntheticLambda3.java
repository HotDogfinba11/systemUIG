package com.android.systemui.statusbar;

import java.util.function.Supplier;

public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda3 implements Supplier {
    public final /* synthetic */ KeyguardIndicationController f$0;

    public /* synthetic */ KeyguardIndicationController$$ExternalSyntheticLambda3(KeyguardIndicationController keyguardIndicationController) {
        this.f$0 = keyguardIndicationController;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return Boolean.valueOf(this.f$0.isOrganizationOwnedDevice());
    }
}

package com.google.android.systemui.qs.tileimpl;

import com.android.systemui.qs.tiles.BatterySaverTile;
import javax.inject.Provider;

public final /* synthetic */ class QSFactoryImplGoogle$$ExternalSyntheticLambda0 implements Provider {
    public final /* synthetic */ Provider f$0;

    public /* synthetic */ QSFactoryImplGoogle$$ExternalSyntheticLambda0(Provider provider) {
        this.f$0 = provider;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return (BatterySaverTile) this.f$0.get();
    }
}

package com.android.wm.shell.startingsurface;

import android.window.SplashScreenView;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;
import java.util.function.Consumer;

public final /* synthetic */ class StartingSurfaceDrawer$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ StartingSurfaceDrawer.SplashScreenViewSupplier f$0;

    public /* synthetic */ StartingSurfaceDrawer$$ExternalSyntheticLambda8(StartingSurfaceDrawer.SplashScreenViewSupplier splashScreenViewSupplier) {
        this.f$0 = splashScreenViewSupplier;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.setView((SplashScreenView) obj);
    }
}

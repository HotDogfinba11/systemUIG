package com.android.systemui.biometrics;

import android.view.Surface;
import com.android.systemui.biometrics.UdfpsSurfaceView;

public final /* synthetic */ class UdfpsView$$ExternalSyntheticLambda0 implements UdfpsSurfaceView.GhbmIlluminationListener {
    public final /* synthetic */ UdfpsView f$0;

    public /* synthetic */ UdfpsView$$ExternalSyntheticLambda0(UdfpsView udfpsView) {
        this.f$0 = udfpsView;
    }

    @Override // com.android.systemui.biometrics.UdfpsSurfaceView.GhbmIlluminationListener
    public final void enableGhbm(Surface surface, Runnable runnable) {
        this.f$0.doIlluminate(surface, runnable);
    }
}

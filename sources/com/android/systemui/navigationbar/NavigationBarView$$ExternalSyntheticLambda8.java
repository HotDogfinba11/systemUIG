package com.android.systemui.navigationbar;

import android.graphics.Rect;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import java.util.function.Consumer;

public final /* synthetic */ class NavigationBarView$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ EdgeBackGestureHandler f$0;

    public /* synthetic */ NavigationBarView$$ExternalSyntheticLambda8(EdgeBackGestureHandler edgeBackGestureHandler) {
        this.f$0 = edgeBackGestureHandler;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.updateNavigationBarOverlayExcludeRegion((Rect) obj);
    }
}

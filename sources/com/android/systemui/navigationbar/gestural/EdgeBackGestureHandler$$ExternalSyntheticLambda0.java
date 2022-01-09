package com.android.systemui.navigationbar.gestural;

import android.view.InputEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;

public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda0 implements InputChannelCompat$InputEventListener {
    public final /* synthetic */ EdgeBackGestureHandler f$0;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda0(EdgeBackGestureHandler edgeBackGestureHandler) {
        this.f$0 = edgeBackGestureHandler;
    }

    @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
    public final void onInputEvent(InputEvent inputEvent) {
        this.f$0.onInputEvent(inputEvent);
    }
}

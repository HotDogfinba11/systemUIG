package com.google.android.systemui.gamedashboard;

import com.google.android.systemui.gamedashboard.FpsController;

public final /* synthetic */ class ShortcutBarController$$ExternalSyntheticLambda0 implements FpsController.Callback {
    public final /* synthetic */ ShortcutBarView f$0;

    public /* synthetic */ ShortcutBarController$$ExternalSyntheticLambda0(ShortcutBarView shortcutBarView) {
        this.f$0 = shortcutBarView;
    }

    @Override // com.google.android.systemui.gamedashboard.FpsController.Callback
    public final void onFpsUpdated(float f) {
        this.f$0.setFps(f);
    }
}

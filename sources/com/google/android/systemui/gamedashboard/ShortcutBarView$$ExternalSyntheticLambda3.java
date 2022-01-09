package com.google.android.systemui.gamedashboard;

import android.view.ViewTreeObserver;

public final /* synthetic */ class ShortcutBarView$$ExternalSyntheticLambda3 implements ViewTreeObserver.OnDrawListener {
    public final /* synthetic */ ShortcutBarView f$0;

    public /* synthetic */ ShortcutBarView$$ExternalSyntheticLambda3(ShortcutBarView shortcutBarView) {
        this.f$0 = shortcutBarView;
    }

    public final void onDraw() {
        this.f$0.updateSystemGestureExcludeRects();
    }
}

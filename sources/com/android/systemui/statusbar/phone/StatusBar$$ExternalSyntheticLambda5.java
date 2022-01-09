package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.BackDropView;
import com.android.systemui.statusbar.NotificationShadeDepthController;

public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda5 implements NotificationShadeDepthController.DepthListener {
    public final /* synthetic */ float f$0;
    public final /* synthetic */ BackDropView f$1;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda5(float f, BackDropView backDropView) {
        this.f$0 = f;
        this.f$1 = backDropView;
    }

    @Override // com.android.systemui.statusbar.NotificationShadeDepthController.DepthListener
    public final void onWallpaperZoomOutChanged(float f) {
        StatusBar.lambda$makeStatusBarView$7(this.f$0, this.f$1, f);
    }
}

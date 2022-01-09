package com.google.android.systemui.gamedashboard;

import android.os.Handler;
import android.view.View;
import com.android.internal.util.ScreenshotHelper;
import java.util.Optional;

public final /* synthetic */ class ShortcutBarView$$ExternalSyntheticLambda2 implements View.OnClickListener {
    public final /* synthetic */ ShortcutBarView f$0;
    public final /* synthetic */ Optional f$1;
    public final /* synthetic */ ShortcutBarController f$2;
    public final /* synthetic */ ScreenshotHelper f$3;
    public final /* synthetic */ Handler f$4;

    public /* synthetic */ ShortcutBarView$$ExternalSyntheticLambda2(ShortcutBarView shortcutBarView, Optional optional, ShortcutBarController shortcutBarController, ScreenshotHelper screenshotHelper, Handler handler) {
        this.f$0 = shortcutBarView;
        this.f$1 = optional;
        this.f$2 = shortcutBarController;
        this.f$3 = screenshotHelper;
        this.f$4 = handler;
    }

    public final void onClick(View view) {
        ShortcutBarView.$r8$lambda$z97dER_mVbNAS0z7LmQ5soxX0mw(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, view);
    }
}

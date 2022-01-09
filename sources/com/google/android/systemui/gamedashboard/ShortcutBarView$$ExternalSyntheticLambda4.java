package com.google.android.systemui.gamedashboard;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.Handler;
import android.view.SurfaceControl;
import com.android.internal.util.ScreenshotHelper;
import java.util.function.Consumer;

public final /* synthetic */ class ShortcutBarView$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ ShortcutBarView f$0;
    public final /* synthetic */ ScreenshotHelper f$1;
    public final /* synthetic */ Rect f$2;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$3;
    public final /* synthetic */ Handler f$4;

    public /* synthetic */ ShortcutBarView$$ExternalSyntheticLambda4(ShortcutBarView shortcutBarView, ScreenshotHelper screenshotHelper, Rect rect, ActivityManager.RunningTaskInfo runningTaskInfo, Handler handler) {
        this.f$0 = shortcutBarView;
        this.f$1 = screenshotHelper;
        this.f$2 = rect;
        this.f$3 = runningTaskInfo;
        this.f$4 = handler;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$init$0(this.f$1, this.f$2, this.f$3, this.f$4, (SurfaceControl.ScreenshotHardwareBuffer) obj);
    }
}

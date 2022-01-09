package com.google.android.systemui.gamedashboard;

import android.os.Handler;
import com.android.internal.util.ScreenshotHelper;
import com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import java.util.function.Consumer;

public final /* synthetic */ class ShortcutBarView$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ ShortcutBarView f$0;
    public final /* synthetic */ ShortcutBarController f$1;
    public final /* synthetic */ ScreenshotHelper f$2;
    public final /* synthetic */ Handler f$3;

    public /* synthetic */ ShortcutBarView$$ExternalSyntheticLambda5(ShortcutBarView shortcutBarView, ShortcutBarController shortcutBarController, ScreenshotHelper screenshotHelper, Handler handler) {
        this.f$0 = shortcutBarView;
        this.f$1 = shortcutBarController;
        this.f$2 = screenshotHelper;
        this.f$3 = handler;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$init$1(this.f$1, this.f$2, this.f$3, (TaskSurfaceHelper) obj);
    }
}

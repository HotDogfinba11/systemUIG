package com.google.android.systemui.assist.uihints;

import android.app.PendingIntent;
import android.net.Uri;
import java.util.function.Consumer;

public final /* synthetic */ class TakeScreenshotHandler$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ TakeScreenshotHandler f$0;
    public final /* synthetic */ PendingIntent f$1;

    public /* synthetic */ TakeScreenshotHandler$$ExternalSyntheticLambda0(TakeScreenshotHandler takeScreenshotHandler, PendingIntent pendingIntent) {
        this.f$0 = takeScreenshotHandler;
        this.f$1 = pendingIntent;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        TakeScreenshotHandler.$r8$lambda$csP2_MYBsYscfEqGKBTE8f7bLf8(this.f$0, this.f$1, (Uri) obj);
    }
}

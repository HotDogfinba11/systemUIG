package com.android.systemui.screenshot;

import com.android.systemui.screenshot.LongScreenshotActivity;

public final /* synthetic */ class LongScreenshotActivity$1$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ LongScreenshotActivity.AnonymousClass1 f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ LongScreenshotActivity$1$$ExternalSyntheticLambda1(LongScreenshotActivity.AnonymousClass1 r1, float f, float f2) {
        this.f$0 = r1;
        this.f$1 = f;
        this.f$2 = f2;
    }

    public final void run() {
        this.f$0.lambda$onPreDraw$0(this.f$1, this.f$2);
    }
}

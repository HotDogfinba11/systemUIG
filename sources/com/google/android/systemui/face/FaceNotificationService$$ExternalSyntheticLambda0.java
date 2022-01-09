package com.google.android.systemui.face;

public final /* synthetic */ class FaceNotificationService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FaceNotificationService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ FaceNotificationService$$ExternalSyntheticLambda0(FaceNotificationService faceNotificationService, String str, String str2) {
        this.f$0 = faceNotificationService;
        this.f$1 = str;
        this.f$2 = str2;
    }

    public final void run() {
        this.f$0.lambda$queueReenrollNotification$0(this.f$1, this.f$2);
    }
}

package com.google.android.systemui.columbus.gates;

/* compiled from: CameraVisibility.kt */
final class CameraVisibility$taskStackListener$1$onTaskStackChanged$1 implements Runnable {
    final /* synthetic */ CameraVisibility this$0;

    CameraVisibility$taskStackListener$1$onTaskStackChanged$1(CameraVisibility cameraVisibility) {
        this.this$0 = cameraVisibility;
    }

    public final void run() {
        this.this$0.updateCameraIsShowing();
    }
}

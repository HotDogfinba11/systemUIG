package com.google.android.systemui.columbus.gates;

/* compiled from: CameraVisibility.kt */
final class CameraVisibility$gateListener$1$onGateChanged$1 implements Runnable {
    final /* synthetic */ CameraVisibility this$0;

    CameraVisibility$gateListener$1$onGateChanged$1(CameraVisibility cameraVisibility) {
        this.this$0 = cameraVisibility;
    }

    public final void run() {
        this.this$0.updateCameraIsShowing();
    }
}

package com.android.systemui;

import android.hardware.camera2.CameraManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CameraAvailabilityListener.kt */
public final class CameraAvailabilityListener$availabilityCallback$1 extends CameraManager.AvailabilityCallback {
    final /* synthetic */ CameraAvailabilityListener this$0;

    CameraAvailabilityListener$availabilityCallback$1(CameraAvailabilityListener cameraAvailabilityListener) {
        this.this$0 = cameraAvailabilityListener;
    }

    public void onCameraClosed(String str) {
        Intrinsics.checkNotNullParameter(str, "cameraId");
        if (Intrinsics.areEqual(CameraAvailabilityListener.access$getTargetCameraId$p(this.this$0), str)) {
            CameraAvailabilityListener.access$notifyCameraInactive(this.this$0);
        }
    }

    public void onCameraOpened(String str, String str2) {
        Intrinsics.checkNotNullParameter(str, "cameraId");
        Intrinsics.checkNotNullParameter(str2, "packageId");
        if (Intrinsics.areEqual(CameraAvailabilityListener.access$getTargetCameraId$p(this.this$0), str) && !CameraAvailabilityListener.access$isExcluded(this.this$0, str2)) {
            CameraAvailabilityListener.access$notifyCameraActive(this.this$0);
        }
    }
}

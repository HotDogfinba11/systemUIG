package com.android.systemui.statusbar.policy;

/* compiled from: DeviceControlsController.kt */
public interface DeviceControlsController {

    /* compiled from: DeviceControlsController.kt */
    public interface Callback {
        void onControlsUpdate(Integer num);
    }

    void removeCallback();

    void setCallback(Callback callback);
}

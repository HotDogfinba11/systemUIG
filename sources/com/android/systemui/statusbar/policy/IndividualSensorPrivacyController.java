package com.android.systemui.statusbar.policy;

public interface IndividualSensorPrivacyController extends CallbackController<Callback> {

    public interface Callback {
        void onSensorBlockedChanged(int i, boolean z);
    }

    void init();

    boolean isSensorBlocked(int i);

    void setSensorBlocked(int i, int i2, boolean z);

    boolean supportsSensorToggle(int i);

    void suppressSensorPrivacyReminders(int i, boolean z);
}

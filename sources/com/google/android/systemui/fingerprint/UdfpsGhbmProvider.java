package com.google.android.systemui.fingerprint;

import android.util.Log;
import android.view.Surface;

public class UdfpsGhbmProvider {
    private static final String TAG = "UdfpsGhbmProvider";

    public void enableGhbm(Surface surface) {
        Log.v(TAG, "enableGhbm");
        Log.e(TAG, "udfps_ghbm_jni.so is missing - GHBM is not supported.");
    }

    public void disableGhbm(Surface surface) {
        Log.v(TAG, "disableGhbm");
        Log.e(TAG, "udfps_ghbm_jni.so is missing - GHBM is not supported.");
    }
}

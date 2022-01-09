package com.google.android.systemui.columbus.gates;

import android.telephony.TelephonyCallback;

/* compiled from: TelephonyActivity.kt */
public final class TelephonyActivity$phoneStateListener$1 implements TelephonyCallback.CallStateListener {
    final /* synthetic */ TelephonyActivity this$0;

    TelephonyActivity$phoneStateListener$1(TelephonyActivity telephonyActivity) {
        this.this$0 = telephonyActivity;
    }

    public void onCallStateChanged(int i) {
        TelephonyActivity telephonyActivity = this.this$0;
        telephonyActivity.isCallBlocked = telephonyActivity.isCallBlocked(Integer.valueOf(i));
        this.this$0.updateBlocking();
    }
}

package com.google.android.systemui.columbus.actions;

import android.telephony.TelephonyCallback;

/* compiled from: SilenceCall.kt */
public final class SilenceCall$phoneStateListener$1 implements TelephonyCallback.CallStateListener {
    final /* synthetic */ SilenceCall this$0;

    SilenceCall$phoneStateListener$1(SilenceCall silenceCall) {
        this.this$0 = silenceCall;
    }

    public void onCallStateChanged(int i) {
        SilenceCall silenceCall = this.this$0;
        silenceCall.isPhoneRinging = silenceCall.isPhoneRinging(i);
        this.this$0.updateAvailable();
    }
}

package com.google.android.systemui.columbus.gates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: UsbState.kt */
public final class UsbState$usbReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ UsbState this$0;

    UsbState$usbReceiver$1(UsbState usbState) {
        this.this$0 = usbState;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            UsbState usbState = this.this$0;
            boolean booleanExtra = intent.getBooleanExtra("connected", false);
            if (booleanExtra != usbState.usbConnected) {
                usbState.usbConnected = booleanExtra;
                usbState.blockForMillis(usbState.gateDuration);
            }
        }
    }
}

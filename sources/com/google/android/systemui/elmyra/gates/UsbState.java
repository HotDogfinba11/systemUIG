package com.google.android.systemui.elmyra.gates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.R$integer;

public class UsbState extends TransientGate {
    private boolean mUsbConnected;
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        /* class com.google.android.systemui.elmyra.gates.UsbState.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            boolean booleanExtra = intent.getBooleanExtra("connected", false);
            if (booleanExtra != UsbState.this.mUsbConnected) {
                UsbState.this.mUsbConnected = booleanExtra;
                UsbState.this.block();
            }
        }
    };

    public UsbState(Context context) {
        super(context, (long) context.getResources().getInteger(R$integer.elmyra_usb_gate_duration));
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        IntentFilter intentFilter = new IntentFilter("android.hardware.usb.action.USB_STATE");
        Intent registerReceiver = getContext().registerReceiver(null, intentFilter);
        if (registerReceiver != null) {
            this.mUsbConnected = registerReceiver.getBooleanExtra("connected", false);
        }
        getContext().registerReceiver(this.mUsbReceiver, intentFilter);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        getContext().unregisterReceiver(this.mUsbReceiver);
    }
}

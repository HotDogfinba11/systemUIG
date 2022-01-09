package com.google.android.systemui.elmyra.gates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.R$integer;

public class ChargingState extends TransientGate {
    private final BroadcastReceiver mPowerReceiver = new BroadcastReceiver() {
        /* class com.google.android.systemui.elmyra.gates.ChargingState.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            ChargingState.this.block();
        }
    };

    public ChargingState(Context context) {
        super(context, (long) context.getResources().getInteger(R$integer.elmyra_charging_gate_duration));
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        getContext().registerReceiver(this.mPowerReceiver, intentFilter);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        getContext().unregisterReceiver(this.mPowerReceiver);
    }
}

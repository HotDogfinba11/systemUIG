package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.content.IntentFilter;
import android.os.PowerManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PowerSaveState.kt */
public final class PowerSaveState extends Gate {
    private boolean batterySaverEnabled;
    private boolean isDeviceInteractive;
    private final PowerManager powerManager;
    private final PowerSaveState$receiver$1 receiver = new PowerSaveState$receiver$1(this);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PowerSaveState(Context context) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        this.powerManager = (PowerManager) context.getSystemService("power");
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onActivate() {
        IntentFilter intentFilter = new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        getContext().registerReceiver(this.receiver, intentFilter);
        refreshStatus();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onDeactivate() {
        getContext().unregisterReceiver(this.receiver);
    }

    /* access modifiers changed from: private */
    public final void refreshStatus() {
        android.os.PowerSaveState powerSaveState;
        PowerManager powerManager2 = this.powerManager;
        Boolean bool = null;
        Boolean valueOf = (powerManager2 == null || (powerSaveState = powerManager2.getPowerSaveState(13)) == null) ? null : Boolean.valueOf(powerSaveState.batterySaverEnabled);
        Boolean bool2 = Boolean.TRUE;
        this.batterySaverEnabled = Intrinsics.areEqual(valueOf, bool2);
        PowerManager powerManager3 = this.powerManager;
        if (powerManager3 != null) {
            bool = Boolean.valueOf(powerManager3.isInteractive());
        }
        boolean areEqual = Intrinsics.areEqual(bool, bool2);
        this.isDeviceInteractive = areEqual;
        setBlocking(this.batterySaverEnabled && !areEqual);
    }

    @Override // com.google.android.systemui.columbus.gates.Gate
    public String toString() {
        return super.toString() + "[batterySaverEnabled -> " + this.batterySaverEnabled + "; isDeviceInteractive -> " + this.isDeviceInteractive + ']';
    }
}

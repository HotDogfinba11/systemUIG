package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import android.content.Intent;
import com.google.android.systemui.elmyra.sensors.GestureSensor;

public class DismissTimer extends DeskClockAction {
    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.actions.DeskClockAction
    public String getAlertAction() {
        return "com.google.android.deskclock.action.TIMER_ALERT";
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.actions.DeskClockAction
    public String getDoneAction() {
        return "com.google.android.deskclock.action.TIMER_DONE";
    }

    @Override // com.google.android.systemui.elmyra.actions.DeskClockAction, com.google.android.systemui.elmyra.actions.Action
    public /* bridge */ /* synthetic */ boolean isAvailable() {
        return super.isAvailable();
    }

    @Override // com.google.android.systemui.elmyra.actions.DeskClockAction, com.google.android.systemui.elmyra.actions.Action
    public /* bridge */ /* synthetic */ void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        super.onTrigger(detectionProperties);
    }

    @Override // com.google.android.systemui.elmyra.actions.DeskClockAction, com.google.android.systemui.elmyra.actions.Action
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public DismissTimer(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.actions.DeskClockAction
    public Intent createDismissIntent() {
        return new Intent("android.intent.action.DISMISS_TIMER");
    }
}

package com.google.android.systemui.elmyra.actions;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.google.android.systemui.elmyra.UserContentObserver;
import com.google.android.systemui.elmyra.sensors.GestureSensor;

/* access modifiers changed from: package-private */
public abstract class DeskClockAction extends Action {
    private boolean mAlertFiring;
    private final BroadcastReceiver mAlertReceiver = new BroadcastReceiver() {
        /* class com.google.android.systemui.elmyra.actions.DeskClockAction.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DeskClockAction.this.getAlertAction())) {
                DeskClockAction.this.mAlertFiring = true;
            } else if (intent.getAction().equals(DeskClockAction.this.getDoneAction())) {
                DeskClockAction.this.mAlertFiring = false;
            }
            DeskClockAction.this.notifyListener();
        }
    };
    private boolean mReceiverRegistered;
    private final UserContentObserver mSettingsObserver;

    /* access modifiers changed from: protected */
    public abstract Intent createDismissIntent();

    /* access modifiers changed from: protected */
    public abstract String getAlertAction();

    /* access modifiers changed from: protected */
    public abstract String getDoneAction();

    DeskClockAction(Context context) {
        super(context, null);
        updateBroadcastReceiver();
        this.mSettingsObserver = new UserContentObserver(getContext(), Settings.Secure.getUriFor("assist_gesture_silence_alerts_enabled"), new DeskClockAction$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Uri uri) {
        updateBroadcastReceiver();
    }

    private void updateBroadcastReceiver() {
        boolean z = false;
        this.mAlertFiring = false;
        if (this.mReceiverRegistered) {
            getContext().unregisterReceiver(this.mAlertReceiver);
            this.mReceiverRegistered = false;
        }
        if (Settings.Secure.getIntForUser(getContext().getContentResolver(), "assist_gesture_silence_alerts_enabled", 1, -2) != 0) {
            z = true;
        }
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(getAlertAction());
            intentFilter.addAction(getDoneAction());
            getContext().registerReceiverAsUser(this.mAlertReceiver, UserHandle.CURRENT, intentFilter, "com.android.systemui.permission.SEND_ALERT_BROADCASTS", null);
            this.mReceiverRegistered = true;
        }
        notifyListener();
    }

    @Override // com.google.android.systemui.elmyra.actions.Action
    public boolean isAvailable() {
        return this.mAlertFiring;
    }

    @Override // com.google.android.systemui.elmyra.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        try {
            Intent createDismissIntent = createDismissIntent();
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
            createDismissIntent.setFlags(268435456);
            createDismissIntent.putExtra("android.intent.extra.REFERRER", Uri.parse("android-app://" + getContext().getPackageName()));
            getContext().startActivityAsUser(createDismissIntent, makeBasic.toBundle(), UserHandle.CURRENT);
        } catch (ActivityNotFoundException e) {
            Log.e("Elmyra/DeskClockAction", "Failed to dismiss alert", e);
        }
        this.mAlertFiring = false;
        notifyListener();
    }

    @Override // com.google.android.systemui.elmyra.actions.Action
    public String toString() {
        return super.toString() + " [mReceiverRegistered -> " + this.mReceiverRegistered + "]";
    }
}

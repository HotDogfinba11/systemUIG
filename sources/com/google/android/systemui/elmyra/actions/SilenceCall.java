package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.google.android.systemui.elmyra.UserContentObserver;
import com.google.android.systemui.elmyra.sensors.GestureSensor;

public class SilenceCall extends Action {
    private boolean mIsPhoneRinging;
    private final TelephonyCallback.CallStateListener mPhoneStateListener = new TelephonyCallback.CallStateListener() {
        /* class com.google.android.systemui.elmyra.actions.SilenceCall.AnonymousClass1 */

        public void onCallStateChanged(int i) {
            boolean isPhoneRinging = SilenceCall.this.isPhoneRinging(i);
            if (SilenceCall.this.mIsPhoneRinging != isPhoneRinging) {
                SilenceCall.this.mIsPhoneRinging = isPhoneRinging;
                SilenceCall.this.notifyListener();
            }
        }
    };
    private final UserContentObserver mSettingsObserver;
    private boolean mSilenceSettingEnabled;
    private final TelecomManager mTelecomManager;
    private final TelephonyListenerManager mTelephonyListenerManager;
    private final TelephonyManager mTelephonyManager;

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isPhoneRinging(int i) {
        return i == 1;
    }

    public SilenceCall(Context context, TelephonyListenerManager telephonyListenerManager) {
        super(context, null);
        this.mTelecomManager = (TelecomManager) context.getSystemService(TelecomManager.class);
        this.mTelephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.mTelephonyListenerManager = telephonyListenerManager;
        updatePhoneStateListener();
        this.mSettingsObserver = new UserContentObserver(getContext(), Settings.Secure.getUriFor("assist_gesture_silence_alerts_enabled"), new SilenceCall$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Uri uri) {
        updatePhoneStateListener();
    }

    private void updatePhoneStateListener() {
        boolean z = true;
        if (Settings.Secure.getIntForUser(getContext().getContentResolver(), "assist_gesture_silence_alerts_enabled", 1, -2) == 0) {
            z = false;
        }
        if (z != this.mSilenceSettingEnabled) {
            this.mSilenceSettingEnabled = z;
            if (z) {
                this.mTelephonyListenerManager.addCallStateListener(this.mPhoneStateListener);
            } else {
                this.mTelephonyListenerManager.removeCallStateListener(this.mPhoneStateListener);
            }
            this.mIsPhoneRinging = isPhoneRinging(this.mTelephonyManager.getCallState());
            notifyListener();
        }
    }

    @Override // com.google.android.systemui.elmyra.actions.Action
    public boolean isAvailable() {
        if (this.mSilenceSettingEnabled) {
            return this.mIsPhoneRinging;
        }
        return false;
    }

    @Override // com.google.android.systemui.elmyra.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.mTelecomManager.silenceRinger();
    }

    @Override // com.google.android.systemui.elmyra.actions.Action
    public String toString() {
        return super.toString() + " [mSilenceSettingEnabled -> " + this.mSilenceSettingEnabled + "]";
    }
}

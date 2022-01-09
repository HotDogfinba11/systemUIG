package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import com.android.systemui.telephony.TelephonyListenerManager;

public class TelephonyActivity extends Gate {
    private boolean mIsCallBlocked;
    private final TelephonyCallback.CallStateListener mPhoneStateListener = new TelephonyCallback.CallStateListener() {
        /* class com.google.android.systemui.elmyra.gates.TelephonyActivity.AnonymousClass1 */

        public void onCallStateChanged(int i) {
            boolean isCallBlocked = TelephonyActivity.this.isCallBlocked(i);
            if (isCallBlocked != TelephonyActivity.this.mIsCallBlocked) {
                TelephonyActivity.this.mIsCallBlocked = isCallBlocked;
                TelephonyActivity.this.notifyListener();
            }
        }
    };
    private final TelephonyListenerManager mTelephonyListenerManager;
    private final TelephonyManager mTelephonyManager;

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isCallBlocked(int i) {
        return i == 2;
    }

    public TelephonyActivity(Context context, TelephonyListenerManager telephonyListenerManager) {
        super(context);
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mTelephonyListenerManager = telephonyListenerManager;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        this.mIsCallBlocked = isCallBlocked(this.mTelephonyManager.getCallState());
        this.mTelephonyListenerManager.addCallStateListener(this.mPhoneStateListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        this.mTelephonyListenerManager.removeCallStateListener(this.mPhoneStateListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public boolean isBlocked() {
        return this.mIsCallBlocked;
    }
}

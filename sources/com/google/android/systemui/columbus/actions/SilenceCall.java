package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SilenceCall.kt */
public final class SilenceCall extends Action {
    public static final Companion Companion = new Companion(null);
    private final SilenceCall$gateListener$1 gateListener;
    private boolean isPhoneRinging;
    private final SilenceCall$phoneStateListener$1 phoneStateListener = new SilenceCall$phoneStateListener$1(this);
    private final SilenceAlertsDisabled silenceAlertsDisabled;
    private final String tag = "Columbus/SilenceCall";
    private final Lazy<TelecomManager> telecomManager;
    private final Lazy<TelephonyListenerManager> telephonyListenerManager;
    private final Lazy<TelephonyManager> telephonyManager;

    /* access modifiers changed from: private */
    public final boolean isPhoneRinging(int i) {
        return i == 1;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SilenceCall(Context context, SilenceAlertsDisabled silenceAlertsDisabled2, Lazy<TelecomManager> lazy, Lazy<TelephonyManager> lazy2, Lazy<TelephonyListenerManager> lazy3) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(silenceAlertsDisabled2, "silenceAlertsDisabled");
        Intrinsics.checkNotNullParameter(lazy, "telecomManager");
        Intrinsics.checkNotNullParameter(lazy2, "telephonyManager");
        Intrinsics.checkNotNullParameter(lazy3, "telephonyListenerManager");
        this.silenceAlertsDisabled = silenceAlertsDisabled2;
        this.telecomManager = lazy;
        this.telephonyManager = lazy2;
        this.telephonyListenerManager = lazy3;
        SilenceCall$gateListener$1 silenceCall$gateListener$1 = new SilenceCall$gateListener$1(this);
        this.gateListener = silenceCall$gateListener$1;
        silenceAlertsDisabled2.registerListener(silenceCall$gateListener$1);
        updatePhoneStateListener();
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig() {
        return this.tag;
    }

    /* access modifiers changed from: private */
    public final void updatePhoneStateListener() {
        if (this.silenceAlertsDisabled.isBlocking()) {
            this.telephonyListenerManager.get().removeCallStateListener(this.phoneStateListener);
        } else {
            this.telephonyListenerManager.get().addCallStateListener(this.phoneStateListener);
        }
        this.isPhoneRinging = isPhoneRinging(this.telephonyManager.get().getCallState());
        updateAvailable();
    }

    /* access modifiers changed from: private */
    public final void updateAvailable() {
        setAvailable(!this.silenceAlertsDisabled.isBlocking() && this.isPhoneRinging);
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.telecomManager.get().silenceRinger();
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String toString() {
        return super.toString() + " [isPhoneRinging -> " + this.isPhoneRinging + ']';
    }

    /* compiled from: SilenceCall.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

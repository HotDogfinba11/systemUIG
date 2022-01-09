package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.util.Log;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Optional;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UnpinNotifications.kt */
public final class UnpinNotifications extends Action {
    public static final Companion Companion = new Companion(null);
    private final UnpinNotifications$gateListener$1 gateListener;
    private boolean hasPinnedHeadsUp;
    private final UnpinNotifications$headsUpChangedListener$1 headsUpChangedListener;
    private final HeadsUpManager headsUpManager;
    private final SilenceAlertsDisabled silenceAlertsDisabled;
    private final String tag = "Columbus/UnpinNotif";

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UnpinNotifications(Context context, SilenceAlertsDisabled silenceAlertsDisabled2, Optional<HeadsUpManager> optional) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(silenceAlertsDisabled2, "silenceAlertsDisabled");
        Intrinsics.checkNotNullParameter(optional, "headsUpManagerOptional");
        this.silenceAlertsDisabled = silenceAlertsDisabled2;
        HeadsUpManager orElse = optional.orElse(null);
        this.headsUpManager = orElse;
        this.headsUpChangedListener = new UnpinNotifications$headsUpChangedListener$1(this);
        UnpinNotifications$gateListener$1 unpinNotifications$gateListener$1 = new UnpinNotifications$gateListener$1(this);
        this.gateListener = unpinNotifications$gateListener$1;
        if (orElse == null) {
            Log.w("Columbus/UnpinNotif", "No HeadsUpManager");
        } else {
            silenceAlertsDisabled2.registerListener(unpinNotifications$gateListener$1);
        }
        updateAvailable();
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig() {
        return this.tag;
    }

    /* access modifiers changed from: private */
    public final void onSilenceAlertsEnabled() {
        HeadsUpManager headsUpManager2 = this.headsUpManager;
        if (headsUpManager2 != null) {
            headsUpManager2.addListener(this.headsUpChangedListener);
        }
        HeadsUpManager headsUpManager3 = this.headsUpManager;
        this.hasPinnedHeadsUp = headsUpManager3 == null ? false : headsUpManager3.hasPinnedHeadsUp();
    }

    /* access modifiers changed from: private */
    public final void onSilenceAlertsDisabled() {
        HeadsUpManager headsUpManager2 = this.headsUpManager;
        if (headsUpManager2 != null) {
            headsUpManager2.removeListener(this.headsUpChangedListener);
        }
    }

    /* access modifiers changed from: private */
    public final void updateAvailable() {
        setAvailable(!this.silenceAlertsDisabled.isBlocking() && this.hasPinnedHeadsUp);
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        HeadsUpManager headsUpManager2 = this.headsUpManager;
        if (headsUpManager2 != null) {
            headsUpManager2.unpinAll(true);
        }
    }

    /* compiled from: UnpinNotifications.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

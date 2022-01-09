package com.google.android.systemui.columbus.gates;

import android.content.Context;
import com.android.systemui.util.sensors.ProximitySensor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Proximity.kt */
public class Proximity extends Gate {
    public static final Companion Companion = new Companion(null);
    private final Proximity$proximityListener$1 proximityListener = new Proximity$proximityListener$1(this);
    private final ProximitySensor proximitySensor;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Proximity(Context context, ProximitySensor proximitySensor2) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(proximitySensor2, "proximitySensor");
        this.proximitySensor = proximitySensor2;
        proximitySensor2.setTag("Columbus/Proximity");
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onActivate() {
        this.proximitySensor.register(this.proximityListener);
        updateBlocking();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onDeactivate() {
        this.proximitySensor.unregister(this.proximityListener);
    }

    /* access modifiers changed from: private */
    public final void updateBlocking() {
        setBlocking(!Intrinsics.areEqual(this.proximitySensor.isNear(), Boolean.FALSE));
    }

    /* compiled from: Proximity.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

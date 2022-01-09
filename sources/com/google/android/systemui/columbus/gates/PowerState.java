package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.PowerManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import dagger.Lazy;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PowerState.kt */
public class PowerState extends Gate {
    private final PowerManager powerManager;
    private final Lazy<WakefulnessLifecycle> wakefulnessLifecycle;
    private final PowerState$wakefulnessLifecycleObserver$1 wakefulnessLifecycleObserver = new PowerState$wakefulnessLifecycleObserver$1(this);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PowerState(Context context, Lazy<WakefulnessLifecycle> lazy) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(lazy, "wakefulnessLifecycle");
        this.wakefulnessLifecycle = lazy;
        this.powerManager = (PowerManager) context.getSystemService("power");
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onActivate() {
        this.wakefulnessLifecycle.get().addObserver(this.wakefulnessLifecycleObserver);
        updateBlocking();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onDeactivate() {
        this.wakefulnessLifecycle.get().removeObserver(this.wakefulnessLifecycleObserver);
    }

    /* access modifiers changed from: private */
    public final void updateBlocking() {
        PowerManager powerManager2 = this.powerManager;
        setBlocking(Intrinsics.areEqual(powerManager2 == null ? null : Boolean.valueOf(powerManager2.isInteractive()), Boolean.FALSE));
    }
}

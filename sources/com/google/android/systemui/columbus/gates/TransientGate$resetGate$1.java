package com.google.android.systemui.columbus.gates;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: TransientGate.kt */
public final class TransientGate$resetGate$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ TransientGate this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TransientGate$resetGate$1(TransientGate transientGate) {
        super(0);
        this.this$0 = transientGate;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        this.this$0.setBlocking(false);
    }
}

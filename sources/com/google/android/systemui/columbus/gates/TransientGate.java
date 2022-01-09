package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Handler;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TransientGate.kt */
public abstract class TransientGate extends Gate {
    private final Function0<Unit> resetGate = new TransientGate$resetGate$1(this);
    private final Handler resetGateHandler;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TransientGate(Context context, Handler handler) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(handler, "resetGateHandler");
        this.resetGateHandler = handler;
    }

    /* access modifiers changed from: protected */
    public final void blockForMillis(long j) {
        this.resetGateHandler.removeCallbacks(new TransientGate$sam$java_lang_Runnable$0(this.resetGate));
        setBlocking(true);
        this.resetGateHandler.postDelayed(new TransientGate$sam$java_lang_Runnable$0(this.resetGate), j);
    }
}

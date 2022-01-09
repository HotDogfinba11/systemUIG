package com.google.android.systemui.columbus.gates;

import kotlin.jvm.functions.Function0;

/* access modifiers changed from: package-private */
/* compiled from: TransientGate.kt */
public final class TransientGate$sam$java_lang_Runnable$0 implements Runnable {
    private final /* synthetic */ Function0 function;

    TransientGate$sam$java_lang_Runnable$0(Function0 function0) {
        this.function = function0;
    }

    public final /* synthetic */ void run() {
        this.function.invoke();
    }
}

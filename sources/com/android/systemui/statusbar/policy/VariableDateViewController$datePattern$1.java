package com.android.systemui.statusbar.policy;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* access modifiers changed from: package-private */
/* compiled from: VariableDateViewController.kt */
public /* synthetic */ class VariableDateViewController$datePattern$1 extends FunctionReferenceImpl implements Function0<Unit> {
    VariableDateViewController$datePattern$1(VariableDateViewController variableDateViewController) {
        super(0, variableDateViewController, VariableDateViewController.class, "updateClock", "updateClock()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        ((VariableDateViewController) this.receiver).updateClock();
    }
}

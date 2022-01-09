package com.android.systemui.statusbar.policy;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: VariableDateViewController.kt */
/* synthetic */ class VariableDateViewController$onViewAttached$1 extends FunctionReferenceImpl implements Function0<Unit> {
    VariableDateViewController$onViewAttached$1(VariableDateViewController variableDateViewController) {
        super(0, variableDateViewController, VariableDateViewController.class, "updateClock", "updateClock()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        ((VariableDateViewController) this.receiver).updateClock();
    }
}

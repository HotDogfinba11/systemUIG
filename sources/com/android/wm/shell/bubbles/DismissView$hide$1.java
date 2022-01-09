package com.android.wm.shell.bubbles;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: DismissView.kt */
public final class DismissView$hide$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ DismissView this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DismissView$hide$1(DismissView dismissView) {
        super(0);
        this.this$0 = dismissView;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        this.this$0.setVisibility(4);
    }
}

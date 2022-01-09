package com.android.systemui.controls.ui;

import android.content.Intent;
import android.service.controls.Control;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ControlActionCoordinatorImpl.kt */
final class ControlActionCoordinatorImpl$longPress$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ ControlViewHolder $cvh;
    final /* synthetic */ ControlActionCoordinatorImpl this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ControlActionCoordinatorImpl$longPress$1(ControlViewHolder controlViewHolder, ControlActionCoordinatorImpl controlActionCoordinatorImpl) {
        super(0);
        this.$cvh = controlViewHolder;
        this.this$0 = controlActionCoordinatorImpl;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        Control control = this.$cvh.getCws().getControl();
        if (control != null) {
            ControlViewHolder controlViewHolder = this.$cvh;
            ControlActionCoordinatorImpl controlActionCoordinatorImpl = this.this$0;
            controlViewHolder.getLayout().performHapticFeedback(0);
            Intent intent = control.getAppIntent().getIntent();
            Intrinsics.checkNotNullExpressionValue(intent, "it.getAppIntent().getIntent()");
            ControlActionCoordinatorImpl.access$showDetail(controlActionCoordinatorImpl, controlViewHolder, intent);
        }
    }
}

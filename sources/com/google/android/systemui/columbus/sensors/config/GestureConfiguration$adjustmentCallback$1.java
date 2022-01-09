package com.google.android.systemui.columbus.sensors.config;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: GestureConfiguration.kt */
public final class GestureConfiguration$adjustmentCallback$1 extends Lambda implements Function1<Adjustment, Unit> {
    final /* synthetic */ GestureConfiguration this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    GestureConfiguration$adjustmentCallback$1(GestureConfiguration gestureConfiguration) {
        super(1);
        this.this$0 = gestureConfiguration;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Adjustment adjustment) {
        invoke(adjustment);
        return Unit.INSTANCE;
    }

    public final void invoke(Adjustment adjustment) {
        Intrinsics.checkNotNullParameter(adjustment, "it");
        this.this$0.updateSensitivity();
    }
}

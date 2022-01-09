package com.google.android.systemui.columbus.sensors;

import com.google.android.systemui.columbus.ColumbusEvent;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor$startRecognizer$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ CHREGestureSensor this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CHREGestureSensor$startRecognizer$1(CHREGestureSensor cHREGestureSensor) {
        super(0);
        this.this$0 = cHREGestureSensor;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        this.this$0.uiEventLogger.log(ColumbusEvent.COLUMBUS_MODE_LOW_POWER_ACTIVE);
    }
}

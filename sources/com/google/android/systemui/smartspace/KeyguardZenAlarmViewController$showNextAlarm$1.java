package com.google.android.systemui.smartspace;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* access modifiers changed from: package-private */
/* compiled from: KeyguardZenAlarmViewController.kt */
public /* synthetic */ class KeyguardZenAlarmViewController$showNextAlarm$1 extends FunctionReferenceImpl implements Function0<Unit> {
    KeyguardZenAlarmViewController$showNextAlarm$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController) {
        super(0, keyguardZenAlarmViewController, KeyguardZenAlarmViewController.class, "showAlarm", "showAlarm()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        ((KeyguardZenAlarmViewController) this.receiver).showAlarm();
    }
}

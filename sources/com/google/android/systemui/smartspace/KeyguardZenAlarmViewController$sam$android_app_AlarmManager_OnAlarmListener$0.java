package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import kotlin.jvm.functions.Function0;

/* access modifiers changed from: package-private */
/* compiled from: KeyguardZenAlarmViewController.kt */
public final class KeyguardZenAlarmViewController$sam$android_app_AlarmManager_OnAlarmListener$0 implements AlarmManager.OnAlarmListener {
    private final /* synthetic */ Function0 function;

    KeyguardZenAlarmViewController$sam$android_app_AlarmManager_OnAlarmListener$0(Function0 function0) {
        this.function = function0;
    }

    public final /* synthetic */ void onAlarm() {
        this.function.invoke();
    }
}

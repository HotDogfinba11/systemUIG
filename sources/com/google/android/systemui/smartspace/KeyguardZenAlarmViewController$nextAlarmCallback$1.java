package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import com.android.systemui.statusbar.policy.NextAlarmController;

/* access modifiers changed from: package-private */
/* compiled from: KeyguardZenAlarmViewController.kt */
public final class KeyguardZenAlarmViewController$nextAlarmCallback$1 implements NextAlarmController.NextAlarmChangeCallback {
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

    KeyguardZenAlarmViewController$nextAlarmCallback$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController) {
        this.this$0 = keyguardZenAlarmViewController;
    }

    @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        this.this$0.updateNextAlarm();
    }
}

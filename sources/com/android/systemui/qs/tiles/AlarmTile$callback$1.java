package com.android.systemui.qs.tiles;

import android.app.AlarmManager;
import com.android.systemui.statusbar.policy.NextAlarmController;

/* access modifiers changed from: package-private */
/* compiled from: AlarmTile.kt */
public final class AlarmTile$callback$1 implements NextAlarmController.NextAlarmChangeCallback {
    final /* synthetic */ AlarmTile this$0;

    AlarmTile$callback$1(AlarmTile alarmTile) {
        this.this$0 = alarmTile;
    }

    @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        this.this$0.lastAlarmInfo = alarmClockInfo;
        this.this$0.refreshState();
    }
}

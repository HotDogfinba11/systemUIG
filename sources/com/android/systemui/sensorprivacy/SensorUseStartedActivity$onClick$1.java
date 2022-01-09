package com.android.systemui.sensorprivacy;

import android.os.Handler;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SensorUseStartedActivity.kt */
final class SensorUseStartedActivity$onClick$1 implements ActivityStarter.OnDismissAction {
    final /* synthetic */ SensorUseStartedActivity this$0;

    SensorUseStartedActivity$onClick$1(SensorUseStartedActivity sensorUseStartedActivity) {
        this.this$0 = sensorUseStartedActivity;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        Handler handler = this.this$0.bgHandler;
        final SensorUseStartedActivity sensorUseStartedActivity = this.this$0;
        handler.postDelayed(new Runnable() {
            /* class com.android.systemui.sensorprivacy.SensorUseStartedActivity$onClick$1.AnonymousClass1 */

            public final void run() {
                sensorUseStartedActivity.disableSensorPrivacy();
                String str = sensorUseStartedActivity.sensorUsePackageName;
                if (str != null) {
                    FrameworkStatsLog.write(382, 1, str);
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("sensorUsePackageName");
                    throw null;
                }
            }
        }, 200);
        return false;
    }
}

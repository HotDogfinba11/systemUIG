package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.tuner.TunerService;

public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda9 implements TunerService.Tunable {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda9(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        this.f$0.lambda$attach$5(str, str2);
    }
}

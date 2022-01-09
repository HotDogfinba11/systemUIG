package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.KeyguardBypassController;

public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda7 implements NotificationStackScrollLayout.KeyguardBypassEnabledProvider {
    public final /* synthetic */ KeyguardBypassController f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda7(KeyguardBypassController keyguardBypassController) {
        this.f$0 = keyguardBypassController;
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.KeyguardBypassEnabledProvider
    public final boolean getBypassEnabled() {
        return this.f$0.getBypassEnabled();
    }
}

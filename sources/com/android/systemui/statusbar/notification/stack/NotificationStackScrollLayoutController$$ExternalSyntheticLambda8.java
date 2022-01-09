package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;

public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda8 implements HeadsUpManagerPhone.AnimationStateHandler {
    public final /* synthetic */ NotificationStackScrollLayout f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda8(NotificationStackScrollLayout notificationStackScrollLayout) {
        this.f$0 = notificationStackScrollLayout;
    }

    @Override // com.android.systemui.statusbar.phone.HeadsUpManagerPhone.AnimationStateHandler
    public final void setHeadsUpGoingAwayAnimationsAllowed(boolean z) {
        this.f$0.setHeadsUpGoingAwayAnimationsAllowed(z);
    }
}

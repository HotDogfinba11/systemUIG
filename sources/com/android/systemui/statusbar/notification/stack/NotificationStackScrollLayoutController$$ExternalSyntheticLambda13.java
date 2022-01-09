package com.android.systemui.statusbar.notification.stack;

import java.util.function.BiConsumer;

public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda13 implements BiConsumer {
    public final /* synthetic */ NotificationRoundnessManager f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda13(NotificationRoundnessManager notificationRoundnessManager) {
        this.f$0 = notificationRoundnessManager;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        this.f$0.setExpanded(((Float) obj).floatValue(), ((Float) obj2).floatValue());
    }
}

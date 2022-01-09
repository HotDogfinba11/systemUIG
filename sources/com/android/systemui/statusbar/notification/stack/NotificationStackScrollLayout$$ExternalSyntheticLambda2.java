package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.systemui.statusbar.notification.row.FooterView;

public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda2 implements View.OnClickListener {
    public final /* synthetic */ NotificationStackScrollLayout f$0;
    public final /* synthetic */ FooterView f$1;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda2(NotificationStackScrollLayout notificationStackScrollLayout, FooterView footerView) {
        this.f$0 = notificationStackScrollLayout;
        this.f$1 = footerView;
    }

    public final void onClick(View view) {
        this.f$0.lambda$inflateFooterView$7(this.f$1, view);
    }
}

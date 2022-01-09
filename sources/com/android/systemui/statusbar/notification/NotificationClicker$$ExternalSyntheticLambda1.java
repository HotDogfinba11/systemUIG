package com.android.systemui.statusbar.notification;

import android.view.View;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.function.Consumer;

public final /* synthetic */ class NotificationClicker$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ View f$0;

    public /* synthetic */ NotificationClicker$$ExternalSyntheticLambda1(View view) {
        this.f$0 = view;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NotificationClicker.lambda$onClick$0(this.f$0, (StatusBar) obj);
    }
}

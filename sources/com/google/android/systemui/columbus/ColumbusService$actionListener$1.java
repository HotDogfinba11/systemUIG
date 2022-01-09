package com.google.android.systemui.columbus;

import com.google.android.systemui.columbus.actions.Action;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusService.kt */
public final class ColumbusService$actionListener$1 implements Action.Listener {
    final /* synthetic */ ColumbusService this$0;

    ColumbusService$actionListener$1(ColumbusService columbusService) {
        this.this$0 = columbusService;
    }

    @Override // com.google.android.systemui.columbus.actions.Action.Listener
    public void onActionAvailabilityChanged(Action action) {
        Intrinsics.checkNotNullParameter(action, "action");
        ColumbusService.access$updateSensorListener(this.this$0);
    }
}

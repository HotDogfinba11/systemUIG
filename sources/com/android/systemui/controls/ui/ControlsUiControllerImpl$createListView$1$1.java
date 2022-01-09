package com.android.systemui.controls.ui;

import android.view.View;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsUiControllerImpl.kt */
final class ControlsUiControllerImpl$createListView$1$1 implements View.OnClickListener {
    final /* synthetic */ ControlsUiControllerImpl this$0;

    ControlsUiControllerImpl$createListView$1$1(ControlsUiControllerImpl controlsUiControllerImpl) {
        this.this$0 = controlsUiControllerImpl;
    }

    public final void onClick(View view) {
        Intrinsics.checkNotNullParameter(view, "$noName_0");
        Runnable access$getOnDismiss$p = ControlsUiControllerImpl.access$getOnDismiss$p(this.this$0);
        if (access$getOnDismiss$p != null) {
            access$getOnDismiss$p.run();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("onDismiss");
            throw null;
        }
    }
}

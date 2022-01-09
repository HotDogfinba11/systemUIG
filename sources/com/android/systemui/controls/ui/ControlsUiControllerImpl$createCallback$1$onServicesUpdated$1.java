package com.android.systemui.controls.ui;

import android.view.ViewGroup;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsUiControllerImpl.kt */
final class ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1 implements Runnable {
    final /* synthetic */ List<SelectionItem> $lastItems;
    final /* synthetic */ Function1<List<SelectionItem>, Unit> $onResult;
    final /* synthetic */ ControlsUiControllerImpl this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: kotlin.jvm.functions.Function1<? super java.util.List<com.android.systemui.controls.ui.SelectionItem>, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1(ControlsUiControllerImpl controlsUiControllerImpl, List<SelectionItem> list, Function1<? super List<SelectionItem>, Unit> function1) {
        this.this$0 = controlsUiControllerImpl;
        this.$lastItems = list;
        this.$onResult = function1;
    }

    public final void run() {
        ViewGroup viewGroup = this.this$0.parent;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            if (this.$lastItems.size() > 0) {
                this.$onResult.invoke(this.$lastItems);
                return;
            }
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        throw null;
    }
}

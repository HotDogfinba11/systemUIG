package com.android.systemui.controls.ui;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ControlsUiControllerImpl.kt */
public /* synthetic */ class ControlsUiControllerImpl$show$1 extends FunctionReferenceImpl implements Function1<List<? extends SelectionItem>, Unit> {
    ControlsUiControllerImpl$show$1(ControlsUiControllerImpl controlsUiControllerImpl) {
        super(1, controlsUiControllerImpl, ControlsUiControllerImpl.class, "showSeedingView", "showSeedingView(Ljava/util/List;)V", 0);
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(List<? extends SelectionItem> list) {
        invoke((List<SelectionItem>) list);
        return Unit.INSTANCE;
    }

    public final void invoke(List<SelectionItem> list) {
        Intrinsics.checkNotNullParameter(list, "p0");
        ((ControlsUiControllerImpl) this.receiver).showSeedingView(list);
    }
}

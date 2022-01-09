package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$createCallback$1 implements ControlsListingController.ControlsListingCallback {
    final /* synthetic */ Function1<List<SelectionItem>, Unit> $onResult;
    final /* synthetic */ ControlsUiControllerImpl this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: kotlin.jvm.functions.Function1<? super java.util.List<com.android.systemui.controls.ui.SelectionItem>, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    ControlsUiControllerImpl$createCallback$1(ControlsUiControllerImpl controlsUiControllerImpl, Function1<? super List<SelectionItem>, Unit> function1) {
        this.this$0 = controlsUiControllerImpl;
        this.$onResult = function1;
    }

    @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
    public void onServicesUpdated(List<ControlsServiceInfo> list) {
        Intrinsics.checkNotNullParameter(list, "serviceInfos");
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (T t : list) {
            int i = t.getServiceInfo().applicationInfo.uid;
            CharSequence loadLabel = t.loadLabel();
            Intrinsics.checkNotNullExpressionValue(loadLabel, "it.loadLabel()");
            Drawable loadIcon = t.loadIcon();
            Intrinsics.checkNotNullExpressionValue(loadIcon, "it.loadIcon()");
            ComponentName componentName = t.componentName;
            Intrinsics.checkNotNullExpressionValue(componentName, "it.componentName");
            arrayList.add(new SelectionItem(loadLabel, "", loadIcon, componentName, i));
        }
        this.this$0.getUiExecutor().execute(new ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1(this.this$0, arrayList, this.$onResult));
    }
}

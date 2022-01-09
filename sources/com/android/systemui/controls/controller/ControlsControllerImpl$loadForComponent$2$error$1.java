package com.android.systemui.controls.controller;

import android.content.ComponentName;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;

/* compiled from: ControlsControllerImpl.kt */
final class ControlsControllerImpl$loadForComponent$2$error$1 implements Runnable {
    final /* synthetic */ ComponentName $componentName;
    final /* synthetic */ Consumer<ControlsController.LoadData> $dataCallback;
    final /* synthetic */ ControlsControllerImpl this$0;

    ControlsControllerImpl$loadForComponent$2$error$1(ComponentName componentName, Consumer<ControlsController.LoadData> consumer, ControlsControllerImpl controlsControllerImpl) {
        this.$componentName = componentName;
        this.$dataCallback = consumer;
        this.this$0 = controlsControllerImpl;
    }

    public final void run() {
        List<StructureInfo> structuresForComponent = Favorites.INSTANCE.getStructuresForComponent(this.$componentName);
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        ComponentName componentName = this.$componentName;
        ArrayList<ControlStatus> arrayList = new ArrayList();
        for (T t : structuresForComponent) {
            List<ControlInfo> controls = t.getControls();
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controls, 10));
            Iterator<T> it = controls.iterator();
            while (it.hasNext()) {
                arrayList2.add(controlsControllerImpl.createRemovedStatus(componentName, it.next(), t.getStructure(), false));
            }
            boolean unused = CollectionsKt__MutableCollectionsKt.addAll(arrayList, arrayList2);
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        for (ControlStatus controlStatus : arrayList) {
            arrayList3.add(controlStatus.getControl().getControlId());
        }
        this.$dataCallback.accept(ControlsControllerKt.createLoadDataObject(arrayList, arrayList3, true));
    }
}

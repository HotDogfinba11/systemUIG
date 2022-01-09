package com.android.systemui.controls.ui;

import android.view.ViewGroup;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsUiControllerImpl.kt */
final class ControlsUiControllerImpl$onSeedingComplete$1 implements Consumer<Boolean> {
    final /* synthetic */ ControlsUiControllerImpl this$0;

    ControlsUiControllerImpl$onSeedingComplete$1(ControlsUiControllerImpl controlsUiControllerImpl) {
        this.this$0 = controlsUiControllerImpl;
    }

    public final void accept(Boolean bool) {
        T t;
        Intrinsics.checkNotNullExpressionValue(bool, "accepted");
        if (bool.booleanValue()) {
            ControlsUiControllerImpl controlsUiControllerImpl = this.this$0;
            Iterator<T> it = controlsUiControllerImpl.getControlsController().get().getFavorites().iterator();
            if (!it.hasNext()) {
                t = null;
            } else {
                t = it.next();
                if (it.hasNext()) {
                    int size = t.getControls().size();
                    do {
                        T next = it.next();
                        int size2 = next.getControls().size();
                        if (size < size2) {
                            t = next;
                            size = size2;
                        }
                    } while (it.hasNext());
                }
            }
            T t2 = t;
            if (t2 == null) {
                t2 = ControlsUiControllerImpl.EMPTY_STRUCTURE;
            }
            controlsUiControllerImpl.selectedStructure = t2;
            ControlsUiControllerImpl controlsUiControllerImpl2 = this.this$0;
            controlsUiControllerImpl2.updatePreferences(controlsUiControllerImpl2.selectedStructure);
        }
        ControlsUiControllerImpl controlsUiControllerImpl3 = this.this$0;
        ViewGroup viewGroup = controlsUiControllerImpl3.parent;
        if (viewGroup != null) {
            controlsUiControllerImpl3.reload(viewGroup);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            throw null;
        }
    }
}

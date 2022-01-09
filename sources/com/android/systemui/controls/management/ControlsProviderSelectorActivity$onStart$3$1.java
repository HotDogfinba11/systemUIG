package com.android.systemui.controls.management;

import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsProviderSelectorActivity.kt */
public final class ControlsProviderSelectorActivity$onStart$3$1 extends RecyclerView.AdapterDataObserver {
    private boolean hasAnimated;
    final /* synthetic */ ControlsProviderSelectorActivity this$0;

    ControlsProviderSelectorActivity$onStart$3$1(ControlsProviderSelectorActivity controlsProviderSelectorActivity) {
        this.this$0 = controlsProviderSelectorActivity;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
    public void onChanged() {
        if (!this.hasAnimated) {
            this.hasAnimated = true;
            ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
            RecyclerView recyclerView = this.this$0.recyclerView;
            if (recyclerView != null) {
                controlsAnimations.enterAnimation(recyclerView).start();
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                throw null;
            }
        }
    }
}

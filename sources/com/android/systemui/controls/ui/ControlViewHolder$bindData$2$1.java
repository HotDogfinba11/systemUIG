package com.android.systemui.controls.ui;

import android.view.View;

/* access modifiers changed from: package-private */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$bindData$2$1 implements View.OnLongClickListener {
    final /* synthetic */ ControlViewHolder this$0;

    ControlViewHolder$bindData$2$1(ControlViewHolder controlViewHolder) {
        this.this$0 = controlViewHolder;
    }

    public final boolean onLongClick(View view) {
        this.this$0.getControlActionCoordinator().longPress(this.this$0);
        return true;
    }
}

package com.android.systemui.controls.ui;

import android.view.View;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ThumbnailBehavior.kt */
final class ThumbnailBehavior$initialize$1 implements View.OnClickListener {
    final /* synthetic */ ControlViewHolder $cvh;
    final /* synthetic */ ThumbnailBehavior this$0;

    ThumbnailBehavior$initialize$1(ControlViewHolder controlViewHolder, ThumbnailBehavior thumbnailBehavior) {
        this.$cvh = controlViewHolder;
        this.this$0 = thumbnailBehavior;
    }

    public final void onClick(View view) {
        ControlActionCoordinator controlActionCoordinator = this.$cvh.getControlActionCoordinator();
        ControlViewHolder controlViewHolder = this.$cvh;
        String templateId = this.this$0.getTemplate().getTemplateId();
        Intrinsics.checkNotNullExpressionValue(templateId, "template.getTemplateId()");
        controlActionCoordinator.touch(controlViewHolder, templateId, this.this$0.getControl());
    }
}

package com.android.systemui.statusbar.notification.row;

import android.view.View;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ChannelEditorDialogController.kt */
public final class ChannelEditorDialogController$initDialog$1$5 implements View.OnClickListener {
    final /* synthetic */ ChannelEditorDialogController this$0;

    ChannelEditorDialogController$initDialog$1$5(ChannelEditorDialogController channelEditorDialogController) {
        this.this$0 = channelEditorDialogController;
    }

    public final void onClick(View view) {
        ChannelEditorDialogController channelEditorDialogController = this.this$0;
        Intrinsics.checkNotNullExpressionValue(view, "it");
        channelEditorDialogController.launchSettings(view);
        this.this$0.done();
    }
}

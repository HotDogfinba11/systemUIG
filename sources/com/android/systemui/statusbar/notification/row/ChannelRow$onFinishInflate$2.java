package com.android.systemui.statusbar.notification.row;

import android.view.View;
import android.widget.Switch;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChannelEditorListView.kt */
final class ChannelRow$onFinishInflate$2 implements View.OnClickListener {
    final /* synthetic */ ChannelRow this$0;

    ChannelRow$onFinishInflate$2(ChannelRow channelRow) {
        this.this$0 = channelRow;
    }

    public final void onClick(View view) {
        Switch r0 = this.this$0.f1switch;
        if (r0 != null) {
            r0.toggle();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("switch");
            throw null;
        }
    }
}

package com.android.systemui.controls.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: DetailDialog.kt */
public final class DetailDialog$3$1 implements View.OnClickListener {
    final /* synthetic */ ImageView $this_apply;
    final /* synthetic */ DetailDialog this$0;

    DetailDialog$3$1(DetailDialog detailDialog, ImageView imageView) {
        this.this$0 = detailDialog;
        this.$this_apply = imageView;
    }

    public final void onClick(View view) {
        Intrinsics.checkNotNullParameter(view, "v");
        this.this$0.removeDetailTask();
        this.this$0.dismiss();
        this.$this_apply.getContext().sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        view.getContext().startActivity(this.this$0.getIntent());
    }
}

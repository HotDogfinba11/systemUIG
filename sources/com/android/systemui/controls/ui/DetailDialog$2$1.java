package com.android.systemui.controls.ui;

import android.view.View;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: DetailDialog.kt */
public final class DetailDialog$2$1 implements View.OnClickListener {
    final /* synthetic */ DetailDialog this$0;

    DetailDialog$2$1(DetailDialog detailDialog) {
        this.this$0 = detailDialog;
    }

    public final void onClick(View view) {
        Intrinsics.checkNotNullParameter(view, "$noName_0");
        this.this$0.dismiss();
    }
}

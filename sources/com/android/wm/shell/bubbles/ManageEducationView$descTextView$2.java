package com.android.wm.shell.bubbles;

import android.widget.TextView;
import com.android.wm.shell.R;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: ManageEducationView.kt */
final class ManageEducationView$descTextView$2 extends Lambda implements Function0<TextView> {
    final /* synthetic */ ManageEducationView this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ManageEducationView$descTextView$2(ManageEducationView manageEducationView) {
        super(0);
        this.this$0 = manageEducationView;
    }

    @Override // kotlin.jvm.functions.Function0
    public final TextView invoke() {
        return (TextView) this.this$0.findViewById(R.id.user_education_description);
    }
}

package com.android.wm.shell.bubbles;

import android.view.View;
import com.android.wm.shell.R;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: StackEducationView.kt */
final class StackEducationView$view$2 extends Lambda implements Function0<View> {
    final /* synthetic */ StackEducationView this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StackEducationView$view$2(StackEducationView stackEducationView) {
        super(0);
        this.this$0 = stackEducationView;
    }

    @Override // kotlin.jvm.functions.Function0
    public final View invoke() {
        return this.this$0.findViewById(R.id.stack_education_layout);
    }
}

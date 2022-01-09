package com.android.systemui.accessibility.floatingmenu;

import android.view.View;
import java.util.function.Consumer;

public final /* synthetic */ class AnnotationLinkSpan$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ View f$0;

    public /* synthetic */ AnnotationLinkSpan$$ExternalSyntheticLambda1(View view) {
        this.f$0 = view;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AnnotationLinkSpan.lambda$onClick$0(this.f$0, (View.OnClickListener) obj);
    }
}

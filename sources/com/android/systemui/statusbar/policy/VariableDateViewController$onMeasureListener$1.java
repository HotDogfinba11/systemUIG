package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.VariableDateView;

/* compiled from: VariableDateViewController.kt */
public final class VariableDateViewController$onMeasureListener$1 implements VariableDateView.OnMeasureListener {
    final /* synthetic */ VariableDateViewController this$0;

    VariableDateViewController$onMeasureListener$1(VariableDateViewController variableDateViewController) {
        this.this$0 = variableDateViewController;
    }

    @Override // com.android.systemui.statusbar.policy.VariableDateView.OnMeasureListener
    public void onMeasureAction(int i) {
        if (i != this.this$0.lastWidth) {
            this.this$0.maybeChangeFormat(i);
            this.this$0.lastWidth = i;
        }
    }
}

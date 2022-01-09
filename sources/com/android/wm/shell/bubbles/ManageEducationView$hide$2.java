package com.android.wm.shell.bubbles;

/* access modifiers changed from: package-private */
/* compiled from: ManageEducationView.kt */
public final class ManageEducationView$hide$2 implements Runnable {
    final /* synthetic */ ManageEducationView this$0;

    ManageEducationView$hide$2(ManageEducationView manageEducationView) {
        this.this$0 = manageEducationView;
    }

    public final void run() {
        ManageEducationView.access$setHiding$p(this.this$0, false);
        this.this$0.setVisibility(8);
    }
}

package com.android.systemui.statusbar.policy;

/* compiled from: VariableDateViewController.kt */
/* synthetic */ class VariableDateViewController$intentReceiver$1$onReceive$2 implements Runnable {
    final /* synthetic */ VariableDateViewController $tmp0;

    VariableDateViewController$intentReceiver$1$onReceive$2(VariableDateViewController variableDateViewController) {
        this.$tmp0 = variableDateViewController;
    }

    public final void run() {
        this.$tmp0.updateClock();
    }
}

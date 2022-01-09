package com.android.systemui.statusbar.notification.row;

public final /* synthetic */ class StackScrollerDecorView$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ StackScrollerDecorView f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ StackScrollerDecorView$$ExternalSyntheticLambda2(StackScrollerDecorView stackScrollerDecorView, Runnable runnable) {
        this.f$0 = stackScrollerDecorView;
        this.f$1 = runnable;
    }

    public final void run() {
        this.f$0.lambda$setContentVisible$2(this.f$1);
    }
}

package com.android.systemui.statusbar.phone;

public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ ShadeController f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda10(ShadeController shadeController) {
        this.f$0 = shadeController;
    }

    public final void run() {
        this.f$0.animateCollapsePanels();
    }
}

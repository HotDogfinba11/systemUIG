package com.android.systemui.statusbar.phone;

public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda7 implements PanelExpansionListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda7(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    @Override // com.android.systemui.statusbar.phone.PanelExpansionListener
    public final void onPanelExpansionChanged(float f, boolean z) {
        this.f$0.dispatchPanelExpansionForKeyguardDismiss(f, z);
    }
}

package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

public final /* synthetic */ class BcSmartSpaceUtil$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ BcSmartspaceCardLoggingInfo f$0;
    public final /* synthetic */ BcSmartspaceDataPlugin.IntentStarter f$1;
    public final /* synthetic */ SmartspaceAction f$2;
    public final /* synthetic */ View.OnClickListener f$3;
    public final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceEventNotifier f$4;
    public final /* synthetic */ String f$5;
    public final /* synthetic */ SmartspaceTarget f$6;

    public /* synthetic */ BcSmartSpaceUtil$$ExternalSyntheticLambda0(BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, BcSmartspaceDataPlugin.IntentStarter intentStarter, SmartspaceAction smartspaceAction, View.OnClickListener onClickListener, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, String str, SmartspaceTarget smartspaceTarget) {
        this.f$0 = bcSmartspaceCardLoggingInfo;
        this.f$1 = intentStarter;
        this.f$2 = smartspaceAction;
        this.f$3 = onClickListener;
        this.f$4 = smartspaceEventNotifier;
        this.f$5 = str;
        this.f$6 = smartspaceTarget;
    }

    public final void onClick(View view) {
        BcSmartSpaceUtil.m690$r8$lambda$pF3BUC1agVsehUiTfvGt35rdDQ(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, view);
    }
}

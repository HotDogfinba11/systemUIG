package com.google.android.systemui.smartspace;

public final /* synthetic */ class SmartSpaceController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ SmartSpaceController f$0;
    public final /* synthetic */ NewCardInfo f$1;
    public final /* synthetic */ SmartSpaceCard f$2;

    public /* synthetic */ SmartSpaceController$$ExternalSyntheticLambda2(SmartSpaceController smartSpaceController, NewCardInfo newCardInfo, SmartSpaceCard smartSpaceCard) {
        this.f$0 = smartSpaceController;
        this.f$1 = newCardInfo;
        this.f$2 = smartSpaceCard;
    }

    public final void run() {
        this.f$0.lambda$onNewCard$1(this.f$1, this.f$2);
    }
}

package com.android.wm.shell.pip.phone;

import com.android.wm.shell.pip.phone.PipInputConsumer;

public final /* synthetic */ class PipController$$ExternalSyntheticLambda3 implements PipInputConsumer.RegistrationListener {
    public final /* synthetic */ PipTouchHandler f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda3(PipTouchHandler pipTouchHandler) {
        this.f$0 = pipTouchHandler;
    }

    @Override // com.android.wm.shell.pip.phone.PipInputConsumer.RegistrationListener
    public final void onRegistrationChanged(boolean z) {
        this.f$0.onRegistrationChanged(z);
    }
}

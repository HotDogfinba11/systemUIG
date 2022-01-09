package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedState;
import java.util.function.Consumer;

public final /* synthetic */ class OneHandedState$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int f$0;

    public /* synthetic */ OneHandedState$$ExternalSyntheticLambda0(int i) {
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((OneHandedState.OnStateChangedListener) obj).onStateChanged(this.f$0);
    }
}

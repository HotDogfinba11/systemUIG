package com.google.android.systemui.assist.uihints;

import com.android.systemui.statusbar.CommandQueue;
import java.util.function.Consumer;

public final /* synthetic */ class KeyboardMonitor$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ KeyboardMonitor f$0;

    public /* synthetic */ KeyboardMonitor$$ExternalSyntheticLambda0(KeyboardMonitor keyboardMonitor) {
        this.f$0 = keyboardMonitor;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$new$0((CommandQueue) obj);
    }
}

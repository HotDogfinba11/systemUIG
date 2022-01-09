package com.android.wm.shell.sizecompatui;

import java.util.function.Consumer;

public final /* synthetic */ class SizeCompatUIController$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SizeCompatUIController$$ExternalSyntheticLambda2(boolean z) {
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((SizeCompatUILayout) obj).updateImeVisibility(this.f$0);
    }
}

package com.android.wm.shell.sizecompatui;

import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class SizeCompatUIController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ List f$0;

    public /* synthetic */ SizeCompatUIController$$ExternalSyntheticLambda1(List list) {
        this.f$0 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SizeCompatUIController.lambda$onDisplayRemoved$0(this.f$0, (SizeCompatUILayout) obj);
    }
}

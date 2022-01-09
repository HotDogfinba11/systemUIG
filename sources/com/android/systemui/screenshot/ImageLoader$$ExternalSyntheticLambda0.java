package com.android.systemui.screenshot;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.io.File;

public final /* synthetic */ class ImageLoader$$ExternalSyntheticLambda0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ File f$0;

    public /* synthetic */ ImageLoader$$ExternalSyntheticLambda0(File file) {
        this.f$0 = file;
    }

    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return ImageLoader.m244$r8$lambda$h9JMGk62ggV3fhJzUwU1zdmyY(this.f$0, completer);
    }
}

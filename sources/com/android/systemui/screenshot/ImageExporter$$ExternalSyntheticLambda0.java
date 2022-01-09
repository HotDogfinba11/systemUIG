package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.io.File;
import java.util.concurrent.Executor;

public final /* synthetic */ class ImageExporter$$ExternalSyntheticLambda0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ImageExporter f$0;
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ File f$2;
    public final /* synthetic */ Bitmap f$3;

    public /* synthetic */ ImageExporter$$ExternalSyntheticLambda0(ImageExporter imageExporter, Executor executor, File file, Bitmap bitmap) {
        this.f$0 = imageExporter;
        this.f$1 = executor;
        this.f$2 = file;
        this.f$3 = bitmap;
    }

    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return ImageExporter.$r8$lambda$yjrfcN9tIQcZd4RAekYYOMRhu14(this.f$0, this.f$1, this.f$2, this.f$3, completer);
    }
}

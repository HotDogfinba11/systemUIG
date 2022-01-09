package com.android.systemui.controls.management;

import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$loadControls$1$2 implements Consumer<Runnable> {
    final /* synthetic */ ControlsFavoritingActivity this$0;

    ControlsFavoritingActivity$loadControls$1$2(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    public final void accept(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        this.this$0.cancelLoadRunnable = runnable;
    }
}

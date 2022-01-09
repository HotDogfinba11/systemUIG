package com.google.android.systemui.columbus.actions;

import android.net.Uri;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: LaunchOpa.kt */
public final class LaunchOpa$settingsObserver$1 extends Lambda implements Function1<Uri, Unit> {
    final /* synthetic */ LaunchOpa this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    LaunchOpa$settingsObserver$1(LaunchOpa launchOpa) {
        super(1);
        this.this$0 = launchOpa;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Uri uri) {
        invoke(uri);
        return Unit.INSTANCE;
    }

    public final void invoke(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "it");
        this.this$0.updateGestureEnabled();
    }
}

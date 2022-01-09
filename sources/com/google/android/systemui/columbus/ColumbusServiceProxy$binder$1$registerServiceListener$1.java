package com.google.android.systemui.columbus;

import android.os.IBinder;
import com.google.android.systemui.columbus.ColumbusServiceProxy;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ColumbusServiceProxy.kt */
final class ColumbusServiceProxy$binder$1$registerServiceListener$1 extends Lambda implements Function1<ColumbusServiceProxy.ColumbusServiceListener, Boolean> {
    final /* synthetic */ IBinder $token;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ColumbusServiceProxy$binder$1$registerServiceListener$1(IBinder iBinder) {
        super(1);
        this.$token = iBinder;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Boolean invoke(ColumbusServiceProxy.ColumbusServiceListener columbusServiceListener) {
        return Boolean.valueOf(invoke(columbusServiceListener));
    }

    public final boolean invoke(ColumbusServiceProxy.ColumbusServiceListener columbusServiceListener) {
        Intrinsics.checkNotNullParameter(columbusServiceListener, "it");
        if (!Intrinsics.areEqual(this.$token, columbusServiceListener.getToken())) {
            return false;
        }
        columbusServiceListener.unlinkToDeath();
        return true;
    }
}

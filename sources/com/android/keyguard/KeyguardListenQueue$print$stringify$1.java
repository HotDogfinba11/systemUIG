package com.android.keyguard;

import java.text.DateFormat;
import java.util.Date;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: KeyguardListenQueue.kt */
final class KeyguardListenQueue$print$stringify$1 extends Lambda implements Function1<KeyguardListenModel, String> {
    final /* synthetic */ DateFormat $dateFormat;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KeyguardListenQueue$print$stringify$1(DateFormat dateFormat) {
        super(1);
        this.$dateFormat = dateFormat;
    }

    public final String invoke(KeyguardListenModel keyguardListenModel) {
        Intrinsics.checkNotNullParameter(keyguardListenModel, "model");
        return "    " + ((Object) this.$dateFormat.format(new Date(keyguardListenModel.getTimeMillis()))) + ' ' + keyguardListenModel;
    }
}

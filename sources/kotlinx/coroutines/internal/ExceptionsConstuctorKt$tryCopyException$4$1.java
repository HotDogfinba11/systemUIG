package kotlinx.coroutines.internal;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: ExceptionsConstuctor.kt */
public final class ExceptionsConstuctorKt$tryCopyException$4$1 extends Lambda implements Function1 {
    public static final ExceptionsConstuctorKt$tryCopyException$4$1 INSTANCE = new ExceptionsConstuctorKt$tryCopyException$4$1();

    ExceptionsConstuctorKt$tryCopyException$4$1() {
        super(1);
    }

    public final Void invoke(Throwable th) {
        Intrinsics.checkParameterIsNotNull(th, "it");
        return null;
    }
}

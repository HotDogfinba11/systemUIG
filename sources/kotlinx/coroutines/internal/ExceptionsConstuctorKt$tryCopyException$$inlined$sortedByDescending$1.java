package kotlinx.coroutines.internal;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Comparisons.kt */
public final class ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1<T> implements Comparator<T> {
    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        T t3 = t2;
        Intrinsics.checkExpressionValueIsNotNull(t3, "it");
        Integer valueOf = Integer.valueOf(t3.getParameterTypes().length);
        T t4 = t;
        Intrinsics.checkExpressionValueIsNotNull(t4, "it");
        return ComparisonsKt__ComparisonsKt.compareValues(valueOf, Integer.valueOf(t4.getParameterTypes().length));
    }
}

package kotlin.comparisons;

import java.util.Comparator;
import kotlin.jvm.functions.Function1;

/* access modifiers changed from: package-private */
/* compiled from: Comparisons.kt */
public final class ComparisonsKt__ComparisonsKt$compareBy$1<T> implements Comparator<T> {
    final /* synthetic */ Function1[] $selectors;

    ComparisonsKt__ComparisonsKt$compareBy$1(Function1[] function1Arr) {
        this.$selectors = function1Arr;
    }

    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        return ComparisonsKt__ComparisonsKt.compareValuesByImpl$ComparisonsKt__ComparisonsKt(t, t2, this.$selectors);
    }
}

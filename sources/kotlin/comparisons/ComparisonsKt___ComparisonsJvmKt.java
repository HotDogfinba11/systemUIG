package kotlin.comparisons;

import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: _ComparisonsJvm.kt */
public class ComparisonsKt___ComparisonsJvmKt extends ComparisonsKt__ComparisonsKt {
    public static float maxOf(float f, float... fArr) {
        Intrinsics.checkNotNullParameter(fArr, "other");
        for (float f2 : fArr) {
            f = Math.max(f, f2);
        }
        return f;
    }
}

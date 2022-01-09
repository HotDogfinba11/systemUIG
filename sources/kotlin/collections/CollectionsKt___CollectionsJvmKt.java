package kotlin.collections;

import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: _CollectionsJvm.kt */
public class CollectionsKt___CollectionsJvmKt extends CollectionsKt__ReversedViewsKt {
    public static final <T> void reverse(List<T> list) {
        Intrinsics.checkNotNullParameter(list, "$this$reverse");
        Collections.reverse(list);
    }
}

package kotlinx.coroutines;

import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: JobSupport.kt */
public final class IncompleteStateBox {
    public final Incomplete state;

    public IncompleteStateBox(Incomplete incomplete) {
        Intrinsics.checkParameterIsNotNull(incomplete, "state");
        this.state = incomplete;
    }
}

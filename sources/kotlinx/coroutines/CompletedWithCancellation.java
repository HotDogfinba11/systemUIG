package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* access modifiers changed from: package-private */
/* compiled from: CancellableContinuationImpl.kt */
public final class CompletedWithCancellation {
    public final Function1<Throwable, Unit> onCancellation;
    public final Object result;

    public String toString() {
        return "CompletedWithCancellation[" + this.result + ']';
    }
}

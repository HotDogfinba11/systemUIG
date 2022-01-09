package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import kotlinx.coroutines.DebugKt;

/* compiled from: FlowExceptions.kt */
public final class AbortFlowException extends CancellationException {
    public AbortFlowException() {
        super("Flow was aborted, no more elements needed");
    }

    public Throwable fillInStackTrace() {
        if (DebugKt.getDEBUG()) {
            super.fillInStackTrace();
        }
        return this;
    }
}

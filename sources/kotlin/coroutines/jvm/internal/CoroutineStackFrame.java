package kotlin.coroutines.jvm.internal;

/* compiled from: CoroutineStackFrame.kt */
public interface CoroutineStackFrame {
    CoroutineStackFrame getCallerFrame();

    StackTraceElement getStackTraceElement();
}

package kotlinx.coroutines;

/* access modifiers changed from: package-private */
/* compiled from: CancellableContinuationImpl.kt */
public final class Active implements NotCompleted {
    public static final Active INSTANCE = new Active();

    public String toString() {
        return "Active";
    }

    private Active() {
    }
}

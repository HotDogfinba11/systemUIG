package kotlinx.coroutines;

/* compiled from: Job.kt */
public interface ChildHandle extends DisposableHandle {
    boolean childCancelled(Throwable th);
}

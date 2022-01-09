package kotlinx.coroutines;

import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: Supervisor.kt */
public final class SupervisorJobImpl extends JobImpl {
    @Override // kotlinx.coroutines.JobSupport
    public boolean childCancelled(Throwable th) {
        Intrinsics.checkParameterIsNotNull(th, "cause");
        return false;
    }

    public SupervisorJobImpl(Job job) {
        super(job);
    }
}

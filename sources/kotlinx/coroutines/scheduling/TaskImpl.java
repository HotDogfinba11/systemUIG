package kotlinx.coroutines.scheduling;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugStringsKt;

/* compiled from: Tasks.kt */
public final class TaskImpl extends Task {
    public final Runnable block;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TaskImpl(Runnable runnable, long j, TaskContext taskContext) {
        super(j, taskContext);
        Intrinsics.checkParameterIsNotNull(runnable, "block");
        Intrinsics.checkParameterIsNotNull(taskContext, "taskContext");
        this.block = runnable;
    }

    public void run() {
        try {
            this.block.run();
        } finally {
            this.taskContext.afterTask();
        }
    }

    public String toString() {
        return "Task[" + DebugStringsKt.getClassSimpleName(this.block) + '@' + DebugStringsKt.getHexAddress(this.block) + ", " + this.submissionTime + ", " + this.taskContext + ']';
    }
}

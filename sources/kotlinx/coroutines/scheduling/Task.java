package kotlinx.coroutines.scheduling;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tasks.kt */
public abstract class Task implements Runnable {
    public long submissionTime;
    public TaskContext taskContext;

    public Task(long j, TaskContext taskContext2) {
        Intrinsics.checkParameterIsNotNull(taskContext2, "taskContext");
        this.submissionTime = j;
        this.taskContext = taskContext2;
    }

    public Task() {
        this(0, NonBlockingContext.INSTANCE);
    }

    public final TaskMode getMode() {
        return this.taskContext.getTaskMode();
    }
}

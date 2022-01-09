package kotlinx.coroutines.scheduling;

/* compiled from: Tasks.kt */
public interface TaskContext {
    void afterTask();

    TaskMode getTaskMode();
}

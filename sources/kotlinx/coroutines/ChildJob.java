package kotlinx.coroutines;

/* compiled from: Job.kt */
public interface ChildJob extends Job {
    void parentCancelled(ParentJob parentJob);
}

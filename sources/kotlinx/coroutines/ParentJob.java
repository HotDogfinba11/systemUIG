package kotlinx.coroutines;

import java.util.concurrent.CancellationException;

/* compiled from: Job.kt */
public interface ParentJob extends Job {
    CancellationException getChildJobCancellationCause();
}

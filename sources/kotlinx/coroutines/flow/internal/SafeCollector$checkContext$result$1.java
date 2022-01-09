package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.Job;

/* access modifiers changed from: package-private */
/* compiled from: SafeCollector.kt */
public final class SafeCollector$checkContext$result$1 extends Lambda implements Function2<Integer, CoroutineContext.Element, Integer> {
    final /* synthetic */ SafeCollector this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SafeCollector$checkContext$result$1(SafeCollector safeCollector) {
        super(2);
        this.this$0 = safeCollector;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.Object] */
    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Integer invoke(Integer num, CoroutineContext.Element element) {
        return Integer.valueOf(invoke(num.intValue(), element));
    }

    public final int invoke(int i, CoroutineContext.Element element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        CoroutineContext.Key<?> key = element.getKey();
        CoroutineContext.Element element2 = this.this$0.collectContext.get(key);
        if (key == Job.Key) {
            Job job = (Job) element2;
            Job job2 = this.this$0.transitiveCoroutineParent((Job) element, job);
            if (job2 == job) {
                return job == null ? i : i + 1;
            }
            throw new IllegalStateException(("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n" + "\t\tChild of " + job2 + ", expected child of " + job + ".\n" + "\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n" + "\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'").toString());
        } else if (element != element2) {
            return Integer.MIN_VALUE;
        } else {
            return i + 1;
        }
    }
}

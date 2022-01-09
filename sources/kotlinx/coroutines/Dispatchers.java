package kotlinx.coroutines;

import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: Dispatchers.kt */
public final class Dispatchers {
    private static final CoroutineDispatcher Default = CoroutineContextKt.createDefaultDispatcher();
    public static final Dispatchers INSTANCE = new Dispatchers();
    private static final CoroutineDispatcher IO = DefaultScheduler.INSTANCE.getIO();
    private static final CoroutineDispatcher Unconfined = Unconfined.INSTANCE;

    private Dispatchers() {
    }

    public static final CoroutineDispatcher getDefault() {
        return Default;
    }

    public static final MainCoroutineDispatcher getMain() {
        return MainDispatcherLoader.dispatcher;
    }

    public static final CoroutineDispatcher getIO() {
        return IO;
    }
}

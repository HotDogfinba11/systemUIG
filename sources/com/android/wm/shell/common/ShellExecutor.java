package com.android.wm.shell.common;

import java.lang.reflect.Array;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface ShellExecutor extends Executor {
    void execute(Runnable runnable);

    void executeDelayed(Runnable runnable, long j);

    boolean hasCallback(Runnable runnable);

    void removeCallbacks(Runnable runnable);

    default void executeBlocking(Runnable runnable, int i, TimeUnit timeUnit) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        execute(new ShellExecutor$$ExternalSyntheticLambda0(runnable, countDownLatch));
        countDownLatch.await((long) i, timeUnit);
    }

    /* access modifiers changed from: private */
    static /* synthetic */ default void lambda$executeBlocking$0(Runnable runnable, CountDownLatch countDownLatch) {
        runnable.run();
        countDownLatch.countDown();
    }

    default void executeBlocking(Runnable runnable) throws InterruptedException {
        executeBlocking(runnable, 2, TimeUnit.SECONDS);
    }

    default <T> T executeBlockingForResult(Supplier<T> supplier, Class cls) {
        Object[] objArr = (Object[]) Array.newInstance(cls, 1);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        execute(new ShellExecutor$$ExternalSyntheticLambda1(objArr, supplier, countDownLatch));
        try {
            countDownLatch.await();
            return (T) objArr[0];
        } catch (InterruptedException unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    static /* synthetic */ default void lambda$executeBlockingForResult$1(Object[] objArr, Supplier supplier, CountDownLatch countDownLatch) {
        objArr[0] = supplier.get();
        countDownLatch.countDown();
    }
}

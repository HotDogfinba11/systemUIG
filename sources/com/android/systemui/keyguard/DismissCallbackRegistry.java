package com.android.systemui.keyguard;

import com.android.internal.policy.IKeyguardDismissCallback;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

public class DismissCallbackRegistry {
    private final ArrayList<DismissCallbackWrapper> mDismissCallbacks = new ArrayList<>();
    private final Executor mUiBgExecutor;

    public DismissCallbackRegistry(Executor executor) {
        this.mUiBgExecutor = executor;
    }

    public void addCallback(IKeyguardDismissCallback iKeyguardDismissCallback) {
        this.mDismissCallbacks.add(new DismissCallbackWrapper(iKeyguardDismissCallback));
    }

    public void notifyDismissCancelled() {
        for (int size = this.mDismissCallbacks.size() - 1; size >= 0; size--) {
            DismissCallbackWrapper dismissCallbackWrapper = this.mDismissCallbacks.get(size);
            Executor executor = this.mUiBgExecutor;
            Objects.requireNonNull(dismissCallbackWrapper);
            executor.execute(new DismissCallbackRegistry$$ExternalSyntheticLambda0(dismissCallbackWrapper));
        }
        this.mDismissCallbacks.clear();
    }

    public void notifyDismissSucceeded() {
        for (int size = this.mDismissCallbacks.size() - 1; size >= 0; size--) {
            DismissCallbackWrapper dismissCallbackWrapper = this.mDismissCallbacks.get(size);
            Executor executor = this.mUiBgExecutor;
            Objects.requireNonNull(dismissCallbackWrapper);
            executor.execute(new DismissCallbackRegistry$$ExternalSyntheticLambda1(dismissCallbackWrapper));
        }
        this.mDismissCallbacks.clear();
    }
}

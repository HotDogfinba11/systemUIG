package com.android.systemui.settings;

import com.android.systemui.settings.UserTracker;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: UserTrackerImpl.kt */
public final class DataItem {
    private final WeakReference<UserTracker.Callback> callback;
    private final Executor executor;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataItem)) {
            return false;
        }
        DataItem dataItem = (DataItem) obj;
        return Intrinsics.areEqual(this.callback, dataItem.callback) && Intrinsics.areEqual(this.executor, dataItem.executor);
    }

    public int hashCode() {
        return (this.callback.hashCode() * 31) + this.executor.hashCode();
    }

    public String toString() {
        return "DataItem(callback=" + this.callback + ", executor=" + this.executor + ')';
    }

    public DataItem(WeakReference<UserTracker.Callback> weakReference, Executor executor2) {
        Intrinsics.checkNotNullParameter(weakReference, "callback");
        Intrinsics.checkNotNullParameter(executor2, "executor");
        this.callback = weakReference;
        this.executor = executor2;
    }

    public final WeakReference<UserTracker.Callback> getCallback() {
        return this.callback;
    }

    public final Executor getExecutor() {
        return this.executor;
    }

    public final boolean sameOrEmpty(UserTracker.Callback callback2) {
        Intrinsics.checkNotNullParameter(callback2, "other");
        UserTracker.Callback callback3 = this.callback.get();
        if (callback3 == null) {
            return true;
        }
        return callback3.equals(callback2);
    }
}

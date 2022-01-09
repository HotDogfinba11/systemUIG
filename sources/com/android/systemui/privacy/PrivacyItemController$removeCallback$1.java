package com.android.systemui.privacy;

import com.android.systemui.privacy.PrivacyItemController;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$removeCallback$1 implements Predicate<WeakReference<PrivacyItemController.Callback>> {
    final /* synthetic */ WeakReference<PrivacyItemController.Callback> $callback;

    PrivacyItemController$removeCallback$1(WeakReference<PrivacyItemController.Callback> weakReference) {
        this.$callback = weakReference;
    }

    public final boolean test(WeakReference<PrivacyItemController.Callback> weakReference) {
        Intrinsics.checkNotNullParameter(weakReference, "it");
        PrivacyItemController.Callback callback = weakReference.get();
        if (callback == null) {
            return true;
        }
        return callback.equals(this.$callback.get());
    }
}

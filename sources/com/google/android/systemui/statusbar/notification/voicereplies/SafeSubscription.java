package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.people.Subscription;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class SafeSubscription implements Subscription {
    private final AtomicReference<Function0<Unit>> blockRef;

    public SafeSubscription(Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "block");
        this.blockRef = new AtomicReference<>(function0);
    }

    @Override // com.android.systemui.statusbar.notification.people.Subscription
    public void unsubscribe() {
        Function0<Unit> andSet = this.blockRef.getAndSet(null);
        if (andSet != null) {
            andSet.invoke();
        }
    }
}

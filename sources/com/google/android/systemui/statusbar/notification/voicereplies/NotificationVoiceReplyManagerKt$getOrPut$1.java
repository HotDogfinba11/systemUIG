package com.google.android.systemui.statusbar.notification.voicereplies;

import java.util.function.BiFunction;
import kotlin.jvm.functions.Function0;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$getOrPut$1 implements BiFunction<K, V, V> {
    final /* synthetic */ Function0<V> $default;

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: kotlin.jvm.functions.Function0<? extends V> */
    /* JADX WARN: Multi-variable type inference failed */
    NotificationVoiceReplyManagerKt$getOrPut$1(Function0<? extends V> function0) {
        this.$default = function0;
    }

    @Override // java.util.function.BiFunction
    public final V apply(K k, V v) {
        return v == null ? this.$default.invoke() : v;
    }
}

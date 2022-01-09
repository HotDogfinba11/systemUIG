package com.google.android.systemui.statusbar;

import android.os.IBinder;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class NotificationVoiceReplyManagerServiceKt$onDeath$recipient$1 implements IBinder.DeathRecipient {
    final /* synthetic */ Function0<Unit> $block;

    NotificationVoiceReplyManagerServiceKt$onDeath$recipient$1(Function0<Unit> function0) {
        this.$block = function0;
    }

    public final void binderDied() {
        this.$block.invoke();
    }
}

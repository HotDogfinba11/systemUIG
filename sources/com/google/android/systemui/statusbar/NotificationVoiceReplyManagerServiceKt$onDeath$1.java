package com.google.android.systemui.statusbar;

import android.os.IBinder;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class NotificationVoiceReplyManagerServiceKt$onDeath$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ IBinder.DeathRecipient $recipient;
    final /* synthetic */ IBinder $this_onDeath;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyManagerServiceKt$onDeath$1(IBinder iBinder, IBinder.DeathRecipient deathRecipient) {
        super(0);
        this.$this_onDeath = iBinder;
        this.$recipient = deathRecipient;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        this.$this_onDeath.unlinkToDeath(this.$recipient, 0);
    }
}

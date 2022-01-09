package com.google.android.systemui.statusbar.notification.voicereplies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Job;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1 extends BroadcastReceiver {
    final /* synthetic */ Ref$BooleanRef $notifAvailable;
    final /* synthetic */ VoiceReplySubscription $subscription;
    final /* synthetic */ Ref$IntRef $token;
    final /* synthetic */ DebugNotificationVoiceReplyClient$startClient$job$1 this$0;

    DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1(Ref$BooleanRef ref$BooleanRef, DebugNotificationVoiceReplyClient$startClient$job$1 debugNotificationVoiceReplyClient$startClient$job$1, VoiceReplySubscription voiceReplySubscription, Ref$IntRef ref$IntRef) {
        this.$notifAvailable = ref$BooleanRef;
        this.this$0 = debugNotificationVoiceReplyClient$startClient$job$1;
        this.$subscription = voiceReplySubscription;
        this.$token = ref$IntRef;
    }

    public void onReceive(Context context, Intent intent) {
        if (!this.$notifAvailable.element) {
            Log.d("NotifVoiceReplyDebug", "no notification available for voice reply");
        }
        Job unused = BuildersKt__Builders_commonKt.launch$default(this.this$0.p$, null, null, new DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1$onReceive$1(this.$subscription, this.$token, null), 3, null);
    }
}

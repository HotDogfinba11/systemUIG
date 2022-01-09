package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$awaitFocusState$2$1 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ VoiceReplyTarget$awaitFocusState$2$listener$1 $listener;
    final /* synthetic */ RemoteInputView $this_awaitFocusState;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    VoiceReplyTarget$awaitFocusState$2$1(RemoteInputView remoteInputView, VoiceReplyTarget$awaitFocusState$2$listener$1 voiceReplyTarget$awaitFocusState$2$listener$1) {
        super(1);
        this.$this_awaitFocusState = remoteInputView;
        this.$listener = voiceReplyTarget$awaitFocusState$2$listener$1;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke(th);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable th) {
        this.$this_awaitFocusState.removeOnEditTextFocusChangedListener(this.$listener);
    }
}

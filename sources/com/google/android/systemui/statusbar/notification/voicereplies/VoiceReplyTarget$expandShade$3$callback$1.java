package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Result;
import kotlinx.coroutines.CancellableContinuation;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$expandShade$3$callback$1 implements StatusBarStateController.StateListener {
    final /* synthetic */ CancellableContinuation<Boolean> $k;
    final /* synthetic */ AtomicBoolean $latch;
    final /* synthetic */ VoiceReplyTarget this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: kotlinx.coroutines.CancellableContinuation<? super java.lang.Boolean> */
    /* JADX WARN: Multi-variable type inference failed */
    VoiceReplyTarget$expandShade$3$callback$1(AtomicBoolean atomicBoolean, VoiceReplyTarget voiceReplyTarget, CancellableContinuation<? super Boolean> cancellableContinuation) {
        this.$latch = atomicBoolean;
        this.this$0 = voiceReplyTarget;
        this.$k = cancellableContinuation;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onExpandedChanged(boolean z) {
        AtomicBoolean atomicBoolean = this.$latch;
        VoiceReplyTarget voiceReplyTarget = this.this$0;
        CancellableContinuation<Boolean> cancellableContinuation = this.$k;
        if (atomicBoolean.getAndSet(false)) {
            voiceReplyTarget.statusBarStateController.removeCallback(this);
            Boolean valueOf = Boolean.valueOf(z);
            Result.Companion companion = Result.Companion;
            cancellableContinuation.resumeWith(Result.m699constructorimpl(valueOf));
        }
    }
}

package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$refreshCandidateOnNotifChanges$listener$1 implements NotificationEntryListener {
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_refreshCandidateOnNotifChanges;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    NotificationVoiceReplyController$refreshCandidateOnNotifChanges$listener$1(NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyController notificationVoiceReplyController) {
        this.$this_refreshCandidateOnNotifChanges = connection;
        this.this$0 = notificationVoiceReplyController;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
    public void onEntryInflated(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        NotificationVoiceReplyController.refreshCandidateOnNotifChanges$newCandidate(this.$this_refreshCandidateOnNotifChanges, this.this$0, notificationEntry, "onEntryInflated");
    }

    @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
    public void onEntryReinflated(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        NotificationVoiceReplyController.refreshCandidateOnNotifChanges$newCandidate(this.$this_refreshCandidateOnNotifChanges, this.this$0, notificationEntry, "onEntryReinflated");
    }

    @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
    public void onEntryRemoved(NotificationEntry notificationEntry, NotificationVisibility notificationVisibility, boolean z, int i) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        Object unused = BuildersKt__BuildersKt.runBlocking$default(null, new NotificationVoiceReplyController$refreshCandidateOnNotifChanges$listener$1$onEntryRemoved$1(this.$this_refreshCandidateOnNotifChanges, notificationEntry, null), 1, null);
    }
}

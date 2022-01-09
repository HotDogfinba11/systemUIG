package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController$registerHandler$2 extends Lambda implements Function0<Unit> {
    final /* synthetic */ NotificationVoiceReplyHandler $handler;
    final /* synthetic */ NotificationVoiceReplyController.Connection $this_registerHandler;
    final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationVoiceReplyController$registerHandler$2(NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyHandler notificationVoiceReplyHandler, NotificationVoiceReplyController notificationVoiceReplyController) {
        super(0);
        this.$this_registerHandler = connection;
        this.$handler = notificationVoiceReplyHandler;
        this.this$0 = notificationVoiceReplyController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        List<NotificationVoiceReplyHandler> list = this.$this_registerHandler.getActiveHandlersByUser().get(Integer.valueOf(this.$handler.getUserId()));
        if (list != null) {
            NotificationVoiceReplyHandler notificationVoiceReplyHandler = this.$handler;
            NotificationVoiceReplyController.Connection connection = this.$this_registerHandler;
            NotificationVoiceReplyController notificationVoiceReplyController = this.this$0;
            list.remove(notificationVoiceReplyHandler);
            if (list.isEmpty()) {
                connection.getActiveHandlersByUser().remove(Integer.valueOf(notificationVoiceReplyHandler.getUserId()));
                VoiceReplyTarget activeCandidate = connection.getActiveCandidate();
                Integer valueOf = activeCandidate == null ? null : Integer.valueOf(activeCandidate.getUserId());
                int userId = notificationVoiceReplyHandler.getUserId();
                if (valueOf != null && valueOf.intValue() == userId) {
                    LogBuffer logBuffer = notificationVoiceReplyController.logger.getLogBuffer();
                    LogLevel logLevel = LogLevel.DEBUG;
                    NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$2 = new NotificationVoiceReplyLogger$logStatic$2("No more registered handlers for current candidate");
                    if (!logBuffer.getFrozen()) {
                        logBuffer.push(logBuffer.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logStatic$2));
                    }
                    connection.getStateFlow().setValue(notificationVoiceReplyController.queryInitialState(connection));
                }
            }
        }
    }
}

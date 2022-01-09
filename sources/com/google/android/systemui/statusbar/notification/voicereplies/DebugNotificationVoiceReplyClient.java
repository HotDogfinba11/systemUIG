package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.people.Subscription;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class DebugNotificationVoiceReplyClient implements NotificationVoiceReplyClient {
    public static final Companion Companion = new Companion(null);
    private final BroadcastDispatcher broadcastDispatcher;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final NotificationVoiceReplyManager.Initializer voiceReplyInitializer;

    public DebugNotificationVoiceReplyClient(BroadcastDispatcher broadcastDispatcher2, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationVoiceReplyManager.Initializer initializer) {
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(notificationLockscreenUserManager, "lockscreenUserManager");
        Intrinsics.checkNotNullParameter(initializer, "voiceReplyInitializer");
        this.broadcastDispatcher = broadcastDispatcher2;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.voiceReplyInitializer = initializer;
    }

    @Override // com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyClient
    public Subscription startClient() {
        GlobalScope globalScope = GlobalScope.INSTANCE;
        Dispatchers dispatchers = Dispatchers.INSTANCE;
        return NotificationVoiceReplyManagerKt.Subscription(new DebugNotificationVoiceReplyClient$startClient$1(BuildersKt__Builders_commonKt.launch$default(globalScope, Dispatchers.getMain(), null, new DebugNotificationVoiceReplyClient$startClient$job$1(this, null), 2, null)));
    }

    /* compiled from: NotificationVoiceReplyManager.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

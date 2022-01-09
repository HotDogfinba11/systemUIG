package com.google.android.systemui.assist;

import android.content.Context;
import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.PhoneStateMonitor;
import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GoogleAssistLogger.kt */
public final class GoogleAssistLogger extends AssistLogger {
    private final AssistantPresenceHandler assistantPresenceHandler;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public GoogleAssistLogger(Context context, UiEventLogger uiEventLogger, AssistUtils assistUtils, PhoneStateMonitor phoneStateMonitor, AssistantPresenceHandler assistantPresenceHandler2) {
        super(context, uiEventLogger, assistUtils, phoneStateMonitor);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uiEventLogger, "uiEventLogger");
        Intrinsics.checkNotNullParameter(assistUtils, "assistUtils");
        Intrinsics.checkNotNullParameter(phoneStateMonitor, "phoneStateMonitor");
        Intrinsics.checkNotNullParameter(assistantPresenceHandler2, "assistantPresenceHandler");
        this.assistantPresenceHandler = assistantPresenceHandler2;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.assist.AssistLogger
    public void reportAssistantInvocationExtraData() {
        StatsLog.write(StatsEvent.newBuilder().setAtomId(100045).writeInt(getOrCreateInstanceId().getId()).writeBoolean(this.assistantPresenceHandler.isNgaAssistant()).build());
    }
}

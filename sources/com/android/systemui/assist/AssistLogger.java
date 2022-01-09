package com.android.systemui.assist;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.keyguard.KeyguardUpdateMonitor;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AssistLogger.kt */
public class AssistLogger {
    public static final Companion Companion = new Companion(null);
    private static final Set<AssistantSessionEvent> SESSION_END_EVENTS = SetsKt__SetsKt.setOf((Object[]) new AssistantSessionEvent[]{AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED, AssistantSessionEvent.ASSISTANT_SESSION_CLOSE});
    private final AssistUtils assistUtils;
    private final Context context;
    private InstanceId currentInstanceId;
    private final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(1048576);
    private final PhoneStateMonitor phoneStateMonitor;
    private final UiEventLogger uiEventLogger;

    /* access modifiers changed from: protected */
    public void reportAssistantInvocationExtraData() {
    }

    public AssistLogger(Context context2, UiEventLogger uiEventLogger2, AssistUtils assistUtils2, PhoneStateMonitor phoneStateMonitor2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        Intrinsics.checkNotNullParameter(assistUtils2, "assistUtils");
        Intrinsics.checkNotNullParameter(phoneStateMonitor2, "phoneStateMonitor");
        this.context = context2;
        this.uiEventLogger = uiEventLogger2;
        this.assistUtils = assistUtils2;
        this.phoneStateMonitor = phoneStateMonitor2;
    }

    public final void reportAssistantInvocationEventFromLegacy(int i, boolean z, ComponentName componentName, Integer num) {
        reportAssistantInvocationEvent(AssistantInvocationEvent.Companion.eventFromLegacyInvocationType(i, z), componentName, num == null ? null : Integer.valueOf(AssistantInvocationEvent.Companion.deviceStateFromLegacyDeviceState(num.intValue())));
    }

    public final void reportAssistantInvocationEvent(UiEventLogger.UiEventEnum uiEventEnum, ComponentName componentName, Integer num) {
        int i;
        Intrinsics.checkNotNullParameter(uiEventEnum, "invocationEvent");
        if (componentName == null) {
            componentName = getAssistantComponentForCurrentUser();
        }
        int assistantUid = getAssistantUid(componentName);
        if (num == null) {
            i = AssistantInvocationEvent.Companion.deviceStateFromLegacyDeviceState(this.phoneStateMonitor.getPhoneState());
        } else {
            i = num.intValue();
        }
        FrameworkStatsLog.write(281, uiEventEnum.getId(), assistantUid, componentName.flattenToString(), getOrCreateInstanceId().getId(), i, false);
        reportAssistantInvocationExtraData();
    }

    public final void reportAssistantSessionEvent(UiEventLogger.UiEventEnum uiEventEnum) {
        Intrinsics.checkNotNullParameter(uiEventEnum, "sessionEvent");
        ComponentName assistantComponentForCurrentUser = getAssistantComponentForCurrentUser();
        this.uiEventLogger.logWithInstanceId(uiEventEnum, getAssistantUid(assistantComponentForCurrentUser), assistantComponentForCurrentUser.flattenToString(), getOrCreateInstanceId());
        if (CollectionsKt___CollectionsKt.contains(SESSION_END_EVENTS, uiEventEnum)) {
            clearInstanceId();
        }
    }

    /* access modifiers changed from: protected */
    public final InstanceId getOrCreateInstanceId() {
        InstanceId instanceId = this.currentInstanceId;
        if (instanceId == null) {
            instanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceId;
        Intrinsics.checkNotNullExpressionValue(instanceId, "instanceId");
        return instanceId;
    }

    /* access modifiers changed from: protected */
    public final void clearInstanceId() {
        this.currentInstanceId = null;
    }

    /* access modifiers changed from: protected */
    public final ComponentName getAssistantComponentForCurrentUser() {
        ComponentName assistComponentForUser = this.assistUtils.getAssistComponentForUser(KeyguardUpdateMonitor.getCurrentUser());
        Intrinsics.checkNotNullExpressionValue(assistComponentForUser, "assistUtils.getAssistComponentForUser(KeyguardUpdateMonitor.getCurrentUser())");
        return assistComponentForUser;
    }

    /* access modifiers changed from: protected */
    public final int getAssistantUid(ComponentName componentName) {
        Intrinsics.checkNotNullParameter(componentName, "assistantComponent");
        try {
            return this.context.getPackageManager().getApplicationInfo(componentName.getPackageName(), 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AssistLogger", "Unable to find Assistant UID", e);
            return 0;
        }
    }

    /* compiled from: AssistLogger.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

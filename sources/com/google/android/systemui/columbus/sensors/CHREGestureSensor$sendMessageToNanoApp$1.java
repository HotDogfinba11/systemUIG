package com.google.android.systemui.columbus.sensors;

import android.hardware.location.ContextHubClient;
import android.hardware.location.NanoAppMessage;
import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* access modifiers changed from: package-private */
/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor$sendMessageToNanoApp$1 implements Runnable {
    final /* synthetic */ byte[] $bytes;
    final /* synthetic */ int $messageType;
    final /* synthetic */ Function0<Unit> $onFail;
    final /* synthetic */ Function0<Unit> $onSuccess;
    final /* synthetic */ CHREGestureSensor this$0;

    CHREGestureSensor$sendMessageToNanoApp$1(int i, byte[] bArr, CHREGestureSensor cHREGestureSensor, Function0<Unit> function0, Function0<Unit> function02) {
        this.$messageType = i;
        this.$bytes = bArr;
        this.this$0 = cHREGestureSensor;
        this.$onFail = function0;
        this.$onSuccess = function02;
    }

    public final void run() {
        NanoAppMessage createMessageToNanoApp = NanoAppMessage.createMessageToNanoApp(5147455389092024345L, this.$messageType, this.$bytes);
        ContextHubClient contextHubClient = this.this$0.contextHubClient;
        Integer valueOf = contextHubClient == null ? null : Integer.valueOf(contextHubClient.sendMessageToNanoApp(createMessageToNanoApp));
        if (valueOf != null && valueOf.intValue() == 0) {
            Function0<Unit> function0 = this.$onSuccess;
            if (function0 != null) {
                function0.invoke();
                return;
            }
            return;
        }
        Log.e("Columbus/GestureSensor", "Unable to send message " + this.$messageType + " to nanoapp, error code " + valueOf);
        Function0<Unit> function02 = this.$onFail;
        if (function02 != null) {
            function02.invoke();
        }
    }
}

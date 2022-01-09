package com.google.android.systemui.statusbar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class NotificationVoiceReplyManagerService extends Service {
    private final IBinder binder = new NotificationVoiceReplyManagerService$binder$1(this);
    private final NotificationVoiceReplyLogger logger;
    private final NotificationVoiceReplyManager.Initializer managerInitializer;
    private final CoroutineScope scope = CoroutineScopeKt.MainScope();
    private final Channel<Function1<Continuation<? super Unit>, Object>> serializer = ChannelKt.Channel$default(0, 1, null);
    private NotificationVoiceReplyManager voiceReplyManager;

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    public NotificationVoiceReplyManagerService(NotificationVoiceReplyManager.Initializer initializer, NotificationVoiceReplyLogger notificationVoiceReplyLogger) {
        Intrinsics.checkNotNullParameter(initializer, "managerInitializer");
        Intrinsics.checkNotNullParameter(notificationVoiceReplyLogger, "logger");
        this.managerInitializer = initializer;
        this.logger = notificationVoiceReplyLogger;
    }

    public void onCreate() {
        super.onCreate();
        this.voiceReplyManager = this.managerInitializer.connect(this.scope);
        Job unused = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new NotificationVoiceReplyManagerService$onCreate$1(this, null), 3, null);
    }

    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public void onDestroy() {
        CoroutineScopeKt.cancel$default(this.scope, null, 1, null);
        super.onDestroy();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0053 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object serializeIncomingIPCs(kotlin.coroutines.Continuation r6) {
        /*
        // Method dump skipped, instructions count: 112
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService.serializeIncomingIPCs(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final void serially(Function1<? super Continuation<? super Unit>, ? extends Object> function1) {
        Object unused = BuildersKt__BuildersKt.runBlocking$default(null, new NotificationVoiceReplyManagerService$serially$1(this, function1, null), 1, null);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        if (r6 != false) goto L_0x003e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void ensureCallerIsAgsa() {
        /*
            r12 = this;
            android.content.pm.PackageManager r0 = r12.getPackageManager()
            int r1 = android.os.Binder.getCallingUid()
            java.lang.String[] r0 = r0.getPackagesForUid(r1)
            r1 = 0
            if (r0 != 0) goto L_0x0010
            goto L_0x0045
        L_0x0010:
            int r2 = r0.length
            r3 = 0
            r4 = r3
        L_0x0013:
            if (r4 >= r2) goto L_0x0045
            r5 = r0[r4]
            java.lang.String r6 = "com.google.android.googlequicksearchbox"
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
            r7 = 1
            if (r6 == 0) goto L_0x003d
            byte[][] r6 = com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt.access$getAGSA_CERTS$p()
            int r8 = r6.length
            r9 = r3
        L_0x0026:
            if (r9 >= r8) goto L_0x0039
            r10 = r6[r9]
            android.content.pm.PackageManager r11 = r12.getPackageManager()
            boolean r10 = r11.hasSigningCertificate(r5, r10, r7)
            if (r10 == 0) goto L_0x0036
            r6 = r7
            goto L_0x003a
        L_0x0036:
            int r9 = r9 + 1
            goto L_0x0026
        L_0x0039:
            r6 = r3
        L_0x003a:
            if (r6 == 0) goto L_0x003d
            goto L_0x003e
        L_0x003d:
            r7 = r3
        L_0x003e:
            if (r7 == 0) goto L_0x0042
            r1 = r5
            goto L_0x0045
        L_0x0042:
            int r4 = r4 + 1
            goto L_0x0013
        L_0x0045:
            if (r1 == 0) goto L_0x0048
            return
        L_0x0048:
            java.lang.SecurityException r12 = new java.lang.SecurityException
            java.lang.String r0 = "Caller is not allowlisted"
            r12.<init>(r0)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService.ensureCallerIsAgsa():void");
    }
}

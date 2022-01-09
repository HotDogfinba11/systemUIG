package com.google.android.systemui.columbus;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.settings.UserTracker;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* compiled from: ColumbusTargetRequestService.kt */
public final class ColumbusTargetRequestService extends Service {
    public static final Companion Companion = new Companion(null);
    private static final long PACKAGE_DENY_COOLDOWN_MS = TimeUnit.DAYS.toMillis(5);
    private final Set<String> allowCertList;
    private final Set<String> allowPackageList;
    private final ColumbusContext columbusContext;
    private final ColumbusSettings columbusSettings;
    private final ColumbusStructuredDataManager columbusStructuredDataManager;
    private LauncherApps launcherApps;
    private final Handler mainHandler;
    private final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    private final Messenger messenger;
    private final Context sysUIContext;
    private final UiEventLogger uiEventLogger;
    private final UserTracker userTracker;

    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    public static final /* synthetic */ ColumbusStructuredDataManager access$getColumbusStructuredDataManager$p(ColumbusTargetRequestService columbusTargetRequestService) {
        return columbusTargetRequestService.columbusStructuredDataManager;
    }

    public static final /* synthetic */ Context access$getSysUIContext$p(ColumbusTargetRequestService columbusTargetRequestService) {
        return columbusTargetRequestService.sysUIContext;
    }

    public static final /* synthetic */ UiEventLogger access$getUiEventLogger$p(ColumbusTargetRequestService columbusTargetRequestService) {
        return columbusTargetRequestService.uiEventLogger;
    }

    public static final /* synthetic */ UserTracker access$getUserTracker$p(ColumbusTargetRequestService columbusTargetRequestService) {
        return columbusTargetRequestService.userTracker;
    }

    public ColumbusTargetRequestService(Context context, UserTracker userTracker2, ColumbusSettings columbusSettings2, ColumbusStructuredDataManager columbusStructuredDataManager2, UiEventLogger uiEventLogger2, Handler handler, Looper looper) {
        Intrinsics.checkNotNullParameter(context, "sysUIContext");
        Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
        Intrinsics.checkNotNullParameter(columbusSettings2, "columbusSettings");
        Intrinsics.checkNotNullParameter(columbusStructuredDataManager2, "columbusStructuredDataManager");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        Intrinsics.checkNotNullParameter(handler, "mainHandler");
        Intrinsics.checkNotNullParameter(looper, "looper");
        this.sysUIContext = context;
        this.userTracker = userTracker2;
        this.columbusSettings = columbusSettings2;
        this.columbusStructuredDataManager = columbusStructuredDataManager2;
        this.uiEventLogger = uiEventLogger2;
        this.mainHandler = handler;
        this.columbusContext = new ColumbusContext(context);
        this.messenger = new Messenger(new IncomingMessageHandler(this, looper));
        String[] stringArray = context.getResources().getStringArray(ColumbusResourceHelper.SUMATRA_ALLOW_LIST);
        Intrinsics.checkNotNullExpressionValue(stringArray, "sysUIContext.resources.getStringArray(\n            ColumbusResourceHelper.SUMATRA_ALLOW_LIST)");
        String[] strArr = new String[stringArray.length];
        System.arraycopy(stringArray, 0, strArr, 0, stringArray.length);
        this.allowPackageList = SetsKt__SetsKt.setOf((Object[]) strArr);
        String[] stringArray2 = context.getResources().getStringArray(ColumbusResourceHelper.SUMATRA_CERT_LIST);
        Intrinsics.checkNotNullExpressionValue(stringArray2, "sysUIContext.resources.getStringArray(\n            ColumbusResourceHelper.SUMATRA_CERT_LIST)");
        String[] strArr2 = new String[stringArray2.length];
        System.arraycopy(stringArray2, 0, strArr2, 0, stringArray2.length);
        this.allowCertList = SetsKt__SetsKt.setOf((Object[]) strArr2);
    }

    public void onCreate() {
        super.onCreate();
        Object systemService = getSystemService("launcherapps");
        this.launcherApps = systemService instanceof LauncherApps ? (LauncherApps) systemService : null;
    }

    public IBinder onBind(Intent intent) {
        if (this.columbusContext.isAvailable()) {
            return this.messenger.getBinder();
        }
        return null;
    }

    /* compiled from: ColumbusTargetRequestService.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: ColumbusTargetRequestService.kt */
    public final class IncomingMessageHandler extends Handler {
        final /* synthetic */ ColumbusTargetRequestService this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public IncomingMessageHandler(ColumbusTargetRequestService columbusTargetRequestService, Looper looper) {
            super(looper);
            Intrinsics.checkNotNullParameter(columbusTargetRequestService, "this$0");
            Intrinsics.checkNotNullParameter(looper, "looper");
            this.this$0 = columbusTargetRequestService;
        }

        public void handleMessage(Message message) {
            Intrinsics.checkNotNullParameter(message, "msg");
            String[] packagesForUid = this.this$0.getPackageManager().getPackagesForUid(message.sendingUid);
            String str = packagesForUid == null ? null : packagesForUid[0];
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    Log.w("Columbus/TargetRequest", Intrinsics.stringPlus("Invalid request type: ", Integer.valueOf(i)));
                } else if (str == null || packageIsNotAllowed(str) || packageQuotaIsExhausted(str) || getAppInfoForPackage(str) == null) {
                    replyToMessenger(message.replyTo, message.what, 2);
                } else if (packageIsTarget(str)) {
                    replyToMessenger(message.replyTo, message.what, 0);
                } else if (packageNeedsToCoolDown(str)) {
                    replyToMessenger(message.replyTo, message.what, 3);
                } else {
                    replyToMessenger(message.replyTo, message.what, 1);
                }
            } else if (str == null || packageIsNotAllowed(str)) {
                replyToMessenger(message.replyTo, message.what, 1);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Unsupported caller: ", str));
            } else if (packageIsTarget(str)) {
                replyToMessenger(message.replyTo, message.what, 0);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Caller already target: ", str));
            } else if (packageNeedsToCoolDown(str)) {
                replyToMessenger(message.replyTo, message.what, 2);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Caller throttled: ", str));
            } else if (packageQuotaIsExhausted(str)) {
                replyToMessenger(message.replyTo, message.what, 3);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Caller already shown max times: ", str));
            } else {
                LauncherActivityInfo appInfoForPackage = getAppInfoForPackage(str);
                if (appInfoForPackage == null) {
                    replyToMessenger(message.replyTo, message.what, 4);
                    Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Caller not launchable: ", str));
                    return;
                }
                Messenger messenger = message.replyTo;
                Intrinsics.checkNotNullExpressionValue(messenger, "msg.replyTo");
                displayDialog(appInfoForPackage, messenger, message.what);
            }
        }

        private final boolean packageIsNotAllowed(String str) {
            Signature[] signatureArr;
            if (str == null || !this.this$0.allowPackageList.contains(str)) {
                return true;
            }
            PackageInfo packageInfo = ColumbusTargetRequestService.access$getSysUIContext$p(this.this$0).getPackageManager().getPackageInfo(str, 134217728);
            if (packageInfo.signingInfo.hasMultipleSigners()) {
                signatureArr = packageInfo.signingInfo.getApkContentsSigners();
            } else {
                signatureArr = packageInfo.signingInfo.getSigningCertificateHistory();
            }
            Intrinsics.checkNotNullExpressionValue(signatureArr, "if (packageInfo.signingInfo.hasMultipleSigners()) {\n                packageInfo.signingInfo.apkContentsSigners\n            } else {\n                packageInfo.signingInfo.signingCertificateHistory\n            }");
            ColumbusTargetRequestService columbusTargetRequestService = this.this$0;
            ArrayList arrayList = new ArrayList(signatureArr.length);
            boolean z = false;
            for (Signature signature : signatureArr) {
                byte[] digest = columbusTargetRequestService.messageDigest.digest(signature.toByteArray());
                Intrinsics.checkNotNullExpressionValue(digest, "messageDigest.digest(it.toByteArray())");
                arrayList.add(new String(digest, Charsets.UTF_16));
            }
            ColumbusTargetRequestService columbusTargetRequestService2 = this.this$0;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (columbusTargetRequestService2.allowCertList.contains((String) it.next())) {
                        z = true;
                        break;
                    }
                }
            }
            return !z;
        }

        private final boolean packageNeedsToCoolDown(String str) {
            return ColumbusTargetRequestService.access$getColumbusStructuredDataManager$p(this.this$0).getTimeSinceLastDeny(str) < ColumbusTargetRequestService.PACKAGE_DENY_COOLDOWN_MS;
        }

        private final boolean packageQuotaIsExhausted(String str) {
            return ColumbusTargetRequestService.access$getColumbusStructuredDataManager$p(this.this$0).getPackageShownCount(str) >= 3;
        }

        private final boolean packageIsTarget(String str) {
            boolean isColumbusEnabled = this.this$0.columbusSettings.isColumbusEnabled();
            boolean areEqual = Intrinsics.areEqual("launch", this.this$0.columbusSettings.selectedAction());
            ComponentName unflattenFromString = ComponentName.unflattenFromString(this.this$0.columbusSettings.selectedApp());
            return isColumbusEnabled && areEqual && Intrinsics.areEqual(str, unflattenFromString == null ? null : unflattenFromString.getPackageName());
        }

        private final LauncherActivityInfo getAppInfoForPackage(String str) {
            List<LauncherActivityInfo> activityList;
            LauncherApps launcherApps = this.this$0.launcherApps;
            T t = null;
            if (launcherApps == null || (activityList = launcherApps.getActivityList(str, ColumbusTargetRequestService.access$getUserTracker$p(this.this$0).getUserHandle())) == null) {
                return null;
            }
            ColumbusTargetRequestService columbusTargetRequestService = this.this$0;
            Iterator<T> it = activityList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                T next = it.next();
                T t2 = next;
                boolean z = false;
                try {
                    LauncherApps launcherApps2 = columbusTargetRequestService.launcherApps;
                    if ((launcherApps2 == null ? null : launcherApps2.getMainActivityLaunchIntent(t2.getComponentName(), null, ColumbusTargetRequestService.access$getUserTracker$p(columbusTargetRequestService).getUserHandle())) != null) {
                        z = true;
                        continue;
                    } else {
                        continue;
                    }
                } catch (RuntimeException unused) {
                }
                if (z) {
                    t = next;
                    break;
                }
            }
            return t;
        }

        private final void displayDialog(LauncherActivityInfo launcherActivityInfo, Messenger messenger, int i) {
            this.this$0.mainHandler.post(new ColumbusTargetRequestService$IncomingMessageHandler$displayDialog$1(this.this$0, launcherActivityInfo, this, messenger, i));
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private final void incrementPackageShowCount(String str) {
            ColumbusTargetRequestService.access$getColumbusStructuredDataManager$p(this.this$0).incrementPackageShownCount(str);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private final void replyToMessenger(Messenger messenger, int i, int i2) {
            if (messenger != null) {
                Message what = Message.obtain().setWhat(i2);
                what.arg1 = i;
                messenger.send(what);
            }
        }
    }
}

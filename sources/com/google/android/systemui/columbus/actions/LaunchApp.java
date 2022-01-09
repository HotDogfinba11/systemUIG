package com.google.android.systemui.columbus.actions;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.ColumbusResourceHelper;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.gates.KeyguardVisibility;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: LaunchApp.kt */
public final class LaunchApp extends UserAction {
    public static final Companion Companion = new Companion(null);
    private final ActivityStarter activityStarter;
    private final Set<String> allowCertList;
    private final Set<String> allowPackageList;
    private final Map<ComponentName, PendingIntent> availableApps;
    private final Map<String, Map<String, ShortcutInfo>> availableShortcuts;
    private final Executor bgExecutor;
    private final Handler bgHandler;
    private final LaunchApp$broadcastReceiver$1 broadcastReceiver;
    private ComponentName currentApp;
    private String currentShortcut;
    private final Set<String> denyPackageList;
    private final DeviceConfig.OnPropertiesChangedListener deviceConfigPropertiesChangedListener;
    private final LaunchApp$gateListener$1 gateListener;
    private final KeyguardUpdateMonitor keyguardUpdateMonitor;
    private final LaunchApp$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    private final KeyguardVisibility keyguardVisibility;
    private final LauncherApps launcherApps;
    private final Handler mainHandler;
    private final MessageDigest messageDigest;
    private final ActivityStarter.OnDismissAction onDismissKeyguardAction;
    private final LaunchApp$settingsListener$1 settingsListener = new LaunchApp$settingsListener$1(this);
    private final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    private final String tag = "Columbus/LaunchApp";
    private final UiEventLogger uiEventLogger;
    private final UserManager userManager;
    private final LaunchApp$userSwitchCallback$1 userSwitchCallback;
    private final UserTracker userTracker;

    @Override // com.google.android.systemui.columbus.actions.UserAction
    public boolean availableOnLockscreen() {
        return true;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LaunchApp(Context context, LauncherApps launcherApps2, ActivityStarter activityStarter2, StatusBarKeyguardViewManager statusBarKeyguardViewManager2, IActivityManager iActivityManager, UserManager userManager2, ColumbusSettings columbusSettings, KeyguardVisibility keyguardVisibility2, KeyguardUpdateMonitor keyguardUpdateMonitor2, Handler handler, Handler handler2, Executor executor, UiEventLogger uiEventLogger2, UserTracker userTracker2) {
        super(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(launcherApps2, "launcherApps");
        Intrinsics.checkNotNullParameter(activityStarter2, "activityStarter");
        Intrinsics.checkNotNullParameter(statusBarKeyguardViewManager2, "statusBarKeyguardViewManager");
        Intrinsics.checkNotNullParameter(iActivityManager, "activityManagerService");
        Intrinsics.checkNotNullParameter(userManager2, "userManager");
        Intrinsics.checkNotNullParameter(columbusSettings, "columbusSettings");
        Intrinsics.checkNotNullParameter(keyguardVisibility2, "keyguardVisibility");
        Intrinsics.checkNotNullParameter(keyguardUpdateMonitor2, "keyguardUpdateMonitor");
        Intrinsics.checkNotNullParameter(handler, "mainHandler");
        Intrinsics.checkNotNullParameter(handler2, "bgHandler");
        Intrinsics.checkNotNullParameter(executor, "bgExecutor");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
        this.launcherApps = launcherApps2;
        this.activityStarter = activityStarter2;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager2;
        this.userManager = userManager2;
        this.keyguardVisibility = keyguardVisibility2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor2;
        this.mainHandler = handler;
        this.bgHandler = handler2;
        this.bgExecutor = executor;
        this.uiEventLogger = uiEventLogger2;
        this.userTracker = userTracker2;
        LaunchApp$userSwitchCallback$1 launchApp$userSwitchCallback$1 = new LaunchApp$userSwitchCallback$1(this);
        this.userSwitchCallback = launchApp$userSwitchCallback$1;
        this.broadcastReceiver = new LaunchApp$broadcastReceiver$1(this);
        this.gateListener = new LaunchApp$gateListener$1(this);
        this.keyguardUpdateMonitorCallback = new LaunchApp$keyguardUpdateMonitorCallback$1(this, context);
        LaunchApp$deviceConfigPropertiesChangedListener$1 launchApp$deviceConfigPropertiesChangedListener$1 = new LaunchApp$deviceConfigPropertiesChangedListener$1(this);
        this.deviceConfigPropertiesChangedListener = launchApp$deviceConfigPropertiesChangedListener$1;
        this.onDismissKeyguardAction = new LaunchApp$onDismissKeyguardAction$1(this);
        this.messageDigest = MessageDigest.getInstance("SHA-256");
        String[] stringArray = context.getResources().getStringArray(ColumbusResourceHelper.SUMATRA_ALLOW_LIST);
        Intrinsics.checkNotNullExpressionValue(stringArray, "context.resources.getStringArray(\n            ColumbusResourceHelper.SUMATRA_ALLOW_LIST)");
        String[] strArr = new String[stringArray.length];
        System.arraycopy(stringArray, 0, strArr, 0, stringArray.length);
        this.allowPackageList = SetsKt__SetsKt.setOf((Object[]) strArr);
        String[] stringArray2 = context.getResources().getStringArray(ColumbusResourceHelper.SUMATRA_CERT_LIST);
        Intrinsics.checkNotNullExpressionValue(stringArray2, "context.resources.getStringArray(\n            ColumbusResourceHelper.SUMATRA_CERT_LIST)");
        String[] strArr2 = new String[stringArray2.length];
        System.arraycopy(stringArray2, 0, strArr2, 0, stringArray2.length);
        this.allowCertList = SetsKt__SetsKt.setOf((Object[]) strArr2);
        this.denyPackageList = new LinkedHashSet();
        this.availableApps = new LinkedHashMap();
        this.availableShortcuts = new LinkedHashMap();
        this.currentShortcut = "";
        DeviceConfig.addOnPropertiesChangedListener("systemui", executor, launchApp$deviceConfigPropertiesChangedListener$1);
        updateDenyList(DeviceConfig.getString("systemui", "systemui_google_columbus_secure_deny_list", (String) null));
        try {
            iActivityManager.registerUserSwitchObserver(launchApp$userSwitchCallback$1, "Columbus/LaunchApp");
        } catch (RemoteException e) {
            Log.e("Columbus/LaunchApp", "Failed to register user switch observer", e);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(this.broadcastReceiver, intentFilter);
        context.registerReceiver(this.broadcastReceiver, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        updateAvailableAppsAndShortcutsAsync();
        columbusSettings.registerColumbusSettingsChangeListener(this.settingsListener);
        this.currentApp = ComponentName.unflattenFromString(columbusSettings.selectedApp());
        this.currentShortcut = columbusSettings.selectedAppShortcut();
        this.keyguardVisibility.registerListener(this.gateListener);
        updateAvailable();
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig() {
        return this.tag;
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        if (!maybeLaunchSecureApp()) {
            launchUnsecureApp();
        }
    }

    private final boolean maybeLaunchSecureApp() {
        Intent maybeGetSecureIntent = maybeGetSecureIntent();
        if (maybeGetSecureIntent == null) {
            return false;
        }
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
        makeBasic.setRotationAnimationHint(3);
        try {
            ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, getContext().getBasePackageName(), getContext().getAttributionTag(), maybeGetSecureIntent, maybeGetSecureIntent.resolveTypeIfNeeded(getContext().getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, makeBasic.toBundle(), this.userTracker.getUserId());
            UiEventLogger uiEventLogger2 = this.uiEventLogger;
            ColumbusEvent columbusEvent = ColumbusEvent.COLUMBUS_INVOKED_LAUNCH_APP_SECURE;
            ComponentName componentName = this.currentApp;
            uiEventLogger2.log(columbusEvent, 0, componentName == null ? null : componentName.flattenToString());
            return true;
        } catch (RemoteException e) {
            Log.e("Columbus/LaunchApp", "Unable to start secure activity for", e);
            return false;
        }
    }

    private final Intent maybeGetSecureIntent() {
        if (!stateIsSecure()) {
            return null;
        }
        ComponentName componentName = this.currentApp;
        if (!packageIsAllowed(componentName == null ? null : componentName.getPackageName())) {
            return null;
        }
        Intent intent = new Intent("android.media.action.STILL_IMAGE_CAMERA_SECURE");
        ComponentName componentName2 = this.currentApp;
        Intent putExtra = intent.setPackage(componentName2 == null ? null : componentName2.getPackageName()).putExtra("systemui_google_quick_tap_is_source", true);
        if (putExtra.resolveActivity(getContext().getPackageManager()) == null) {
            return null;
        }
        return putExtra;
    }

    private final boolean stateIsSecure() {
        return this.keyguardVisibility.isBlocking() && this.keyguardVisibility.isKeyguardSecure();
    }

    private final boolean packageIsAllowed(String str) {
        return !(CollectionsKt___CollectionsKt.contains(this.denyPackageList, str)) && packageIsStrictlyAllowed(str);
    }

    private final boolean packageIsStrictlyAllowed(String str) {
        Signature[] signatureArr;
        if (str == null || !this.allowPackageList.contains(str)) {
            return false;
        }
        PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(str, 134217728);
        if (packageInfo.signingInfo.hasMultipleSigners()) {
            signatureArr = packageInfo.signingInfo.getApkContentsSigners();
        } else {
            signatureArr = packageInfo.signingInfo.getSigningCertificateHistory();
        }
        Intrinsics.checkNotNullExpressionValue(signatureArr, "if (packageInfo.signingInfo.hasMultipleSigners()) {\n            packageInfo.signingInfo.apkContentsSigners\n        } else {\n            packageInfo.signingInfo.signingCertificateHistory\n        }");
        ArrayList<String> arrayList = new ArrayList(signatureArr.length);
        for (Signature signature : signatureArr) {
            byte[] digest = this.messageDigest.digest(signature.toByteArray());
            Intrinsics.checkNotNullExpressionValue(digest, "messageDigest.digest(it.toByteArray())");
            arrayList.add(new String(digest, Charsets.UTF_16));
        }
        if (arrayList.isEmpty()) {
            return false;
        }
        for (String str2 : arrayList) {
            if (this.allowCertList.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    private final void launchUnsecureApp() {
        if (this.keyguardVisibility.isKeyguardShowing() && this.keyguardVisibility.isKeyguardSecure()) {
            this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        }
        this.activityStarter.dismissKeyguardThenExecute(this.onDismissKeyguardAction, null, true);
    }

    /* access modifiers changed from: private */
    public final void launchUnsecureAppInternal() {
        ShortcutInfo shortcutInfo;
        String str = null;
        if (usingShortcut()) {
            Map<String, Map<String, ShortcutInfo>> map = this.availableShortcuts;
            ComponentName componentName = this.currentApp;
            Map<String, ShortcutInfo> map2 = map.get(componentName == null ? null : componentName.getPackageName());
            if (map2 != null && (shortcutInfo = map2.get(this.currentShortcut)) != null) {
                UiEventLogger uiEventLogger2 = this.uiEventLogger;
                ColumbusEvent columbusEvent = ColumbusEvent.COLUMBUS_INVOKED_LAUNCH_SHORTCUT;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                ComponentName componentName2 = this.currentApp;
                sb.append((Object) (componentName2 == null ? null : componentName2.getPackageName()));
                sb.append('/');
                sb.append((Object) shortcutInfo.getId());
                uiEventLogger2.log(columbusEvent, 0, sb.toString());
                this.launcherApps.startShortcut(shortcutInfo, null, null);
                return;
            }
            return;
        }
        PendingIntent pendingIntent = this.availableApps.get(this.currentApp);
        if (pendingIntent != null) {
            UiEventLogger uiEventLogger3 = this.uiEventLogger;
            ColumbusEvent columbusEvent2 = ColumbusEvent.COLUMBUS_INVOKED_LAUNCH_APP;
            ComponentName componentName3 = this.currentApp;
            if (componentName3 != null) {
                str = componentName3.flattenToString();
            }
            uiEventLogger3.log(columbusEvent2, 0, str);
            pendingIntent.send();
        }
    }

    /* access modifiers changed from: private */
    public final void updateAvailable() {
        if (usingShortcut()) {
            Map<String, Map<String, ShortcutInfo>> map = this.availableShortcuts;
            ComponentName componentName = this.currentApp;
            Boolean bool = null;
            Map<String, ShortcutInfo> map2 = map.get(componentName == null ? null : componentName.getPackageName());
            if (map2 != null) {
                bool = Boolean.valueOf(map2.containsKey(this.currentShortcut));
            }
            setAvailable(Intrinsics.areEqual(bool, Boolean.TRUE));
            return;
        }
        Map<ComponentName, PendingIntent> map3 = this.availableApps;
        ComponentName componentName2 = this.currentApp;
        Objects.requireNonNull(map3, "null cannot be cast to non-null type kotlin.collections.Map<K, *>");
        setAvailable(map3.containsKey(componentName2));
    }

    /* access modifiers changed from: private */
    public final void updateAvailableAppsAndShortcutsAsync() {
        this.bgHandler.post(new LaunchApp$updateAvailableAppsAndShortcutsAsync$1(this));
    }

    /* access modifiers changed from: private */
    public final List<ShortcutInfo> getAllShortcutsForUser(int i) {
        LauncherApps.ShortcutQuery shortcutQuery = new LauncherApps.ShortcutQuery();
        shortcutQuery.setQueryFlags(9);
        try {
            return this.launcherApps.getShortcuts(shortcutQuery, UserHandle.of(i));
        } catch (Exception e) {
            if ((e instanceof SecurityException) || (e instanceof IllegalStateException)) {
                Log.e("Columbus/LaunchApp", "Failed to query for shortcuts", e);
                return null;
            }
            throw e;
        }
    }

    /* access modifiers changed from: private */
    public final void updateDenyList(String str) {
        this.denyPackageList.clear();
        if (str != null) {
            Set<String> set = this.denyPackageList;
            List<String> list = StringsKt__StringsKt.split$default(str, new String[]{","}, false, 0, 6, null);
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            for (String str2 : list) {
                Objects.requireNonNull(str2, "null cannot be cast to non-null type kotlin.CharSequence");
                arrayList.add(StringsKt__StringsKt.trim(str2).toString());
            }
            set.addAll(arrayList);
        }
    }

    private final boolean usingShortcut() {
        if (this.currentShortcut.length() > 0) {
            String str = this.currentShortcut;
            ComponentName componentName = this.currentApp;
            if (!Intrinsics.areEqual(str, componentName == null ? null : componentName.flattenToString())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String toString() {
        if (!usingShortcut()) {
            return Intrinsics.stringPlus("Launch ", this.currentApp);
        }
        return "Launch " + this.currentApp + " shortcut " + this.currentShortcut;
    }

    /* compiled from: LaunchApp.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* access modifiers changed from: private */
    public final void addShortcutsForApp(LauncherActivityInfo launcherActivityInfo, List<ShortcutInfo> list) {
        if (list != null) {
            ArrayList<ShortcutInfo> arrayList = new ArrayList();
            for (T t : list) {
                if (Intrinsics.areEqual(t.getPackage(), launcherActivityInfo.getComponentName().getPackageName())) {
                    arrayList.add(t);
                }
            }
            for (ShortcutInfo shortcutInfo : arrayList) {
                this.availableShortcuts.putIfAbsent(shortcutInfo.getPackage(), new LinkedHashMap());
                Map<String, ShortcutInfo> map = this.availableShortcuts.get(shortcutInfo.getPackage());
                Intrinsics.checkNotNull(map);
                String id = shortcutInfo.getId();
                Intrinsics.checkNotNullExpressionValue(id, "it.id");
                map.put(id, shortcutInfo);
            }
        }
    }
}

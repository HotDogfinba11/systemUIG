package com.android.systemui.privacy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.permission.PermGroupUsage;
import android.permission.PermissionManager;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyDialogController.kt */
public final class PrivacyDialogController {
    public static final Companion Companion = new Companion(null);
    private final ActivityStarter activityStarter;
    private final AppOpsController appOpsController;
    private final Executor backgroundExecutor;
    private Dialog dialog;
    private final DialogProvider dialogProvider;
    private final KeyguardStateController keyguardStateController;
    private final PrivacyDialogController$onDialogDismissed$1 onDialogDismissed;
    private final PackageManager packageManager;
    private final PermissionManager permissionManager;
    private final PrivacyItemController privacyItemController;
    private final PrivacyLogger privacyLogger;
    private final UiEventLogger uiEventLogger;
    private final Executor uiExecutor;
    private final UserTracker userTracker;

    /* compiled from: PrivacyDialogController.kt */
    public interface DialogProvider {
        PrivacyDialog makeDialog(Context context, List<PrivacyDialog.PrivacyElement> list, Function2<? super String, ? super Integer, Unit> function2);
    }

    public PrivacyDialogController(PermissionManager permissionManager2, PackageManager packageManager2, PrivacyItemController privacyItemController2, UserTracker userTracker2, ActivityStarter activityStarter2, Executor executor, Executor executor2, PrivacyLogger privacyLogger2, KeyguardStateController keyguardStateController2, AppOpsController appOpsController2, UiEventLogger uiEventLogger2, DialogProvider dialogProvider2) {
        Intrinsics.checkNotNullParameter(permissionManager2, "permissionManager");
        Intrinsics.checkNotNullParameter(packageManager2, "packageManager");
        Intrinsics.checkNotNullParameter(privacyItemController2, "privacyItemController");
        Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
        Intrinsics.checkNotNullParameter(activityStarter2, "activityStarter");
        Intrinsics.checkNotNullParameter(executor, "backgroundExecutor");
        Intrinsics.checkNotNullParameter(executor2, "uiExecutor");
        Intrinsics.checkNotNullParameter(privacyLogger2, "privacyLogger");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        Intrinsics.checkNotNullParameter(appOpsController2, "appOpsController");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        Intrinsics.checkNotNullParameter(dialogProvider2, "dialogProvider");
        this.permissionManager = permissionManager2;
        this.packageManager = packageManager2;
        this.privacyItemController = privacyItemController2;
        this.userTracker = userTracker2;
        this.activityStarter = activityStarter2;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.privacyLogger = privacyLogger2;
        this.keyguardStateController = keyguardStateController2;
        this.appOpsController = appOpsController2;
        this.uiEventLogger = uiEventLogger2;
        this.dialogProvider = dialogProvider2;
        this.onDialogDismissed = new PrivacyDialogController$onDialogDismissed$1(this);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public PrivacyDialogController(PermissionManager permissionManager2, PackageManager packageManager2, PrivacyItemController privacyItemController2, UserTracker userTracker2, ActivityStarter activityStarter2, Executor executor, Executor executor2, PrivacyLogger privacyLogger2, KeyguardStateController keyguardStateController2, AppOpsController appOpsController2, UiEventLogger uiEventLogger2) {
        this(permissionManager2, packageManager2, privacyItemController2, userTracker2, activityStarter2, executor, executor2, privacyLogger2, keyguardStateController2, appOpsController2, uiEventLogger2, PrivacyDialogControllerKt.access$getDefaultDialogProvider$p());
        Intrinsics.checkNotNullParameter(permissionManager2, "permissionManager");
        Intrinsics.checkNotNullParameter(packageManager2, "packageManager");
        Intrinsics.checkNotNullParameter(privacyItemController2, "privacyItemController");
        Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
        Intrinsics.checkNotNullParameter(activityStarter2, "activityStarter");
        Intrinsics.checkNotNullParameter(executor, "backgroundExecutor");
        Intrinsics.checkNotNullParameter(executor2, "uiExecutor");
        Intrinsics.checkNotNullParameter(privacyLogger2, "privacyLogger");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        Intrinsics.checkNotNullParameter(appOpsController2, "appOpsController");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
    }

    /* compiled from: PrivacyDialogController.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* access modifiers changed from: private */
    public final void startActivity(String str, int i) {
        Dialog dialog2;
        Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.USER", UserHandle.of(i));
        this.uiEventLogger.log(PrivacyDialogEvent.PRIVACY_DIALOG_ITEM_CLICKED_TO_APP_SETTINGS, i, str);
        this.privacyLogger.logStartSettingsActivityFromDialog(str, i);
        if (!this.keyguardStateController.isUnlocked() && (dialog2 = this.dialog) != null) {
            dialog2.hide();
        }
        this.activityStarter.startActivity(intent, true, (ActivityStarter.Callback) new PrivacyDialogController$startActivity$1(this));
    }

    /* access modifiers changed from: private */
    public final List<PermGroupUsage> permGroupUsage() {
        List<PermGroupUsage> indicatorAppOpUsageData = this.permissionManager.getIndicatorAppOpUsageData(this.appOpsController.isMicMuted());
        Intrinsics.checkNotNullExpressionValue(indicatorAppOpUsageData, "permissionManager.getIndicatorAppOpUsageData(appOpsController.isMicMuted)");
        return indicatorAppOpUsageData;
    }

    public final void showDialog(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        dismissDialog();
        this.backgroundExecutor.execute(new PrivacyDialogController$showDialog$1(this, context));
    }

    public final void dismissDialog() {
        Dialog dialog2 = this.dialog;
        if (dialog2 != null) {
            dialog2.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public final CharSequence getLabelForPackage(String str, int i) {
        try {
            CharSequence loadLabel = this.packageManager.getApplicationInfoAsUser(str, 0, UserHandle.getUserId(i)).loadLabel(this.packageManager);
            Intrinsics.checkNotNullExpressionValue(loadLabel, "{\n            packageManager\n                    .getApplicationInfoAsUser(packageName, 0, UserHandle.getUserId(uid))\n                    .loadLabel(packageManager)\n        }");
            return loadLabel;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("PrivacyDialogController", Intrinsics.stringPlus("Label not found for: ", str));
            return str;
        }
    }

    /* access modifiers changed from: private */
    public final PrivacyType permGroupToPrivacyType(String str) {
        int hashCode = str.hashCode();
        if (hashCode != -1140935117) {
            if (hashCode != 828638019) {
                if (hashCode == 1581272376 && str.equals("android.permission-group.MICROPHONE")) {
                    return PrivacyType.TYPE_MICROPHONE;
                }
            } else if (str.equals("android.permission-group.LOCATION")) {
                return PrivacyType.TYPE_LOCATION;
            }
        } else if (str.equals("android.permission-group.CAMERA")) {
            return PrivacyType.TYPE_CAMERA;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public final PrivacyType filterType(PrivacyType privacyType) {
        if (privacyType == null) {
            return null;
        }
        if ((!(privacyType == PrivacyType.TYPE_CAMERA || privacyType == PrivacyType.TYPE_MICROPHONE) || !this.privacyItemController.getMicCameraAvailable()) && (privacyType != PrivacyType.TYPE_LOCATION || !this.privacyItemController.getLocationAvailable())) {
            privacyType = null;
        }
        return privacyType;
    }

    /* access modifiers changed from: private */
    public final List<PrivacyDialog.PrivacyElement> filterAndSelect(List<PrivacyDialog.PrivacyElement> list) {
        List list2;
        Object obj;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T t : list) {
            PrivacyType type = t.getType();
            Object obj2 = linkedHashMap.get(type);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(type, obj2);
            }
            ((List) obj2).add(t);
        }
        SortedMap sortedMap = MapsKt__MapsJVMKt.toSortedMap(linkedHashMap);
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : sortedMap.entrySet()) {
            List list3 = (List) entry.getValue();
            Intrinsics.checkNotNullExpressionValue(list3, "elements");
            ArrayList arrayList2 = new ArrayList();
            for (Object obj3 : list3) {
                if (((PrivacyDialog.PrivacyElement) obj3).getActive()) {
                    arrayList2.add(obj3);
                }
            }
            if (!arrayList2.isEmpty()) {
                list2 = CollectionsKt___CollectionsKt.sortedWith(arrayList2, new PrivacyDialogController$filterAndSelect$lambda6$$inlined$sortedByDescending$1());
            } else {
                Iterator it = list3.iterator();
                if (!it.hasNext()) {
                    obj = null;
                } else {
                    obj = it.next();
                    if (it.hasNext()) {
                        long lastActiveTimestamp = ((PrivacyDialog.PrivacyElement) obj).getLastActiveTimestamp();
                        do {
                            Object next = it.next();
                            long lastActiveTimestamp2 = ((PrivacyDialog.PrivacyElement) next).getLastActiveTimestamp();
                            if (lastActiveTimestamp < lastActiveTimestamp2) {
                                obj = next;
                                lastActiveTimestamp = lastActiveTimestamp2;
                            }
                        } while (it.hasNext());
                    }
                }
                PrivacyDialog.PrivacyElement privacyElement = (PrivacyDialog.PrivacyElement) obj;
                list2 = privacyElement == null ? null : CollectionsKt__CollectionsJVMKt.listOf(privacyElement);
                if (list2 == null) {
                    list2 = CollectionsKt__CollectionsKt.emptyList();
                }
            }
            boolean unused = CollectionsKt__MutableCollectionsKt.addAll(arrayList, list2);
        }
        return arrayList;
    }
}

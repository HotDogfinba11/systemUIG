package com.android.systemui.privacy;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.permission.PermGroupUsage;
import android.util.Log;
import com.android.systemui.privacy.PrivacyDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: PrivacyDialogController.kt */
public final class PrivacyDialogController$showDialog$1 implements Runnable {
    final /* synthetic */ Context $context;
    final /* synthetic */ PrivacyDialogController this$0;

    PrivacyDialogController$showDialog$1(PrivacyDialogController privacyDialogController, Context context) {
        this.this$0 = privacyDialogController;
        this.$context = context;
    }

    public final void run() {
        PrivacyDialog.PrivacyElement privacyElement;
        T t;
        CharSequence charSequence;
        boolean z;
        List<PermGroupUsage> list = this.this$0.permGroupUsage();
        List<UserInfo> userProfiles = this.this$0.userTracker.getUserProfiles();
        this.this$0.privacyLogger.logUnfilteredPermGroupUsage(list);
        PrivacyDialogController privacyDialogController = this.this$0;
        final ArrayList arrayList = new ArrayList();
        for (T t2 : list) {
            String permGroupName = t2.getPermGroupName();
            Intrinsics.checkNotNullExpressionValue(permGroupName, "it.permGroupName");
            PrivacyType privacyType = privacyDialogController.filterType(privacyDialogController.permGroupToPrivacyType(permGroupName));
            Iterator<T> it = userProfiles.iterator();
            while (true) {
                privacyElement = null;
                if (!it.hasNext()) {
                    t = null;
                    break;
                }
                t = it.next();
                if (((UserInfo) t).id == UserHandle.getUserId(t2.getUid())) {
                    z = true;
                    continue;
                } else {
                    z = false;
                    continue;
                }
                if (z) {
                    break;
                }
            }
            T t3 = t;
            if ((t3 != null || t2.isPhoneCall()) && privacyType != null) {
                if (t2.isPhoneCall()) {
                    charSequence = "";
                } else {
                    String packageName = t2.getPackageName();
                    Intrinsics.checkNotNullExpressionValue(packageName, "it.packageName");
                    charSequence = privacyDialogController.getLabelForPackage(packageName, t2.getUid());
                }
                String packageName2 = t2.getPackageName();
                Intrinsics.checkNotNullExpressionValue(packageName2, "it.packageName");
                privacyElement = new PrivacyDialog.PrivacyElement(privacyType, packageName2, UserHandle.getUserId(t2.getUid()), charSequence, t2.getAttribution(), t2.getLastAccess(), t2.isActive(), t3 == null ? false : t3.isManagedProfile(), t2.isPhoneCall());
            }
            if (privacyElement != null) {
                arrayList.add(privacyElement);
            }
        }
        Executor executor = this.this$0.uiExecutor;
        final PrivacyDialogController privacyDialogController2 = this.this$0;
        final Context context = this.$context;
        executor.execute(new Runnable() {
            /* class com.android.systemui.privacy.PrivacyDialogController$showDialog$1.AnonymousClass1 */

            public final void run() {
                List<PrivacyDialog.PrivacyElement> list = privacyDialogController2.filterAndSelect(arrayList);
                if (!list.isEmpty()) {
                    PrivacyDialog makeDialog = privacyDialogController2.dialogProvider.makeDialog(context, list, new PrivacyDialogController$showDialog$1$1$d$1(privacyDialogController2));
                    makeDialog.setShowForAllUsers(true);
                    makeDialog.addOnDismissListener(privacyDialogController2.onDialogDismissed);
                    makeDialog.show();
                    privacyDialogController2.privacyLogger.logShowDialogContents(list);
                    privacyDialogController2.dialog = makeDialog;
                    return;
                }
                Log.w("PrivacyDialogController", "Trying to show empty dialog");
            }
        });
    }
}

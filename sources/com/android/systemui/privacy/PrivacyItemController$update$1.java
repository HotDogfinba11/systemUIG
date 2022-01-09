package com.android.systemui.privacy;

import android.content.pm.UserInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;

/* access modifiers changed from: package-private */
/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$update$1 implements Runnable {
    final /* synthetic */ boolean $updateUsers;
    final /* synthetic */ PrivacyItemController this$0;

    PrivacyItemController$update$1(boolean z, PrivacyItemController privacyItemController) {
        this.$updateUsers = z;
        this.this$0 = privacyItemController;
    }

    public final void run() {
        if (this.$updateUsers) {
            PrivacyItemController privacyItemController = this.this$0;
            List<UserInfo> userProfiles = privacyItemController.userTracker.getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
            Iterator<T> it = userProfiles.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
            privacyItemController.currentUserIds = arrayList;
            this.this$0.logger.logCurrentProfilesChanged(this.this$0.currentUserIds);
        }
        this.this$0.updateListAndNotifyChanges.run();
    }
}

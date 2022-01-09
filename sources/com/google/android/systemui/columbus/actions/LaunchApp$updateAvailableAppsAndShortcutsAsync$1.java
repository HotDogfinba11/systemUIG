package com.google.android.systemui.columbus.actions;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: LaunchApp.kt */
public final class LaunchApp$updateAvailableAppsAndShortcutsAsync$1 implements Runnable {
    final /* synthetic */ LaunchApp this$0;

    LaunchApp$updateAvailableAppsAndShortcutsAsync$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public final void run() {
        int currentUser = ActivityManager.getCurrentUser();
        if (this.this$0.userManager.isUserUnlocked(currentUser)) {
            this.this$0.availableApps.clear();
            this.this$0.availableShortcuts.clear();
            List<LauncherActivityInfo> activityList = this.this$0.launcherApps.getActivityList(null, UserHandle.of(currentUser));
            List list = this.this$0.getAllShortcutsForUser(currentUser);
            for (LauncherActivityInfo launcherActivityInfo : activityList) {
                try {
                    PendingIntent mainActivityLaunchIntent = this.this$0.launcherApps.getMainActivityLaunchIntent(launcherActivityInfo.getComponentName(), null, UserHandle.of(currentUser));
                    if (mainActivityLaunchIntent != null) {
                        Intent intent = new Intent(mainActivityLaunchIntent.getIntent());
                        intent.putExtra("systemui_google_quick_tap_is_source", true);
                        Map map = this.this$0.availableApps;
                        ComponentName componentName = launcherActivityInfo.getComponentName();
                        Intrinsics.checkNotNullExpressionValue(componentName, "appInfo.componentName");
                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.this$0.getContext(), 0, intent, 67108864, null, this.this$0.userTracker.getUserHandle());
                        Intrinsics.checkNotNullExpressionValue(activityAsUser, "getActivityAsUser(\n                                            context,\n                                            0,\n                                            sourcedIntent,\n                                            FLAG_IMMUTABLE,\n                                            null,\n                                            userTracker.userHandle)");
                        map.put(componentName, activityAsUser);
                        LaunchApp launchApp = this.this$0;
                        Intrinsics.checkNotNullExpressionValue(launcherActivityInfo, "appInfo");
                        launchApp.addShortcutsForApp(launcherActivityInfo, list);
                    }
                } catch (RuntimeException unused) {
                }
            }
            Handler handler = this.this$0.mainHandler;
            final LaunchApp launchApp2 = this.this$0;
            handler.post(new Runnable() {
                /* class com.google.android.systemui.columbus.actions.LaunchApp$updateAvailableAppsAndShortcutsAsync$1.AnonymousClass1 */

                public final void run() {
                    launchApp2.updateAvailable();
                }
            });
            return;
        }
        Log.d("Columbus/LaunchApp", "Did not update apps and shortcuts, user " + currentUser + " not unlocked");
    }
}

package com.android.systemui.assist;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public final class PhoneStateMonitor {
    private static final String[] DEFAULT_HOME_CHANGE_ACTIONS = {"android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED", "android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED"};
    private final Context mContext;
    private ComponentName mDefaultHome = getCurrentDefaultHome();
    private boolean mLauncherShowing;
    private final Optional<Lazy<StatusBar>> mStatusBarOptionalLazy;
    private final StatusBarStateController mStatusBarStateController = ((StatusBarStateController) Dependency.get(StatusBarStateController.class));

    private boolean isLauncherInAllApps() {
        return false;
    }

    private boolean isLauncherInOverview() {
        return false;
    }

    PhoneStateMonitor(Context context, BroadcastDispatcher broadcastDispatcher, Optional<Lazy<StatusBar>> optional, BootCompleteCache bootCompleteCache) {
        this.mContext = context;
        this.mStatusBarOptionalLazy = optional;
        bootCompleteCache.addListener(new PhoneStateMonitor$$ExternalSyntheticLambda0(this));
        IntentFilter intentFilter = new IntentFilter();
        for (String str : DEFAULT_HOME_CHANGE_ACTIONS) {
            intentFilter.addAction(str);
        }
        broadcastDispatcher.registerReceiver(new BroadcastReceiver() {
            /* class com.android.systemui.assist.PhoneStateMonitor.AnonymousClass1 */

            public void onReceive(Context context, Intent intent) {
                PhoneStateMonitor.this.mDefaultHome = PhoneStateMonitor.getCurrentDefaultHome();
            }
        }, intentFilter);
        this.mLauncherShowing = isLauncherShowing(ActivityManagerWrapper.getInstance().getRunningTask());
        TaskStackChangeListeners.getInstance().registerTaskStackListener(new TaskStackChangeListener() {
            /* class com.android.systemui.assist.PhoneStateMonitor.AnonymousClass2 */

            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
                PhoneStateMonitor phoneStateMonitor = PhoneStateMonitor.this;
                phoneStateMonitor.mLauncherShowing = phoneStateMonitor.isLauncherShowing(runningTaskInfo);
            }
        });
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mDefaultHome = getCurrentDefaultHome();
    }

    public int getPhoneState() {
        if (isShadeFullscreen()) {
            return getPhoneLockscreenState();
        }
        if (this.mLauncherShowing) {
            return getPhoneLauncherState();
        }
        return 9;
    }

    /* access modifiers changed from: private */
    public static ComponentName getCurrentDefaultHome() {
        ArrayList arrayList = new ArrayList();
        ComponentName homeActivities = PackageManagerWrapper.getInstance().getHomeActivities(arrayList);
        if (homeActivities != null) {
            return homeActivities;
        }
        int i = Integer.MIN_VALUE;
        Iterator it = arrayList.iterator();
        while (true) {
            ComponentName componentName = null;
            while (true) {
                if (!it.hasNext()) {
                    return componentName;
                }
                ResolveInfo resolveInfo = (ResolveInfo) it.next();
                int i2 = resolveInfo.priority;
                if (i2 > i) {
                    componentName = resolveInfo.activityInfo.getComponentName();
                    i = resolveInfo.priority;
                } else if (i2 == i) {
                }
            }
        }
    }

    private int getPhoneLockscreenState() {
        if (isDozing()) {
            return 1;
        }
        if (isBouncerShowing()) {
            return 3;
        }
        return isKeyguardLocked() ? 2 : 4;
    }

    private int getPhoneLauncherState() {
        if (isLauncherInOverview()) {
            return 6;
        }
        return isLauncherInAllApps() ? 7 : 5;
    }

    private boolean isShadeFullscreen() {
        int state = this.mStatusBarStateController.getState();
        return state == 1 || state == 2;
    }

    private boolean isDozing() {
        return this.mStatusBarStateController.isDozing();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isLauncherShowing(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo == null) {
            return false;
        }
        return runningTaskInfo.topActivity.equals(this.mDefaultHome);
    }

    private boolean isBouncerShowing() {
        return this.mStatusBarOptionalLazy.map(PhoneStateMonitor$$ExternalSyntheticLambda1.INSTANCE).orElse((U) Boolean.FALSE).booleanValue();
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ Boolean lambda$isBouncerShowing$1(Lazy lazy) {
        return Boolean.valueOf(((StatusBar) lazy.get()).isBouncerShowing());
    }

    private boolean isKeyguardLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        return keyguardManager != null && keyguardManager.isKeyguardLocked();
    }
}

package com.android.systemui.statusbar.lockscreen;

import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTarget;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.UserHandle;
import android.view.View;
import android.view.ViewGroup;
import com.android.settingslib.Utils;
import com.android.systemui.R$attr;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.FeatureFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController {
    private final ActivityStarter activityStarter;
    private final LockscreenSmartspaceController$configChangeListener$1 configChangeListener;
    private final ConfigurationController configurationController;
    private final ContentResolver contentResolver;
    private final Context context;
    private final DeviceProvisionedController deviceProvisionedController;
    private final LockscreenSmartspaceController$deviceProvisionedListener$1 deviceProvisionedListener;
    private final Execution execution;
    private final FalsingManager falsingManager;
    private final FeatureFlags featureFlags;
    private final Handler handler;
    private UserHandle managedUserHandle;
    private final BcSmartspaceDataPlugin plugin;
    private final SecureSettings secureSettings;
    private SmartspaceSession session;
    private final SmartspaceSession.OnTargetsAvailableListener sessionListener = new LockscreenSmartspaceController$sessionListener$1(this);
    private final LockscreenSmartspaceController$settingsObserver$1 settingsObserver;
    private boolean showSensitiveContentForCurrentUser;
    private boolean showSensitiveContentForManagedUser;
    private final SmartspaceManager smartspaceManager;
    private BcSmartspaceDataPlugin.SmartspaceView smartspaceView;
    private final StatusBarStateController statusBarStateController;
    private final LockscreenSmartspaceController$statusBarStateListener$1 statusBarStateListener;
    private final Executor uiExecutor;
    private final UserTracker userTracker;
    private final LockscreenSmartspaceController$userTrackerCallback$1 userTrackerCallback = new LockscreenSmartspaceController$userTrackerCallback$1(this);
    private View view;

    public LockscreenSmartspaceController(Context context2, FeatureFlags featureFlags2, SmartspaceManager smartspaceManager2, ActivityStarter activityStarter2, FalsingManager falsingManager2, SecureSettings secureSettings2, UserTracker userTracker2, ContentResolver contentResolver2, ConfigurationController configurationController2, StatusBarStateController statusBarStateController2, DeviceProvisionedController deviceProvisionedController2, Execution execution2, Executor executor, Handler handler2, Optional<BcSmartspaceDataPlugin> optional) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(featureFlags2, "featureFlags");
        Intrinsics.checkNotNullParameter(smartspaceManager2, "smartspaceManager");
        Intrinsics.checkNotNullParameter(activityStarter2, "activityStarter");
        Intrinsics.checkNotNullParameter(falsingManager2, "falsingManager");
        Intrinsics.checkNotNullParameter(secureSettings2, "secureSettings");
        Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
        Intrinsics.checkNotNullParameter(contentResolver2, "contentResolver");
        Intrinsics.checkNotNullParameter(configurationController2, "configurationController");
        Intrinsics.checkNotNullParameter(statusBarStateController2, "statusBarStateController");
        Intrinsics.checkNotNullParameter(deviceProvisionedController2, "deviceProvisionedController");
        Intrinsics.checkNotNullParameter(execution2, "execution");
        Intrinsics.checkNotNullParameter(executor, "uiExecutor");
        Intrinsics.checkNotNullParameter(handler2, "handler");
        Intrinsics.checkNotNullParameter(optional, "optionalPlugin");
        this.context = context2;
        this.featureFlags = featureFlags2;
        this.smartspaceManager = smartspaceManager2;
        this.activityStarter = activityStarter2;
        this.falsingManager = falsingManager2;
        this.secureSettings = secureSettings2;
        this.userTracker = userTracker2;
        this.contentResolver = contentResolver2;
        this.configurationController = configurationController2;
        this.statusBarStateController = statusBarStateController2;
        this.deviceProvisionedController = deviceProvisionedController2;
        this.execution = execution2;
        this.uiExecutor = executor;
        this.handler = handler2;
        this.plugin = optional.orElse(null);
        LockscreenSmartspaceController$deviceProvisionedListener$1 lockscreenSmartspaceController$deviceProvisionedListener$1 = new LockscreenSmartspaceController$deviceProvisionedListener$1(this);
        this.deviceProvisionedListener = lockscreenSmartspaceController$deviceProvisionedListener$1;
        this.settingsObserver = new LockscreenSmartspaceController$settingsObserver$1(this, handler2);
        this.configChangeListener = new LockscreenSmartspaceController$configChangeListener$1(this);
        this.statusBarStateListener = new LockscreenSmartspaceController$statusBarStateListener$1(this);
        deviceProvisionedController2.addCallback(lockscreenSmartspaceController$deviceProvisionedListener$1);
    }

    public final View getView() {
        View view2 = this.view;
        if (view2 != null) {
            return view2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("view");
        throw null;
    }

    public final boolean isEnabled() {
        this.execution.assertIsMainThread();
        return this.featureFlags.isSmartspaceEnabled() && this.plugin != null;
    }

    public final View buildAndConnectView(ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "parent");
        this.execution.assertIsMainThread();
        if (isEnabled()) {
            buildView(viewGroup);
            connectSession();
            return getView();
        }
        throw new RuntimeException("Cannot build view when not enabled");
    }

    public final void requestSmartspaceUpdate() {
        SmartspaceSession smartspaceSession = this.session;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
    }

    private final void buildView(ViewGroup viewGroup) {
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.plugin;
        if (bcSmartspaceDataPlugin != null) {
            if (this.view != null) {
                ViewGroup viewGroup2 = (ViewGroup) getView().getParent();
                if (viewGroup2 != null) {
                    viewGroup2.removeView(getView());
                    return;
                }
                return;
            }
            BcSmartspaceDataPlugin.SmartspaceView view2 = bcSmartspaceDataPlugin.getView(viewGroup);
            view2.registerDataProvider(this.plugin);
            view2.setIntentStarter(new LockscreenSmartspaceController$buildView$2(this));
            view2.setFalsingManager(this.falsingManager);
            Intrinsics.checkNotNullExpressionValue(view2, "ssView");
            this.smartspaceView = view2;
            this.view = (View) view2;
            updateTextColorFromWallpaper();
            this.statusBarStateListener.onDozeAmountChanged(0.0f, this.statusBarStateController.getDozeAmount());
        }
    }

    /* access modifiers changed from: private */
    public final void connectSession() {
        if (this.plugin != null && this.session == null && this.smartspaceView != null && this.deviceProvisionedController.isDeviceProvisioned() && this.deviceProvisionedController.isCurrentUserSetup()) {
            SmartspaceSession createSmartspaceSession = this.smartspaceManager.createSmartspaceSession(new SmartspaceConfig.Builder(this.context, "lockscreen").build());
            createSmartspaceSession.addOnTargetsAvailableListener(this.uiExecutor, this.sessionListener);
            this.session = createSmartspaceSession;
            this.deviceProvisionedController.removeCallback(this.deviceProvisionedListener);
            this.userTracker.addCallback(this.userTrackerCallback, this.uiExecutor);
            this.contentResolver.registerContentObserver(this.secureSettings.getUriFor("lock_screen_allow_private_notifications"), true, this.settingsObserver, -1);
            this.configurationController.addCallback(this.configChangeListener);
            this.statusBarStateController.addCallback(this.statusBarStateListener);
            reloadSmartspace();
        }
    }

    public final void disconnect() {
        this.execution.assertIsMainThread();
        SmartspaceSession smartspaceSession = this.session;
        if (smartspaceSession != null) {
            if (smartspaceSession != null) {
                smartspaceSession.removeOnTargetsAvailableListener(this.sessionListener);
                smartspaceSession.close();
            }
            this.userTracker.removeCallback(this.userTrackerCallback);
            this.contentResolver.unregisterContentObserver(this.settingsObserver);
            this.configurationController.removeCallback(this.configChangeListener);
            this.statusBarStateController.removeCallback(this.statusBarStateListener);
            this.session = null;
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.plugin;
            if (bcSmartspaceDataPlugin != null) {
                bcSmartspaceDataPlugin.onTargetsAvailable(CollectionsKt__CollectionsKt.emptyList());
            }
        }
    }

    public final void addListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        Intrinsics.checkNotNullParameter(smartspaceTargetListener, "listener");
        this.execution.assertIsMainThread();
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.plugin;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.registerListener(smartspaceTargetListener);
        }
    }

    /* access modifiers changed from: private */
    public final boolean filterSmartspaceTarget(SmartspaceTarget smartspaceTarget) {
        UserHandle userHandle = smartspaceTarget.getUserHandle();
        if (Intrinsics.areEqual(userHandle, this.userTracker.getUserHandle())) {
            if (!smartspaceTarget.isSensitive() || this.showSensitiveContentForCurrentUser) {
                return true;
            }
        } else if (Intrinsics.areEqual(userHandle, this.managedUserHandle) && this.userTracker.getUserHandle().getIdentifier() == 0 && (!smartspaceTarget.isSensitive() || this.showSensitiveContentForManagedUser)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public final void updateTextColorFromWallpaper() {
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(this.context, R$attr.wallpaperTextColor);
        BcSmartspaceDataPlugin.SmartspaceView smartspaceView2 = this.smartspaceView;
        if (smartspaceView2 != null) {
            smartspaceView2.setPrimaryTextColor(colorAttrDefaultColor);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("smartspaceView");
            throw null;
        }
    }

    /* access modifiers changed from: private */
    public final void reloadSmartspace() {
        Integer num;
        boolean z = false;
        this.showSensitiveContentForCurrentUser = this.secureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, this.userTracker.getUserId()) == 1;
        UserHandle workProfileUser = getWorkProfileUser();
        this.managedUserHandle = workProfileUser;
        if (workProfileUser == null) {
            num = null;
        } else {
            num = Integer.valueOf(workProfileUser.getIdentifier());
        }
        if (num != null) {
            if (this.secureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, num.intValue()) == 1) {
                z = true;
            }
            this.showSensitiveContentForManagedUser = z;
        }
        SmartspaceSession smartspaceSession = this.session;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
    }

    private final UserHandle getWorkProfileUser() {
        for (UserInfo userInfo : this.userTracker.getUserProfiles()) {
            if (userInfo.isManagedProfile()) {
                return userInfo.getUserHandle();
            }
        }
        return null;
    }
}

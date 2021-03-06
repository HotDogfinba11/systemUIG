package com.android.systemui.statusbar.phone;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.Slog;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingMessage;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.ForegroundServiceNotificationListener;
import com.android.systemui.InitController;
import com.android.systemui.R$id;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptSuppressor;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.row.NotificationInfo;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.List;
import java.util.Objects;

public class StatusBarNotificationPresenter implements NotificationPresenter, ConfigurationController.ConfigurationListener, NotificationRowBinderImpl.BindRowCallback, CommandQueue.Callbacks {
    private final AboveShelfObserver mAboveShelfObserver;
    private final AccessibilityManager mAccessibilityManager;
    private final ActivityStarter mActivityStarter = ((ActivityStarter) Dependency.get(ActivityStarter.class));
    private final IStatusBarService mBarService;
    private final NotificationInfo.CheckSaveListener mCheckSaveListener;
    private final CommandQueue mCommandQueue;
    private boolean mDispatchUiModeChangeOnUserSwitched;
    private final DozeScrimController mDozeScrimController;
    private final DynamicPrivacyController mDynamicPrivacyController;
    private final NotificationEntryManager mEntryManager = ((NotificationEntryManager) Dependency.get(NotificationEntryManager.class));
    private final NotificationGutsManager mGutsManager = ((NotificationGutsManager) Dependency.get(NotificationGutsManager.class));
    private final HeadsUpManagerPhone mHeadsUpManager;
    private final NotificationInterruptSuppressor mInterruptSuppressor;
    private final KeyguardIndicationController mKeyguardIndicationController;
    private final KeyguardManager mKeyguardManager;
    private final KeyguardStateController mKeyguardStateController;
    private final LockscreenGestureLogger mLockscreenGestureLogger = ((LockscreenGestureLogger) Dependency.get(LockscreenGestureLogger.class));
    private final NotificationLockscreenUserManager mLockscreenUserManager = ((NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class));
    private final NotificationMediaManager mMediaManager = ((NotificationMediaManager) Dependency.get(NotificationMediaManager.class));
    private final NotificationPanelViewController mNotificationPanel;
    private final NotificationShadeWindowController mNotificationShadeWindowController;
    private final NotificationGutsManager.OnSettingsClickListener mOnSettingsClickListener;
    private boolean mReinflateNotificationsOnUserSwitched;
    private final ScrimController mScrimController;
    private final ShadeController mShadeController;
    private final LockscreenShadeTransitionController mShadeTransitionController;
    private final StatusBar mStatusBar;
    private final SysuiStatusBarStateController mStatusBarStateController = ((SysuiStatusBarStateController) Dependency.get(StatusBarStateController.class));
    private final NotificationViewHierarchyManager mViewHierarchyManager = ((NotificationViewHierarchyManager) Dependency.get(NotificationViewHierarchyManager.class));
    private final VisualStabilityManager mVisualStabilityManager = ((VisualStabilityManager) Dependency.get(VisualStabilityManager.class));
    protected boolean mVrMode;
    private final IVrStateCallbacks mVrStateCallbacks;

    /* access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onExpandClicked$2() {
        return false;
    }

    public StatusBarNotificationPresenter(Context context, NotificationPanelViewController notificationPanelViewController, HeadsUpManagerPhone headsUpManagerPhone, NotificationShadeWindowView notificationShadeWindowView, NotificationStackScrollLayoutController notificationStackScrollLayoutController, DozeScrimController dozeScrimController, ScrimController scrimController, NotificationShadeWindowController notificationShadeWindowController, DynamicPrivacyController dynamicPrivacyController, KeyguardStateController keyguardStateController, KeyguardIndicationController keyguardIndicationController, StatusBar statusBar, ShadeController shadeController, LockscreenShadeTransitionController lockscreenShadeTransitionController, CommandQueue commandQueue, InitController initController, NotificationInterruptStateProvider notificationInterruptStateProvider) {
        AnonymousClass2 r2 = new IVrStateCallbacks.Stub() {
            /* class com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.AnonymousClass2 */

            public void onVrStateChanged(boolean z) {
                StatusBarNotificationPresenter.this.mVrMode = z;
            }
        };
        this.mVrStateCallbacks = r2;
        this.mCheckSaveListener = new NotificationInfo.CheckSaveListener() {
            /* class com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.AnonymousClass3 */
        };
        this.mOnSettingsClickListener = new NotificationGutsManager.OnSettingsClickListener() {
            /* class com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.AnonymousClass4 */

            @Override // com.android.systemui.statusbar.notification.row.NotificationGutsManager.OnSettingsClickListener
            public void onSettingsClick(String str) {
                try {
                    StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(str);
                } catch (RemoteException unused) {
                }
            }
        };
        this.mInterruptSuppressor = new NotificationInterruptSuppressor() {
            /* class com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.AnonymousClass5 */

            @Override // com.android.systemui.statusbar.notification.interruption.NotificationInterruptSuppressor
            public String getName() {
                return "StatusBarNotificationPresenter";
            }

            @Override // com.android.systemui.statusbar.notification.interruption.NotificationInterruptSuppressor
            public boolean suppressAwakeHeadsUp(NotificationEntry notificationEntry) {
                StatusBarNotification sbn = notificationEntry.getSbn();
                if (StatusBarNotificationPresenter.this.mStatusBar.isOccluded()) {
                    boolean z = StatusBarNotificationPresenter.this.mLockscreenUserManager.isLockscreenPublicMode(StatusBarNotificationPresenter.this.mLockscreenUserManager.getCurrentUserId()) || StatusBarNotificationPresenter.this.mLockscreenUserManager.isLockscreenPublicMode(sbn.getUserId());
                    boolean needsRedaction = StatusBarNotificationPresenter.this.mLockscreenUserManager.needsRedaction(notificationEntry);
                    if (z && needsRedaction) {
                        return true;
                    }
                }
                if (!StatusBarNotificationPresenter.this.mCommandQueue.panelsEnabled()) {
                    return true;
                }
                return sbn.getNotification().fullScreenIntent != null && ((StatusBarNotificationPresenter.this.mKeyguardStateController.isShowing() && !StatusBarNotificationPresenter.this.mStatusBar.isOccluded()) || StatusBarNotificationPresenter.this.mAccessibilityManager.isTouchExplorationEnabled());
            }

            @Override // com.android.systemui.statusbar.notification.interruption.NotificationInterruptSuppressor
            public boolean suppressAwakeInterruptions(NotificationEntry notificationEntry) {
                return StatusBarNotificationPresenter.this.isDeviceInVrMode();
            }

            @Override // com.android.systemui.statusbar.notification.interruption.NotificationInterruptSuppressor
            public boolean suppressInterruptions(NotificationEntry notificationEntry) {
                return StatusBarNotificationPresenter.this.mStatusBar.areNotificationAlertsDisabled();
            }
        };
        this.mKeyguardStateController = keyguardStateController;
        this.mNotificationPanel = notificationPanelViewController;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mKeyguardIndicationController = keyguardIndicationController;
        this.mStatusBar = statusBar;
        this.mShadeController = shadeController;
        this.mShadeTransitionController = lockscreenShadeTransitionController;
        this.mCommandQueue = commandQueue;
        AboveShelfObserver aboveShelfObserver = new AboveShelfObserver(notificationStackScrollLayoutController.getView());
        this.mAboveShelfObserver = aboveShelfObserver;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        aboveShelfObserver.setListener((AboveShelfObserver.HasViewAboveShelfChangedListener) notificationShadeWindowView.findViewById(R$id.notification_container_parent));
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mDozeScrimController = dozeScrimController;
        this.mScrimController = scrimController;
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        IVrManager asInterface = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                asInterface.registerListener(r2);
            } catch (RemoteException e) {
                Slog.e("StatusBarNotificationPresenter", "Failed to register VR mode state listener: " + e);
            }
        }
        NotificationRemoteInputManager notificationRemoteInputManager = (NotificationRemoteInputManager) Dependency.get(NotificationRemoteInputManager.class);
        notificationRemoteInputManager.setUpWithCallback((NotificationRemoteInputManager.Callback) Dependency.get(NotificationRemoteInputManager.Callback.class), this.mNotificationPanel.createRemoteInputDelegate());
        notificationRemoteInputManager.getController().addCallback((RemoteInputController.Callback) Dependency.get(NotificationShadeWindowController.class));
        initController.addPostInitTask(new StatusBarNotificationPresenter$$ExternalSyntheticLambda1(this, notificationStackScrollLayoutController, notificationRemoteInputManager, notificationInterruptStateProvider));
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationRemoteInputManager notificationRemoteInputManager, NotificationInterruptStateProvider notificationInterruptStateProvider) {
        AnonymousClass1 r0 = new NotificationEntryListener() {
            /* class com.android.systemui.statusbar.phone.StatusBarNotificationPresenter.AnonymousClass1 */

            @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
            public void onEntryRemoved(NotificationEntry notificationEntry, NotificationVisibility notificationVisibility, boolean z, int i) {
                StatusBarNotificationPresenter.this.onNotificationRemoved(notificationEntry.getKey(), notificationEntry.getSbn(), i);
                if (z) {
                    StatusBarNotificationPresenter.this.maybeEndAmbientPulse();
                }
            }
        };
        this.mKeyguardIndicationController.init();
        this.mViewHierarchyManager.setUpWithPresenter(this, notificationStackScrollLayoutController.getNotificationListContainer());
        this.mEntryManager.setUpWithPresenter(this);
        this.mEntryManager.addNotificationEntryListener(r0);
        this.mEntryManager.addNotificationLifetimeExtender(this.mHeadsUpManager);
        this.mEntryManager.addNotificationLifetimeExtender(this.mGutsManager);
        this.mEntryManager.addNotificationLifetimeExtenders(notificationRemoteInputManager.getLifetimeExtenders());
        notificationInterruptStateProvider.addSuppressor(this.mInterruptSuppressor);
        this.mLockscreenUserManager.setUpWithPresenter(this);
        this.mMediaManager.setUpWithPresenter(this);
        this.mGutsManager.setUpWithPresenter(this, notificationStackScrollLayoutController.getNotificationListContainer(), this.mCheckSaveListener, this.mOnSettingsClickListener);
        Dependency.get(ForegroundServiceNotificationListener.class);
        onUserSwitched(this.mLockscreenUserManager.getCurrentUserId());
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        MessagingMessage.dropCache();
        MessagingGroup.dropCache();
        if (!((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isSwitchingUser()) {
            updateNotificationsOnDensityOrFontScaleChanged();
        } else {
            this.mReinflateNotificationsOnUserSwitched = true;
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        if (!((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isSwitchingUser()) {
            updateNotificationOnUiModeChanged();
        } else {
            this.mDispatchUiModeChangeOnUserSwitched = true;
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onOverlayChanged() {
        onDensityOrFontScaleChanged();
    }

    private void updateNotificationOnUiModeChanged() {
        List<NotificationEntry> activeNotificationsForCurrentUser = this.mEntryManager.getActiveNotificationsForCurrentUser();
        for (int i = 0; i < activeNotificationsForCurrentUser.size(); i++) {
            ExpandableNotificationRow row = activeNotificationsForCurrentUser.get(i).getRow();
            if (row != null) {
                row.onUiModeChanged();
            }
        }
    }

    private void updateNotificationsOnDensityOrFontScaleChanged() {
        List<NotificationEntry> activeNotificationsForCurrentUser = this.mEntryManager.getActiveNotificationsForCurrentUser();
        for (int i = 0; i < activeNotificationsForCurrentUser.size(); i++) {
            NotificationEntry notificationEntry = activeNotificationsForCurrentUser.get(i);
            notificationEntry.onDensityOrFontScaleChanged();
            if (notificationEntry.areGutsExposed()) {
                this.mGutsManager.onDensityOrFontScaleChanged(notificationEntry);
            }
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public boolean isCollapsing() {
        return this.mNotificationPanel.isCollapsing() || this.mNotificationShadeWindowController.isLaunchingActivity();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void maybeEndAmbientPulse() {
        if (this.mNotificationPanel.hasPulsingNotifications() && !this.mHeadsUpManager.hasNotifications()) {
            this.mDozeScrimController.pulseOutNow();
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    /* renamed from: updateNotificationViews */
    public void lambda$updateNotificationViews$1(String str) {
        if (this.mScrimController != null) {
            if (isCollapsing()) {
                this.mShadeController.addPostCollapseAction(new StatusBarNotificationPresenter$$ExternalSyntheticLambda2(this, str));
                return;
            }
            this.mViewHierarchyManager.updateNotificationViews();
            this.mNotificationPanel.updateNotificationViews(str);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onNotificationRemoved(String str, StatusBarNotification statusBarNotification, int i) {
        if (statusBarNotification != null && !hasActiveNotifications() && !this.mNotificationPanel.isTracking() && !this.mNotificationPanel.isQsExpanded() && this.mStatusBarStateController.getState() == 2 && !isCollapsing()) {
            this.mStatusBarStateController.setState(1);
        }
    }

    public boolean hasActiveNotifications() {
        return this.mEntryManager.hasActiveNotifications();
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void onUserSwitched(int i) {
        this.mHeadsUpManager.setUser(i);
        this.mCommandQueue.animateCollapsePanels();
        if (this.mReinflateNotificationsOnUserSwitched) {
            updateNotificationsOnDensityOrFontScaleChanged();
            this.mReinflateNotificationsOnUserSwitched = false;
        }
        if (this.mDispatchUiModeChangeOnUserSwitched) {
            updateNotificationOnUiModeChanged();
            this.mDispatchUiModeChangeOnUserSwitched = false;
        }
        lambda$updateNotificationViews$1("user switched");
        this.mMediaManager.clearCurrentMediaNotification();
        this.mStatusBar.setLockscreenUser(i);
        updateMediaMetaData(true, false);
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl.BindRowCallback
    public void onBindRow(ExpandableNotificationRow expandableNotificationRow) {
        expandableNotificationRow.setAboveShelfChangedListener(this.mAboveShelfObserver);
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        Objects.requireNonNull(keyguardStateController);
        expandableNotificationRow.setSecureStateProvider(new StatusBarNotificationPresenter$$ExternalSyntheticLambda3(keyguardStateController));
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public boolean isPresenterFullyCollapsed() {
        return this.mNotificationPanel.isFullyCollapsed();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView.OnActivatedListener
    public void onActivated(ActivatableNotificationView activatableNotificationView) {
        onActivated();
        if (activatableNotificationView != null) {
            this.mNotificationPanel.setActivatedChild(activatableNotificationView);
        }
    }

    public void onActivated() {
        this.mLockscreenGestureLogger.write(192, 0, 0);
        this.mLockscreenGestureLogger.log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_NOTIFICATION_FALSE_TOUCH);
        ActivatableNotificationView activatedChild = this.mNotificationPanel.getActivatedChild();
        if (activatedChild != null) {
            activatedChild.makeInactive(true);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView.OnActivatedListener
    public void onActivationReset(ActivatableNotificationView activatableNotificationView) {
        if (activatableNotificationView == this.mNotificationPanel.getActivatedChild()) {
            this.mNotificationPanel.setActivatedChild(null);
            this.mKeyguardIndicationController.hideTransientIndication();
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void updateMediaMetaData(boolean z, boolean z2) {
        this.mMediaManager.updateMediaMetaData(z, z2);
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void onUpdateRowStates() {
        this.mNotificationPanel.onUpdateRowStates();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.OnExpandClickListener
    public void onExpandClicked(NotificationEntry notificationEntry, View view, boolean z) {
        this.mHeadsUpManager.setExpanded(notificationEntry, z);
        this.mStatusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), view, "NOTIFICATION_CLICK");
        if (!z) {
            return;
        }
        if (this.mStatusBarStateController.getState() == 1) {
            this.mShadeTransitionController.goToLockedShade(notificationEntry.getRow());
        } else if (notificationEntry.isSensitive() && this.mDynamicPrivacyController.isInLockedDownShade()) {
            this.mStatusBarStateController.setLeaveOpenOnKeyguardHide(true);
            this.mActivityStarter.dismissKeyguardThenExecute(StatusBarNotificationPresenter$$ExternalSyntheticLambda0.INSTANCE, null, false);
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public boolean isDeviceInVrMode() {
        return this.mVrMode;
    }
}

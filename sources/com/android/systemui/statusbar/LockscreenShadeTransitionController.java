package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.view.View;
import com.android.systemui.ExpandHelper;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.UdfpsKeyguardViewController;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.media.MediaHierarchyManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.Utils;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController {
    private final AmbientState ambientState;
    private Function1<? super Long, Unit> animationHandlerOnKeyguardDismiss;
    private final Context context;
    private final NotificationShadeDepthController depthController;
    private final DisplayMetrics displayMetrics;
    private float dragDownAmount;
    private ValueAnimator dragDownAnimator;
    private NotificationEntry draggedDownEntry;
    private final FalsingCollector falsingCollector;
    private final FeatureFlags featureFlags;
    private boolean forceApplyAmount;
    private int fullTransitionDistance;
    private final KeyguardBypassController keyguardBypassController;
    private final NotificationLockscreenUserManager lockScreenUserManager;
    private final LockscreenGestureLogger lockscreenGestureLogger;
    private final MediaHierarchyManager mediaHierarchyManager;
    private boolean nextHideKeyguardNeedsNoAnimation;
    public NotificationPanelViewController notificationPanelController;
    private NotificationStackScrollLayoutController nsslController;
    private float pulseHeight;
    private ValueAnimator pulseHeightAnimator;
    public QS qS;
    private final ScrimController scrimController;
    private int scrimTransitionDistance;
    private final SysuiStatusBarStateController statusBarStateController;
    public StatusBar statusbar;
    private final DragDownHelper touchHelper;
    private UdfpsKeyguardViewController udfpsKeyguardViewController;
    private boolean useSplitShade;

    public static /* synthetic */ void getDragDownAnimator$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getPulseHeightAnimator$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public final void goToLockedShade(View view) {
        goToLockedShade$default(this, view, false, 2, null);
    }

    public LockscreenShadeTransitionController(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenGestureLogger lockscreenGestureLogger2, KeyguardBypassController keyguardBypassController2, NotificationLockscreenUserManager notificationLockscreenUserManager, FalsingCollector falsingCollector2, AmbientState ambientState2, DisplayMetrics displayMetrics2, MediaHierarchyManager mediaHierarchyManager2, ScrimController scrimController2, NotificationShadeDepthController notificationShadeDepthController, FeatureFlags featureFlags2, Context context2, ConfigurationController configurationController, FalsingManager falsingManager) {
        Intrinsics.checkNotNullParameter(sysuiStatusBarStateController, "statusBarStateController");
        Intrinsics.checkNotNullParameter(lockscreenGestureLogger2, "lockscreenGestureLogger");
        Intrinsics.checkNotNullParameter(keyguardBypassController2, "keyguardBypassController");
        Intrinsics.checkNotNullParameter(notificationLockscreenUserManager, "lockScreenUserManager");
        Intrinsics.checkNotNullParameter(falsingCollector2, "falsingCollector");
        Intrinsics.checkNotNullParameter(ambientState2, "ambientState");
        Intrinsics.checkNotNullParameter(displayMetrics2, "displayMetrics");
        Intrinsics.checkNotNullParameter(mediaHierarchyManager2, "mediaHierarchyManager");
        Intrinsics.checkNotNullParameter(scrimController2, "scrimController");
        Intrinsics.checkNotNullParameter(notificationShadeDepthController, "depthController");
        Intrinsics.checkNotNullParameter(featureFlags2, "featureFlags");
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(configurationController, "configurationController");
        Intrinsics.checkNotNullParameter(falsingManager, "falsingManager");
        this.statusBarStateController = sysuiStatusBarStateController;
        this.lockscreenGestureLogger = lockscreenGestureLogger2;
        this.keyguardBypassController = keyguardBypassController2;
        this.lockScreenUserManager = notificationLockscreenUserManager;
        this.falsingCollector = falsingCollector2;
        this.ambientState = ambientState2;
        this.displayMetrics = displayMetrics2;
        this.mediaHierarchyManager = mediaHierarchyManager2;
        this.scrimController = scrimController2;
        this.depthController = notificationShadeDepthController;
        this.featureFlags = featureFlags2;
        this.context = context2;
        this.touchHelper = new DragDownHelper(falsingManager, falsingCollector2, this, context2);
        updateResources();
        configurationController.addCallback(new ConfigurationController.ConfigurationListener(this) {
            /* class com.android.systemui.statusbar.LockscreenShadeTransitionController.AnonymousClass1 */
            final /* synthetic */ LockscreenShadeTransitionController this$0;

            {
                this.this$0 = r1;
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onConfigChanged(Configuration configuration) {
                this.this$0.updateResources();
                this.this$0.getTouchHelper().updateResources(this.this$0.context);
            }
        });
    }

    public final NotificationPanelViewController getNotificationPanelController() {
        NotificationPanelViewController notificationPanelViewController = this.notificationPanelController;
        if (notificationPanelViewController != null) {
            return notificationPanelViewController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("notificationPanelController");
        throw null;
    }

    public final void setNotificationPanelController(NotificationPanelViewController notificationPanelViewController) {
        Intrinsics.checkNotNullParameter(notificationPanelViewController, "<set-?>");
        this.notificationPanelController = notificationPanelViewController;
    }

    public final StatusBar getStatusbar() {
        StatusBar statusBar = this.statusbar;
        if (statusBar != null) {
            return statusBar;
        }
        Intrinsics.throwUninitializedPropertyAccessException("statusbar");
        throw null;
    }

    public final void setStatusbar(StatusBar statusBar) {
        Intrinsics.checkNotNullParameter(statusBar, "<set-?>");
        this.statusbar = statusBar;
    }

    public final QS getQS() {
        QS qs = this.qS;
        if (qs != null) {
            return qs;
        }
        Intrinsics.throwUninitializedPropertyAccessException("qS");
        throw null;
    }

    public final void setQS(QS qs) {
        Intrinsics.checkNotNullParameter(qs, "<set-?>");
        this.qS = qs;
    }

    public final int getDistanceUntilShowingPulsingNotifications() {
        return this.scrimTransitionDistance;
    }

    public final UdfpsKeyguardViewController getUdfpsKeyguardViewController() {
        return this.udfpsKeyguardViewController;
    }

    public final void setUdfpsKeyguardViewController(UdfpsKeyguardViewController udfpsKeyguardViewController2) {
        this.udfpsKeyguardViewController = udfpsKeyguardViewController2;
    }

    public final DragDownHelper getTouchHelper() {
        return this.touchHelper;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void updateResources() {
        this.scrimTransitionDistance = this.context.getResources().getDimensionPixelSize(R$dimen.lockscreen_shade_scrim_transition_distance);
        this.fullTransitionDistance = this.context.getResources().getDimensionPixelSize(R$dimen.lockscreen_shade_qs_transition_distance);
        this.useSplitShade = Utils.shouldUseSplitNotificationShade(this.featureFlags, this.context.getResources());
    }

    public final void setStackScroller(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        Intrinsics.checkNotNullParameter(notificationStackScrollLayoutController, "nsslController");
        this.nsslController = notificationStackScrollLayoutController;
        DragDownHelper dragDownHelper = this.touchHelper;
        NotificationStackScrollLayout view = notificationStackScrollLayoutController.getView();
        Intrinsics.checkNotNullExpressionValue(view, "nsslController.view");
        dragDownHelper.setHost(view);
        DragDownHelper dragDownHelper2 = this.touchHelper;
        ExpandHelper.Callback expandHelperCallback = notificationStackScrollLayoutController.getExpandHelperCallback();
        Intrinsics.checkNotNullExpressionValue(expandHelperCallback, "nsslController.expandHelperCallback");
        dragDownHelper2.setExpandCallback(expandHelperCallback);
    }

    public final void bindController(NotificationShelfController notificationShelfController) {
        Intrinsics.checkNotNullParameter(notificationShelfController, "notificationShelfController");
        notificationShelfController.setOnClickListener(new LockscreenShadeTransitionController$bindController$1(this));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0011, code lost:
        if (r0.isInLockedDownShade() != false) goto L_0x001b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        /*
            r2 = this;
            com.android.systemui.statusbar.SysuiStatusBarStateController r0 = r2.statusBarStateController
            int r0 = r0.getState()
            r1 = 1
            if (r0 == r1) goto L_0x001b
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r2.nsslController
            if (r0 == 0) goto L_0x0014
            boolean r0 = r0.isInLockedDownShade()
            if (r0 == 0) goto L_0x0026
            goto L_0x001b
        L_0x0014:
            java.lang.String r2 = "nsslController"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r2 = 0
            throw r2
        L_0x001b:
            com.android.systemui.plugins.qs.QS r2 = r2.getQS()
            boolean r2 = r2.isFullyCollapsed()
            if (r2 == 0) goto L_0x0026
            goto L_0x0027
        L_0x0026:
            r1 = 0
        L_0x0027:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.LockscreenShadeTransitionController.canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core():boolean");
    }

    public final void onDraggedDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core(View view, int i) {
        if (canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
            if (notificationStackScrollLayoutController == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nsslController");
                throw null;
            } else if (notificationStackScrollLayoutController.isInLockedDownShade()) {
                this.statusBarStateController.setLeaveOpenOnKeyguardHide(true);
                getStatusbar().dismissKeyguardThenExecute(new LockscreenShadeTransitionController$onDraggedDown$1(this), null, false);
            } else {
                this.lockscreenGestureLogger.write(187, (int) (((float) i) / this.displayMetrics.density), 0);
                this.lockscreenGestureLogger.log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_PULL_SHADE_OPEN);
                if (!this.ambientState.isDozing() || view != null) {
                    goToLockedShadeInternal(view, new LockscreenShadeTransitionController$onDraggedDown$animationHandler$1(view, this), new LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1(this));
                }
            }
        } else {
            setDragDownAmountAnimated$default(this, 0.0f, 0, null, 6, null);
        }
    }

    public final void onDragDownReset$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController != null) {
            notificationStackScrollLayoutController.setDimmed(true, true);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.nsslController;
            if (notificationStackScrollLayoutController2 != null) {
                notificationStackScrollLayoutController2.resetScrollPosition();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = this.nsslController;
                if (notificationStackScrollLayoutController3 != null) {
                    notificationStackScrollLayoutController3.resetCheckSnoozeLeavebehind();
                    setDragDownAmountAnimated$default(this, 0.0f, 0, null, 6, null);
                    return;
                }
                Intrinsics.throwUninitializedPropertyAccessException("nsslController");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("nsslController");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("nsslController");
        throw null;
    }

    public final void onCrossedThreshold$frameworks__base__packages__SystemUI__android_common__SystemUI_core(boolean z) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController != null) {
            notificationStackScrollLayoutController.setDimmed(!z, true);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("nsslController");
            throw null;
        }
    }

    public final void onDragDownStarted$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController != null) {
            notificationStackScrollLayoutController.cancelLongPress();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.nsslController;
            if (notificationStackScrollLayoutController2 != null) {
                notificationStackScrollLayoutController2.checkSnoozeLeavebehind();
                ValueAnimator valueAnimator = this.dragDownAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    return;
                }
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("nsslController");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("nsslController");
        throw null;
    }

    public final boolean isFalsingCheckNeeded$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.statusBarStateController.getState() == 1;
    }

    public final boolean isDragDownEnabledForView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(ExpandableView expandableView) {
        if (isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
            return true;
        }
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nsslController");
            throw null;
        } else if (!notificationStackScrollLayoutController.isInLockedDownShade()) {
            return false;
        } else {
            if (expandableView == null) {
                return true;
            }
            if (expandableView instanceof ExpandableNotificationRow) {
                return ((ExpandableNotificationRow) expandableView).getEntry().isSensitive();
            }
            return false;
        }
    }

    public final boolean isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        if (this.statusBarStateController.getState() != 1 || this.keyguardBypassController.getBypassEnabled() || !getQS().isFullyCollapsed()) {
            return false;
        }
        return true;
    }

    public final float getDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.dragDownAmount;
    }

    public final void setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(float f) {
        if (!(this.dragDownAmount == f) || this.forceApplyAmount) {
            this.dragDownAmount = f;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
            if (notificationStackScrollLayoutController == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nsslController");
                throw null;
            } else if (!notificationStackScrollLayoutController.isInLockedDownShade() || this.forceApplyAmount) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.nsslController;
                if (notificationStackScrollLayoutController2 != null) {
                    notificationStackScrollLayoutController2.setTransitionToFullShadeAmount(this.dragDownAmount);
                    getNotificationPanelController().setTransitionToFullShadeAmount(this.dragDownAmount, false, 0);
                    float f2 = 0.0f;
                    getQS().setTransitionToFullShadeAmount(this.useSplitShade ? 0.0f : this.dragDownAmount, false);
                    if (!this.useSplitShade) {
                        f2 = this.dragDownAmount;
                    }
                    this.mediaHierarchyManager.setTransitionToFullShadeAmount(f2);
                    transitionToShadeAmountCommon(this.dragDownAmount);
                    return;
                }
                Intrinsics.throwUninitializedPropertyAccessException("nsslController");
                throw null;
            }
        }
    }

    private final void transitionToShadeAmountCommon(float f) {
        float saturate = MathUtils.saturate(f / ((float) this.scrimTransitionDistance));
        this.scrimController.setTransitionToFullShadeProgress(saturate);
        getNotificationPanelController().setKeyguardOnlyContentAlpha(1.0f - saturate);
        this.depthController.setTransitionToFullShadeProgress(saturate);
        UdfpsKeyguardViewController udfpsKeyguardViewController2 = this.udfpsKeyguardViewController;
        if (udfpsKeyguardViewController2 != null) {
            udfpsKeyguardViewController2.setTransitionToFullShadeProgress(saturate);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.android.systemui.statusbar.LockscreenShadeTransitionController */
    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void setDragDownAmountAnimated$default(LockscreenShadeTransitionController lockscreenShadeTransitionController, float f, long j, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            j = 0;
        }
        if ((i & 4) != 0) {
            function0 = null;
        }
        lockscreenShadeTransitionController.setDragDownAmountAnimated(f, j, function0);
    }

    private final void setDragDownAmountAnimated(float f, long j, Function0<Unit> function0) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.dragDownAmount, f);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.setDuration(375L);
        ofFloat.addUpdateListener(new LockscreenShadeTransitionController$setDragDownAmountAnimated$1(this));
        if (j > 0) {
            ofFloat.setStartDelay(j);
        }
        if (function0 != null) {
            ofFloat.addListener(new LockscreenShadeTransitionController$setDragDownAmountAnimated$2(function0));
        }
        ofFloat.start();
        this.dragDownAnimator = ofFloat;
    }

    private final void animateAppear(long j) {
        this.forceApplyAmount = true;
        setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(1.0f);
        setDragDownAmountAnimated((float) this.fullTransitionDistance, j, new LockscreenShadeTransitionController$animateAppear$1(this));
    }

    public static /* synthetic */ void goToLockedShade$default(LockscreenShadeTransitionController lockscreenShadeTransitionController, View view, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        lockscreenShadeTransitionController.goToLockedShade(view, z);
    }

    public final void goToLockedShade(View view, boolean z) {
        LockscreenShadeTransitionController$goToLockedShade$1 lockscreenShadeTransitionController$goToLockedShade$1;
        if (this.statusBarStateController.getState() == 1) {
            if (z) {
                lockscreenShadeTransitionController$goToLockedShade$1 = null;
            } else {
                lockscreenShadeTransitionController$goToLockedShade$1 = new LockscreenShadeTransitionController$goToLockedShade$1(this);
            }
            goToLockedShadeInternal(view, lockscreenShadeTransitionController$goToLockedShade$1, null);
        }
    }

    private final void goToLockedShadeInternal(View view, Function1<? super Long, Unit> function1, Runnable runnable) {
        NotificationEntry notificationEntry;
        if (!getStatusbar().isShadeDisabled()) {
            int currentUserId = this.lockScreenUserManager.getCurrentUserId();
            LockscreenShadeTransitionController$goToLockedShadeInternal$1 lockscreenShadeTransitionController$goToLockedShadeInternal$1 = null;
            if (view instanceof ExpandableNotificationRow) {
                notificationEntry = ((ExpandableNotificationRow) view).getEntry();
                notificationEntry.setUserExpanded(true, true);
                notificationEntry.setGroupExpansionChanging(true);
                currentUserId = notificationEntry.getSbn().getUserId();
            } else {
                notificationEntry = null;
            }
            NotificationLockscreenUserManager notificationLockscreenUserManager = this.lockScreenUserManager;
            boolean z = false;
            boolean z2 = !notificationLockscreenUserManager.userAllowsPrivateNotificationsInPublic(notificationLockscreenUserManager.getCurrentUserId()) || !this.lockScreenUserManager.shouldShowLockscreenNotifications() || this.falsingCollector.shouldEnforceBouncer();
            if (!this.keyguardBypassController.getBypassEnabled()) {
                z = z2;
            }
            if (!this.lockScreenUserManager.isLockscreenPublicMode(currentUserId) || !z) {
                this.statusBarStateController.setState(2);
                if (function1 != null) {
                    function1.invoke(0L);
                } else {
                    performDefaultGoToFullShadeAnimation(0);
                }
            } else {
                this.statusBarStateController.setLeaveOpenOnKeyguardHide(true);
                if (function1 != null) {
                    lockscreenShadeTransitionController$goToLockedShadeInternal$1 = new LockscreenShadeTransitionController$goToLockedShadeInternal$1(this, function1);
                }
                getStatusbar().showBouncerWithDimissAndCancelIfKeyguard(lockscreenShadeTransitionController$goToLockedShadeInternal$1, new LockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1(this, runnable));
                this.draggedDownEntry = notificationEntry;
            }
        } else if (runnable != null) {
            runnable.run();
        }
    }

    public final void onHideKeyguard(long j, int i) {
        Function1<? super Long, Unit> function1 = this.animationHandlerOnKeyguardDismiss;
        if (function1 != null) {
            Intrinsics.checkNotNull(function1);
            function1.invoke(Long.valueOf(j));
            this.animationHandlerOnKeyguardDismiss = null;
        } else if (this.nextHideKeyguardNeedsNoAnimation) {
            this.nextHideKeyguardNeedsNoAnimation = false;
        } else if (i != 2) {
            performDefaultGoToFullShadeAnimation(j);
        }
        NotificationEntry notificationEntry = this.draggedDownEntry;
        if (notificationEntry != null) {
            notificationEntry.setUserLocked(false);
            this.draggedDownEntry = null;
        }
    }

    private final void performDefaultGoToFullShadeAnimation(long j) {
        getNotificationPanelController().animateToFullShade(j);
        animateAppear(j);
    }

    public static /* synthetic */ void setPulseHeight$default(LockscreenShadeTransitionController lockscreenShadeTransitionController, float f, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        lockscreenShadeTransitionController.setPulseHeight(f, z);
    }

    public final void setPulseHeight(float f, boolean z) {
        if (z) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.pulseHeight, f);
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            ofFloat.setDuration(375L);
            ofFloat.addUpdateListener(new LockscreenShadeTransitionController$setPulseHeight$1(this));
            ofFloat.start();
            this.pulseHeightAnimator = ofFloat;
            return;
        }
        this.pulseHeight = f;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        if (notificationStackScrollLayoutController != null) {
            getNotificationPanelController().setOverStrechAmount(notificationStackScrollLayoutController.setPulseHeight(f));
            if (!this.keyguardBypassController.getBypassEnabled()) {
                f = 0.0f;
            }
            transitionToShadeAmountCommon(f);
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("nsslController");
        throw null;
    }

    public final void finishPulseAnimation(boolean z) {
        if (z) {
            setPulseHeight(0.0f, true);
            return;
        }
        getNotificationPanelController().onPulseExpansionFinished();
        setPulseHeight(0.0f, false);
    }

    public final void onPulseExpansionStarted() {
        ValueAnimator valueAnimator = this.pulseHeightAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }
}

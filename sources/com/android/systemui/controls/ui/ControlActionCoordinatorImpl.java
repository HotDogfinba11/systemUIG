package com.android.systemui.controls.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.service.controls.Control;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.TaskViewFactory;
import dagger.Lazy;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ControlActionCoordinatorImpl implements ControlActionCoordinator {
    public static final Companion Companion = new Companion(null);
    private Set<String> actionsInProgress = new LinkedHashSet();
    public Context activityContext;
    private final ActivityStarter activityStarter;
    private final DelayableExecutor bgExecutor;
    private final BroadcastDispatcher broadcastDispatcher;
    private final Context context;
    private final ControlsMetricsLogger controlsMetricsLogger;
    private Dialog dialog;
    private final GlobalActionsComponent globalActionsComponent;
    private final KeyguardStateController keyguardStateController;
    private final Lazy<ControlsUiController> lazyUiController;
    private Action pendingAction;
    private final Optional<TaskViewFactory> taskViewFactory;
    private final DelayableExecutor uiExecutor;
    private final Vibrator vibrator;

    public ControlActionCoordinatorImpl(Context context2, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ActivityStarter activityStarter2, KeyguardStateController keyguardStateController2, GlobalActionsComponent globalActionsComponent2, Optional<TaskViewFactory> optional, BroadcastDispatcher broadcastDispatcher2, Lazy<ControlsUiController> lazy, ControlsMetricsLogger controlsMetricsLogger2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(delayableExecutor, "bgExecutor");
        Intrinsics.checkNotNullParameter(delayableExecutor2, "uiExecutor");
        Intrinsics.checkNotNullParameter(activityStarter2, "activityStarter");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        Intrinsics.checkNotNullParameter(globalActionsComponent2, "globalActionsComponent");
        Intrinsics.checkNotNullParameter(optional, "taskViewFactory");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(lazy, "lazyUiController");
        Intrinsics.checkNotNullParameter(controlsMetricsLogger2, "controlsMetricsLogger");
        this.context = context2;
        this.bgExecutor = delayableExecutor;
        this.uiExecutor = delayableExecutor2;
        this.activityStarter = activityStarter2;
        this.keyguardStateController = keyguardStateController2;
        this.globalActionsComponent = globalActionsComponent2;
        this.taskViewFactory = optional;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.lazyUiController = lazy;
        this.controlsMetricsLogger = controlsMetricsLogger2;
        Object systemService = context2.getSystemService("vibrator");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.os.Vibrator");
        this.vibrator = (Vibrator) systemService;
    }

    private final boolean isLocked() {
        return !this.keyguardStateController.isUnlocked();
    }

    public Context getActivityContext() {
        Context context2 = this.activityContext;
        if (context2 != null) {
            return context2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("activityContext");
        throw null;
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void setActivityContext(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "<set-?>");
        this.activityContext = context2;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void closeDialogs() {
        Dialog dialog2 = this.dialog;
        if (dialog2 != null) {
            dialog2.dismiss();
        }
        this.dialog = null;
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void toggle(ControlViewHolder controlViewHolder, String str, boolean z) {
        Intrinsics.checkNotNullParameter(controlViewHolder, "cvh");
        Intrinsics.checkNotNullParameter(str, "templateId");
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        bouncerOrRun(createAction(controlViewHolder.getCws().getCi().getControlId(), new ControlActionCoordinatorImpl$toggle$1(controlViewHolder, str, z), true));
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void touch(ControlViewHolder controlViewHolder, String str, Control control) {
        Intrinsics.checkNotNullParameter(controlViewHolder, "cvh");
        Intrinsics.checkNotNullParameter(str, "templateId");
        Intrinsics.checkNotNullParameter(control, "control");
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        bouncerOrRun(createAction(controlViewHolder.getCws().getCi().getControlId(), new ControlActionCoordinatorImpl$touch$1(controlViewHolder, this, control, str), controlViewHolder.usePanel()));
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void drag(boolean z) {
        if (z) {
            vibrate(Vibrations.INSTANCE.getRangeEdgeEffect());
        } else {
            vibrate(Vibrations.INSTANCE.getRangeMiddleEffect());
        }
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void setValue(ControlViewHolder controlViewHolder, String str, float f) {
        Intrinsics.checkNotNullParameter(controlViewHolder, "cvh");
        Intrinsics.checkNotNullParameter(str, "templateId");
        this.controlsMetricsLogger.drag(controlViewHolder, isLocked());
        bouncerOrRun(createAction(controlViewHolder.getCws().getCi().getControlId(), new ControlActionCoordinatorImpl$setValue$1(controlViewHolder, str, f), false));
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void longPress(ControlViewHolder controlViewHolder) {
        Intrinsics.checkNotNullParameter(controlViewHolder, "cvh");
        this.controlsMetricsLogger.longPress(controlViewHolder, isLocked());
        bouncerOrRun(createAction(controlViewHolder.getCws().getCi().getControlId(), new ControlActionCoordinatorImpl$longPress$1(controlViewHolder, this), false));
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void runPendingAction(String str) {
        Intrinsics.checkNotNullParameter(str, "controlId");
        if (!isLocked()) {
            Action action = this.pendingAction;
            if (Intrinsics.areEqual(action == null ? null : action.getControlId(), str)) {
                Action action2 = this.pendingAction;
                if (action2 != null) {
                    action2.invoke();
                }
                this.pendingAction = null;
            }
        }
    }

    @Override // com.android.systemui.controls.ui.ControlActionCoordinator
    public void enableActionOnTouch(String str) {
        Intrinsics.checkNotNullParameter(str, "controlId");
        this.actionsInProgress.remove(str);
    }

    private final boolean shouldRunAction(String str) {
        if (!this.actionsInProgress.add(str)) {
            return false;
        }
        this.uiExecutor.executeDelayed(new ControlActionCoordinatorImpl$shouldRunAction$1(this, str), 3000);
        return true;
    }

    public final void bouncerOrRun(Action action) {
        Intrinsics.checkNotNullParameter(action, "action");
        if (this.keyguardStateController.isShowing()) {
            if (isLocked()) {
                this.context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
                this.pendingAction = action;
            }
            this.activityStarter.dismissKeyguardThenExecute(new ControlActionCoordinatorImpl$bouncerOrRun$1(action), new ControlActionCoordinatorImpl$bouncerOrRun$2(this), true);
            return;
        }
        action.invoke();
    }

    private final void vibrate(VibrationEffect vibrationEffect) {
        this.bgExecutor.execute(new ControlActionCoordinatorImpl$vibrate$1(this, vibrationEffect));
    }

    /* access modifiers changed from: public */
    private final void showDetail(ControlViewHolder controlViewHolder, Intent intent) {
        this.bgExecutor.execute(new ControlActionCoordinatorImpl$showDetail$1(this, intent, controlViewHolder));
    }

    public final Action createAction(String str, Function0<Unit> function0, boolean z) {
        Intrinsics.checkNotNullParameter(str, "controlId");
        Intrinsics.checkNotNullParameter(function0, "f");
        return new Action(this, str, function0, z);
    }

    public final class Action {
        private final boolean blockable;
        private final String controlId;
        private final Function0<Unit> f;
        final /* synthetic */ ControlActionCoordinatorImpl this$0;

        public Action(ControlActionCoordinatorImpl controlActionCoordinatorImpl, String str, Function0<Unit> function0, boolean z) {
            Intrinsics.checkNotNullParameter(controlActionCoordinatorImpl, "this$0");
            Intrinsics.checkNotNullParameter(str, "controlId");
            Intrinsics.checkNotNullParameter(function0, "f");
            this.this$0 = controlActionCoordinatorImpl;
            this.controlId = str;
            this.f = function0;
            this.blockable = z;
        }

        public final String getControlId() {
            return this.controlId;
        }

        public final void invoke() {
            if (!this.blockable || this.this$0.shouldRunAction(this.controlId)) {
                this.f.invoke();
            }
        }
    }
}

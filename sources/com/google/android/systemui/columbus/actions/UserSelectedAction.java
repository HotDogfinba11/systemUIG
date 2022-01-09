package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.util.Log;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.PowerManagerWrapper;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserSelectedAction.kt */
public final class UserSelectedAction extends Action {
    public static final Companion Companion = new Companion(null);
    private UserAction currentAction;
    private final UserSelectedAction$keyguardMonitorCallback$1 keyguardMonitorCallback = new UserSelectedAction$keyguardMonitorCallback$1(this);
    private final KeyguardStateController keyguardStateController;
    private final PowerManagerWrapper powerManager;
    private final UserSelectedAction$settingsChangeListener$1 settingsChangeListener;
    private final TakeScreenshot takeScreenshot;
    private final Map<String, UserAction> userSelectedActions;
    private final UserSelectedAction$wakefulnessLifecycleObserver$1 wakefulnessLifecycleObserver = new UserSelectedAction$wakefulnessLifecycleObserver$1(this);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UserSelectedAction(Context context, ColumbusSettings columbusSettings, Map<String, UserAction> map, TakeScreenshot takeScreenshot2, KeyguardStateController keyguardStateController2, PowerManagerWrapper powerManagerWrapper, WakefulnessLifecycle wakefulnessLifecycle) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(columbusSettings, "columbusSettings");
        Intrinsics.checkNotNullParameter(map, "userSelectedActions");
        Intrinsics.checkNotNullParameter(takeScreenshot2, "takeScreenshot");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        Intrinsics.checkNotNullParameter(powerManagerWrapper, "powerManager");
        Intrinsics.checkNotNullParameter(wakefulnessLifecycle, "wakefulnessLifecycle");
        this.userSelectedActions = map;
        this.takeScreenshot = takeScreenshot2;
        this.keyguardStateController = keyguardStateController2;
        this.powerManager = powerManagerWrapper;
        UserSelectedAction$settingsChangeListener$1 userSelectedAction$settingsChangeListener$1 = new UserSelectedAction$settingsChangeListener$1(this);
        this.settingsChangeListener = userSelectedAction$settingsChangeListener$1;
        UserAction actionFromSetting = getActionFromSetting(columbusSettings.selectedAction());
        this.currentAction = actionFromSetting;
        Log.i("Columbus/SelectedAction", Intrinsics.stringPlus("User Action selected: ", actionFromSetting));
        columbusSettings.registerColumbusSettingsChangeListener(userSelectedAction$settingsChangeListener$1);
        UserSelectedAction$sublistener$1 userSelectedAction$sublistener$1 = new UserSelectedAction$sublistener$1(this);
        Iterator<T> it = map.values().iterator();
        while (it.hasNext()) {
            it.next().registerListener(userSelectedAction$sublistener$1);
        }
        this.keyguardStateController.addCallback(this.keyguardMonitorCallback);
        wakefulnessLifecycle.addObserver(this.wakefulnessLifecycleObserver);
        updateAvailable();
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig() {
        return this.currentAction.getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig();
    }

    /* access modifiers changed from: private */
    public final void updateAvailable() {
        if (!this.currentAction.isAvailable()) {
            setAvailable(false);
        } else if (!this.currentAction.availableOnScreenOff() && !Intrinsics.areEqual(this.powerManager.isInteractive(), Boolean.TRUE)) {
            setAvailable(false);
        } else if (this.currentAction.availableOnLockscreen() || !this.keyguardStateController.isShowing()) {
            setAvailable(true);
        } else {
            setAvailable(false);
        }
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void onGestureDetected(int i, GestureSensor.DetectionProperties detectionProperties) {
        this.currentAction.onGestureDetected(i, detectionProperties);
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void updateFeedbackEffects(int i, GestureSensor.DetectionProperties detectionProperties) {
        this.currentAction.updateFeedbackEffects(i, detectionProperties);
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.currentAction.onTrigger(detectionProperties);
    }

    /* access modifiers changed from: private */
    public final UserAction getActionFromSetting(String str) {
        return this.userSelectedActions.getOrDefault(str, this.takeScreenshot);
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String toString() {
        return super.toString() + " [currentAction -> " + this.currentAction + ']';
    }

    /* compiled from: UserSelectedAction.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

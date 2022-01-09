package com.google.android.systemui.columbus.actions;

import android.app.KeyguardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.R$styleable;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.tuner.TunerService;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.assist.OpaEnabledListener;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import dagger.Lazy;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LaunchOpa.kt */
public class LaunchOpa extends UserAction {
    public static final Companion Companion = new Companion(null);
    private final AssistManagerGoogle assistManager;
    private boolean enableForAnyAssistant;
    private boolean isGestureEnabled;
    private boolean isOpaEnabled;
    private final Lazy<KeyguardManager> keyguardManager;
    private final OpaEnabledListener opaEnabledListener;
    private final ColumbusContentObserver settingsObserver;
    private final StatusBar statusBar;
    private final String tag = "Columbus/LaunchOpa";
    private final TunerService.Tunable tunable;
    private final UiEventLogger uiEventLogger;

    @Override // com.google.android.systemui.columbus.actions.UserAction
    public boolean availableOnLockscreen() {
        return true;
    }

    @Override // com.google.android.systemui.columbus.actions.UserAction
    public boolean availableOnScreenOff() {
        return true;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LaunchOpa(Context context, StatusBar statusBar2, Set<FeedbackEffect> set, AssistManager assistManager2, Lazy<KeyguardManager> lazy, TunerService tunerService, ColumbusContentObserver.Factory factory, UiEventLogger uiEventLogger2) {
        super(context, set);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(statusBar2, "statusBar");
        Intrinsics.checkNotNullParameter(set, "feedbackEffects");
        Intrinsics.checkNotNullParameter(assistManager2, "assistManager");
        Intrinsics.checkNotNullParameter(lazy, "keyguardManager");
        Intrinsics.checkNotNullParameter(tunerService, "tunerService");
        Intrinsics.checkNotNullParameter(factory, "settingsObserverFactory");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        this.statusBar = statusBar2;
        this.keyguardManager = lazy;
        this.uiEventLogger = uiEventLogger2;
        AssistManagerGoogle assistManagerGoogle = assistManager2 instanceof AssistManagerGoogle ? (AssistManagerGoogle) assistManager2 : null;
        this.assistManager = assistManagerGoogle;
        LaunchOpa$opaEnabledListener$1 launchOpa$opaEnabledListener$1 = new LaunchOpa$opaEnabledListener$1(this);
        this.opaEnabledListener = launchOpa$opaEnabledListener$1;
        Uri uriFor = Settings.Secure.getUriFor("assist_gesture_enabled");
        Intrinsics.checkNotNullExpressionValue(uriFor, "getUriFor(Settings.Secure.ASSIST_GESTURE_ENABLED)");
        ColumbusContentObserver create = factory.create(uriFor, new LaunchOpa$settingsObserver$1(this));
        this.settingsObserver = create;
        LaunchOpa$tunable$1 launchOpa$tunable$1 = new LaunchOpa$tunable$1(this);
        this.tunable = launchOpa$tunable$1;
        this.isGestureEnabled = fetchIsGestureEnabled();
        this.enableForAnyAssistant = Settings.Secure.getInt(getContext().getContentResolver(), "assist_gesture_any_assistant", 0) == 1;
        create.activate();
        tunerService.addTunable(launchOpa$tunable$1, "assist_gesture_any_assistant");
        if (assistManagerGoogle != null) {
            assistManagerGoogle.addOpaEnabledListener(launchOpa$opaEnabledListener$1);
        }
        updateAvailable();
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig() {
        return this.tag;
    }

    /* access modifiers changed from: private */
    public final void updateGestureEnabled() {
        this.isGestureEnabled = fetchIsGestureEnabled();
        updateAvailable();
    }

    private final boolean fetchIsGestureEnabled() {
        if (Settings.Secure.getIntForUser(getContext().getContentResolver(), "assist_gesture_enabled", 1, -2) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public final void updateAvailable() {
        setAvailable(this.isGestureEnabled && this.isOpaEnabled);
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        long j;
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_ASSISTANT);
        this.statusBar.collapseShade();
        if (detectionProperties == null) {
            j = 0;
        } else {
            j = detectionProperties.getActionId();
        }
        launchOpa(j);
    }

    private final void launchOpa(long j) {
        Bundle bundle = new Bundle();
        bundle.putInt("triggered_by", this.keyguardManager.get().isKeyguardLocked() ? R$styleable.AppCompatTheme_windowFixedHeightMajor : R$styleable.AppCompatTheme_windowActionModeOverlay);
        bundle.putLong("latency_id", j);
        bundle.putInt("invocation_type", 2);
        AssistManagerGoogle assistManagerGoogle = this.assistManager;
        if (assistManagerGoogle != null) {
            assistManagerGoogle.startAssist(bundle);
        }
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String toString() {
        return super.toString() + " [isGestureEnabled -> " + this.isGestureEnabled + "; isOpaEnabled -> " + this.isOpaEnabled + ']';
    }

    /* compiled from: LaunchOpa.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

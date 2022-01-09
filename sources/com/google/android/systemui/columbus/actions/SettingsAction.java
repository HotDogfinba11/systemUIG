package com.google.android.systemui.columbus.actions;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Set;
import kotlin.collections.SetsKt__SetsJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SettingsAction.kt */
public final class SettingsAction extends ServiceAction {
    public static final Companion Companion = new Companion(null);
    private final StatusBar statusBar;
    private final Set<String> supportedCallerPackages = SetsKt__SetsJVMKt.setOf("com.android.settings");
    private final String tag = "Columbus/SettingsAction";
    private final UiEventLogger uiEventLogger;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SettingsAction(Context context, StatusBar statusBar2, UiEventLogger uiEventLogger2) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(statusBar2, "statusBar");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        this.statusBar = statusBar2;
        this.uiEventLogger = uiEventLogger2;
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig() {
        return this.tag;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.actions.ServiceAction
    public Set<String> getSupportedCallerPackages() {
        return this.supportedCallerPackages;
    }

    @Override // com.google.android.systemui.columbus.actions.ServiceAction, com.google.android.systemui.columbus.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_ON_SETTINGS);
        this.statusBar.collapseShade();
        super.onTrigger(detectionProperties);
    }

    /* compiled from: SettingsAction.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

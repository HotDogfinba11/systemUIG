package com.google.android.systemui.columbus;

import android.app.backup.BackupManager;
import android.net.Uri;
import android.util.Log;
import com.google.android.systemui.columbus.ColumbusSettings;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: ColumbusSettings.kt */
public final class ColumbusSettings$callback$1 extends Lambda implements Function1<Uri, Unit> {
    final /* synthetic */ ColumbusSettings this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ColumbusSettings$callback$1(ColumbusSettings columbusSettings) {
        super(1);
        this.this$0 = columbusSettings;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Uri uri) {
        invoke(uri);
        return Unit.INSTANCE;
    }

    public final void invoke(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        if (Intrinsics.areEqual(uri, ColumbusSettings.COLUMBUS_ENABLED_URI)) {
            boolean isColumbusEnabled = this.this$0.isColumbusEnabled();
            for (ColumbusSettings.ColumbusSettingsChangeListener columbusSettingsChangeListener : this.this$0.listeners) {
                columbusSettingsChangeListener.onColumbusEnabledChange(isColumbusEnabled);
            }
            BackupManager.dataChangedForUser(this.this$0.userTracker.getUserId(), this.this$0.backupPackage);
        } else if (Intrinsics.areEqual(uri, ColumbusSettings.COLUMBUS_AP_SENSOR_URI)) {
            boolean useApSensor = this.this$0.useApSensor();
            for (ColumbusSettings.ColumbusSettingsChangeListener columbusSettingsChangeListener2 : this.this$0.listeners) {
                columbusSettingsChangeListener2.onUseApSensorChange(useApSensor);
            }
        } else if (Intrinsics.areEqual(uri, ColumbusSettings.COLUMBUS_ACTION_URI)) {
            String selectedAction = this.this$0.selectedAction();
            for (ColumbusSettings.ColumbusSettingsChangeListener columbusSettingsChangeListener3 : this.this$0.listeners) {
                columbusSettingsChangeListener3.onSelectedActionChange(selectedAction);
            }
            BackupManager.dataChangedForUser(this.this$0.userTracker.getUserId(), this.this$0.backupPackage);
        } else if (Intrinsics.areEqual(uri, ColumbusSettings.COLUMBUS_LAUNCH_APP_URI)) {
            String selectedApp = this.this$0.selectedApp();
            for (ColumbusSettings.ColumbusSettingsChangeListener columbusSettingsChangeListener4 : this.this$0.listeners) {
                columbusSettingsChangeListener4.onSelectedAppChange(selectedApp);
            }
            BackupManager.dataChangedForUser(this.this$0.userTracker.getUserId(), this.this$0.backupPackage);
        } else if (Intrinsics.areEqual(uri, ColumbusSettings.COLUMBUS_LAUNCH_APP_SHORTCUT_URI)) {
            String selectedAppShortcut = this.this$0.selectedAppShortcut();
            for (ColumbusSettings.ColumbusSettingsChangeListener columbusSettingsChangeListener5 : this.this$0.listeners) {
                columbusSettingsChangeListener5.onSelectedAppShortcutChange(selectedAppShortcut);
            }
            BackupManager.dataChangedForUser(this.this$0.userTracker.getUserId(), this.this$0.backupPackage);
        } else if (Intrinsics.areEqual(uri, ColumbusSettings.COLUMBUS_LOW_SENSITIVITY_URI)) {
            boolean useLowSensitivity = this.this$0.useLowSensitivity();
            for (ColumbusSettings.ColumbusSettingsChangeListener columbusSettingsChangeListener6 : this.this$0.listeners) {
                columbusSettingsChangeListener6.onLowSensitivityChange(useLowSensitivity);
            }
            BackupManager.dataChangedForUser(this.this$0.userTracker.getUserId(), this.this$0.backupPackage);
        } else if (Intrinsics.areEqual(uri, ColumbusSettings.COLUMBUS_SILENCE_ALERTS_URI)) {
            boolean silenceAlertsEnabled = this.this$0.silenceAlertsEnabled();
            for (ColumbusSettings.ColumbusSettingsChangeListener columbusSettingsChangeListener7 : this.this$0.listeners) {
                columbusSettingsChangeListener7.onAlertSilenceEnabledChange(silenceAlertsEnabled);
            }
        } else {
            Log.w("Columbus/Settings", Intrinsics.stringPlus("Unknown setting change: ", uri));
        }
    }
}

package com.google.android.systemui.columbus.gates;

import android.content.Context;
import com.google.android.systemui.columbus.ColumbusSettings;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SilenceAlertsDisabled.kt */
public final class SilenceAlertsDisabled extends Gate {
    private final ColumbusSettings columbusSettings;
    private final SilenceAlertsDisabled$settingsChangeListener$1 settingsChangeListener = new SilenceAlertsDisabled$settingsChangeListener$1(this);
    private boolean silenceAlertsEnabled;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SilenceAlertsDisabled(Context context, ColumbusSettings columbusSettings2) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(columbusSettings2, "columbusSettings");
        this.columbusSettings = columbusSettings2;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onActivate() {
        this.columbusSettings.registerColumbusSettingsChangeListener(this.settingsChangeListener);
        updateSilenceAlertsEnabled(this.columbusSettings.silenceAlertsEnabled());
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onDeactivate() {
        this.columbusSettings.unregisterColumbusSettingsChangeListener(this.settingsChangeListener);
    }

    /* access modifiers changed from: private */
    public final void updateSilenceAlertsEnabled(boolean z) {
        this.silenceAlertsEnabled = z;
        updateBlocking();
    }

    private final void updateBlocking() {
        setBlocking(!this.silenceAlertsEnabled);
    }
}

package com.google.android.systemui.columbus;

import com.android.systemui.Dumpable;
import com.google.android.systemui.columbus.actions.SettingsAction;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusServiceWrapper.kt */
public final class ColumbusServiceWrapper implements Dumpable {
    private final Lazy<ColumbusService> columbusService;
    private final ColumbusSettings columbusSettings;
    private final Lazy<ColumbusStructuredDataManager> columbusStructuredDataManager;
    private final Lazy<SettingsAction> settingsAction;
    private final ColumbusServiceWrapper$settingsChangeListener$1 settingsChangeListener;
    private boolean started;

    public ColumbusServiceWrapper(ColumbusSettings columbusSettings2, Lazy<ColumbusService> lazy, Lazy<SettingsAction> lazy2, Lazy<ColumbusStructuredDataManager> lazy3) {
        Intrinsics.checkNotNullParameter(columbusSettings2, "columbusSettings");
        Intrinsics.checkNotNullParameter(lazy, "columbusService");
        Intrinsics.checkNotNullParameter(lazy2, "settingsAction");
        Intrinsics.checkNotNullParameter(lazy3, "columbusStructuredDataManager");
        this.columbusSettings = columbusSettings2;
        this.columbusService = lazy;
        this.settingsAction = lazy2;
        this.columbusStructuredDataManager = lazy3;
        ColumbusServiceWrapper$settingsChangeListener$1 columbusServiceWrapper$settingsChangeListener$1 = new ColumbusServiceWrapper$settingsChangeListener$1(this);
        this.settingsChangeListener = columbusServiceWrapper$settingsChangeListener$1;
        if (columbusSettings2.isColumbusEnabled()) {
            startService();
        } else {
            columbusSettings2.registerColumbusSettingsChangeListener(columbusServiceWrapper$settingsChangeListener$1);
            lazy2.get();
        }
        lazy3.get();
    }

    /* access modifiers changed from: private */
    public final void startService() {
        this.columbusSettings.unregisterColumbusSettingsChangeListener(this.settingsChangeListener);
        this.started = true;
        this.columbusService.get();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        if (this.started) {
            this.columbusService.get().dump(fileDescriptor, printWriter, strArr);
        }
    }
}

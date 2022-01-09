package com.android.systemui.media;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LocalMediaManagerFactory.kt */
public final class LocalMediaManagerFactory {
    private final Context context;
    private final LocalBluetoothManager localBluetoothManager;

    public LocalMediaManagerFactory(Context context2, LocalBluetoothManager localBluetoothManager2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
        this.localBluetoothManager = localBluetoothManager2;
    }

    public final LocalMediaManager create(String str) {
        Intrinsics.checkNotNullParameter(str, "packageName");
        return new LocalMediaManager(this.context, this.localBluetoothManager, new InfoMediaManager(this.context, str, null, this.localBluetoothManager), str);
    }
}

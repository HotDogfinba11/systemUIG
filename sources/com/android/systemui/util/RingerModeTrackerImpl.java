package com.android.systemui.util;

import android.media.AudioManager;
import androidx.lifecycle.LiveData;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RingerModeTrackerImpl.kt */
public final class RingerModeTrackerImpl implements RingerModeTracker {
    private final LiveData<Integer> ringerMode;
    private final LiveData<Integer> ringerModeInternal;

    public RingerModeTrackerImpl(AudioManager audioManager, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        Intrinsics.checkNotNullParameter(audioManager, "audioManager");
        Intrinsics.checkNotNullParameter(broadcastDispatcher, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(executor, "executor");
        this.ringerMode = new RingerModeLiveData(broadcastDispatcher, executor, "android.media.RINGER_MODE_CHANGED", new RingerModeTrackerImpl$ringerMode$1(audioManager));
        this.ringerModeInternal = new RingerModeLiveData(broadcastDispatcher, executor, "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", new RingerModeTrackerImpl$ringerModeInternal$1(audioManager));
    }

    @Override // com.android.systemui.util.RingerModeTracker
    public LiveData<Integer> getRingerMode() {
        return this.ringerMode;
    }

    @Override // com.android.systemui.util.RingerModeTracker
    public LiveData<Integer> getRingerModeInternal() {
        return this.ringerModeInternal;
    }
}

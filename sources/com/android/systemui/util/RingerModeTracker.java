package com.android.systemui.util;

import androidx.lifecycle.LiveData;

/* compiled from: RingerModeTracker.kt */
public interface RingerModeTracker {
    LiveData<Integer> getRingerMode();

    LiveData<Integer> getRingerModeInternal();
}

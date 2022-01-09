package com.android.systemui.util;

import android.media.AudioManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* access modifiers changed from: package-private */
/* compiled from: RingerModeTrackerImpl.kt */
public /* synthetic */ class RingerModeTrackerImpl$ringerMode$1 extends FunctionReferenceImpl implements Function0<Integer> {
    RingerModeTrackerImpl$ringerMode$1(AudioManager audioManager) {
        super(0, audioManager, AudioManager.class, "getRingerMode", "getRingerMode()I", 0);
    }

    /* Return type fixed from 'int' to match base method */
    @Override // kotlin.jvm.functions.Function0
    public final Integer invoke() {
        return ((AudioManager) this.receiver).getRingerMode();
    }
}

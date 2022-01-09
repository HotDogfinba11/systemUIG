package com.android.systemui.util;

import android.media.AudioManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* access modifiers changed from: package-private */
/* compiled from: RingerModeTrackerImpl.kt */
public /* synthetic */ class RingerModeTrackerImpl$ringerModeInternal$1 extends FunctionReferenceImpl implements Function0<Integer> {
    RingerModeTrackerImpl$ringerModeInternal$1(AudioManager audioManager) {
        super(0, audioManager, AudioManager.class, "getRingerModeInternal", "getRingerModeInternal()I", 0);
    }

    /* Return type fixed from 'int' to match base method */
    @Override // kotlin.jvm.functions.Function0
    public final Integer invoke() {
        return ((AudioManager) this.receiver).getRingerModeInternal();
    }
}

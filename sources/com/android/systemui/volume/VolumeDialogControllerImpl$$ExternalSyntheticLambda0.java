package com.android.systemui.volume;

import android.os.VibrationEffect;
import android.os.Vibrator;
import java.util.function.Consumer;

public final /* synthetic */ class VolumeDialogControllerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ VibrationEffect f$0;

    public /* synthetic */ VolumeDialogControllerImpl$$ExternalSyntheticLambda0(VibrationEffect vibrationEffect) {
        this.f$0 = vibrationEffect;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        VolumeDialogControllerImpl.lambda$vibrate$0(this.f$0, (Vibrator) obj);
    }
}

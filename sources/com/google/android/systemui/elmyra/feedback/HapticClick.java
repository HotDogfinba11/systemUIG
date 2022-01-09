package com.google.android.systemui.elmyra.feedback;

import android.media.AudioAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.google.android.systemui.elmyra.sensors.GestureSensor;

public class HapticClick implements FeedbackEffect {
    private static final AudioAttributes SONIFICATION_AUDIO_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    private int mLastGestureStage;
    private final VibrationEffect mProgressVibrationEffect;
    private final VibrationEffect mResolveVibrationEffect = VibrationEffect.get(0);
    private final Vibrator mVibrator;

    @Override // com.google.android.systemui.elmyra.feedback.FeedbackEffect
    public void onRelease() {
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x002c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public HapticClick(android.content.Context r5) {
        /*
            r4 = this;
            r4.<init>()
            java.lang.String r0 = "vibrator"
            java.lang.Object r0 = r5.getSystemService(r0)
            android.os.Vibrator r0 = (android.os.Vibrator) r0
            r4.mVibrator = r0
            r1 = 0
            android.os.VibrationEffect r1 = android.os.VibrationEffect.get(r1)
            r4.mResolveVibrationEffect = r1
            r1 = 5
            android.os.VibrationEffect r1 = android.os.VibrationEffect.get(r1)
            r4.mProgressVibrationEffect = r1
            if (r0 == 0) goto L_0x003f
            android.content.res.Resources r2 = r5.getResources()     // Catch:{ NotFoundException -> 0x002c }
            int r3 = com.android.systemui.R$integer.elmyra_progress_always_on_vibration     // Catch:{ NotFoundException -> 0x002c }
            int r2 = r2.getInteger(r3)     // Catch:{ NotFoundException -> 0x002c }
            android.media.AudioAttributes r3 = com.google.android.systemui.elmyra.feedback.HapticClick.SONIFICATION_AUDIO_ATTRIBUTES     // Catch:{ NotFoundException -> 0x002c }
            r0.setAlwaysOnEffect(r2, r1, r3)     // Catch:{ NotFoundException -> 0x002c }
        L_0x002c:
            android.content.res.Resources r5 = r5.getResources()     // Catch:{ NotFoundException -> 0x003f }
            int r0 = com.android.systemui.R$integer.elmyra_resolve_always_on_vibration     // Catch:{ NotFoundException -> 0x003f }
            int r5 = r5.getInteger(r0)     // Catch:{ NotFoundException -> 0x003f }
            android.os.Vibrator r0 = r4.mVibrator     // Catch:{ NotFoundException -> 0x003f }
            android.os.VibrationEffect r4 = r4.mResolveVibrationEffect     // Catch:{ NotFoundException -> 0x003f }
            android.media.AudioAttributes r1 = com.google.android.systemui.elmyra.feedback.HapticClick.SONIFICATION_AUDIO_ATTRIBUTES     // Catch:{ NotFoundException -> 0x003f }
            r0.setAlwaysOnEffect(r5, r4, r1)     // Catch:{ NotFoundException -> 0x003f }
        L_0x003f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.elmyra.feedback.HapticClick.<init>(android.content.Context):void");
    }

    @Override // com.google.android.systemui.elmyra.feedback.FeedbackEffect
    public void onProgress(float f, int i) {
        Vibrator vibrator;
        if (!(this.mLastGestureStage == 2 || i != 2 || (vibrator = this.mVibrator) == null)) {
            vibrator.vibrate(this.mProgressVibrationEffect, SONIFICATION_AUDIO_ATTRIBUTES);
        }
        this.mLastGestureStage = i;
    }

    @Override // com.google.android.systemui.elmyra.feedback.FeedbackEffect
    public void onResolve(GestureSensor.DetectionProperties detectionProperties) {
        Vibrator vibrator;
        if ((detectionProperties == null || !detectionProperties.isHapticConsumed()) && (vibrator = this.mVibrator) != null) {
            vibrator.vibrate(this.mResolveVibrationEffect, SONIFICATION_AUDIO_ATTRIBUTES);
        }
    }
}

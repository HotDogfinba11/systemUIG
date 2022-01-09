package com.android.systemui.statusbar;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;

public class VibratorHelper {
    private static final AudioAttributes STATUS_BAR_VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    private final Context mContext;
    private boolean mHapticFeedbackEnabled;
    private final ContentObserver mVibrationObserver;
    private final Vibrator mVibrator;

    public VibratorHelper(Context context) {
        AnonymousClass1 r0 = new ContentObserver(Handler.getMain()) {
            /* class com.android.systemui.statusbar.VibratorHelper.AnonymousClass1 */

            public void onChange(boolean z) {
                VibratorHelper.this.updateHapticFeedBackEnabled();
            }
        };
        this.mVibrationObserver = r0;
        this.mContext = context;
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("haptic_feedback_enabled"), true, r0);
        r0.onChange(false);
    }

    public void vibrate(int i) {
        if (this.mHapticFeedbackEnabled) {
            AsyncTask.execute(new VibratorHelper$$ExternalSyntheticLambda0(this, i));
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$vibrate$0(int i) {
        this.mVibrator.vibrate(VibrationEffect.get(i, false), STATUS_BAR_VIBRATION_ATTRIBUTES);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateHapticFeedBackEnabled() {
        boolean z = false;
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "haptic_feedback_enabled", 0, -2) != 0) {
            z = true;
        }
        this.mHapticFeedbackEnabled = z;
    }
}

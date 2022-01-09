package com.google.android.systemui.ambientmusic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;

public class AmbientIndicationService extends BroadcastReceiver {
    private final AlarmManager mAlarmManager;
    private final AmbientIndicationContainer mAmbientIndicationContainer;
    private final KeyguardUpdateMonitorCallback mCallback = new KeyguardUpdateMonitorCallback() {
        /* class com.google.android.systemui.ambientmusic.AmbientIndicationService.AnonymousClass1 */

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitchComplete(int i) {
            AmbientIndicationService.this.onUserSwitched();
        }
    };
    private final Context mContext;
    private final AlarmManager.OnAlarmListener mHideIndicationListener;

    public AmbientIndicationService(Context context, AmbientIndicationContainer ambientIndicationContainer, AlarmManager alarmManager) {
        this.mContext = context;
        this.mAmbientIndicationContainer = ambientIndicationContainer;
        this.mAlarmManager = alarmManager;
        this.mHideIndicationListener = new AmbientIndicationService$$ExternalSyntheticLambda0(this);
        start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$0() {
        this.mAmbientIndicationContainer.hideAmbientMusic();
    }

    /* access modifiers changed from: package-private */
    public void start() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.android.ambientindication.action.AMBIENT_INDICATION_SHOW");
        intentFilter.addAction("com.google.android.ambientindication.action.AMBIENT_INDICATION_HIDE");
        this.mContext.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, "com.google.android.ambientindication.permission.AMBIENT_INDICATION", null);
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(this.mCallback);
    }

    public void onReceive(Context context, Intent intent) {
        if (!isForCurrentUser()) {
            Log.i("AmbientIndication", "Suppressing ambient, not for this user.");
        } else if (verifyAmbientApiVersion(intent)) {
            String action = intent.getAction();
            action.hashCode();
            if (action.equals("com.google.android.ambientindication.action.AMBIENT_INDICATION_HIDE")) {
                this.mAlarmManager.cancel(this.mHideIndicationListener);
                this.mAmbientIndicationContainer.hideAmbientMusic();
                Log.i("AmbientIndication", "Hiding ambient indication.");
            } else if (action.equals("com.google.android.ambientindication.action.AMBIENT_INDICATION_SHOW")) {
                long min = Math.min(Math.max(intent.getLongExtra("com.google.android.ambientindication.extra.TTL_MILLIS", 180000), 0L), 180000L);
                boolean booleanExtra = intent.getBooleanExtra("com.google.android.ambientindication.extra.SKIP_UNLOCK", false);
                int intExtra = intent.getIntExtra("com.google.android.ambientindication.extra.ICON_OVERRIDE", 0);
                this.mAmbientIndicationContainer.setAmbientMusic(intent.getCharSequenceExtra("com.google.android.ambientindication.extra.TEXT"), (PendingIntent) intent.getParcelableExtra("com.google.android.ambientindication.extra.OPEN_INTENT"), (PendingIntent) intent.getParcelableExtra("com.google.android.ambientindication.extra.FAVORITING_INTENT"), intExtra, booleanExtra);
                this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + min, "AmbientIndication", this.mHideIndicationListener, null);
                Log.i("AmbientIndication", "Showing ambient indication.");
            }
        }
    }

    private boolean verifyAmbientApiVersion(Intent intent) {
        int intExtra = intent.getIntExtra("com.google.android.ambientindication.extra.VERSION", 0);
        if (intExtra == 1) {
            return true;
        }
        Log.e("AmbientIndication", "AmbientIndicationApi.EXTRA_VERSION is " + 1 + ", but received an intent with version " + intExtra + ", dropping intent.");
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isForCurrentUser() {
        return getSendingUserId() == getCurrentUser() || getSendingUserId() == -1;
    }

    /* access modifiers changed from: package-private */
    public int getCurrentUser() {
        return KeyguardUpdateMonitor.getCurrentUser();
    }

    /* access modifiers changed from: package-private */
    public void onUserSwitched() {
        this.mAmbientIndicationContainer.hideAmbientMusic();
    }
}

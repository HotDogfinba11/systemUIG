package com.google.android.systemui.power;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IHwBinder;
import android.os.LocaleList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerNotificationWarnings;
import com.android.systemui.util.NotificationChannels;
import java.text.NumberFormat;
import java.time.Clock;
import java.util.Locale;
import java.util.NoSuchElementException;
import vendor.google.google_battery.V1_1.IGoogleBattery;

public final class PowerNotificationWarningsGoogleImpl extends PowerNotificationWarnings {
    static final String ACTION_RESUME_CHARGING;
    static final String ACTION_RESUME_CHARGING_SETTINGS;
    static final String KEY_TRIGGER_TIME;
    static final int NOTIFICATION_ID = R$string.defender_notify_title;
    static final int POST_NOTIFICATION_ID = R$string.defender_post_notify_title;
    static final String SHARED_PREFS_FILE;
    static final String TAG_DEFENDER;
    private int mBatteryLevel;
    final BroadcastReceiver mBroadcastReceiver;
    private final Context mContext;
    boolean mDefenderEnabled;
    private final NotificationManager mNotificationManager;
    boolean mPostNotificationVisible;
    boolean mPrvPluggedState;
    boolean mRunBypassActionTask = true;
    private SharedPreferences mSharedPreferences;
    private final UiEventLogger mUiEventLogger;

    public enum BatteryDefenderEvent implements UiEventLogger.UiEventEnum {
        BATTERY_DEFENDER_NOTIFICATION(876),
        BATTERY_DEFENDER_BYPASS_LIMIT(877),
        BATTERY_DEFENDER_BYPASS_LIMIT_FOR_TIPS(878);
        
        private final int mId;

        private BatteryDefenderEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public PowerNotificationWarningsGoogleImpl(Context context, ActivityStarter activityStarter, UiEventLogger uiEventLogger) {
        super(context, activityStarter);
        AnonymousClass1 r8 = new BroadcastReceiver() {
            /* class com.google.android.systemui.power.PowerNotificationWarningsGoogleImpl.AnonymousClass1 */

            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                    PowerNotificationWarningsGoogleImpl.this.resolveBatteryChangedIntent(intent);
                } else if (PowerNotificationWarningsGoogleImpl.ACTION_RESUME_CHARGING.equals(action)) {
                    PowerNotificationWarningsGoogleImpl.this.resumeCharging(BatteryDefenderEvent.BATTERY_DEFENDER_BYPASS_LIMIT);
                } else if (PowerNotificationWarningsGoogleImpl.ACTION_RESUME_CHARGING_SETTINGS.equals(action)) {
                    PowerNotificationWarningsGoogleImpl.this.resumeCharging(BatteryDefenderEvent.BATTERY_DEFENDER_BYPASS_LIMIT_FOR_TIPS);
                }
            }
        };
        this.mBroadcastReceiver = r8;
        this.mContext = context;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction(ACTION_RESUME_CHARGING);
        intentFilter.addAction(ACTION_RESUME_CHARGING_SETTINGS);
        context.registerReceiverAsUser(r8, UserHandle.ALL, intentFilter, null, null);
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver != null) {
            r8.onReceive(context, registerReceiver);
        }
    }

    private void resolveBatteryChangedIntent(Intent intent) {
        this.mBatteryLevel = getBatteryLevel(intent);
        boolean z = false;
        boolean z2 = intent.getIntExtra("plugged", 0) != 0;
        boolean z3 = intent.getIntExtra("health", 1) == 3;
        if ((intent.getIntExtra("status", 1) == 5) || this.mBatteryLevel >= 100) {
            z = true;
        }
        Log.d("PowerNotificationWarningsGoogleImpl", "isPlugged: " + z2 + " | isOverheated: " + z3 + " | defenderEnabled: " + this.mDefenderEnabled + " | isCharged: " + z);
        if (z && this.mPostNotificationVisible) {
            cancelPostNotification();
        }
        if (z3) {
            sendNotification(z2);
        } else if (this.mDefenderEnabled) {
            cancelNotificationAndSendPostNotification();
        }
    }

    private void sendNotification(boolean z) {
        if (!this.mDefenderEnabled) {
            if (this.mPostNotificationVisible) {
                cancelPostNotification();
            }
            getSharedPreferences().edit().putLong(KEY_TRIGGER_TIME, Clock.systemUTC().millis()).apply();
        }
        if (!this.mDefenderEnabled || this.mPrvPluggedState != z) {
            this.mPrvPluggedState = z;
            sendNotificationInternal(z);
        }
    }

    private void cancelNotificationAndSendPostNotification() {
        cancelNotification();
        if (getSharedPreferences().contains(KEY_TRIGGER_TIME)) {
            sendPostNotification();
        }
    }

    private void resumeCharging(BatteryDefenderEvent batteryDefenderEvent) {
        UiEventLogger uiEventLogger = this.mUiEventLogger;
        if (uiEventLogger != null) {
            uiEventLogger.logWithPosition(batteryDefenderEvent, 0, (String) null, this.mBatteryLevel);
        }
        executeBypassActionWithAsync();
        this.mNotificationManager.cancelAsUser(TAG_DEFENDER, NOTIFICATION_ID, UserHandle.ALL);
        clearDefenderStartRecord();
    }

    private void sendNotificationInternal(boolean z) {
        NotificationCompat.Builder addAction = new NotificationCompat.Builder(this.mContext, NotificationChannels.BATTERY).setSmallIcon(17303566).setContentTitle(this.mContext.getString(R$string.defender_notify_title)).setContentText(this.mContext.getString(R$string.defender_notify_des)).addAction(0, this.mContext.getString(R$string.defender_notify_learn_more), createHelpArticlePendingIntent());
        if (z) {
            addAction.addAction(0, this.mContext.getString(R$string.defender_notify_resume_charge), createResumeChargingPendingIntent());
        }
        this.mNotificationManager.notifyAsUser(TAG_DEFENDER, NOTIFICATION_ID, addAction.build(), UserHandle.ALL);
        if (!this.mDefenderEnabled) {
            this.mDefenderEnabled = true;
            UiEventLogger uiEventLogger = this.mUiEventLogger;
            if (uiEventLogger != null) {
                uiEventLogger.log(BatteryDefenderEvent.BATTERY_DEFENDER_NOTIFICATION);
            }
        }
    }

    private void sendPostNotification() {
        long j = getSharedPreferences().getLong(KEY_TRIGGER_TIME, -1);
        String currentTime = j > 0 ? getCurrentTime(j) : null;
        if (currentTime != null) {
            String currentTime2 = getCurrentTime(Clock.systemUTC().millis());
            this.mNotificationManager.notifyAsUser(TAG_DEFENDER, POST_NOTIFICATION_ID, new NotificationCompat.Builder(this.mContext, NotificationChannels.BATTERY).setSmallIcon(17303566).setContentTitle(this.mContext.getString(R$string.defender_post_notify_title)).setContentText(this.mContext.getString(R$string.defender_post_notify_des, NumberFormat.getPercentInstance().format(0.800000011920929d), currentTime, currentTime2)).addAction(0, this.mContext.getString(R$string.defender_notify_learn_more), createHelpArticlePendingIntent()).build(), UserHandle.ALL);
        } else {
            Log.w("PowerNotificationWarningsGoogleImpl", "error getting trigger time");
        }
        this.mPostNotificationVisible = true;
        clearDefenderStartRecord();
    }

    private void executeBypassActionWithAsync() {
        if (this.mRunBypassActionTask) {
            AsyncTask.execute(new PowerNotificationWarningsGoogleImpl$$ExternalSyntheticLambda1(this));
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$executeBypassActionWithAsync$1() {
        PowerNotificationWarningsGoogleImpl$$ExternalSyntheticLambda0 powerNotificationWarningsGoogleImpl$$ExternalSyntheticLambda0 = PowerNotificationWarningsGoogleImpl$$ExternalSyntheticLambda0.INSTANCE;
        IGoogleBattery initHalInterface = initHalInterface(powerNotificationWarningsGoogleImpl$$ExternalSyntheticLambda0);
        if (initHalInterface == null) {
            Log.d("PowerNotificationWarningsGoogleImpl", "Can not init hal interface");
        }
        try {
            initHalInterface.setProperty(1, 17, 1);
            initHalInterface.setProperty(2, 17, 1);
            initHalInterface.setProperty(3, 17, 1);
            destroyHalInterface(initHalInterface, powerNotificationWarningsGoogleImpl$$ExternalSyntheticLambda0);
        } catch (RemoteException e) {
            Log.e("PowerNotificationWarningsGoogleImpl", "setProperty error: " + e);
            destroyHalInterface(initHalInterface, powerNotificationWarningsGoogleImpl$$ExternalSyntheticLambda0);
        }
    }

    private IGoogleBattery initHalInterface(IHwBinder.DeathRecipient deathRecipient) {
        try {
            IGoogleBattery service = IGoogleBattery.getService();
            if (!(service == null || deathRecipient == null)) {
                service.linkToDeath(deathRecipient, 0);
            }
            return service;
        } catch (RemoteException | NoSuchElementException e) {
            Log.e("PowerNotificationWarningsGoogleImpl", "failed to get Google Battery HAL: ", e);
            return null;
        }
    }

    private void destroyHalInterface(IGoogleBattery iGoogleBattery, IHwBinder.DeathRecipient deathRecipient) {
        if (deathRecipient != null) {
            try {
                iGoogleBattery.unlinkToDeath(deathRecipient);
            } catch (RemoteException e) {
                Log.e("PowerNotificationWarningsGoogleImpl", "unlinkToDeath failed: ", e);
            }
        }
    }

    private SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            return sharedPreferences;
        }
        SharedPreferences sharedPreferences2 = this.mContext.getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, 0);
        this.mSharedPreferences = sharedPreferences2;
        return sharedPreferences2;
    }

    private void cancelNotification() {
        this.mDefenderEnabled = false;
        this.mNotificationManager.cancelAsUser(TAG_DEFENDER, NOTIFICATION_ID, UserHandle.ALL);
    }

    private void cancelPostNotification() {
        this.mPostNotificationVisible = false;
        clearDefenderStartRecord();
        this.mNotificationManager.cancelAsUser(TAG_DEFENDER, POST_NOTIFICATION_ID, UserHandle.ALL);
    }

    private void clearDefenderStartRecord() {
        getSharedPreferences().edit().clear().apply();
    }

    private PendingIntent createHelpArticlePendingIntent() {
        return PendingIntent.getActivity(this.mContext, 0, new Intent("android.intent.action.VIEW", Uri.parse(this.mContext.getString(R$string.defender_notify_help_url))), 67108864);
    }

    private PendingIntent createResumeChargingPendingIntent() {
        return PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent(ACTION_RESUME_CHARGING).setPackage(this.mContext.getPackageName()).setFlags(268435456), 67108864, UserHandle.CURRENT);
    }

    private int getBatteryLevel(Intent intent) {
        int intExtra = intent.getIntExtra("level", -1);
        int intExtra2 = intent.getIntExtra("scale", 0);
        if (intExtra2 == 0) {
            return -1;
        }
        return Math.round((((float) intExtra) / ((float) intExtra2)) * 100.0f);
    }

    public String getCurrentTime(long j) {
        Locale locale = getLocale(this.mContext);
        return DateFormat.format(DateFormat.getBestDateTimePattern(locale, DateFormat.is24HourFormat(this.mContext) ? "HH:mm" : "h:m"), j).toString().toUpperCase(locale);
    }

    public Locale getLocale(Context context) {
        LocaleList locales = context.getResources().getConfiguration().getLocales();
        if (locales == null || locales.isEmpty()) {
            return Locale.getDefault();
        }
        return locales.get(0);
    }
}

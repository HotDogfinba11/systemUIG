package com.android.systemui.sensorprivacy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.os.Handler;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SensorUseStartedActivity.kt */
public final class SensorUseStartedActivity extends Activity implements DialogInterface.OnClickListener {
    public static final Companion Companion = new Companion(null);
    private static final String LOG_TAG = SensorUseStartedActivity.class.getSimpleName();
    private final Handler bgHandler;
    private final KeyguardDismissUtil keyguardDismissUtil;
    private final KeyguardStateController keyguardStateController;
    private AlertDialog mDialog;
    private int sensor = -1;
    private final IndividualSensorPrivacyController sensorPrivacyController;
    private IndividualSensorPrivacyController.Callback sensorPrivacyListener;
    private String sensorUsePackageName;
    private boolean unsuppressImmediately;

    public void onBackPressed() {
    }

    public SensorUseStartedActivity(IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController2, KeyguardDismissUtil keyguardDismissUtil2, Handler handler) {
        Intrinsics.checkNotNullParameter(individualSensorPrivacyController, "sensorPrivacyController");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        Intrinsics.checkNotNullParameter(keyguardDismissUtil2, "keyguardDismissUtil");
        Intrinsics.checkNotNullParameter(handler, "bgHandler");
        this.sensorPrivacyController = individualSensorPrivacyController;
        this.keyguardStateController = keyguardStateController2;
        this.keyguardDismissUtil = keyguardDismissUtil2;
        this.bgHandler = handler;
    }

    /* compiled from: SensorUseStartedActivity.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setShowWhenLocked(true);
        setFinishOnTouchOutside(false);
        setResult(0);
        String stringExtra = getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME");
        if (stringExtra != null) {
            this.sensorUsePackageName = stringExtra;
            if (getIntent().getBooleanExtra(SensorPrivacyManager.EXTRA_ALL_SENSORS, false)) {
                this.sensor = Integer.MAX_VALUE;
                SensorUseStartedActivity$onCreate$1 sensorUseStartedActivity$onCreate$1 = new SensorUseStartedActivity$onCreate$1(this);
                this.sensorPrivacyListener = sensorUseStartedActivity$onCreate$1;
                this.sensorPrivacyController.addCallback(sensorUseStartedActivity$onCreate$1);
                if (!this.sensorPrivacyController.isSensorBlocked(1) && !this.sensorPrivacyController.isSensorBlocked(2)) {
                    finish();
                    return;
                }
            } else {
                int intExtra = getIntent().getIntExtra(SensorPrivacyManager.EXTRA_SENSOR, -1);
                if (intExtra == -1) {
                    finish();
                    return;
                }
                Unit unit = Unit.INSTANCE;
                this.sensor = intExtra;
                SensorUseStartedActivity$onCreate$3 sensorUseStartedActivity$onCreate$3 = new SensorUseStartedActivity$onCreate$3(this);
                this.sensorPrivacyListener = sensorUseStartedActivity$onCreate$3;
                this.sensorPrivacyController.addCallback(sensorUseStartedActivity$onCreate$3);
                if (!this.sensorPrivacyController.isSensorBlocked(this.sensor)) {
                    finish();
                    return;
                }
            }
            SensorUseDialog sensorUseDialog = new SensorUseDialog(this, this.sensor, this);
            this.mDialog = sensorUseDialog;
            Intrinsics.checkNotNull(sensorUseDialog);
            sensorUseDialog.show();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        setSuppressed(true);
        this.unsuppressImmediately = false;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            this.unsuppressImmediately = false;
            String str = this.sensorUsePackageName;
            if (str != null) {
                FrameworkStatsLog.write(382, 2, str);
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("sensorUsePackageName");
                throw null;
            }
        } else if (i == -1) {
            if (!this.keyguardStateController.isMethodSecure() || !this.keyguardStateController.isShowing()) {
                disableSensorPrivacy();
                String str2 = this.sensorUsePackageName;
                if (str2 != null) {
                    FrameworkStatsLog.write(382, 1, str2);
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("sensorUsePackageName");
                    throw null;
                }
            } else {
                this.keyguardDismissUtil.executeWhenUnlocked(new SensorUseStartedActivity$onClick$1(this), false, true);
            }
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.unsuppressImmediately) {
            setSuppressed(false);
        } else {
            this.bgHandler.postDelayed(new SensorUseStartedActivity$onStop$1(this), 2000);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        IndividualSensorPrivacyController individualSensorPrivacyController = this.sensorPrivacyController;
        IndividualSensorPrivacyController.Callback callback = this.sensorPrivacyListener;
        if (callback != null) {
            individualSensorPrivacyController.removeCallback(callback);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("sensorPrivacyListener");
            throw null;
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        recreate();
    }

    /* access modifiers changed from: private */
    public final void disableSensorPrivacy() {
        int i = this.sensor;
        if (i == Integer.MAX_VALUE) {
            this.sensorPrivacyController.setSensorBlocked(3, 1, false);
            this.sensorPrivacyController.setSensorBlocked(3, 2, false);
        } else {
            this.sensorPrivacyController.setSensorBlocked(3, i, false);
        }
        this.unsuppressImmediately = true;
        setResult(-1);
    }

    /* access modifiers changed from: private */
    public final void setSuppressed(boolean z) {
        int i = this.sensor;
        if (i == Integer.MAX_VALUE) {
            this.sensorPrivacyController.suppressSensorPrivacyReminders(1, z);
            this.sensorPrivacyController.suppressSensorPrivacyReminders(2, z);
            return;
        }
        this.sensorPrivacyController.suppressSensorPrivacyReminders(i, z);
    }
}

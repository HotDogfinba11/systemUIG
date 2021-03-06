package com.android.keyguard;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.DejankUtils;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

public class EmergencyButtonController extends ViewController<EmergencyButton> {
    private final ActivityTaskManager mActivityTaskManager;
    private final ConfigurationController mConfigurationController;
    private final ConfigurationController.ConfigurationListener mConfigurationListener;
    private EmergencyButtonCallback mEmergencyButtonCallback;
    private final KeyguardUpdateMonitorCallback mInfoCallback;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private final MetricsLogger mMetricsLogger;
    private final PowerManager mPowerManager;
    private ShadeController mShadeController;
    private final TelecomManager mTelecomManager;
    private final TelephonyManager mTelephonyManager;

    public interface EmergencyButtonCallback {
        void onEmergencyButtonClickedWhenInCall();
    }

    private EmergencyButtonController(EmergencyButton emergencyButton, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger) {
        super(emergencyButton);
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() {
            /* class com.android.keyguard.EmergencyButtonController.AnonymousClass1 */

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChanged(int i, int i2, int i3) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onPhoneStateChanged(int i) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() {
            /* class com.android.keyguard.EmergencyButtonController.AnonymousClass2 */

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onConfigChanged(Configuration configuration) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationController = configurationController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mTelephonyManager = telephonyManager;
        this.mPowerManager = powerManager;
        this.mActivityTaskManager = activityTaskManager;
        this.mShadeController = shadeController;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
    }

    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        DejankUtils.whitelistIpcs(new EmergencyButtonController$$ExternalSyntheticLambda1(this));
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        ((EmergencyButton) this.mView).setOnClickListener(new EmergencyButtonController$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onViewAttached$0(View view) {
        takeEmergencyCallAction();
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }

    /* access modifiers changed from: public */
    private void updateEmergencyCallButton() {
        T t = this.mView;
        if (t != null) {
            EmergencyButton emergencyButton = (EmergencyButton) t;
            TelecomManager telecomManager = this.mTelecomManager;
            emergencyButton.updateEmergencyCallButton(telecomManager != null && telecomManager.isInCall(), this.mTelephonyManager.isVoiceCapable(), this.mKeyguardUpdateMonitor.isSimPinVoiceSecure());
        }
    }

    public void setEmergencyButtonCallback(EmergencyButtonCallback emergencyButtonCallback) {
        this.mEmergencyButtonCallback = emergencyButtonCallback;
    }

    public void takeEmergencyCallAction() {
        this.mMetricsLogger.action(200);
        PowerManager powerManager = this.mPowerManager;
        if (powerManager != null) {
            powerManager.userActivity(SystemClock.uptimeMillis(), true);
        }
        this.mActivityTaskManager.stopSystemLockTaskMode();
        this.mShadeController.collapsePanel(false);
        TelecomManager telecomManager = this.mTelecomManager;
        if (telecomManager == null || !telecomManager.isInCall()) {
            this.mKeyguardUpdateMonitor.reportEmergencyCallAction(true);
            TelecomManager telecomManager2 = this.mTelecomManager;
            if (telecomManager2 == null) {
                Log.wtf("EmergencyButton", "TelecomManager was null, cannot launch emergency dialer");
                return;
            }
            getContext().startActivityAsUser(telecomManager2.createLaunchEmergencyDialerIntent(null).setFlags(343932928).putExtra("com.android.phone.EmergencyDialer.extra.ENTRY_TYPE", 1), ActivityOptions.makeCustomAnimation(getContext(), 0, 0).toBundle(), new UserHandle(KeyguardUpdateMonitor.getCurrentUser()));
            return;
        }
        this.mTelecomManager.showInCallScreen(false);
        EmergencyButtonCallback emergencyButtonCallback = this.mEmergencyButtonCallback;
        if (emergencyButtonCallback != null) {
            emergencyButtonCallback.onEmergencyButtonClickedWhenInCall();
        }
    }

    public static class Factory {
        private final ActivityTaskManager mActivityTaskManager;
        private final ConfigurationController mConfigurationController;
        private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        private final MetricsLogger mMetricsLogger;
        private final PowerManager mPowerManager;
        private ShadeController mShadeController;
        private final TelecomManager mTelecomManager;
        private final TelephonyManager mTelephonyManager;

        public Factory(ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger) {
            this.mConfigurationController = configurationController;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mTelephonyManager = telephonyManager;
            this.mPowerManager = powerManager;
            this.mActivityTaskManager = activityTaskManager;
            this.mShadeController = shadeController;
            this.mTelecomManager = telecomManager;
            this.mMetricsLogger = metricsLogger;
        }

        public EmergencyButtonController create(EmergencyButton emergencyButton) {
            return new EmergencyButtonController(emergencyButton, this.mConfigurationController, this.mKeyguardUpdateMonitor, this.mTelephonyManager, this.mPowerManager, this.mActivityTaskManager, this.mShadeController, this.mTelecomManager, this.mMetricsLogger);
        }
    }
}

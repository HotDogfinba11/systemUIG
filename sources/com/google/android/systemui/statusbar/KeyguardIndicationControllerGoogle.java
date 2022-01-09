package com.google.android.systemui.statusbar;

import android.app.IActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.text.format.DateFormat;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IBatteryStats;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.R$anim;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.keyguard.KeyguardIndication;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.DateFormatUtil;
import com.android.systemui.util.wakelock.WakeLock;
import com.google.android.systemui.adaptivecharging.AdaptiveChargingManager;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class KeyguardIndicationControllerGoogle extends KeyguardIndicationController {
    private boolean mAdaptiveChargingActive;
    private boolean mAdaptiveChargingEnabledInSettings;
    @VisibleForTesting
    protected AdaptiveChargingManager mAdaptiveChargingManager;
    @VisibleForTesting
    protected AdaptiveChargingManager.AdaptiveChargingStatusReceiver mAdaptiveChargingStatusReceiver = new AdaptiveChargingManager.AdaptiveChargingStatusReceiver() {
        /* class com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle.AnonymousClass2 */

        @Override // com.google.android.systemui.adaptivecharging.AdaptiveChargingManager.AdaptiveChargingStatusReceiver
        public void onDestroyInterface() {
        }

        @Override // com.google.android.systemui.adaptivecharging.AdaptiveChargingManager.AdaptiveChargingStatusReceiver
        public void onReceiveStatus(String str, int i) {
            boolean z = KeyguardIndicationControllerGoogle.this.mAdaptiveChargingActive;
            KeyguardIndicationControllerGoogle.this.mAdaptiveChargingActive = AdaptiveChargingManager.isStageActiveOrEnabled(str) && i > 0;
            long j = KeyguardIndicationControllerGoogle.this.mEstimatedChargeCompletion;
            KeyguardIndicationControllerGoogle keyguardIndicationControllerGoogle = KeyguardIndicationControllerGoogle.this;
            long currentTimeMillis = System.currentTimeMillis();
            TimeUnit timeUnit = TimeUnit.SECONDS;
            keyguardIndicationControllerGoogle.mEstimatedChargeCompletion = currentTimeMillis + timeUnit.toMillis((long) (i + 29));
            long abs = Math.abs(KeyguardIndicationControllerGoogle.this.mEstimatedChargeCompletion - j);
            if (z != KeyguardIndicationControllerGoogle.this.mAdaptiveChargingActive || (KeyguardIndicationControllerGoogle.this.mAdaptiveChargingActive && abs > timeUnit.toMillis(30))) {
                KeyguardIndicationControllerGoogle.this.updateIndication(true);
            }
        }
    };
    private final IBatteryStats mBatteryInfo;
    private int mBatteryLevel;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        /* class com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.google.android.systemui.adaptivecharging.ADAPTIVE_CHARGING_DEADLINE_SET")) {
                KeyguardIndicationControllerGoogle.this.triggerAdaptiveChargingStatusUpdate();
            }
        }
    };
    private final Context mContext;
    private final DateFormatUtil mDateFormatUtil;
    private final DeviceConfigProxy mDeviceConfig;
    private long mEstimatedChargeCompletion;
    private boolean mInited;
    private boolean mIsCharging;
    private StatusBar mStatusBar;
    private final TunerService mTunerService;
    private KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    public KeyguardIndicationControllerGoogle(Context context, WakeLock.Builder builder, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, IBatteryStats iBatteryStats, UserManager userManager, TunerService tunerService, DeviceConfigProxy deviceConfigProxy, DelayableExecutor delayableExecutor, FalsingManager falsingManager, LockPatternUtils lockPatternUtils, IActivityManager iActivityManager, KeyguardBypassController keyguardBypassController) {
        super(context, builder, keyguardStateController, statusBarStateController, keyguardUpdateMonitor, dockManager, broadcastDispatcher, devicePolicyManager, iBatteryStats, userManager, delayableExecutor, falsingManager, lockPatternUtils, iActivityManager, keyguardBypassController);
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mTunerService = tunerService;
        this.mDeviceConfig = deviceConfigProxy;
        this.mAdaptiveChargingManager = new AdaptiveChargingManager(context);
        this.mBatteryInfo = iBatteryStats;
        this.mDateFormatUtil = new DateFormatUtil(context);
    }

    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public void init() {
        super.init();
        if (!this.mInited) {
            this.mInited = true;
            this.mTunerService.addTunable(new KeyguardIndicationControllerGoogle$$ExternalSyntheticLambda1(this), "adaptive_charging_enabled");
            this.mDeviceConfig.addOnPropertiesChangedListener("adaptive_charging", this.mContext.getMainExecutor(), new KeyguardIndicationControllerGoogle$$ExternalSyntheticLambda0(this));
            triggerAdaptiveChargingStatusUpdate();
            this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.google.android.systemui.adaptivecharging.ADAPTIVE_CHARGING_DEADLINE_SET"), null, UserHandle.ALL);
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(String str, String str2) {
        refreshAdaptiveChargingEnabled();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(DeviceConfig.Properties properties) {
        if (properties.getKeyset().contains("adaptive_charging_enabled")) {
            triggerAdaptiveChargingStatusUpdate();
        }
    }

    private void refreshAdaptiveChargingEnabled() {
        this.mAdaptiveChargingEnabledInSettings = this.mAdaptiveChargingManager.isAvailable() && this.mAdaptiveChargingManager.isEnabled();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public String computePowerIndication() {
        if (!this.mIsCharging || !this.mAdaptiveChargingEnabledInSettings || !this.mAdaptiveChargingActive) {
            return super.computePowerIndication();
        }
        String charSequence = DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), this.mDateFormatUtil.is24HourFormat() ? "Hm" : "hma"), this.mEstimatedChargeCompletion).toString();
        return this.mContext.getResources().getString(R$string.adaptive_charging_time_estimate, NumberFormat.getPercentInstance().format((double) (((float) this.mBatteryLevel) / 100.0f)), charSequence);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.statusbar.KeyguardIndicationController
    public KeyguardUpdateMonitorCallback getKeyguardCallback() {
        if (this.mUpdateMonitorCallback == null) {
            this.mUpdateMonitorCallback = new GoogleKeyguardCallback();
        }
        return this.mUpdateMonitorCallback;
    }

    public void setReverseChargingMessage(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mRotateTextViewController.hideIndication(10);
        } else {
            this.mRotateTextViewController.updateIndication(10, new KeyguardIndication.Builder().setMessage(charSequence).setIcon(this.mContext.getDrawable(R$anim.reverse_charging_animation)).setTextColor(this.mInitialTextColorState).build(), false);
        }
    }

    public void setStatusBar(StatusBar statusBar) {
        this.mStatusBar = statusBar;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void triggerAdaptiveChargingStatusUpdate() {
        refreshAdaptiveChargingEnabled();
        if (this.mAdaptiveChargingEnabledInSettings) {
            this.mAdaptiveChargingManager.queryStatus(this.mAdaptiveChargingStatusReceiver);
        } else {
            this.mAdaptiveChargingActive = false;
        }
    }

    protected class GoogleKeyguardCallback extends KeyguardIndicationController.BaseKeyguardCallback {
        protected GoogleKeyguardCallback() {
            super();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback, com.android.systemui.statusbar.KeyguardIndicationController.BaseKeyguardCallback
        public void onRefreshBatteryInfo(BatteryStatus batteryStatus) {
            KeyguardIndicationControllerGoogle.this.mIsCharging = batteryStatus.status == 2;
            KeyguardIndicationControllerGoogle.this.mBatteryLevel = batteryStatus.level;
            super.onRefreshBatteryInfo(batteryStatus);
            if (KeyguardIndicationControllerGoogle.this.mIsCharging) {
                KeyguardIndicationControllerGoogle.this.triggerAdaptiveChargingStatusUpdate();
            } else {
                KeyguardIndicationControllerGoogle.this.mAdaptiveChargingActive = false;
            }
        }
    }
}

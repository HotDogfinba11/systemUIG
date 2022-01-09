package com.android.keyguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.icu.text.NumberFormat;
import com.android.settingslib.Utils;
import com.android.systemui.R$attr;
import com.android.systemui.R$dimen;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.ViewController;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class AnimatableClockController extends ViewController<AnimatableClockView> {
    private final BatteryController.BatteryStateChangeCallback mBatteryCallback;
    private final BatteryController mBatteryController;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private final float mBurmeseLineSpacing;
    private final NumberFormat mBurmeseNf;
    private final String mBurmeseNumerals;
    private final KeyguardBypassController mBypassController;
    private final float mDefaultLineSpacing;
    private float mDozeAmount;
    private final int mDozingColor = -1;
    private boolean mIsCharging;
    private boolean mIsDozing;
    boolean mKeyguardShowing;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    private Locale mLocale;
    private final BroadcastReceiver mLocaleBroadcastReceiver;
    private int mLockScreenColor;
    private final StatusBarStateController mStatusBarStateController;
    private final StatusBarStateController.StateListener mStatusBarStateListener;

    public AnimatableClockController(AnimatableClockView animatableClockView, StatusBarStateController statusBarStateController, BroadcastDispatcher broadcastDispatcher, BatteryController batteryController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardBypassController keyguardBypassController, Resources resources) {
        super(animatableClockView);
        NumberFormat instance = NumberFormat.getInstance(Locale.forLanguageTag("my"));
        this.mBurmeseNf = instance;
        this.mBatteryCallback = new BatteryController.BatteryStateChangeCallback() {
            /* class com.android.keyguard.AnimatableClockController.AnonymousClass1 */

            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public void onBatteryLevelChanged(int i, boolean z, boolean z2) {
                AnimatableClockController animatableClockController = AnimatableClockController.this;
                if (animatableClockController.mKeyguardShowing && !animatableClockController.mIsCharging && z2) {
                    StatusBarStateController statusBarStateController = AnimatableClockController.this.mStatusBarStateController;
                    Objects.requireNonNull(statusBarStateController);
                    ((AnimatableClockView) ((ViewController) AnimatableClockController.this).mView).animateCharge(new AnimatableClockController$1$$ExternalSyntheticLambda0(statusBarStateController));
                }
                AnimatableClockController.this.mIsCharging = z2;
            }
        };
        this.mLocaleBroadcastReceiver = new BroadcastReceiver() {
            /* class com.android.keyguard.AnimatableClockController.AnonymousClass2 */

            public void onReceive(Context context, Intent intent) {
                AnimatableClockController.this.updateLocale();
            }
        };
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() {
            /* class com.android.keyguard.AnimatableClockController.AnonymousClass3 */

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozeAmountChanged(float f, float f2) {
                boolean z = false;
                boolean z2 = (AnimatableClockController.this.mDozeAmount == 0.0f && f == 1.0f) || (AnimatableClockController.this.mDozeAmount == 1.0f && f == 0.0f);
                if (f > AnimatableClockController.this.mDozeAmount) {
                    z = true;
                }
                AnimatableClockController.this.mDozeAmount = f;
                if (AnimatableClockController.this.mIsDozing != z) {
                    AnimatableClockController.this.mIsDozing = z;
                    ((AnimatableClockView) ((ViewController) AnimatableClockController.this).mView).animateDoze(AnimatableClockController.this.mIsDozing, !z2);
                }
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
            /* class com.android.keyguard.AnimatableClockController.AnonymousClass4 */

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onKeyguardVisibilityChanged(boolean z) {
                AnimatableClockController animatableClockController = AnimatableClockController.this;
                animatableClockController.mKeyguardShowing = z;
                if (!z) {
                    animatableClockController.reset();
                }
            }
        };
        this.mStatusBarStateController = statusBarStateController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mBypassController = keyguardBypassController;
        this.mBatteryController = batteryController;
        this.mBurmeseNumerals = instance.format(1234567890L);
        this.mBurmeseLineSpacing = resources.getFloat(R$dimen.keyguard_clock_line_spacing_scale_burmese);
        this.mDefaultLineSpacing = resources.getFloat(R$dimen.keyguard_clock_line_spacing_scale);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void reset() {
        ((AnimatableClockView) this.mView).animateDoze(this.mIsDozing, false);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        this.mIsDozing = this.mStatusBarStateController.isDozing();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        updateLocale();
        this.mBroadcastDispatcher.registerReceiver(this.mLocaleBroadcastReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        this.mDozeAmount = this.mStatusBarStateController.getDozeAmount();
        this.mIsDozing = this.mStatusBarStateController.isDozing() || this.mDozeAmount != 0.0f;
        this.mBatteryController.addCallback(this.mBatteryCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        refreshTime();
        initColors();
        ((AnimatableClockView) this.mView).animateDoze(this.mIsDozing, false);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mLocaleBroadcastReceiver);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        this.mBatteryController.removeCallback(this.mBatteryCallback);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
    }

    public void animateAppear() {
        if (!this.mIsDozing) {
            ((AnimatableClockView) this.mView).animateAppearOnLockscreen();
        }
    }

    public void refreshTime() {
        ((AnimatableClockView) this.mView).refreshTime();
    }

    public void onTimeZoneChanged(TimeZone timeZone) {
        ((AnimatableClockView) this.mView).onTimeZoneChanged(timeZone);
    }

    public void refreshFormat() {
        ((AnimatableClockView) this.mView).refreshFormat();
    }

    public boolean isDozing() {
        return this.mIsDozing;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateLocale() {
        Locale locale = Locale.getDefault();
        if (!Objects.equals(locale, this.mLocale)) {
            this.mLocale = locale;
            if (NumberFormat.getInstance(locale).format(1234567890L).equals(this.mBurmeseNumerals)) {
                ((AnimatableClockView) this.mView).setLineSpacingScale(this.mBurmeseLineSpacing);
            } else {
                ((AnimatableClockView) this.mView).setLineSpacingScale(this.mDefaultLineSpacing);
            }
            ((AnimatableClockView) this.mView).refreshFormat();
        }
    }

    private void initColors() {
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(getContext(), R$attr.wallpaperTextColorAccent);
        this.mLockScreenColor = colorAttrDefaultColor;
        ((AnimatableClockView) this.mView).setColors(-1, colorAttrDefaultColor);
        ((AnimatableClockView) this.mView).animateDoze(this.mIsDozing, false);
    }
}

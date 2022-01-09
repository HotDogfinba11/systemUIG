package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.R$integer;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.LightRevealEffect;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Provider;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: AuthRippleController.kt */
public final class AuthRippleController extends ViewController<AuthRippleView> implements KeyguardStateController.Callback, WakefulnessLifecycle.Observer {
    public static final Companion Companion = new Companion(null);
    private float aodDwellScale = 1.9f;
    private float aodExpandedDwellScale = 2.3f;
    private final AuthController authController;
    private final AuthController.Callback authControllerCallback = new AuthRippleController$authControllerCallback$1(this);
    private final BiometricUnlockController biometricUnlockController;
    private final KeyguardBypassController bypassController;
    private LightRevealEffect circleReveal;
    private final CommandRegistry commandRegistry;
    private final AuthRippleController$configurationChangedListener$1 configurationChangedListener = new AuthRippleController$configurationChangedListener$1(this);
    private final ConfigurationController configurationController;
    private float dwellScale = 2.0f;
    private float expandedDwellScale = 2.5f;
    private PointF faceSensorLocation;
    private PointF fingerprintSensorLocation;
    private final KeyguardStateController keyguardStateController;
    private final KeyguardUpdateMonitor keyguardUpdateMonitor;
    private final AuthRippleController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback = new AuthRippleController$keyguardUpdateMonitorCallback$1(this);
    private final NotificationShadeWindowController notificationShadeWindowController;
    private boolean startLightRevealScrimOnKeyguardFadingAway;
    private final StatusBar statusBar;
    private final StatusBarStateController statusBarStateController;
    private final Context sysuiContext;
    private UdfpsController udfpsController;
    private final AuthRippleController$udfpsControllerCallback$1 udfpsControllerCallback = new AuthRippleController$udfpsControllerCallback$1(this);
    private final Provider<UdfpsController> udfpsControllerProvider;
    private float udfpsRadius = -1.0f;
    private final WakefulnessLifecycle wakefulnessLifecycle;

    public static /* synthetic */ void getStartLightRevealScrimOnKeyguardFadingAway$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AuthRippleController(StatusBar statusBar2, Context context, AuthController authController2, ConfigurationController configurationController2, KeyguardUpdateMonitor keyguardUpdateMonitor2, KeyguardStateController keyguardStateController2, WakefulnessLifecycle wakefulnessLifecycle2, CommandRegistry commandRegistry2, NotificationShadeWindowController notificationShadeWindowController2, KeyguardBypassController keyguardBypassController, BiometricUnlockController biometricUnlockController2, Provider<UdfpsController> provider, StatusBarStateController statusBarStateController2, AuthRippleView authRippleView) {
        super(authRippleView);
        Intrinsics.checkNotNullParameter(statusBar2, "statusBar");
        Intrinsics.checkNotNullParameter(context, "sysuiContext");
        Intrinsics.checkNotNullParameter(authController2, "authController");
        Intrinsics.checkNotNullParameter(configurationController2, "configurationController");
        Intrinsics.checkNotNullParameter(keyguardUpdateMonitor2, "keyguardUpdateMonitor");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        Intrinsics.checkNotNullParameter(wakefulnessLifecycle2, "wakefulnessLifecycle");
        Intrinsics.checkNotNullParameter(commandRegistry2, "commandRegistry");
        Intrinsics.checkNotNullParameter(notificationShadeWindowController2, "notificationShadeWindowController");
        Intrinsics.checkNotNullParameter(keyguardBypassController, "bypassController");
        Intrinsics.checkNotNullParameter(biometricUnlockController2, "biometricUnlockController");
        Intrinsics.checkNotNullParameter(provider, "udfpsControllerProvider");
        Intrinsics.checkNotNullParameter(statusBarStateController2, "statusBarStateController");
        this.statusBar = statusBar2;
        this.sysuiContext = context;
        this.authController = authController2;
        this.configurationController = configurationController2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor2;
        this.keyguardStateController = keyguardStateController2;
        this.wakefulnessLifecycle = wakefulnessLifecycle2;
        this.commandRegistry = commandRegistry2;
        this.notificationShadeWindowController = notificationShadeWindowController2;
        this.bypassController = keyguardBypassController;
        this.biometricUnlockController = biometricUnlockController2;
        this.udfpsControllerProvider = provider;
        this.statusBarStateController = statusBarStateController2;
    }

    public final PointF getFingerprintSensorLocation() {
        return this.fingerprintSensorLocation;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        ((AuthRippleView) this.mView).setAlphaInDuration((long) this.sysuiContext.getResources().getInteger(R$integer.auth_ripple_alpha_in_duration));
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        this.authController.addCallback(this.authControllerCallback);
        updateRippleColor();
        updateSensorLocation();
        updateUdfpsDependentParams();
        UdfpsController udfpsController2 = this.udfpsController;
        if (udfpsController2 != null) {
            udfpsController2.addCallback(this.udfpsControllerCallback);
        }
        this.configurationController.addCallback(this.configurationChangedListener);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        this.keyguardStateController.addCallback(this);
        this.wakefulnessLifecycle.addObserver(this);
        this.commandRegistry.registerCommand("auth-ripple", new AuthRippleController$onViewAttached$1(this));
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        UdfpsController udfpsController2 = this.udfpsController;
        if (udfpsController2 != null) {
            udfpsController2.removeCallback(this.udfpsControllerCallback);
        }
        this.authController.removeCallback(this.authControllerCallback);
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
        this.configurationController.removeCallback(this.configurationChangedListener);
        this.keyguardStateController.removeCallback(this);
        this.wakefulnessLifecycle.removeObserver(this);
        this.commandRegistry.unregisterCommand("auth-ripple");
        this.notificationShadeWindowController.setForcePluginOpen(false, this);
    }

    public final void showRipple(BiometricSourceType biometricSourceType) {
        PointF pointF;
        if (this.keyguardUpdateMonitor.isKeyguardVisible() && !this.keyguardUpdateMonitor.userNeedsStrongAuth()) {
            if (biometricSourceType == BiometricSourceType.FINGERPRINT && (pointF = this.fingerprintSensorLocation) != null) {
                Intrinsics.checkNotNull(pointF);
                ((AuthRippleView) this.mView).setSensorLocation(pointF);
                showUnlockedRipple();
            } else if (biometricSourceType == BiometricSourceType.FACE && this.faceSensorLocation != null && this.bypassController.canBypass()) {
                PointF pointF2 = this.faceSensorLocation;
                Intrinsics.checkNotNull(pointF2);
                ((AuthRippleView) this.mView).setSensorLocation(pointF2);
                showUnlockedRipple();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void showUnlockedRipple() {
        this.notificationShadeWindowController.setForcePluginOpen(true, this);
        boolean z = this.circleReveal != null && this.biometricUnlockController.isWakeAndUnlock();
        LightRevealScrim lightRevealScrim = this.statusBar.getLightRevealScrim();
        if (z) {
            if (lightRevealScrim != null) {
                LightRevealEffect lightRevealEffect = this.circleReveal;
                Intrinsics.checkNotNull(lightRevealEffect);
                lightRevealScrim.setRevealEffect(lightRevealEffect);
            }
            this.startLightRevealScrimOnKeyguardFadingAway = true;
        }
        ((AuthRippleView) this.mView).startUnlockedRipple(new AuthRippleController$showUnlockedRipple$1(this));
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public void onKeyguardFadingAwayChanged() {
        if (this.keyguardStateController.isKeyguardFadingAway()) {
            LightRevealScrim lightRevealScrim = this.statusBar.getLightRevealScrim();
            if (this.startLightRevealScrimOnKeyguardFadingAway && lightRevealScrim != null) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.1f, 1.0f);
                ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                ofFloat.setDuration(1533L);
                ofFloat.setStartDelay(this.keyguardStateController.getKeyguardFadingAwayDelay());
                ofFloat.addUpdateListener(new AuthRippleController$onKeyguardFadingAwayChanged$revealAnimator$1$1(lightRevealScrim, this));
                ofFloat.start();
                this.startLightRevealScrimOnKeyguardFadingAway = false;
            }
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedGoingToSleep() {
        this.startLightRevealScrimOnKeyguardFadingAway = false;
    }

    public final void updateSensorLocation() {
        this.fingerprintSensorLocation = this.authController.getFingerprintSensorLocation();
        this.faceSensorLocation = this.authController.getFaceAuthSensorLocation();
        PointF pointF = this.fingerprintSensorLocation;
        if (pointF != null) {
            float f = pointF.x;
            this.circleReveal = new CircleReveal(f, pointF.y, 0.0f, Math.max(Math.max(f, this.statusBar.getDisplayWidth() - pointF.x), Math.max(pointF.y, this.statusBar.getDisplayHeight() - pointF.y)));
        }
    }

    /* access modifiers changed from: private */
    public final void updateRippleColor() {
        ((AuthRippleView) this.mView).setColor(Utils.getColorAttr(this.sysuiContext, 16843829).getDefaultColor());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void showDwellRipple() {
        if (this.statusBarStateController.isDozing()) {
            float f = this.udfpsRadius;
            ((AuthRippleView) this.mView).startDwellRipple(f, this.aodDwellScale * f, this.aodExpandedDwellScale * f, true);
            return;
        }
        float f2 = this.udfpsRadius;
        ((AuthRippleView) this.mView).startDwellRipple(f2, this.dwellScale * f2, this.expandedDwellScale * f2, false);
    }

    /* access modifiers changed from: private */
    public final void updateUdfpsDependentParams() {
        UdfpsController udfpsController2;
        List<FingerprintSensorPropertiesInternal> udfpsProps = this.authController.getUdfpsProps();
        if (udfpsProps != null && udfpsProps.size() > 0) {
            this.udfpsRadius = (float) udfpsProps.get(0).sensorRadius;
            this.udfpsController = this.udfpsControllerProvider.get();
            if (((AuthRippleView) this.mView).isAttachedToWindow() && (udfpsController2 = this.udfpsController) != null) {
                udfpsController2.addCallback(this.udfpsControllerCallback);
            }
        }
    }

    /* compiled from: AuthRippleController.kt */
    public final class AuthRippleCommand implements Command {
        final /* synthetic */ AuthRippleController this$0;

        /* JADX WARN: Incorrect args count in method signature: ()V */
        public AuthRippleCommand(AuthRippleController authRippleController) {
            Intrinsics.checkNotNullParameter(authRippleController, "this$0");
            this.this$0 = authRippleController;
        }

        public final void printLockScreenDwellInfo(PrintWriter printWriter) {
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            printWriter.println("lock screen dwell ripple: \n\tsensorLocation=" + this.this$0.getFingerprintSensorLocation() + "\n\tdwellScale=" + this.this$0.dwellScale + "\n\tdwellExpand=" + this.this$0.expandedDwellScale);
        }

        public final void printAodDwellInfo(PrintWriter printWriter) {
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            printWriter.println("aod dwell ripple: \n\tsensorLocation=" + this.this$0.getFingerprintSensorLocation() + "\n\tdwellScale=" + this.this$0.aodDwellScale + "\n\tdwellExpand=" + this.this$0.aodExpandedDwellScale);
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public void execute(PrintWriter printWriter, List<String> list) {
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            Intrinsics.checkNotNullParameter(list, "args");
            if (list.isEmpty()) {
                invalidCommand(printWriter);
                return;
            }
            String str = list.get(0);
            switch (str.hashCode()) {
                case -1375934236:
                    if (str.equals("fingerprint")) {
                        printWriter.println(Intrinsics.stringPlus("fingerprint ripple sensorLocation=", this.this$0.getFingerprintSensorLocation()));
                        this.this$0.showRipple(BiometricSourceType.FINGERPRINT);
                        return;
                    }
                    break;
                case -1349088399:
                    if (str.equals("custom")) {
                        if (list.size() != 3 || StringsKt.toFloatOrNull(list.get(1)) == null || StringsKt.toFloatOrNull(list.get(2)) == null) {
                            invalidCommand(printWriter);
                            return;
                        }
                        printWriter.println("custom ripple sensorLocation=" + Float.parseFloat(list.get(1)) + ", " + Float.parseFloat(list.get(2)));
                        ((AuthRippleView) ((ViewController) this.this$0).mView).setSensorLocation(new PointF(Float.parseFloat(list.get(1)), Float.parseFloat(list.get(2))));
                        this.this$0.showUnlockedRipple();
                        return;
                    }
                    break;
                case 3135069:
                    if (str.equals("face")) {
                        printWriter.println(Intrinsics.stringPlus("face ripple sensorLocation=", this.this$0.faceSensorLocation));
                        this.this$0.showRipple(BiometricSourceType.FACE);
                        return;
                    }
                    break;
                case 95997746:
                    if (str.equals("dwell")) {
                        this.this$0.showDwellRipple();
                        if (this.this$0.statusBarStateController.isDozing()) {
                            printAodDwellInfo(printWriter);
                            return;
                        } else {
                            printLockScreenDwellInfo(printWriter);
                            return;
                        }
                    }
                    break;
            }
            invalidCommand(printWriter);
        }

        public void help(PrintWriter printWriter) {
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            printWriter.println("Usage: adb shell cmd statusbar auth-ripple <command>");
            printWriter.println("Available commands:");
            printWriter.println("  dwell");
            printWriter.println("  fingerprint");
            printWriter.println("  face");
            printWriter.println("  custom <x-location: int> <y-location: int>");
        }

        public final void invalidCommand(PrintWriter printWriter) {
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            printWriter.println("invalid command");
            help(printWriter);
        }
    }

    /* compiled from: AuthRippleController.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

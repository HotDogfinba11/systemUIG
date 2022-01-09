package com.android.systemui.statusbar;

import android.animation.Animator;
import android.app.WallpaperManager;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.PanelExpansionListener;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController implements PanelExpansionListener, Dumpable {
    public static final Companion Companion = new Companion(null);
    private final BiometricUnlockController biometricUnlockController;
    private View blurRoot;
    private final BlurUtils blurUtils;
    private boolean blursDisabledForAppLaunch;
    private DepthAnimation brightnessMirrorSpring = new DepthAnimation(this);
    private boolean brightnessMirrorVisible;
    private final Choreographer choreographer;
    private final DozeParameters dozeParameters;
    private boolean isBlurred;
    private boolean isClosed = true;
    private boolean isOpen;
    private Animator keyguardAnimator;
    private final NotificationShadeDepthController$keyguardStateCallback$1 keyguardStateCallback;
    private final KeyguardStateController keyguardStateController;
    private int lastAppliedBlur;
    private List<DepthListener> listeners = new ArrayList();
    private Animator notificationAnimator;
    private final NotificationShadeWindowController notificationShadeWindowController;
    private float panelPullDownMinFraction;
    private int prevShadeDirection;
    private float prevShadeVelocity;
    private long prevTimestamp = -1;
    private boolean prevTracking;
    private float qsPanelExpansion;
    public View root;
    private boolean scrimsVisible;
    private DepthAnimation shadeAnimation = new DepthAnimation(this);
    private float shadeExpansion;
    private final NotificationShadeDepthController$statusBarStateCallback$1 statusBarStateCallback;
    private final StatusBarStateController statusBarStateController;
    private float transitionToFullShadeProgress;
    private final Choreographer.FrameCallback updateBlurCallback = new NotificationShadeDepthController$updateBlurCallback$1(this);
    private boolean updateScheduled;
    private float wakeAndUnlockBlurRadius;
    private final WallpaperManager wallpaperManager;

    /* compiled from: NotificationShadeDepthController.kt */
    public interface DepthListener {
        default void onBlurRadiusChanged(int i) {
        }

        void onWallpaperZoomOutChanged(float f);
    }

    public static /* synthetic */ void getBrightnessMirrorSpring$annotations() {
    }

    public static /* synthetic */ void getShadeExpansion$annotations() {
    }

    public static /* synthetic */ void getUpdateBlurCallback$annotations() {
    }

    public NotificationShadeDepthController(StatusBarStateController statusBarStateController2, BlurUtils blurUtils2, BiometricUnlockController biometricUnlockController2, KeyguardStateController keyguardStateController2, Choreographer choreographer2, WallpaperManager wallpaperManager2, NotificationShadeWindowController notificationShadeWindowController2, DozeParameters dozeParameters2, DumpManager dumpManager) {
        Intrinsics.checkNotNullParameter(statusBarStateController2, "statusBarStateController");
        Intrinsics.checkNotNullParameter(blurUtils2, "blurUtils");
        Intrinsics.checkNotNullParameter(biometricUnlockController2, "biometricUnlockController");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        Intrinsics.checkNotNullParameter(choreographer2, "choreographer");
        Intrinsics.checkNotNullParameter(wallpaperManager2, "wallpaperManager");
        Intrinsics.checkNotNullParameter(notificationShadeWindowController2, "notificationShadeWindowController");
        Intrinsics.checkNotNullParameter(dozeParameters2, "dozeParameters");
        Intrinsics.checkNotNullParameter(dumpManager, "dumpManager");
        this.statusBarStateController = statusBarStateController2;
        this.blurUtils = blurUtils2;
        this.biometricUnlockController = biometricUnlockController2;
        this.keyguardStateController = keyguardStateController2;
        this.choreographer = choreographer2;
        this.wallpaperManager = wallpaperManager2;
        this.notificationShadeWindowController = notificationShadeWindowController2;
        this.dozeParameters = dozeParameters2;
        NotificationShadeDepthController$keyguardStateCallback$1 notificationShadeDepthController$keyguardStateCallback$1 = new NotificationShadeDepthController$keyguardStateCallback$1(this);
        this.keyguardStateCallback = notificationShadeDepthController$keyguardStateCallback$1;
        NotificationShadeDepthController$statusBarStateCallback$1 notificationShadeDepthController$statusBarStateCallback$1 = new NotificationShadeDepthController$statusBarStateCallback$1(this);
        this.statusBarStateCallback = notificationShadeDepthController$statusBarStateCallback$1;
        String name = NotificationShadeDepthController.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "javaClass.name");
        dumpManager.registerDumpable(name, this);
        keyguardStateController2.addCallback(notificationShadeDepthController$keyguardStateCallback$1);
        statusBarStateController2.addCallback(notificationShadeDepthController$statusBarStateCallback$1);
        notificationShadeWindowController2.setScrimsVisibilityListener(new Consumer<Integer>(this) {
            /* class com.android.systemui.statusbar.NotificationShadeDepthController.AnonymousClass1 */
            final /* synthetic */ NotificationShadeDepthController this$0;

            {
                this.this$0 = r1;
            }

            public final void accept(Integer num) {
                this.this$0.setScrimsVisible(num != null && num.intValue() == 2);
            }
        });
        this.shadeAnimation.setStiffness(200.0f);
        this.shadeAnimation.setDampingRatio(1.0f);
    }

    /* compiled from: NotificationShadeDepthController.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final View getRoot() {
        View view = this.root;
        if (view != null) {
            return view;
        }
        Intrinsics.throwUninitializedPropertyAccessException("root");
        throw null;
    }

    public final void setRoot(View view) {
        Intrinsics.checkNotNullParameter(view, "<set-?>");
        this.root = view;
    }

    public final float getShadeExpansion() {
        return this.shadeExpansion;
    }

    public final void setPanelPullDownMinFraction(float f) {
        this.panelPullDownMinFraction = f;
    }

    public final DepthAnimation getShadeAnimation() {
        return this.shadeAnimation;
    }

    public final DepthAnimation getBrightnessMirrorSpring() {
        return this.brightnessMirrorSpring;
    }

    public final void setBrightnessMirrorVisible(boolean z) {
        this.brightnessMirrorVisible = z;
        DepthAnimation.animateTo$default(this.brightnessMirrorSpring, z ? (int) this.blurUtils.blurRadiusOfRatio(1.0f) : 0, null, 2, null);
    }

    public final float getQsPanelExpansion() {
        return this.qsPanelExpansion;
    }

    public final void setQsPanelExpansion(float f) {
        if (Float.isNaN(f)) {
            Log.w("DepthController", "Invalid qs expansion");
            return;
        }
        if (!(this.qsPanelExpansion == f)) {
            this.qsPanelExpansion = f;
            scheduleUpdate$default(this, null, 1, null);
        }
    }

    public final float getTransitionToFullShadeProgress() {
        return this.transitionToFullShadeProgress;
    }

    public final void setTransitionToFullShadeProgress(float f) {
        if (!(this.transitionToFullShadeProgress == f)) {
            this.transitionToFullShadeProgress = f;
            scheduleUpdate$default(this, null, 1, null);
        }
    }

    public final boolean getBlursDisabledForAppLaunch() {
        return this.blursDisabledForAppLaunch;
    }

    public final void setBlursDisabledForAppLaunch(boolean z) {
        if (this.blursDisabledForAppLaunch != z) {
            this.blursDisabledForAppLaunch = z;
            boolean z2 = true;
            scheduleUpdate$default(this, null, 1, null);
            if (this.shadeExpansion == 0.0f) {
                if (this.shadeAnimation.getRadius() != 0.0f) {
                    z2 = false;
                }
                if (z2) {
                    return;
                }
            }
            if (z) {
                DepthAnimation.animateTo$default(this.shadeAnimation, 0, null, 2, null);
                this.shadeAnimation.finishIfRunning();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void setScrimsVisible(boolean z) {
        if (this.scrimsVisible != z) {
            this.scrimsVisible = z;
            scheduleUpdate$default(this, null, 1, null);
        }
    }

    /* access modifiers changed from: private */
    public final void setWakeAndUnlockBlurRadius(float f) {
        if (!(this.wakeAndUnlockBlurRadius == f)) {
            this.wakeAndUnlockBlurRadius = f;
            scheduleUpdate$default(this, null, 1, null);
        }
    }

    public final void addListener(DepthListener depthListener) {
        Intrinsics.checkNotNullParameter(depthListener, "listener");
        this.listeners.add(depthListener);
    }

    public final void removeListener(DepthListener depthListener) {
        Intrinsics.checkNotNullParameter(depthListener, "listener");
        this.listeners.remove(depthListener);
    }

    @Override // com.android.systemui.statusbar.phone.PanelExpansionListener
    public void onPanelExpansionChanged(float f, boolean z) {
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        float f2 = this.panelPullDownMinFraction;
        float f3 = 1.0f;
        float saturate = MathUtils.saturate((f - f2) / (1.0f - f2));
        if (!(this.shadeExpansion == saturate) || this.prevTracking != z) {
            long j = this.prevTimestamp;
            if (j < 0) {
                this.prevTimestamp = elapsedRealtimeNanos;
            } else {
                f3 = MathUtils.constrain((float) (((double) (elapsedRealtimeNanos - j)) / 1.0E9d), 1.0E-5f, 1.0f);
            }
            float f4 = saturate - this.shadeExpansion;
            int signum = (int) Math.signum(f4);
            float constrain = MathUtils.constrain((f4 * 100.0f) / f3, -3000.0f, 3000.0f);
            updateShadeAnimationBlur(saturate, z, constrain, signum);
            this.prevShadeDirection = signum;
            this.prevShadeVelocity = constrain;
            this.shadeExpansion = saturate;
            this.prevTracking = z;
            this.prevTimestamp = elapsedRealtimeNanos;
            scheduleUpdate$default(this, null, 1, null);
            return;
        }
        this.prevTimestamp = elapsedRealtimeNanos;
    }

    /* access modifiers changed from: private */
    public final void updateShadeAnimationBlur(float f, boolean z, float f2, int i) {
        if (!shouldApplyShadeBlur()) {
            animateBlur(false, 0.0f);
            this.isClosed = true;
            this.isOpen = false;
        } else if (f > 0.0f) {
            if (this.isClosed) {
                animateBlur(true, f2);
                this.isClosed = false;
            }
            if (z && !this.isBlurred) {
                animateBlur(true, 0.0f);
            }
            if (!z && i < 0 && this.isBlurred) {
                animateBlur(false, f2);
            }
            if (!(f == 1.0f)) {
                this.isOpen = false;
            } else if (!this.isOpen) {
                this.isOpen = true;
                if (!this.isBlurred) {
                    animateBlur(true, f2);
                }
            }
        } else if (!this.isClosed) {
            this.isClosed = true;
            if (this.isBlurred) {
                animateBlur(false, f2);
            }
        }
    }

    private final void animateBlur(boolean z, float f) {
        this.isBlurred = z;
        float f2 = (!z || !shouldApplyShadeBlur()) ? 0.0f : 1.0f;
        this.shadeAnimation.setStartVelocity(f);
        DepthAnimation.animateTo$default(this.shadeAnimation, (int) this.blurUtils.blurRadiusOfRatio(f2), null, 2, null);
    }

    static /* synthetic */ void scheduleUpdate$default(NotificationShadeDepthController notificationShadeDepthController, View view, int i, Object obj) {
        if ((i & 1) != 0) {
            view = null;
        }
        notificationShadeDepthController.scheduleUpdate(view);
    }

    /* access modifiers changed from: private */
    public final void scheduleUpdate(View view) {
        if (!this.updateScheduled) {
            this.updateScheduled = true;
            this.blurRoot = view;
            this.choreographer.postFrameCallback(this.updateBlurCallback);
        }
    }

    /* access modifiers changed from: private */
    public final boolean shouldApplyShadeBlur() {
        int state = this.statusBarStateController.getState();
        return (state == 0 || state == 2) && !this.keyguardStateController.isKeyguardFadingAway();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("StatusBarWindowBlurController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(Intrinsics.stringPlus("shadeExpansion: ", Float.valueOf(getShadeExpansion())));
        indentingPrintWriter.println(Intrinsics.stringPlus("shouldApplyShaeBlur: ", Boolean.valueOf(shouldApplyShadeBlur())));
        indentingPrintWriter.println(Intrinsics.stringPlus("shadeAnimation: ", Float.valueOf(getShadeAnimation().getRadius())));
        indentingPrintWriter.println(Intrinsics.stringPlus("brightnessMirrorRadius: ", Float.valueOf(getBrightnessMirrorSpring().getRadius())));
        indentingPrintWriter.println(Intrinsics.stringPlus("wakeAndUnlockBlur: ", Float.valueOf(this.wakeAndUnlockBlurRadius)));
        indentingPrintWriter.println(Intrinsics.stringPlus("blursDisabledForAppLaunch: ", Boolean.valueOf(getBlursDisabledForAppLaunch())));
        indentingPrintWriter.println(Intrinsics.stringPlus("qsPanelExpansion: ", Float.valueOf(getQsPanelExpansion())));
        indentingPrintWriter.println(Intrinsics.stringPlus("transitionToFullShadeProgress: ", Float.valueOf(getTransitionToFullShadeProgress())));
        indentingPrintWriter.println(Intrinsics.stringPlus("lastAppliedBlur: ", Integer.valueOf(this.lastAppliedBlur)));
    }

    /* compiled from: NotificationShadeDepthController.kt */
    public final class DepthAnimation {
        private int pendingRadius = -1;
        private float radius;
        private SpringAnimation springAnimation;
        final /* synthetic */ NotificationShadeDepthController this$0;
        private View view;

        /* JADX WARN: Incorrect args count in method signature: ()V */
        public DepthAnimation(NotificationShadeDepthController notificationShadeDepthController) {
            Intrinsics.checkNotNullParameter(notificationShadeDepthController, "this$0");
            this.this$0 = notificationShadeDepthController;
            SpringAnimation springAnimation2 = new SpringAnimation(this, new NotificationShadeDepthController$DepthAnimation$springAnimation$1(this, notificationShadeDepthController));
            this.springAnimation = springAnimation2;
            springAnimation2.setSpring(new SpringForce(0.0f));
            this.springAnimation.getSpring().setDampingRatio(1.0f);
            this.springAnimation.getSpring().setStiffness(10000.0f);
            this.springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener(this) {
                /* class com.android.systemui.statusbar.NotificationShadeDepthController.DepthAnimation.AnonymousClass1 */
                final /* synthetic */ DepthAnimation this$0;

                {
                    this.this$0 = r1;
                }

                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    this.this$0.pendingRadius = -1;
                }
            });
        }

        public final float getRadius() {
            return this.radius;
        }

        public final void setRadius(float f) {
            this.radius = f;
        }

        public final float getRatio() {
            return this.this$0.blurUtils.ratioOfBlurRadius(this.radius);
        }

        public static /* synthetic */ void animateTo$default(DepthAnimation depthAnimation, int i, View view2, int i2, Object obj) {
            if ((i2 & 2) != 0) {
                view2 = null;
            }
            depthAnimation.animateTo(i, view2);
        }

        public final void animateTo(int i, View view2) {
            if (this.pendingRadius != i || !Intrinsics.areEqual(this.view, view2)) {
                this.view = view2;
                this.pendingRadius = i;
                this.springAnimation.animateToFinalPosition((float) i);
            }
        }

        public final void finishIfRunning() {
            if (this.springAnimation.isRunning()) {
                this.springAnimation.skipToEnd();
            }
        }

        public final void setStiffness(float f) {
            this.springAnimation.getSpring().setStiffness(f);
        }

        public final void setDampingRatio(float f) {
            this.springAnimation.getSpring().setDampingRatio(f);
        }

        public final void setStartVelocity(float f) {
            this.springAnimation.setStartVelocity(f);
        }
    }
}

package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController implements WakefulnessLifecycle.Observer {
    private float animatorDurationScale = 1.0f;
    private final ContentObserver animatorDurationScaleObserver;
    private boolean aodUiAnimationPlaying;
    private HashSet<Callback> callbacks = new HashSet<>();
    private final Context context;
    private Boolean decidedToAnimateGoingToSleep;
    private final Lazy<DozeParameters> dozeParameters;
    private final GlobalSettings globalSettings;
    private final Handler handler = new Handler();
    private final KeyguardStateController keyguardStateController;
    private final Lazy<KeyguardViewMediator> keyguardViewMediatorLazy;
    private boolean lightRevealAnimationPlaying;
    private final ValueAnimator lightRevealAnimator;
    private LightRevealScrim lightRevealScrim;
    private boolean shouldAnimateInKeyguard;
    private StatusBar statusBar;
    private final StatusBarStateControllerImpl statusBarStateControllerImpl;
    private final WakefulnessLifecycle wakefulnessLifecycle;

    /* compiled from: UnlockedScreenOffAnimationController.kt */
    public interface Callback {
        void onUnlockedScreenOffProgressUpdate(float f, float f2);
    }

    public UnlockedScreenOffAnimationController(Context context2, WakefulnessLifecycle wakefulnessLifecycle2, StatusBarStateControllerImpl statusBarStateControllerImpl2, Lazy<KeyguardViewMediator> lazy, KeyguardStateController keyguardStateController2, Lazy<DozeParameters> lazy2, GlobalSettings globalSettings2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(wakefulnessLifecycle2, "wakefulnessLifecycle");
        Intrinsics.checkNotNullParameter(statusBarStateControllerImpl2, "statusBarStateControllerImpl");
        Intrinsics.checkNotNullParameter(lazy, "keyguardViewMediatorLazy");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        Intrinsics.checkNotNullParameter(lazy2, "dozeParameters");
        Intrinsics.checkNotNullParameter(globalSettings2, "globalSettings");
        this.context = context2;
        this.wakefulnessLifecycle = wakefulnessLifecycle2;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl2;
        this.keyguardViewMediatorLazy = lazy;
        this.keyguardStateController = keyguardStateController2;
        this.dozeParameters = lazy2;
        this.globalSettings = globalSettings2;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(750L);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addUpdateListener(new UnlockedScreenOffAnimationController$lightRevealAnimator$1$1(this));
        ofFloat.addListener(new UnlockedScreenOffAnimationController$lightRevealAnimator$1$2(this));
        Unit unit = Unit.INSTANCE;
        this.lightRevealAnimator = ofFloat;
        this.animatorDurationScaleObserver = new UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1(this);
    }

    public final void initialize(StatusBar statusBar2, LightRevealScrim lightRevealScrim2) {
        Intrinsics.checkNotNullParameter(statusBar2, "statusBar");
        Intrinsics.checkNotNullParameter(lightRevealScrim2, "lightRevealScrim");
        this.lightRevealScrim = lightRevealScrim2;
        this.statusBar = statusBar2;
        updateAnimatorDurationScale();
        this.globalSettings.registerContentObserver(Settings.Global.getUriFor("animator_duration_scale"), false, this.animatorDurationScaleObserver);
        this.wakefulnessLifecycle.addObserver(this);
    }

    public final void updateAnimatorDurationScale() {
        this.animatorDurationScale = this.globalSettings.getFloat("animator_duration_scale", 1.0f);
    }

    public final void animateInKeyguard(View view, Runnable runnable) {
        Intrinsics.checkNotNullParameter(view, "keyguardView");
        Intrinsics.checkNotNullParameter(runnable, "after");
        this.shouldAnimateInKeyguard = false;
        view.setAlpha(0.0f);
        view.setVisibility(0);
        float y = view.getY();
        view.setY(y - (((float) view.getHeight()) * 0.1f));
        AnimatableProperty animatableProperty = AnimatableProperty.Y;
        PropertyAnimator.cancelAnimation(view, animatableProperty);
        long j = (long) 500;
        PropertyAnimator.setProperty(view, animatableProperty, y, new AnimationProperties().setDuration(j), true);
        view.animate().setDuration(j).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f).withEndAction(new UnlockedScreenOffAnimationController$animateInKeyguard$1(this, runnable)).start();
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedWakingUp() {
        this.decidedToAnimateGoingToSleep = null;
        this.shouldAnimateInKeyguard = false;
        this.lightRevealAnimator.cancel();
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onFinishedWakingUp() {
        this.aodUiAnimationPlaying = false;
        if (this.dozeParameters.get().canControlUnlockedScreenOff()) {
            StatusBar statusBar2 = this.statusBar;
            if (statusBar2 != null) {
                statusBar2.updateIsKeyguard(true);
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("statusBar");
                throw null;
            }
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedGoingToSleep() {
        if (this.dozeParameters.get().shouldControlUnlockedScreenOff()) {
            this.decidedToAnimateGoingToSleep = Boolean.TRUE;
            this.shouldAnimateInKeyguard = true;
            this.lightRevealAnimationPlaying = true;
            this.lightRevealAnimator.start();
            this.handler.postDelayed(new UnlockedScreenOffAnimationController$onStartedGoingToSleep$1(this), (long) (((float) 600) * this.animatorDurationScale));
            return;
        }
        this.decidedToAnimateGoingToSleep = Boolean.FALSE;
    }

    public final boolean shouldPlayUnlockedScreenOffAnimation() {
        StatusBar statusBar2;
        if (!Intrinsics.areEqual(this.decidedToAnimateGoingToSleep, Boolean.FALSE) && this.dozeParameters.get().canControlUnlockedScreenOff() && this.statusBarStateControllerImpl.getState() == 0 && (statusBar2 = this.statusBar) != null) {
            if (statusBar2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("statusBar");
                throw null;
            } else if (statusBar2.getNotificationPanelViewController().isFullyCollapsed()) {
                if (this.keyguardStateController.isKeyguardScreenRotationAllowed() || this.context.getResources().getConfiguration().orientation == 1) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public final void addCallback(Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callbacks.add(callback);
    }

    public final void removeCallback(Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callbacks.remove(callback);
    }

    public final void sendUnlockedScreenOffProgressUpdate(float f, float f2) {
        Iterator<T> it = this.callbacks.iterator();
        while (it.hasNext()) {
            it.next().onUnlockedScreenOffProgressUpdate(f, f2);
        }
    }

    public final boolean isScreenOffAnimationPlaying() {
        return this.lightRevealAnimationPlaying || this.aodUiAnimationPlaying;
    }

    public final boolean shouldAnimateInKeyguard() {
        return this.shouldAnimateInKeyguard;
    }

    public final boolean isScreenOffLightRevealAnimationPlaying() {
        return this.lightRevealAnimationPlaying;
    }
}

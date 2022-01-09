package com.android.wm.shell.common.magnetictarget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.wm.shell.animation.PhysicsAnimator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MagnetizedObject.kt */
public abstract class MagnetizedObject<T> {
    public static final Companion Companion = new Companion(null);
    private static boolean hapticSettingObserverInitialized;
    private static boolean systemHapticsEnabled;
    private Function5<? super MagneticTarget, ? super Float, ? super Float, ? super Boolean, ? super Function0<Unit>, Unit> animateStuckToTarget;
    private final PhysicsAnimator<T> animator;
    private final ArrayList<MagneticTarget> associatedTargets = new ArrayList<>();
    private final Context context;
    private boolean flingToTargetEnabled;
    private float flingToTargetMinVelocity;
    private float flingToTargetWidthPercent;
    private float flingUnstuckFromTargetMinVelocity;
    private PhysicsAnimator.SpringConfig flungIntoTargetSpringConfig;
    private boolean hapticsEnabled;
    public MagnetListener magnetListener;
    private boolean movedBeyondSlop;
    private final int[] objectLocationOnScreen = new int[2];
    private PhysicsAnimator.EndListener<T> physicsAnimatorEndListener;
    private PhysicsAnimator.UpdateListener<T> physicsAnimatorUpdateListener;
    private PhysicsAnimator.SpringConfig springConfig;
    private float stickToTargetMaxXVelocity;
    private MagneticTarget targetObjectIsStuckTo;
    private PointF touchDown;
    private int touchSlop;
    private final T underlyingObject;
    private final VelocityTracker velocityTracker;
    private final Vibrator vibrator;
    private final FloatPropertyCompat<? super T> xProperty;
    private final FloatPropertyCompat<? super T> yProperty;

    /* compiled from: MagnetizedObject.kt */
    public interface MagnetListener {
        void onReleasedInTarget(MagneticTarget magneticTarget);

        void onStuckToTarget(MagneticTarget magneticTarget);

        void onUnstuckFromTarget(MagneticTarget magneticTarget, float f, float f2, boolean z);
    }

    public abstract float getHeight(T t);

    public abstract void getLocationOnScreen(T t, int[] iArr);

    public abstract float getWidth(T t);

    public MagnetizedObject(Context context2, T t, FloatPropertyCompat<? super T> floatPropertyCompat, FloatPropertyCompat<? super T> floatPropertyCompat2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(t, "underlyingObject");
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "xProperty");
        Intrinsics.checkNotNullParameter(floatPropertyCompat2, "yProperty");
        this.context = context2;
        this.underlyingObject = t;
        this.xProperty = floatPropertyCompat;
        this.yProperty = floatPropertyCompat2;
        this.animator = PhysicsAnimator.Companion.getInstance(t);
        VelocityTracker obtain = VelocityTracker.obtain();
        Intrinsics.checkNotNullExpressionValue(obtain, "obtain()");
        this.velocityTracker = obtain;
        Object systemService = context2.getSystemService("vibrator");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.os.Vibrator");
        this.vibrator = (Vibrator) systemService;
        this.touchDown = new PointF();
        this.animateStuckToTarget = new MagnetizedObject$animateStuckToTarget$1(this);
        this.flingToTargetEnabled = true;
        this.flingToTargetWidthPercent = 3.0f;
        this.flingToTargetMinVelocity = 4000.0f;
        this.flingUnstuckFromTargetMinVelocity = 4000.0f;
        this.stickToTargetMaxXVelocity = 2000.0f;
        this.hapticsEnabled = true;
        PhysicsAnimator.SpringConfig springConfig2 = new PhysicsAnimator.SpringConfig(1500.0f, 1.0f);
        this.springConfig = springConfig2;
        this.flungIntoTargetSpringConfig = springConfig2;
        Companion.initHapticSettingObserver(context2);
    }

    public final T getUnderlyingObject() {
        return this.underlyingObject;
    }

    public final boolean getObjectStuckToTarget() {
        return this.targetObjectIsStuckTo != null;
    }

    public final MagnetListener getMagnetListener() {
        MagnetListener magnetListener2 = this.magnetListener;
        if (magnetListener2 != null) {
            return magnetListener2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("magnetListener");
        throw null;
    }

    public final void setMagnetListener(MagnetListener magnetListener2) {
        Intrinsics.checkNotNullParameter(magnetListener2, "<set-?>");
        this.magnetListener = magnetListener2;
    }

    public final void setAnimateStuckToTarget(Function5<? super MagneticTarget, ? super Float, ? super Float, ? super Boolean, ? super Function0<Unit>, Unit> function5) {
        Intrinsics.checkNotNullParameter(function5, "<set-?>");
        this.animateStuckToTarget = function5;
    }

    public final float getFlingToTargetWidthPercent() {
        return this.flingToTargetWidthPercent;
    }

    public final void setFlingToTargetWidthPercent(float f) {
        this.flingToTargetWidthPercent = f;
    }

    public final float getFlingToTargetMinVelocity() {
        return this.flingToTargetMinVelocity;
    }

    public final void setFlingToTargetMinVelocity(float f) {
        this.flingToTargetMinVelocity = f;
    }

    public final float getStickToTargetMaxXVelocity() {
        return this.stickToTargetMaxXVelocity;
    }

    public final void setStickToTargetMaxXVelocity(float f) {
        this.stickToTargetMaxXVelocity = f;
    }

    public final void setHapticsEnabled(boolean z) {
        this.hapticsEnabled = z;
    }

    public final void addTarget(MagneticTarget magneticTarget) {
        Intrinsics.checkNotNullParameter(magneticTarget, "target");
        this.associatedTargets.add(magneticTarget);
        magneticTarget.updateLocationOnScreen();
    }

    public final MagneticTarget addTarget(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "target");
        MagneticTarget magneticTarget = new MagneticTarget(view, i);
        addTarget(magneticTarget);
        return magneticTarget;
    }

    public final void clearAllTargets() {
        this.associatedTargets.clear();
    }

    public final boolean maybeConsumeMotionEvent(MotionEvent motionEvent) {
        T t;
        boolean z;
        Intrinsics.checkNotNullParameter(motionEvent, "ev");
        if (this.associatedTargets.size() == 0) {
            return false;
        }
        T t2 = null;
        if (motionEvent.getAction() == 0) {
            updateTargetViews$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell();
            this.velocityTracker.clear();
            this.targetObjectIsStuckTo = null;
            this.touchDown.set(motionEvent.getRawX(), motionEvent.getRawY());
            this.movedBeyondSlop = false;
        }
        addMovement(motionEvent);
        if (!this.movedBeyondSlop) {
            if (((float) Math.hypot((double) (motionEvent.getRawX() - this.touchDown.x), (double) (motionEvent.getRawY() - this.touchDown.y))) <= ((float) this.touchSlop)) {
                return false;
            }
            this.movedBeyondSlop = true;
        }
        Iterator<T> it = this.associatedTargets.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            T t3 = t;
            if (((float) Math.hypot((double) (motionEvent.getRawX() - t3.getCenterOnScreen().x), (double) (motionEvent.getRawY() - t3.getCenterOnScreen().y))) < ((float) t3.getMagneticFieldRadiusPx())) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (z) {
                break;
            }
        }
        T t4 = t;
        boolean z2 = !getObjectStuckToTarget() && t4 != null;
        boolean z3 = getObjectStuckToTarget() && t4 != null && !Intrinsics.areEqual(this.targetObjectIsStuckTo, t4);
        if (z2 || z3) {
            this.velocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.velocityTracker.getXVelocity();
            float yVelocity = this.velocityTracker.getYVelocity();
            if (z2 && Math.abs(xVelocity) > this.stickToTargetMaxXVelocity) {
                return false;
            }
            this.targetObjectIsStuckTo = t4;
            cancelAnimations$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell();
            MagnetListener magnetListener2 = getMagnetListener();
            Intrinsics.checkNotNull(t4);
            magnetListener2.onStuckToTarget(t4);
            this.animateStuckToTarget.invoke(t4, Float.valueOf(xVelocity), Float.valueOf(yVelocity), Boolean.FALSE, null);
            vibrateIfEnabled(5);
        } else if (t4 == null && getObjectStuckToTarget()) {
            this.velocityTracker.computeCurrentVelocity(1000);
            cancelAnimations$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell();
            MagnetListener magnetListener3 = getMagnetListener();
            MagneticTarget magneticTarget = this.targetObjectIsStuckTo;
            Intrinsics.checkNotNull(magneticTarget);
            magnetListener3.onUnstuckFromTarget(magneticTarget, this.velocityTracker.getXVelocity(), this.velocityTracker.getYVelocity(), false);
            this.targetObjectIsStuckTo = null;
            vibrateIfEnabled(2);
        }
        if (motionEvent.getAction() != 1) {
            return getObjectStuckToTarget();
        }
        this.velocityTracker.computeCurrentVelocity(1000);
        float xVelocity2 = this.velocityTracker.getXVelocity();
        float yVelocity2 = this.velocityTracker.getYVelocity();
        cancelAnimations$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell();
        if (getObjectStuckToTarget()) {
            if ((-yVelocity2) > this.flingUnstuckFromTargetMinVelocity) {
                MagnetListener magnetListener4 = getMagnetListener();
                MagneticTarget magneticTarget2 = this.targetObjectIsStuckTo;
                Intrinsics.checkNotNull(magneticTarget2);
                magnetListener4.onUnstuckFromTarget(magneticTarget2, xVelocity2, yVelocity2, true);
            } else {
                MagnetListener magnetListener5 = getMagnetListener();
                MagneticTarget magneticTarget3 = this.targetObjectIsStuckTo;
                Intrinsics.checkNotNull(magneticTarget3);
                magnetListener5.onReleasedInTarget(magneticTarget3);
                vibrateIfEnabled(5);
            }
            this.targetObjectIsStuckTo = null;
            return true;
        }
        Iterator<T> it2 = this.associatedTargets.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            T next = it2.next();
            if (isForcefulFlingTowardsTarget(next, motionEvent.getRawX(), motionEvent.getRawY(), xVelocity2, yVelocity2)) {
                t2 = next;
                break;
            }
        }
        T t5 = t2;
        if (t5 == null) {
            return false;
        }
        getMagnetListener().onStuckToTarget(t5);
        this.targetObjectIsStuckTo = t5;
        this.animateStuckToTarget.invoke(t5, Float.valueOf(xVelocity2), Float.valueOf(yVelocity2), Boolean.TRUE, new MagnetizedObject$maybeConsumeMotionEvent$1(this, t5));
        return true;
    }

    /* access modifiers changed from: private */
    @SuppressLint({"MissingPermission"})
    public final void vibrateIfEnabled(int i) {
        if (this.hapticsEnabled && systemHapticsEnabled) {
            this.vibrator.vibrate(VibrationEffect.createPredefined(i));
        }
    }

    private final void addMovement(MotionEvent motionEvent) {
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        this.velocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }

    /* access modifiers changed from: private */
    public final void animateStuckToTargetInternal(MagneticTarget magneticTarget, float f, float f2, boolean z, Function0<Unit> function0) {
        magneticTarget.updateLocationOnScreen();
        getLocationOnScreen(this.underlyingObject, this.objectLocationOnScreen);
        float width = (magneticTarget.getCenterOnScreen().x - (getWidth(this.underlyingObject) / 2.0f)) - ((float) this.objectLocationOnScreen[0]);
        float height = (magneticTarget.getCenterOnScreen().y - (getHeight(this.underlyingObject) / 2.0f)) - ((float) this.objectLocationOnScreen[1]);
        PhysicsAnimator.SpringConfig springConfig2 = z ? this.flungIntoTargetSpringConfig : this.springConfig;
        cancelAnimations$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell();
        PhysicsAnimator<T> physicsAnimator = this.animator;
        FloatPropertyCompat<? super T> floatPropertyCompat = this.xProperty;
        PhysicsAnimator<T> spring = physicsAnimator.spring(floatPropertyCompat, floatPropertyCompat.getValue(this.underlyingObject) + width, f, springConfig2);
        FloatPropertyCompat<? super T> floatPropertyCompat2 = this.yProperty;
        spring.spring(floatPropertyCompat2, floatPropertyCompat2.getValue(this.underlyingObject) + height, f2, springConfig2);
        PhysicsAnimator.UpdateListener<T> updateListener = this.physicsAnimatorUpdateListener;
        if (updateListener != null) {
            PhysicsAnimator<T> physicsAnimator2 = this.animator;
            Intrinsics.checkNotNull(updateListener);
            physicsAnimator2.addUpdateListener(updateListener);
        }
        PhysicsAnimator.EndListener<T> endListener = this.physicsAnimatorEndListener;
        if (endListener != null) {
            PhysicsAnimator<T> physicsAnimator3 = this.animator;
            Intrinsics.checkNotNull(endListener);
            physicsAnimator3.addEndListener(endListener);
        }
        if (function0 != null) {
            this.animator.withEndActions(function0);
        }
        this.animator.start();
    }

    private final boolean isForcefulFlingTowardsTarget(MagneticTarget magneticTarget, float f, float f2, float f3, float f4) {
        if (!this.flingToTargetEnabled) {
            return false;
        }
        if (!(f2 >= magneticTarget.getCenterOnScreen().y ? f4 < this.flingToTargetMinVelocity : f4 > this.flingToTargetMinVelocity)) {
            return false;
        }
        if (!(f3 == 0.0f)) {
            float f5 = f4 / f3;
            f = (magneticTarget.getCenterOnScreen().y - (f2 - (f * f5))) / f5;
        }
        float width = (((float) magneticTarget.getTargetView().getWidth()) * this.flingToTargetWidthPercent) / ((float) 2);
        if (f <= magneticTarget.getCenterOnScreen().x - width || f >= magneticTarget.getCenterOnScreen().x + width) {
            return false;
        }
        return true;
    }

    public final void cancelAnimations$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
        this.animator.cancel(this.xProperty, this.yProperty);
    }

    public final void updateTargetViews$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
        Iterator<T> it = this.associatedTargets.iterator();
        while (it.hasNext()) {
            it.next().updateLocationOnScreen();
        }
        if (this.associatedTargets.size() > 0) {
            this.touchSlop = ViewConfiguration.get(this.associatedTargets.get(0).getTargetView().getContext()).getScaledTouchSlop();
        }
    }

    /* compiled from: MagnetizedObject.kt */
    public static final class MagneticTarget {
        private final PointF centerOnScreen = new PointF();
        private int magneticFieldRadiusPx;
        private final View targetView;
        private final int[] tempLoc = new int[2];

        public MagneticTarget(View view, int i) {
            Intrinsics.checkNotNullParameter(view, "targetView");
            this.targetView = view;
            this.magneticFieldRadiusPx = i;
        }

        public final View getTargetView() {
            return this.targetView;
        }

        public final int getMagneticFieldRadiusPx() {
            return this.magneticFieldRadiusPx;
        }

        public final void setMagneticFieldRadiusPx(int i) {
            this.magneticFieldRadiusPx = i;
        }

        public final PointF getCenterOnScreen() {
            return this.centerOnScreen;
        }

        public final void updateLocationOnScreen() {
            this.targetView.post(new MagnetizedObject$MagneticTarget$updateLocationOnScreen$1(this));
        }
    }

    /* compiled from: MagnetizedObject.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private final void initHapticSettingObserver(Context context) {
            if (!MagnetizedObject.hapticSettingObserverInitialized) {
                MagnetizedObject$Companion$initHapticSettingObserver$hapticSettingObserver$1 magnetizedObject$Companion$initHapticSettingObserver$hapticSettingObserver$1 = new MagnetizedObject$Companion$initHapticSettingObserver$hapticSettingObserver$1(context, Handler.getMain());
                context.getContentResolver().registerContentObserver(Settings.System.getUriFor("haptic_feedback_enabled"), true, magnetizedObject$Companion$initHapticSettingObserver$hapticSettingObserver$1);
                magnetizedObject$Companion$initHapticSettingObserver$hapticSettingObserver$1.onChange(false);
                MagnetizedObject.hapticSettingObserverInitialized = true;
            }
        }
    }
}

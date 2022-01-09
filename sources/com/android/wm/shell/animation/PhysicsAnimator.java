package com.android.wm.shell.animation;

import android.util.ArrayMap;
import android.util.Log;
import androidx.dynamicanimation.animation.AnimationHandler;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PhysicsAnimator.kt */
public final class PhysicsAnimator<T> {
    public static final Companion Companion = new Companion(null);
    private static Function1<Object, ? extends PhysicsAnimator<?>> instanceConstructor = PhysicsAnimator$Companion$instanceConstructor$1.INSTANCE;
    private Function1<? super Set<? extends FloatPropertyCompat<? super T>>, Unit> cancelAction;
    private AnimationHandler customAnimationHandler;
    private FlingConfig defaultFling;
    private SpringConfig defaultSpring;
    private final ArrayList<Function0<Unit>> endActions;
    private final ArrayList<EndListener<T>> endListeners;
    private final ArrayMap<FloatPropertyCompat<? super T>, FlingAnimation> flingAnimations;
    private final ArrayMap<FloatPropertyCompat<? super T>, FlingConfig> flingConfigs;
    private ArrayList<PhysicsAnimator<T>.InternalListener> internalListeners;
    private final ArrayMap<FloatPropertyCompat<? super T>, SpringAnimation> springAnimations;
    private final ArrayMap<FloatPropertyCompat<? super T>, SpringConfig> springConfigs;
    private Function0<Unit> startAction;
    private final ArrayList<UpdateListener<T>> updateListeners;
    private final WeakReference<T> weakTarget;

    /* compiled from: PhysicsAnimator.kt */
    public interface EndListener<T> {
        void onAnimationEnd(T t, FloatPropertyCompat<? super T> floatPropertyCompat, boolean z, boolean z2, float f, float f2, boolean z3);
    }

    /* compiled from: PhysicsAnimator.kt */
    public interface UpdateListener<T> {
        void onAnimationUpdateForProperty(T t, ArrayMap<FloatPropertyCompat<? super T>, AnimationUpdate> arrayMap);
    }

    public /* synthetic */ PhysicsAnimator(Object obj, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj);
    }

    public static final float estimateFlingEndValue(float f, float f2, FlingConfig flingConfig) {
        return Companion.estimateFlingEndValue(f, f2, flingConfig);
    }

    public static final <T> PhysicsAnimator<T> getInstance(T t) {
        return Companion.getInstance(t);
    }

    private final boolean isValidValue(float f) {
        return f < Float.MAX_VALUE && f > -3.4028235E38f;
    }

    public final PhysicsAnimator<T> flingThenSpring(FloatPropertyCompat<? super T> floatPropertyCompat, float f, FlingConfig flingConfig, SpringConfig springConfig) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        Intrinsics.checkNotNullParameter(flingConfig, "flingConfig");
        Intrinsics.checkNotNullParameter(springConfig, "springConfig");
        return flingThenSpring$default(this, floatPropertyCompat, f, flingConfig, springConfig, false, 16, null);
    }

    private PhysicsAnimator(T t) {
        this.weakTarget = new WeakReference<>(t);
        this.springAnimations = new ArrayMap<>();
        this.flingAnimations = new ArrayMap<>();
        this.springConfigs = new ArrayMap<>();
        this.flingConfigs = new ArrayMap<>();
        this.updateListeners = new ArrayList<>();
        this.endListeners = new ArrayList<>();
        this.endActions = new ArrayList<>();
        this.defaultSpring = PhysicsAnimatorKt.access$getGlobalDefaultSpring$p();
        this.defaultFling = PhysicsAnimatorKt.access$getGlobalDefaultFling$p();
        this.internalListeners = new ArrayList<>();
        this.startAction = new PhysicsAnimator$startAction$1(this);
        this.cancelAction = new PhysicsAnimator$cancelAction$1(this);
    }

    /* compiled from: PhysicsAnimator.kt */
    public static final class AnimationUpdate {
        private final float value;
        private final float velocity;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnimationUpdate)) {
                return false;
            }
            AnimationUpdate animationUpdate = (AnimationUpdate) obj;
            return Intrinsics.areEqual(Float.valueOf(this.value), Float.valueOf(animationUpdate.value)) && Intrinsics.areEqual(Float.valueOf(this.velocity), Float.valueOf(animationUpdate.velocity));
        }

        public int hashCode() {
            return (Float.hashCode(this.value) * 31) + Float.hashCode(this.velocity);
        }

        public String toString() {
            return "AnimationUpdate(value=" + this.value + ", velocity=" + this.velocity + ')';
        }

        public AnimationUpdate(float f, float f2) {
            this.value = f;
            this.velocity = f2;
        }
    }

    public final ArrayList<PhysicsAnimator<T>.InternalListener> getInternalListeners$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
        return this.internalListeners;
    }

    public final PhysicsAnimator<T> spring(FloatPropertyCompat<? super T> floatPropertyCompat, float f, float f2, float f3, float f4) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        if (PhysicsAnimatorKt.access$getVerboseLogging$p()) {
            Log.d("PhysicsAnimator", "Springing " + Companion.getReadablePropertyName(floatPropertyCompat) + " to " + f + '.');
        }
        this.springConfigs.put(floatPropertyCompat, new SpringConfig(f3, f4, f2, f));
        return this;
    }

    public static /* synthetic */ PhysicsAnimator spring$default(PhysicsAnimator physicsAnimator, FloatPropertyCompat floatPropertyCompat, float f, float f2, SpringConfig springConfig, int i, Object obj) {
        if ((i & 8) != 0) {
            springConfig = physicsAnimator.defaultSpring;
        }
        return physicsAnimator.spring(floatPropertyCompat, f, f2, springConfig);
    }

    public final PhysicsAnimator<T> spring(FloatPropertyCompat<? super T> floatPropertyCompat, float f, float f2, SpringConfig springConfig) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        Intrinsics.checkNotNullParameter(springConfig, "config");
        return spring(floatPropertyCompat, f, f2, springConfig.getStiffness(), springConfig.getDampingRatio$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell());
    }

    public final PhysicsAnimator<T> spring(FloatPropertyCompat<? super T> floatPropertyCompat, float f, SpringConfig springConfig) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        Intrinsics.checkNotNullParameter(springConfig, "config");
        return spring(floatPropertyCompat, f, 0.0f, springConfig);
    }

    public final PhysicsAnimator<T> spring(FloatPropertyCompat<? super T> floatPropertyCompat, float f) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        return spring$default(this, floatPropertyCompat, f, 0.0f, null, 8, null);
    }

    public final PhysicsAnimator<T> fling(FloatPropertyCompat<? super T> floatPropertyCompat, float f, float f2, float f3, float f4) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        if (PhysicsAnimatorKt.access$getVerboseLogging$p()) {
            Log.d("PhysicsAnimator", "Flinging " + Companion.getReadablePropertyName(floatPropertyCompat) + " with velocity " + f + '.');
        }
        this.flingConfigs.put(floatPropertyCompat, new FlingConfig(f2, f3, f4, f));
        return this;
    }

    public final PhysicsAnimator<T> fling(FloatPropertyCompat<? super T> floatPropertyCompat, float f, FlingConfig flingConfig) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        Intrinsics.checkNotNullParameter(flingConfig, "config");
        return fling(floatPropertyCompat, f, flingConfig.getFriction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(), flingConfig.getMin(), flingConfig.getMax());
    }

    public static /* synthetic */ PhysicsAnimator flingThenSpring$default(PhysicsAnimator physicsAnimator, FloatPropertyCompat floatPropertyCompat, float f, FlingConfig flingConfig, SpringConfig springConfig, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            z = false;
        }
        return physicsAnimator.flingThenSpring(floatPropertyCompat, f, flingConfig, springConfig, z);
    }

    public final PhysicsAnimator<T> flingThenSpring(FloatPropertyCompat<? super T> floatPropertyCompat, float f, FlingConfig flingConfig, SpringConfig springConfig, boolean z) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        Intrinsics.checkNotNullParameter(flingConfig, "flingConfig");
        Intrinsics.checkNotNullParameter(springConfig, "springConfig");
        T t = this.weakTarget.get();
        if (t == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed target.");
            return this;
        }
        FlingConfig copy$default = FlingConfig.copy$default(flingConfig, 0.0f, 0.0f, 0.0f, 0.0f, 15, null);
        SpringConfig copy$default2 = SpringConfig.copy$default(springConfig, 0.0f, 0.0f, 0.0f, 0.0f, 15, null);
        int i = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
        float min = i < 0 ? flingConfig.getMin() : flingConfig.getMax();
        if (!z || !isValidValue(min)) {
            copy$default.setStartVelocity$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(f);
        } else {
            float value = floatPropertyCompat.getValue(t) + (f / (flingConfig.getFriction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() * 4.2f));
            float min2 = (flingConfig.getMin() + flingConfig.getMax()) / ((float) 2);
            if ((i < 0 && value > min2) || (f > 0.0f && value < min2)) {
                float min3 = value < min2 ? flingConfig.getMin() : flingConfig.getMax();
                if (isValidValue(min3)) {
                    return spring(floatPropertyCompat, min3, f, springConfig);
                }
            }
            float value2 = min - floatPropertyCompat.getValue(t);
            float friction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell = flingConfig.getFriction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() * 4.2f * value2;
            if (value2 > 0.0f && f >= 0.0f) {
                f = Math.max(friction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell, f);
            } else if (value2 < 0.0f && i <= 0) {
                f = Math.min(friction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell, f);
            }
            copy$default.setStartVelocity$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(f);
            copy$default2.setFinalPosition$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(min);
        }
        this.flingConfigs.put(floatPropertyCompat, copy$default);
        this.springConfigs.put(floatPropertyCompat, copy$default2);
        return this;
    }

    public final PhysicsAnimator<T> addUpdateListener(UpdateListener<T> updateListener) {
        Intrinsics.checkNotNullParameter(updateListener, "listener");
        this.updateListeners.add(updateListener);
        return this;
    }

    public final PhysicsAnimator<T> addEndListener(EndListener<T> endListener) {
        Intrinsics.checkNotNullParameter(endListener, "listener");
        this.endListeners.add(endListener);
        return this;
    }

    public final PhysicsAnimator<T> withEndActions(Function0<Unit>... function0Arr) {
        Intrinsics.checkNotNullParameter(function0Arr, "endActions");
        this.endActions.addAll(ArraysKt___ArraysKt.filterNotNull(function0Arr));
        return this;
    }

    public final PhysicsAnimator<T> withEndActions(Runnable... runnableArr) {
        Intrinsics.checkNotNullParameter(runnableArr, "endActions");
        ArrayList<Function0<Unit>> arrayList = this.endActions;
        List<T> list = ArraysKt___ArraysKt.filterNotNull(runnableArr);
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (T t : list) {
            arrayList2.add(new PhysicsAnimator$withEndActions$1$1(t));
        }
        arrayList.addAll(arrayList2);
        return this;
    }

    public final void setDefaultSpringConfig(SpringConfig springConfig) {
        Intrinsics.checkNotNullParameter(springConfig, "defaultSpring");
        this.defaultSpring = springConfig;
    }

    public final void setCustomAnimationHandler(AnimationHandler animationHandler) {
        Intrinsics.checkNotNullParameter(animationHandler, "handler");
        this.customAnimationHandler = animationHandler;
    }

    public final void start() {
        this.startAction.invoke();
    }

    public final void startInternal$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
        T t = this.weakTarget.get();
        if (t == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed object.");
            return;
        }
        ArrayList<T> arrayList = new ArrayList();
        for (FloatPropertyCompat<? super T> floatPropertyCompat : getAnimatedProperties$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell()) {
            FlingConfig flingConfig = this.flingConfigs.get(floatPropertyCompat);
            SpringConfig springConfig = this.springConfigs.get(floatPropertyCompat);
            float value = floatPropertyCompat.getValue(t);
            if (flingConfig != null) {
                arrayList.add(new PhysicsAnimator$startInternal$1(flingConfig, this, floatPropertyCompat, t, value));
            }
            if (springConfig != null) {
                if (flingConfig == null) {
                    SpringAnimation springAnimation = getSpringAnimation(floatPropertyCompat, t);
                    if (this.customAnimationHandler != null && !Intrinsics.areEqual(springAnimation.getAnimationHandler(), this.customAnimationHandler)) {
                        if (springAnimation.isRunning()) {
                            cancel(floatPropertyCompat);
                        }
                        AnimationHandler animationHandler = this.customAnimationHandler;
                        if (animationHandler == null) {
                            animationHandler = springAnimation.getAnimationHandler();
                            Intrinsics.checkNotNullExpressionValue(animationHandler, "springAnim.animationHandler");
                        }
                        springAnimation.setAnimationHandler(animationHandler);
                    }
                    springConfig.applyToAnimation$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(springAnimation);
                    arrayList.add(new PhysicsAnimator$startInternal$2(springAnimation));
                } else {
                    this.endListeners.add(0, new PhysicsAnimator$startInternal$3(floatPropertyCompat, flingConfig.getMin(), flingConfig.getMax(), springConfig, this));
                }
            }
        }
        this.internalListeners.add(new InternalListener(this, t, getAnimatedProperties$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(), new ArrayList(this.updateListeners), new ArrayList(this.endListeners), new ArrayList(this.endActions)));
        for (T t2 : arrayList) {
            t2.invoke();
        }
        clearAnimator();
    }

    private final void clearAnimator() {
        this.springConfigs.clear();
        this.flingConfigs.clear();
        this.updateListeners.clear();
        this.endListeners.clear();
        this.endActions.clear();
    }

    /* access modifiers changed from: private */
    public final SpringAnimation getSpringAnimation(FloatPropertyCompat<? super T> floatPropertyCompat, T t) {
        ArrayMap<FloatPropertyCompat<? super T>, SpringAnimation> arrayMap = this.springAnimations;
        SpringAnimation springAnimation = arrayMap.get(floatPropertyCompat);
        if (springAnimation == null) {
            springAnimation = (SpringAnimation) configureDynamicAnimation(new SpringAnimation(t, floatPropertyCompat), floatPropertyCompat);
            arrayMap.put(floatPropertyCompat, springAnimation);
        }
        Intrinsics.checkNotNullExpressionValue(springAnimation, "springAnimations.getOrPut(\n                property,\n                { configureDynamicAnimation(SpringAnimation(target, property), property)\n                        as SpringAnimation })");
        return springAnimation;
    }

    /* access modifiers changed from: private */
    public final FlingAnimation getFlingAnimation(FloatPropertyCompat<? super T> floatPropertyCompat, T t) {
        ArrayMap<FloatPropertyCompat<? super T>, FlingAnimation> arrayMap = this.flingAnimations;
        FlingAnimation flingAnimation = arrayMap.get(floatPropertyCompat);
        if (flingAnimation == null) {
            flingAnimation = (FlingAnimation) configureDynamicAnimation(new FlingAnimation(t, floatPropertyCompat), floatPropertyCompat);
            arrayMap.put(floatPropertyCompat, flingAnimation);
        }
        Intrinsics.checkNotNullExpressionValue(flingAnimation, "flingAnimations.getOrPut(\n                property,\n                { configureDynamicAnimation(FlingAnimation(target, property), property)\n                        as FlingAnimation })");
        return flingAnimation;
    }

    private final DynamicAnimation<?> configureDynamicAnimation(DynamicAnimation<?> dynamicAnimation, FloatPropertyCompat<? super T> floatPropertyCompat) {
        dynamicAnimation.addUpdateListener(new PhysicsAnimator$configureDynamicAnimation$1(this, floatPropertyCompat));
        dynamicAnimation.addEndListener(new PhysicsAnimator$configureDynamicAnimation$2(this, floatPropertyCompat, dynamicAnimation));
        return dynamicAnimation;
    }

    /* compiled from: PhysicsAnimator.kt */
    public final class InternalListener {
        private List<? extends Function0<Unit>> endActions;
        private List<? extends EndListener<T>> endListeners;
        private int numPropertiesAnimating;
        private Set<? extends FloatPropertyCompat<? super T>> properties;
        private final T target;
        final /* synthetic */ PhysicsAnimator<T> this$0;
        private final ArrayMap<FloatPropertyCompat<? super T>, AnimationUpdate> undispatchedUpdates = new ArrayMap<>();
        private List<? extends UpdateListener<T>> updateListeners;

        public InternalListener(PhysicsAnimator physicsAnimator, T t, Set<? extends FloatPropertyCompat<? super T>> set, List<? extends UpdateListener<T>> list, List<? extends EndListener<T>> list2, List<? extends Function0<Unit>> list3) {
            Intrinsics.checkNotNullParameter(physicsAnimator, "this$0");
            Intrinsics.checkNotNullParameter(set, "properties");
            Intrinsics.checkNotNullParameter(list, "updateListeners");
            Intrinsics.checkNotNullParameter(list2, "endListeners");
            Intrinsics.checkNotNullParameter(list3, "endActions");
            this.this$0 = physicsAnimator;
            this.target = t;
            this.properties = set;
            this.updateListeners = list;
            this.endListeners = list2;
            this.endActions = list3;
            this.numPropertiesAnimating = set.size();
        }

        public final void onInternalAnimationUpdate$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(FloatPropertyCompat<? super T> floatPropertyCompat, float f, float f2) {
            Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
            if (this.properties.contains(floatPropertyCompat)) {
                this.undispatchedUpdates.put(floatPropertyCompat, new AnimationUpdate(f, f2));
                maybeDispatchUpdates();
            }
        }

        public final boolean onInternalAnimationEnd$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(FloatPropertyCompat<? super T> floatPropertyCompat, boolean z, float f, float f2, boolean z2) {
            Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
            if (!this.properties.contains(floatPropertyCompat)) {
                return false;
            }
            this.numPropertiesAnimating--;
            maybeDispatchUpdates();
            if (this.undispatchedUpdates.containsKey(floatPropertyCompat)) {
                Iterator<T> it = this.updateListeners.iterator();
                while (it.hasNext()) {
                    T t = this.target;
                    ArrayMap<FloatPropertyCompat<? super T>, AnimationUpdate> arrayMap = new ArrayMap<>();
                    arrayMap.put(floatPropertyCompat, this.undispatchedUpdates.get(floatPropertyCompat));
                    Unit unit = Unit.INSTANCE;
                    it.next().onAnimationUpdateForProperty(t, arrayMap);
                }
                this.undispatchedUpdates.remove(floatPropertyCompat);
            }
            boolean z3 = !this.this$0.arePropertiesAnimating(this.properties);
            List<? extends EndListener<T>> list = this.endListeners;
            PhysicsAnimator<T> physicsAnimator = this.this$0;
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                it2.next().onAnimationEnd(this.target, floatPropertyCompat, z2, z, f, f2, z3);
                if (physicsAnimator.isPropertyAnimating(floatPropertyCompat)) {
                    return false;
                }
            }
            if (z3 && !z) {
                Iterator<T> it3 = this.endActions.iterator();
                while (it3.hasNext()) {
                    it3.next().invoke();
                }
            }
            return z3;
        }

        private final void maybeDispatchUpdates() {
            if (this.undispatchedUpdates.size() >= this.numPropertiesAnimating && this.undispatchedUpdates.size() > 0) {
                Iterator<T> it = this.updateListeners.iterator();
                while (it.hasNext()) {
                    it.next().onAnimationUpdateForProperty(this.target, new ArrayMap<>(this.undispatchedUpdates));
                }
                this.undispatchedUpdates.clear();
            }
        }
    }

    public final boolean isRunning() {
        Set<FloatPropertyCompat<? super T>> keySet = this.springAnimations.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "springAnimations.keys");
        Set<FloatPropertyCompat<? super T>> keySet2 = this.flingAnimations.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet2, "flingAnimations.keys");
        return arePropertiesAnimating(CollectionsKt___CollectionsKt.union(keySet, keySet2));
    }

    public final boolean isPropertyAnimating(FloatPropertyCompat<? super T> floatPropertyCompat) {
        Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
        SpringAnimation springAnimation = this.springAnimations.get(floatPropertyCompat);
        if (!(springAnimation == null ? false : springAnimation.isRunning())) {
            FlingAnimation flingAnimation = this.flingAnimations.get(floatPropertyCompat);
            if (flingAnimation == null ? false : flingAnimation.isRunning()) {
                return true;
            }
            return false;
        }
        return true;
    }

    public final Set<FloatPropertyCompat<? super T>> getAnimatedProperties$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
        Set<FloatPropertyCompat<? super T>> keySet = this.springConfigs.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "springConfigs.keys");
        Set<FloatPropertyCompat<? super T>> keySet2 = this.flingConfigs.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet2, "flingConfigs.keys");
        return CollectionsKt___CollectionsKt.union(keySet, keySet2);
    }

    public final void cancelInternal$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(Set<? extends FloatPropertyCompat<? super T>> set) {
        Intrinsics.checkNotNullParameter(set, "properties");
        Iterator<? extends FloatPropertyCompat<? super T>> it = set.iterator();
        while (it.hasNext()) {
            FloatPropertyCompat floatPropertyCompat = (FloatPropertyCompat) it.next();
            FlingAnimation flingAnimation = this.flingAnimations.get(floatPropertyCompat);
            if (flingAnimation != null) {
                flingAnimation.cancel();
            }
            SpringAnimation springAnimation = this.springAnimations.get(floatPropertyCompat);
            if (springAnimation != null) {
                springAnimation.cancel();
            }
        }
    }

    public final void cancel() {
        Function1<? super Set<? extends FloatPropertyCompat<? super T>>, Unit> function1 = this.cancelAction;
        Set<FloatPropertyCompat<? super T>> keySet = this.flingAnimations.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "flingAnimations.keys");
        function1.invoke(keySet);
        Function1<? super Set<? extends FloatPropertyCompat<? super T>>, Unit> function12 = this.cancelAction;
        Set<FloatPropertyCompat<? super T>> keySet2 = this.springAnimations.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet2, "springAnimations.keys");
        function12.invoke(keySet2);
    }

    public final void cancel(FloatPropertyCompat<? super T>... floatPropertyCompatArr) {
        Intrinsics.checkNotNullParameter(floatPropertyCompatArr, "properties");
        this.cancelAction.invoke(ArraysKt___ArraysKt.toSet(floatPropertyCompatArr));
    }

    /* compiled from: PhysicsAnimator.kt */
    public static final class SpringConfig {
        private float dampingRatio;
        private float finalPosition;
        private float startVelocity;
        private float stiffness;

        public static /* synthetic */ SpringConfig copy$default(SpringConfig springConfig, float f, float f2, float f3, float f4, int i, Object obj) {
            if ((i & 1) != 0) {
                f = springConfig.stiffness;
            }
            if ((i & 2) != 0) {
                f2 = springConfig.dampingRatio;
            }
            if ((i & 4) != 0) {
                f3 = springConfig.startVelocity;
            }
            if ((i & 8) != 0) {
                f4 = springConfig.finalPosition;
            }
            return springConfig.copy(f, f2, f3, f4);
        }

        public final SpringConfig copy(float f, float f2, float f3, float f4) {
            return new SpringConfig(f, f2, f3, f4);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SpringConfig)) {
                return false;
            }
            SpringConfig springConfig = (SpringConfig) obj;
            return Intrinsics.areEqual(Float.valueOf(this.stiffness), Float.valueOf(springConfig.stiffness)) && Intrinsics.areEqual(Float.valueOf(this.dampingRatio), Float.valueOf(springConfig.dampingRatio)) && Intrinsics.areEqual(Float.valueOf(this.startVelocity), Float.valueOf(springConfig.startVelocity)) && Intrinsics.areEqual(Float.valueOf(this.finalPosition), Float.valueOf(springConfig.finalPosition));
        }

        public int hashCode() {
            return (((((Float.hashCode(this.stiffness) * 31) + Float.hashCode(this.dampingRatio)) * 31) + Float.hashCode(this.startVelocity)) * 31) + Float.hashCode(this.finalPosition);
        }

        public String toString() {
            return "SpringConfig(stiffness=" + this.stiffness + ", dampingRatio=" + this.dampingRatio + ", startVelocity=" + this.startVelocity + ", finalPosition=" + this.finalPosition + ')';
        }

        public SpringConfig(float f, float f2, float f3, float f4) {
            this.stiffness = f;
            this.dampingRatio = f2;
            this.startVelocity = f3;
            this.finalPosition = f4;
        }

        public final float getStiffness() {
            return this.stiffness;
        }

        public final float getDampingRatio$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
            return this.dampingRatio;
        }

        public final void setStartVelocity$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(float f) {
            this.startVelocity = f;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ SpringConfig(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? PhysicsAnimatorKt.access$getUNSET$p() : f4);
        }

        public final float getFinalPosition$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
            return this.finalPosition;
        }

        public final void setFinalPosition$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(float f) {
            this.finalPosition = f;
        }

        public SpringConfig() {
            this(PhysicsAnimatorKt.access$getGlobalDefaultSpring$p().stiffness, PhysicsAnimatorKt.access$getGlobalDefaultSpring$p().dampingRatio);
        }

        public SpringConfig(float f, float f2) {
            this(f, f2, 0.0f, 0.0f, 8, null);
        }

        public final void applyToAnimation$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(SpringAnimation springAnimation) {
            Intrinsics.checkNotNullParameter(springAnimation, "anim");
            SpringForce spring = springAnimation.getSpring();
            if (spring == null) {
                spring = new SpringForce();
            }
            spring.setStiffness(getStiffness());
            spring.setDampingRatio(getDampingRatio$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell());
            spring.setFinalPosition(getFinalPosition$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell());
            Unit unit = Unit.INSTANCE;
            springAnimation.setSpring(spring);
            float f = this.startVelocity;
            if (!(f == 0.0f)) {
                springAnimation.setStartVelocity(f);
            }
        }
    }

    /* compiled from: PhysicsAnimator.kt */
    public static final class FlingConfig {
        private float friction;
        private float max;
        private float min;
        private float startVelocity;

        public static /* synthetic */ FlingConfig copy$default(FlingConfig flingConfig, float f, float f2, float f3, float f4, int i, Object obj) {
            if ((i & 1) != 0) {
                f = flingConfig.friction;
            }
            if ((i & 2) != 0) {
                f2 = flingConfig.min;
            }
            if ((i & 4) != 0) {
                f3 = flingConfig.max;
            }
            if ((i & 8) != 0) {
                f4 = flingConfig.startVelocity;
            }
            return flingConfig.copy(f, f2, f3, f4);
        }

        public final FlingConfig copy(float f, float f2, float f3, float f4) {
            return new FlingConfig(f, f2, f3, f4);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FlingConfig)) {
                return false;
            }
            FlingConfig flingConfig = (FlingConfig) obj;
            return Intrinsics.areEqual(Float.valueOf(this.friction), Float.valueOf(flingConfig.friction)) && Intrinsics.areEqual(Float.valueOf(this.min), Float.valueOf(flingConfig.min)) && Intrinsics.areEqual(Float.valueOf(this.max), Float.valueOf(flingConfig.max)) && Intrinsics.areEqual(Float.valueOf(this.startVelocity), Float.valueOf(flingConfig.startVelocity));
        }

        public int hashCode() {
            return (((((Float.hashCode(this.friction) * 31) + Float.hashCode(this.min)) * 31) + Float.hashCode(this.max)) * 31) + Float.hashCode(this.startVelocity);
        }

        public String toString() {
            return "FlingConfig(friction=" + this.friction + ", min=" + this.min + ", max=" + this.max + ", startVelocity=" + this.startVelocity + ')';
        }

        public FlingConfig(float f, float f2, float f3, float f4) {
            this.friction = f;
            this.min = f2;
            this.max = f3;
            this.startVelocity = f4;
        }

        public final float getFriction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
            return this.friction;
        }

        public final float getMin() {
            return this.min;
        }

        public final void setMin(float f) {
            this.min = f;
        }

        public final float getMax() {
            return this.max;
        }

        public final void setMax(float f) {
            this.max = f;
        }

        public final float getStartVelocity$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
            return this.startVelocity;
        }

        public final void setStartVelocity$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(float f) {
            this.startVelocity = f;
        }

        public FlingConfig() {
            this(PhysicsAnimatorKt.access$getGlobalDefaultFling$p().friction);
        }

        public FlingConfig(float f) {
            this(f, PhysicsAnimatorKt.access$getGlobalDefaultFling$p().min, PhysicsAnimatorKt.access$getGlobalDefaultFling$p().max);
        }

        public FlingConfig(float f, float f2, float f3) {
            this(f, f2, f3, 0.0f);
        }

        public final void applyToAnimation$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell(FlingAnimation flingAnimation) {
            Intrinsics.checkNotNullParameter(flingAnimation, "anim");
            flingAnimation.setFriction(getFriction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell());
            flingAnimation.setMinValue(getMin());
            flingAnimation.setMaxValue(getMax());
            flingAnimation.setStartVelocity(getStartVelocity$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell());
        }
    }

    /* compiled from: PhysicsAnimator.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Function1<Object, PhysicsAnimator<?>> getInstanceConstructor$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() {
            return PhysicsAnimator.instanceConstructor;
        }

        public final <T> PhysicsAnimator<T> getInstance(T t) {
            Intrinsics.checkNotNullParameter(t, "target");
            if (!PhysicsAnimatorKt.getAnimators().containsKey(t)) {
                PhysicsAnimatorKt.getAnimators().put(t, getInstanceConstructor$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell().invoke(t));
            }
            PhysicsAnimator<?> physicsAnimator = PhysicsAnimatorKt.getAnimators().get(t);
            Objects.requireNonNull(physicsAnimator, "null cannot be cast to non-null type com.android.wm.shell.animation.PhysicsAnimator<T of com.android.wm.shell.animation.PhysicsAnimator.Companion.getInstance>");
            return (PhysicsAnimator<T>) physicsAnimator;
        }

        public final float estimateFlingEndValue(float f, float f2, FlingConfig flingConfig) {
            Intrinsics.checkNotNullParameter(flingConfig, "flingConfig");
            return Math.min(flingConfig.getMax(), Math.max(flingConfig.getMin(), f + (f2 / (flingConfig.getFriction$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell() * 4.2f))));
        }

        public final String getReadablePropertyName(FloatPropertyCompat<?> floatPropertyCompat) {
            Intrinsics.checkNotNullParameter(floatPropertyCompat, "property");
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.TRANSLATION_X)) {
                return "translationX";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.TRANSLATION_Y)) {
                return "translationY";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.TRANSLATION_Z)) {
                return "translationZ";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.SCALE_X)) {
                return "scaleX";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.SCALE_Y)) {
                return "scaleY";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.ROTATION)) {
                return "rotation";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.ROTATION_X)) {
                return "rotationX";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.ROTATION_Y)) {
                return "rotationY";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.SCROLL_X)) {
                return "scrollX";
            }
            if (Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.SCROLL_Y)) {
                return "scrollY";
            }
            return Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.ALPHA) ? "alpha" : "Custom FloatPropertyCompat instance";
        }
    }

    public final boolean arePropertiesAnimating(Set<? extends FloatPropertyCompat<? super T>> set) {
        Intrinsics.checkNotNullParameter(set, "properties");
        if ((set instanceof Collection) && set.isEmpty()) {
            return false;
        }
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            if (isPropertyAnimating(it.next())) {
                return true;
            }
        }
        return false;
    }
}

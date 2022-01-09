package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Action.kt */
public abstract class Action {
    private final Context context;
    private final Set<FeedbackEffect> feedbackEffects;
    private final Handler handler;
    private boolean isAvailable;
    private final Set<Listener> listeners;

    /* compiled from: Action.kt */
    public interface Listener {
        void onActionAvailabilityChanged(Action action);
    }

    public abstract String getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig();

    public abstract void onTrigger(GestureSensor.DetectionProperties detectionProperties);

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: java.util.Set<? extends com.google.android.systemui.columbus.feedback.FeedbackEffect> */
    /* JADX WARN: Multi-variable type inference failed */
    public Action(Context context2, Set<? extends FeedbackEffect> set) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
        this.feedbackEffects = set;
        this.isAvailable = true;
        this.listeners = new LinkedHashSet();
        this.handler = new Handler(Looper.getMainLooper());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ Action(Context context2, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context2, (i & 2) != 0 ? null : set);
    }

    /* access modifiers changed from: protected */
    public final Context getContext() {
        return this.context;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setAvailable$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig(boolean z) {
        this.isAvailable = z;
    }

    public void registerListener(Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listeners.remove(listener);
    }

    public void onGestureDetected(int i, GestureSensor.DetectionProperties detectionProperties) {
        updateFeedbackEffects(i, detectionProperties);
        if (i == 1) {
            Log.i(getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig(), "Triggering");
            onTrigger(detectionProperties);
        }
    }

    /* access modifiers changed from: protected */
    public final void setAvailable(boolean z) {
        if (isAvailable() != z) {
            setAvailable$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig(z);
            Iterator<T> it = this.listeners.iterator();
            while (it.hasNext()) {
                this.handler.post(new Action$setAvailable$1$1(it.next(), this));
            }
            if (!isAvailable()) {
                this.handler.post(new Action$setAvailable$2(this));
            }
        }
    }

    public static /* synthetic */ void updateFeedbackEffects$default(Action action, int i, GestureSensor.DetectionProperties detectionProperties, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                detectionProperties = null;
            }
            action.updateFeedbackEffects(i, detectionProperties);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateFeedbackEffects");
    }

    public void updateFeedbackEffects(int i, GestureSensor.DetectionProperties detectionProperties) {
        Set<FeedbackEffect> set = this.feedbackEffects;
        if (set != null) {
            Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                it.next().onGestureDetected(i, detectionProperties);
            }
        }
    }

    public String toString() {
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "javaClass.simpleName");
        return simpleName;
    }
}

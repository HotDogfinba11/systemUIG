package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Gate.kt */
public abstract class Gate {
    private boolean active;
    private final Context context;
    private boolean isBlocked;
    private final Set<Listener> listeners;
    private final Handler notifyHandler;

    /* compiled from: Gate.kt */
    public interface Listener {
        void onGateChanged(Gate gate);
    }

    /* access modifiers changed from: protected */
    public abstract void onActivate();

    /* access modifiers changed from: protected */
    public abstract void onDeactivate();

    public Gate(Context context2, Handler handler) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(handler, "notifyHandler");
        this.context = context2;
        this.notifyHandler = handler;
        this.listeners = new LinkedHashSet();
    }

    public final Context getContext() {
        return this.context;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ Gate(Context context2, Handler handler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context2, (i & 2) != 0 ? new Handler(Looper.getMainLooper()) : handler);
    }

    public final boolean getActive() {
        return this.active;
    }

    public void registerListener(Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listeners.add(listener);
        maybeActivate();
    }

    public void unregisterListener(Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listeners.remove(listener);
        maybeDeactivate();
    }

    private final void maybeActivate() {
        if (!this.active && (!this.listeners.isEmpty())) {
            this.active = true;
            onActivate();
        }
    }

    private final void maybeDeactivate() {
        if (this.active && this.listeners.isEmpty()) {
            this.active = false;
            onDeactivate();
        }
    }

    /* access modifiers changed from: protected */
    public final void setBlocking(boolean z) {
        if (this.isBlocked != z) {
            this.isBlocked = z;
            notifyListeners();
        }
    }

    public boolean isBlocking() {
        return this.active && this.isBlocked;
    }

    private final void notifyListeners() {
        if (this.active) {
            Iterator<T> it = this.listeners.iterator();
            while (it.hasNext()) {
                this.notifyHandler.post(new Gate$notifyListeners$1$1(it.next(), this));
            }
        }
    }

    public String toString() {
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "javaClass.simpleName");
        return simpleName;
    }
}

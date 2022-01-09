package com.google.android.systemui.columbus.gates;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.systemui.columbus.actions.Action;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: CameraVisibility.kt */
public final class CameraVisibility extends Gate {
    public static final Companion Companion = new Companion(null);
    private final CameraVisibility$actionListener$1 actionListener = new CameraVisibility$actionListener$1(this);
    private final IActivityManager activityManager;
    private boolean cameraShowing;
    private boolean exceptionActive;
    private final List<Action> exceptions;
    private final CameraVisibility$gateListener$1 gateListener = new CameraVisibility$gateListener$1(this);
    private final KeyguardVisibility keyguardGate;
    private final PackageManager packageManager;
    private final PowerState powerState;
    private final CameraVisibility$taskStackListener$1 taskStackListener = new CameraVisibility$taskStackListener$1(this);
    private final Handler updateHandler;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CameraVisibility(Context context, List<Action> list, KeyguardVisibility keyguardVisibility, PowerState powerState2, IActivityManager iActivityManager, Handler handler) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(list, "exceptions");
        Intrinsics.checkNotNullParameter(keyguardVisibility, "keyguardGate");
        Intrinsics.checkNotNullParameter(powerState2, "powerState");
        Intrinsics.checkNotNullParameter(iActivityManager, "activityManager");
        Intrinsics.checkNotNullParameter(handler, "updateHandler");
        this.exceptions = list;
        this.keyguardGate = keyguardVisibility;
        this.powerState = powerState2;
        this.activityManager = iActivityManager;
        this.updateHandler = handler;
        this.packageManager = context.getPackageManager();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onActivate() {
        this.exceptionActive = false;
        for (T t : this.exceptions) {
            t.registerListener(this.actionListener);
            this.exceptionActive = t.isAvailable() | this.exceptionActive;
        }
        this.cameraShowing = isCameraShowing();
        this.keyguardGate.registerListener(this.gateListener);
        this.powerState.registerListener(this.gateListener);
        try {
            this.activityManager.registerTaskStackListener(this.taskStackListener);
        } catch (RemoteException e) {
            Log.e("Columbus/CameraVis", "Could not register task stack listener", e);
        }
        updateBlocking();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onDeactivate() {
        this.keyguardGate.unregisterListener(this.gateListener);
        this.powerState.unregisterListener(this.gateListener);
        Iterator<T> it = this.exceptions.iterator();
        while (it.hasNext()) {
            it.next().unregisterListener(this.actionListener);
        }
        try {
            this.activityManager.unregisterTaskStackListener(this.taskStackListener);
        } catch (RemoteException e) {
            Log.e("Columbus/CameraVis", "Could not unregister task stack listener", e);
        }
    }

    /* access modifiers changed from: private */
    public final void updateBlocking() {
        setBlocking(!this.exceptionActive && this.cameraShowing);
    }

    /* access modifiers changed from: private */
    public final void updateCameraIsShowing() {
        this.cameraShowing = isCameraShowing();
        updateBlocking();
    }

    public final boolean isCameraShowing() {
        return isCameraTopActivity() && isCameraInForeground() && !this.powerState.isBlocking();
    }

    private final boolean isCameraTopActivity() {
        String str;
        try {
            List tasks = this.activityManager.getTasks(1);
            if (tasks.isEmpty()) {
                return false;
            }
            ComponentName componentName = ((ActivityManager.RunningTaskInfo) tasks.get(0)).topActivity;
            if (componentName == null) {
                str = null;
            } else {
                str = componentName.getPackageName();
            }
            return StringsKt__StringsJVMKt.equals(str, "com.google.android.GoogleCamera", true);
        } catch (RemoteException e) {
            Log.e("Columbus/CameraVis", "unable to check task stack", e);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean isCameraInForeground() {
        /*
        // Method dump skipped, instructions count: 103
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.columbus.gates.CameraVisibility.isCameraInForeground():boolean");
    }

    @Override // com.google.android.systemui.columbus.gates.Gate
    public String toString() {
        return super.toString() + " [cameraShowing -> " + this.cameraShowing + "; exceptionActive -> " + this.exceptionActive + ']';
    }

    /* compiled from: CameraVisibility.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

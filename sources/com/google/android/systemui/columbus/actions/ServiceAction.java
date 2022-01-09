package com.google.android.systemui.columbus.actions;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.systemui.columbus.ColumbusServiceProxy;
import com.google.android.systemui.columbus.IColumbusService;
import com.google.android.systemui.columbus.IColumbusServiceGestureListener;
import com.google.android.systemui.columbus.IColumbusServiceListener;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ServiceAction.kt */
public abstract class ServiceAction extends Action implements IBinder.DeathRecipient {
    public static final Companion Companion = new Companion(null);
    private IColumbusService columbusService;
    private final ColumbusServiceConnection columbusServiceConnection;
    private IColumbusServiceGestureListener columbusServiceGestureListener;
    private final ColumbusServiceListener columbusServiceListener;
    private final Set<String> supportedCallerPackages;
    private final IBinder token;

    /* access modifiers changed from: protected */
    public abstract Set<String> getSupportedCallerPackages();

    /* access modifiers changed from: protected */
    public void onServiceConnected() {
    }

    /* access modifiers changed from: protected */
    public void onServiceDisconnected() {
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ServiceAction(Context context, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : set);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ServiceAction(Context context, Set<? extends FeedbackEffect> set) {
        super(context, set);
        Intrinsics.checkNotNullParameter(context, "context");
        this.supportedCallerPackages = SetsKt__SetsKt.emptySet();
        this.token = new Binder();
        ColumbusServiceConnection columbusServiceConnection2 = new ColumbusServiceConnection(this);
        this.columbusServiceConnection = columbusServiceConnection2;
        this.columbusServiceListener = new ColumbusServiceListener(this);
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(context, ColumbusServiceProxy.class));
            context.bindService(intent, columbusServiceConnection2, 1);
        } catch (SecurityException e) {
            Log.e("Columbus/ServiceAction", "Unable to bind to ColumbusServiceProxy", e);
        }
        updateAvailable();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void updateAvailable() {
        setAvailable(this.columbusServiceGestureListener != null);
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        IColumbusServiceGestureListener iColumbusServiceGestureListener = this.columbusServiceGestureListener;
        if (iColumbusServiceGestureListener != null) {
            try {
                iColumbusServiceGestureListener.onTrigger();
            } catch (DeadObjectException e) {
                Log.e("Columbus/ServiceAction", "Listener crashed or closed without unregistering", e);
                this.columbusServiceGestureListener = null;
                updateAvailable();
            } catch (RemoteException e2) {
                Log.e("Columbus/ServiceAction", "Unable to send trigger, setting listener to null", e2);
                this.columbusServiceGestureListener = null;
                updateAvailable();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean checkSupportedCaller() {
        T t;
        String[] packagesForUid = getContext().getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if (packagesForUid == null) {
            return false;
        }
        Iterator<T> it = getSupportedCallerPackages().iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (ArraysKt___ArraysKt.contains(packagesForUid, t)) {
                break;
            }
        }
        if (t != null) {
            return true;
        }
        return false;
    }

    public void binderDied() {
        Log.w("Columbus/ServiceAction", "Binder died");
        this.columbusServiceGestureListener = null;
        updateAvailable();
    }

    /* compiled from: ServiceAction.kt */
    private final class ColumbusServiceConnection implements ServiceConnection {
        final /* synthetic */ ServiceAction this$0;

        /* JADX WARN: Incorrect args count in method signature: ()V */
        public ColumbusServiceConnection(ServiceAction serviceAction) {
            Intrinsics.checkNotNullParameter(serviceAction, "this$0");
            this.this$0 = serviceAction;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.this$0.columbusService = IColumbusService.Stub.asInterface(iBinder);
            try {
                IColumbusService iColumbusService = this.this$0.columbusService;
                if (iColumbusService != null) {
                    iColumbusService.registerServiceListener(this.this$0.token, this.this$0.columbusServiceListener);
                }
            } catch (RemoteException e) {
                Log.e("Columbus/ServiceAction", "Error registering listener", e);
            }
            this.this$0.onServiceConnected();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.this$0.columbusService = null;
            this.this$0.onServiceDisconnected();
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: ServiceAction.kt */
    public final class ColumbusServiceListener extends IColumbusServiceListener.Stub {
        final /* synthetic */ ServiceAction this$0;

        /* JADX WARN: Incorrect args count in method signature: ()V */
        public ColumbusServiceListener(ServiceAction serviceAction) {
            Intrinsics.checkNotNullParameter(serviceAction, "this$0");
            this.this$0 = serviceAction;
        }

        @Override // com.google.android.systemui.columbus.IColumbusServiceListener
        public void setListener(IBinder iBinder, IBinder iBinder2) {
            if (this.this$0.checkSupportedCaller()) {
                if (iBinder2 != null || this.this$0.columbusServiceGestureListener != null) {
                    this.this$0.columbusServiceGestureListener = IColumbusServiceGestureListener.Stub.asInterface(iBinder2);
                    this.this$0.updateAvailable();
                    if (iBinder != null) {
                        try {
                            ServiceAction serviceAction = this.this$0;
                            if (iBinder2 == null) {
                                iBinder.unlinkToDeath(serviceAction, 0);
                            } else {
                                iBinder.linkToDeath(serviceAction, 0);
                            }
                        } catch (RemoteException e) {
                            Log.e("Columbus/ServiceAction", "RemoteException during linkToDeath", e);
                        } catch (NoSuchElementException e2) {
                            Log.e("Columbus/ServiceAction", "NoSuchElementException during linkToDeath", e2);
                        }
                    }
                }
            }
        }
    }

    /* compiled from: ServiceAction.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

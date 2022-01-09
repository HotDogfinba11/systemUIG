package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsProviderLifecycleManager.kt */
public final class ControlsProviderLifecycleManager implements IBinder.DeathRecipient {
    private static final int BIND_FLAGS = 67109121;
    public static final Companion Companion = new Companion(null);
    private final String TAG = ControlsProviderLifecycleManager.class.getSimpleName();
    private final IControlsActionCallback.Stub actionCallbackService;
    private int bindTryCount;
    private final ComponentName componentName;
    private final Context context;
    private final DelayableExecutor executor;
    private final Intent intent;
    private Runnable onLoadCanceller;
    @GuardedBy({"queuedServiceMethods"})
    private final Set<ServiceMethod> queuedServiceMethods = new ArraySet();
    private boolean requiresBound;
    private final ControlsProviderLifecycleManager$serviceConnection$1 serviceConnection;
    private final IBinder token = new Binder();
    private final UserHandle user;
    private ServiceWrapper wrapper;

    public ControlsProviderLifecycleManager(Context context2, DelayableExecutor delayableExecutor, IControlsActionCallback.Stub stub, UserHandle userHandle, ComponentName componentName2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(delayableExecutor, "executor");
        Intrinsics.checkNotNullParameter(stub, "actionCallbackService");
        Intrinsics.checkNotNullParameter(userHandle, "user");
        Intrinsics.checkNotNullParameter(componentName2, "componentName");
        this.context = context2;
        this.executor = delayableExecutor;
        this.actionCallbackService = stub;
        this.user = userHandle;
        this.componentName = componentName2;
        Intent intent2 = new Intent();
        intent2.setComponent(getComponentName());
        Bundle bundle = new Bundle();
        bundle.putBinder("CALLBACK_TOKEN", getToken());
        Unit unit = Unit.INSTANCE;
        intent2.putExtra("CALLBACK_BUNDLE", bundle);
        this.intent = intent2;
        this.serviceConnection = new ControlsProviderLifecycleManager$serviceConnection$1(this);
    }

    public final UserHandle getUser() {
        return this.user;
    }

    public final ComponentName getComponentName() {
        return this.componentName;
    }

    public final IBinder getToken() {
        return this.token;
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* access modifiers changed from: private */
    public final void bindService(boolean z) {
        this.executor.execute(new ControlsProviderLifecycleManager$bindService$1(this, z));
    }

    /* access modifiers changed from: private */
    public final void handlePendingServiceMethods() {
        ArraySet<ServiceMethod> arraySet;
        synchronized (this.queuedServiceMethods) {
            arraySet = new ArraySet(this.queuedServiceMethods);
            this.queuedServiceMethods.clear();
        }
        for (ServiceMethod serviceMethod : arraySet) {
            serviceMethod.run();
        }
    }

    public void binderDied() {
        if (this.wrapper != null) {
            this.wrapper = null;
            if (this.requiresBound) {
                Log.d(this.TAG, "binderDied");
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void queueServiceMethod(ServiceMethod serviceMethod) {
        synchronized (this.queuedServiceMethods) {
            this.queuedServiceMethods.add(serviceMethod);
        }
    }

    private final void invokeOrQueue(ServiceMethod serviceMethod) {
        Unit unit;
        if (this.wrapper == null) {
            unit = null;
        } else {
            serviceMethod.run();
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            queueServiceMethod(serviceMethod);
            bindService(true);
        }
    }

    public final void maybeBindAndLoad(IControlsSubscriber.Stub stub) {
        Intrinsics.checkNotNullParameter(stub, "subscriber");
        this.onLoadCanceller = this.executor.executeDelayed(new ControlsProviderLifecycleManager$maybeBindAndLoad$1(this, stub), 20, TimeUnit.SECONDS);
        invokeOrQueue(new Load(this, stub));
    }

    public final void maybeBindAndLoadSuggested(IControlsSubscriber.Stub stub) {
        Intrinsics.checkNotNullParameter(stub, "subscriber");
        this.onLoadCanceller = this.executor.executeDelayed(new ControlsProviderLifecycleManager$maybeBindAndLoadSuggested$1(this, stub), 20, TimeUnit.SECONDS);
        invokeOrQueue(new Suggest(this, stub));
    }

    public final void cancelLoadTimeout() {
        Runnable runnable = this.onLoadCanceller;
        if (runnable != null) {
            runnable.run();
        }
        this.onLoadCanceller = null;
    }

    public final void maybeBindAndSubscribe(List<String> list, IControlsSubscriber iControlsSubscriber) {
        Intrinsics.checkNotNullParameter(list, "controlIds");
        Intrinsics.checkNotNullParameter(iControlsSubscriber, "subscriber");
        invokeOrQueue(new Subscribe(this, list, iControlsSubscriber));
    }

    public final void maybeBindAndSendAction(String str, ControlAction controlAction) {
        Intrinsics.checkNotNullParameter(str, "controlId");
        Intrinsics.checkNotNullParameter(controlAction, "action");
        invokeOrQueue(new Action(this, str, controlAction));
    }

    public final void startSubscription(IControlsSubscription iControlsSubscription, long j) {
        Intrinsics.checkNotNullParameter(iControlsSubscription, "subscription");
        Log.d(this.TAG, Intrinsics.stringPlus("startSubscription: ", iControlsSubscription));
        ServiceWrapper serviceWrapper = this.wrapper;
        if (serviceWrapper != null) {
            serviceWrapper.request(iControlsSubscription, j);
        }
    }

    public final void cancelSubscription(IControlsSubscription iControlsSubscription) {
        Intrinsics.checkNotNullParameter(iControlsSubscription, "subscription");
        Log.d(this.TAG, Intrinsics.stringPlus("cancelSubscription: ", iControlsSubscription));
        ServiceWrapper serviceWrapper = this.wrapper;
        if (serviceWrapper != null) {
            serviceWrapper.cancel(iControlsSubscription);
        }
    }

    public final void unbindService() {
        Runnable runnable = this.onLoadCanceller;
        if (runnable != null) {
            runnable.run();
        }
        this.onLoadCanceller = null;
        bindService(false);
    }

    public String toString() {
        String str = "ControlsProviderLifecycleManager(" + Intrinsics.stringPlus("component=", getComponentName()) + Intrinsics.stringPlus(", user=", getUser()) + ")";
        Intrinsics.checkNotNullExpressionValue(str, "StringBuilder(\"ControlsProviderLifecycleManager(\").apply {\n            append(\"component=$componentName\")\n            append(\", user=$user\")\n            append(\")\")\n        }.toString()");
        return str;
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public abstract class ServiceMethod {
        final /* synthetic */ ControlsProviderLifecycleManager this$0;

        public abstract boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core();

        /* JADX WARN: Incorrect args count in method signature: ()V */
        public ServiceMethod(ControlsProviderLifecycleManager controlsProviderLifecycleManager) {
            Intrinsics.checkNotNullParameter(controlsProviderLifecycleManager, "this$0");
            this.this$0 = controlsProviderLifecycleManager;
        }

        public final void run() {
            if (!callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                this.this$0.queueServiceMethod(this);
                this.this$0.binderDied();
            }
        }
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public final class Load extends ServiceMethod {
        private final IControlsSubscriber.Stub subscriber;
        final /* synthetic */ ControlsProviderLifecycleManager this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Load(ControlsProviderLifecycleManager controlsProviderLifecycleManager, IControlsSubscriber.Stub stub) {
            super(controlsProviderLifecycleManager);
            Intrinsics.checkNotNullParameter(controlsProviderLifecycleManager, "this$0");
            Intrinsics.checkNotNullParameter(stub, "subscriber");
            this.this$0 = controlsProviderLifecycleManager;
            this.subscriber = stub;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            Log.d(this.this$0.TAG, Intrinsics.stringPlus("load ", this.this$0.getComponentName()));
            ServiceWrapper serviceWrapper = this.this$0.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            return serviceWrapper.load(this.subscriber);
        }
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public final class Suggest extends ServiceMethod {
        private final IControlsSubscriber.Stub subscriber;
        final /* synthetic */ ControlsProviderLifecycleManager this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Suggest(ControlsProviderLifecycleManager controlsProviderLifecycleManager, IControlsSubscriber.Stub stub) {
            super(controlsProviderLifecycleManager);
            Intrinsics.checkNotNullParameter(controlsProviderLifecycleManager, "this$0");
            Intrinsics.checkNotNullParameter(stub, "subscriber");
            this.this$0 = controlsProviderLifecycleManager;
            this.subscriber = stub;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            Log.d(this.this$0.TAG, Intrinsics.stringPlus("suggest ", this.this$0.getComponentName()));
            ServiceWrapper serviceWrapper = this.this$0.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            return serviceWrapper.loadSuggested(this.subscriber);
        }
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public final class Subscribe extends ServiceMethod {
        private final List<String> list;
        private final IControlsSubscriber subscriber;
        final /* synthetic */ ControlsProviderLifecycleManager this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Subscribe(ControlsProviderLifecycleManager controlsProviderLifecycleManager, List<String> list2, IControlsSubscriber iControlsSubscriber) {
            super(controlsProviderLifecycleManager);
            Intrinsics.checkNotNullParameter(controlsProviderLifecycleManager, "this$0");
            Intrinsics.checkNotNullParameter(list2, "list");
            Intrinsics.checkNotNullParameter(iControlsSubscriber, "subscriber");
            this.this$0 = controlsProviderLifecycleManager;
            this.list = list2;
            this.subscriber = iControlsSubscriber;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            String str = this.this$0.TAG;
            Log.d(str, "subscribe " + this.this$0.getComponentName() + " - " + this.list);
            ServiceWrapper serviceWrapper = this.this$0.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            return serviceWrapper.subscribe(this.list, this.subscriber);
        }
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public final class Action extends ServiceMethod {
        private final ControlAction action;
        private final String id;
        final /* synthetic */ ControlsProviderLifecycleManager this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Action(ControlsProviderLifecycleManager controlsProviderLifecycleManager, String str, ControlAction controlAction) {
            super(controlsProviderLifecycleManager);
            Intrinsics.checkNotNullParameter(controlsProviderLifecycleManager, "this$0");
            Intrinsics.checkNotNullParameter(str, "id");
            Intrinsics.checkNotNullParameter(controlAction, "action");
            this.this$0 = controlsProviderLifecycleManager;
            this.id = str;
            this.action = controlAction;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            String str = this.this$0.TAG;
            Log.d(str, "onAction " + this.this$0.getComponentName() + " - " + this.id);
            ServiceWrapper serviceWrapper = this.this$0.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            return serviceWrapper.action(this.id, this.action, this.this$0.actionCallbackService);
        }
    }
}

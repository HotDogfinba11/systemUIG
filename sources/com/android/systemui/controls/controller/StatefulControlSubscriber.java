package com.android.systemui.controls.controller;

import android.os.IBinder;
import android.service.controls.Control;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import com.android.systemui.util.concurrency.DelayableExecutor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StatefulControlSubscriber.kt */
public final class StatefulControlSubscriber extends IControlsSubscriber.Stub {
    public static final Companion Companion = new Companion(null);
    private final DelayableExecutor bgExecutor;
    private final ControlsController controller;
    private final ControlsProviderLifecycleManager provider;
    private final long requestLimit;
    private IControlsSubscription subscription;
    private boolean subscriptionOpen;

    public StatefulControlSubscriber(ControlsController controlsController, ControlsProviderLifecycleManager controlsProviderLifecycleManager, DelayableExecutor delayableExecutor, long j) {
        Intrinsics.checkNotNullParameter(controlsController, "controller");
        Intrinsics.checkNotNullParameter(controlsProviderLifecycleManager, "provider");
        Intrinsics.checkNotNullParameter(delayableExecutor, "bgExecutor");
        this.controller = controlsController;
        this.provider = controlsProviderLifecycleManager;
        this.bgExecutor = delayableExecutor;
        this.requestLimit = j;
    }

    /* compiled from: StatefulControlSubscriber.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private final void run(IBinder iBinder, Function0<Unit> function0) {
        if (Intrinsics.areEqual(this.provider.getToken(), iBinder)) {
            this.bgExecutor.execute(new StatefulControlSubscriber$run$1(function0));
        }
    }

    public void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription) {
        Intrinsics.checkNotNullParameter(iBinder, "token");
        Intrinsics.checkNotNullParameter(iControlsSubscription, "subs");
        run(iBinder, new StatefulControlSubscriber$onSubscribe$1(this, iControlsSubscription));
    }

    public void onNext(IBinder iBinder, Control control) {
        Intrinsics.checkNotNullParameter(iBinder, "token");
        Intrinsics.checkNotNullParameter(control, "control");
        run(iBinder, new StatefulControlSubscriber$onNext$1(this, iBinder, control));
    }

    public void onError(IBinder iBinder, String str) {
        Intrinsics.checkNotNullParameter(iBinder, "token");
        Intrinsics.checkNotNullParameter(str, "error");
        run(iBinder, new StatefulControlSubscriber$onError$1(this, str));
    }

    public void onComplete(IBinder iBinder) {
        Intrinsics.checkNotNullParameter(iBinder, "token");
        run(iBinder, new StatefulControlSubscriber$onComplete$1(this));
    }

    public final void cancel() {
        if (this.subscriptionOpen) {
            this.bgExecutor.execute(new StatefulControlSubscriber$cancel$1(this));
        }
    }
}

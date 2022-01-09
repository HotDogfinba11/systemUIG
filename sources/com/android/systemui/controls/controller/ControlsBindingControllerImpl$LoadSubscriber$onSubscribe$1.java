package com.android.systemui.controls.controller;

import android.service.controls.IControlsSubscription;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ControlsBindingControllerImpl.kt */
final class ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ ControlsBindingControllerImpl this$0;
    final /* synthetic */ ControlsBindingControllerImpl.LoadSubscriber this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1(ControlsBindingControllerImpl controlsBindingControllerImpl, ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
        super(0);
        this.this$0 = controlsBindingControllerImpl;
        this.this$1 = loadSubscriber;
    }

    @Override // kotlin.jvm.functions.Function0
    public final void invoke() {
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.this$0.currentProvider;
        if (controlsProviderLifecycleManager != null) {
            IControlsSubscription iControlsSubscription = this.this$1.subscription;
            if (iControlsSubscription != null) {
                controlsProviderLifecycleManager.cancelSubscription(iControlsSubscription);
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("subscription");
                throw null;
            }
        }
    }
}

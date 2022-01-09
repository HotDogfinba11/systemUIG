package com.android.systemui.controls.management;

import android.content.pm.ServiceInfo;
import android.util.Log;
import com.android.settingslib.applications.ServiceListing;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsListingControllerImpl.kt */
final class ControlsListingControllerImpl$serviceListingCallback$1 implements ServiceListing.Callback {
    final /* synthetic */ ControlsListingControllerImpl this$0;

    ControlsListingControllerImpl$serviceListingCallback$1(ControlsListingControllerImpl controlsListingControllerImpl) {
        this.this$0 = controlsListingControllerImpl;
    }

    @Override // com.android.settingslib.applications.ServiceListing.Callback
    public final void onServicesReloaded(List<ServiceInfo> list) {
        Intrinsics.checkNotNullExpressionValue(list, "it");
        final List<ServiceInfo> list2 = CollectionsKt___CollectionsKt.toList(list);
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (ServiceInfo serviceInfo : list2) {
            linkedHashSet.add(serviceInfo.getComponentName());
        }
        Executor executor = this.this$0.backgroundExecutor;
        final ControlsListingControllerImpl controlsListingControllerImpl = this.this$0;
        executor.execute(new Runnable() {
            /* class com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.AnonymousClass1 */

            public final void run() {
                if (ControlsListingControllerImpl.access$getUserChangeInProgress$p(controlsListingControllerImpl).get() <= 0 && !linkedHashSet.equals(controlsListingControllerImpl.availableComponents)) {
                    Log.d("ControlsListingControllerImpl", Intrinsics.stringPlus("ServiceConfig reloaded, count: ", Integer.valueOf(linkedHashSet.size())));
                    controlsListingControllerImpl.availableComponents = linkedHashSet;
                    controlsListingControllerImpl.availableServices = list2;
                    List<ControlsServiceInfo> currentServices = controlsListingControllerImpl.getCurrentServices();
                    for (ControlsListingController.ControlsListingCallback controlsListingCallback : controlsListingControllerImpl.callbacks) {
                        controlsListingCallback.onServicesUpdated(currentServices);
                    }
                }
            }
        });
    }
}

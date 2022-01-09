package com.android.systemui.statusbar.policy;

import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.controls.controller.SeedResponse;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: DeviceControlsControllerImpl.kt */
public final class DeviceControlsControllerImpl$seedFavorites$2 implements Consumer<SeedResponse> {
    final /* synthetic */ SharedPreferences $prefs;
    final /* synthetic */ DeviceControlsControllerImpl this$0;

    DeviceControlsControllerImpl$seedFavorites$2(DeviceControlsControllerImpl deviceControlsControllerImpl, SharedPreferences sharedPreferences) {
        this.this$0 = deviceControlsControllerImpl;
        this.$prefs = sharedPreferences;
    }

    public final void accept(SeedResponse seedResponse) {
        Intrinsics.checkNotNullParameter(seedResponse, "response");
        Log.d("DeviceControlsControllerImpl", Intrinsics.stringPlus("Controls seeded: ", seedResponse));
        if (seedResponse.getAccepted()) {
            DeviceControlsControllerImpl deviceControlsControllerImpl = this.this$0;
            SharedPreferences sharedPreferences = this.$prefs;
            Intrinsics.checkNotNullExpressionValue(sharedPreferences, "prefs");
            deviceControlsControllerImpl.addPackageToSeededSet(sharedPreferences, seedResponse.getPackageName());
            if (this.this$0.getPosition$frameworks__base__packages__SystemUI__android_common__SystemUI_core() == null) {
                this.this$0.setPosition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(7);
            }
            this.this$0.fireControlsUpdate();
            Optional<ControlsListingController> controlsListingController = this.this$0.controlsComponent.getControlsListingController();
            final DeviceControlsControllerImpl deviceControlsControllerImpl2 = this.this$0;
            controlsListingController.ifPresent(new Consumer<ControlsListingController>() {
                /* class com.android.systemui.statusbar.policy.DeviceControlsControllerImpl$seedFavorites$2.AnonymousClass1 */

                public final void accept(ControlsListingController controlsListingController) {
                    Intrinsics.checkNotNullParameter(controlsListingController, "it");
                    controlsListingController.removeCallback(deviceControlsControllerImpl2.listingCallback);
                }
            });
        }
    }
}

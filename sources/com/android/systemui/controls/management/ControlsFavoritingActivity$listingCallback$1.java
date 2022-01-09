package com.android.systemui.controls.management;

import android.view.View;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$listingCallback$1 implements ControlsListingController.ControlsListingCallback {
    final /* synthetic */ ControlsFavoritingActivity this$0;

    ControlsFavoritingActivity$listingCallback$1(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
    public void onServicesUpdated(List<ControlsServiceInfo> list) {
        Intrinsics.checkNotNullParameter(list, "serviceInfos");
        if (list.size() > 1) {
            View view = this.this$0.otherAppsButton;
            if (view != null) {
                view.post(new ControlsFavoritingActivity$listingCallback$1$onServicesUpdated$1(this.this$0));
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("otherAppsButton");
                throw null;
            }
        }
    }
}

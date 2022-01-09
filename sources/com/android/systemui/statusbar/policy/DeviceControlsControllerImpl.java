package com.android.systemui.statusbar.policy;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.R$array;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.policy.DeviceControlsController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeviceControlsControllerImpl.kt */
public final class DeviceControlsControllerImpl implements DeviceControlsController {
    public static final Companion Companion = new Companion(null);
    private DeviceControlsController.Callback callback;
    private final Context context;
    private final ControlsComponent controlsComponent;
    private final DeviceControlsControllerImpl$listingCallback$1 listingCallback = new DeviceControlsControllerImpl$listingCallback$1(this);
    private Integer position;
    private final SecureSettings secureSettings;
    private final UserContextProvider userContextProvider;

    public DeviceControlsControllerImpl(Context context2, ControlsComponent controlsComponent2, UserContextProvider userContextProvider2, SecureSettings secureSettings2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(controlsComponent2, "controlsComponent");
        Intrinsics.checkNotNullParameter(userContextProvider2, "userContextProvider");
        Intrinsics.checkNotNullParameter(secureSettings2, "secureSettings");
        this.context = context2;
        this.controlsComponent = controlsComponent2;
        this.userContextProvider = userContextProvider2;
        this.secureSettings = secureSettings2;
    }

    public final Integer getPosition$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.position;
    }

    public final void setPosition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(Integer num) {
        this.position = num;
    }

    /* compiled from: DeviceControlsControllerImpl.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private final void checkMigrationToQs() {
        this.controlsComponent.getControlsController().ifPresent(new DeviceControlsControllerImpl$checkMigrationToQs$1(this));
    }

    @Override // com.android.systemui.statusbar.policy.DeviceControlsController
    public void setCallback(DeviceControlsController.Callback callback2) {
        Intrinsics.checkNotNullParameter(callback2, "callback");
        removeCallback();
        this.callback = callback2;
        if (this.secureSettings.getInt("controls_enabled", 1) == 0) {
            fireControlsUpdate();
            return;
        }
        checkMigrationToQs();
        this.controlsComponent.getControlsListingController().ifPresent(new DeviceControlsControllerImpl$setCallback$1(this));
    }

    @Override // com.android.systemui.statusbar.policy.DeviceControlsController
    public void removeCallback() {
        this.position = null;
        this.callback = null;
        this.controlsComponent.getControlsListingController().ifPresent(new DeviceControlsControllerImpl$removeCallback$1(this));
    }

    /* access modifiers changed from: private */
    public final void fireControlsUpdate() {
        Log.i("DeviceControlsControllerImpl", Intrinsics.stringPlus("Setting DeviceControlsTile position: ", this.position));
        DeviceControlsController.Callback callback2 = this.callback;
        if (callback2 != null) {
            callback2.onControlsUpdate(this.position);
        }
    }

    /* access modifiers changed from: private */
    public final void seedFavorites(List<ControlsServiceInfo> list) {
        String[] stringArray = this.context.getResources().getStringArray(R$array.config_controlsPreferredPackages);
        SharedPreferences sharedPreferences = this.userContextProvider.getUserContext().getSharedPreferences("controls_prefs", 0);
        Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", SetsKt__SetsKt.emptySet());
        ControlsController controlsController = this.controlsComponent.getControlsController().get();
        Intrinsics.checkNotNullExpressionValue(controlsController, "controlsComponent.getControlsController().get()");
        ControlsController controlsController2 = controlsController;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < Math.min(2, stringArray.length); i++) {
            String str = stringArray[i];
            for (T t : list) {
                if (str.equals(t.componentName.getPackageName()) && !stringSet.contains(str)) {
                    ComponentName componentName = t.componentName;
                    Intrinsics.checkNotNullExpressionValue(componentName, "it.componentName");
                    if (controlsController2.countFavoritesForComponent(componentName) > 0) {
                        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "prefs");
                        Intrinsics.checkNotNullExpressionValue(str, "pkg");
                        addPackageToSeededSet(sharedPreferences, str);
                    } else {
                        ComponentName componentName2 = t.componentName;
                        Intrinsics.checkNotNullExpressionValue(componentName2, "it.componentName");
                        arrayList.add(componentName2);
                    }
                }
            }
        }
        if (!arrayList.isEmpty()) {
            controlsController2.seedFavoritesForComponents(arrayList, new DeviceControlsControllerImpl$seedFavorites$2(this, sharedPreferences));
        }
    }

    /* access modifiers changed from: private */
    public final void addPackageToSeededSet(SharedPreferences sharedPreferences, String str) {
        Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", SetsKt__SetsKt.emptySet());
        Intrinsics.checkNotNullExpressionValue(stringSet, "seededPackages");
        Set<String> set = CollectionsKt___CollectionsKt.toMutableSet(stringSet);
        set.add(str);
        sharedPreferences.edit().putStringSet("SeedingCompleted", set).apply();
    }
}

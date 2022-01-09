package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.controls.ControlsServiceInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsControllerImpl.kt */
final class ControlsControllerImpl$listingCallback$1$onServicesUpdated$1 implements Runnable {
    final /* synthetic */ List<ControlsServiceInfo> $serviceInfos;
    final /* synthetic */ ControlsControllerImpl this$0;

    ControlsControllerImpl$listingCallback$1$onServicesUpdated$1(List<ControlsServiceInfo> list, ControlsControllerImpl controlsControllerImpl) {
        this.$serviceInfos = list;
        this.this$0 = controlsControllerImpl;
    }

    public final void run() {
        List<ControlsServiceInfo> list = this.$serviceInfos;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().componentName);
        }
        Set<ComponentName> set = CollectionsKt___CollectionsKt.toSet(arrayList);
        List<StructureInfo> allStructures = Favorites.INSTANCE.getAllStructures();
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(allStructures, 10));
        Iterator<T> it2 = allStructures.iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().getComponentName());
        }
        Set set2 = CollectionsKt___CollectionsKt.toSet(arrayList2);
        boolean z = false;
        SharedPreferences sharedPreferences = this.this$0.userStructure.getUserContext().getSharedPreferences("controls_prefs", 0);
        Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", new LinkedHashSet());
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
        for (ComponentName componentName : set) {
            arrayList3.add(componentName.getPackageName());
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        Intrinsics.checkNotNullExpressionValue(stringSet, "completedSeedingPackageSet");
        edit.putStringSet("SeedingCompleted", CollectionsKt___CollectionsKt.intersect(stringSet, arrayList3)).apply();
        Set<ComponentName> set3 = CollectionsKt___CollectionsKt.subtract(set2, set);
        ControlsControllerImpl controlsControllerImpl = this.this$0;
        for (ComponentName componentName2 : set3) {
            Favorites favorites = Favorites.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(componentName2, "it");
            favorites.removeStructures(componentName2);
            controlsControllerImpl.bindingController.onComponentRemoved(componentName2);
            z = true;
        }
        if (!this.this$0.getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getFavorites().isEmpty()) {
            Set<ComponentName> set4 = CollectionsKt___CollectionsKt.subtract(set, set2);
            ControlsControllerImpl controlsControllerImpl2 = this.this$0;
            for (ComponentName componentName3 : set4) {
                AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core = controlsControllerImpl2.getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                Intrinsics.checkNotNullExpressionValue(componentName3, "it");
                List<StructureInfo> cachedFavoritesAndRemoveFor = auxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core.getCachedFavoritesAndRemoveFor(componentName3);
                if (!cachedFavoritesAndRemoveFor.isEmpty()) {
                    Iterator<T> it3 = cachedFavoritesAndRemoveFor.iterator();
                    while (it3.hasNext()) {
                        Favorites.INSTANCE.replaceControls(it3.next());
                    }
                    z = true;
                }
            }
            Set<ComponentName> set5 = CollectionsKt___CollectionsKt.intersect(set, set2);
            ControlsControllerImpl controlsControllerImpl3 = this.this$0;
            for (ComponentName componentName4 : set5) {
                AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 = controlsControllerImpl3.getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                Intrinsics.checkNotNullExpressionValue(componentName4, "it");
                auxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core2.getCachedFavoritesAndRemoveFor(componentName4);
            }
        }
        if (z) {
            Log.d("ControlsControllerImpl", "Detected change in available services, storing updated favorites");
            this.this$0.persistenceWrapper.storeFavorites(Favorites.INSTANCE.getAllStructures());
        }
    }
}

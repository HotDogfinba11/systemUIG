package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.service.controls.Control;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* access modifiers changed from: package-private */
/* compiled from: ControlsControllerImpl.kt */
public final class Favorites {
    public static final Favorites INSTANCE = new Favorites();
    private static Map<ComponentName, ? extends List<StructureInfo>> favMap = MapsKt__MapsKt.emptyMap();

    private Favorites() {
    }

    public final List<StructureInfo> getAllStructures() {
        Map<ComponentName, ? extends List<StructureInfo>> map = favMap;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<ComponentName, ? extends List<StructureInfo>> entry : map.entrySet()) {
            boolean unused = CollectionsKt__MutableCollectionsKt.addAll(arrayList, (List) entry.getValue());
        }
        return arrayList;
    }

    public final List<StructureInfo> getStructuresForComponent(ComponentName componentName) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        List<StructureInfo> list = (List) favMap.get(componentName);
        return list == null ? CollectionsKt__CollectionsKt.emptyList() : list;
    }

    public final List<ControlInfo> getControlsForStructure(StructureInfo structureInfo) {
        List<ControlInfo> list;
        T t;
        Intrinsics.checkNotNullParameter(structureInfo, "structure");
        Iterator<T> it = getStructuresForComponent(structureInfo.getComponentName()).iterator();
        while (true) {
            list = null;
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (Intrinsics.areEqual(t.getStructure(), structureInfo.getStructure())) {
                break;
            }
        }
        T t2 = t;
        if (t2 != null) {
            list = t2.getControls();
        }
        return list == null ? CollectionsKt__CollectionsKt.emptyList() : list;
    }

    public final List<ControlInfo> getControlsForComponent(ComponentName componentName) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        List<StructureInfo> structuresForComponent = getStructuresForComponent(componentName);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = structuresForComponent.iterator();
        while (it.hasNext()) {
            boolean unused = CollectionsKt__MutableCollectionsKt.addAll(arrayList, it.next().getControls());
        }
        return arrayList;
    }

    public final void removeStructures(ComponentName componentName) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Map<ComponentName, ? extends List<StructureInfo>> map = MapsKt__MapsKt.toMutableMap(favMap);
        map.remove(componentName);
        favMap = map;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v3, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0042 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean addFavorite(android.content.ComponentName r10, java.lang.CharSequence r11, com.android.systemui.controls.controller.ControlInfo r12) {
        /*
        // Method dump skipped, instructions count: 141
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.controller.Favorites.addFavorite(android.content.ComponentName, java.lang.CharSequence, com.android.systemui.controls.controller.ControlInfo):boolean");
    }

    public final void replaceControls(StructureInfo structureInfo) {
        Intrinsics.checkNotNullParameter(structureInfo, "updatedStructure");
        Map<ComponentName, ? extends List<StructureInfo>> map = MapsKt__MapsKt.toMutableMap(favMap);
        ArrayList arrayList = new ArrayList();
        ComponentName componentName = structureInfo.getComponentName();
        Iterator<T> it = getStructuresForComponent(componentName).iterator();
        boolean z = false;
        while (it.hasNext()) {
            T next = it.next();
            if (Intrinsics.areEqual(next.getStructure(), structureInfo.getStructure())) {
                z = true;
                next = structureInfo;
            }
            if (!next.getControls().isEmpty()) {
                arrayList.add(next);
            }
        }
        if (!z && !structureInfo.getControls().isEmpty()) {
            arrayList.add(structureInfo);
        }
        map.put(componentName, arrayList);
        favMap = map;
    }

    public final void clear() {
        favMap = MapsKt__MapsKt.emptyMap();
    }

    public final boolean updateControls(ComponentName componentName, List<Control> list) {
        Pair pair;
        boolean z;
        ControlInfo controlInfo;
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(list, "controls");
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10)), 16));
        for (T t : list) {
            linkedHashMap.put(t.getControlId(), t);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        boolean z2 = false;
        for (T t2 : getStructuresForComponent(componentName)) {
            for (T t3 : t2.getControls()) {
                Control control = (Control) linkedHashMap.get(t3.getControlId());
                if (control == null) {
                    pair = null;
                } else {
                    if (!Intrinsics.areEqual(control.getTitle(), t3.getControlTitle()) || !Intrinsics.areEqual(control.getSubtitle(), t3.getControlSubtitle()) || control.getDeviceType() != t3.getDeviceType()) {
                        CharSequence title = control.getTitle();
                        Intrinsics.checkNotNullExpressionValue(title, "updatedControl.title");
                        CharSequence subtitle = control.getSubtitle();
                        Intrinsics.checkNotNullExpressionValue(subtitle, "updatedControl.subtitle");
                        controlInfo = ControlInfo.copy$default(t3, null, title, subtitle, control.getDeviceType(), 1, null);
                        z = true;
                    } else {
                        z = z2;
                        controlInfo = t3;
                    }
                    Object structure = control.getStructure();
                    if (structure == null) {
                        structure = "";
                    }
                    if (!Intrinsics.areEqual(t2.getStructure(), structure)) {
                        z = true;
                    }
                    Pair pair2 = new Pair(structure, controlInfo);
                    z2 = z;
                    pair = pair2;
                }
                if (pair == null) {
                    pair = new Pair(t2.getStructure(), t3);
                }
                CharSequence charSequence = (CharSequence) pair.component1();
                ControlInfo controlInfo2 = (ControlInfo) pair.component2();
                Object obj = linkedHashMap2.get(charSequence);
                if (obj == null) {
                    obj = new ArrayList();
                    linkedHashMap2.put(charSequence, obj);
                }
                ((List) obj).add(controlInfo2);
            }
        }
        if (!z2) {
            return false;
        }
        ArrayList arrayList = new ArrayList(linkedHashMap2.size());
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            arrayList.add(new StructureInfo(componentName, (CharSequence) entry.getKey(), (List) entry.getValue()));
        }
        Map<ComponentName, ? extends List<StructureInfo>> map = MapsKt__MapsKt.toMutableMap(favMap);
        map.put(componentName, arrayList);
        favMap = map;
        return true;
    }

    public final void load(List<StructureInfo> list) {
        Intrinsics.checkNotNullParameter(list, "structures");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T t : list) {
            ComponentName componentName = t.getComponentName();
            Object obj = linkedHashMap.get(componentName);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(componentName, obj);
            }
            ((List) obj).add(t);
        }
        favMap = linkedHashMap;
    }
}

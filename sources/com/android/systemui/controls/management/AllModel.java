package com.android.systemui.controls.management;

import android.service.controls.Control;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.ControlsModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: AllModel.kt */
public final class AllModel implements ControlsModel {
    private final List<ControlStatus> controls;
    private final ControlsModel.ControlsModelCallback controlsModelCallback;
    private final List<ElementWrapper> elements;
    private final CharSequence emptyZoneString;
    private final List<String> favoriteIds;
    private boolean modified;
    private final Void moveHelper;

    public AllModel(List<ControlStatus> list, List<String> list2, CharSequence charSequence, ControlsModel.ControlsModelCallback controlsModelCallback2) {
        Intrinsics.checkNotNullParameter(list, "controls");
        Intrinsics.checkNotNullParameter(list2, "initialFavoriteIds");
        Intrinsics.checkNotNullParameter(charSequence, "emptyZoneString");
        Intrinsics.checkNotNullParameter(controlsModelCallback2, "controlsModelCallback");
        this.controls = list;
        this.emptyZoneString = charSequence;
        this.controlsModelCallback = controlsModelCallback2;
        HashSet hashSet = new HashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getControl().getControlId());
        }
        ArrayList arrayList = new ArrayList();
        for (T t : list2) {
            if (hashSet.contains(t)) {
                arrayList.add(t);
            }
        }
        this.favoriteIds = CollectionsKt___CollectionsKt.toMutableList((Collection) arrayList);
        this.elements = createWrappers(this.controls);
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public Void getMoveHelper() {
        return this.moveHelper;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public List<ControlInfo> getFavorites() {
        ControlInfo controlInfo;
        T t;
        List<String> list = this.favoriteIds;
        ArrayList arrayList = new ArrayList();
        for (T t2 : list) {
            Iterator<T> it = this.controls.iterator();
            while (true) {
                controlInfo = null;
                if (!it.hasNext()) {
                    t = null;
                    break;
                }
                t = it.next();
                if (Intrinsics.areEqual(t.getControl().getControlId(), t2)) {
                    break;
                }
            }
            T t3 = t;
            Control control = t3 == null ? null : t3.getControl();
            if (control != null) {
                controlInfo = ControlInfo.Companion.fromControl(control);
            }
            if (controlInfo != null) {
                arrayList.add(controlInfo);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public List<ElementWrapper> getElements() {
        return this.elements;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public void changeFavoriteStatus(String str, boolean z) {
        Boolean bool;
        T t;
        boolean z2;
        ControlStatus controlStatus;
        boolean z3;
        Intrinsics.checkNotNullParameter(str, "controlId");
        Iterator<T> it = getElements().iterator();
        while (true) {
            bool = null;
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            T t2 = t;
            if (!(t2 instanceof ControlStatusWrapper) || !Intrinsics.areEqual(t2.getControlStatus().getControl().getControlId(), str)) {
                z3 = false;
                continue;
            } else {
                z3 = true;
                continue;
            }
            if (z3) {
                break;
            }
        }
        T t3 = t;
        Boolean valueOf = Boolean.valueOf(z);
        if (!(t3 == null || (controlStatus = t3.getControlStatus()) == null)) {
            bool = Boolean.valueOf(controlStatus.getFavorite());
        }
        if (!Intrinsics.areEqual(valueOf, bool)) {
            if (z) {
                z2 = this.favoriteIds.add(str);
            } else {
                z2 = this.favoriteIds.remove(str);
            }
            if (z2 && !this.modified) {
                this.modified = true;
                this.controlsModelCallback.onFirstChange();
            }
            if (t3 != null) {
                t3.getControlStatus().setFavorite(z);
            }
        }
    }

    private final List<ElementWrapper> createWrappers(List<ControlStatus> list) {
        OrderedMap orderedMap = new OrderedMap(new ArrayMap());
        for (T t : list) {
            Object zone = t.getControl().getZone();
            if (zone == null) {
                zone = "";
            }
            Object obj = orderedMap.get(zone);
            if (obj == null) {
                obj = new ArrayList();
                orderedMap.put(zone, obj);
            }
            ((List) obj).add(t);
        }
        ArrayList arrayList = new ArrayList();
        Sequence sequence = null;
        for (CharSequence charSequence : orderedMap.getOrderedKeys()) {
            Object value = MapsKt.getValue(orderedMap, charSequence);
            Intrinsics.checkNotNullExpressionValue(value, "map.getValue(zoneName)");
            Sequence sequence2 = SequencesKt___SequencesKt.map(CollectionsKt___CollectionsKt.asSequence((Iterable) value), AllModel$createWrappers$values$1.INSTANCE);
            if (TextUtils.isEmpty(charSequence)) {
                sequence = sequence2;
            } else {
                Intrinsics.checkNotNullExpressionValue(charSequence, "zoneName");
                arrayList.add(new ZoneNameWrapper(charSequence));
                boolean unused = CollectionsKt__MutableCollectionsKt.addAll(arrayList, sequence2);
            }
        }
        if (sequence != null) {
            if (orderedMap.size() != 1) {
                arrayList.add(new ZoneNameWrapper(this.emptyZoneString));
            }
            boolean unused2 = CollectionsKt__MutableCollectionsKt.addAll(arrayList, sequence);
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* compiled from: AllModel.kt */
    public static final class OrderedMap<K, V> implements Map<K, V>, KMutableMap {
        private final Map<K, V> map;
        private final List<K> orderedKeys = new ArrayList();

        public boolean containsKey(Object obj) {
            return this.map.containsKey(obj);
        }

        public boolean containsValue(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // java.util.Map
        public V get(Object obj) {
            return this.map.get(obj);
        }

        public Set<Map.Entry<K, V>> getEntries() {
            return this.map.entrySet();
        }

        public Set<K> getKeys() {
            return this.map.keySet();
        }

        public int getSize() {
            return this.map.size();
        }

        public Collection<V> getValues() {
            return this.map.values();
        }

        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map2) {
            Intrinsics.checkNotNullParameter(map2, "from");
            this.map.putAll(map2);
        }

        public OrderedMap(Map<K, V> map2) {
            Intrinsics.checkNotNullParameter(map2, "map");
            this.map = map2;
        }

        @Override // java.util.Map
        public final /* bridge */ Set<Map.Entry<K, V>> entrySet() {
            return getEntries();
        }

        @Override // java.util.Map
        public final /* bridge */ Set<K> keySet() {
            return getKeys();
        }

        public final /* bridge */ int size() {
            return getSize();
        }

        @Override // java.util.Map
        public final /* bridge */ Collection<V> values() {
            return getValues();
        }

        public final List<K> getOrderedKeys() {
            return this.orderedKeys;
        }

        @Override // java.util.Map
        public V put(K k, V v) {
            if (!this.map.containsKey(k)) {
                this.orderedKeys.add(k);
            }
            return this.map.put(k, v);
        }

        public void clear() {
            this.orderedKeys.clear();
            this.map.clear();
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            V remove = this.map.remove(obj);
            if (remove != null) {
                this.orderedKeys.remove(obj);
            }
            return remove;
        }
    }
}

package com.android.systemui.controls.management;

import android.content.ComponentName;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.ControlsModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FavoritesModel.kt */
public final class FavoritesModel implements ControlsModel {
    public static final Companion Companion = new Companion(null);
    private RecyclerView.Adapter<?> adapter;
    private final ComponentName componentName;
    private final CustomIconCache customIconCache;
    private int dividerPosition;
    private final List<ElementWrapper> elements;
    private final FavoritesModelCallback favoritesModelCallback;
    private final ItemTouchHelper.SimpleCallback itemTouchHelperCallback;
    private boolean modified;
    private final ControlsModel.MoveHelper moveHelper = new FavoritesModel$moveHelper$1(this);

    /* compiled from: FavoritesModel.kt */
    public interface FavoritesModelCallback extends ControlsModel.ControlsModelCallback {
        void onNoneChanged(boolean z);
    }

    public FavoritesModel(CustomIconCache customIconCache2, ComponentName componentName2, List<ControlInfo> list, FavoritesModelCallback favoritesModelCallback2) {
        Intrinsics.checkNotNullParameter(customIconCache2, "customIconCache");
        Intrinsics.checkNotNullParameter(componentName2, "componentName");
        Intrinsics.checkNotNullParameter(list, "favorites");
        Intrinsics.checkNotNullParameter(favoritesModelCallback2, "favoritesModelCallback");
        this.customIconCache = customIconCache2;
        this.componentName = componentName2;
        this.favoritesModelCallback = favoritesModelCallback2;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ControlInfoWrapper(this.componentName, it.next(), true, new FavoritesModel$elements$1$1(this.customIconCache)));
        }
        this.elements = CollectionsKt___CollectionsKt.plus((Collection) arrayList, (Object) new DividerWrapper(false, false, 3, null));
        this.dividerPosition = getElements().size() - 1;
        this.itemTouchHelperCallback = new FavoritesModel$itemTouchHelperCallback$1(this);
    }

    /* compiled from: FavoritesModel.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public ControlsModel.MoveHelper getMoveHelper() {
        return this.moveHelper;
    }

    public void attachAdapter(RecyclerView.Adapter<?> adapter2) {
        Intrinsics.checkNotNullParameter(adapter2, "adapter");
        this.adapter = adapter2;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public List<ControlInfo> getFavorites() {
        List<ElementWrapper> list = CollectionsKt___CollectionsKt.take(getElements(), this.dividerPosition);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (ElementWrapper elementWrapper : list) {
            arrayList.add(((ControlInfoWrapper) elementWrapper).getControlInfo());
        }
        return arrayList;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public List<ElementWrapper> getElements() {
        return this.elements;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public void changeFavoriteStatus(String str, boolean z) {
        Intrinsics.checkNotNullParameter(str, "controlId");
        Iterator<ElementWrapper> it = getElements().iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            ElementWrapper next = it.next();
            if ((next instanceof ControlInterface) && Intrinsics.areEqual(((ControlInterface) next).getControlId(), str)) {
                break;
            }
            i++;
        }
        if (i != -1) {
            int i2 = this.dividerPosition;
            if (i < i2 && z) {
                return;
            }
            if (i > i2 && !z) {
                return;
            }
            if (z) {
                onMoveItemInternal(i, i2);
            } else {
                onMoveItemInternal(i, getElements().size() - 1);
            }
        }
    }

    public void onMoveItem(int i, int i2) {
        onMoveItemInternal(i, i2);
    }

    private final void updateDividerNone(int i, boolean z) {
        ((DividerWrapper) getElements().get(i)).setShowNone(z);
        this.favoritesModelCallback.onNoneChanged(z);
    }

    private final void updateDividerShow(int i, boolean z) {
        ((DividerWrapper) getElements().get(i)).setShowDivider(z);
    }

    private final void onMoveItemInternal(int i, int i2) {
        RecyclerView.Adapter<?> adapter2;
        int i3 = this.dividerPosition;
        if (i != i3) {
            boolean z = false;
            if ((i < i3 && i2 >= i3) || (i > i3 && i2 <= i3)) {
                if (i < i3 && i2 >= i3) {
                    ((ControlInfoWrapper) getElements().get(i)).setFavorite(false);
                } else if (i > i3 && i2 <= i3) {
                    ((ControlInfoWrapper) getElements().get(i)).setFavorite(true);
                }
                updateDivider(i, i2);
                z = true;
            }
            moveElement(i, i2);
            RecyclerView.Adapter<?> adapter3 = this.adapter;
            if (adapter3 != null) {
                adapter3.notifyItemMoved(i, i2);
            }
            if (z && (adapter2 = this.adapter) != null) {
                adapter2.notifyItemChanged(i2, new Object());
            }
            if (!this.modified) {
                this.modified = true;
                this.favoritesModelCallback.onFirstChange();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void updateDivider(int r5, int r6) {
        /*
            r4 = this;
            int r0 = r4.dividerPosition
            r1 = 0
            r2 = 1
            if (r5 >= r0) goto L_0x0025
            if (r6 < r0) goto L_0x0025
            int r5 = r0 + -1
            r4.dividerPosition = r5
            if (r5 != 0) goto L_0x0012
            r4.updateDividerNone(r0, r2)
            r1 = r2
        L_0x0012:
            int r5 = r4.dividerPosition
            java.util.List r6 = r4.getElements()
            int r6 = r6.size()
            int r6 = r6 + -2
            if (r5 != r6) goto L_0x0047
            r4.updateDividerShow(r0, r2)
        L_0x0023:
            r1 = r2
            goto L_0x0047
        L_0x0025:
            if (r5 <= r0) goto L_0x0047
            if (r6 > r0) goto L_0x0047
            int r5 = r0 + 1
            r4.dividerPosition = r5
            if (r5 != r2) goto L_0x0034
            r4.updateDividerNone(r0, r1)
            r5 = r2
            goto L_0x0035
        L_0x0034:
            r5 = r1
        L_0x0035:
            int r6 = r4.dividerPosition
            java.util.List r3 = r4.getElements()
            int r3 = r3.size()
            int r3 = r3 - r2
            if (r6 != r3) goto L_0x0046
            r4.updateDividerShow(r0, r1)
            goto L_0x0023
        L_0x0046:
            r1 = r5
        L_0x0047:
            if (r1 == 0) goto L_0x0051
            androidx.recyclerview.widget.RecyclerView$Adapter<?> r4 = r4.adapter
            if (r4 != 0) goto L_0x004e
            goto L_0x0051
        L_0x004e:
            r4.notifyItemChanged(r0)
        L_0x0051:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.management.FavoritesModel.updateDivider(int, int):void");
    }

    private final void moveElement(int i, int i2) {
        if (i >= i2) {
            int i3 = i2 + 1;
            if (i3 <= i) {
                while (true) {
                    int i4 = i - 1;
                    Collections.swap(getElements(), i, i - 1);
                    if (i != i3) {
                        i = i4;
                    } else {
                        return;
                    }
                }
            }
        } else if (i < i2) {
            while (true) {
                int i5 = i + 1;
                Collections.swap(getElements(), i, i5);
                if (i5 < i2) {
                    i = i5;
                } else {
                    return;
                }
            }
        }
    }

    public final ItemTouchHelper.SimpleCallback getItemTouchHelperCallback() {
        return this.itemTouchHelperCallback;
    }
}

package com.android.systemui.qs.external;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CustomTileStatePersister.kt */
public final class TileServiceKey {
    private final ComponentName componentName;
    private final String string;
    private final int user;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TileServiceKey)) {
            return false;
        }
        TileServiceKey tileServiceKey = (TileServiceKey) obj;
        return Intrinsics.areEqual(this.componentName, tileServiceKey.componentName) && this.user == tileServiceKey.user;
    }

    public int hashCode() {
        return (this.componentName.hashCode() * 31) + Integer.hashCode(this.user);
    }

    public TileServiceKey(ComponentName componentName2, int i) {
        Intrinsics.checkNotNullParameter(componentName2, "componentName");
        this.componentName = componentName2;
        this.user = i;
        StringBuilder sb = new StringBuilder();
        sb.append((Object) componentName2.flattenToString());
        sb.append(':');
        sb.append(i);
        this.string = sb.toString();
    }

    public String toString() {
        return this.string;
    }
}

package com.android.systemui.controls;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.service.controls.Control;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlStatus.kt */
public final class ControlStatus implements ControlInterface {
    private final ComponentName component;
    private final Control control;
    private boolean favorite;
    private final boolean removed;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlStatus)) {
            return false;
        }
        ControlStatus controlStatus = (ControlStatus) obj;
        return Intrinsics.areEqual(this.control, controlStatus.control) && Intrinsics.areEqual(getComponent(), controlStatus.getComponent()) && getFavorite() == controlStatus.getFavorite() && getRemoved() == controlStatus.getRemoved();
    }

    public int hashCode() {
        int hashCode = ((this.control.hashCode() * 31) + getComponent().hashCode()) * 31;
        boolean favorite2 = getFavorite();
        int i = 1;
        if (favorite2) {
            favorite2 = true;
        }
        int i2 = favorite2 ? 1 : 0;
        int i3 = favorite2 ? 1 : 0;
        int i4 = favorite2 ? 1 : 0;
        int i5 = (hashCode + i2) * 31;
        boolean removed2 = getRemoved();
        if (!removed2) {
            i = removed2;
        }
        return i5 + i;
    }

    public String toString() {
        return "ControlStatus(control=" + this.control + ", component=" + getComponent() + ", favorite=" + getFavorite() + ", removed=" + getRemoved() + ')';
    }

    public ControlStatus(Control control2, ComponentName componentName, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(control2, "control");
        Intrinsics.checkNotNullParameter(componentName, "component");
        this.control = control2;
        this.component = componentName;
        this.favorite = z;
        this.removed = z2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ControlStatus(Control control2, ComponentName componentName, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(control2, componentName, z, (i & 8) != 0 ? false : z2);
    }

    public final Control getControl() {
        return this.control;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public ComponentName getComponent() {
        return this.component;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public boolean getFavorite() {
        return this.favorite;
    }

    public void setFavorite(boolean z) {
        this.favorite = z;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public boolean getRemoved() {
        return this.removed;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public String getControlId() {
        String controlId = this.control.getControlId();
        Intrinsics.checkNotNullExpressionValue(controlId, "control.controlId");
        return controlId;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public CharSequence getTitle() {
        CharSequence title = this.control.getTitle();
        Intrinsics.checkNotNullExpressionValue(title, "control.title");
        return title;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public CharSequence getSubtitle() {
        CharSequence subtitle = this.control.getSubtitle();
        Intrinsics.checkNotNullExpressionValue(subtitle, "control.subtitle");
        return subtitle;
    }

    @Override // com.android.systemui.controls.ControlInterface
    public Icon getCustomIcon() {
        return this.control.getCustomIcon();
    }

    @Override // com.android.systemui.controls.ControlInterface
    public int getDeviceType() {
        return this.control.getDeviceType();
    }
}

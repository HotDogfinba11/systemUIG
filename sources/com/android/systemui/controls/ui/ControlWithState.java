package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.service.controls.Control;
import com.android.systemui.controls.controller.ControlInfo;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlWithState.kt */
public final class ControlWithState {
    private final ControlInfo ci;
    private final ComponentName componentName;
    private final Control control;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlWithState)) {
            return false;
        }
        ControlWithState controlWithState = (ControlWithState) obj;
        return Intrinsics.areEqual(this.componentName, controlWithState.componentName) && Intrinsics.areEqual(this.ci, controlWithState.ci) && Intrinsics.areEqual(this.control, controlWithState.control);
    }

    public int hashCode() {
        int hashCode = ((this.componentName.hashCode() * 31) + this.ci.hashCode()) * 31;
        Control control2 = this.control;
        return hashCode + (control2 == null ? 0 : control2.hashCode());
    }

    public String toString() {
        return "ControlWithState(componentName=" + this.componentName + ", ci=" + this.ci + ", control=" + this.control + ')';
    }

    public ControlWithState(ComponentName componentName2, ControlInfo controlInfo, Control control2) {
        Intrinsics.checkNotNullParameter(componentName2, "componentName");
        Intrinsics.checkNotNullParameter(controlInfo, "ci");
        this.componentName = componentName2;
        this.ci = controlInfo;
        this.control = control2;
    }

    public final ComponentName getComponentName() {
        return this.componentName;
    }

    public final ControlInfo getCi() {
        return this.ci;
    }

    public final Control getControl() {
        return this.control;
    }
}

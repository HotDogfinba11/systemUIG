package com.android.systemui.controls.ui;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: DefaultBehavior.kt */
public final class DefaultBehavior implements Behavior {
    public ControlViewHolder cvh;

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        Intrinsics.throwUninitializedPropertyAccessException("cvh");
        throw null;
    }

    public final void setCvh(ControlViewHolder controlViewHolder) {
        Intrinsics.checkNotNullParameter(controlViewHolder, "<set-?>");
        this.cvh = controlViewHolder;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public void initialize(ControlViewHolder controlViewHolder) {
        Intrinsics.checkNotNullParameter(controlViewHolder, "cvh");
        setCvh(controlViewHolder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v3, types: [java.lang.CharSequence] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @Override // com.android.systemui.controls.ui.Behavior
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void bind(com.android.systemui.controls.ui.ControlWithState r11, int r12) {
        /*
            r10 = this;
            java.lang.String r0 = "cws"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
            com.android.systemui.controls.ui.ControlViewHolder r0 = r10.getCvh()
            android.service.controls.Control r11 = r11.getControl()
            java.lang.String r1 = ""
            if (r11 != 0) goto L_0x0012
            goto L_0x001a
        L_0x0012:
            java.lang.CharSequence r11 = r11.getStatusText()
            if (r11 != 0) goto L_0x0019
            goto L_0x001a
        L_0x0019:
            r1 = r11
        L_0x001a:
            r11 = 0
            r2 = 2
            r3 = 0
            com.android.systemui.controls.ui.ControlViewHolder.setStatusText$default(r0, r1, r11, r2, r3)
            com.android.systemui.controls.ui.ControlViewHolder r4 = r10.getCvh()
            r5 = 0
            r7 = 0
            r8 = 4
            r9 = 0
            r6 = r12
            com.android.systemui.controls.ui.ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.DefaultBehavior.bind(com.android.systemui.controls.ui.ControlWithState, int):void");
    }
}

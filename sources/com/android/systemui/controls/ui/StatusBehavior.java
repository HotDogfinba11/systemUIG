package com.android.systemui.controls.ui;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.service.controls.Control;
import com.android.systemui.R$string;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StatusBehavior.kt */
public final class StatusBehavior implements Behavior {
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

    @Override // com.android.systemui.controls.ui.Behavior
    public void bind(ControlWithState controlWithState, int i) {
        int i2;
        Intrinsics.checkNotNullParameter(controlWithState, "cws");
        Control control = controlWithState.getControl();
        int status = control == null ? 0 : control.getStatus();
        if (status == 2) {
            getCvh().getLayout().setOnClickListener(new StatusBehavior$bind$msg$1(this, controlWithState));
            getCvh().getLayout().setOnLongClickListener(new StatusBehavior$bind$msg$2(this, controlWithState));
            i2 = R$string.controls_error_removed;
        } else if (status == 3) {
            i2 = R$string.controls_error_generic;
        } else if (status != 4) {
            getCvh().setLoading(true);
            i2 = 17040507;
        } else {
            i2 = R$string.controls_error_timeout;
        }
        ControlViewHolder cvh2 = getCvh();
        String string = getCvh().getContext().getString(i2);
        Intrinsics.checkNotNullExpressionValue(string, "cvh.context.getString(msg)");
        ControlViewHolder.setStatusText$default(cvh2, string, false, 2, null);
        ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(getCvh(), false, i, false, 4, null);
    }

    /* access modifiers changed from: private */
    public final void showNotFoundDialog(ControlViewHolder controlViewHolder, ControlWithState controlWithState) {
        PackageManager packageManager = controlViewHolder.getContext().getPackageManager();
        CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(controlWithState.getComponentName().getPackageName(), 128));
        AlertDialog.Builder builder = new AlertDialog.Builder(controlViewHolder.getContext(), 16974545);
        Resources resources = controlViewHolder.getContext().getResources();
        builder.setTitle(resources.getString(R$string.controls_error_removed_title));
        builder.setMessage(resources.getString(R$string.controls_error_removed_message, controlViewHolder.getTitle().getText(), applicationLabel));
        builder.setPositiveButton(R$string.controls_open_app, new StatusBehavior$showNotFoundDialog$builder$1$1(controlWithState, builder, controlViewHolder));
        builder.setNegativeButton(17039360, StatusBehavior$showNotFoundDialog$builder$1$2.INSTANCE);
        AlertDialog create = builder.create();
        create.getWindow().setType(2020);
        create.show();
        Unit unit = Unit.INSTANCE;
        controlViewHolder.setVisibleDialog(create);
    }
}

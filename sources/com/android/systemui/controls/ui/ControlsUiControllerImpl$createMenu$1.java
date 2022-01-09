package com.android.systemui.controls.ui;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.android.systemui.globalactions.GlobalActionsPopupMenu;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$createMenu$1 implements View.OnClickListener {
    final /* synthetic */ Ref$ObjectRef<ArrayAdapter<String>> $adapter;
    final /* synthetic */ ImageView $anchor;
    final /* synthetic */ ControlsUiControllerImpl this$0;

    ControlsUiControllerImpl$createMenu$1(ControlsUiControllerImpl controlsUiControllerImpl, ImageView imageView, Ref$ObjectRef<ArrayAdapter<String>> ref$ObjectRef) {
        this.this$0 = controlsUiControllerImpl;
        this.$anchor = imageView;
        this.$adapter = ref$ObjectRef;
    }

    public void onClick(View view) {
        Intrinsics.checkNotNullParameter(view, "v");
        ControlsUiControllerImpl controlsUiControllerImpl = this.this$0;
        GlobalActionsPopupMenu globalActionsPopupMenu = new GlobalActionsPopupMenu(ControlsUiControllerImpl.access$getPopupThemedContext$p(this.this$0), false);
        ImageView imageView = this.$anchor;
        Ref$ObjectRef<ArrayAdapter<String>> ref$ObjectRef = this.$adapter;
        ControlsUiControllerImpl controlsUiControllerImpl2 = this.this$0;
        globalActionsPopupMenu.setAnchorView(imageView);
        globalActionsPopupMenu.setAdapter(ref$ObjectRef.element);
        globalActionsPopupMenu.setOnItemClickListener(new ControlsUiControllerImpl$createMenu$1$onClick$1$1(controlsUiControllerImpl2, globalActionsPopupMenu));
        globalActionsPopupMenu.show();
        Unit unit = Unit.INSTANCE;
        ControlsUiControllerImpl.access$setPopup$p(controlsUiControllerImpl, globalActionsPopupMenu);
    }
}

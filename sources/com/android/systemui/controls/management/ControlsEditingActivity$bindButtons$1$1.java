package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import com.android.systemui.controls.ui.ControlsActivity;

/* compiled from: ControlsEditingActivity.kt */
final class ControlsEditingActivity$bindButtons$1$1 implements View.OnClickListener {
    final /* synthetic */ ControlsEditingActivity this$0;

    ControlsEditingActivity$bindButtons$1$1(ControlsEditingActivity controlsEditingActivity) {
        this.this$0 = controlsEditingActivity;
    }

    public final void onClick(View view) {
        ControlsEditingActivity.access$saveFavorites(this.this$0);
        this.this$0.startActivity(new Intent(this.this$0.getApplicationContext(), ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this.this$0, new Pair[0]).toBundle());
        ControlsEditingActivity.access$animateExitAndFinish(this.this$0);
    }
}

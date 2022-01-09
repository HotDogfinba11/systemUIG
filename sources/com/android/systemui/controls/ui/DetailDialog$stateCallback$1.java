package com.android.systemui.controls.ui;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.android.wm.shell.TaskView;

/* compiled from: DetailDialog.kt */
public final class DetailDialog$stateCallback$1 implements TaskView.Listener {
    final /* synthetic */ DetailDialog this$0;

    DetailDialog$stateCallback$1(DetailDialog detailDialog) {
        this.this$0 = detailDialog;
    }

    @Override // com.android.wm.shell.TaskView.Listener
    public void onInitialized() {
        ActivityOptions activityOptions;
        Intent intent = new Intent(this.this$0.getIntent());
        intent.putExtra("controls.DISPLAY_IN_PANEL", true);
        intent.addFlags(524288);
        intent.addFlags(134217728);
        Context activityContext = this.this$0.getActivityContext();
        if (activityContext == null) {
            activityOptions = null;
        } else {
            activityOptions = ActivityOptions.makeCustomAnimation(activityContext, 0, 0);
        }
        if (activityOptions == null) {
            activityOptions = ActivityOptions.makeBasic();
        }
        this.this$0.getTaskView().startActivity(PendingIntent.getActivity(this.this$0.getContext(), 0, intent, 201326592), null, activityOptions, this.this$0.getTaskViewBounds());
    }

    @Override // com.android.wm.shell.TaskView.Listener
    public void onTaskRemovalStarted(int i) {
        this.this$0.setDetailTaskId(-1);
        this.this$0.dismiss();
    }

    @Override // com.android.wm.shell.TaskView.Listener
    public void onTaskCreated(int i, ComponentName componentName) {
        this.this$0.setDetailTaskId(i);
    }

    @Override // com.android.wm.shell.TaskView.Listener
    public void onReleased() {
        this.this$0.removeDetailTask();
    }

    @Override // com.android.wm.shell.TaskView.Listener
    public void onBackPressedOnTaskRoot(int i) {
        this.this$0.dismiss();
    }
}

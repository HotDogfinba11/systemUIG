package com.android.systemui.controls.ui;

import android.app.ActivityTaskManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ImageView;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$style;
import com.android.wm.shell.TaskView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DetailDialog.kt */
public final class DetailDialog extends Dialog {
    public static final Companion Companion = new Companion(null);
    private final Context activityContext;
    private final ControlViewHolder cvh;
    private int detailTaskId;
    private final Intent intent;
    private final TaskView.Listener stateCallback;
    private final TaskView taskView;

    public final Context getActivityContext() {
        return this.activityContext;
    }

    public final TaskView getTaskView() {
        return this.taskView;
    }

    public final Intent getIntent() {
        return this.intent;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DetailDialog(Context context, TaskView taskView2, Intent intent2, ControlViewHolder controlViewHolder) {
        super(context == null ? controlViewHolder.getContext() : context, R$style.Theme_SystemUI_Dialog_Control_DetailPanel);
        Intrinsics.checkNotNullParameter(taskView2, "taskView");
        Intrinsics.checkNotNullParameter(intent2, "intent");
        Intrinsics.checkNotNullParameter(controlViewHolder, "cvh");
        this.activityContext = context;
        this.taskView = taskView2;
        this.intent = intent2;
        this.cvh = controlViewHolder;
        this.detailTaskId = -1;
        DetailDialog$stateCallback$1 detailDialog$stateCallback$1 = new DetailDialog$stateCallback$1(this);
        this.stateCallback = detailDialog$stateCallback$1;
        if (context == null) {
            getWindow().setType(2020);
        }
        getWindow().addFlags(32);
        getWindow().addPrivateFlags(536870912);
        setContentView(R$layout.controls_detail_dialog);
        ((ViewGroup) requireViewById(R$id.controls_activity_view)).addView(getTaskView());
        ((ImageView) requireViewById(R$id.control_detail_close)).setOnClickListener(new DetailDialog$2$1(this));
        ImageView imageView = (ImageView) requireViewById(R$id.control_detail_open_in_app);
        imageView.setOnClickListener(new DetailDialog$3$1(this, imageView));
        getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(this) {
            /* class com.android.systemui.controls.ui.DetailDialog.AnonymousClass4 */
            final /* synthetic */ DetailDialog this$0;

            {
                this.this$0 = r1;
            }

            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Intrinsics.checkNotNullParameter(view, "v");
                Intrinsics.checkNotNullParameter(windowInsets, "insets");
                TaskView taskView = this.this$0.getTaskView();
                taskView.setPadding(taskView.getPaddingLeft(), taskView.getPaddingTop(), taskView.getPaddingRight(), windowInsets.getInsets(WindowInsets.Type.systemBars()).bottom);
                int paddingLeft = view.getPaddingLeft();
                int paddingBottom = view.getPaddingBottom();
                view.setPadding(paddingLeft, windowInsets.getInsets(WindowInsets.Type.systemBars()).top, view.getPaddingRight(), paddingBottom);
                return WindowInsets.CONSUMED;
            }
        });
        if (ScreenDecorationsUtils.supportsRoundedCornersOnWindows(getContext().getResources())) {
            taskView2.setCornerRadius((float) getContext().getResources().getDimensionPixelSize(R$dimen.controls_activity_view_corner_radius));
        }
        taskView2.setListener(controlViewHolder.getUiExecutor(), detailDialog$stateCallback$1);
    }

    /* compiled from: DetailDialog.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final void setDetailTaskId(int i) {
        this.detailTaskId = i;
    }

    public final void removeDetailTask() {
        if (this.detailTaskId != -1) {
            ActivityTaskManager.getInstance().removeTask(this.detailTaskId);
            this.detailTaskId = -1;
        }
    }

    public final Rect getTaskViewBounds() {
        WindowMetrics currentWindowMetrics = ((WindowManager) getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics();
        Rect bounds = currentWindowMetrics.getBounds();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        return new Rect(bounds.left - insetsIgnoringVisibility.left, bounds.top + insetsIgnoringVisibility.top + getContext().getResources().getDimensionPixelSize(R$dimen.controls_detail_dialog_header_height), bounds.right - insetsIgnoringVisibility.right, bounds.bottom - insetsIgnoringVisibility.bottom);
    }

    public void dismiss() {
        if (isShowing()) {
            this.taskView.release();
            super.dismiss();
        }
    }
}

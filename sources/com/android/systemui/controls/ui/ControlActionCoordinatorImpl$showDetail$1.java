package com.android.systemui.controls.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.TaskView;
import com.android.wm.shell.TaskViewFactory;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ControlActionCoordinatorImpl.kt */
public final class ControlActionCoordinatorImpl$showDetail$1 implements Runnable {
    final /* synthetic */ ControlViewHolder $cvh;
    final /* synthetic */ Intent $intent;
    final /* synthetic */ ControlActionCoordinatorImpl this$0;

    ControlActionCoordinatorImpl$showDetail$1(ControlActionCoordinatorImpl controlActionCoordinatorImpl, Intent intent, ControlViewHolder controlViewHolder) {
        this.this$0 = controlActionCoordinatorImpl;
        this.$intent = intent;
        this.$cvh = controlViewHolder;
    }

    public final void run() {
        final List<ResolveInfo> queryIntentActivities = this.this$0.context.getPackageManager().queryIntentActivities(this.$intent, 65536);
        Intrinsics.checkNotNullExpressionValue(queryIntentActivities, "context.packageManager.queryIntentActivities(\n                intent,\n                PackageManager.MATCH_DEFAULT_ONLY\n            )");
        DelayableExecutor delayableExecutor = this.this$0.uiExecutor;
        final ControlActionCoordinatorImpl controlActionCoordinatorImpl = this.this$0;
        final ControlViewHolder controlViewHolder = this.$cvh;
        final Intent intent = this.$intent;
        delayableExecutor.execute(new Runnable() {
            /* class com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1.AnonymousClass1 */

            public final void run() {
                if (!(!queryIntentActivities.isEmpty()) || !controlActionCoordinatorImpl.taskViewFactory.isPresent()) {
                    controlViewHolder.setErrorStatus();
                    return;
                }
                Context context = controlActionCoordinatorImpl.context;
                DelayableExecutor delayableExecutor = controlActionCoordinatorImpl.uiExecutor;
                final ControlActionCoordinatorImpl controlActionCoordinatorImpl = controlActionCoordinatorImpl;
                final Intent intent = intent;
                final ControlViewHolder controlViewHolder = controlViewHolder;
                ((TaskViewFactory) controlActionCoordinatorImpl.taskViewFactory.get()).create(context, delayableExecutor, new Consumer<TaskView>() {
                    /* class com.android.systemui.controls.ui.ControlActionCoordinatorImpl$showDetail$1.AnonymousClass1.AnonymousClass1 */

                    public final void accept(TaskView taskView) {
                        ControlActionCoordinatorImpl controlActionCoordinatorImpl = controlActionCoordinatorImpl;
                        Context activityContext = controlActionCoordinatorImpl.getActivityContext();
                        Intrinsics.checkNotNullExpressionValue(taskView, "it");
                        DetailDialog detailDialog = new DetailDialog(activityContext, taskView, intent, controlViewHolder);
                        detailDialog.setOnDismissListener(new ControlActionCoordinatorImpl$showDetail$1$1$1$1$1(controlActionCoordinatorImpl));
                        detailDialog.show();
                        Unit unit = Unit.INSTANCE;
                        controlActionCoordinatorImpl.dialog = detailDialog;
                    }
                });
            }
        });
    }
}

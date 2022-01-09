package com.android.wm.shell;

import android.content.Context;
import com.android.wm.shell.common.ShellExecutor;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class TaskViewFactoryController {
    private final TaskViewFactory mImpl = new TaskViewFactoryImpl();
    private final ShellExecutor mShellExecutor;
    private final ShellTaskOrganizer mTaskOrganizer;

    public TaskViewFactoryController(ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor) {
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mShellExecutor = shellExecutor;
    }

    public TaskViewFactory asTaskViewFactory() {
        return this.mImpl;
    }

    public void create(Context context, Executor executor, Consumer<TaskView> consumer) {
        executor.execute(new TaskViewFactoryController$$ExternalSyntheticLambda0(consumer, new TaskView(context, this.mTaskOrganizer)));
    }

    /* access modifiers changed from: private */
    public class TaskViewFactoryImpl implements TaskViewFactory {
        private TaskViewFactoryImpl() {
        }

        @Override // com.android.wm.shell.TaskViewFactory
        public void create(Context context, Executor executor, Consumer<TaskView> consumer) {
            TaskViewFactoryController.this.mShellExecutor.execute(new TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0(this, context, executor, consumer));
        }

        /* access modifiers changed from: private */
        public /* synthetic */ void lambda$create$0(Context context, Executor executor, Consumer consumer) {
            TaskViewFactoryController.this.create(context, executor, consumer);
        }
    }
}

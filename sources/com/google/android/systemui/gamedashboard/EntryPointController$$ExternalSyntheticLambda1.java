package com.google.android.systemui.gamedashboard;

import com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import java.util.function.Consumer;

public final /* synthetic */ class EntryPointController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ EntryPointController$$ExternalSyntheticLambda1(int i, int i2) {
        this.f$0 = i;
        this.f$1 = i2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((TaskSurfaceHelper) obj).setGameModeForTask(this.f$0, this.f$1);
    }
}

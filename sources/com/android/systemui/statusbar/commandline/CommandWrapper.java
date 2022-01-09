package com.android.systemui.statusbar.commandline;

import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: CommandRegistry.kt */
public final class CommandWrapper {
    private final Function0<Command> commandFactory;
    private final Executor executor;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommandWrapper)) {
            return false;
        }
        CommandWrapper commandWrapper = (CommandWrapper) obj;
        return Intrinsics.areEqual(this.commandFactory, commandWrapper.commandFactory) && Intrinsics.areEqual(this.executor, commandWrapper.executor);
    }

    public int hashCode() {
        return (this.commandFactory.hashCode() * 31) + this.executor.hashCode();
    }

    public String toString() {
        return "CommandWrapper(commandFactory=" + this.commandFactory + ", executor=" + this.executor + ')';
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: kotlin.jvm.functions.Function0<? extends com.android.systemui.statusbar.commandline.Command> */
    /* JADX WARN: Multi-variable type inference failed */
    public CommandWrapper(Function0<? extends Command> function0, Executor executor2) {
        Intrinsics.checkNotNullParameter(function0, "commandFactory");
        Intrinsics.checkNotNullParameter(executor2, "executor");
        this.commandFactory = function0;
        this.executor = executor2;
    }

    public final Function0<Command> getCommandFactory() {
        return this.commandFactory;
    }

    public final Executor getExecutor() {
        return this.executor;
    }
}

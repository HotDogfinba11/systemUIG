package com.android.wm.shell.transition;

public interface ShellTransitions {
    default IShellTransitions createExternalInterface() {
        return null;
    }
}

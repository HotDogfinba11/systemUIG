package com.android.wm.shell.startingsurface;

import android.app.TaskInfo;

public interface StartingSurface {
    default IStartingWindow createExternalInterface() {
        return null;
    }

    default int getBackgroundColor(TaskInfo taskInfo) {
        return -16777216;
    }
}

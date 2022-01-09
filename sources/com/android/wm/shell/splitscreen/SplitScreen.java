package com.android.wm.shell.splitscreen;

public interface SplitScreen {

    public interface SplitScreenListener {
        void onStagePositionChanged(int i, int i2);

        void onTaskStageChanged(int i, int i2, boolean z);
    }

    default ISplitScreen createExternalInterface() {
        return null;
    }

    static default String stageTypeToString(int i) {
        if (i == -1) {
            return "UNDEFINED";
        }
        if (i == 0) {
            return "MAIN";
        }
        if (i == 1) {
            return "SIDE";
        }
        return "UNKNOWN(" + i + ")";
    }
}

package com.android.wm.shell.onehanded;

import android.graphics.Rect;

public interface OneHandedTransitionCallback {
    default void onStartFinished(Rect rect) {
    }

    default void onStartTransition(boolean z) {
    }

    default void onStopFinished(Rect rect) {
    }
}

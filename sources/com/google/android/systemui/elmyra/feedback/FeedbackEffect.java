package com.google.android.systemui.elmyra.feedback;

import com.google.android.systemui.elmyra.sensors.GestureSensor;

public interface FeedbackEffect {
    void onProgress(float f, int i);

    void onRelease();

    void onResolve(GestureSensor.DetectionProperties detectionProperties);
}

package com.google.android.systemui.columbus.sensors;

import java.util.Random;

/* compiled from: GestureSensor.kt */
public abstract class GestureSensor implements Sensor {
    private Listener listener;

    /* compiled from: GestureSensor.kt */
    public interface Listener {
        void onGestureDetected(GestureSensor gestureSensor, int i, DetectionProperties detectionProperties);
    }

    /* compiled from: GestureSensor.kt */
    public static final class DetectionProperties {
        private final long actionId = new Random().nextLong();
        private final boolean isHapticConsumed;

        public DetectionProperties(boolean z) {
            this.isHapticConsumed = z;
        }

        public final boolean isHapticConsumed() {
            return this.isHapticConsumed;
        }

        public final long getActionId() {
            return this.actionId;
        }
    }

    public final void setGestureListener(Listener listener2) {
        this.listener = listener2;
    }

    public final void reportGestureDetected(int i, DetectionProperties detectionProperties) {
        Listener listener2 = this.listener;
        if (listener2 != null) {
            listener2.onGestureDetected(this, i, detectionProperties);
        }
    }
}

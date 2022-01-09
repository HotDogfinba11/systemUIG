package com.google.android.systemui.columbus;

import com.google.android.systemui.columbus.actions.Action;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.sensors.GestureController;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusService.kt */
public final class ColumbusService$gestureListener$1 implements GestureController.GestureListener {
    final /* synthetic */ ColumbusService this$0;

    ColumbusService$gestureListener$1(ColumbusService columbusService) {
        this.this$0 = columbusService;
    }

    @Override // com.google.android.systemui.columbus.sensors.GestureController.GestureListener
    public void onGestureDetected(GestureSensor gestureSensor, int i, GestureSensor.DetectionProperties detectionProperties) {
        Intrinsics.checkNotNullParameter(gestureSensor, "sensor");
        if (i != 0) {
            ColumbusService.access$getWakeLock$p(this.this$0).acquire(2000);
        }
        Action access$updateActiveAction = ColumbusService.access$updateActiveAction(this.this$0);
        if (access$updateActiveAction != null) {
            ColumbusService columbusService = this.this$0;
            access$updateActiveAction.onGestureDetected(i, detectionProperties);
            for (FeedbackEffect feedbackEffect : ColumbusService.access$getEffects$p(columbusService)) {
                feedbackEffect.onGestureDetected(i, detectionProperties);
            }
        }
    }
}

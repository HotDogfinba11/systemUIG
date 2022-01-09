package com.google.android.systemui.columbus.sensors;

import android.util.Log;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.sensors.GestureController;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GestureController.kt */
public final class GestureController$gestureSensorListener$1 implements GestureSensor.Listener {
    final /* synthetic */ GestureController this$0;

    GestureController$gestureSensorListener$1(GestureController gestureController) {
        this.this$0 = gestureController;
    }

    @Override // com.google.android.systemui.columbus.sensors.GestureSensor.Listener
    public void onGestureDetected(GestureSensor gestureSensor, int i, GestureSensor.DetectionProperties detectionProperties) {
        Object obj;
        Intrinsics.checkNotNullParameter(gestureSensor, "sensor");
        if (this.this$0.isThrottled(i)) {
            Log.w("Columbus/GestureControl", "Gesture " + i + " throttled");
            return;
        }
        Iterator it = this.this$0.softGates.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((Gate) obj).isBlocking()) {
                break;
            }
        }
        Gate gate = (Gate) obj;
        if (gate != null) {
            GestureController gestureController = this.this$0;
            gestureController.softGateBlockCount = gestureController.softGateBlockCount + 1;
            Log.i("Columbus/GestureControl", Intrinsics.stringPlus("Gesture blocked by ", gate));
            return;
        }
        if (i == 1) {
            this.this$0.uiEventLogger.log(ColumbusEvent.COLUMBUS_DOUBLE_TAP_DETECTED);
        }
        GestureController.GestureListener gestureListener = this.this$0.gestureListener;
        if (gestureListener != null) {
            gestureListener.onGestureDetected(gestureSensor, i, detectionProperties);
        }
    }
}

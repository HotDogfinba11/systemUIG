package com.google.android.systemui.assist.uihints.input;

import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.TouchInsideHandler;
import java.util.Set;

public class NgaInputHandler implements NgaMessageHandler.EdgeLightsInfoListener {
    private InputEventReceiver mInputEventReceiver;
    private InputMonitor mInputMonitor;
    private final Set<TouchActionRegion> mTouchActionRegions;
    private final TouchInsideHandler mTouchInsideHandler;
    private final Set<TouchInsideRegion> mTouchInsideRegions;

    NgaInputHandler(TouchInsideHandler touchInsideHandler, Set<TouchActionRegion> set, Set<TouchInsideRegion> set2) {
        this.mTouchInsideHandler = touchInsideHandler;
        this.mTouchActionRegions = set;
        this.mTouchInsideRegions = set2;
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.EdgeLightsInfoListener
    public void onEdgeLightsInfo(String str, boolean z) {
        if ("HALF_LISTENING".equals(str)) {
            startMonitoring();
        } else {
            stopMonitoring();
        }
    }

    private void startMonitoring() {
        if (this.mInputEventReceiver == null && this.mInputMonitor == null) {
            this.mInputMonitor = InputManager.getInstance().monitorGestureInput("NgaInputHandler", 0);
            this.mInputEventReceiver = new NgaInputEventReceiver(this.mInputMonitor.getInputChannel());
            return;
        }
        Log.w("NgaInputHandler", "Already monitoring");
    }

    private void stopMonitoring() {
        InputEventReceiver inputEventReceiver = this.mInputEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleMotionEvent(MotionEvent motionEvent) {
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        Region region = new Region();
        for (TouchInsideRegion touchInsideRegion : this.mTouchInsideRegions) {
            touchInsideRegion.getTouchInsideRegion().ifPresent(new NgaInputHandler$$ExternalSyntheticLambda0(region));
        }
        for (TouchActionRegion touchActionRegion : this.mTouchActionRegions) {
            touchActionRegion.getTouchActionRegion().ifPresent(new NgaInputHandler$$ExternalSyntheticLambda1(region));
        }
        if (region.contains(rawX, rawY)) {
            this.mTouchInsideHandler.onTouchInside();
        }
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleMotionEvent$0(Region region, Region region2) {
        region.op(region2, Region.Op.UNION);
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleMotionEvent$1(Region region, Region region2) {
        region.op(region2, Region.Op.DIFFERENCE);
    }

    /* access modifiers changed from: private */
    public class NgaInputEventReceiver extends InputEventReceiver {
        private NgaInputEventReceiver(InputChannel inputChannel) {
            super(inputChannel, Looper.getMainLooper());
        }

        public void onInputEvent(InputEvent inputEvent) {
            if (inputEvent instanceof MotionEvent) {
                NgaInputHandler.this.handleMotionEvent((MotionEvent) inputEvent);
            }
            finishInputEvent(inputEvent, false);
        }
    }
}

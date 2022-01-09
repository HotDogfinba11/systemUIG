package com.android.systemui.controls.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.controls.ui.ToggleRangeBehavior;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ToggleRangeBehavior.kt */
final class ToggleRangeBehavior$initialize$1 implements View.OnTouchListener {
    final /* synthetic */ GestureDetector $gestureDetector;
    final /* synthetic */ ToggleRangeBehavior.ToggleRangeGestureListener $gestureListener;
    final /* synthetic */ ToggleRangeBehavior this$0;

    ToggleRangeBehavior$initialize$1(GestureDetector gestureDetector, ToggleRangeBehavior.ToggleRangeGestureListener toggleRangeGestureListener, ToggleRangeBehavior toggleRangeBehavior) {
        this.$gestureDetector = gestureDetector;
        this.$gestureListener = toggleRangeGestureListener;
        this.this$0 = toggleRangeBehavior;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(view, "v");
        Intrinsics.checkNotNullParameter(motionEvent, "e");
        if (!this.$gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1 && this.$gestureListener.isDragging()) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
            this.$gestureListener.setDragging(false);
            this.this$0.endUpdateRange();
        }
        return false;
    }
}

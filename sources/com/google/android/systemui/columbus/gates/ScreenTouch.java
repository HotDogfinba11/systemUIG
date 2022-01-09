package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ScreenTouch.kt */
public final class ScreenTouch extends Gate {
    public static final Companion Companion = new Companion(null);
    private final Runnable clearBlocking = new ScreenTouch$clearBlocking$1(this);
    private final ScreenTouch$gateListener$1 gateListener = new ScreenTouch$gateListener$1(this);
    private final Handler handler;
    private final InputChannelCompat$InputEventListener inputEventListener = new ScreenTouch$inputEventListener$1(this);
    private InputChannelCompat$InputEventReceiver inputEventReceiver;
    private InputMonitorCompat inputMonitor;
    private final PowerState powerState;
    private final RectF touchRegion;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ScreenTouch(Context context, PowerState powerState2, Handler handler2) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(powerState2, "powerState");
        Intrinsics.checkNotNullParameter(handler2, "handler");
        this.powerState = powerState2;
        this.handler = handler2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float f = displayMetrics.density * ((float) 32);
        this.touchRegion = new RectF(f, f, ((float) displayMetrics.widthPixels) - f, ((float) displayMetrics.heightPixels) - f);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onActivate() {
        this.powerState.registerListener(this.gateListener);
        if (!this.powerState.isBlocking()) {
            startListeningForTouch();
        }
        setBlocking(false);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.columbus.gates.Gate
    public void onDeactivate() {
        this.powerState.unregisterListener(this.gateListener);
        stopListeningForTouch();
    }

    /* access modifiers changed from: private */
    public final void startListeningForTouch() {
        if (this.inputEventReceiver == null) {
            InputMonitorCompat inputMonitorCompat = new InputMonitorCompat("Quick Tap", 0);
            this.inputMonitor = inputMonitorCompat;
            this.inputEventReceiver = inputMonitorCompat.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), this.inputEventListener);
        }
    }

    /* access modifiers changed from: private */
    public final void stopListeningForTouch() {
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.inputEventReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            inputChannelCompat$InputEventReceiver.dispose();
        }
        this.inputEventReceiver = null;
        InputMonitorCompat inputMonitorCompat = this.inputMonitor;
        if (inputMonitorCompat != null) {
            inputMonitorCompat.dispose();
        }
        this.inputMonitor = null;
    }

    /* access modifiers changed from: private */
    public final void onInputEvent(InputEvent inputEvent) {
        MotionEvent motionEvent = inputEvent instanceof MotionEvent ? (MotionEvent) inputEvent : null;
        if (motionEvent != null) {
            if (isABlockingTouch(motionEvent)) {
                this.handler.removeCallbacks(this.clearBlocking);
                setBlocking(true);
            } else if (motionEvent.getAction() == 1 && isBlocking()) {
                this.handler.postDelayed(this.clearBlocking, 500);
            }
        }
    }

    private final boolean isABlockingTouch(MotionEvent motionEvent) {
        return motionEvent.getAction() == 0 && this.touchRegion.contains(motionEvent.getRawX(), motionEvent.getRawY());
    }

    /* compiled from: ScreenTouch.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

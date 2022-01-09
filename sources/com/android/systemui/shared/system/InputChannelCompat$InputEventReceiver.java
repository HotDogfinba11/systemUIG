package com.android.systemui.shared.system;

import android.os.Looper;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;

public class InputChannelCompat$InputEventReceiver {
    private final BatchedInputEventReceiver mReceiver;

    public InputChannelCompat$InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer, final InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener) {
        this.mReceiver = new BatchedInputEventReceiver(inputChannel, looper, choreographer) {
            /* class com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver.AnonymousClass1 */

            public void onInputEvent(InputEvent inputEvent) {
                inputChannelCompat$InputEventListener.onInputEvent(inputEvent);
                finishInputEvent(inputEvent, true);
            }
        };
    }

    public void setBatchingEnabled(boolean z) {
        this.mReceiver.setBatchingEnabled(z);
    }

    public void dispose() {
        this.mReceiver.dispose();
    }
}

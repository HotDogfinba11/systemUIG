package com.google.android.systemui.columbus.gates;

import android.view.InputEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;

/* access modifiers changed from: package-private */
/* compiled from: ScreenTouch.kt */
public final class ScreenTouch$inputEventListener$1 implements InputChannelCompat$InputEventListener {
    final /* synthetic */ ScreenTouch this$0;

    ScreenTouch$inputEventListener$1(ScreenTouch screenTouch) {
        this.this$0 = screenTouch;
    }

    @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
    public final void onInputEvent(InputEvent inputEvent) {
        if (inputEvent != null) {
            this.this$0.onInputEvent(inputEvent);
        }
    }
}

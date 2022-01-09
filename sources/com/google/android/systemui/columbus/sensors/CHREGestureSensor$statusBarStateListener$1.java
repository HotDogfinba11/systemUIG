package com.google.android.systemui.columbus.sensors;

import com.android.systemui.plugins.statusbar.StatusBarStateController;

/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor$statusBarStateListener$1 implements StatusBarStateController.StateListener {
    final /* synthetic */ CHREGestureSensor this$0;

    CHREGestureSensor$statusBarStateListener$1(CHREGestureSensor cHREGestureSensor) {
        this.this$0 = cHREGestureSensor;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozingChanged(boolean z) {
        this.this$0.handleDozingChanged(z);
    }
}

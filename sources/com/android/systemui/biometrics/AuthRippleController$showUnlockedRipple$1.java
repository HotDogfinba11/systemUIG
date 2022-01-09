package com.android.systemui.biometrics;

/* access modifiers changed from: package-private */
/* compiled from: AuthRippleController.kt */
public final class AuthRippleController$showUnlockedRipple$1 implements Runnable {
    final /* synthetic */ AuthRippleController this$0;

    AuthRippleController$showUnlockedRipple$1(AuthRippleController authRippleController) {
        this.this$0 = authRippleController;
    }

    public final void run() {
        this.this$0.notificationShadeWindowController.setForcePluginOpen(false, this.this$0);
    }
}

package com.google.android.systemui.fingerprint;

import android.view.Surface;

/* access modifiers changed from: package-private */
public class UdfpsHbmRequest {
    public final Args args;
    public boolean beganEnablingHbm;
    public boolean finishedEnablingHbm;

    /* access modifiers changed from: package-private */
    public static class Args {
        public final int displayId;
        public final int hbmType;
        public final Runnable onHbmEnabled;
        public final Surface surface;

        Args(int i, int i2, Surface surface2, Runnable runnable) {
            this.displayId = i;
            this.hbmType = i2;
            this.surface = surface2;
            this.onHbmEnabled = runnable;
        }
    }

    UdfpsHbmRequest(int i, int i2, Surface surface, Runnable runnable) {
        this.args = new Args(i, i2, surface, runnable);
    }
}

package com.google.android.systemui.columbus;

import android.content.Context;
import android.os.PowerManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PowerManagerWrapper.kt */
public class PowerManagerWrapper {
    private final PowerManager powerManager;

    public PowerManagerWrapper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.powerManager = (PowerManager) context.getSystemService("power");
    }

    /* compiled from: PowerManagerWrapper.kt */
    public static class WakeLockWrapper {
        private final PowerManager.WakeLock wakeLock;

        public WakeLockWrapper(PowerManager.WakeLock wakeLock2) {
            this.wakeLock = wakeLock2;
        }

        public void acquire(long j) {
            PowerManager.WakeLock wakeLock2 = this.wakeLock;
            if (wakeLock2 != null) {
                wakeLock2.acquire(j);
            }
        }
    }

    public WakeLockWrapper newWakeLock(int i, String str) {
        Intrinsics.checkNotNullParameter(str, "tag");
        PowerManager powerManager2 = this.powerManager;
        return new WakeLockWrapper(powerManager2 == null ? null : powerManager2.newWakeLock(i, str));
    }

    public Boolean isInteractive() {
        PowerManager powerManager2 = this.powerManager;
        if (powerManager2 == null) {
            return null;
        }
        return Boolean.valueOf(powerManager2.isInteractive());
    }
}

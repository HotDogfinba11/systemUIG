package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.os.Handler;

/* access modifiers changed from: package-private */
public abstract class TransientGate extends Gate {
    private final long mBlockDuration;
    private boolean mIsBlocking;
    private final Runnable mResetGate = new Runnable() {
        /* class com.google.android.systemui.elmyra.gates.TransientGate.AnonymousClass1 */

        public void run() {
            TransientGate.this.mIsBlocking = false;
            TransientGate.this.notifyListener();
        }
    };
    private final Handler mResetGateHandler;

    TransientGate(Context context, long j) {
        super(context);
        this.mBlockDuration = j;
        this.mResetGateHandler = new Handler(context.getMainLooper());
    }

    /* access modifiers changed from: protected */
    public void block() {
        this.mIsBlocking = true;
        notifyListener();
        this.mResetGateHandler.removeCallbacks(this.mResetGate);
        this.mResetGateHandler.postDelayed(this.mResetGate, this.mBlockDuration);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public boolean isBlocked() {
        return this.mIsBlocking;
    }
}

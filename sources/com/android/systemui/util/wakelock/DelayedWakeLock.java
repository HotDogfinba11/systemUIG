package com.android.systemui.util.wakelock;

import android.content.Context;
import android.os.Handler;

public class DelayedWakeLock implements WakeLock {
    private final Handler mHandler;
    private final WakeLock mInner;

    public DelayedWakeLock(Handler handler, WakeLock wakeLock) {
        this.mHandler = handler;
        this.mInner = wakeLock;
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void acquire(String str) {
        this.mInner.acquire(str);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$release$0(String str) {
        this.mInner.release(str);
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void release(String str) {
        this.mHandler.postDelayed(new DelayedWakeLock$$ExternalSyntheticLambda0(this, str), 100);
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public Runnable wrap(Runnable runnable) {
        return WakeLock.wrapImpl(this, runnable);
    }

    public String toString() {
        return "[DelayedWakeLock] " + this.mInner;
    }

    public static class Builder {
        private final Context mContext;
        private Handler mHandler;
        private String mTag;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTag(String str) {
            this.mTag = str;
            return this;
        }

        public Builder setHandler(Handler handler) {
            this.mHandler = handler;
            return this;
        }

        public DelayedWakeLock build() {
            return new DelayedWakeLock(this.mHandler, WakeLock.createPartial(this.mContext, this.mTag));
        }
    }
}

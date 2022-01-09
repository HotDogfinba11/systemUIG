package com.google.android.systemui.assist.uihints;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.assist.AssistManager;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.Lazy;
import java.util.concurrent.TimeUnit;

/* access modifiers changed from: package-private */
public class TimeoutManager implements NgaMessageHandler.KeepAliveListener {
    private static final long SESSION_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(10);
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable mOnTimeout;
    private TimeoutCallback mTimeoutCallback;

    /* access modifiers changed from: package-private */
    public interface TimeoutCallback {
        void onTimeout();
    }

    TimeoutManager(Lazy<AssistManager> lazy) {
        this.mOnTimeout = new TimeoutManager$$ExternalSyntheticLambda0(this, lazy);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Lazy lazy) {
        TimeoutCallback timeoutCallback = this.mTimeoutCallback;
        if (timeoutCallback != null) {
            timeoutCallback.onTimeout();
            return;
        }
        Log.e("TimeoutManager", "Timeout occurred, but there was no callback provided");
        ((AssistManager) lazy.get()).hideAssist();
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.KeepAliveListener
    public void onKeepAlive(String str) {
        resetTimeout();
    }

    /* access modifiers changed from: package-private */
    public void resetTimeout() {
        this.mHandler.removeCallbacks(this.mOnTimeout);
        this.mHandler.postDelayed(this.mOnTimeout, SESSION_TIMEOUT_MS);
    }

    /* access modifiers changed from: package-private */
    public void setTimeoutCallback(TimeoutCallback timeoutCallback) {
        this.mTimeoutCallback = timeoutCallback;
    }
}

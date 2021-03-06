package com.google.android.systemui.assist.uihints;

import android.app.PendingIntent;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.shared.system.QuickStepContract;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.Lazy;

public class TouchInsideHandler implements NgaMessageHandler.ConfigInfoListener, View.OnClickListener, View.OnTouchListener {
    private final AssistLogger mAssistLogger;
    private Runnable mFallback;
    private boolean mGuardLocked;
    private boolean mGuarded;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mInGesturalMode;
    private PendingIntent mTouchInside;

    TouchInsideHandler(Lazy<AssistManager> lazy, NavigationModeController navigationModeController, AssistLogger assistLogger) {
        this.mAssistLogger = assistLogger;
        this.mFallback = new TouchInsideHandler$$ExternalSyntheticLambda2(lazy);
        onNavigationModeChange(navigationModeController.addListener(new TouchInsideHandler$$ExternalSyntheticLambda0(this)));
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(Lazy lazy) {
        ((AssistManager) lazy.get()).hideAssist();
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.ConfigInfoListener
    public void onConfigInfo(NgaMessageHandler.ConfigInfo configInfo) {
        this.mTouchInside = configInfo.onTouchInside;
    }

    public void onClick(View view) {
        onTouchInside();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.mInGesturalMode) {
            gestureModeOnTouch(view, motionEvent);
            return true;
        }
        nonGestureModeOnTouch(view, motionEvent);
        return true;
    }

    public void onTouchInside() {
        PendingIntent pendingIntent = this.mTouchInside;
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException unused) {
                Log.w("TouchInsideHandler", "Touch outside PendingIntent canceled");
                this.mFallback.run();
            }
        } else {
            this.mFallback.run();
        }
        this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_USER_DISMISS);
        MetricsLogger.action(new LogMaker(1716).setType(5).setSubtype(2));
    }

    private void gestureModeOnTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            onTouchInside();
        }
    }

    private void nonGestureModeOnTouch(View view, MotionEvent motionEvent) {
        if (this.mGuarded && !this.mGuardLocked && motionEvent.getAction() == 0) {
            this.mGuarded = false;
        } else if (!this.mGuarded && motionEvent.getAction() == 1) {
            onTouchInside();
        }
    }

    /* access modifiers changed from: package-private */
    public void maybeSetGuarded() {
        if (!this.mInGesturalMode) {
            this.mGuardLocked = true;
            this.mGuarded = true;
            this.mHandler.postDelayed(new TouchInsideHandler$$ExternalSyntheticLambda1(this), 500);
        }
    }

    /* access modifiers changed from: private */
    public void unlockGuard() {
        this.mGuardLocked = false;
    }

    /* access modifiers changed from: package-private */
    public void setFallback(Runnable runnable) {
        this.mFallback = runnable;
    }

    /* access modifiers changed from: private */
    public void onNavigationModeChange(int i) {
        boolean isGesturalMode = QuickStepContract.isGesturalMode(i);
        this.mInGesturalMode = isGesturalMode;
        if (isGesturalMode) {
            this.mGuardLocked = false;
            this.mGuarded = false;
        }
    }
}

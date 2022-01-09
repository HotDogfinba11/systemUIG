package com.android.systemui.statusbar.phone.ongoingcall;

import android.app.IActivityManager;
import android.app.IUidObserver;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.android.systemui.R$id;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.FeatureFlags;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController implements CallbackController<OngoingCallListener> {
    private final ActivityStarter activityStarter;
    private CallNotificationInfo callNotificationInfo;
    private View chipView;
    private final FeatureFlags featureFlags;
    private final IActivityManager iActivityManager;
    private boolean isCallAppVisible = true;
    private final OngoingCallLogger logger;
    private final List<OngoingCallListener> mListeners = new ArrayList();
    private final Executor mainExecutor;
    private final CommonNotifCollection notifCollection;
    private final OngoingCallController$notifListener$1 notifListener = new OngoingCallController$notifListener$1(this);
    private final SystemClock systemClock;
    private IUidObserver.Stub uidObserver;

    /* access modifiers changed from: private */
    public final boolean isProcessVisibleToUser(int i) {
        return i <= 2;
    }

    public OngoingCallController(CommonNotifCollection commonNotifCollection, FeatureFlags featureFlags2, SystemClock systemClock2, ActivityStarter activityStarter2, Executor executor, IActivityManager iActivityManager2, OngoingCallLogger ongoingCallLogger) {
        Intrinsics.checkNotNullParameter(commonNotifCollection, "notifCollection");
        Intrinsics.checkNotNullParameter(featureFlags2, "featureFlags");
        Intrinsics.checkNotNullParameter(systemClock2, "systemClock");
        Intrinsics.checkNotNullParameter(activityStarter2, "activityStarter");
        Intrinsics.checkNotNullParameter(executor, "mainExecutor");
        Intrinsics.checkNotNullParameter(iActivityManager2, "iActivityManager");
        Intrinsics.checkNotNullParameter(ongoingCallLogger, "logger");
        this.notifCollection = commonNotifCollection;
        this.featureFlags = featureFlags2;
        this.systemClock = systemClock2;
        this.activityStarter = activityStarter2;
        this.mainExecutor = executor;
        this.iActivityManager = iActivityManager2;
        this.logger = ongoingCallLogger;
    }

    public final void init() {
        if (this.featureFlags.isOngoingCallStatusBarChipEnabled()) {
            this.notifCollection.addCollectionListener(this.notifListener);
        }
    }

    public final void setChipView(View view) {
        Intrinsics.checkNotNullParameter(view, "chipView");
        tearDownChipView();
        this.chipView = view;
        if (hasOngoingCall()) {
            updateChip();
        }
    }

    public final void notifyChipVisibilityChanged(boolean z) {
        this.logger.logChipVisibilityChanged(z);
    }

    public final boolean hasOngoingCall() {
        CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
        return Intrinsics.areEqual(callNotificationInfo2 == null ? null : Boolean.valueOf(callNotificationInfo2.isOngoing()), Boolean.TRUE) && !this.isCallAppVisible;
    }

    public void addCallback(OngoingCallListener ongoingCallListener) {
        Intrinsics.checkNotNullParameter(ongoingCallListener, "listener");
        synchronized (this.mListeners) {
            if (!this.mListeners.contains(ongoingCallListener)) {
                this.mListeners.add(ongoingCallListener);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public void removeCallback(OngoingCallListener ongoingCallListener) {
        Intrinsics.checkNotNullParameter(ongoingCallListener, "listener");
        synchronized (this.mListeners) {
            this.mListeners.remove(ongoingCallListener);
        }
    }

    /* access modifiers changed from: private */
    public final void updateChip() {
        OngoingCallChronometer ongoingCallChronometer;
        View view;
        CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
        if (callNotificationInfo2 != null) {
            View view2 = this.chipView;
            if (view2 == null) {
                ongoingCallChronometer = null;
            } else {
                ongoingCallChronometer = getTimeView(view2);
            }
            if (view2 == null) {
                view = null;
            } else {
                view = view2.findViewById(R$id.ongoing_call_chip_background);
            }
            if (view2 == null || ongoingCallChronometer == null || view == null) {
                this.callNotificationInfo = null;
                if (OngoingCallControllerKt.access$getDEBUG$p()) {
                    Log.w("OngoingCallController", "Ongoing call chip view could not be found; Not displaying chip in status bar");
                    return;
                }
                return;
            }
            if (callNotificationInfo2.hasValidStartTime()) {
                ongoingCallChronometer.setShouldHideText(false);
                ongoingCallChronometer.setBase((callNotificationInfo2.getCallStartTime() - this.systemClock.currentTimeMillis()) + this.systemClock.elapsedRealtime());
                ongoingCallChronometer.start();
            } else {
                ongoingCallChronometer.setShouldHideText(true);
                ongoingCallChronometer.stop();
            }
            Intent intent = callNotificationInfo2.getIntent();
            if (intent != null) {
                view2.setOnClickListener(new OngoingCallController$updateChip$1$1(this, intent, view));
            }
            setUpUidObserver(callNotificationInfo2);
            Iterator<T> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onOngoingCallStateChanged(true);
            }
        }
    }

    private final void setUpUidObserver(CallNotificationInfo callNotificationInfo2) {
        this.isCallAppVisible = isProcessVisibleToUser(this.iActivityManager.getUidProcessState(callNotificationInfo2.getUid(), (String) null));
        IUidObserver.Stub stub = this.uidObserver;
        if (stub != null) {
            this.iActivityManager.unregisterUidObserver(stub);
        }
        OngoingCallController$setUpUidObserver$1 ongoingCallController$setUpUidObserver$1 = new OngoingCallController$setUpUidObserver$1(callNotificationInfo2, this);
        this.uidObserver = ongoingCallController$setUpUidObserver$1;
        this.iActivityManager.registerUidObserver(ongoingCallController$setUpUidObserver$1, 1, -1, (String) null);
    }

    /* access modifiers changed from: private */
    public final void removeChip() {
        this.callNotificationInfo = null;
        tearDownChipView();
        Iterator<T> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onOngoingCallStateChanged(true);
        }
        IUidObserver.Stub stub = this.uidObserver;
        if (stub != null) {
            this.iActivityManager.unregisterUidObserver(stub);
        }
    }

    public final Unit tearDownChipView() {
        OngoingCallChronometer timeView;
        View view = this.chipView;
        if (view == null || (timeView = getTimeView(view)) == null) {
            return null;
        }
        timeView.stop();
        return Unit.INSTANCE;
    }

    private final OngoingCallChronometer getTimeView(View view) {
        return (OngoingCallChronometer) view.findViewById(R$id.ongoing_call_chip_time);
    }

    /* access modifiers changed from: private */
    /* compiled from: OngoingCallController.kt */
    public static final class CallNotificationInfo {
        private final long callStartTime;
        private final Intent intent;
        private final boolean isOngoing;
        private final String key;
        private final int uid;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CallNotificationInfo)) {
                return false;
            }
            CallNotificationInfo callNotificationInfo = (CallNotificationInfo) obj;
            return Intrinsics.areEqual(this.key, callNotificationInfo.key) && this.callStartTime == callNotificationInfo.callStartTime && Intrinsics.areEqual(this.intent, callNotificationInfo.intent) && this.uid == callNotificationInfo.uid && this.isOngoing == callNotificationInfo.isOngoing;
        }

        public int hashCode() {
            int hashCode = ((this.key.hashCode() * 31) + Long.hashCode(this.callStartTime)) * 31;
            Intent intent2 = this.intent;
            int hashCode2 = (((hashCode + (intent2 == null ? 0 : intent2.hashCode())) * 31) + Integer.hashCode(this.uid)) * 31;
            boolean z = this.isOngoing;
            if (z) {
                z = true;
            }
            int i = z ? 1 : 0;
            int i2 = z ? 1 : 0;
            int i3 = z ? 1 : 0;
            return hashCode2 + i;
        }

        public String toString() {
            return "CallNotificationInfo(key=" + this.key + ", callStartTime=" + this.callStartTime + ", intent=" + this.intent + ", uid=" + this.uid + ", isOngoing=" + this.isOngoing + ')';
        }

        public CallNotificationInfo(String str, long j, Intent intent2, int i, boolean z) {
            Intrinsics.checkNotNullParameter(str, "key");
            this.key = str;
            this.callStartTime = j;
            this.intent = intent2;
            this.uid = i;
            this.isOngoing = z;
        }

        public final String getKey() {
            return this.key;
        }

        public final long getCallStartTime() {
            return this.callStartTime;
        }

        public final Intent getIntent() {
            return this.intent;
        }

        public final int getUid() {
            return this.uid;
        }

        public final boolean isOngoing() {
            return this.isOngoing;
        }

        public final boolean hasValidStartTime() {
            return this.callStartTime > 0;
        }
    }
}

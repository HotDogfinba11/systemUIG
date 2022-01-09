package com.android.systemui.statusbar.events;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Process;
import android.provider.DeviceConfig;
import android.view.View;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.phone.StatusBarWindowController;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SystemStatusAnimationScheduler.kt */
public final class SystemStatusAnimationScheduler implements CallbackController<SystemStatusAnimationCallback>, Dumpable {
    public static final Companion Companion = new Companion(null);
    private int animationState;
    private Runnable cancelExecutionRunnable;
    private final SystemEventChipAnimationController chipAnimationController;
    private final ValueAnimator.AnimatorUpdateListener chipUpdateListener;
    private final SystemEventCoordinator coordinator;
    private final DumpManager dumpManager;
    private final DelayableExecutor executor;
    private boolean hasPersistentDot;
    private final Set<SystemStatusAnimationCallback> listeners = new LinkedHashSet();
    private StatusEvent scheduledEvent;
    private final StatusBarWindowController statusBarWindowController;
    private final SystemStatusAnimationScheduler$systemAnimatorAdapter$1 systemAnimatorAdapter;
    private final SystemClock systemClock;
    private final ValueAnimator.AnimatorUpdateListener systemUpdateListener;

    public SystemStatusAnimationScheduler(SystemEventCoordinator systemEventCoordinator, SystemEventChipAnimationController systemEventChipAnimationController, StatusBarWindowController statusBarWindowController2, DumpManager dumpManager2, SystemClock systemClock2, DelayableExecutor delayableExecutor) {
        Intrinsics.checkNotNullParameter(systemEventCoordinator, "coordinator");
        Intrinsics.checkNotNullParameter(systemEventChipAnimationController, "chipAnimationController");
        Intrinsics.checkNotNullParameter(statusBarWindowController2, "statusBarWindowController");
        Intrinsics.checkNotNullParameter(dumpManager2, "dumpManager");
        Intrinsics.checkNotNullParameter(systemClock2, "systemClock");
        Intrinsics.checkNotNullParameter(delayableExecutor, "executor");
        this.coordinator = systemEventCoordinator;
        this.chipAnimationController = systemEventChipAnimationController;
        this.statusBarWindowController = statusBarWindowController2;
        this.dumpManager = dumpManager2;
        this.systemClock = systemClock2;
        this.executor = delayableExecutor;
        systemEventCoordinator.attachScheduler(this);
        dumpManager2.registerDumpable("SystemStatusAnimationScheduler", this);
        this.systemUpdateListener = new SystemStatusAnimationScheduler$systemUpdateListener$1(this);
        this.systemAnimatorAdapter = new SystemStatusAnimationScheduler$systemAnimatorAdapter$1(this);
        this.chipUpdateListener = new SystemStatusAnimationScheduler$chipUpdateListener$1(this);
    }

    /* compiled from: SystemStatusAnimationScheduler.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private final boolean isImmersiveIndicatorEnabled() {
        return DeviceConfig.getBoolean("privacy", "enable_immersive_indicator", true);
    }

    public final int getAnimationState() {
        return this.animationState;
    }

    public final boolean getHasPersistentDot() {
        return this.hasPersistentDot;
    }

    public final void onStatusEvent(StatusEvent statusEvent) {
        int i;
        Intrinsics.checkNotNullParameter(statusEvent, "event");
        if (!isTooEarly() && isImmersiveIndicatorEnabled()) {
            Assert.isMainThread();
            int priority = statusEvent.getPriority();
            StatusEvent statusEvent2 = this.scheduledEvent;
            if (priority <= (statusEvent2 == null ? -1 : statusEvent2.getPriority()) || (i = this.animationState) == 3 || i == 4 || !statusEvent.getForceVisible()) {
                StatusEvent statusEvent3 = this.scheduledEvent;
                if (Intrinsics.areEqual(statusEvent3 == null ? null : Boolean.valueOf(statusEvent3.shouldUpdateFromEvent(statusEvent)), Boolean.TRUE)) {
                    StatusEvent statusEvent4 = this.scheduledEvent;
                    if (statusEvent4 != null) {
                        statusEvent4.updateFromEvent(statusEvent);
                    }
                    if (statusEvent.getForceVisible()) {
                        this.hasPersistentDot = true;
                        notifyTransitionToPersistentDot();
                        return;
                    }
                    return;
                }
                return;
            }
            scheduleEvent(statusEvent);
        }
    }

    private final void clearDotIfVisible() {
        notifyHidePersistentDot();
    }

    public final void setShouldShowPersistentPrivacyIndicator(boolean z) {
        if (this.hasPersistentDot != z && isImmersiveIndicatorEnabled()) {
            this.hasPersistentDot = z;
            if (!z) {
                clearDotIfVisible();
            }
        }
    }

    private final boolean isTooEarly() {
        return this.systemClock.uptimeMillis() - Process.getStartUptimeMillis() < 5000;
    }

    private final void scheduleEvent(StatusEvent statusEvent) {
        this.scheduledEvent = statusEvent;
        if (statusEvent.getForceVisible()) {
            this.hasPersistentDot = true;
        }
        if (statusEvent.getShowAnimation() || !statusEvent.getForceVisible()) {
            this.cancelExecutionRunnable = this.executor.executeDelayed(new SystemStatusAnimationScheduler$scheduleEvent$1(this), 0);
            return;
        }
        notifyTransitionToPersistentDot();
        this.scheduledEvent = null;
    }

    /* access modifiers changed from: private */
    public final Animator notifyTransitionToPersistentDot() {
        Set<SystemStatusAnimationCallback> set = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = set.iterator();
        while (true) {
            String str = null;
            if (!it.hasNext()) {
                break;
            }
            T next = it.next();
            StatusEvent statusEvent = this.scheduledEvent;
            if (statusEvent != null) {
                str = statusEvent.getContentDescription();
            }
            Animator onSystemStatusAnimationTransitionToPersistentDot = next.onSystemStatusAnimationTransitionToPersistentDot(str);
            if (onSystemStatusAnimationTransitionToPersistentDot != null) {
                arrayList.add(onSystemStatusAnimationTransitionToPersistentDot);
            }
        }
        if (!(!arrayList.isEmpty())) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        return animatorSet;
    }

    private final Animator notifyHidePersistentDot() {
        Set<SystemStatusAnimationCallback> set = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            Animator onHidePersistentDot = it.next().onHidePersistentDot();
            if (onHidePersistentDot != null) {
                arrayList.add(onHidePersistentDot);
            }
        }
        if (this.animationState == 4) {
            this.animationState = 0;
        }
        if (!(!arrayList.isEmpty())) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        return animatorSet;
    }

    /* access modifiers changed from: private */
    public final void notifySystemStart() {
        Iterator<T> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onSystemChromeAnimationStart();
        }
    }

    /* access modifiers changed from: private */
    public final void notifySystemFinish() {
        Iterator<T> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onSystemChromeAnimationEnd();
        }
    }

    /* access modifiers changed from: private */
    public final void notifySystemAnimationUpdate(ValueAnimator valueAnimator) {
        Iterator<T> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onSystemChromeAnimationUpdate(valueAnimator);
        }
    }

    public void addCallback(SystemStatusAnimationCallback systemStatusAnimationCallback) {
        Intrinsics.checkNotNullParameter(systemStatusAnimationCallback, "listener");
        Assert.isMainThread();
        if (this.listeners.isEmpty()) {
            this.coordinator.startObserving();
        }
        this.listeners.add(systemStatusAnimationCallback);
    }

    public void removeCallback(SystemStatusAnimationCallback systemStatusAnimationCallback) {
        Intrinsics.checkNotNullParameter(systemStatusAnimationCallback, "listener");
        Assert.isMainThread();
        this.listeners.remove(systemStatusAnimationCallback);
        if (this.listeners.isEmpty()) {
            this.coordinator.stopObserving();
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println(Intrinsics.stringPlus("Scheduled event: ", this.scheduledEvent));
        printWriter.println(Intrinsics.stringPlus("Has persistent privacy dot: ", Boolean.valueOf(this.hasPersistentDot)));
        printWriter.println(Intrinsics.stringPlus("Animation state: ", Integer.valueOf(this.animationState)));
        printWriter.println("Listeners:");
        if (this.listeners.isEmpty()) {
            printWriter.println("(none)");
            return;
        }
        Iterator<T> it = this.listeners.iterator();
        while (it.hasNext()) {
            printWriter.println(Intrinsics.stringPlus("  ", it.next()));
        }
    }

    /* compiled from: SystemStatusAnimationScheduler.kt */
    public final class ChipAnimatorAdapter extends AnimatorListenerAdapter {
        private final int endState;
        final /* synthetic */ SystemStatusAnimationScheduler this$0;
        private final Function1<Context, View> viewCreator;

        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.Context, ? extends android.view.View> */
        /* JADX WARN: Multi-variable type inference failed */
        public ChipAnimatorAdapter(SystemStatusAnimationScheduler systemStatusAnimationScheduler, int i, Function1<? super Context, ? extends View> function1) {
            Intrinsics.checkNotNullParameter(systemStatusAnimationScheduler, "this$0");
            Intrinsics.checkNotNullParameter(function1, "viewCreator");
            this.this$0 = systemStatusAnimationScheduler;
            this.endState = i;
            this.viewCreator = function1;
        }

        public void onAnimationEnd(Animator animator) {
            int i;
            this.this$0.chipAnimationController.onChipAnimationEnd(this.this$0.getAnimationState());
            SystemStatusAnimationScheduler systemStatusAnimationScheduler = this.this$0;
            if (this.endState != 4 || systemStatusAnimationScheduler.getHasPersistentDot()) {
                i = this.endState;
            } else {
                i = 0;
            }
            systemStatusAnimationScheduler.animationState = i;
        }

        public void onAnimationStart(Animator animator) {
            this.this$0.chipAnimationController.onChipAnimationStart(this.viewCreator, this.this$0.getAnimationState());
        }
    }
}

package com.android.systemui;

import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.Lazy;
import java.util.Optional;

public class ActivityStarterDelegate implements ActivityStarter {
    private Optional<Lazy<StatusBar>> mActualStarter;

    public static /* synthetic */ void $r8$lambda$7CADyxpqsE3shlaxQ_Y_7aUHr7A(Intent intent, int i, Lazy lazy) {
        lambda$postStartActivityDismissingKeyguard$9(intent, i, lazy);
    }

    public ActivityStarterDelegate(Optional<Lazy<StatusBar>> optional) {
        this.mActualStarter = optional;
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda0(pendingIntent));
    }

    public static /* synthetic */ void lambda$startPendingIntentDismissingKeyguard$0(PendingIntent pendingIntent, Lazy lazy) {
        ((StatusBar) lazy.get()).startPendingIntentDismissingKeyguard(pendingIntent);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda3(pendingIntent, runnable));
    }

    public static /* synthetic */ void lambda$startPendingIntentDismissingKeyguard$1(PendingIntent pendingIntent, Runnable runnable, Lazy lazy) {
        ((StatusBar) lazy.get()).startPendingIntentDismissingKeyguard(pendingIntent, runnable);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda4(pendingIntent, runnable, view));
    }

    public static /* synthetic */ void lambda$startPendingIntentDismissingKeyguard$2(PendingIntent pendingIntent, Runnable runnable, View view, Lazy lazy) {
        ((StatusBar) lazy.get()).startPendingIntentDismissingKeyguard(pendingIntent, runnable, view);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, ActivityLaunchAnimator.Controller controller) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda5(pendingIntent, runnable, controller));
    }

    public static /* synthetic */ void lambda$startPendingIntentDismissingKeyguard$3(PendingIntent pendingIntent, Runnable runnable, ActivityLaunchAnimator.Controller controller, Lazy lazy) {
        ((StatusBar) lazy.get()).startPendingIntentDismissingKeyguard(pendingIntent, runnable, controller);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean z, boolean z2, int i) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda12(intent, z, z2, i));
    }

    public static /* synthetic */ void lambda$startActivity$4(Intent intent, boolean z, boolean z2, int i, Lazy lazy) {
        ((StatusBar) lazy.get()).startActivity(intent, z, z2, i);
    }

    public static /* synthetic */ void lambda$startActivity$5(Intent intent, boolean z, Lazy lazy) {
        ((StatusBar) lazy.get()).startActivity(intent, z);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean z) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda8(intent, z));
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda9(intent, z, controller));
    }

    public static /* synthetic */ void lambda$startActivity$6(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, Lazy lazy) {
        ((StatusBar) lazy.get()).startActivity(intent, z, controller);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean z, boolean z2) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda11(intent, z, z2));
    }

    public static /* synthetic */ void lambda$startActivity$7(Intent intent, boolean z, boolean z2, Lazy lazy) {
        ((StatusBar) lazy.get()).startActivity(intent, z, z2);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(Intent intent, boolean z, ActivityStarter.Callback callback) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda10(intent, z, callback));
    }

    public static /* synthetic */ void lambda$startActivity$8(Intent intent, boolean z, ActivityStarter.Callback callback, Lazy lazy) {
        ((StatusBar) lazy.get()).startActivity(intent, z, callback);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(Intent intent, int i) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda6(intent, i));
    }

    private static /* synthetic */ void lambda$postStartActivityDismissingKeyguard$9(Intent intent, int i, Lazy lazy) {
        ((StatusBar) lazy.get()).postStartActivityDismissingKeyguard(intent, i);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(Intent intent, int i, ActivityLaunchAnimator.Controller controller) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda7(intent, i, controller));
    }

    public static /* synthetic */ void lambda$postStartActivityDismissingKeyguard$10(Intent intent, int i, ActivityLaunchAnimator.Controller controller, Lazy lazy) {
        ((StatusBar) lazy.get()).postStartActivityDismissingKeyguard(intent, i, controller);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(PendingIntent pendingIntent) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda1(pendingIntent));
    }

    public static /* synthetic */ void lambda$postStartActivityDismissingKeyguard$11(PendingIntent pendingIntent, Lazy lazy) {
        ((StatusBar) lazy.get()).postStartActivityDismissingKeyguard(pendingIntent);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(PendingIntent pendingIntent, ActivityLaunchAnimator.Controller controller) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda2(pendingIntent, controller));
    }

    public static /* synthetic */ void lambda$postStartActivityDismissingKeyguard$12(PendingIntent pendingIntent, ActivityLaunchAnimator.Controller controller, Lazy lazy) {
        ((StatusBar) lazy.get()).postStartActivityDismissingKeyguard(pendingIntent, controller);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postQSRunnableDismissingKeyguard(Runnable runnable) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda14(runnable));
    }

    public static /* synthetic */ void lambda$postQSRunnableDismissingKeyguard$13(Runnable runnable, Lazy lazy) {
        ((StatusBar) lazy.get()).postQSRunnableDismissingKeyguard(runnable);
    }

    public static /* synthetic */ void lambda$dismissKeyguardThenExecute$14(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, Lazy lazy) {
        ((StatusBar) lazy.get()).dismissKeyguardThenExecute(onDismissAction, runnable, z);
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void dismissKeyguardThenExecute(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        this.mActualStarter.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda13(onDismissAction, runnable, z));
    }
}

package com.android.systemui.statusbar.policy;

import android.app.PendingIntent;
import android.util.Log;
import android.view.View;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.NotificationUiAdjustment;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartReplyStateInflaterKt {
    private static final boolean DEBUG = Log.isLoggable("SmartReplyViewInflater", 3);

    public static final boolean shouldShowSmartReplyView(NotificationEntry notificationEntry, InflatedSmartReplyState inflatedSmartReplyState) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        Intrinsics.checkNotNullParameter(inflatedSmartReplyState, "smartReplyState");
        if ((inflatedSmartReplyState.getSmartReplies() != null || inflatedSmartReplyState.getSmartActions() != null) && !notificationEntry.getSbn().getNotification().extras.getBoolean("android.remoteInputSpinner", false)) {
            return !notificationEntry.getSbn().getNotification().extras.getBoolean("android.hideSmartReplies", false);
        }
        return false;
    }

    public static final boolean areSuggestionsSimilar(InflatedSmartReplyState inflatedSmartReplyState, InflatedSmartReplyState inflatedSmartReplyState2) {
        if (inflatedSmartReplyState == inflatedSmartReplyState2) {
            return true;
        }
        return inflatedSmartReplyState != null && inflatedSmartReplyState2 != null && inflatedSmartReplyState.getHasPhishingAction() == inflatedSmartReplyState2.getHasPhishingAction() && Intrinsics.areEqual(inflatedSmartReplyState.getSmartRepliesList(), inflatedSmartReplyState2.getSmartRepliesList()) && Intrinsics.areEqual(inflatedSmartReplyState.getSuppressedActionIndices(), inflatedSmartReplyState2.getSuppressedActionIndices()) && !NotificationUiAdjustment.areDifferent(inflatedSmartReplyState.getSmartActionsList(), inflatedSmartReplyState2.getSmartActionsList());
    }

    /* access modifiers changed from: private */
    public static final void executeWhenUnlocked(KeyguardDismissUtil keyguardDismissUtil, boolean z, Function0<Boolean> function0) {
        keyguardDismissUtil.executeWhenUnlocked(new SmartReplyStateInflaterKt$sam$com_android_systemui_plugins_ActivityStarter_OnDismissAction$0(function0), z, false);
    }

    /* access modifiers changed from: private */
    public static final void startPendingIntentDismissingKeyguard(ActivityStarter activityStarter, PendingIntent pendingIntent, View view, Function0<Unit> function0) {
        activityStarter.startPendingIntentDismissingKeyguard(pendingIntent, new SmartReplyStateInflaterKt$startPendingIntentDismissingKeyguard$1(function0), view);
    }
}

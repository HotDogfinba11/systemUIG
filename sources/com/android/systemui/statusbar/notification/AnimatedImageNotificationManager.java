package com.android.systemui.statusbar.notification;

import android.graphics.drawable.AnimatedImageDrawable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: ConversationNotifications.kt */
public final class AnimatedImageNotificationManager {
    private final HeadsUpManager headsUpManager;
    private boolean isStatusBarExpanded;
    private final NotificationEntryManager notificationEntryManager;
    private final StatusBarStateController statusBarStateController;

    public AnimatedImageNotificationManager(NotificationEntryManager notificationEntryManager2, HeadsUpManager headsUpManager2, StatusBarStateController statusBarStateController2) {
        Intrinsics.checkNotNullParameter(notificationEntryManager2, "notificationEntryManager");
        Intrinsics.checkNotNullParameter(headsUpManager2, "headsUpManager");
        Intrinsics.checkNotNullParameter(statusBarStateController2, "statusBarStateController");
        this.notificationEntryManager = notificationEntryManager2;
        this.headsUpManager = headsUpManager2;
        this.statusBarStateController = statusBarStateController2;
    }

    public final void bind() {
        this.headsUpManager.addListener(new AnimatedImageNotificationManager$bind$1(this));
        this.statusBarStateController.addCallback(new AnimatedImageNotificationManager$bind$2(this));
        this.notificationEntryManager.addNotificationEntryListener(new AnimatedImageNotificationManager$bind$3(this));
    }

    /* access modifiers changed from: private */
    public final void updateAnimatedImageDrawables(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        NotificationContentView[] layouts = expandableNotificationRow.getLayouts();
        Sequence sequence = layouts == null ? null : ArraysKt___ArraysKt.asSequence(layouts);
        if (sequence == null) {
            sequence = SequencesKt__SequencesKt.emptySequence();
        }
        for (AnimatedImageDrawable animatedImageDrawable : SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.flatMap(SequencesKt___SequencesKt.flatMap(SequencesKt___SequencesKt.flatMap(sequence, AnimatedImageNotificationManager$updateAnimatedImageDrawables$1.INSTANCE), AnimatedImageNotificationManager$updateAnimatedImageDrawables$2.INSTANCE), AnimatedImageNotificationManager$updateAnimatedImageDrawables$3.INSTANCE), AnimatedImageNotificationManager$updateAnimatedImageDrawables$4.INSTANCE)) {
            if (z) {
                animatedImageDrawable.start();
            } else {
                animatedImageDrawable.stop();
            }
        }
    }
}

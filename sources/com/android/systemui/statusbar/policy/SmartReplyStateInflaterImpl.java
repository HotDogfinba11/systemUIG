package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.ContextThemeWrapper;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartReplyStateInflaterImpl implements SmartReplyStateInflater {
    private final ActivityManagerWrapper activityManagerWrapper;
    private final SmartReplyConstants constants;
    private final DevicePolicyManagerWrapper devicePolicyManagerWrapper;
    private final PackageManagerWrapper packageManagerWrapper;
    private final SmartActionInflater smartActionsInflater;
    private final SmartReplyInflater smartRepliesInflater;

    public SmartReplyStateInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityManagerWrapper activityManagerWrapper2, PackageManagerWrapper packageManagerWrapper2, DevicePolicyManagerWrapper devicePolicyManagerWrapper2, SmartReplyInflater smartReplyInflater, SmartActionInflater smartActionInflater) {
        Intrinsics.checkNotNullParameter(smartReplyConstants, "constants");
        Intrinsics.checkNotNullParameter(activityManagerWrapper2, "activityManagerWrapper");
        Intrinsics.checkNotNullParameter(packageManagerWrapper2, "packageManagerWrapper");
        Intrinsics.checkNotNullParameter(devicePolicyManagerWrapper2, "devicePolicyManagerWrapper");
        Intrinsics.checkNotNullParameter(smartReplyInflater, "smartRepliesInflater");
        Intrinsics.checkNotNullParameter(smartActionInflater, "smartActionsInflater");
        this.constants = smartReplyConstants;
        this.activityManagerWrapper = activityManagerWrapper2;
        this.packageManagerWrapper = packageManagerWrapper2;
        this.devicePolicyManagerWrapper = devicePolicyManagerWrapper2;
        this.smartRepliesInflater = smartReplyInflater;
        this.smartActionsInflater = smartActionInflater;
    }

    @Override // com.android.systemui.statusbar.policy.SmartReplyStateInflater
    public InflatedSmartReplyState inflateSmartReplyState(NotificationEntry notificationEntry) {
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        return chooseSmartRepliesAndActions(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.policy.SmartReplyStateInflater
    public InflatedSmartReplyViewHolder inflateSmartReplyViewHolder(Context context, Context context2, NotificationEntry notificationEntry, InflatedSmartReplyState inflatedSmartReplyState, InflatedSmartReplyState inflatedSmartReplyState2) {
        boolean z;
        Sequence sequence;
        Intrinsics.checkNotNullParameter(context, "sysuiContext");
        Intrinsics.checkNotNullParameter(context2, "notifPackageContext");
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        Intrinsics.checkNotNullParameter(inflatedSmartReplyState2, "newSmartReplyState");
        Sequence sequence2 = null;
        if (!SmartReplyStateInflaterKt.shouldShowSmartReplyView(notificationEntry, inflatedSmartReplyState2)) {
            return new InflatedSmartReplyViewHolder(null, null);
        }
        boolean z2 = !SmartReplyStateInflaterKt.areSuggestionsSimilar(inflatedSmartReplyState, inflatedSmartReplyState2);
        SmartReplyView inflate = SmartReplyView.inflate(context, this.constants);
        SmartReplyView.SmartReplies smartReplies = inflatedSmartReplyState2.getSmartReplies();
        if (smartReplies == null) {
            z = false;
        } else {
            z = smartReplies.fromAssistant;
        }
        inflate.setSmartRepliesGeneratedByAssistant(z);
        if (smartReplies == null) {
            sequence = null;
        } else {
            List<CharSequence> list = smartReplies.choices;
            Intrinsics.checkNotNullExpressionValue(list, "smartReplies.choices");
            sequence = SequencesKt___SequencesKt.mapIndexed(CollectionsKt___CollectionsKt.asSequence(list), new SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1(this, inflate, notificationEntry, smartReplies, z2));
        }
        if (sequence == null) {
            sequence = SequencesKt__SequencesKt.emptySequence();
        }
        SmartReplyView.SmartActions smartActions = inflatedSmartReplyState2.getSmartActions();
        if (smartActions != null) {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context2, context.getTheme());
            List<Notification.Action> list2 = smartActions.actions;
            Intrinsics.checkNotNullExpressionValue(list2, "smartActions.actions");
            sequence2 = SequencesKt___SequencesKt.mapIndexed(SequencesKt___SequencesKt.filter(CollectionsKt___CollectionsKt.asSequence(list2), SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$1.INSTANCE), new SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$2(this, inflate, notificationEntry, smartActions, z2, contextThemeWrapper));
        }
        if (sequence2 == null) {
            sequence2 = SequencesKt__SequencesKt.emptySequence();
        }
        return new InflatedSmartReplyViewHolder(inflate, SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.plus(sequence, sequence2)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0112  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0145  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0116 A[EDGE_INSN: B:92:0x0116->B:56:0x0116 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.android.systemui.statusbar.policy.InflatedSmartReplyState chooseSmartRepliesAndActions(com.android.systemui.statusbar.notification.collection.NotificationEntry r13) {
        /*
        // Method dump skipped, instructions count: 399
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.chooseSmartRepliesAndActions(com.android.systemui.statusbar.notification.collection.NotificationEntry):com.android.systemui.statusbar.policy.InflatedSmartReplyState");
    }

    private final List<Notification.Action> filterAllowlistedLockTaskApps(List<? extends Notification.Action> list) {
        Intent intent;
        ArrayList arrayList = new ArrayList();
        for (T t : list) {
            PendingIntent pendingIntent = ((Notification.Action) t).actionIntent;
            boolean z = false;
            ResolveInfo resolveInfo = null;
            if (!(pendingIntent == null || (intent = pendingIntent.getIntent()) == null)) {
                resolveInfo = this.packageManagerWrapper.resolveActivity(intent, 0);
            }
            if (resolveInfo != null) {
                z = this.devicePolicyManagerWrapper.isLockTaskPermitted(resolveInfo.activityInfo.packageName);
            }
            if (z) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }
}

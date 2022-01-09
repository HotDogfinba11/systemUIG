package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.SmartReplyView;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: SmartReplyStateInflater.kt */
final class SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$2 extends Lambda implements Function2<Integer, Notification.Action, Button> {
    final /* synthetic */ boolean $delayOnClickListener;
    final /* synthetic */ NotificationEntry $entry;
    final /* synthetic */ SmartReplyView.SmartActions $smartActions;
    final /* synthetic */ SmartReplyView $smartReplyView;
    final /* synthetic */ ContextThemeWrapper $themedPackageContext;
    final /* synthetic */ SmartReplyStateInflaterImpl this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$2(SmartReplyStateInflaterImpl smartReplyStateInflaterImpl, SmartReplyView smartReplyView, NotificationEntry notificationEntry, SmartReplyView.SmartActions smartActions, boolean z, ContextThemeWrapper contextThemeWrapper) {
        super(2);
        this.this$0 = smartReplyStateInflaterImpl;
        this.$smartReplyView = smartReplyView;
        this.$entry = notificationEntry;
        this.$smartActions = smartActions;
        this.$delayOnClickListener = z;
        this.$themedPackageContext = contextThemeWrapper;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.Object] */
    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Button invoke(Integer num, Notification.Action action) {
        return invoke(num.intValue(), action);
    }

    public final Button invoke(int i, Notification.Action action) {
        SmartActionInflater smartActionInflater = this.this$0.smartActionsInflater;
        SmartReplyView smartReplyView = this.$smartReplyView;
        Intrinsics.checkNotNullExpressionValue(smartReplyView, "smartReplyView");
        NotificationEntry notificationEntry = this.$entry;
        SmartReplyView.SmartActions smartActions = this.$smartActions;
        Intrinsics.checkNotNullExpressionValue(action, "action");
        return smartActionInflater.inflateActionButton(smartReplyView, notificationEntry, smartActions, i, action, this.$delayOnClickListener, this.$themedPackageContext);
    }
}

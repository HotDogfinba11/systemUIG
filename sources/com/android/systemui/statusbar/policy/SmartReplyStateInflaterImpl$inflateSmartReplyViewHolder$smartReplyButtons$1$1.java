package com.android.systemui.statusbar.policy;

import android.widget.Button;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.SmartReplyView;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: SmartReplyStateInflater.kt */
final class SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1 extends Lambda implements Function2<Integer, CharSequence, Button> {
    final /* synthetic */ boolean $delayOnClickListener;
    final /* synthetic */ NotificationEntry $entry;
    final /* synthetic */ SmartReplyView.SmartReplies $smartReplies;
    final /* synthetic */ SmartReplyView $smartReplyView;
    final /* synthetic */ SmartReplyStateInflaterImpl this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1(SmartReplyStateInflaterImpl smartReplyStateInflaterImpl, SmartReplyView smartReplyView, NotificationEntry notificationEntry, SmartReplyView.SmartReplies smartReplies, boolean z) {
        super(2);
        this.this$0 = smartReplyStateInflaterImpl;
        this.$smartReplyView = smartReplyView;
        this.$entry = notificationEntry;
        this.$smartReplies = smartReplies;
        this.$delayOnClickListener = z;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.Object] */
    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Button invoke(Integer num, CharSequence charSequence) {
        return invoke(num.intValue(), charSequence);
    }

    public final Button invoke(int i, CharSequence charSequence) {
        SmartReplyInflater smartReplyInflater = this.this$0.smartRepliesInflater;
        SmartReplyView smartReplyView = this.$smartReplyView;
        Intrinsics.checkNotNullExpressionValue(smartReplyView, "smartReplyView");
        NotificationEntry notificationEntry = this.$entry;
        SmartReplyView.SmartReplies smartReplies = this.$smartReplies;
        Intrinsics.checkNotNullExpressionValue(charSequence, "choice");
        return smartReplyInflater.inflateReplyButton(smartReplyView, notificationEntry, smartReplies, i, charSequence, this.$delayOnClickListener);
    }
}

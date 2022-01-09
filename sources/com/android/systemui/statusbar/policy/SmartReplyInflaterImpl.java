package com.android.systemui.statusbar.policy;

import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.systemui.R$layout;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartReplyInflaterImpl implements SmartReplyInflater {
    private final SmartReplyConstants constants;
    private final Context context;
    private final KeyguardDismissUtil keyguardDismissUtil;
    private final NotificationRemoteInputManager remoteInputManager;
    private final SmartReplyController smartReplyController;

    public SmartReplyInflaterImpl(SmartReplyConstants smartReplyConstants, KeyguardDismissUtil keyguardDismissUtil2, NotificationRemoteInputManager notificationRemoteInputManager, SmartReplyController smartReplyController2, Context context2) {
        Intrinsics.checkNotNullParameter(smartReplyConstants, "constants");
        Intrinsics.checkNotNullParameter(keyguardDismissUtil2, "keyguardDismissUtil");
        Intrinsics.checkNotNullParameter(notificationRemoteInputManager, "remoteInputManager");
        Intrinsics.checkNotNullParameter(smartReplyController2, "smartReplyController");
        Intrinsics.checkNotNullParameter(context2, "context");
        this.constants = smartReplyConstants;
        this.keyguardDismissUtil = keyguardDismissUtil2;
        this.remoteInputManager = notificationRemoteInputManager;
        this.smartReplyController = smartReplyController2;
        this.context = context2;
    }

    @Override // com.android.systemui.statusbar.policy.SmartReplyInflater
    public Button inflateReplyButton(SmartReplyView smartReplyView, NotificationEntry notificationEntry, SmartReplyView.SmartReplies smartReplies, int i, CharSequence charSequence, boolean z) {
        Intrinsics.checkNotNullParameter(smartReplyView, "parent");
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        Intrinsics.checkNotNullParameter(smartReplies, "smartReplies");
        Intrinsics.checkNotNullParameter(charSequence, "choice");
        View inflate = LayoutInflater.from(smartReplyView.getContext()).inflate(R$layout.smart_reply_button, (ViewGroup) smartReplyView, false);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.widget.Button");
        Button button = (Button) inflate;
        button.setText(charSequence);
        View.OnClickListener smartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1 = new SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1(this, notificationEntry, smartReplies, i, smartReplyView, button, charSequence);
        if (z) {
            smartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1 = new DelayedOnClickListener(smartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1, this.constants.getOnClickInitDelay());
        }
        button.setOnClickListener(smartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1);
        button.setAccessibilityDelegate(new SmartReplyInflaterImpl$inflateReplyButton$1$1(smartReplyView));
        ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
        Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams");
        ((SmartReplyView.LayoutParams) layoutParams).mButtonType = SmartReplyView.SmartButtonType.REPLY;
        return button;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void onSmartReplyClick(NotificationEntry notificationEntry, SmartReplyView.SmartReplies smartReplies, int i, SmartReplyView smartReplyView, Button button, CharSequence charSequence) {
        SmartReplyStateInflaterKt.executeWhenUnlocked(this.keyguardDismissUtil, !notificationEntry.isRowPinned(), new SmartReplyInflaterImpl$onSmartReplyClick$1(this, smartReplies, button, charSequence, i, notificationEntry, smartReplyView));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final Intent createRemoteInputIntent(SmartReplyView.SmartReplies smartReplies, CharSequence charSequence) {
        Bundle bundle = new Bundle();
        bundle.putString(smartReplies.remoteInput.getResultKey(), charSequence.toString());
        Intent addFlags = new Intent().addFlags(268435456);
        RemoteInput.addResultsToIntent(new RemoteInput[]{smartReplies.remoteInput}, addFlags, bundle);
        RemoteInput.setResultsSource(addFlags, 1);
        Intrinsics.checkNotNullExpressionValue(addFlags, "intent");
        return addFlags;
    }
}

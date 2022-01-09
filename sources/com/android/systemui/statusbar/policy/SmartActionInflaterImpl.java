package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.systemui.R$dimen;
import com.android.systemui.R$layout;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartActionInflaterImpl implements SmartActionInflater {
    private final ActivityStarter activityStarter;
    private final SmartReplyConstants constants;
    private final HeadsUpManager headsUpManager;
    private final SmartReplyController smartReplyController;

    public SmartActionInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityStarter activityStarter2, SmartReplyController smartReplyController2, HeadsUpManager headsUpManager2) {
        Intrinsics.checkNotNullParameter(smartReplyConstants, "constants");
        Intrinsics.checkNotNullParameter(activityStarter2, "activityStarter");
        Intrinsics.checkNotNullParameter(smartReplyController2, "smartReplyController");
        Intrinsics.checkNotNullParameter(headsUpManager2, "headsUpManager");
        this.constants = smartReplyConstants;
        this.activityStarter = activityStarter2;
        this.smartReplyController = smartReplyController2;
        this.headsUpManager = headsUpManager2;
    }

    @Override // com.android.systemui.statusbar.policy.SmartActionInflater
    public Button inflateActionButton(ViewGroup viewGroup, NotificationEntry notificationEntry, SmartReplyView.SmartActions smartActions, int i, Notification.Action action, boolean z, Context context) {
        Intrinsics.checkNotNullParameter(viewGroup, "parent");
        Intrinsics.checkNotNullParameter(notificationEntry, "entry");
        Intrinsics.checkNotNullParameter(smartActions, "smartActions");
        Intrinsics.checkNotNullParameter(action, "action");
        Intrinsics.checkNotNullParameter(context, "packageContext");
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.smart_action_button, viewGroup, false);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.widget.Button");
        Button button = (Button) inflate;
        button.setText(action.title);
        Drawable loadDrawable = action.getIcon().loadDrawable(context);
        int dimensionPixelSize = button.getContext().getResources().getDimensionPixelSize(R$dimen.smart_action_button_icon_size);
        loadDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        button.setCompoundDrawables(loadDrawable, null, null, null);
        View.OnClickListener smartActionInflaterImpl$inflateActionButton$1$onClickListener$1 = new SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1(this, notificationEntry, smartActions, i, action);
        if (z) {
            smartActionInflaterImpl$inflateActionButton$1$onClickListener$1 = new DelayedOnClickListener(smartActionInflaterImpl$inflateActionButton$1$onClickListener$1, this.constants.getOnClickInitDelay());
        }
        button.setOnClickListener(smartActionInflaterImpl$inflateActionButton$1$onClickListener$1);
        ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
        Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams");
        ((SmartReplyView.LayoutParams) layoutParams).mButtonType = SmartReplyView.SmartButtonType.ACTION;
        return button;
    }

    /* access modifiers changed from: private */
    public final void onSmartActionClick(NotificationEntry notificationEntry, SmartReplyView.SmartActions smartActions, int i, Notification.Action action) {
        if (!smartActions.fromAssistant || 11 != action.getSemanticAction()) {
            ActivityStarter activityStarter2 = this.activityStarter;
            PendingIntent pendingIntent = action.actionIntent;
            Intrinsics.checkNotNullExpressionValue(pendingIntent, "action.actionIntent");
            SmartReplyStateInflaterKt.startPendingIntentDismissingKeyguard(activityStarter2, pendingIntent, notificationEntry.getRow(), new SmartActionInflaterImpl$onSmartActionClick$1(this, notificationEntry, i, action, smartActions));
            return;
        }
        notificationEntry.getRow().doSmartActionClick(((int) notificationEntry.getRow().getX()) / 2, ((int) notificationEntry.getRow().getY()) / 2, 11);
        this.smartReplyController.smartActionClicked(notificationEntry, i, action, smartActions.fromAssistant);
    }
}

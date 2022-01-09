package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChannelEditorListView.kt */
public final class ChannelEditorListView extends LinearLayout {
    private AppControlView appControlRow;
    private Drawable appIcon;
    private String appName;
    private final List<ChannelRow> channelRows = new ArrayList();
    private List<NotificationChannel> channels = new ArrayList();
    public ChannelEditorDialogController controller;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ChannelEditorListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "c");
        Intrinsics.checkNotNullParameter(attributeSet, "attrs");
    }

    public final ChannelEditorDialogController getController() {
        ChannelEditorDialogController channelEditorDialogController = this.controller;
        if (channelEditorDialogController != null) {
            return channelEditorDialogController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("controller");
        throw null;
    }

    public final void setController(ChannelEditorDialogController channelEditorDialogController) {
        Intrinsics.checkNotNullParameter(channelEditorDialogController, "<set-?>");
        this.controller = channelEditorDialogController;
    }

    public final void setAppIcon(Drawable drawable) {
        this.appIcon = drawable;
    }

    public final void setAppName(String str) {
        this.appName = str;
    }

    public final void setChannels(List<NotificationChannel> list) {
        Intrinsics.checkNotNullParameter(list, "newValue");
        this.channels = list;
        updateRows();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        View findViewById = findViewById(R$id.app_control);
        Intrinsics.checkNotNullExpressionValue(findViewById, "findViewById(R.id.app_control)");
        this.appControlRow = (AppControlView) findViewById;
    }

    public final void highlightChannel(NotificationChannel notificationChannel) {
        Intrinsics.checkNotNullParameter(notificationChannel, "channel");
        Assert.isMainThread();
        for (ChannelRow channelRow : this.channelRows) {
            if (Intrinsics.areEqual(channelRow.getChannel(), notificationChannel)) {
                channelRow.playHighlight();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void updateRows() {
        boolean areAppNotificationsEnabled = getController().areAppNotificationsEnabled();
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200L);
        autoTransition.addListener((Transition.TransitionListener) new ChannelEditorListView$updateRows$1(this));
        TransitionManager.beginDelayedTransition(this, autoTransition);
        for (ChannelRow channelRow : this.channelRows) {
            removeView(channelRow);
        }
        this.channelRows.clear();
        updateAppControlRow(areAppNotificationsEnabled);
        if (areAppNotificationsEnabled) {
            LayoutInflater from = LayoutInflater.from(getContext());
            for (NotificationChannel notificationChannel : this.channels) {
                Intrinsics.checkNotNullExpressionValue(from, "inflater");
                addChannelRow(notificationChannel, from);
            }
        }
    }

    private final void addChannelRow(NotificationChannel notificationChannel, LayoutInflater layoutInflater) {
        View inflate = layoutInflater.inflate(R$layout.notif_half_shelf_row, (ViewGroup) null);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type com.android.systemui.statusbar.notification.row.ChannelRow");
        ChannelRow channelRow = (ChannelRow) inflate;
        channelRow.setController(getController());
        channelRow.setChannel(notificationChannel);
        this.channelRows.add(channelRow);
        addView(channelRow);
    }

    private final void updateAppControlRow(boolean z) {
        AppControlView appControlView = this.appControlRow;
        if (appControlView != null) {
            appControlView.getIconView().setImageDrawable(this.appIcon);
            AppControlView appControlView2 = this.appControlRow;
            if (appControlView2 != null) {
                appControlView2.getChannelName().setText(getContext().getResources().getString(R$string.notification_channel_dialog_title, this.appName));
                AppControlView appControlView3 = this.appControlRow;
                if (appControlView3 != null) {
                    appControlView3.getSwitch().setChecked(z);
                    AppControlView appControlView4 = this.appControlRow;
                    if (appControlView4 != null) {
                        appControlView4.getSwitch().setOnCheckedChangeListener(new ChannelEditorListView$updateAppControlRow$1(this));
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("appControlRow");
                        throw null;
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("appControlRow");
                    throw null;
                }
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("appControlRow");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("appControlRow");
            throw null;
        }
    }
}

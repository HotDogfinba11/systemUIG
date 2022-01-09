package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R$id;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.icon.IconPack;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DungeonRow.kt */
public final class DungeonRow extends LinearLayout {
    private NotificationEntry entry;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DungeonRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attrs");
    }

    public final NotificationEntry getEntry() {
        return this.entry;
    }

    public final void setEntry(NotificationEntry notificationEntry) {
        this.entry = notificationEntry;
        update();
    }

    private final void update() {
        StatusBarIconView statusBarIcon;
        ExpandableNotificationRow row;
        View findViewById = findViewById(R$id.app_name);
        Objects.requireNonNull(findViewById, "null cannot be cast to non-null type android.widget.TextView");
        TextView textView = (TextView) findViewById;
        NotificationEntry entry2 = getEntry();
        StatusBarIcon statusBarIcon2 = null;
        textView.setText((entry2 == null || (row = entry2.getRow()) == null) ? null : row.getAppName());
        View findViewById2 = findViewById(R$id.icon);
        Objects.requireNonNull(findViewById2, "null cannot be cast to non-null type com.android.systemui.statusbar.StatusBarIconView");
        StatusBarIconView statusBarIconView = (StatusBarIconView) findViewById2;
        NotificationEntry entry3 = getEntry();
        IconPack icons = entry3 == null ? null : entry3.getIcons();
        if (!(icons == null || (statusBarIcon = icons.getStatusBarIcon()) == null)) {
            statusBarIcon2 = statusBarIcon.getStatusBarIcon();
        }
        statusBarIconView.set(statusBarIcon2);
    }
}

package com.android.systemui.statusbar.events;

import android.content.Context;
import android.view.View;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyItem;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StatusEvent.kt */
public final class PrivacyEvent implements StatusEvent {
    private String contentDescription;
    private final boolean forceVisible;
    private final int priority;
    private OngoingPrivacyChip privacyChip;
    private List<PrivacyItem> privacyItems;
    private final boolean showAnimation;
    private final Function1<Context, View> viewCreator;

    public PrivacyEvent() {
        this(false, 1, null);
    }

    public PrivacyEvent(boolean z) {
        this.showAnimation = z;
        this.priority = 100;
        this.forceVisible = true;
        this.privacyItems = CollectionsKt__CollectionsKt.emptyList();
        this.viewCreator = new PrivacyEvent$viewCreator$1(this);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ PrivacyEvent(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public boolean getShowAnimation() {
        return this.showAnimation;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public String getContentDescription() {
        return this.contentDescription;
    }

    public void setContentDescription(String str) {
        this.contentDescription = str;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public int getPriority() {
        return this.priority;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public boolean getForceVisible() {
        return this.forceVisible;
    }

    public final List<PrivacyItem> getPrivacyItems() {
        return this.privacyItems;
    }

    public final void setPrivacyItems(List<PrivacyItem> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.privacyItems = list;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public Function1<Context, View> getViewCreator() {
        return this.viewCreator;
    }

    public String toString() {
        String simpleName = PrivacyEvent.class.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "javaClass.simpleName");
        return simpleName;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public boolean shouldUpdateFromEvent(StatusEvent statusEvent) {
        return (statusEvent instanceof PrivacyEvent) && (!Intrinsics.areEqual(((PrivacyEvent) statusEvent).privacyItems, this.privacyItems) || !Intrinsics.areEqual(statusEvent.getContentDescription(), getContentDescription()));
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public void updateFromEvent(StatusEvent statusEvent) {
        if (statusEvent instanceof PrivacyEvent) {
            this.privacyItems = ((PrivacyEvent) statusEvent).privacyItems;
            setContentDescription(statusEvent.getContentDescription());
            OngoingPrivacyChip ongoingPrivacyChip = this.privacyChip;
            if (ongoingPrivacyChip != null) {
                ongoingPrivacyChip.setContentDescription(statusEvent.getContentDescription());
            }
            OngoingPrivacyChip ongoingPrivacyChip2 = this.privacyChip;
            if (ongoingPrivacyChip2 != null) {
                ongoingPrivacyChip2.setPrivacyList(((PrivacyEvent) statusEvent).privacyItems);
            }
        }
    }
}

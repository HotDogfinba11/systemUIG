package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

public final class PeopleHubView extends StackScrollerDecorView implements SwipeableView {
    private boolean canSwipe;
    private ViewGroup contents;
    private TextView label;
    private Sequence<?> personViewAdapters;

    @Override // com.android.systemui.statusbar.notification.stack.SwipeableView
    public NotificationMenuRowPlugin createMenu() {
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public View findSecondaryView() {
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.stack.SwipeableView
    public boolean hasFinishedInitialization() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean needsClippingToShelf() {
        return true;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PeopleHubView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attrs");
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public void onFinishInflate() {
        View requireViewById = requireViewById(R$id.people_list);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(R.id.people_list)");
        this.contents = (ViewGroup) requireViewById;
        View requireViewById2 = requireViewById(R$id.header_label);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(R.id.header_label)");
        this.label = (TextView) requireViewById2;
        ViewGroup viewGroup = this.contents;
        if (viewGroup != null) {
            this.personViewAdapters = CollectionsKt___CollectionsKt.asSequence(SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.mapNotNull(CollectionsKt___CollectionsKt.asSequence(RangesKt___RangesKt.until(0, viewGroup.getChildCount())), new PeopleHubView$onFinishInflate$1(this))));
            super.onFinishInflate();
            setVisible(true, false);
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("contents");
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public View findContentView() {
        ViewGroup viewGroup = this.contents;
        if (viewGroup != null) {
            return viewGroup;
        }
        Intrinsics.throwUninitializedPropertyAccessException("contents");
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.stack.SwipeableView
    public void resetTranslation() {
        setTranslationX(0.0f);
    }

    @Override // com.android.systemui.statusbar.notification.stack.SwipeableView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setTranslation(float f) {
        if (this.canSwipe) {
            super.setTranslation(f);
        }
    }

    public final boolean getCanSwipe() {
        return this.canSwipe;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void applyContentTransformation(float f, float f2) {
        super.applyContentTransformation(f, f2);
        ViewGroup viewGroup = this.contents;
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            if (childCount > 0) {
                int i = 0;
                while (true) {
                    int i2 = i + 1;
                    ViewGroup viewGroup2 = this.contents;
                    if (viewGroup2 != null) {
                        View childAt = viewGroup2.getChildAt(i);
                        childAt.setAlpha(f);
                        childAt.setTranslationY(f2);
                        if (i2 < childCount) {
                            i = i2;
                        } else {
                            return;
                        }
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("contents");
                        throw null;
                    }
                }
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("contents");
            throw null;
        }
    }

    public final class PersonDataListenerImpl {
        private final ImageView avatarView;
        final /* synthetic */ PeopleHubView this$0;

        public PersonDataListenerImpl(PeopleHubView peopleHubView, ImageView imageView) {
            Intrinsics.checkNotNullParameter(peopleHubView, "this$0");
            Intrinsics.checkNotNullParameter(imageView, "avatarView");
            this.this$0 = peopleHubView;
            this.avatarView = imageView;
        }
    }
}

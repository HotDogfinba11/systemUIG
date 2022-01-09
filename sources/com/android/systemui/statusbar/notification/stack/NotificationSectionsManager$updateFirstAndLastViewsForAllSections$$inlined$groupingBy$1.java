package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.Iterator;
import kotlin.collections.Grouping;
import kotlin.sequences.Sequence;

/* compiled from: _Sequences.kt */
public final class NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1 implements Grouping<ExpandableView, Integer> {
    final /* synthetic */ Sequence $this_groupingBy;
    final /* synthetic */ NotificationSectionsManager this$0;

    public NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1(Sequence sequence, NotificationSectionsManager notificationSectionsManager) {
        this.$this_groupingBy = sequence;
        this.this$0 = notificationSectionsManager;
    }

    @Override // kotlin.collections.Grouping
    public Iterator<ExpandableView> sourceIterator() {
        return this.$this_groupingBy.iterator();
    }

    @Override // kotlin.collections.Grouping
    public Integer keyOf(ExpandableView expandableView) {
        Integer num = this.this$0.getBucket(expandableView);
        if (num != null) {
            return Integer.valueOf(num.intValue());
        }
        throw new IllegalArgumentException("Cannot find section bucket for view");
    }
}

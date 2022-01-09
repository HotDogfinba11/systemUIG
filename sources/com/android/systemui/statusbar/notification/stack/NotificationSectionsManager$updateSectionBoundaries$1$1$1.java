package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* access modifiers changed from: package-private */
/* compiled from: NotificationSectionsManager.kt */
public final class NotificationSectionsManager$updateSectionBoundaries$1$1$1 extends Lambda implements Function1<NotificationSectionsManager.SectionUpdateState<? extends ExpandableView>, Boolean> {
    final /* synthetic */ NotificationSectionsManager.SectionUpdateState<ExpandableView> $state;

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$SectionUpdateState<? extends com.android.systemui.statusbar.notification.row.ExpandableView> */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    NotificationSectionsManager$updateSectionBoundaries$1$1$1(NotificationSectionsManager.SectionUpdateState<? extends ExpandableView> sectionUpdateState) {
        super(1);
        this.$state = sectionUpdateState;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Boolean invoke(NotificationSectionsManager.SectionUpdateState<? extends ExpandableView> sectionUpdateState) {
        return Boolean.valueOf(invoke(sectionUpdateState));
    }

    public final boolean invoke(NotificationSectionsManager.SectionUpdateState<? extends ExpandableView> sectionUpdateState) {
        Intrinsics.checkNotNullParameter(sectionUpdateState, "it");
        return sectionUpdateState == this.$state;
    }
}

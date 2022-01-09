package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Set;
import java.util.function.Predicate;

public final /* synthetic */ class GroupExpansionManagerImpl$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ Set f$0;

    public /* synthetic */ GroupExpansionManagerImpl$$ExternalSyntheticLambda1(Set set) {
        this.f$0 = set;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return GroupExpansionManagerImpl.$r8$lambda$X6Zw9ekax86_8Mu9ZeFWltuFGU0(this.f$0, (NotificationEntry) obj);
    }
}

package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;

public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda2 implements Pluggable.PluggableListener {
    public final /* synthetic */ ShadeListBuilder f$0;

    public /* synthetic */ ShadeListBuilder$$ExternalSyntheticLambda2(ShadeListBuilder shadeListBuilder) {
        this.f$0 = shadeListBuilder;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
    public final void onPluggableInvalidated(Object obj) {
        this.f$0.onPromoterInvalidated((NotifPromoter) obj);
    }
}

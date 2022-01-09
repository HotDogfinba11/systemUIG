package com.android.systemui;

import android.content.ContentProvider;
import android.content.Context;
import com.android.systemui.SystemUIAppComponentFactory;

public final /* synthetic */ class SystemUIAppComponentFactory$$ExternalSyntheticLambda0 implements SystemUIAppComponentFactory.ContextAvailableCallback {
    public final /* synthetic */ ContentProvider f$0;

    public /* synthetic */ SystemUIAppComponentFactory$$ExternalSyntheticLambda0(ContentProvider contentProvider) {
        this.f$0 = contentProvider;
    }

    @Override // com.android.systemui.SystemUIAppComponentFactory.ContextAvailableCallback
    public final void onContextAvailable(Context context) {
        SystemUIAppComponentFactory.$r8$lambda$qcrPqG7eXiVK_IrWSM3yDqdi2js(this.f$0, context);
    }
}

package com.android.systemui.tv;

import android.content.Context;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.dagger.GlobalRootComponent;

public class TvSystemUIFactory extends SystemUIFactory {
    /* access modifiers changed from: protected */
    @Override // com.android.systemui.SystemUIFactory
    public GlobalRootComponent buildGlobalRootComponent(Context context) {
        return DaggerTvGlobalRootComponent.builder().context(context).build();
    }
}

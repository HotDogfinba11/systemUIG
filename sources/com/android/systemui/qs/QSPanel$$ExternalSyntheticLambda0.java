package com.android.systemui.qs;

import android.content.res.Configuration;
import com.android.systemui.qs.QSPanel;
import java.util.function.Consumer;

public final /* synthetic */ class QSPanel$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ Configuration f$0;

    public /* synthetic */ QSPanel$$ExternalSyntheticLambda0(Configuration configuration) {
        this.f$0 = configuration;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((QSPanel.OnConfigurationChangedListener) obj).onConfigurationChange(this.f$0);
    }
}

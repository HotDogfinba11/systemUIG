package com.android.systemui.qs;

import java.util.Map;
import java.util.function.Consumer;

public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ QSTileHost f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda1(QSTileHost qSTileHost) {
        this.f$0 = qSTileHost;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.lambda$onTuningChanged$3((Map.Entry) obj);
    }
}

package com.android.settingslib.wifi;

import java.util.Iterator;
import java.util.function.Consumer;

public final /* synthetic */ class AccessPoint$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ long f$0;
    public final /* synthetic */ Iterator f$1;

    public /* synthetic */ AccessPoint$$ExternalSyntheticLambda5(long j, Iterator it) {
        this.f$0 = j;
        this.f$1 = it;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AccessPoint.$r8$lambda$eeEFeIMR6UCrf_qCtv1iNqRPOo8(this.f$0, this.f$1, (TimestampedScoredNetwork) obj);
    }
}

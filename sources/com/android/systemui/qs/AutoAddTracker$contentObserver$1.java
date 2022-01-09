package com.android.systemui.qs;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AutoAddTracker.kt */
public final class AutoAddTracker$contentObserver$1 extends ContentObserver {
    final /* synthetic */ AutoAddTracker this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    AutoAddTracker$contentObserver$1(AutoAddTracker autoAddTracker, Handler handler) {
        super(handler);
        this.this$0 = autoAddTracker;
    }

    public void onChange(boolean z, Collection<? extends Uri> collection, int i, int i2) {
        Intrinsics.checkNotNullParameter(collection, "uris");
        if (i2 == this.this$0.userId) {
            this.this$0.loadTiles();
        }
    }
}

package com.android.systemui.qs.tileimpl;

import com.android.systemui.plugins.qs.QSTile;

/* compiled from: QSTileViewImpl.kt */
final class QSTileViewImpl$onStateChanged$1 implements Runnable {
    final /* synthetic */ QSTile.State $state;
    final /* synthetic */ QSTileViewImpl this$0;

    QSTileViewImpl$onStateChanged$1(QSTileViewImpl qSTileViewImpl, QSTile.State state) {
        this.this$0 = qSTileViewImpl;
        this.$state = state;
    }

    public final void run() {
        this.this$0.handleStateChanged(this.$state);
    }
}

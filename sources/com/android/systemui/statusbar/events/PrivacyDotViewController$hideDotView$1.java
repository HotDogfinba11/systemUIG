package com.android.systemui.statusbar.events;

import android.view.View;

/* access modifiers changed from: package-private */
/* compiled from: PrivacyDotViewController.kt */
public final class PrivacyDotViewController$hideDotView$1 implements Runnable {
    final /* synthetic */ View $dot;

    PrivacyDotViewController$hideDotView$1(View view) {
        this.$dot = view;
    }

    public final void run() {
        this.$dot.setVisibility(4);
    }
}

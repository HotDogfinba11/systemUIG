package com.google.android.systemui.smartspace;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;

/* compiled from: KeyguardMediaViewController.kt */
public final class KeyguardMediaViewController$init$2 extends CurrentUserTracker {
    final /* synthetic */ KeyguardMediaViewController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KeyguardMediaViewController$init$2(KeyguardMediaViewController keyguardMediaViewController, BroadcastDispatcher broadcastDispatcher) {
        super(broadcastDispatcher);
        this.this$0 = keyguardMediaViewController;
    }

    @Override // com.android.systemui.settings.CurrentUserTracker
    public void onUserSwitched(int i) {
        this.this$0.reset();
    }
}

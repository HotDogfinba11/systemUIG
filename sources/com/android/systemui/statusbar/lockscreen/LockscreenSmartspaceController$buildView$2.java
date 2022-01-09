package com.android.systemui.statusbar.lockscreen;

import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$buildView$2 implements BcSmartspaceDataPlugin.IntentStarter {
    final /* synthetic */ LockscreenSmartspaceController this$0;

    LockscreenSmartspaceController$buildView$2(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
    public void startIntent(View view, Intent intent) {
        this.this$0.activityStarter.startActivity(intent, true);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
    public void startPendingIntent(PendingIntent pendingIntent) {
        this.this$0.activityStarter.startPendingIntentDismissingKeyguard(pendingIntent);
    }
}

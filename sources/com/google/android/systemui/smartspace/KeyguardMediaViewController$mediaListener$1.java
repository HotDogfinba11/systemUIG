package com.google.android.systemui.smartspace;

import android.media.MediaMetadata;
import com.android.systemui.statusbar.NotificationMediaManager;

/* compiled from: KeyguardMediaViewController.kt */
public final class KeyguardMediaViewController$mediaListener$1 implements NotificationMediaManager.MediaListener {
    final /* synthetic */ KeyguardMediaViewController this$0;

    KeyguardMediaViewController$mediaListener$1(KeyguardMediaViewController keyguardMediaViewController) {
        this.this$0 = keyguardMediaViewController;
    }

    @Override // com.android.systemui.statusbar.NotificationMediaManager.MediaListener
    public void onPrimaryMetadataOrStateChanged(MediaMetadata mediaMetadata, int i) {
        this.this$0.getUiExecutor().execute(new KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1(this.this$0, mediaMetadata, i));
    }
}

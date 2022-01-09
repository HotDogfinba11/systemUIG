package com.google.android.systemui.smartspace;

import android.media.MediaMetadata;

/* compiled from: KeyguardMediaViewController.kt */
final class KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1 implements Runnable {
    final /* synthetic */ MediaMetadata $metadata;
    final /* synthetic */ int $state;
    final /* synthetic */ KeyguardMediaViewController this$0;

    KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1(KeyguardMediaViewController keyguardMediaViewController, MediaMetadata mediaMetadata, int i) {
        this.this$0 = keyguardMediaViewController;
        this.$metadata = mediaMetadata;
        this.$state = i;
    }

    public final void run() {
        this.this$0.updateMediaInfo(this.$metadata, this.$state);
    }
}

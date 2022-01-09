package com.android.wm.shell.pip.tv;

import android.media.MediaMetadata;
import com.android.wm.shell.pip.PipMediaController;

public final /* synthetic */ class TvPipNotificationController$$ExternalSyntheticLambda0 implements PipMediaController.MetadataListener {
    public final /* synthetic */ TvPipNotificationController f$0;

    public /* synthetic */ TvPipNotificationController$$ExternalSyntheticLambda0(TvPipNotificationController tvPipNotificationController) {
        this.f$0 = tvPipNotificationController;
    }

    @Override // com.android.wm.shell.pip.PipMediaController.MetadataListener
    public final void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        this.f$0.onMediaMetadataChanged(mediaMetadata);
    }
}

package com.android.wm.shell.pip;

import android.media.MediaMetadata;
import com.android.wm.shell.pip.PipMediaController;
import java.util.function.Consumer;

public final /* synthetic */ class PipMediaController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ MediaMetadata f$0;

    public /* synthetic */ PipMediaController$$ExternalSyntheticLambda1(MediaMetadata mediaMetadata) {
        this.f$0 = mediaMetadata;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PipMediaController.MetadataListener) obj).onMediaMetadataChanged(this.f$0);
    }
}

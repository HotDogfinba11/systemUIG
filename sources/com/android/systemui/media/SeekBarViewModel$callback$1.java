package com.android.systemui.media;

import android.media.session.MediaController;
import android.media.session.PlaybackState;

/* compiled from: SeekBarViewModel.kt */
public final class SeekBarViewModel$callback$1 extends MediaController.Callback {
    final /* synthetic */ SeekBarViewModel this$0;

    SeekBarViewModel$callback$1(SeekBarViewModel seekBarViewModel) {
        this.this$0 = seekBarViewModel;
    }

    public void onPlaybackStateChanged(PlaybackState playbackState) {
        this.this$0.playbackState = playbackState;
        if (this.this$0.playbackState != null) {
            Integer num = 0;
            if (!num.equals(this.this$0.playbackState)) {
                this.this$0.checkIfPollingNeeded();
                return;
            }
        }
        this.this$0.clearController();
    }

    public void onSessionDestroyed() {
        this.this$0.clearController();
    }
}

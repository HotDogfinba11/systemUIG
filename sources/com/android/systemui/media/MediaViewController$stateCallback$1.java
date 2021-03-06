package com.android.systemui.media;

import com.android.systemui.media.MediaHostStatesManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaViewController.kt */
public final class MediaViewController$stateCallback$1 implements MediaHostStatesManager.Callback {
    final /* synthetic */ MediaViewController this$0;

    MediaViewController$stateCallback$1(MediaViewController mediaViewController) {
        this.this$0 = mediaViewController;
    }

    @Override // com.android.systemui.media.MediaHostStatesManager.Callback
    public void onHostStateChanged(int i, MediaHostState mediaHostState) {
        Intrinsics.checkNotNullParameter(mediaHostState, "mediaHostState");
        if (i == this.this$0.getCurrentEndLocation() || i == this.this$0.currentStartLocation) {
            MediaViewController mediaViewController = this.this$0;
            mediaViewController.setCurrentState(mediaViewController.currentStartLocation, this.this$0.getCurrentEndLocation(), this.this$0.currentTransitionProgress, false);
        }
    }
}

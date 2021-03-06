package com.android.systemui.media;

import android.view.View;

/* compiled from: MediaHost.kt */
public final class MediaHost$init$1 implements View.OnAttachStateChangeListener {
    final /* synthetic */ MediaHost this$0;

    MediaHost$init$1(MediaHost mediaHost) {
        this.this$0 = mediaHost;
    }

    public void onViewAttachedToWindow(View view) {
        this.this$0.setListeningToMediaData(true);
        this.this$0.updateViewVisibility();
    }

    public void onViewDetachedFromWindow(View view) {
        this.this$0.setListeningToMediaData(false);
    }
}

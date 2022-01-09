package com.android.systemui.media;

/* access modifiers changed from: package-private */
/* compiled from: MediaCarouselScrollHandler.kt */
public final class MediaCarouselScrollHandler$onTouch$1 implements Runnable {
    final /* synthetic */ int $newScrollX;
    final /* synthetic */ MediaCarouselScrollHandler this$0;

    MediaCarouselScrollHandler$onTouch$1(MediaCarouselScrollHandler mediaCarouselScrollHandler, int i) {
        this.this$0 = mediaCarouselScrollHandler;
        this.$newScrollX = i;
    }

    public final void run() {
        this.this$0.scrollView.smoothScrollTo(this.$newScrollX, this.this$0.scrollView.getScrollY());
    }
}

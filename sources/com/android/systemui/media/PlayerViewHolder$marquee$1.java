package com.android.systemui.media;

/* access modifiers changed from: package-private */
/* compiled from: PlayerViewHolder.kt */
public final class PlayerViewHolder$marquee$1 implements Runnable {
    final /* synthetic */ boolean $start;
    final /* synthetic */ PlayerViewHolder this$0;

    PlayerViewHolder$marquee$1(PlayerViewHolder playerViewHolder, boolean z) {
        this.this$0 = playerViewHolder;
        this.$start = z;
    }

    public final void run() {
        this.this$0.getLongPressText().setSelected(this.$start);
    }
}

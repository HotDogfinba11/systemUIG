package com.android.systemui.media;

import android.graphics.ImageDecoder;

/* access modifiers changed from: package-private */
/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$loadBitmapFromUri$1 implements ImageDecoder.OnHeaderDecodedListener {
    public static final MediaDataManager$loadBitmapFromUri$1 INSTANCE = new MediaDataManager$loadBitmapFromUri$1();

    MediaDataManager$loadBitmapFromUri$1() {
    }

    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
    }
}

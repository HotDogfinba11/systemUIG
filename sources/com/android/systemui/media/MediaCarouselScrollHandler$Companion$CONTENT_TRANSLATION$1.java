package com.android.systemui.media;

import androidx.dynamicanimation.animation.FloatPropertyCompat;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaCarouselScrollHandler.kt */
public final class MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 extends FloatPropertyCompat<MediaCarouselScrollHandler> {
    MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1() {
        super("contentTranslation");
    }

    public float getValue(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
        Intrinsics.checkNotNullParameter(mediaCarouselScrollHandler, "handler");
        return mediaCarouselScrollHandler.getContentTranslation();
    }

    public void setValue(MediaCarouselScrollHandler mediaCarouselScrollHandler, float f) {
        Intrinsics.checkNotNullParameter(mediaCarouselScrollHandler, "handler");
        mediaCarouselScrollHandler.setContentTranslation(f);
    }
}

package com.android.systemui.media;

import android.content.Context;
import com.android.systemui.util.Utils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaFeatureFlag.kt */
public final class MediaFeatureFlag {
    private final Context context;

    public MediaFeatureFlag(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
    }

    public final boolean getEnabled() {
        return Utils.useQsMediaPlayer(this.context);
    }
}

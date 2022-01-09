package com.android.systemui.media;

import com.android.systemui.media.MediaDataManager;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* access modifiers changed from: package-private */
/* compiled from: MediaSessionBasedFilter.kt */
public final class MediaSessionBasedFilter$dispatchSmartspaceMediaDataLoaded$1 implements Runnable {
    final /* synthetic */ SmartspaceMediaData $info;
    final /* synthetic */ String $key;
    final /* synthetic */ MediaSessionBasedFilter this$0;

    MediaSessionBasedFilter$dispatchSmartspaceMediaDataLoaded$1(MediaSessionBasedFilter mediaSessionBasedFilter, String str, SmartspaceMediaData smartspaceMediaData) {
        this.this$0 = mediaSessionBasedFilter;
        this.$key = str;
        this.$info = smartspaceMediaData;
    }

    public final void run() {
        Set<MediaDataManager.Listener> set = CollectionsKt___CollectionsKt.toSet(this.this$0.listeners);
        String str = this.$key;
        SmartspaceMediaData smartspaceMediaData = this.$info;
        for (MediaDataManager.Listener listener : set) {
            MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded$default(listener, str, smartspaceMediaData, false, 4, null);
        }
    }
}

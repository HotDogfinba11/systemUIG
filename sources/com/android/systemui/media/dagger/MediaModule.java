package com.android.systemui.media.dagger;

import com.android.systemui.media.MediaDataManager;
import com.android.systemui.media.MediaHierarchyManager;
import com.android.systemui.media.MediaHost;
import com.android.systemui.media.MediaHostStatesManager;

public interface MediaModule {
    static default MediaHost providesQSMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager) {
        return new MediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager);
    }

    static default MediaHost providesQuickQSMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager) {
        return new MediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager);
    }

    static default MediaHost providesKeyguardMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager) {
        return new MediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager);
    }
}

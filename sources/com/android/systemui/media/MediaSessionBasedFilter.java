package com.android.systemui.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaSessionBasedFilter.kt */
public final class MediaSessionBasedFilter implements MediaDataManager.Listener {
    private final Executor backgroundExecutor;
    private final Executor foregroundExecutor;
    private final Map<String, Set<MediaSession.Token>> keyedTokens = new LinkedHashMap();
    private final Set<MediaDataManager.Listener> listeners = new LinkedHashSet();
    private final LinkedHashMap<String, List<MediaController>> packageControllers = new LinkedHashMap<>();
    private final MediaSessionBasedFilter$sessionListener$1 sessionListener = new MediaSessionBasedFilter$sessionListener$1(this);
    private final MediaSessionManager sessionManager;
    private final Set<MediaSession.Token> tokensWithNotifications = new LinkedHashSet();

    public MediaSessionBasedFilter(final Context context, MediaSessionManager mediaSessionManager, Executor executor, Executor executor2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mediaSessionManager, "sessionManager");
        Intrinsics.checkNotNullParameter(executor, "foregroundExecutor");
        Intrinsics.checkNotNullParameter(executor2, "backgroundExecutor");
        this.sessionManager = mediaSessionManager;
        this.foregroundExecutor = executor;
        this.backgroundExecutor = executor2;
        executor2.execute(new Runnable() {
            /* class com.android.systemui.media.MediaSessionBasedFilter.AnonymousClass1 */

            public final void run() {
                ComponentName componentName = new ComponentName(context, NotificationListenerWithPlugins.class);
                this.sessionManager.addOnActiveSessionsChangedListener(this.sessionListener, componentName);
                MediaSessionBasedFilter mediaSessionBasedFilter = this;
                List<MediaController> activeSessions = mediaSessionBasedFilter.sessionManager.getActiveSessions(componentName);
                Intrinsics.checkNotNullExpressionValue(activeSessions, "sessionManager.getActiveSessions(name)");
                mediaSessionBasedFilter.handleControllersChanged(activeSessions);
            }
        });
    }

    public final boolean addListener(MediaDataManager.Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        return this.listeners.add(listener);
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(mediaData, "data");
        this.backgroundExecutor.execute(new MediaSessionBasedFilter$onMediaDataLoaded$1(mediaData, str2, str, this, z));
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(smartspaceMediaData, "data");
        this.backgroundExecutor.execute(new MediaSessionBasedFilter$onSmartspaceMediaDataLoaded$1(this, str, smartspaceMediaData));
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        Intrinsics.checkNotNullParameter(str, "key");
        this.backgroundExecutor.execute(new MediaSessionBasedFilter$onMediaDataRemoved$1(this, str));
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        Intrinsics.checkNotNullParameter(str, "key");
        this.backgroundExecutor.execute(new MediaSessionBasedFilter$onSmartspaceMediaDataRemoved$1(this, str, z));
    }

    /* access modifiers changed from: private */
    public final void dispatchMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
        this.foregroundExecutor.execute(new MediaSessionBasedFilter$dispatchMediaDataLoaded$1(this, str, str2, mediaData, z));
    }

    /* access modifiers changed from: private */
    public final void dispatchMediaDataRemoved(String str) {
        this.foregroundExecutor.execute(new MediaSessionBasedFilter$dispatchMediaDataRemoved$1(this, str));
    }

    /* access modifiers changed from: private */
    public final void dispatchSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        this.foregroundExecutor.execute(new MediaSessionBasedFilter$dispatchSmartspaceMediaDataLoaded$1(this, str, smartspaceMediaData));
    }

    /* access modifiers changed from: private */
    public final void dispatchSmartspaceMediaDataRemoved(String str, boolean z) {
        this.foregroundExecutor.execute(new MediaSessionBasedFilter$dispatchSmartspaceMediaDataRemoved$1(this, str, z));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void handleControllersChanged(List<MediaController> list) {
        Boolean bool;
        this.packageControllers.clear();
        for (T t : list) {
            List<MediaController> list2 = this.packageControllers.get(t.getPackageName());
            if (list2 == null) {
                bool = null;
            } else {
                bool = Boolean.valueOf(list2.add(t));
            }
            if (bool == null) {
                this.packageControllers.put(t.getPackageName(), CollectionsKt__CollectionsKt.mutableListOf(t));
            }
        }
        Set<MediaSession.Token> set = this.tokensWithNotifications;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getSessionToken());
        }
        set.retainAll(arrayList);
    }
}

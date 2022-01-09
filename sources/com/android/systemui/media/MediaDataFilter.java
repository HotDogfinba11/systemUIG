package com.android.systemui.media;

import android.app.smartspace.SmartspaceAction;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.time.SystemClock;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaDataFilter.kt */
public final class MediaDataFilter implements MediaDataManager.Listener {
    private final Set<MediaDataManager.Listener> _listeners = new LinkedHashSet();
    private final LinkedHashMap<String, MediaData> allEntries = new LinkedHashMap<>();
    private final BroadcastDispatcher broadcastDispatcher;
    private final Context context;
    private final Executor executor;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    public MediaDataManager mediaDataManager;
    private final MediaResumeListener mediaResumeListener;
    private String reactivatedKey;
    private SmartspaceMediaData smartspaceMediaData = MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA();
    private final SystemClock systemClock;
    private final LinkedHashMap<String, MediaData> userEntries = new LinkedHashMap<>();
    private final CurrentUserTracker userTracker;

    public MediaDataFilter(Context context2, BroadcastDispatcher broadcastDispatcher2, MediaResumeListener mediaResumeListener2, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor2, SystemClock systemClock2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(mediaResumeListener2, "mediaResumeListener");
        Intrinsics.checkNotNullParameter(notificationLockscreenUserManager, "lockscreenUserManager");
        Intrinsics.checkNotNullParameter(executor2, "executor");
        Intrinsics.checkNotNullParameter(systemClock2, "systemClock");
        this.context = context2;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.mediaResumeListener = mediaResumeListener2;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor2;
        this.systemClock = systemClock2;
        AnonymousClass1 r2 = new CurrentUserTracker(this, broadcastDispatcher2) {
            /* class com.android.systemui.media.MediaDataFilter.AnonymousClass1 */
            final /* synthetic */ MediaDataFilter this$0;

            {
                this.this$0 = r1;
            }

            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int i) {
                this.this$0.executor.execute(new MediaDataFilter$1$onUserSwitched$1(this.this$0, i));
            }
        };
        this.userTracker = r2;
        r2.startTracking();
    }

    public final Set<MediaDataManager.Listener> getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return CollectionsKt___CollectionsKt.toSet(this._listeners);
    }

    public final MediaDataManager getMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        MediaDataManager mediaDataManager2 = this.mediaDataManager;
        if (mediaDataManager2 != null) {
            return mediaDataManager2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaDataManager");
        throw null;
    }

    public final void setMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core(MediaDataManager mediaDataManager2) {
        Intrinsics.checkNotNullParameter(mediaDataManager2, "<set-?>");
        this.mediaDataManager = mediaDataManager2;
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(mediaData, "data");
        if (str2 != null && !Intrinsics.areEqual(str2, str)) {
            this.allEntries.remove(str2);
        }
        this.allEntries.put(str, mediaData);
        if (this.lockscreenUserManager.isCurrentProfile(mediaData.getUserId())) {
            if (str2 != null && !Intrinsics.areEqual(str2, str)) {
                this.userEntries.remove(str2);
            }
            this.userEntries.put(str, mediaData);
            Iterator<T> it = getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core().iterator();
            while (it.hasNext()) {
                MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(it.next(), str, str2, mediaData, false, z2, 8, null);
            }
        }
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData2, boolean z) {
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(smartspaceMediaData2, "data");
        if (!smartspaceMediaData2.isActive()) {
            Log.d("MediaDataFilter", "Inactive recommendation data. Skip triggering.");
            return;
        }
        this.smartspaceMediaData = smartspaceMediaData2;
        SortedMap<String, MediaData> sortedMap = MapsKt__MapsJVMKt.toSortedMap(this.userEntries, new MediaDataFilter$onSmartspaceMediaDataLoaded$$inlined$compareBy$1(this));
        long timeSinceActiveForMostRecentMedia = timeSinceActiveForMostRecentMedia(sortedMap);
        long smartspace_max_age = MediaDataFilterKt.getSMARTSPACE_MAX_AGE();
        SmartspaceAction cardAction = smartspaceMediaData2.getCardAction();
        if (cardAction != null) {
            long j = cardAction.getExtras().getLong("resumable_media_max_age_seconds", 0);
            if (j > 0) {
                smartspace_max_age = TimeUnit.SECONDS.toMillis(j);
            }
        }
        boolean z2 = true;
        if (timeSinceActiveForMostRecentMedia < smartspace_max_age) {
            String lastKey = sortedMap.lastKey();
            Log.d("MediaDataFilter", "reactivating " + ((Object) lastKey) + " instead of smartspace");
            this.reactivatedKey = lastKey;
            if (MediaPlayerData.INSTANCE.firstActiveMediaIndex() != -1) {
                z2 = false;
            }
            MediaData mediaData = sortedMap.get(lastKey);
            Intrinsics.checkNotNull(mediaData);
            MediaData copy$default = MediaData.copy$default(mediaData, 0, false, 0, null, null, null, null, null, null, null, null, null, null, null, true, null, false, false, null, false, null, false, 0, 8372223, null);
            Iterator<T> it = getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core().iterator();
            while (it.hasNext()) {
                Intrinsics.checkNotNullExpressionValue(lastKey, "lastActiveKey");
                MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(it.next(), lastKey, lastKey, copy$default, false, z2, 8, null);
            }
            z2 = false;
        }
        if (!smartspaceMediaData2.isValid()) {
            Log.d("MediaDataFilter", "Invalid recommendation data. Skip showing the rec card");
            return;
        }
        Iterator<T> it2 = getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core().iterator();
        while (it2.hasNext()) {
            it2.next().onSmartspaceMediaDataLoaded(str, smartspaceMediaData2, z2);
        }
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        Intrinsics.checkNotNullParameter(str, "key");
        this.allEntries.remove(str);
        if (this.userEntries.remove(str) != null) {
            Iterator<T> it = getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core().iterator();
            while (it.hasNext()) {
                it.next().onMediaDataRemoved(str);
            }
        }
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        Intrinsics.checkNotNullParameter(str, "key");
        String str2 = this.reactivatedKey;
        if (str2 != null) {
            this.reactivatedKey = null;
            Log.d("MediaDataFilter", Intrinsics.stringPlus("expiring reactivated key ", str2));
            MediaData mediaData = this.userEntries.get(str2);
            if (mediaData != null) {
                Iterator<T> it = getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core().iterator();
                while (it.hasNext()) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(it.next(), str2, str2, mediaData, z, false, 16, null);
                }
            }
        }
        if (this.smartspaceMediaData.isActive()) {
            this.smartspaceMediaData = SmartspaceMediaData.copy$default(MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA(), this.smartspaceMediaData.getTargetId(), false, this.smartspaceMediaData.isValid(), null, null, null, null, 0, 250, null);
        }
        Iterator<T> it2 = getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core().iterator();
        while (it2.hasNext()) {
            it2.next().onSmartspaceMediaDataRemoved(str, z);
        }
    }

    @VisibleForTesting
    public final void handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i) {
        Set<MediaDataManager.Listener> listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        Set<String> keySet = this.userEntries.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "userEntries.keys");
        List<String> list = CollectionsKt___CollectionsKt.toMutableList((Collection) keySet);
        this.userEntries.clear();
        for (String str : list) {
            Log.d("MediaDataFilter", "Removing " + str + " after user change");
            Iterator<T> it = listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core.iterator();
            while (it.hasNext()) {
                Intrinsics.checkNotNullExpressionValue(str, "it");
                it.next().onMediaDataRemoved(str);
            }
        }
        for (Map.Entry<String, MediaData> entry : this.allEntries.entrySet()) {
            String key = entry.getKey();
            MediaData value = entry.getValue();
            if (this.lockscreenUserManager.isCurrentProfile(value.getUserId())) {
                Log.d("MediaDataFilter", "Re-adding " + key + " after user change");
                this.userEntries.put(key, value);
                Iterator<T> it2 = listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core.iterator();
                while (it2.hasNext()) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(it2.next(), key, null, value, false, false, 24, null);
                }
            }
        }
    }

    public final void onSwipeToDismiss() {
        Log.d("MediaDataFilter", "Media carousel swiped away");
        Set<String> keySet = this.userEntries.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "userEntries.keys");
        for (String str : CollectionsKt___CollectionsKt.toSet(keySet)) {
            MediaDataManager mediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            Intrinsics.checkNotNullExpressionValue(str, "it");
            mediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setTimedOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core(str, true, true);
        }
        if (this.smartspaceMediaData.isActive()) {
            Intent dismissIntent = this.smartspaceMediaData.getDismissIntent();
            if (dismissIntent == null) {
                Log.w("MediaDataFilter", "Cannot create dismiss action click action: extras missing dismiss_intent.");
            } else if (dismissIntent.getComponent() == null || !Intrinsics.areEqual(dismissIntent.getComponent().getClassName(), "com.google.android.apps.gsa.staticplugins.opa.smartspace.ExportedSmartspaceTrampolineActivity")) {
                this.context.sendBroadcast(dismissIntent);
            } else {
                this.context.startActivity(dismissIntent);
            }
            this.smartspaceMediaData = SmartspaceMediaData.copy$default(MediaDataManagerKt.getEMPTY_SMARTSPACE_MEDIA_DATA(), this.smartspaceMediaData.getTargetId(), false, this.smartspaceMediaData.isValid(), null, null, null, null, 0, 250, null);
        }
        getMediaDataManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core().dismissSmartspaceRecommendation(this.smartspaceMediaData.getTargetId(), 0);
    }

    public final boolean hasActiveMedia() {
        boolean z;
        LinkedHashMap<String, MediaData> linkedHashMap = this.userEntries;
        if (!linkedHashMap.isEmpty()) {
            Iterator<Map.Entry<String, MediaData>> it = linkedHashMap.entrySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getValue().getActive()) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            return z || this.smartspaceMediaData.isActive();
        }
        z = false;
        if (z) {
            return true;
        }
    }

    public final boolean hasAnyMedia() {
        return (this.userEntries.isEmpty() ^ true) || this.smartspaceMediaData.isActive();
    }

    public final boolean addListener(MediaDataManager.Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        return this._listeners.add(listener);
    }

    public final boolean removeListener(MediaDataManager.Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        return this._listeners.remove(listener);
    }

    private final long timeSinceActiveForMostRecentMedia(SortedMap<String, MediaData> sortedMap) {
        if (sortedMap.isEmpty()) {
            return Long.MAX_VALUE;
        }
        long elapsedRealtime = this.systemClock.elapsedRealtime();
        MediaData mediaData = sortedMap.get(sortedMap.lastKey());
        if (mediaData == null) {
            return Long.MAX_VALUE;
        }
        return elapsedRealtime - mediaData.getLastActive();
    }
}

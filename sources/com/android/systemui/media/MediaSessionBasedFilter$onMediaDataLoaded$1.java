package com.android.systemui.media;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: MediaSessionBasedFilter.kt */
final class MediaSessionBasedFilter$onMediaDataLoaded$1 implements Runnable {
    final /* synthetic */ MediaData $data;
    final /* synthetic */ boolean $immediately;
    final /* synthetic */ String $key;
    final /* synthetic */ String $oldKey;
    final /* synthetic */ MediaSessionBasedFilter this$0;

    MediaSessionBasedFilter$onMediaDataLoaded$1(MediaData mediaData, String str, String str2, MediaSessionBasedFilter mediaSessionBasedFilter, boolean z) {
        this.$data = mediaData;
        this.$oldKey = str;
        this.$key = str2;
        this.this$0 = mediaSessionBasedFilter;
        this.$immediately = z;
    }

    public final void run() {
        ArrayList arrayList;
        MediaSession.Token token = this.$data.getToken();
        if (token != null) {
            this.this$0.tokensWithNotifications.add(token);
        }
        String str = this.$oldKey;
        boolean z = str != null && !Intrinsics.areEqual(this.$key, str);
        if (z) {
            Map map = this.this$0.keyedTokens;
            String str2 = this.$oldKey;
            Objects.requireNonNull(map, "null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
            Set set = (Set) TypeIntrinsics.asMutableMap(map).remove(str2);
            if (set != null) {
                Set set2 = (Set) this.this$0.keyedTokens.put(this.$key, set);
            }
        }
        MediaController mediaController = null;
        if (this.$data.getToken() != null) {
            Set set3 = (Set) this.this$0.keyedTokens.get(this.$key);
            if ((set3 == null ? null : Boolean.valueOf(set3.add(this.$data.getToken()))) == null) {
                Set set4 = (Set) this.this$0.keyedTokens.put(this.$key, SetsKt__SetsKt.mutableSetOf(this.$data.getToken()));
            }
        }
        List list = (List) this.this$0.packageControllers.get(this.$data.getPackageName());
        if (list == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList();
            for (Object obj : list) {
                MediaController.PlaybackInfo playbackInfo = ((MediaController) obj).getPlaybackInfo();
                Integer valueOf = playbackInfo == null ? null : Integer.valueOf(playbackInfo.getPlaybackType());
                if (valueOf != null && valueOf.intValue() == 2) {
                    arrayList.add(obj);
                }
            }
        }
        Integer valueOf2 = arrayList == null ? null : Integer.valueOf(arrayList.size());
        if (valueOf2 != null && valueOf2.intValue() == 1) {
            mediaController = (MediaController) CollectionsKt.firstOrNull(arrayList);
        }
        if (z || mediaController == null || Intrinsics.areEqual(mediaController.getSessionToken(), this.$data.getToken()) || !this.this$0.tokensWithNotifications.contains(mediaController.getSessionToken())) {
            this.this$0.dispatchMediaDataLoaded(this.$key, this.$oldKey, this.$data, this.$immediately);
            return;
        }
        Log.d("MediaSessionBasedFilter", "filtering key=" + this.$key + " local=" + this.$data.getToken() + " remote=" + mediaController.getSessionToken());
        Set set5 = (Set) this.this$0.keyedTokens.get(this.$key);
        Intrinsics.checkNotNull(set5);
        if (!set5.contains(mediaController.getSessionToken())) {
            this.this$0.dispatchMediaDataRemoved(this.$key);
        }
    }
}

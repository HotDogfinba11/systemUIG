package com.android.systemui.media;

import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.util.Log;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: MediaTimeoutListener.kt */
public final class MediaTimeoutListener implements MediaDataManager.Listener {
    private final DelayableExecutor mainExecutor;
    private final MediaControllerFactory mediaControllerFactory;
    private final Map<String, PlaybackStateListener> mediaListeners = new LinkedHashMap();
    public Function2<? super String, ? super Boolean, Unit> timeoutCallback;

    public MediaTimeoutListener(MediaControllerFactory mediaControllerFactory2, DelayableExecutor delayableExecutor) {
        Intrinsics.checkNotNullParameter(mediaControllerFactory2, "mediaControllerFactory");
        Intrinsics.checkNotNullParameter(delayableExecutor, "mainExecutor");
        this.mediaControllerFactory = mediaControllerFactory2;
        this.mainExecutor = delayableExecutor;
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded(this, str, smartspaceMediaData, z);
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataRemoved(this, str, z);
    }

    /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit>, kotlin.jvm.functions.Function2<java.lang.String, java.lang.Boolean, kotlin.Unit> */
    public final Function2<String, Boolean, Unit> getTimeoutCallback() {
        Function2 function2 = this.timeoutCallback;
        if (function2 != null) {
            return function2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("timeoutCallback");
        throw null;
    }

    public final void setTimeoutCallback(Function2<? super String, ? super Boolean, Unit> function2) {
        Intrinsics.checkNotNullParameter(function2, "<set-?>");
        this.timeoutCallback = function2;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:13:0x0034 */
    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        Object obj;
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(mediaData, "data");
        PlaybackStateListener playbackStateListener = this.mediaListeners.get(str);
        if (playbackStateListener == null) {
            obj = null;
        } else if (playbackStateListener.getDestroyed()) {
            Log.d("MediaTimeout", Intrinsics.stringPlus("Reusing destroyed listener ", str));
            obj = playbackStateListener;
        } else {
            return;
        }
        boolean z3 = false;
        if (str2 != null && !Intrinsics.areEqual(str, str2)) {
            Map<String, PlaybackStateListener> map = this.mediaListeners;
            Objects.requireNonNull(map, "null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
            obj = TypeIntrinsics.asMutableMap(map).remove(str2);
            if (obj != null) {
                Log.d("MediaTimeout", "migrating key " + ((Object) str2) + " to " + str + ", for resumption");
            } else {
                Log.w("MediaTimeout", "Old key " + ((Object) str2) + " for player " + str + " doesn't exist. Continuing...");
            }
        }
        PlaybackStateListener playbackStateListener2 = (PlaybackStateListener) obj;
        if (playbackStateListener2 == null) {
            this.mediaListeners.put(str, new PlaybackStateListener(this, str, mediaData));
            return;
        }
        Boolean playing = playbackStateListener2.getPlaying();
        if (playing != null) {
            z3 = playing.booleanValue();
        }
        Log.d("MediaTimeout", "updating listener for " + str + ", was playing? " + z3);
        playbackStateListener2.setMediaData(mediaData);
        playbackStateListener2.setKey(str);
        this.mediaListeners.put(str, playbackStateListener2);
        if (!Intrinsics.areEqual(Boolean.valueOf(z3), playbackStateListener2.getPlaying())) {
            this.mainExecutor.execute(new MediaTimeoutListener$onMediaDataLoaded$2$1(this, str));
        }
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        Intrinsics.checkNotNullParameter(str, "key");
        PlaybackStateListener remove = this.mediaListeners.remove(str);
        if (remove != null) {
            remove.destroy();
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: MediaTimeoutListener.kt */
    public final class PlaybackStateListener extends MediaController.Callback {
        private Runnable cancellation;
        private boolean destroyed;
        private String key;
        private MediaController mediaController;
        private MediaData mediaData;
        private Boolean playing;
        private Boolean resumption;
        final /* synthetic */ MediaTimeoutListener this$0;
        private boolean timedOut;

        public PlaybackStateListener(MediaTimeoutListener mediaTimeoutListener, String str, MediaData mediaData2) {
            Intrinsics.checkNotNullParameter(mediaTimeoutListener, "this$0");
            Intrinsics.checkNotNullParameter(str, "key");
            Intrinsics.checkNotNullParameter(mediaData2, "data");
            this.this$0 = mediaTimeoutListener;
            this.key = str;
            this.mediaData = mediaData2;
            setMediaData(mediaData2);
        }

        public final String getKey() {
            return this.key;
        }

        public final void setKey(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.key = str;
        }

        public final boolean getTimedOut() {
            return this.timedOut;
        }

        public final void setTimedOut(boolean z) {
            this.timedOut = z;
        }

        public final Boolean getPlaying() {
            return this.playing;
        }

        public final boolean getDestroyed() {
            return this.destroyed;
        }

        public final void setMediaData(MediaData mediaData2) {
            Intrinsics.checkNotNullParameter(mediaData2, "value");
            this.destroyed = false;
            MediaController mediaController2 = this.mediaController;
            if (mediaController2 != null) {
                mediaController2.unregisterCallback(this);
            }
            this.mediaData = mediaData2;
            PlaybackState playbackState = null;
            MediaController create = mediaData2.getToken() != null ? this.this$0.mediaControllerFactory.create(this.mediaData.getToken()) : null;
            this.mediaController = create;
            if (create != null) {
                create.registerCallback(this);
            }
            MediaController mediaController3 = this.mediaController;
            if (mediaController3 != null) {
                playbackState = mediaController3.getPlaybackState();
            }
            processState(playbackState, false);
        }

        public final void destroy() {
            MediaController mediaController2 = this.mediaController;
            if (mediaController2 != null) {
                mediaController2.unregisterCallback(this);
            }
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            this.destroyed = true;
        }

        public void onPlaybackStateChanged(PlaybackState playbackState) {
            processState(playbackState, true);
        }

        public void onSessionDestroyed() {
            Log.d("MediaTimeout", Intrinsics.stringPlus("Session destroyed for ", this.key));
            if (Intrinsics.areEqual(this.resumption, Boolean.TRUE)) {
                MediaController mediaController2 = this.mediaController;
                if (mediaController2 != null) {
                    mediaController2.unregisterCallback(this);
                    return;
                }
                return;
            }
            destroy();
        }

        private final void processState(PlaybackState playbackState, boolean z) {
            long j;
            Log.v("MediaTimeout", "processState " + this.key + ": " + playbackState);
            boolean z2 = playbackState != null && NotificationMediaManager.isPlayingState(playbackState.getState());
            boolean areEqual = true ^ Intrinsics.areEqual(this.resumption, Boolean.valueOf(this.mediaData.getResumption()));
            if (!Intrinsics.areEqual(this.playing, Boolean.valueOf(z2)) || this.playing == null || areEqual) {
                this.playing = Boolean.valueOf(z2);
                this.resumption = Boolean.valueOf(this.mediaData.getResumption());
                if (!z2) {
                    Log.v("MediaTimeout", "schedule timeout for " + this.key + " playing " + z2 + ", " + this.resumption);
                    if (this.cancellation == null || areEqual) {
                        String str = this.key;
                        expireMediaTimeout(str, "PLAYBACK STATE CHANGED - " + playbackState + ", " + this.resumption);
                        if (this.mediaData.getResumption()) {
                            j = MediaTimeoutListenerKt.getRESUME_MEDIA_TIMEOUT();
                        } else {
                            j = MediaTimeoutListenerKt.getPAUSED_MEDIA_TIMEOUT();
                        }
                        this.cancellation = this.this$0.mainExecutor.executeDelayed(new MediaTimeoutListener$PlaybackStateListener$processState$1(this, this.this$0), j);
                        return;
                    }
                    Log.d("MediaTimeout", "cancellation already exists, continuing.");
                    return;
                }
                String str2 = this.key;
                expireMediaTimeout(str2, "playback started - " + playbackState + ", " + this.key);
                this.timedOut = false;
                if (z) {
                    this.this$0.getTimeoutCallback().invoke(this.key, Boolean.valueOf(this.timedOut));
                }
            }
        }

        private final void expireMediaTimeout(String str, String str2) {
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                Log.v("MediaTimeout", "media timeout cancelled for  " + str + ", reason: " + str2);
                runnable.run();
            }
            this.cancellation = null;
        }
    }
}

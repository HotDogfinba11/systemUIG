package com.android.systemui.media;

import android.graphics.drawable.Drawable;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaDataManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaDeviceManager.kt */
public final class MediaDeviceManager implements MediaDataManager.Listener, Dumpable {
    private final Executor bgExecutor;
    private final MediaControllerFactory controllerFactory;
    private final Map<String, Entry> entries = new LinkedHashMap();
    private final Executor fgExecutor;
    private final Set<Listener> listeners = new LinkedHashSet();
    private final LocalMediaManagerFactory localMediaManagerFactory;
    private final MediaRouter2Manager mr2manager;

    /* compiled from: MediaDeviceManager.kt */
    public interface Listener {
        void onKeyRemoved(String str);

        void onMediaDeviceChanged(String str, String str2, MediaDeviceData mediaDeviceData);
    }

    public MediaDeviceManager(MediaControllerFactory mediaControllerFactory, LocalMediaManagerFactory localMediaManagerFactory2, MediaRouter2Manager mediaRouter2Manager, Executor executor, Executor executor2, DumpManager dumpManager) {
        Intrinsics.checkNotNullParameter(mediaControllerFactory, "controllerFactory");
        Intrinsics.checkNotNullParameter(localMediaManagerFactory2, "localMediaManagerFactory");
        Intrinsics.checkNotNullParameter(mediaRouter2Manager, "mr2manager");
        Intrinsics.checkNotNullParameter(executor, "fgExecutor");
        Intrinsics.checkNotNullParameter(executor2, "bgExecutor");
        Intrinsics.checkNotNullParameter(dumpManager, "dumpManager");
        this.controllerFactory = mediaControllerFactory;
        this.localMediaManagerFactory = localMediaManagerFactory2;
        this.mr2manager = mediaRouter2Manager;
        this.fgExecutor = executor;
        this.bgExecutor = executor2;
        String name = MediaDeviceManager.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "javaClass.name");
        dumpManager.registerDumpable(name, this);
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded(this, str, smartspaceMediaData, z);
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataRemoved(this, str, z);
    }

    public final boolean addListener(Listener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        return this.listeners.add(listener);
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        MediaController mediaController;
        Entry remove;
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(mediaData, "data");
        if (!(str2 == null || Intrinsics.areEqual(str2, str) || (remove = this.entries.remove(str2)) == null)) {
            remove.stop();
        }
        Entry entry = this.entries.get(str);
        if (entry == null || !Intrinsics.areEqual(entry.getToken(), mediaData.getToken())) {
            if (entry != null) {
                entry.stop();
            }
            MediaSession.Token token = mediaData.getToken();
            if (token == null) {
                mediaController = null;
            } else {
                mediaController = this.controllerFactory.create(token);
            }
            Entry entry2 = new Entry(this, str, str2, mediaController, this.localMediaManagerFactory.create(mediaData.getPackageName()));
            this.entries.put(str, entry2);
            entry2.start();
        }
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        Intrinsics.checkNotNullParameter(str, "key");
        Entry remove = this.entries.remove(str);
        if (remove != null) {
            remove.stop();
        }
        if (remove != null) {
            Iterator<T> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onKeyRemoved(str);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println("MediaDeviceManager state:");
        this.entries.forEach(new MediaDeviceManager$dump$1$1(printWriter, fileDescriptor, printWriter, strArr));
    }

    /* access modifiers changed from: private */
    public final void processDevice(String str, String str2, MediaDevice mediaDevice) {
        boolean z = mediaDevice != null;
        String str3 = null;
        Drawable iconWithoutBackground = mediaDevice == null ? null : mediaDevice.getIconWithoutBackground();
        if (mediaDevice != null) {
            str3 = mediaDevice.getName();
        }
        MediaDeviceData mediaDeviceData = new MediaDeviceData(z, iconWithoutBackground, str3);
        Iterator<T> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onMediaDeviceChanged(str, str2, mediaDeviceData);
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: MediaDeviceManager.kt */
    public final class Entry extends MediaController.Callback implements LocalMediaManager.DeviceCallback {
        private final MediaController controller;
        private MediaDevice current;
        private final String key;
        private final LocalMediaManager localMediaManager;
        private final String oldKey;
        private int playbackType;
        private boolean started;
        final /* synthetic */ MediaDeviceManager this$0;

        public Entry(MediaDeviceManager mediaDeviceManager, String str, String str2, MediaController mediaController, LocalMediaManager localMediaManager2) {
            Intrinsics.checkNotNullParameter(mediaDeviceManager, "this$0");
            Intrinsics.checkNotNullParameter(str, "key");
            Intrinsics.checkNotNullParameter(localMediaManager2, "localMediaManager");
            this.this$0 = mediaDeviceManager;
            this.key = str;
            this.oldKey = str2;
            this.controller = mediaController;
            this.localMediaManager = localMediaManager2;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getOldKey() {
            return this.oldKey;
        }

        public final MediaController getController() {
            return this.controller;
        }

        public final LocalMediaManager getLocalMediaManager() {
            return this.localMediaManager;
        }

        public final MediaSession.Token getToken() {
            MediaController mediaController = this.controller;
            if (mediaController == null) {
                return null;
            }
            return mediaController.getSessionToken();
        }

        private final void setCurrent(MediaDevice mediaDevice) {
            if (!this.started || !Intrinsics.areEqual(mediaDevice, this.current)) {
                this.current = mediaDevice;
                this.this$0.fgExecutor.execute(new MediaDeviceManager$Entry$current$1(this.this$0, this, mediaDevice));
            }
        }

        public final void start() {
            this.this$0.bgExecutor.execute(new MediaDeviceManager$Entry$start$1(this));
        }

        public final void stop() {
            this.this$0.bgExecutor.execute(new MediaDeviceManager$Entry$stop$1(this));
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            RoutingSessionInfo routingSessionInfo;
            List list;
            MediaController.PlaybackInfo playbackInfo;
            Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            Intrinsics.checkNotNullParameter(strArr, "args");
            MediaController mediaController = this.controller;
            Integer num = null;
            if (mediaController == null) {
                routingSessionInfo = null;
            } else {
                routingSessionInfo = this.this$0.mr2manager.getRoutingSessionForMediaController(mediaController);
            }
            if (routingSessionInfo == null) {
                list = null;
            } else {
                list = this.this$0.mr2manager.getSelectedRoutes(routingSessionInfo);
            }
            MediaDevice mediaDevice = this.current;
            printWriter.println(Intrinsics.stringPlus("    current device is ", mediaDevice == null ? null : mediaDevice.getName()));
            MediaController controller2 = getController();
            if (!(controller2 == null || (playbackInfo = controller2.getPlaybackInfo()) == null)) {
                num = Integer.valueOf(playbackInfo.getPlaybackType());
            }
            printWriter.println("    PlaybackType=" + num + " (1 for local, 2 for remote) cached=" + this.playbackType);
            printWriter.println(Intrinsics.stringPlus("    routingSession=", routingSessionInfo));
            printWriter.println(Intrinsics.stringPlus("    selectedRoutes=", list));
        }

        public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            int playbackType2 = playbackInfo == null ? 0 : playbackInfo.getPlaybackType();
            if (playbackType2 != this.playbackType) {
                this.playbackType = playbackType2;
                updateCurrent();
            }
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public void onDeviceListUpdate(List<? extends MediaDevice> list) {
            this.this$0.bgExecutor.execute(new MediaDeviceManager$Entry$onDeviceListUpdate$1(this));
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public void onSelectedDeviceStateChanged(MediaDevice mediaDevice, int i) {
            Intrinsics.checkNotNullParameter(mediaDevice, "device");
            this.this$0.bgExecutor.execute(new MediaDeviceManager$Entry$onSelectedDeviceStateChanged$1(this));
        }

        /* access modifiers changed from: private */
        public final void updateCurrent() {
            MediaDevice currentConnectedDevice = this.localMediaManager.getCurrentConnectedDevice();
            MediaController mediaController = this.controller;
            Unit unit = null;
            MediaDevice mediaDevice = null;
            if (mediaController != null) {
                if (this.this$0.mr2manager.getRoutingSessionForMediaController(mediaController) != null) {
                    mediaDevice = currentConnectedDevice;
                }
                setCurrent(mediaDevice);
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                setCurrent(currentConnectedDevice);
            }
        }
    }
}

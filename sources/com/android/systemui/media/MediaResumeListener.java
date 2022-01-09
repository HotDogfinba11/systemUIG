package com.android.systemui.media;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener implements MediaDataManager.Listener, Dumpable {
    private final Executor backgroundExecutor;
    private final BroadcastDispatcher broadcastDispatcher;
    private final Context context;
    private int currentUserId;
    private ResumeMediaBrowser mediaBrowser;
    private final MediaResumeListener$mediaBrowserCallback$1 mediaBrowserCallback;
    private final ResumeMediaBrowserFactory mediaBrowserFactory;
    private MediaDataManager mediaDataManager;
    private final ConcurrentLinkedQueue<Pair<ComponentName, Long>> resumeComponents = new ConcurrentLinkedQueue<>();
    private final SystemClock systemClock;
    private final TunerService tunerService;
    private boolean useMediaResumption;
    private final BroadcastReceiver userChangeReceiver;

    @VisibleForTesting
    public static /* synthetic */ void getUserChangeReceiver$annotations() {
    }

    public MediaResumeListener(Context context2, BroadcastDispatcher broadcastDispatcher2, Executor executor, TunerService tunerService2, ResumeMediaBrowserFactory resumeMediaBrowserFactory, DumpManager dumpManager, SystemClock systemClock2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(executor, "backgroundExecutor");
        Intrinsics.checkNotNullParameter(tunerService2, "tunerService");
        Intrinsics.checkNotNullParameter(resumeMediaBrowserFactory, "mediaBrowserFactory");
        Intrinsics.checkNotNullParameter(dumpManager, "dumpManager");
        Intrinsics.checkNotNullParameter(systemClock2, "systemClock");
        this.context = context2;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.backgroundExecutor = executor;
        this.tunerService = tunerService2;
        this.mediaBrowserFactory = resumeMediaBrowserFactory;
        this.systemClock = systemClock2;
        this.useMediaResumption = Utils.useMediaResumption(context2);
        this.currentUserId = context2.getUserId();
        MediaResumeListener$userChangeReceiver$1 mediaResumeListener$userChangeReceiver$1 = new MediaResumeListener$userChangeReceiver$1(this);
        this.userChangeReceiver = mediaResumeListener$userChangeReceiver$1;
        this.mediaBrowserCallback = new MediaResumeListener$mediaBrowserCallback$1(this);
        if (this.useMediaResumption) {
            dumpManager.registerDumpable("MediaResumeListener", this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_UNLOCKED");
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            broadcastDispatcher2.registerReceiver(mediaResumeListener$userChangeReceiver$1, intentFilter, null, UserHandle.ALL);
            loadSavedComponents();
        }
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        MediaDataManager.Listener.DefaultImpls.onMediaDataRemoved(this, str);
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded(this, str, smartspaceMediaData, z);
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataRemoved(this, str, z);
    }

    public final void setManager(MediaDataManager mediaDataManager2) {
        Intrinsics.checkNotNullParameter(mediaDataManager2, "manager");
        this.mediaDataManager = mediaDataManager2;
        this.tunerService.addTunable(new MediaResumeListener$setManager$1(this), "qs_media_resumption");
    }

    /* access modifiers changed from: private */
    public final void loadSavedComponents() {
        long j;
        List<String> split;
        boolean z;
        this.resumeComponents.clear();
        boolean z2 = false;
        List<String> list = null;
        String string = this.context.getSharedPreferences("media_control_prefs", 0).getString(Intrinsics.stringPlus("browser_components_", Integer.valueOf(this.currentUserId)), null);
        if (string != null && (split = new Regex(":").split(string, 0)) != null) {
            if (!split.isEmpty()) {
                ListIterator<String> listIterator = split.listIterator(split.size());
                while (true) {
                    if (!listIterator.hasPrevious()) {
                        break;
                    }
                    if (listIterator.previous().length() == 0) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (!z) {
                        list = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            }
            list = CollectionsKt__CollectionsKt.emptyList();
        }
        if (list != null) {
            boolean z3 = false;
            for (String str : list) {
                List list2 = StringsKt__StringsKt.split$default(str, new String[]{"/"}, false, 0, 6, null);
                ComponentName componentName = new ComponentName((String) list2.get(0), (String) list2.get(1));
                if (list2.size() == 3) {
                    try {
                        j = Long.parseLong((String) list2.get(2));
                    } catch (NumberFormatException unused) {
                        j = this.systemClock.currentTimeMillis();
                    }
                } else {
                    j = this.systemClock.currentTimeMillis();
                    z3 = true;
                }
                this.resumeComponents.add(TuplesKt.to(componentName, Long.valueOf(j)));
            }
            z2 = z3;
        }
        String arrays = Arrays.toString(this.resumeComponents.toArray());
        Intrinsics.checkNotNullExpressionValue(arrays, "java.util.Arrays.toString(this)");
        Log.d("MediaResumeListener", Intrinsics.stringPlus("loaded resume components ", arrays));
        if (z2) {
            writeSharedPrefs();
        }
    }

    /* access modifiers changed from: private */
    public final void loadMediaResumptionControls() {
        if (this.useMediaResumption) {
            long currentTimeMillis = this.systemClock.currentTimeMillis();
            for (T t : this.resumeComponents) {
                if (currentTimeMillis - ((Number) t.getSecond()).longValue() <= MediaTimeoutListenerKt.getRESUME_MEDIA_TIMEOUT()) {
                    this.mediaBrowserFactory.create(this.mediaBrowserCallback, (ComponentName) t.getFirst()).findRecentMedia();
                }
            }
        }
    }

    @Override // com.android.systemui.media.MediaDataManager.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, boolean z2) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(mediaData, "data");
        if (this.useMediaResumption) {
            if (!str.equals(str2)) {
                ResumeMediaBrowser resumeMediaBrowser = this.mediaBrowser;
                if (resumeMediaBrowser != null) {
                    resumeMediaBrowser.disconnect();
                }
                this.mediaBrowser = null;
            }
            if (mediaData.getResumeAction() == null && !mediaData.getHasCheckedForResume() && mediaData.isLocalSession()) {
                Log.d("MediaResumeListener", Intrinsics.stringPlus("Checking for service component for ", mediaData.getPackageName()));
                List<ResolveInfo> queryIntentServices = this.context.getPackageManager().queryIntentServices(new Intent("android.media.browse.MediaBrowserService"), 0);
                if (queryIntentServices == null) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList();
                    for (T t : queryIntentServices) {
                        if (Intrinsics.areEqual(((ResolveInfo) t).serviceInfo.packageName, mediaData.getPackageName())) {
                            arrayList.add(t);
                        }
                    }
                }
                if (arrayList == null || arrayList.size() <= 0) {
                    MediaDataManager mediaDataManager2 = this.mediaDataManager;
                    if (mediaDataManager2 != null) {
                        mediaDataManager2.setResumeAction(str, null);
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("mediaDataManager");
                        throw null;
                    }
                } else {
                    this.backgroundExecutor.execute(new MediaResumeListener$onMediaDataLoaded$1(this, str, arrayList));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public final void tryUpdateResumptionList(String str, ComponentName componentName) {
        Log.d("MediaResumeListener", Intrinsics.stringPlus("Testing if we can connect to ", componentName));
        MediaDataManager mediaDataManager2 = this.mediaDataManager;
        if (mediaDataManager2 != null) {
            mediaDataManager2.setResumeAction(str, null);
            ResumeMediaBrowser resumeMediaBrowser = this.mediaBrowser;
            if (resumeMediaBrowser != null) {
                resumeMediaBrowser.disconnect();
            }
            ResumeMediaBrowser create = this.mediaBrowserFactory.create(new MediaResumeListener$tryUpdateResumptionList$1(componentName, this, str), componentName);
            this.mediaBrowser = create;
            if (create != null) {
                create.testConnection();
                return;
            }
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaDataManager");
        throw null;
    }

    /* access modifiers changed from: private */
    public final void updateResumptionList(ComponentName componentName) {
        T t;
        ConcurrentLinkedQueue<Pair<ComponentName, Long>> concurrentLinkedQueue = this.resumeComponents;
        Iterator<T> it = concurrentLinkedQueue.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (((ComponentName) t.getFirst()).equals(componentName)) {
                break;
            }
        }
        concurrentLinkedQueue.remove(t);
        this.resumeComponents.add(TuplesKt.to(componentName, Long.valueOf(this.systemClock.currentTimeMillis())));
        if (this.resumeComponents.size() > 5) {
            this.resumeComponents.remove();
        }
        writeSharedPrefs();
    }

    private final void writeSharedPrefs() {
        StringBuilder sb = new StringBuilder();
        for (T t : this.resumeComponents) {
            sb.append(((ComponentName) t.getFirst()).flattenToString());
            sb.append("/");
            sb.append(((Number) t.getSecond()).longValue());
            sb.append(":");
        }
        this.context.getSharedPreferences("media_control_prefs", 0).edit().putString(Intrinsics.stringPlus("browser_components_", Integer.valueOf(this.currentUserId)), sb.toString()).apply();
    }

    /* access modifiers changed from: private */
    public final Runnable getResumeAction(ComponentName componentName) {
        return new MediaResumeListener$getResumeAction$1(this, componentName);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println(Intrinsics.stringPlus("resumeComponents: ", this.resumeComponents));
    }
}

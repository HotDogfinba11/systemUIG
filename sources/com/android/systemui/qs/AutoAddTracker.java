package com.android.systemui.qs;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.UserAwareController;
import com.android.systemui.util.settings.SecureSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: AutoAddTracker.kt */
public final class AutoAddTracker implements UserAwareController, Dumpable {
    public static final Companion Companion = new Companion(null);
    private static final IntentFilter FILTER = new IntentFilter("android.os.action.SETTING_RESTORED");
    private final ArraySet<String> autoAdded = new ArraySet<>();
    private final Executor backgroundExecutor;
    private final BroadcastDispatcher broadcastDispatcher;
    private final AutoAddTracker$contentObserver$1 contentObserver;
    private final DumpManager dumpManager;
    private final Handler mainHandler;
    private final QSHost qsHost;
    private final AutoAddTracker$restoreReceiver$1 restoreReceiver;
    private Set<String> restoredTiles;
    private final SecureSettings secureSettings;
    private int userId;

    public AutoAddTracker(SecureSettings secureSettings2, BroadcastDispatcher broadcastDispatcher2, QSHost qSHost, DumpManager dumpManager2, Handler handler, Executor executor, int i) {
        Intrinsics.checkNotNullParameter(secureSettings2, "secureSettings");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(qSHost, "qsHost");
        Intrinsics.checkNotNullParameter(dumpManager2, "dumpManager");
        Intrinsics.checkNotNullParameter(executor, "backgroundExecutor");
        this.secureSettings = secureSettings2;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.qsHost = qSHost;
        this.dumpManager = dumpManager2;
        this.mainHandler = handler;
        this.backgroundExecutor = executor;
        this.userId = i;
        this.contentObserver = new AutoAddTracker$contentObserver$1(this, handler);
        this.restoreReceiver = new AutoAddTracker$restoreReceiver$1(this);
    }

    /* compiled from: AutoAddTracker.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* access modifiers changed from: private */
    public final void processRestoreIntent(Intent intent) {
        String tilesFromListLocked;
        List list;
        String stringExtra = intent.getStringExtra("setting_name");
        Unit unit = null;
        r2 = null;
        Set<String> set = null;
        List list2 = null;
        if (Intrinsics.areEqual(stringExtra, "sysui_qs_tiles")) {
            String stringExtra2 = intent.getStringExtra("new_value");
            if (!(stringExtra2 == null || (list = StringsKt__StringsKt.split$default(stringExtra2, new String[]{","}, false, 0, 6, null)) == null)) {
                set = CollectionsKt___CollectionsKt.toSet(list);
            }
            if (set == null) {
                Log.w("AutoAddTracker", Intrinsics.stringPlus("Null restored tiles for user ", Integer.valueOf(this.userId)));
                set = SetsKt__SetsKt.emptySet();
            }
            this.restoredTiles = set;
        } else if (Intrinsics.areEqual(stringExtra, "qs_auto_tiles")) {
            Set<String> set2 = this.restoredTiles;
            if (set2 != null) {
                String stringExtra3 = intent.getStringExtra("new_value");
                List list3 = stringExtra3 == null ? null : StringsKt__StringsKt.split$default(stringExtra3, new String[]{","}, false, 0, 6, null);
                if (list3 == null) {
                    list3 = CollectionsKt__CollectionsKt.emptyList();
                }
                String stringExtra4 = intent.getStringExtra("previous_value");
                if (stringExtra4 != null) {
                    list2 = StringsKt__StringsKt.split$default(stringExtra4, new String[]{","}, false, 0, 6, null);
                }
                if (list2 == null) {
                    list2 = CollectionsKt__CollectionsKt.emptyList();
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj : list3) {
                    if (!set2.contains((String) obj)) {
                        arrayList.add(obj);
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.qsHost.removeTiles(arrayList);
                }
                synchronized (this.autoAdded) {
                    this.autoAdded.clear();
                    this.autoAdded.addAll(CollectionsKt___CollectionsKt.plus((Collection) list3, (Iterable) list2));
                    tilesFromListLocked = getTilesFromListLocked();
                }
                saveTiles(tilesFromListLocked);
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                Log.w("AutoAddTracker", Intrinsics.stringPlus("qs_auto_tiles restored before sysui_qs_tiles for user ", Integer.valueOf(this.userId)));
            }
        }
    }

    public final void initialize() {
        this.dumpManager.registerDumpable("AutoAddTracker", this);
        loadTiles();
        SecureSettings secureSettings2 = this.secureSettings;
        secureSettings2.registerContentObserverForUser(secureSettings2.getUriFor("qs_auto_tiles"), this.contentObserver, -1);
        registerBroadcastReceiver();
    }

    private final void registerBroadcastReceiver() {
        this.broadcastDispatcher.registerReceiver(this.restoreReceiver, FILTER, this.backgroundExecutor, UserHandle.of(this.userId));
    }

    private final void unregisterBroadcastReceiver() {
        this.broadcastDispatcher.unregisterReceiver(this.restoreReceiver);
    }

    @Override // com.android.systemui.util.UserAwareController
    public void changeUser(UserHandle userHandle) {
        Intrinsics.checkNotNullParameter(userHandle, "newUser");
        if (userHandle.getIdentifier() != this.userId) {
            unregisterBroadcastReceiver();
            this.userId = userHandle.getIdentifier();
            this.restoredTiles = null;
            loadTiles();
            registerBroadcastReceiver();
        }
    }

    public final boolean isAdded(String str) {
        boolean contains;
        Intrinsics.checkNotNullParameter(str, "tile");
        synchronized (this.autoAdded) {
            contains = this.autoAdded.contains(str);
        }
        return contains;
    }

    public final void setTileAdded(String str) {
        String tilesFromListLocked;
        Intrinsics.checkNotNullParameter(str, "tile");
        synchronized (this.autoAdded) {
            tilesFromListLocked = this.autoAdded.add(str) ? getTilesFromListLocked() : null;
        }
        if (tilesFromListLocked != null) {
            saveTiles(tilesFromListLocked);
        }
    }

    public final void setTileRemoved(String str) {
        String tilesFromListLocked;
        Intrinsics.checkNotNullParameter(str, "tile");
        synchronized (this.autoAdded) {
            tilesFromListLocked = this.autoAdded.remove(str) ? getTilesFromListLocked() : null;
        }
        if (tilesFromListLocked != null) {
            saveTiles(tilesFromListLocked);
        }
    }

    private final String getTilesFromListLocked() {
        String join = TextUtils.join(",", this.autoAdded);
        Intrinsics.checkNotNullExpressionValue(join, "join(\",\", autoAdded)");
        return join;
    }

    private final void saveTiles(String str) {
        this.secureSettings.putStringForUser("qs_auto_tiles", str, null, false, this.userId, true);
    }

    /* access modifiers changed from: private */
    public final void loadTiles() {
        synchronized (this.autoAdded) {
            this.autoAdded.clear();
            this.autoAdded.addAll(getAdded());
        }
    }

    private final Collection<String> getAdded() {
        List list;
        String stringForUser = this.secureSettings.getStringForUser("qs_auto_tiles", this.userId);
        if (stringForUser == null) {
            list = null;
        } else {
            list = StringsKt__StringsKt.split$default(stringForUser, new String[]{","}, false, 0, 6, null);
        }
        return list == null ? SetsKt__SetsKt.emptySet() : list;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println(Intrinsics.stringPlus("Current user: ", Integer.valueOf(this.userId)));
        printWriter.println(Intrinsics.stringPlus("Added tiles: ", this.autoAdded));
    }

    /* compiled from: AutoAddTracker.kt */
    public static final class Builder {
        private final BroadcastDispatcher broadcastDispatcher;
        private final DumpManager dumpManager;
        private final Executor executor;
        private final Handler handler;
        private final QSHost qsHost;
        private final SecureSettings secureSettings;
        private int userId;

        public Builder(SecureSettings secureSettings2, BroadcastDispatcher broadcastDispatcher2, QSHost qSHost, DumpManager dumpManager2, Handler handler2, Executor executor2) {
            Intrinsics.checkNotNullParameter(secureSettings2, "secureSettings");
            Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
            Intrinsics.checkNotNullParameter(qSHost, "qsHost");
            Intrinsics.checkNotNullParameter(dumpManager2, "dumpManager");
            Intrinsics.checkNotNullParameter(handler2, "handler");
            Intrinsics.checkNotNullParameter(executor2, "executor");
            this.secureSettings = secureSettings2;
            this.broadcastDispatcher = broadcastDispatcher2;
            this.qsHost = qSHost;
            this.dumpManager = dumpManager2;
            this.handler = handler2;
            this.executor = executor2;
        }

        public final Builder setUserId(int i) {
            this.userId = i;
            return this;
        }

        public final AutoAddTracker build() {
            return new AutoAddTracker(this.secureSettings, this.broadcastDispatcher, this.qsHost, this.dumpManager, this.handler, this.executor, this.userId);
        }
    }
}

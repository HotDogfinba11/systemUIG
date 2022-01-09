package com.android.systemui.privacy;

import android.content.IntentFilter;
import android.os.UserHandle;
import androidx.constraintlayout.widget.R$styleable;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController implements Dumpable {
    public static final Companion Companion;
    private static final int[] OPS;
    private static final int[] OPS_LOCATION = {0, 1};
    private static final int[] OPS_MIC_CAMERA = {26, R$styleable.Constraint_layout_goneMarginRight, 27, 100};
    private static final IntentFilter intentFilter;
    private boolean allIndicatorsAvailable;
    private final AppOpsController appOpsController;
    private final DelayableExecutor bgExecutor;
    private final List<WeakReference<Callback>> callbacks = new ArrayList();
    private final PrivacyItemController$cb$1 cb;
    private List<Integer> currentUserIds = CollectionsKt__CollectionsKt.emptyList();
    private final DeviceConfigProxy deviceConfigProxy;
    private final PrivacyItemController$devicePropertiesChangedListener$1 devicePropertiesChangedListener;
    private Runnable holdingRunnableCanceler;
    private final MyExecutor internalUiExecutor;
    private boolean listening;
    private boolean locationAvailable;
    private final PrivacyLogger logger;
    private boolean micCameraAvailable;
    private final Runnable notifyChanges;
    private List<PrivacyItem> privacyList = CollectionsKt__CollectionsKt.emptyList();
    private final SystemClock systemClock;
    private final Runnable updateListAndNotifyChanges;
    private final UserTracker userTracker;
    private UserTracker.Callback userTrackerCallback;

    /* compiled from: PrivacyItemController.kt */
    public interface Callback {
        default void onFlagLocationChanged(boolean z) {
        }

        default void onFlagMicCameraChanged(boolean z) {
        }

        void onPrivacyItemsChanged(List<PrivacyItem> list);
    }

    @VisibleForTesting
    public static /* synthetic */ void getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    @VisibleForTesting
    public static /* synthetic */ void getUserTrackerCallback$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public PrivacyItemController(AppOpsController appOpsController2, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, DeviceConfigProxy deviceConfigProxy2, UserTracker userTracker2, PrivacyLogger privacyLogger, SystemClock systemClock2, DumpManager dumpManager) {
        Intrinsics.checkNotNullParameter(appOpsController2, "appOpsController");
        Intrinsics.checkNotNullParameter(delayableExecutor, "uiExecutor");
        Intrinsics.checkNotNullParameter(delayableExecutor2, "bgExecutor");
        Intrinsics.checkNotNullParameter(deviceConfigProxy2, "deviceConfigProxy");
        Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
        Intrinsics.checkNotNullParameter(privacyLogger, "logger");
        Intrinsics.checkNotNullParameter(systemClock2, "systemClock");
        Intrinsics.checkNotNullParameter(dumpManager, "dumpManager");
        this.appOpsController = appOpsController2;
        this.bgExecutor = delayableExecutor2;
        this.deviceConfigProxy = deviceConfigProxy2;
        this.userTracker = userTracker2;
        this.logger = privacyLogger;
        this.systemClock = systemClock2;
        this.internalUiExecutor = new MyExecutor(this, delayableExecutor);
        this.notifyChanges = new PrivacyItemController$notifyChanges$1(this);
        this.updateListAndNotifyChanges = new PrivacyItemController$updateListAndNotifyChanges$1(this, delayableExecutor);
        this.micCameraAvailable = isMicCameraEnabled();
        boolean isLocationEnabled = isLocationEnabled();
        this.locationAvailable = isLocationEnabled;
        this.allIndicatorsAvailable = this.micCameraAvailable && isLocationEnabled;
        PrivacyItemController$devicePropertiesChangedListener$1 privacyItemController$devicePropertiesChangedListener$1 = new PrivacyItemController$devicePropertiesChangedListener$1(this);
        this.devicePropertiesChangedListener = privacyItemController$devicePropertiesChangedListener$1;
        this.cb = new PrivacyItemController$cb$1(this);
        this.userTrackerCallback = new PrivacyItemController$userTrackerCallback$1(this);
        deviceConfigProxy2.addOnPropertiesChangedListener("privacy", delayableExecutor, privacyItemController$devicePropertiesChangedListener$1);
        dumpManager.registerDumpable("PrivacyItemController", this);
    }

    @VisibleForTesting
    /* compiled from: PrivacyItemController.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @VisibleForTesting
        public static /* synthetic */ void getTIME_TO_HOLD_INDICATORS$annotations() {
        }

        private Companion() {
        }

        public final int[] getOPS_MIC_CAMERA() {
            return PrivacyItemController.OPS_MIC_CAMERA;
        }

        public final int[] getOPS_LOCATION() {
            return PrivacyItemController.OPS_LOCATION;
        }

        public final int[] getOPS() {
            return PrivacyItemController.OPS;
        }
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        OPS = ArraysKt___ArraysJvmKt.plus(companion.getOPS_MIC_CAMERA(), companion.getOPS_LOCATION());
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_SWITCHED");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        intentFilter = intentFilter2;
    }

    public final synchronized List<PrivacyItem> getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return CollectionsKt___CollectionsKt.toList(this.privacyList);
    }

    private final boolean isMicCameraEnabled() {
        return this.deviceConfigProxy.getBoolean("privacy", "camera_mic_icons_enabled", true);
    }

    private final boolean isLocationEnabled() {
        return this.deviceConfigProxy.getBoolean("privacy", "location_indicators_enabled", false);
    }

    public final boolean getMicCameraAvailable() {
        return this.micCameraAvailable;
    }

    public final boolean getLocationAvailable() {
        return this.locationAvailable;
    }

    public final void setLocationAvailable(boolean z) {
        this.locationAvailable = z;
    }

    public final boolean getAllIndicatorsAvailable() {
        return this.allIndicatorsAvailable;
    }

    public final void setAllIndicatorsAvailable(boolean z) {
        this.allIndicatorsAvailable = z;
    }

    private final void unregisterListener() {
        this.userTracker.removeCallback(this.userTrackerCallback);
    }

    private final void registerReceiver() {
        this.userTracker.addCallback(this.userTrackerCallback, this.bgExecutor);
    }

    /* access modifiers changed from: private */
    public final void update(boolean z) {
        this.bgExecutor.execute(new PrivacyItemController$update$1(z, this));
    }

    /* access modifiers changed from: private */
    public final void setListeningState() {
        boolean z = (!this.callbacks.isEmpty()) & (this.micCameraAvailable || this.locationAvailable);
        if (this.listening != z) {
            this.listening = z;
            if (z) {
                this.appOpsController.addCallback(Companion.getOPS(), this.cb);
                registerReceiver();
                update(true);
                return;
            }
            this.appOpsController.removeCallback(Companion.getOPS(), this.cb);
            unregisterListener();
            update(false);
        }
    }

    private final void addCallback(WeakReference<Callback> weakReference) {
        this.callbacks.add(weakReference);
        if ((!this.callbacks.isEmpty()) && !this.listening) {
            this.internalUiExecutor.updateListeningState();
        } else if (this.listening) {
            this.internalUiExecutor.execute(new NotifyChangesToCallback(weakReference.get(), getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core()));
        }
    }

    private final void removeCallback(WeakReference<Callback> weakReference) {
        this.callbacks.removeIf(new PrivacyItemController$removeCallback$1(weakReference));
        if (this.callbacks.isEmpty()) {
            this.internalUiExecutor.updateListeningState();
        }
    }

    public final void addCallback(Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        addCallback(new WeakReference<>(callback));
    }

    public final void removeCallback(Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        removeCallback(new WeakReference<>(callback));
    }

    /* access modifiers changed from: private */
    public final void updatePrivacyList() {
        Runnable runnable = this.holdingRunnableCanceler;
        if (runnable != null) {
            runnable.run();
            Unit unit = Unit.INSTANCE;
            this.holdingRunnableCanceler = null;
        }
        if (!this.listening) {
            this.privacyList = CollectionsKt__CollectionsKt.emptyList();
            return;
        }
        List<AppOpItem> activeAppOps = this.appOpsController.getActiveAppOps(true);
        Intrinsics.checkNotNullExpressionValue(activeAppOps, "appOpsController.getActiveAppOps(true)");
        ArrayList<AppOpItem> arrayList = new ArrayList();
        for (T t : activeAppOps) {
            T t2 = t;
            if (this.currentUserIds.contains(Integer.valueOf(UserHandle.getUserId(t2.getUid()))) || t2.getCode() == 100 || t2.getCode() == 101) {
                arrayList.add(t);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (AppOpItem appOpItem : arrayList) {
            Intrinsics.checkNotNullExpressionValue(appOpItem, "it");
            PrivacyItem privacyItem = toPrivacyItem(appOpItem);
            if (privacyItem != null) {
                arrayList2.add(privacyItem);
            }
        }
        this.privacyList = processNewList(CollectionsKt___CollectionsKt.distinct(arrayList2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v6 */
    private final List<PrivacyItem> processNewList(List<PrivacyItem> list) {
        PrivacyItem privacyItem;
        this.logger.logRetrievedPrivacyItemsList(list);
        long elapsedRealtime = this.systemClock.elapsedRealtime() - 5000;
        List<PrivacyItem> privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            T next = it.next();
            T t = next;
            if (t.getTimeStampElapsed() <= elapsedRealtime || isIn(t, list)) {
                z = false;
            }
            if (z) {
                arrayList.add(next);
            }
        }
        if (!arrayList.isEmpty()) {
            this.logger.logPrivacyItemsToHold(arrayList);
            Iterator it2 = arrayList.iterator();
            if (!it2.hasNext()) {
                privacyItem = null;
            } else {
                Object next2 = it2.next();
                if (!it2.hasNext()) {
                    privacyItem = next2;
                } else {
                    long timeStampElapsed = ((PrivacyItem) next2).getTimeStampElapsed();
                    do {
                        Object next3 = it2.next();
                        long timeStampElapsed2 = ((PrivacyItem) next3).getTimeStampElapsed();
                        if (timeStampElapsed > timeStampElapsed2) {
                            next2 = next3;
                            timeStampElapsed = timeStampElapsed2;
                        }
                    } while (it2.hasNext());
                }
                privacyItem = next2;
            }
            PrivacyItem privacyItem2 = privacyItem;
            Intrinsics.checkNotNull(privacyItem2);
            long timeStampElapsed3 = privacyItem2.getTimeStampElapsed() - elapsedRealtime;
            this.logger.logPrivacyItemsUpdateScheduled(timeStampElapsed3);
            this.holdingRunnableCanceler = this.bgExecutor.executeDelayed(this.updateListAndNotifyChanges, timeStampElapsed3);
        }
        ArrayList arrayList2 = new ArrayList();
        for (T t2 : list) {
            if (!t2.getPaused()) {
                arrayList2.add(t2);
            }
        }
        return CollectionsKt___CollectionsKt.plus((Collection) arrayList2, (Iterable) arrayList);
    }

    private final PrivacyItem toPrivacyItem(AppOpItem appOpItem) {
        PrivacyType privacyType;
        int code = appOpItem.getCode();
        if (code == 0 || code == 1) {
            privacyType = PrivacyType.TYPE_LOCATION;
        } else {
            if (code != 26) {
                if (code == 27 || code == 100) {
                    privacyType = PrivacyType.TYPE_MICROPHONE;
                } else if (code != 101) {
                    return null;
                }
            }
            privacyType = PrivacyType.TYPE_CAMERA;
        }
        if (privacyType == PrivacyType.TYPE_LOCATION && !this.locationAvailable) {
            return null;
        }
        String packageName = appOpItem.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "appOpItem.packageName");
        return new PrivacyItem(privacyType, new PrivacyApplication(packageName, appOpItem.getUid()), appOpItem.getTimeStartedElapsed(), appOpItem.isDisabled());
    }

    /* access modifiers changed from: private */
    /* compiled from: PrivacyItemController.kt */
    public static final class NotifyChangesToCallback implements Runnable {
        private final Callback callback;
        private final List<PrivacyItem> list;

        public NotifyChangesToCallback(Callback callback2, List<PrivacyItem> list2) {
            Intrinsics.checkNotNullParameter(list2, "list");
            this.callback = callback2;
            this.list = list2;
        }

        public void run() {
            Callback callback2 = this.callback;
            if (callback2 != null) {
                callback2.onPrivacyItemsChanged(this.list);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println("PrivacyItemController state:");
        printWriter.println(Intrinsics.stringPlus("  Listening: ", Boolean.valueOf(this.listening)));
        printWriter.println(Intrinsics.stringPlus("  Current user ids: ", this.currentUserIds));
        printWriter.println("  Privacy Items:");
        Iterator<T> it = getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core().iterator();
        while (it.hasNext()) {
            printWriter.print("    ");
            printWriter.println(it.next().toString());
        }
        printWriter.println("  Callbacks:");
        Iterator<T> it2 = this.callbacks.iterator();
        while (it2.hasNext()) {
            Callback callback = (Callback) it2.next().get();
            if (callback != null) {
                printWriter.print("    ");
                printWriter.println(callback.toString());
            }
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: PrivacyItemController.kt */
    public final class MyExecutor implements Executor {
        private final DelayableExecutor delegate;
        private Runnable listeningCanceller;
        final /* synthetic */ PrivacyItemController this$0;

        public MyExecutor(PrivacyItemController privacyItemController, DelayableExecutor delayableExecutor) {
            Intrinsics.checkNotNullParameter(privacyItemController, "this$0");
            Intrinsics.checkNotNullParameter(delayableExecutor, "delegate");
            this.this$0 = privacyItemController;
            this.delegate = delayableExecutor;
        }

        public void execute(Runnable runnable) {
            Intrinsics.checkNotNullParameter(runnable, "command");
            this.delegate.execute(runnable);
        }

        public final void updateListeningState() {
            Runnable runnable = this.listeningCanceller;
            if (runnable != null) {
                runnable.run();
            }
            this.listeningCanceller = this.delegate.executeDelayed(new PrivacyItemController$MyExecutor$updateListeningState$1(this.this$0), 0);
        }
    }

    private final boolean isIn(PrivacyItem privacyItem, List<PrivacyItem> list) {
        boolean z;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (T t : list) {
                if (t.getPrivacyType() == privacyItem.getPrivacyType() && Intrinsics.areEqual(t.getApplication(), privacyItem.getApplication()) && t.getTimeStampElapsed() == privacyItem.getTimeStampElapsed()) {
                    z = true;
                    continue;
                } else {
                    z = false;
                    continue;
                }
                if (z) {
                    return true;
                }
            }
        }
        return false;
    }
}

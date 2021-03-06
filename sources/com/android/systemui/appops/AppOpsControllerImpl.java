package com.android.systemui.appops;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArraySet;
import android.util.SparseArray;
import androidx.constraintlayout.widget.R$styleable;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AppOpsControllerImpl extends BroadcastReceiver implements AppOpsController, AppOpsManager.OnOpActiveChangedListener, AppOpsManager.OnOpNotedListener, IndividualSensorPrivacyController.Callback, Dumpable {
    protected static final int[] OPS = {42, 26, R$styleable.Constraint_layout_goneMarginRight, 24, 27, 100, 0, 1};
    private final List<AppOpItem> mActiveItems = new ArrayList();
    private final AppOpsManager mAppOps;
    private final AudioManager mAudioManager;
    private AudioManager.AudioRecordingCallback mAudioRecordingCallback = new AudioManager.AudioRecordingCallback() {
        /* class com.android.systemui.appops.AppOpsControllerImpl.AnonymousClass1 */

        @Override // android.media.AudioManager.AudioRecordingCallback
        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
            synchronized (AppOpsControllerImpl.this.mActiveItems) {
                AppOpsControllerImpl.this.mRecordingsByUid.clear();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    AudioRecordingConfiguration audioRecordingConfiguration = list.get(i);
                    ArrayList arrayList = (ArrayList) AppOpsControllerImpl.this.mRecordingsByUid.get(audioRecordingConfiguration.getClientUid());
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        AppOpsControllerImpl.this.mRecordingsByUid.put(audioRecordingConfiguration.getClientUid(), arrayList);
                    }
                    arrayList.add(audioRecordingConfiguration);
                }
            }
            AppOpsControllerImpl.this.updateSensorDisabledStatus();
        }
    };
    private H mBGHandler;
    private final List<AppOpsController.Callback> mCallbacks = new ArrayList();
    private final SparseArray<Set<AppOpsController.Callback>> mCallbacksByCode = new SparseArray<>();
    private boolean mCameraDisabled;
    private final SystemClock mClock;
    private final Context mContext;
    private final BroadcastDispatcher mDispatcher;
    private boolean mListening;
    private boolean mMicMuted;
    private final List<AppOpItem> mNotedItems = new ArrayList();
    private final SparseArray<ArrayList<AudioRecordingConfiguration>> mRecordingsByUid = new SparseArray<>();
    private final IndividualSensorPrivacyController mSensorPrivacyController;

    private boolean isOpCamera(int i) {
        return i == 26 || i == 101;
    }

    private boolean isOpMicrophone(int i) {
        return i == 27 || i == 100;
    }

    public AppOpsControllerImpl(Context context, Looper looper, DumpManager dumpManager, AudioManager audioManager, IndividualSensorPrivacyController individualSensorPrivacyController, BroadcastDispatcher broadcastDispatcher, SystemClock systemClock) {
        this.mDispatcher = broadcastDispatcher;
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        this.mBGHandler = new H(looper);
        int length = OPS.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            this.mCallbacksByCode.put(OPS[i], new ArraySet());
        }
        this.mAudioManager = audioManager;
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mMicMuted = (audioManager.isMicrophoneMute() || individualSensorPrivacyController.isSensorBlocked(1)) ? true : z;
        this.mCameraDisabled = individualSensorPrivacyController.isSensorBlocked(2);
        this.mContext = context;
        this.mClock = systemClock;
        dumpManager.registerDumpable("AppOpsControllerImpl", this);
    }

    public void setBGHandler(H h) {
        this.mBGHandler = h;
    }

    public void setListening(boolean z) {
        this.mListening = z;
        if (z) {
            AppOpsManager appOpsManager = this.mAppOps;
            int[] iArr = OPS;
            appOpsManager.startWatchingActive(iArr, this);
            this.mAppOps.startWatchingNoted(iArr, this);
            this.mAudioManager.registerAudioRecordingCallback(this.mAudioRecordingCallback, this.mBGHandler);
            this.mSensorPrivacyController.addCallback(this);
            boolean z2 = true;
            if (!this.mAudioManager.isMicrophoneMute() && !this.mSensorPrivacyController.isSensorBlocked(1)) {
                z2 = false;
            }
            this.mMicMuted = z2;
            this.mCameraDisabled = this.mSensorPrivacyController.isSensorBlocked(2);
            this.mBGHandler.post(new AppOpsControllerImpl$$ExternalSyntheticLambda0(this));
            this.mDispatcher.registerReceiverWithHandler(this, new IntentFilter("android.media.action.MICROPHONE_MUTE_CHANGED"), this.mBGHandler);
            return;
        }
        this.mAppOps.stopWatchingActive(this);
        this.mAppOps.stopWatchingNoted(this);
        this.mAudioManager.unregisterAudioRecordingCallback(this.mAudioRecordingCallback);
        this.mSensorPrivacyController.removeCallback(this);
        this.mBGHandler.removeCallbacksAndMessages(null);
        this.mDispatcher.unregisterReceiver(this);
        synchronized (this.mActiveItems) {
            this.mActiveItems.clear();
            this.mRecordingsByUid.clear();
        }
        synchronized (this.mNotedItems) {
            this.mNotedItems.clear();
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$setListening$0() {
        this.mAudioRecordingCallback.onRecordingConfigChanged(this.mAudioManager.getActiveRecordingConfigurations());
    }

    @Override // com.android.systemui.appops.AppOpsController
    public void addCallback(int[] iArr, AppOpsController.Callback callback) {
        int length = iArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (this.mCallbacksByCode.contains(iArr[i])) {
                this.mCallbacksByCode.get(iArr[i]).add(callback);
                z = true;
            }
        }
        if (z) {
            this.mCallbacks.add(callback);
        }
        if (!this.mCallbacks.isEmpty()) {
            setListening(true);
        }
    }

    @Override // com.android.systemui.appops.AppOpsController
    public void removeCallback(int[] iArr, AppOpsController.Callback callback) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (this.mCallbacksByCode.contains(iArr[i])) {
                this.mCallbacksByCode.get(iArr[i]).remove(callback);
            }
        }
        this.mCallbacks.remove(callback);
        if (this.mCallbacks.isEmpty()) {
            setListening(false);
        }
    }

    private AppOpItem getAppOpItemLocked(List<AppOpItem> list, int i, int i2, String str) {
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            AppOpItem appOpItem = list.get(i3);
            if (appOpItem.getCode() == i && appOpItem.getUid() == i2 && appOpItem.getPackageName().equals(str)) {
                return appOpItem;
            }
        }
        return null;
    }

    private boolean updateActives(int i, int i2, String str, boolean z) {
        synchronized (this.mActiveItems) {
            AppOpItem appOpItemLocked = getAppOpItemLocked(this.mActiveItems, i, i2, str);
            boolean z2 = true;
            if (appOpItemLocked == null && z) {
                AppOpItem appOpItem = new AppOpItem(i, i2, str, this.mClock.elapsedRealtime());
                if (isOpMicrophone(i)) {
                    appOpItem.setDisabled(isAnyRecordingPausedLocked(i2));
                } else if (isOpCamera(i)) {
                    appOpItem.setDisabled(this.mCameraDisabled);
                }
                this.mActiveItems.add(appOpItem);
                if (appOpItem.isDisabled()) {
                    z2 = false;
                }
                return z2;
            } else if (appOpItemLocked == null || z) {
                return false;
            } else {
                this.mActiveItems.remove(appOpItemLocked);
                return true;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
        if (getAppOpItemLocked(r3.mActiveItems, r4, r5, r6) == null) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0021, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0022, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0023, code lost:
        if (r0 != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0025, code lost:
        lambda$notifySuscribers$1(r4, r5, r6, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        r1 = r3.mActiveItems;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void removeNoted(int r4, int r5, java.lang.String r6) {
        /*
            r3 = this;
            java.util.List<com.android.systemui.appops.AppOpItem> r0 = r3.mNotedItems
            monitor-enter(r0)
            java.util.List<com.android.systemui.appops.AppOpItem> r1 = r3.mNotedItems     // Catch:{ all -> 0x002c }
            com.android.systemui.appops.AppOpItem r1 = r3.getAppOpItemLocked(r1, r4, r5, r6)     // Catch:{ all -> 0x002c }
            if (r1 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x002c }
            return
        L_0x000d:
            java.util.List<com.android.systemui.appops.AppOpItem> r2 = r3.mNotedItems     // Catch:{ all -> 0x002c }
            r2.remove(r1)     // Catch:{ all -> 0x002c }
            monitor-exit(r0)     // Catch:{ all -> 0x002c }
            java.util.List<com.android.systemui.appops.AppOpItem> r1 = r3.mActiveItems
            monitor-enter(r1)
            java.util.List<com.android.systemui.appops.AppOpItem> r0 = r3.mActiveItems     // Catch:{ all -> 0x0029 }
            com.android.systemui.appops.AppOpItem r0 = r3.getAppOpItemLocked(r0, r4, r5, r6)     // Catch:{ all -> 0x0029 }
            r2 = 0
            if (r0 == 0) goto L_0x0021
            r0 = 1
            goto L_0x0022
        L_0x0021:
            r0 = r2
        L_0x0022:
            monitor-exit(r1)     // Catch:{ all -> 0x0029 }
            if (r0 != 0) goto L_0x0028
            r3.lambda$notifySuscribers$1(r4, r5, r6, r2)
        L_0x0028:
            return
        L_0x0029:
            r3 = move-exception
            monitor-exit(r1)
            throw r3
        L_0x002c:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.appops.AppOpsControllerImpl.removeNoted(int, int, java.lang.String):void");
    }

    private boolean addNoted(int i, int i2, String str) {
        AppOpItem appOpItemLocked;
        boolean z;
        synchronized (this.mNotedItems) {
            appOpItemLocked = getAppOpItemLocked(this.mNotedItems, i, i2, str);
            if (appOpItemLocked == null) {
                appOpItemLocked = new AppOpItem(i, i2, str, this.mClock.elapsedRealtime());
                this.mNotedItems.add(appOpItemLocked);
                z = true;
            } else {
                z = false;
            }
        }
        this.mBGHandler.removeCallbacksAndMessages(appOpItemLocked);
        this.mBGHandler.scheduleRemoval(appOpItemLocked, 5000);
        return z;
    }

    private boolean isUserVisible(String str) {
        return PermissionManager.shouldShowPackageForIndicatorCached(this.mContext, str);
    }

    @Override // com.android.systemui.appops.AppOpsController
    public List<AppOpItem> getActiveAppOps() {
        return getActiveAppOps(false);
    }

    @Override // com.android.systemui.appops.AppOpsController
    public List<AppOpItem> getActiveAppOps(boolean z) {
        return getActiveAppOpsForUser(-1, z);
    }

    public List<AppOpItem> getActiveAppOpsForUser(int i, boolean z) {
        int i2;
        Assert.isNotMainThread();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mActiveItems) {
            int size = this.mActiveItems.size();
            for (int i3 = 0; i3 < size; i3++) {
                AppOpItem appOpItem = this.mActiveItems.get(i3);
                if ((i == -1 || UserHandle.getUserId(appOpItem.getUid()) == i) && isUserVisible(appOpItem.getPackageName()) && (z || !appOpItem.isDisabled())) {
                    arrayList.add(appOpItem);
                }
            }
        }
        synchronized (this.mNotedItems) {
            int size2 = this.mNotedItems.size();
            for (i2 = 0; i2 < size2; i2++) {
                AppOpItem appOpItem2 = this.mNotedItems.get(i2);
                if ((i == -1 || UserHandle.getUserId(appOpItem2.getUid()) == i) && isUserVisible(appOpItem2.getPackageName())) {
                    arrayList.add(appOpItem2);
                }
            }
        }
        return arrayList;
    }

    private void notifySuscribers(int i, int i2, String str, boolean z) {
        this.mBGHandler.post(new AppOpsControllerImpl$$ExternalSyntheticLambda1(this, i, i2, str, z));
    }

    public void onOpActiveChanged(String str, int i, String str2, boolean z) {
        onOpActiveChanged(str, i, str2, null, z, 0, -1);
    }

    public void onOpActiveChanged(String str, int i, String str2, String str3, boolean z, int i2, int i3) {
        boolean z2;
        int strOpToOp = AppOpsManager.strOpToOp(str);
        if ((i3 == -1 || i2 == 0 || (i2 & 1) != 0 || (i2 & 8) != 0) && updateActives(strOpToOp, i, str2, z)) {
            synchronized (this.mNotedItems) {
                z2 = getAppOpItemLocked(this.mNotedItems, strOpToOp, i, str2) != null;
            }
            if (!z2) {
                notifySuscribers(strOpToOp, i, str2, z);
            }
        }
    }

    public void onOpNoted(int i, int i2, String str, String str2, int i3, int i4) {
        boolean z;
        if (i4 == 0 && addNoted(i, i2, str)) {
            synchronized (this.mActiveItems) {
                z = getAppOpItemLocked(this.mActiveItems, i, i2, str) != null;
            }
            if (!z) {
                notifySuscribers(i, i2, str, true);
            }
        }
    }

    /* access modifiers changed from: public */
    /* access modifiers changed from: private */
    /* renamed from: notifySuscribersWorker */
    public void lambda$notifySuscribers$1(int i, int i2, String str, boolean z) {
        if (this.mCallbacksByCode.contains(i) && isUserVisible(str)) {
            for (AppOpsController.Callback callback : this.mCallbacksByCode.get(i)) {
                callback.onActiveStateChanged(i, i2, str, z);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("AppOpsController state:");
        printWriter.println("  Listening: " + this.mListening);
        printWriter.println("  Active Items:");
        for (int i = 0; i < this.mActiveItems.size(); i++) {
            printWriter.print("    ");
            printWriter.println(this.mActiveItems.get(i).toString());
        }
        printWriter.println("  Noted Items:");
        for (int i2 = 0; i2 < this.mNotedItems.size(); i2++) {
            printWriter.print("    ");
            printWriter.println(this.mNotedItems.get(i2).toString());
        }
    }

    private boolean isAnyRecordingPausedLocked(int i) {
        if (this.mMicMuted) {
            return true;
        }
        ArrayList<AudioRecordingConfiguration> arrayList = this.mRecordingsByUid.get(i);
        if (arrayList == null) {
            return false;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (arrayList.get(i2).isClientSilenced()) {
                return true;
            }
        }
        return false;
    }

    private void updateSensorDisabledStatus() {
        boolean z;
        synchronized (this.mActiveItems) {
            int size = this.mActiveItems.size();
            for (int i = 0; i < size; i++) {
                AppOpItem appOpItem = this.mActiveItems.get(i);
                if (isOpMicrophone(appOpItem.getCode())) {
                    z = isAnyRecordingPausedLocked(appOpItem.getUid());
                } else {
                    z = isOpCamera(appOpItem.getCode()) ? this.mCameraDisabled : false;
                }
                if (appOpItem.isDisabled() != z) {
                    appOpItem.setDisabled(z);
                    notifySuscribers(appOpItem.getCode(), appOpItem.getUid(), appOpItem.getPackageName(), !appOpItem.isDisabled());
                }
            }
        }
    }

    public void onReceive(Context context, Intent intent) {
        boolean z = true;
        if (!this.mAudioManager.isMicrophoneMute() && !this.mSensorPrivacyController.isSensorBlocked(1)) {
            z = false;
        }
        this.mMicMuted = z;
        updateSensorDisabledStatus();
    }

    @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
    public void onSensorBlockedChanged(int i, boolean z) {
        this.mBGHandler.post(new AppOpsControllerImpl$$ExternalSyntheticLambda2(this, i, z));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onSensorBlockedChanged$2(int i, boolean z) {
        if (i == 2) {
            this.mCameraDisabled = z;
        } else {
            boolean z2 = true;
            if (i == 1) {
                if (!this.mAudioManager.isMicrophoneMute() && !z) {
                    z2 = false;
                }
                this.mMicMuted = z2;
            }
        }
        updateSensorDisabledStatus();
    }

    @Override // com.android.systemui.appops.AppOpsController
    public boolean isMicMuted() {
        return this.mMicMuted;
    }

    public class H extends Handler {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        H(Looper looper) {
            super(looper);
            AppOpsControllerImpl.this = r1;
        }

        public void scheduleRemoval(final AppOpItem appOpItem, long j) {
            removeCallbacksAndMessages(appOpItem);
            postDelayed(new Runnable() {
                /* class com.android.systemui.appops.AppOpsControllerImpl.H.AnonymousClass1 */

                public void run() {
                    AppOpsControllerImpl.this.removeNoted(appOpItem.getCode(), appOpItem.getUid(), appOpItem.getPackageName());
                }
            }, appOpItem, j);
        }
    }
}

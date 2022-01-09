package com.google.android.systemui.elmyra;

import android.app.StatsManager;
import android.content.Context;
import android.util.Log;
import com.android.internal.util.ConcurrentUtils;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.google.android.systemui.elmyra.proto.nano.ChassisProtos$Chassis;
import com.google.android.systemui.elmyra.proto.nano.SnapshotProtos$Snapshot;
import com.google.android.systemui.elmyra.sensors.GestureSensor;
import com.google.android.systemui.elmyra.sensors.config.GestureConfiguration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WestworldLogger implements GestureSensor.Listener {
    private ChassisProtos$Chassis mChassis = null;
    private CountDownLatch mCountDownLatch;
    private GestureConfiguration mGestureConfiguration;
    private Object mMutex;
    private SnapshotProtos$Snapshot mSnapshot;
    private SnapshotController mSnapshotController;
    private final StatsManager.StatsPullAtomCallback mWestworldCallback = new WestworldLogger$$ExternalSyntheticLambda0(this);

    /* access modifiers changed from: public */
    private /* synthetic */ int lambda$new$0(int i, List list) {
        Log.d("Elmyra/Logger", "Receiving pull request from statsd.");
        return pull(i, list);
    }

    public WestworldLogger(Context context, GestureConfiguration gestureConfiguration, SnapshotController snapshotController) {
        this.mGestureConfiguration = gestureConfiguration;
        this.mSnapshotController = snapshotController;
        this.mSnapshot = null;
        this.mMutex = new Object();
        registerWithWestworld(context);
    }

    public void registerWithWestworld(Context context) {
        StatsManager statsManager = (StatsManager) context.getSystemService("stats");
        if (statsManager == null) {
            Log.d("Elmyra/Logger", "Failed to get StatsManager");
        }
        try {
            statsManager.setPullAtomCallback(150000, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, this.mWestworldCallback);
        } catch (RuntimeException e) {
            Log.d("Elmyra/Logger", "Failed to register callback with StatsManager");
            e.printStackTrace();
        }
    }

    public void didReceiveChassis(ChassisProtos$Chassis chassisProtos$Chassis) {
        this.mChassis = chassisProtos$Chassis;
    }

    @Override // com.google.android.systemui.elmyra.sensors.GestureSensor.Listener
    public void onGestureProgress(GestureSensor gestureSensor, float f, int i) {
        SysUiStatsLog.write(176, (int) (f * 100.0f));
        SysUiStatsLog.write(174, i);
    }

    @Override // com.google.android.systemui.elmyra.sensors.GestureSensor.Listener
    public void onGestureDetected(GestureSensor gestureSensor, GestureSensor.DetectionProperties detectionProperties) {
        SysUiStatsLog.write(174, 3);
    }

    public void querySubmitted() {
        SysUiStatsLog.write(175, 2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        r8.mSnapshotController.onWestworldPull();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r2 = java.lang.System.currentTimeMillis();
        r8.mCountDownLatch.await(50, java.util.concurrent.TimeUnit.MILLISECONDS);
        android.util.Log.d("Elmyra/Logger", "Snapshot took " + java.lang.Long.toString(java.lang.System.currentTimeMillis() - r2) + " milliseconds.");
        r2 = r8.mMutex;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0057, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005a, code lost:
        if (r8.mSnapshot == null) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005e, code lost:
        if (r8.mChassis != null) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0061, code lost:
        r1 = new com.google.android.systemui.elmyra.proto.nano.ElmyraAtoms$ElmyraSnapshot();
        r3 = r8.mGestureConfiguration.getSensitivity();
        r4 = r8.mSnapshot;
        r4.sensitivitySetting = r3;
        r1.snapshot = r4;
        r1.chassis = r8.mChassis;
        r10.add(android.util.StatsEvent.newBuilder().setAtomId(r9).writeByteArray(com.google.protobuf.nano.MessageNano.toByteArray(r1.snapshot)).writeByteArray(com.google.protobuf.nano.MessageNano.toByteArray(r1.chassis)).build());
        r8.mSnapshot = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009b, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009d, code lost:
        r8.mCountDownLatch = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x009f, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a0, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a4, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a5, code lost:
        android.util.Log.d("Elmyra/Logger", r9.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00af, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b0, code lost:
        android.util.Log.d("Elmyra/Logger", r9.getMessage());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int pull(int r9, java.util.List<android.util.StatsEvent> r10) {
        /*
        // Method dump skipped, instructions count: 201
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.elmyra.WestworldLogger.pull(int, java.util.List):int");
    }

    public void didReceiveSnapshot(SnapshotProtos$Snapshot snapshotProtos$Snapshot) {
        synchronized (this.mMutex) {
            this.mSnapshot = snapshotProtos$Snapshot;
            CountDownLatch countDownLatch = this.mCountDownLatch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }
}

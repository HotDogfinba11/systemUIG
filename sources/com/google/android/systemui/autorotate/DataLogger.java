package com.google.android.systemui.autorotate;

import android.app.StatsManager;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.StatsEvent;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.google.android.systemui.autorotate.proto.nano.AutorotateProto$DeviceRotatedSensorData;
import com.google.android.systemui.autorotate.proto.nano.AutorotateProto$DeviceRotatedSensorHeader;
import com.google.android.systemui.autorotate.proto.nano.AutorotateProto$DeviceRotatedSensorSample;
import com.google.protobuf.nano.MessageNano;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* access modifiers changed from: package-private */
public final class DataLogger {
    private Queue<Pair<SensorData[], Integer>> mDataQueue = new LinkedList();
    private long mLastPullTimeNanos = 0;
    private StatsManager mStatsManager;

    private static int convertSensorId(int i) {
        if (i == 4) {
            return 2;
        }
        return i;
    }

    private static int rotationToLogEnum(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 2;
        }
        if (i != 2) {
            return i != 3 ? 0 : 4;
        }
        return 3;
    }

    DataLogger(StatsManager statsManager) {
        this.mStatsManager = statsManager;
    }

    /* access modifiers changed from: package-private */
    public void pushDeviceRotatedEvent(long j, int i, int i2) {
        FrameworkStatsLog.write(333, j, rotationToLogEnum(i), i2);
    }

    /* access modifiers changed from: package-private */
    public void setDeviceRotatedData(SensorData[] sensorDataArr, int i) {
        if (sensorDataArr != null && sensorDataArr.length != 0 && sensorDataArr[0] != null) {
            if (SystemClock.elapsedRealtimeNanos() - this.mLastPullTimeNanos > 5000000000L) {
                clearData();
            }
            this.mDataQueue.add(Pair.create(sensorDataArr, Integer.valueOf(i)));
        }
    }

    /* access modifiers changed from: package-private */
    public void clearData() {
        this.mDataQueue.clear();
    }

    /* access modifiers changed from: package-private */
    public class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        StatsPullAtomCallbackImpl() {
        }

        public int onPullAtom(int i, List<StatsEvent> list) {
            if (i == 10097) {
                return DataLogger.this.pullSensorData(list);
            }
            throw new UnsupportedOperationException("Unknown tagId: " + i);
        }
    }

    /* access modifiers changed from: package-private */
    public void registerPullAtomCallback() {
        if (this.mStatsManager != null) {
            this.mStatsManager.setPullAtomCallback(10097, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, new StatsPullAtomCallbackImpl());
            Log.d("Autorotate-DataLogger", "Registered to statsd for pull");
        }
    }

    /* access modifiers changed from: package-private */
    public void unregisterPullAtomCallback() {
        StatsManager statsManager = this.mStatsManager;
        if (statsManager != null) {
            statsManager.clearPullAtomCallback(10097);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int pullSensorData(List<StatsEvent> list) {
        Log.d("Autorotate-DataLogger", "Received pull request from statsd.");
        this.mLastPullTimeNanos = SystemClock.elapsedRealtimeNanos();
        Pair<SensorData[], Integer> poll = this.mDataQueue.poll();
        SensorData[] sensorDataArr = (SensorData[]) poll.first;
        int intValue = ((Integer) poll.second).intValue();
        if (!(sensorDataArr == null || sensorDataArr.length == 0 || sensorDataArr[0] == null)) {
            AutorotateProto$DeviceRotatedSensorData autorotateProto$DeviceRotatedSensorData = new AutorotateProto$DeviceRotatedSensorData();
            AutorotateProto$DeviceRotatedSensorHeader autorotateProto$DeviceRotatedSensorHeader = new AutorotateProto$DeviceRotatedSensorHeader();
            autorotateProto$DeviceRotatedSensorHeader.timestampBase = sensorDataArr[0].mTimestampMillis;
            autorotateProto$DeviceRotatedSensorData.header = autorotateProto$DeviceRotatedSensorHeader;
            AutorotateProto$DeviceRotatedSensorSample[] autorotateProto$DeviceRotatedSensorSampleArr = new AutorotateProto$DeviceRotatedSensorSample[sensorDataArr.length];
            for (int i = 0; i < sensorDataArr.length; i++) {
                AutorotateProto$DeviceRotatedSensorSample autorotateProto$DeviceRotatedSensorSample = new AutorotateProto$DeviceRotatedSensorSample();
                autorotateProto$DeviceRotatedSensorSample.timestampOffset = (int) (sensorDataArr[i].mTimestampMillis - autorotateProto$DeviceRotatedSensorHeader.timestampBase);
                autorotateProto$DeviceRotatedSensorSample.sensorType = convertSensorId(sensorDataArr[i].mSensorIdentifier);
                autorotateProto$DeviceRotatedSensorSample.xValue = sensorDataArr[i].mValueX;
                autorotateProto$DeviceRotatedSensorSample.yValue = sensorDataArr[i].mValueY;
                autorotateProto$DeviceRotatedSensorSample.zValue = sensorDataArr[i].mValueZ;
                autorotateProto$DeviceRotatedSensorSampleArr[i] = autorotateProto$DeviceRotatedSensorSample;
            }
            autorotateProto$DeviceRotatedSensorData.sample = autorotateProto$DeviceRotatedSensorSampleArr;
            list.add(FrameworkStatsLog.buildStatsEvent(10097, MessageNano.toByteArray(autorotateProto$DeviceRotatedSensorData), rotationToLogEnum(intValue)));
        }
        return 0;
    }
}

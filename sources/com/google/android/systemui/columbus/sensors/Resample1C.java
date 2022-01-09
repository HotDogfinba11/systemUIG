package com.google.android.systemui.columbus.sensors;

public class Resample1C {
    protected long mInterval = 0;
    protected long mRawLastT;
    protected float mRawLastX;
    protected long mResampledLastT;
    protected float mResampledThisX = 0.0f;

    public void init(float f, long j, long j2) {
        this.mRawLastX = f;
        this.mRawLastT = j;
        this.mResampledThisX = f;
        this.mResampledLastT = j;
        this.mInterval = j2;
    }

    public long getInterval() {
        return this.mInterval;
    }

    public void setSyncTime(long j) {
        this.mResampledLastT = j;
    }
}

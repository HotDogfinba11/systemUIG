package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class UdfpsEnrollProgressBarDrawable extends Drawable {
    private final Context mContext;
    private UdfpsEnrollHelper mEnrollHelper;
    private boolean mIsShowingHelp = false;
    private int mProgressSteps = 0;
    private List<UdfpsEnrollProgressBarSegment> mSegments = new ArrayList();
    private int mTotalSteps = 1;

    public int getOpacity() {
        return 0;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public UdfpsEnrollProgressBarDrawable(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: package-private */
    public void setEnrollHelper(UdfpsEnrollHelper udfpsEnrollHelper) {
        this.mEnrollHelper = udfpsEnrollHelper;
        if (udfpsEnrollHelper != null) {
            int stageCount = udfpsEnrollHelper.getStageCount();
            this.mSegments = new ArrayList(stageCount);
            float f = 6.0f;
            float f2 = (360.0f / ((float) stageCount)) - 12.0f;
            UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0 udfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0 = new UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0(this);
            for (int i = 0; i < stageCount; i++) {
                this.mSegments.add(new UdfpsEnrollProgressBarSegment(this.mContext, getBounds(), f, f2, 12.0f, udfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0));
                f += f2 + 12.0f;
            }
            invalidateSelf();
        }
    }

    /* access modifiers changed from: package-private */
    public void onEnrollmentProgress(int i, int i2) {
        this.mTotalSteps = i2;
        updateState(getProgressSteps(i, i2), false);
    }

    /* access modifiers changed from: package-private */
    public void onEnrollmentHelp(int i, int i2) {
        updateState(getProgressSteps(i, i2), true);
    }

    /* access modifiers changed from: package-private */
    public void onLastStepAcquired() {
        updateState(this.mTotalSteps, false);
    }

    private static int getProgressSteps(int i, int i2) {
        return Math.max(1, i2 - i);
    }

    private void updateState(int i, boolean z) {
        updateProgress(i);
        updateFillColor(z);
    }

    private void updateProgress(int i) {
        if (this.mProgressSteps != i) {
            this.mProgressSteps = i;
            if (this.mEnrollHelper == null) {
                Log.e("UdfpsProgressBar", "updateState: UDFPS enroll helper was null");
                return;
            }
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i2 >= this.mSegments.size()) {
                    break;
                }
                UdfpsEnrollProgressBarSegment udfpsEnrollProgressBarSegment = this.mSegments.get(i2);
                int stageThresholdSteps = this.mEnrollHelper.getStageThresholdSteps(this.mTotalSteps, i2);
                if (i >= stageThresholdSteps && udfpsEnrollProgressBarSegment.getProgress() < 1.0f) {
                    udfpsEnrollProgressBarSegment.updateProgress(1.0f);
                    break;
                } else if (i >= i3 && i < stageThresholdSteps) {
                    udfpsEnrollProgressBarSegment.updateProgress(((float) (i - i3)) / ((float) (stageThresholdSteps - i3)));
                    break;
                } else {
                    i2++;
                    i3 = stageThresholdSteps;
                }
            }
            if (i >= this.mTotalSteps) {
                for (UdfpsEnrollProgressBarSegment udfpsEnrollProgressBarSegment2 : this.mSegments) {
                    udfpsEnrollProgressBarSegment2.startCompletionAnimation();
                }
                return;
            }
            for (UdfpsEnrollProgressBarSegment udfpsEnrollProgressBarSegment3 : this.mSegments) {
                udfpsEnrollProgressBarSegment3.cancelCompletionAnimation();
            }
        }
    }

    private void updateFillColor(boolean z) {
        if (this.mIsShowingHelp != z) {
            this.mIsShowingHelp = z;
            for (UdfpsEnrollProgressBarSegment udfpsEnrollProgressBarSegment : this.mSegments) {
                udfpsEnrollProgressBarSegment.updateFillColor(z);
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(-90.0f, (float) getBounds().centerX(), (float) getBounds().centerY());
        for (UdfpsEnrollProgressBarSegment udfpsEnrollProgressBarSegment : this.mSegments) {
            udfpsEnrollProgressBarSegment.draw(canvas);
        }
        canvas.restore();
    }
}

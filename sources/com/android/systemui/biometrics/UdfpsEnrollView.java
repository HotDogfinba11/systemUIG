package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R$id;
import java.util.Objects;

public class UdfpsEnrollView extends UdfpsAnimationView {
    private final UdfpsEnrollDrawable mFingerprintDrawable = new UdfpsEnrollDrawable(((FrameLayout) this).mContext);
    private final UdfpsEnrollProgressBarDrawable mFingerprintProgressDrawable;
    private ImageView mFingerprintProgressView;
    private ImageView mFingerprintView;
    private final Handler mHandler;

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public /* bridge */ /* synthetic */ void onExpansionChanged(float f, boolean z) {
        super.onExpansionChanged(f, z);
    }

    public UdfpsEnrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFingerprintProgressDrawable = new UdfpsEnrollProgressBarDrawable(context);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        this.mFingerprintView = (ImageView) findViewById(R$id.udfps_enroll_animation_fp_view);
        this.mFingerprintProgressView = (ImageView) findViewById(R$id.udfps_enroll_animation_fp_progress_view);
        this.mFingerprintView.setImageDrawable(this.mFingerprintDrawable);
        this.mFingerprintProgressView.setImageDrawable(this.mFingerprintProgressDrawable);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public UdfpsDrawable getDrawable() {
        return this.mFingerprintDrawable;
    }

    /* access modifiers changed from: package-private */
    public void updateSensorLocation(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        View findViewById = findViewById(R$id.udfps_enroll_accessibility_view);
        int i = fingerprintSensorPropertiesInternal.sensorRadius * 2;
        ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        findViewById.setLayoutParams(layoutParams);
        findViewById.requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void setEnrollHelper(UdfpsEnrollHelper udfpsEnrollHelper) {
        this.mFingerprintProgressDrawable.setEnrollHelper(udfpsEnrollHelper);
        this.mFingerprintDrawable.setEnrollHelper(udfpsEnrollHelper);
    }

    /* access modifiers changed from: package-private */
    public void onEnrollmentProgress(int i, int i2) {
        this.mHandler.post(new UdfpsEnrollView$$ExternalSyntheticLambda1(this, i, i2));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentProgress$0(int i, int i2) {
        this.mFingerprintProgressDrawable.onEnrollmentProgress(i, i2);
        this.mFingerprintDrawable.onEnrollmentProgress(i, i2);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentHelp$1(int i, int i2) {
        this.mFingerprintProgressDrawable.onEnrollmentHelp(i, i2);
    }

    /* access modifiers changed from: package-private */
    public void onEnrollmentHelp(int i, int i2) {
        this.mHandler.post(new UdfpsEnrollView$$ExternalSyntheticLambda2(this, i, i2));
    }

    /* access modifiers changed from: package-private */
    public void onLastStepAcquired() {
        Handler handler = this.mHandler;
        UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = this.mFingerprintProgressDrawable;
        Objects.requireNonNull(udfpsEnrollProgressBarDrawable);
        handler.post(new UdfpsEnrollView$$ExternalSyntheticLambda0(udfpsEnrollProgressBarDrawable));
    }
}

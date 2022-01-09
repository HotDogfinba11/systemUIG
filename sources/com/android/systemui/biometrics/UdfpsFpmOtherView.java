package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.R$id;

public class UdfpsFpmOtherView extends UdfpsAnimationView {
    private final UdfpsFpDrawable mFingerprintDrawable;
    private ImageView mFingerprintView;

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public /* bridge */ /* synthetic */ void onExpansionChanged(float f, boolean z) {
        super.onExpansionChanged(f, z);
    }

    public UdfpsFpmOtherView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFingerprintDrawable = new UdfpsFpDrawable(context);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        ImageView imageView = (ImageView) findViewById(R$id.udfps_fpm_other_fp_view);
        this.mFingerprintView = imageView;
        imageView.setImageDrawable(this.mFingerprintDrawable);
    }

    /* access modifiers changed from: package-private */
    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public UdfpsDrawable getDrawable() {
        return this.mFingerprintDrawable;
    }
}

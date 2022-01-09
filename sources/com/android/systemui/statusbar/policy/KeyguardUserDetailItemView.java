package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.qs.tiles.UserDetailItemView;

public class KeyguardUserDetailItemView extends UserDetailItemView {
    private float mDarkAmount;
    private int mTextColor;

    public KeyguardUserDetailItemView(Context context) {
        this(context, null);
    }

    public KeyguardUserDetailItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardUserDetailItemView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public KeyguardUserDetailItemView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.android.systemui.qs.tiles.UserDetailItemView
    public int getFontSizeDimen() {
        return R$dimen.kg_user_switcher_text_size;
    }

    @Override // com.android.systemui.qs.tiles.UserDetailItemView
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTextColor = this.mName.getCurrentTextColor();
        updateDark();
    }

    public void updateVisibilities(boolean z, boolean z2, boolean z3) {
        int i = 0;
        getBackground().setAlpha((!z || !z2) ? 0 : 255);
        if (z) {
            if (z2) {
                this.mName.setVisibility(0);
                if (z3) {
                    this.mName.setAlpha(0.0f);
                    this.mName.animate().alpha(1.0f).setDuration(240).setInterpolator(Interpolators.ALPHA_IN);
                } else {
                    this.mName.setAlpha(1.0f);
                }
            } else if (z3) {
                this.mName.setVisibility(0);
                this.mName.setAlpha(1.0f);
                this.mName.animate().alpha(0.0f).setDuration(240).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new KeyguardUserDetailItemView$$ExternalSyntheticLambda0(this));
            } else {
                this.mName.setVisibility(8);
                this.mName.setAlpha(1.0f);
            }
            setVisibility(0);
            setAlpha(1.0f);
            return;
        }
        setVisibility(8);
        setAlpha(1.0f);
        TextView textView = this.mName;
        if (!z2) {
            i = 8;
        }
        textView.setVisibility(i);
        this.mName.setAlpha(1.0f);
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$updateVisibilities$0() {
        this.mName.setVisibility(8);
        this.mName.setAlpha(1.0f);
    }

    public void setDarkAmount(float f) {
        if (this.mDarkAmount != f) {
            this.mDarkAmount = f;
            updateDark();
        }
    }

    private void updateDark() {
        this.mName.setTextColor(ColorUtils.blendARGB(this.mTextColor, -1, this.mDarkAmount));
    }
}
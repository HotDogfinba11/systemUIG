package com.google.android.systemui.gamedashboard;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.android.settingslib.Utils;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;

public class GameDashboardButton extends ImageView {
    private ValueAnimator mAnimator;
    private final BlendModeColorFilter mBackgroundColorFilter;
    private final BlendModeColorFilter mBackgroundColorFilterToggled;
    private final GradientDrawable mBackgroundDrawable;
    private final PorterDuffColorFilter mDrawableColorFilter;
    private final PorterDuffColorFilter mDrawableColorFilterToggled;
    private boolean mToggled;

    public GameDashboardButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(context, 17956900);
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(context, 17956912);
        int colorAttrDefaultColor3 = Utils.getColorAttrDefaultColor(context, 16842806);
        int colorAttrDefaultColor4 = Utils.getColorAttrDefaultColor(context, 17957102);
        GradientDrawable gradientDrawable = (GradientDrawable) context.getDrawable(R$drawable.rounded_rectangle_8dp).mutate();
        this.mBackgroundDrawable = gradientDrawable;
        BlendModeColorFilter blendModeColorFilter = new BlendModeColorFilter(colorAttrDefaultColor2, BlendMode.SRC_ATOP);
        this.mBackgroundColorFilter = blendModeColorFilter;
        gradientDrawable.setColorFilter(blendModeColorFilter);
        this.mBackgroundColorFilterToggled = new BlendModeColorFilter(colorAttrDefaultColor, BlendMode.SRC_ATOP);
        this.mDrawableColorFilter = new PorterDuffColorFilter(colorAttrDefaultColor3, PorterDuff.Mode.SRC_ATOP);
        this.mDrawableColorFilterToggled = new PorterDuffColorFilter(colorAttrDefaultColor4, PorterDuff.Mode.SRC_ATOP);
        setBackground(gradientDrawable);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        refresh(false);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(new GameDashboardButton$$ExternalSyntheticLambda1(this, onClickListener));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$setOnClickListener$0(View.OnClickListener onClickListener, View view) {
        onClickListener.onClick(view);
        toggle();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        refreshDrawable();
    }

    public void setToggled(boolean z, boolean z2) {
        this.mToggled = z;
        refresh(z2);
        String string = ((ImageView) this).mContext.getString(R$string.accessibility_game_dashboard_shortcut_button_enabled);
        String string2 = ((ImageView) this).mContext.getString(R$string.accessibility_game_dashboard_shortcut_button_disabled);
        if (!this.mToggled) {
            string = string2;
        }
        setStateDescription(string);
    }

    public boolean isToggled() {
        return this.mToggled;
    }

    private void toggle() {
        setToggled(!this.mToggled, true);
    }

    private void refresh(boolean z) {
        ValueAnimator valueAnimator;
        this.mBackgroundDrawable.setColorFilter(this.mToggled ? this.mBackgroundColorFilterToggled : this.mBackgroundColorFilter);
        float width = (this.mToggled ? 0.5f : 0.36f) * ((float) getWidth());
        if (z && (valueAnimator = this.mAnimator) != null && valueAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (z) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mBackgroundDrawable.getCornerRadius(), width);
            this.mAnimator = ofFloat;
            ofFloat.addUpdateListener(new GameDashboardButton$$ExternalSyntheticLambda0(this));
            this.mAnimator.setDuration(200L);
            this.mAnimator.setEvaluator(new FloatEvaluator());
            this.mAnimator.start();
        } else {
            this.mBackgroundDrawable.setCornerRadius(width);
        }
        refreshDrawable();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$refresh$1(ValueAnimator valueAnimator) {
        this.mBackgroundDrawable.setCornerRadius(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    private void refreshDrawable() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(this.mToggled ? this.mDrawableColorFilterToggled : this.mDrawableColorFilter);
        }
    }
}

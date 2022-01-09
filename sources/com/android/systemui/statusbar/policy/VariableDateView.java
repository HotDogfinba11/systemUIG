package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R$string;
import com.android.systemui.R$styleable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VariableDateView.kt */
public final class VariableDateView extends TextView {
    private boolean freezeSwitching;
    private final String longerPattern;
    private OnMeasureListener onMeasureListener;
    private final String shorterPattern;

    /* compiled from: VariableDateView.kt */
    public interface OnMeasureListener {
        void onMeasureAction(int i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VariableDateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attrs");
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.VariableDateView, 0, 0);
        String string = obtainStyledAttributes.getString(R$styleable.VariableDateView_longDatePattern);
        if (string == null) {
            string = context.getString(R$string.system_ui_date_pattern);
            Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.system_ui_date_pattern)");
        }
        this.longerPattern = string;
        String string2 = obtainStyledAttributes.getString(R$styleable.VariableDateView_shortDatePattern);
        if (string2 == null) {
            string2 = context.getString(R$string.abbrev_month_day_no_year);
            Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.string.abbrev_month_day_no_year)");
        }
        this.shorterPattern = string2;
        obtainStyledAttributes.recycle();
    }

    public final String getLongerPattern() {
        return this.longerPattern;
    }

    public final String getShorterPattern() {
        return this.shorterPattern;
    }

    public final boolean getFreezeSwitching() {
        return this.freezeSwitching;
    }

    public final void setFreezeSwitching(boolean z) {
        this.freezeSwitching = z;
    }

    public final void onAttach(OnMeasureListener onMeasureListener2) {
        this.onMeasureListener = onMeasureListener2;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        OnMeasureListener onMeasureListener2;
        int size = (View.MeasureSpec.getSize(i) - getPaddingStart()) - getPaddingEnd();
        if (!(View.MeasureSpec.getMode(i) == 0 || this.freezeSwitching || (onMeasureListener2 = this.onMeasureListener) == null)) {
            onMeasureListener2.onMeasureAction(size);
        }
        super.onMeasure(i, i2);
    }

    public final float getDesiredWidthForText(CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "text");
        return StaticLayout.getDesiredWidth(charSequence, getPaint());
    }
}

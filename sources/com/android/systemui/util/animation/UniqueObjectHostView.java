package com.android.systemui.util.animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UniqueObjectHostView.kt */
public final class UniqueObjectHostView extends FrameLayout {
    public MeasurementManager measurementManager;

    /* compiled from: UniqueObjectHostView.kt */
    public interface MeasurementManager {
        MeasurementOutput onMeasure(MeasurementInput measurementInput);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UniqueObjectHostView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final MeasurementManager getMeasurementManager() {
        MeasurementManager measurementManager2 = this.measurementManager;
        if (measurementManager2 != null) {
            return measurementManager2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("measurementManager");
        throw null;
    }

    public final void setMeasurementManager(MeasurementManager measurementManager2) {
        Intrinsics.checkNotNullParameter(measurementManager2, "<set-?>");
        this.measurementManager = measurementManager2;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"DrawAllocation"})
    public void onMeasure(int i, int i2) {
        int paddingStart = getPaddingStart() + getPaddingEnd();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        MeasurementOutput onMeasure = getMeasurementManager().onMeasure(new MeasurementInput(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i) - paddingStart, View.MeasureSpec.getMode(i)), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2) - paddingTop, View.MeasureSpec.getMode(i2))));
        int component1 = onMeasure.component1();
        int component2 = onMeasure.component2();
        if (isCurrentHost()) {
            super.onMeasure(i, i2);
            View childAt = getChildAt(0);
            if (childAt != null) {
                UniqueObjectHostViewKt.setRequiresRemeasuring(childAt, false);
            }
        }
        setMeasuredDimension(component1 + paddingStart, component2 + paddingTop);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            throw new IllegalArgumentException("child must be non-null");
        } else if (view.getMeasuredWidth() == 0 || getMeasuredWidth() == 0 || UniqueObjectHostViewKt.getRequiresRemeasuring(view)) {
            super.addView(view, i, layoutParams);
        } else {
            invalidate();
            addViewInLayout(view, i, layoutParams, true);
            view.resolveRtlPropertiesIfNeeded();
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            view.layout(paddingLeft, paddingTop, (getMeasuredWidth() + paddingLeft) - (getPaddingStart() + getPaddingEnd()), (getMeasuredHeight() + paddingTop) - (getPaddingTop() + getPaddingBottom()));
        }
    }

    private final boolean isCurrentHost() {
        return getChildCount() != 0;
    }
}

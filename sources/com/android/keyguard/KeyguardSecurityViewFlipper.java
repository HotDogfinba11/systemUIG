package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;
import com.android.systemui.R$styleable;

public class KeyguardSecurityViewFlipper extends ViewFlipper {
    private Rect mTempRect;

    public KeyguardSecurityViewFlipper(Context context) {
        this(context, null);
    }

    public KeyguardSecurityViewFlipper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempRect = new Rect();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        this.mTempRect.set(0, 0, 0, 0);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                offsetRectIntoDescendantCoords(childAt, this.mTempRect);
                Rect rect = this.mTempRect;
                motionEvent.offsetLocation((float) rect.left, (float) rect.top);
                onTouchEvent = childAt.dispatchTouchEvent(motionEvent) || onTouchEvent;
                Rect rect2 = this.mTempRect;
                motionEvent.offsetLocation((float) (-rect2.left), (float) (-rect2.top));
            }
        }
        return onTouchEvent;
    }

    /* access modifiers changed from: package-private */
    public KeyguardInputView getSecurityView() {
        View childAt = getChildAt(getDisplayedChild());
        if (childAt instanceof KeyguardInputView) {
            return (KeyguardInputView) childAt;
        }
        return null;
    }

    public CharSequence getTitle() {
        KeyguardInputView securityView = getSecurityView();
        return securityView != null ? securityView.getTitle() : "";
    }

    public void animateForIme(int i, float f, boolean z) {
        super.setTranslationY((float) i);
        KeyguardInputView securityView = getSecurityView();
        if (securityView != null) {
            securityView.animateForIme(f, z);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    @Override // android.widget.FrameLayout, android.widget.FrameLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int childCount = getChildCount();
        int i3 = size;
        int i4 = size2;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() == 0) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i6 = layoutParams.maxWidth;
                if (i6 > 0 && i6 < i3) {
                    i3 = i6;
                }
                int i7 = layoutParams.maxHeight;
                if (i7 > 0 && i7 < i4) {
                    i4 = i7;
                }
            }
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int max = Math.max(0, i3 - paddingLeft);
        int max2 = Math.max(0, i4 - paddingTop);
        int i8 = mode == 1073741824 ? size : 0;
        int i9 = mode2 == 1073741824 ? size2 : 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt2 = getChildAt(i10);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            childAt2.measure(makeChildMeasureSpec(max, ((FrameLayout.LayoutParams) layoutParams2).width), makeChildMeasureSpec(max2, ((FrameLayout.LayoutParams) layoutParams2).height));
            i8 = Math.max(i8, Math.min(childAt2.getMeasuredWidth(), size - paddingLeft));
            i9 = Math.max(i9, Math.min(childAt2.getMeasuredHeight(), size2 - paddingTop));
        }
        setMeasuredDimension(i8 + paddingLeft, i9 + paddingTop);
    }

    private int makeChildMeasureSpec(int i, int i2) {
        int i3 = 1073741824;
        if (i2 == -2) {
            i3 = Integer.MIN_VALUE;
        } else if (i2 != -1) {
            i = Math.min(i, i2);
        }
        return View.MeasureSpec.makeMeasureSpec(i, i3);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        @ViewDebug.ExportedProperty(category = "layout")
        public int maxHeight;
        @ViewDebug.ExportedProperty(category = "layout")
        public int maxWidth;

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((FrameLayout.LayoutParams) layoutParams);
            this.maxWidth = layoutParams.maxWidth;
            this.maxHeight = layoutParams.maxHeight;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyguardSecurityViewFlipper_Layout, 0, 0);
            this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(R$styleable.KeyguardSecurityViewFlipper_Layout_layout_maxWidth, 0);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(R$styleable.KeyguardSecurityViewFlipper_Layout_layout_maxHeight, 0);
            obtainStyledAttributes.recycle();
        }

        /* access modifiers changed from: protected */
        public void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:maxWidth", this.maxWidth);
            viewHierarchyEncoder.addProperty("layout:maxHeight", this.maxHeight);
        }
    }
}

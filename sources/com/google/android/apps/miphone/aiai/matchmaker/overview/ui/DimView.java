package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.utils.LogUtils;

public final class DimView extends FrameLayout {
    private float backgroundCornerRadius;
    private final Paint backgroundPaint;
    private final Rect tmpRect;
    private final RectF tmpRectF;

    public DimView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DimView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DimView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.tmpRectF = new RectF();
        this.tmpRect = new Rect();
        Paint paint = new Paint();
        this.backgroundPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        if (Build.VERSION.SDK_INT >= 29) {
            paint.setColor(0);
        }
        setWillNotDraw(false);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getHeight() != 0) {
            getDrawingRect(this.tmpRect);
            this.tmpRectF.set(this.tmpRect);
            RectF rectF = this.tmpRectF;
            float f = this.backgroundCornerRadius;
            canvas.drawRoundRect(rectF, f, f, this.backgroundPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnTouchListener(new DimView$$ExternalSyntheticLambda0(this));
    }

    public /* synthetic */ boolean lambda$onAttachedToWindow$0$DimView(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return false;
        }
        LogUtils.i("Handle touch for the background scrim.");
        return true;
    }
}

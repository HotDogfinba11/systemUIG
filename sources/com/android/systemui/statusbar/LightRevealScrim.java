package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LightRevealScrim.kt */
public final class LightRevealScrim extends View {
    private final Paint gradientPaint;
    private boolean isScrimOpaque;
    public Consumer<Boolean> isScrimOpaqueChangedListener;
    private float revealAmount = 1.0f;
    private LightRevealEffect revealEffect = LiftReveal.INSTANCE;
    private PointF revealGradientCenter = new PointF();
    private int revealGradientEndColor = -16777216;
    private float revealGradientEndColorAlpha;
    private float revealGradientHeight;
    private float revealGradientWidth;
    private final Matrix shaderGradientMatrix;

    public LightRevealScrim(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        paint.setShader(new RadialGradient(0.0f, 0.0f, 1.0f, new int[]{0, -1}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        Unit unit = Unit.INSTANCE;
        this.gradientPaint = paint;
        this.shaderGradientMatrix = new Matrix();
        this.revealEffect.setRevealAmountOnScrim(this.revealAmount, this);
        setPaintColorFilter();
        invalidate();
    }

    public final Consumer<Boolean> isScrimOpaqueChangedListener() {
        Consumer<Boolean> consumer = this.isScrimOpaqueChangedListener;
        if (consumer != null) {
            return consumer;
        }
        Intrinsics.throwUninitializedPropertyAccessException("isScrimOpaqueChangedListener");
        throw null;
    }

    public final void setScrimOpaqueChangedListener(Consumer<Boolean> consumer) {
        Intrinsics.checkNotNullParameter(consumer, "<set-?>");
        this.isScrimOpaqueChangedListener = consumer;
    }

    public final float getRevealAmount() {
        return this.revealAmount;
    }

    public final void setRevealAmount(float f) {
        if (!(this.revealAmount == f)) {
            this.revealAmount = f;
            this.revealEffect.setRevealAmountOnScrim(f, this);
            updateScrimOpaque();
            invalidate();
        }
    }

    public final LightRevealEffect getRevealEffect() {
        return this.revealEffect;
    }

    public final void setRevealEffect(LightRevealEffect lightRevealEffect) {
        Intrinsics.checkNotNullParameter(lightRevealEffect, "value");
        if (!Intrinsics.areEqual(this.revealEffect, lightRevealEffect)) {
            this.revealEffect = lightRevealEffect;
            lightRevealEffect.setRevealAmountOnScrim(this.revealAmount, this);
            invalidate();
        }
    }

    public final PointF getRevealGradientCenter() {
        return this.revealGradientCenter;
    }

    public final float getRevealGradientWidth() {
        return this.revealGradientWidth;
    }

    public final float getRevealGradientHeight() {
        return this.revealGradientHeight;
    }

    public final void setRevealGradientEndColorAlpha(float f) {
        if (!(this.revealGradientEndColorAlpha == f)) {
            this.revealGradientEndColorAlpha = f;
            setPaintColorFilter();
        }
    }

    public final boolean isScrimOpaque() {
        return this.isScrimOpaque;
    }

    private final void setScrimOpaque(boolean z) {
        if (this.isScrimOpaque != z) {
            this.isScrimOpaque = z;
            isScrimOpaqueChangedListener().accept(Boolean.valueOf(this.isScrimOpaque));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0021, code lost:
        if (getVisibility() == 0) goto L_0x0025;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void updateScrimOpaque() {
        /*
            r4 = this;
            float r0 = r4.revealAmount
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x000b
            r0 = r1
            goto L_0x000c
        L_0x000b:
            r0 = r2
        L_0x000c:
            if (r0 == 0) goto L_0x0024
            float r0 = r4.getAlpha()
            r3 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x001a
            r0 = r1
            goto L_0x001b
        L_0x001a:
            r0 = r2
        L_0x001b:
            if (r0 == 0) goto L_0x0024
            int r0 = r4.getVisibility()
            if (r0 != 0) goto L_0x0024
            goto L_0x0025
        L_0x0024:
            r1 = r2
        L_0x0025:
            r4.setScrimOpaque(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.LightRevealScrim.updateScrimOpaque():void");
    }

    public void setAlpha(float f) {
        super.setAlpha(f);
        updateScrimOpaque();
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        updateScrimOpaque();
    }

    public final void setRevealGradientBounds(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        this.revealGradientWidth = f5;
        float f6 = f4 - f2;
        this.revealGradientHeight = f6;
        PointF pointF = this.revealGradientCenter;
        pointF.x = f + (f5 / 2.0f);
        pointF.y = f2 + (f6 / 2.0f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (canvas != null && this.revealGradientWidth > 0.0f && this.revealGradientHeight > 0.0f) {
            Matrix matrix = this.shaderGradientMatrix;
            matrix.setScale(getRevealGradientWidth(), getRevealGradientHeight(), 0.0f, 0.0f);
            matrix.postTranslate(getRevealGradientCenter().x, getRevealGradientCenter().y);
            this.gradientPaint.getShader().setLocalMatrix(matrix);
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.gradientPaint);
        } else if (this.revealAmount < 1.0f && canvas != null) {
            canvas.drawColor(this.revealGradientEndColor);
        }
    }

    private final void setPaintColorFilter() {
        this.gradientPaint.setColorFilter(new PorterDuffColorFilter(Color.argb((int) (this.revealGradientEndColorAlpha * ((float) 255)), Color.red(this.revealGradientEndColor), Color.green(this.revealGradientEndColor), Color.blue(this.revealGradientEndColor)), PorterDuff.Mode.MULTIPLY));
    }
}

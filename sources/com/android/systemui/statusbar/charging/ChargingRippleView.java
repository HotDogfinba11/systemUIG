package com.android.systemui.statusbar.charging;

import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChargingRippleView.kt */
public final class ChargingRippleView extends View {
    private final int defaultColor = -1;
    private long duration;
    private PointF origin;
    private float radius;
    private boolean rippleInProgress;
    private final Paint ripplePaint;
    private final RippleShader rippleShader;

    public final void startRipple() {
        startRipple$default(this, null, 1, null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: android.graphics.Paint */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.statusbar.charging.RippleShader, android.graphics.Shader] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ChargingRippleView(android.content.Context r4, android.util.AttributeSet r5) {
        /*
            r3 = this;
            r3.<init>(r4, r5)
            com.android.systemui.statusbar.charging.RippleShader r4 = new com.android.systemui.statusbar.charging.RippleShader
            r4.<init>()
            r3.rippleShader = r4
            r5 = -1
            r3.defaultColor = r5
            android.graphics.Paint r0 = new android.graphics.Paint
            r0.<init>()
            r3.ripplePaint = r0
            android.graphics.PointF r1 = new android.graphics.PointF
            r1.<init>()
            r3.origin = r1
            r1 = 1750(0x6d6, double:8.646E-321)
            r3.duration = r1
            r4.setColor(r5)
            r5 = 0
            r4.setProgress(r5)
            r5 = 1050253722(0x3e99999a, float:0.3)
            r4.setSparkleStrength(r5)
            r0.setShader(r4)
            r4 = 8
            r3.setVisibility(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.charging.ChargingRippleView.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public final boolean getRippleInProgress() {
        return this.rippleInProgress;
    }

    public final void setRippleInProgress(boolean z) {
        this.rippleInProgress = z;
    }

    public final void setRadius(float f) {
        this.rippleShader.setRadius(f);
        this.radius = f;
    }

    public final void setOrigin(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "value");
        this.rippleShader.setOrigin(pointF);
        this.origin = pointF;
    }

    public final void setDuration(long j) {
        this.duration = j;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        this.rippleShader.setPixelDensity(getResources().getDisplayMetrics().density);
        super.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        this.rippleShader.setPixelDensity(getResources().getDisplayMetrics().density);
        super.onAttachedToWindow();
    }

    public static /* synthetic */ void startRipple$default(ChargingRippleView chargingRippleView, Runnable runnable, int i, Object obj) {
        if ((i & 1) != 0) {
            runnable = null;
        }
        chargingRippleView.startRipple(runnable);
    }

    public final void startRipple(Runnable runnable) {
        if (!this.rippleInProgress) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(this.duration);
            ofFloat.addUpdateListener(new ChargingRippleView$startRipple$1(this));
            ofFloat.addListener(new ChargingRippleView$startRipple$2(this, runnable));
            ofFloat.start();
            setVisibility(0);
            this.rippleInProgress = true;
        }
    }

    public final void setColor(int i) {
        this.rippleShader.setColor(i);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f = (float) 1;
        float progress = (f - (((f - this.rippleShader.getProgress()) * (f - this.rippleShader.getProgress())) * (f - this.rippleShader.getProgress()))) * this.radius * ((float) 2);
        if (canvas != null) {
            PointF pointF = this.origin;
            canvas.drawCircle(pointF.x, pointF.y, progress, this.ripplePaint);
        }
    }
}

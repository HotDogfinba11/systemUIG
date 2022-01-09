package com.android.systemui.statusbar;

import android.os.Trace;
import android.util.Log;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewRootImpl;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController$updateBlurCallback$1 implements Choreographer.FrameCallback {
    final /* synthetic */ NotificationShadeDepthController this$0;

    NotificationShadeDepthController$updateBlurCallback$1(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    public final void doFrame(long j) {
        boolean z = false;
        this.this$0.updateScheduled = false;
        float f = 0.0f;
        float max = Math.max(Math.max(Math.max((this.this$0.blurUtils.blurRadiusOfRatio(Interpolators.getNotificationScrimAlpha(this.this$0.shouldApplyShadeBlur() ? this.this$0.getShadeExpansion() : 0.0f, false)) * 0.8f) + (MathUtils.constrain(this.this$0.getShadeAnimation().getRadius(), (float) this.this$0.blurUtils.getMinBlurRadius(), (float) this.this$0.blurUtils.getMaxBlurRadius()) * 0.19999999f), this.this$0.blurUtils.blurRadiusOfRatio(Interpolators.getNotificationScrimAlpha(this.this$0.getQsPanelExpansion(), false) * this.this$0.getShadeExpansion())), this.this$0.blurUtils.blurRadiusOfRatio(this.this$0.getTransitionToFullShadeProgress())), this.this$0.wakeAndUnlockBlurRadius);
        if (this.this$0.getBlursDisabledForAppLaunch()) {
            max = 0.0f;
        }
        float saturate = MathUtils.saturate(this.this$0.blurUtils.ratioOfBlurRadius(max));
        int i = (int) max;
        if (this.this$0.scrimsVisible) {
            i = 0;
        } else {
            f = saturate;
        }
        if (!this.this$0.blurUtils.supportsBlursOnWindows()) {
            i = 0;
        }
        int ratio = (int) (((float) i) * (1.0f - this.this$0.getBrightnessMirrorSpring().getRatio()));
        if ((this.this$0.scrimsVisible) && !this.this$0.getBlursDisabledForAppLaunch()) {
            z = true;
        }
        Trace.traceCounter(4096, "shade_blur_radius", ratio);
        BlurUtils blurUtils = this.this$0.blurUtils;
        View view = this.this$0.blurRoot;
        ViewRootImpl viewRootImpl = view == null ? null : view.getViewRootImpl();
        if (viewRootImpl == null) {
            viewRootImpl = this.this$0.getRoot().getViewRootImpl();
        }
        blurUtils.applyBlur(viewRootImpl, ratio, z);
        this.this$0.lastAppliedBlur = ratio;
        try {
            if (!this.this$0.getRoot().isAttachedToWindow() || this.this$0.getRoot().getWindowToken() == null) {
                Log.i("DepthController", Intrinsics.stringPlus("Won't set zoom. Window not attached ", this.this$0.getRoot()));
            } else {
                this.this$0.wallpaperManager.setWallpaperZoomOut(this.this$0.getRoot().getWindowToken(), f);
            }
        } catch (IllegalArgumentException e) {
            Log.w("DepthController", Intrinsics.stringPlus("Can't set zoom. Window is gone: ", this.this$0.getRoot().getWindowToken()), e);
        }
        for (NotificationShadeDepthController.DepthListener depthListener : this.this$0.listeners) {
            depthListener.onWallpaperZoomOutChanged(f);
            depthListener.onBlurRadiusChanged(ratio);
        }
        this.this$0.notificationShadeWindowController.setBackgroundBlurRadius(ratio);
    }
}

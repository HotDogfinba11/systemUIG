package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.CrossWindowBlurListeners;
import android.view.SurfaceControl;
import com.android.systemui.Dumpable;
import com.android.systemui.R$dimen;
import com.android.systemui.dump.DumpManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BlurUtils.kt */
public class BlurUtils implements Dumpable {
    private final CrossWindowBlurListeners crossWindowBlurListeners;
    private int lastAppliedBlur;
    private final int maxBlurRadius;
    private final int minBlurRadius;
    private final Resources resources;

    public BlurUtils(Resources resources2, CrossWindowBlurListeners crossWindowBlurListeners2, DumpManager dumpManager) {
        Intrinsics.checkNotNullParameter(resources2, "resources");
        Intrinsics.checkNotNullParameter(crossWindowBlurListeners2, "crossWindowBlurListeners");
        Intrinsics.checkNotNullParameter(dumpManager, "dumpManager");
        this.resources = resources2;
        this.crossWindowBlurListeners = crossWindowBlurListeners2;
        this.minBlurRadius = resources2.getDimensionPixelSize(R$dimen.min_window_blur_radius);
        this.maxBlurRadius = resources2.getDimensionPixelSize(R$dimen.max_window_blur_radius);
        String name = BlurUtils.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "javaClass.name");
        dumpManager.registerDumpable(name, this);
    }

    public final int getMinBlurRadius() {
        return this.minBlurRadius;
    }

    public final int getMaxBlurRadius() {
        return this.maxBlurRadius;
    }

    public final float blurRadiusOfRatio(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        return MathUtils.lerp((float) this.minBlurRadius, (float) this.maxBlurRadius, f);
    }

    public final float ratioOfBlurRadius(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        return MathUtils.map((float) this.minBlurRadius, (float) this.maxBlurRadius, 0.0f, 1.0f, f);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0045, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0049, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void applyBlur(android.view.ViewRootImpl r4, int r5, boolean r6) {
        /*
            r3 = this;
            if (r4 == 0) goto L_0x004a
            android.view.SurfaceControl r0 = r4.getSurfaceControl()
            boolean r0 = r0.isValid()
            if (r0 == 0) goto L_0x004a
            boolean r0 = r3.supportsBlursOnWindows()
            if (r0 != 0) goto L_0x0013
            goto L_0x004a
        L_0x0013:
            android.view.SurfaceControl$Transaction r0 = r3.createTransaction()
            r1 = 0
            android.view.SurfaceControl r2 = r4.getSurfaceControl()     // Catch:{ all -> 0x0043 }
            r0.setBackgroundBlurRadius(r2, r5)     // Catch:{ all -> 0x0043 }
            android.view.SurfaceControl r4 = r4.getSurfaceControl()     // Catch:{ all -> 0x0043 }
            r0.setOpaque(r4, r6)     // Catch:{ all -> 0x0043 }
            int r4 = r3.lastAppliedBlur     // Catch:{ all -> 0x0043 }
            if (r4 != 0) goto L_0x002f
            if (r5 == 0) goto L_0x002f
            r0.setEarlyWakeupStart()     // Catch:{ all -> 0x0043 }
        L_0x002f:
            int r4 = r3.lastAppliedBlur     // Catch:{ all -> 0x0043 }
            if (r4 == 0) goto L_0x0038
            if (r5 != 0) goto L_0x0038
            r0.setEarlyWakeupEnd()     // Catch:{ all -> 0x0043 }
        L_0x0038:
            r0.apply()     // Catch:{ all -> 0x0043 }
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0043 }
            kotlin.io.CloseableKt.closeFinally(r0, r1)
            r3.lastAppliedBlur = r5
            return
        L_0x0043:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0045 }
        L_0x0045:
            r4 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r3)
            throw r4
        L_0x004a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.BlurUtils.applyBlur(android.view.ViewRootImpl, int, boolean):void");
    }

    public SurfaceControl.Transaction createTransaction() {
        return new SurfaceControl.Transaction();
    }

    public boolean supportsBlursOnWindows() {
        if (!CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED || !ActivityManager.isHighEndGfx() || !this.crossWindowBlurListeners.isCrossWindowBlurEnabled() || SystemProperties.getBoolean("persist.sysui.disableBlur", false)) {
            return false;
        }
        return true;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BlurUtils:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(Intrinsics.stringPlus("minBlurRadius: ", Integer.valueOf(getMinBlurRadius())));
        indentingPrintWriter.println(Intrinsics.stringPlus("maxBlurRadius: ", Integer.valueOf(getMaxBlurRadius())));
        indentingPrintWriter.println(Intrinsics.stringPlus("supportsBlursOnWindows: ", Boolean.valueOf(supportsBlursOnWindows())));
        indentingPrintWriter.println(Intrinsics.stringPlus("CROSS_WINDOW_BLUR_SUPPORTED: ", Boolean.valueOf(CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED)));
        indentingPrintWriter.println(Intrinsics.stringPlus("isHighEndGfx: ", Boolean.valueOf(ActivityManager.isHighEndGfx())));
    }
}

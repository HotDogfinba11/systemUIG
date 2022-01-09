package com.google.material.monet;

import android.app.WallpaperColors;
import com.android.internal.graphics.cam.Cam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: ColorScheme.kt */
public final class ColorScheme {
    public static final Companion Companion = new Companion(null);
    private final List<Integer> accent1;
    private final List<Integer> accent2;
    private final List<Integer> accent3;
    private final boolean darkTheme;
    private final List<Integer> neutral1;
    private final List<Integer> neutral2;

    public ColorScheme(int i, boolean z) {
        this.darkTheme = z;
        Cam fromInt = Cam.fromInt((i == 0 || Cam.fromInt(i).getChroma() < 5.0f) ? -14979341 : i);
        float hue = fromInt.getHue();
        int[] of = Shades.of(hue, RangesKt___RangesKt.coerceAtLeast(fromInt.getChroma(), 48.0f));
        Intrinsics.checkNotNullExpressionValue(of, "of(hue, chroma)");
        this.accent1 = ArraysKt___ArraysKt.toList(of);
        int[] of2 = Shades.of(hue, 16.0f);
        Intrinsics.checkNotNullExpressionValue(of2, "of(hue, ACCENT2_CHROMA)");
        this.accent2 = ArraysKt___ArraysKt.toList(of2);
        int[] of3 = Shades.of(60.0f + hue, 32.0f);
        Intrinsics.checkNotNullExpressionValue(of3, "of(hue + ACCENT3_HUE_SHIFT, ACCENT3_CHROMA)");
        this.accent3 = ArraysKt___ArraysKt.toList(of3);
        int[] of4 = Shades.of(hue, 4.0f);
        Intrinsics.checkNotNullExpressionValue(of4, "of(hue, NEUTRAL1_CHROMA)");
        this.neutral1 = ArraysKt___ArraysKt.toList(of4);
        int[] of5 = Shades.of(hue, 8.0f);
        Intrinsics.checkNotNullExpressionValue(of5, "of(hue, NEUTRAL2_CHROMA)");
        this.neutral2 = ArraysKt___ArraysKt.toList(of5);
    }

    public final List<Integer> getAccent1() {
        return this.accent1;
    }

    public final List<Integer> getAllAccentColors() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.accent1);
        arrayList.addAll(this.accent2);
        arrayList.addAll(this.accent3);
        return arrayList;
    }

    public final List<Integer> getAllNeutralColors() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.neutral1);
        arrayList.addAll(this.neutral2);
        return arrayList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ColorScheme {\n  neutral1: ");
        Companion companion = Companion;
        sb.append(companion.humanReadable(this.neutral1));
        sb.append("\n  neutral2: ");
        sb.append(companion.humanReadable(this.neutral2));
        sb.append("\n  accent1: ");
        sb.append(companion.humanReadable(this.accent1));
        sb.append("\n  accent2: ");
        sb.append(companion.humanReadable(this.accent2));
        sb.append("\n  accent3: ");
        sb.append(companion.humanReadable(this.accent3));
        sb.append("\n}");
        return sb.toString();
    }

    /* compiled from: ColorScheme.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getSeedColor(WallpaperColors wallpaperColors) {
            Intrinsics.checkNotNullParameter(wallpaperColors, "wallpaperColors");
            return ((Number) CollectionsKt.first((List) getSeedColors(wallpaperColors))).intValue();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:83:0x030d, code lost:
            if (r2 != 15) goto L_0x031d;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.util.List<java.lang.Integer> getSeedColors(android.app.WallpaperColors r22) {
            /*
            // Method dump skipped, instructions count: 808
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.material.monet.ColorScheme.Companion.getSeedColors(android.app.WallpaperColors):java.util.List");
        }

        private final int wrapDegrees(int i) {
            if (i < 0) {
                return (i % 360) + 360;
            }
            return i >= 360 ? i % 360 : i;
        }

        private final float hueDiff(float f, float f2) {
            return 180.0f - Math.abs(Math.abs(f - f2) - 180.0f);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private final String humanReadable(List<Integer> list) {
            return CollectionsKt___CollectionsKt.joinToString$default(list, null, null, null, 0, null, ColorScheme$Companion$humanReadable$1.INSTANCE, 31, null);
        }

        private final double score(Cam cam, double d) {
            float f;
            double d2;
            double d3 = d * 70.0d;
            if (cam.getChroma() < 48.0f) {
                d2 = 0.1d;
                f = cam.getChroma();
            } else {
                d2 = 0.3d;
                f = cam.getChroma();
            }
            return (((double) (f - 48.0f)) * d2) + d3;
        }

        private final List<Double> huePopulations(Map<Integer, ? extends Cam> map, Map<Integer, Double> map2) {
            ArrayList arrayList = new ArrayList(360);
            for (int i = 0; i < 360; i++) {
                arrayList.add(Double.valueOf(0.0d));
            }
            List<Double> list = CollectionsKt___CollectionsKt.toMutableList((Collection) arrayList);
            for (Map.Entry<Integer, Double> entry : map2.entrySet()) {
                Double d = map2.get(entry.getKey());
                Intrinsics.checkNotNull(d);
                double doubleValue = d.doubleValue();
                Cam cam = (Cam) map.get(entry.getKey());
                Intrinsics.checkNotNull(cam);
                int i2 = MathKt__MathJVMKt.roundToInt(cam.getHue()) % 360;
                list.set(i2, Double.valueOf(list.get(i2).doubleValue() + doubleValue));
            }
            return list;
        }
    }
}

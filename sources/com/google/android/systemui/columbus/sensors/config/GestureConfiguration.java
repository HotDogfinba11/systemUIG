package com.google.android.systemui.columbus.sensors.config;

import android.util.Range;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GestureConfiguration.kt */
public final class GestureConfiguration {
    public static final Companion Companion = new Companion(null);
    private static final Range<Float> SENSITIVITY_RANGE = Range.create(Float.valueOf(0.0f), Float.valueOf(1.0f));
    private final Function1<Adjustment, Unit> adjustmentCallback = new GestureConfiguration$adjustmentCallback$1(this);
    private final List<Adjustment> adjustments;
    private Listener listener;
    private float sensitivity;
    private final SensorConfiguration sensorConfiguration;

    /* compiled from: GestureConfiguration.kt */
    public interface Listener {
        void onGestureConfigurationChanged(GestureConfiguration gestureConfiguration);
    }

    public GestureConfiguration(List<Adjustment> list, SensorConfiguration sensorConfiguration2) {
        Intrinsics.checkNotNullParameter(list, "adjustments");
        Intrinsics.checkNotNullParameter(sensorConfiguration2, "sensorConfiguration");
        this.adjustments = list;
        this.sensorConfiguration = sensorConfiguration2;
        this.sensitivity = sensorConfiguration2.defaultSensitivityValue;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            it.next().setCallback(this.adjustmentCallback);
        }
        updateSensitivity();
    }

    public final float getSensitivity() {
        return this.sensitivity;
    }

    public final void setListener(Listener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: private */
    public final void updateSensitivity() {
        float f = this.sensorConfiguration.defaultSensitivityValue;
        Iterator<T> it = this.adjustments.iterator();
        while (it.hasNext()) {
            Float clamp = SENSITIVITY_RANGE.clamp(Float.valueOf(it.next().adjustSensitivity(f)));
            Intrinsics.checkNotNullExpressionValue(clamp, "SENSITIVITY_RANGE.clamp(it.adjustSensitivity(newSensitivity))");
            f = clamp.floatValue();
        }
        if (Math.abs(this.sensitivity - f) >= 0.05f) {
            this.sensitivity = f;
            Listener listener2 = this.listener;
            if (listener2 != null) {
                listener2.onGestureConfigurationChanged(this);
            }
        }
    }

    /* compiled from: GestureConfiguration.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

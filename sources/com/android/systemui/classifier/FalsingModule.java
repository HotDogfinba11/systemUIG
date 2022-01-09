package com.android.systemui.classifier;

import android.content.res.Resources;
import android.view.ViewConfiguration;
import com.android.systemui.R$dimen;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface FalsingModule {
    static default long providesDoubleTapTimeoutMs() {
        return 1200;
    }

    static default Set<FalsingClassifier> providesBrightLineGestureClassifiers(DistanceClassifier distanceClassifier, ProximityClassifier proximityClassifier, PointerCountClassifier pointerCountClassifier, TypeClassifier typeClassifier, DiagonalClassifier diagonalClassifier, ZigZagClassifier zigZagClassifier) {
        return new HashSet(Arrays.asList(pointerCountClassifier, typeClassifier, diagonalClassifier, distanceClassifier, proximityClassifier, zigZagClassifier));
    }

    static default float providesDoubleTapTouchSlop(Resources resources) {
        return resources.getDimension(R$dimen.double_tap_slop);
    }

    static default float providesSingleTapTouchSlop(ViewConfiguration viewConfiguration) {
        return (float) viewConfiguration.getScaledTouchSlop();
    }
}

package com.android.systemui.util.animation;

import android.graphics.PointF;
import android.view.View;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

public final class TransitionViewState {
    private float alpha = 1.0f;
    private final PointF contentTranslation = new PointF();
    private int height;
    private final PointF translation = new PointF();
    private Map<Integer, WidgetState> widgetStates = new LinkedHashMap();
    private int width;

    public final Map<Integer, WidgetState> getWidgetStates() {
        return this.widgetStates;
    }

    public final int getWidth() {
        return this.width;
    }

    public final void setWidth(int i) {
        this.width = i;
    }

    public final int getHeight() {
        return this.height;
    }

    public final void setHeight(int i) {
        this.height = i;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final PointF getTranslation() {
        return this.translation;
    }

    public final PointF getContentTranslation() {
        return this.contentTranslation;
    }

    public static /* synthetic */ TransitionViewState copy$default(TransitionViewState transitionViewState, TransitionViewState transitionViewState2, int i, Object obj) {
        if ((i & 1) != 0) {
            transitionViewState2 = null;
        }
        return transitionViewState.copy(transitionViewState2);
    }

    public final TransitionViewState copy(TransitionViewState transitionViewState) {
        TransitionViewState transitionViewState2 = transitionViewState == null ? new TransitionViewState() : transitionViewState;
        transitionViewState2.width = this.width;
        transitionViewState2.height = this.height;
        transitionViewState2.alpha = this.alpha;
        PointF pointF = transitionViewState2.translation;
        PointF pointF2 = this.translation;
        pointF.set(pointF2.x, pointF2.y);
        PointF pointF3 = transitionViewState2.contentTranslation;
        PointF pointF4 = this.contentTranslation;
        pointF3.set(pointF4.x, pointF4.y);
        for (Map.Entry<Integer, WidgetState> entry : this.widgetStates.entrySet()) {
            transitionViewState2.widgetStates.put(Integer.valueOf(entry.getKey().intValue()), WidgetState.copy$default(entry.getValue(), 0.0f, 0.0f, 0, 0, 0, 0, 0.0f, 0.0f, false, 511, null));
        }
        return transitionViewState2;
    }

    public final void initFromLayout(TransitionLayout transitionLayout) {
        Intrinsics.checkNotNullParameter(transitionLayout, "transitionLayout");
        int childCount = transitionLayout.getChildCount();
        if (childCount > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                View childAt = transitionLayout.getChildAt(i);
                Map<Integer, WidgetState> map = this.widgetStates;
                Integer valueOf = Integer.valueOf(childAt.getId());
                WidgetState widgetState = map.get(valueOf);
                if (widgetState == null) {
                    widgetState = new WidgetState(0.0f, 0.0f, 0, 0, 0, 0, 0.0f, 0.0f, false, 384, null);
                    map.put(valueOf, widgetState);
                }
                Intrinsics.checkNotNullExpressionValue(childAt, "child");
                widgetState.initFromLayout(childAt);
                if (i2 >= childCount) {
                    break;
                }
                i = i2;
            }
        }
        this.width = transitionLayout.getMeasuredWidth();
        this.height = transitionLayout.getMeasuredHeight();
        this.translation.set(0.0f, 0.0f);
        this.contentTranslation.set(0.0f, 0.0f);
        this.alpha = 1.0f;
    }
}

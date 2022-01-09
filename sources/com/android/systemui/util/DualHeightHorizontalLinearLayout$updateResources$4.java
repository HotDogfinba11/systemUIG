package com.android.systemui.util;

import kotlin.jvm.internal.MutablePropertyReference0Impl;

/* compiled from: DualHeightHorizontalLinearLayout.kt */
/* synthetic */ class DualHeightHorizontalLinearLayout$updateResources$4 extends MutablePropertyReference0Impl {
    DualHeightHorizontalLinearLayout$updateResources$4(Object obj) {
        super(obj, DualHeightHorizontalLinearLayout.class, "singleLineVerticalPaddingPx", "getSingleLineVerticalPaddingPx()I", 0);
    }

    @Override // kotlin.reflect.KProperty0, kotlin.jvm.internal.MutablePropertyReference0Impl
    public Object get() {
        return Integer.valueOf(((DualHeightHorizontalLinearLayout) this.receiver).singleLineVerticalPaddingPx);
    }

    @Override // kotlin.reflect.KMutableProperty0, kotlin.jvm.internal.MutablePropertyReference0Impl
    public void set(Object obj) {
        ((DualHeightHorizontalLinearLayout) this.receiver).singleLineVerticalPaddingPx = ((Number) obj).intValue();
    }
}

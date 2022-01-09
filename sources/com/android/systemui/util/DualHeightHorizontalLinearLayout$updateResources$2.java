package com.android.systemui.util;

import kotlin.jvm.internal.MutablePropertyReference0Impl;

/* compiled from: DualHeightHorizontalLinearLayout.kt */
/* synthetic */ class DualHeightHorizontalLinearLayout$updateResources$2 extends MutablePropertyReference0Impl {
    DualHeightHorizontalLinearLayout$updateResources$2(Object obj) {
        super(obj, DualHeightHorizontalLinearLayout.class, "singleLineHeightPx", "getSingleLineHeightPx()I", 0);
    }

    @Override // kotlin.reflect.KProperty0, kotlin.jvm.internal.MutablePropertyReference0Impl
    public Object get() {
        return Integer.valueOf(DualHeightHorizontalLinearLayout.access$getSingleLineHeightPx$p((DualHeightHorizontalLinearLayout) this.receiver));
    }

    @Override // kotlin.reflect.KMutableProperty0, kotlin.jvm.internal.MutablePropertyReference0Impl
    public void set(Object obj) {
        DualHeightHorizontalLinearLayout.access$setSingleLineHeightPx$p((DualHeightHorizontalLinearLayout) this.receiver, ((Number) obj).intValue());
    }
}

package com.android.systemui.privacy.logging;

import com.android.systemui.privacy.PrivacyItem;
import kotlin.jvm.internal.PropertyReference1Impl;

/* access modifiers changed from: package-private */
/* compiled from: PrivacyLogger.kt */
public /* synthetic */ class PrivacyLogger$listToString$1 extends PropertyReference1Impl {
    public static final PrivacyLogger$listToString$1 INSTANCE = new PrivacyLogger$listToString$1();

    PrivacyLogger$listToString$1() {
        super(PrivacyItem.class, "log", "getLog()Ljava/lang/String;", 0);
    }

    @Override // kotlin.reflect.KProperty1, kotlin.jvm.internal.PropertyReference1Impl
    public Object get(Object obj) {
        return ((PrivacyItem) obj).getLog();
    }
}

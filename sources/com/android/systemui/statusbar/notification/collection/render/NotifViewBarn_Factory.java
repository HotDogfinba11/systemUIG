package com.android.systemui.statusbar.notification.collection.render;

import dagger.internal.Factory;

public final class NotifViewBarn_Factory implements Factory<NotifViewBarn> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final NotifViewBarn_Factory INSTANCE = new NotifViewBarn_Factory();
    }

    @Override // javax.inject.Provider
    public NotifViewBarn get() {
        return newInstance();
    }

    public static NotifViewBarn_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotifViewBarn newInstance() {
        return new NotifViewBarn();
    }
}

package com.google.android.systemui.dagger;

import dagger.internal.Factory;

public final class SystemUIGoogleModule_ProvideVoiceReplyCtaContainerIdFactory implements Factory<Integer> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final SystemUIGoogleModule_ProvideVoiceReplyCtaContainerIdFactory INSTANCE = new SystemUIGoogleModule_ProvideVoiceReplyCtaContainerIdFactory();
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(provideVoiceReplyCtaContainerId());
    }

    public static SystemUIGoogleModule_ProvideVoiceReplyCtaContainerIdFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static int provideVoiceReplyCtaContainerId() {
        return SystemUIGoogleModule.provideVoiceReplyCtaContainerId();
    }
}

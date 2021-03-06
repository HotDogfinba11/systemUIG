package com.google.android.systemui.dagger;

import dagger.internal.Factory;

public final class SystemUIGoogleModule_ProvideVoiceReplyCtaTextIdFactory implements Factory<Integer> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final SystemUIGoogleModule_ProvideVoiceReplyCtaTextIdFactory INSTANCE = new SystemUIGoogleModule_ProvideVoiceReplyCtaTextIdFactory();
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(provideVoiceReplyCtaTextId());
    }

    public static SystemUIGoogleModule_ProvideVoiceReplyCtaTextIdFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static int provideVoiceReplyCtaTextId() {
        return SystemUIGoogleModule.provideVoiceReplyCtaTextId();
    }
}

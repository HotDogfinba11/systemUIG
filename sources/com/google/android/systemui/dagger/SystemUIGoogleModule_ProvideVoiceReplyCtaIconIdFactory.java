package com.google.android.systemui.dagger;

import dagger.internal.Factory;

public final class SystemUIGoogleModule_ProvideVoiceReplyCtaIconIdFactory implements Factory<Integer> {

    /* access modifiers changed from: private */
    public static final class InstanceHolder {
        private static final SystemUIGoogleModule_ProvideVoiceReplyCtaIconIdFactory INSTANCE = new SystemUIGoogleModule_ProvideVoiceReplyCtaIconIdFactory();
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(provideVoiceReplyCtaIconId());
    }

    public static SystemUIGoogleModule_ProvideVoiceReplyCtaIconIdFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static int provideVoiceReplyCtaIconId() {
        return SystemUIGoogleModule.provideVoiceReplyCtaIconId();
    }
}

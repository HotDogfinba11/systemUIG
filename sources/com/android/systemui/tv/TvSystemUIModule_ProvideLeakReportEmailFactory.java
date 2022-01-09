package com.android.systemui.tv;

import dagger.internal.Factory;

public final class TvSystemUIModule_ProvideLeakReportEmailFactory implements Factory<String> {

    private static final class InstanceHolder {
        private static final TvSystemUIModule_ProvideLeakReportEmailFactory INSTANCE = new TvSystemUIModule_ProvideLeakReportEmailFactory();
    }

    @Override // javax.inject.Provider
    public String get() {
        return provideLeakReportEmail();
    }

    public static TvSystemUIModule_ProvideLeakReportEmailFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provideLeakReportEmail() {
        return TvSystemUIModule.provideLeakReportEmail();
    }
}

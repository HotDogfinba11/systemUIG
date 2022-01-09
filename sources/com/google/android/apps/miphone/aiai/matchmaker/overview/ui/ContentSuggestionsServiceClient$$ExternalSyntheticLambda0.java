package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.InteractionContextParcelables$InteractionContext;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceClient$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ContentSuggestionsServiceClient f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Bundle f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ String f$4;
    public final /* synthetic */ long f$5;
    public final /* synthetic */ InteractionContextParcelables$InteractionContext f$6;
    public final /* synthetic */ UserHandle f$7;
    public final /* synthetic */ Uri f$8;
    public final /* synthetic */ ContentSuggestionsServiceWrapper.BundleCallback f$9;

    public /* synthetic */ ContentSuggestionsServiceClient$$ExternalSyntheticLambda0(ContentSuggestionsServiceClient contentSuggestionsServiceClient, int i, Bundle bundle, String str, String str2, long j, InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext, UserHandle userHandle, Uri uri, ContentSuggestionsServiceWrapper.BundleCallback bundleCallback) {
        this.f$0 = contentSuggestionsServiceClient;
        this.f$1 = i;
        this.f$2 = bundle;
        this.f$3 = str;
        this.f$4 = str2;
        this.f$5 = j;
        this.f$6 = interactionContextParcelables$InteractionContext;
        this.f$7 = userHandle;
        this.f$8 = uri;
        this.f$9 = bundleCallback;
    }

    public final void run() {
        this.f$0.lambda$provideScreenshotActions$0$ContentSuggestionsServiceClient(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9);
    }
}

package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.os.Bundle;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SuggestController$$ExternalSyntheticLambda0 implements ContentSuggestionsServiceWrapper.BundleCallback {
    public final /* synthetic */ SuggestController f$0;

    public /* synthetic */ SuggestController$$ExternalSyntheticLambda0(SuggestController suggestController) {
        this.f$0 = suggestController;
    }

    @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper.BundleCallback
    public final void onResult(Bundle bundle) {
        this.f$0.lambda$startIfNeeded$2$SuggestController(bundle);
    }
}

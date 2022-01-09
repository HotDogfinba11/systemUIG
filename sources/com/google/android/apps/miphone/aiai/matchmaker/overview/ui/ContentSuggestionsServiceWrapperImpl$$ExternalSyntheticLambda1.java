package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.app.contentsuggestions.ContentSuggestionsManager;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapperImpl;
import java.util.List;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda1 implements ContentSuggestionsManager.SelectionsCallback {
    public final /* synthetic */ ContentSuggestionsServiceWrapperImpl.BundleCallbackWrapper f$0;

    public /* synthetic */ ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda1(ContentSuggestionsServiceWrapperImpl.BundleCallbackWrapper bundleCallbackWrapper) {
        this.f$0 = bundleCallbackWrapper;
    }

    public final void onContentSelectionsAvailable(int i, List list) {
        ContentSuggestionsServiceWrapperImpl.lambda$suggestContentSelections$0(this.f$0, i, list);
    }
}

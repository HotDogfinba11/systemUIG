package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ ContentSuggestionsServiceWrapperImpl f$0;
    public final /* synthetic */ Supplier f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ SuggestListener f$3;
    public final /* synthetic */ FeedbackParcelables$FeedbackBatch f$4;

    public /* synthetic */ ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda2(ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl, Supplier supplier, String str, SuggestListener suggestListener, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch) {
        this.f$0 = contentSuggestionsServiceWrapperImpl;
        this.f$1 = supplier;
        this.f$2 = str;
        this.f$3 = suggestListener;
        this.f$4 = feedbackParcelables$FeedbackBatch;
    }

    public final void run() {
        this.f$0.lambda$notifyInteractionAsync$2$ContentSuggestionsServiceWrapperImpl(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

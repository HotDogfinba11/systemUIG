package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SuggestController$$ExternalSyntheticLambda5 implements Supplier {
    public final /* synthetic */ SuggestController f$0;
    public final /* synthetic */ FeedbackParcelables$FeedbackBatch f$1;

    public /* synthetic */ SuggestController$$ExternalSyntheticLambda5(SuggestController suggestController, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch) {
        this.f$0 = suggestController;
        this.f$1 = feedbackParcelables$FeedbackBatch;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f$0.lambda$reportMetricsToService$1$SuggestController(this.f$1);
    }
}

package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOp;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpStatus;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceClient$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ ContentSuggestionsServiceClient f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ FeedbackParcelables$ScreenshotOp f$2;
    public final /* synthetic */ FeedbackParcelables$ScreenshotOpStatus f$3;
    public final /* synthetic */ long f$4;

    public /* synthetic */ ContentSuggestionsServiceClient$$ExternalSyntheticLambda1(ContentSuggestionsServiceClient contentSuggestionsServiceClient, String str, FeedbackParcelables$ScreenshotOp feedbackParcelables$ScreenshotOp, FeedbackParcelables$ScreenshotOpStatus feedbackParcelables$ScreenshotOpStatus, long j) {
        this.f$0 = contentSuggestionsServiceClient;
        this.f$1 = str;
        this.f$2 = feedbackParcelables$ScreenshotOp;
        this.f$3 = feedbackParcelables$ScreenshotOpStatus;
        this.f$4 = j;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f$0.lambda$notifyOp$1$ContentSuggestionsServiceClient(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

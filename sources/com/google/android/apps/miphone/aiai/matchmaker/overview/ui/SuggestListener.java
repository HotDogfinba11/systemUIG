package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;

public interface SuggestListener {
    void onFeedbackBatchSent(String str, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch);
}

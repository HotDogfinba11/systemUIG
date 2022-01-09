package com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2;

import android.os.Bundle;

public enum FeedbackParcelables$SuggestionAction {
    SUGGESTION_ACTION_UNKNOWN(0),
    SUGGESTION_ACTION_CLICKED(1),
    SUGGESTION_ACTION_DISMISSED(2),
    SUGGESTION_ACTION_SHOWN(3),
    SUGGESTION_ACTION_EXPANDED(4);
    
    public final int value;

    private FeedbackParcelables$SuggestionAction(int i) {
        this.value = i;
    }

    public Bundle writeToBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("value", this.value);
        return bundle;
    }
}

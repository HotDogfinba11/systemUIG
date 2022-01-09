package com.google.android.systemui.assist.uihints;

import java.util.Optional;

public final /* synthetic */ class TranscriptionController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ TranscriptionController f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Optional f$2;

    public /* synthetic */ TranscriptionController$$ExternalSyntheticLambda5(TranscriptionController transcriptionController, String str, Optional optional) {
        this.f$0 = transcriptionController;
        this.f$1 = str;
        this.f$2 = optional;
    }

    public final void run() {
        this.f$0.lambda$onGreetingInfo$0(this.f$1, this.f$2);
    }
}

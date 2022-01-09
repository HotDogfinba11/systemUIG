package com.google.android.systemui.assist.uihints;

import java.util.List;
import java.util.Optional;

public final /* synthetic */ class TranscriptionController$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ TranscriptionController f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ Optional f$2;

    public /* synthetic */ TranscriptionController$$ExternalSyntheticLambda8(TranscriptionController transcriptionController, List list, Optional optional) {
        this.f$0 = transcriptionController;
        this.f$1 = list;
        this.f$2 = optional;
    }

    public final void run() {
        this.f$0.lambda$onChipsInfo$2(this.f$1, this.f$2);
    }
}

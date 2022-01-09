package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.content.Intent;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentSuggestionsServiceClient$$ExternalSyntheticLambda2 implements Supplier {
    public final /* synthetic */ ContentSuggestionsServiceClient f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ Intent f$4;

    public /* synthetic */ ContentSuggestionsServiceClient$$ExternalSyntheticLambda2(ContentSuggestionsServiceClient contentSuggestionsServiceClient, String str, String str2, boolean z, Intent intent) {
        this.f$0 = contentSuggestionsServiceClient;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = z;
        this.f$4 = intent;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f$0.lambda$notifyAction$2$ContentSuggestionsServiceClient(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

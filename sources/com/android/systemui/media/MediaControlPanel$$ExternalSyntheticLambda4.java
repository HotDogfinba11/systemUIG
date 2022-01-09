package com.android.systemui.media;

import android.app.smartspace.SmartspaceAction;
import android.view.View;

public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda4 implements View.OnClickListener {
    public final /* synthetic */ MediaControlPanel f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ SmartspaceAction f$2;
    public final /* synthetic */ View f$3;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda4(MediaControlPanel mediaControlPanel, int i, SmartspaceAction smartspaceAction, View view) {
        this.f$0 = mediaControlPanel;
        this.f$1 = i;
        this.f$2 = smartspaceAction;
        this.f$3 = view;
    }

    public final void onClick(View view) {
        this.f$0.lambda$setSmartspaceRecItemOnClickListener$14(this.f$1, this.f$2, this.f$3, view);
    }
}

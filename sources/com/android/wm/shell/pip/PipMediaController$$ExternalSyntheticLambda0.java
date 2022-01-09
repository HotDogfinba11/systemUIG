package com.android.wm.shell.pip;

import android.media.session.MediaSessionManager;
import java.util.List;

public final /* synthetic */ class PipMediaController$$ExternalSyntheticLambda0 implements MediaSessionManager.OnActiveSessionsChangedListener {
    public final /* synthetic */ PipMediaController f$0;

    public /* synthetic */ PipMediaController$$ExternalSyntheticLambda0(PipMediaController pipMediaController) {
        this.f$0 = pipMediaController;
    }

    @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
    public final void onActiveSessionsChanged(List list) {
        this.f$0.resolveActiveMediaController(list);
    }
}

package com.android.systemui.screenrecord;

import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.plugins.ActivityStarter;

public final /* synthetic */ class RecordingService$$ExternalSyntheticLambda0 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ RecordingService f$0;
    public final /* synthetic */ Intent f$1;
    public final /* synthetic */ UserHandle f$2;

    public /* synthetic */ RecordingService$$ExternalSyntheticLambda0(RecordingService recordingService, Intent intent, UserHandle userHandle) {
        this.f$0 = recordingService;
        this.f$1 = intent;
        this.f$2 = userHandle;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        return RecordingService.$r8$lambda$ftry27WcwqOYarCg2igvzA5KHWA(this.f$0, this.f$1, this.f$2);
    }
}

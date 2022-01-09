package com.android.systemui.qs.tiles;

import android.content.Intent;
import com.android.systemui.plugins.ActivityStarter;

public final /* synthetic */ class ScreenRecordTile$$ExternalSyntheticLambda0 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ ScreenRecordTile f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ ScreenRecordTile$$ExternalSyntheticLambda0(ScreenRecordTile screenRecordTile, Intent intent) {
        this.f$0 = screenRecordTile;
        this.f$1 = intent;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        return ScreenRecordTile.$r8$lambda$uPKAmku2Ulv877Doo6iVoJ8aEeo(this.f$0, this.f$1);
    }
}

package com.android.systemui.people;

import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import java.util.function.Function;

public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ LauncherApps f$0;

    public /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda3(LauncherApps launcherApps) {
        this.f$0 = launcherApps;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return PeopleSpaceUtils.lambda$getSortedTiles$3(this.f$0, (ShortcutInfo) obj);
    }
}

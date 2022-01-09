package com.android.systemui.people;

import android.app.people.IPeopleManager;
import android.app.people.PeopleSpaceTile;
import java.util.function.Function;

public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ IPeopleManager f$0;

    public /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda2(IPeopleManager iPeopleManager) {
        this.f$0 = iPeopleManager;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return PeopleSpaceUtils.lambda$getSortedTiles$5(this.f$0, (PeopleSpaceTile) obj);
    }
}

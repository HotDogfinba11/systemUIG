package com.android.systemui.statusbar;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.function.Predicate;

public final /* synthetic */ class StatusBarStateControllerImpl$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ StatusBarStateController.StateListener f$0;

    public /* synthetic */ StatusBarStateControllerImpl$$ExternalSyntheticLambda0(StatusBarStateController.StateListener stateListener) {
        this.f$0 = stateListener;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return StatusBarStateControllerImpl.lambda$removeCallback$1(this.f$0, (SysuiStatusBarStateController.RankedListener) obj);
    }
}

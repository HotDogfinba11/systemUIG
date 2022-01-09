package com.android.systemui.statusbar.phone;

import android.database.ContentObserver;

/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1 extends ContentObserver {
    final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        super(null);
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public void onChange(boolean z) {
        this.this$0.updateAnimatorDurationScale();
    }
}

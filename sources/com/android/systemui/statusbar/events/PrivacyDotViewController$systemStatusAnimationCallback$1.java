package com.android.systemui.statusbar.events;

import android.animation.Animator;
import kotlin.Unit;

/* compiled from: PrivacyDotViewController.kt */
public final class PrivacyDotViewController$systemStatusAnimationCallback$1 implements SystemStatusAnimationCallback {
    final /* synthetic */ PrivacyDotViewController this$0;

    PrivacyDotViewController$systemStatusAnimationCallback$1(PrivacyDotViewController privacyDotViewController) {
        this.this$0 = privacyDotViewController;
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public Animator onSystemStatusAnimationTransitionToPersistentDot(String str) {
        Object obj = this.this$0.lock;
        PrivacyDotViewController privacyDotViewController = this.this$0;
        synchronized (obj) {
            privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, true, false, false, null, null, null, null, false, 0, 0, 0, null, str, 8189, null));
            Unit unit = Unit.INSTANCE;
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    public Animator onHidePersistentDot() {
        Object obj = this.this$0.lock;
        PrivacyDotViewController privacyDotViewController = this.this$0;
        synchronized (obj) {
            privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 16381, null));
            Unit unit = Unit.INSTANCE;
        }
        return null;
    }
}

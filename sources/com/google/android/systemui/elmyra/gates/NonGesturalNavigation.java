package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.shared.system.QuickStepContract;

public class NonGesturalNavigation extends Gate {
    private boolean mCurrentModeIsGestural;
    private final NavigationModeController mModeController = ((NavigationModeController) Dependency.get(NavigationModeController.class));
    private final NavigationModeController.ModeChangedListener mModeListener = new NavigationModeController.ModeChangedListener() {
        /* class com.google.android.systemui.elmyra.gates.NonGesturalNavigation.AnonymousClass1 */

        @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
        public void onNavigationModeChanged(int i) {
            NonGesturalNavigation.this.mCurrentModeIsGestural = QuickStepContract.isGesturalMode(i);
            NonGesturalNavigation.this.notifyListener();
        }
    };

    public NonGesturalNavigation(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        this.mCurrentModeIsGestural = QuickStepContract.isGesturalMode(this.mModeController.addListener(this.mModeListener));
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        this.mModeController.removeListener(this.mModeListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public boolean isBlocked() {
        return !isNavigationGestural();
    }

    public boolean isNavigationGestural() {
        return this.mCurrentModeIsGestural;
    }
}

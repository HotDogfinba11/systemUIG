package com.android.systemui.statusbar.phone;

import android.app.Fragment;
import com.android.systemui.fragments.FragmentHostManager;

public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda3 implements FragmentHostManager.FragmentListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda3(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
    public final void onFragmentViewCreated(String str, Fragment fragment) {
        this.f$0.lambda$makeStatusBarView$3(str, fragment);
    }
}

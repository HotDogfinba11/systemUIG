package com.android.systemui.tuner;

import androidx.preference.ListPreference;
import com.android.systemui.tuner.TunerService;

public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda4 implements TunerService.Tunable {
    public final /* synthetic */ NavBarTuner f$0;
    public final /* synthetic */ ListPreference f$1;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda4(NavBarTuner navBarTuner, ListPreference listPreference) {
        this.f$0 = navBarTuner;
        this.f$1 = listPreference;
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        NavBarTuner.$r8$lambda$nbDC6NT5LE3qqWPYR4XtIw7EhmM(this.f$0, this.f$1, str, str2);
    }
}

package com.android.systemui.privacy.television;

public final /* synthetic */ class TvOngoingPrivacyChip$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TvOngoingPrivacyChip f$0;

    public /* synthetic */ TvOngoingPrivacyChip$$ExternalSyntheticLambda0(TvOngoingPrivacyChip tvOngoingPrivacyChip) {
        this.f$0 = tvOngoingPrivacyChip;
    }

    public final void run() {
        this.f$0.makeAccessibilityAnnouncement();
    }
}

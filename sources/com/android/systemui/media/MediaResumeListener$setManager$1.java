package com.android.systemui.media;

import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$setManager$1 implements TunerService.Tunable {
    final /* synthetic */ MediaResumeListener this$0;

    MediaResumeListener$setManager$1(MediaResumeListener mediaResumeListener) {
        this.this$0 = mediaResumeListener;
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String str, String str2) {
        MediaResumeListener mediaResumeListener = this.this$0;
        mediaResumeListener.useMediaResumption = Utils.useMediaResumption(mediaResumeListener.context);
        MediaDataManager mediaDataManager = this.this$0.mediaDataManager;
        if (mediaDataManager != null) {
            mediaDataManager.setMediaResumptionEnabled(this.this$0.useMediaResumption);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("mediaDataManager");
            throw null;
        }
    }
}

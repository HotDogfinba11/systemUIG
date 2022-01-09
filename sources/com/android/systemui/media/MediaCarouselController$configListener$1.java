package com.android.systemui.media;

import android.content.res.Configuration;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: MediaCarouselController.kt */
public final class MediaCarouselController$configListener$1 implements ConfigurationController.ConfigurationListener {
    final /* synthetic */ MediaCarouselController this$0;

    MediaCarouselController$configListener$1(MediaCarouselController mediaCarouselController) {
        this.this$0 = mediaCarouselController;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        this.this$0.recreatePlayers();
        this.this$0.inflateSettingsButton();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onOverlayChanged() {
        this.this$0.recreatePlayers();
        this.this$0.inflateSettingsButton();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration configuration) {
        if (configuration != null) {
            MediaCarouselController mediaCarouselController = this.this$0;
            boolean z = true;
            if (configuration.getLayoutDirection() != 1) {
                z = false;
            }
            mediaCarouselController.setRtl(z);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        this.this$0.recreatePlayers();
        this.this$0.inflateSettingsButton();
    }
}

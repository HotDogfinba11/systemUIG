package com.android.systemui.statusbar.policy;

import android.content.res.Configuration;

public interface ConfigurationController extends CallbackController<ConfigurationListener> {

    public interface ConfigurationListener {
        default void onConfigChanged(Configuration configuration) {
        }

        default void onDensityOrFontScaleChanged() {
        }

        default void onLayoutDirectionChanged(boolean z) {
        }

        default void onLocaleListChanged() {
        }

        default void onOverlayChanged() {
        }

        default void onSmallestScreenWidthChanged() {
        }

        default void onThemeChanged() {
        }

        default void onUiModeChanged() {
        }
    }

    boolean isLayoutRtl();

    void notifyThemeChanged();

    void onConfigurationChanged(Configuration configuration);
}

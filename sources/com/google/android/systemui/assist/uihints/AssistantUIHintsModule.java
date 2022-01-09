package com.google.android.systemui.assist.uihints;

import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController;
import com.google.android.systemui.assist.uihints.input.NgaInputHandler;
import dagger.Lazy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AssistantUIHintsModule {
    static Set<NgaMessageHandler.AudioInfoListener> provideAudioInfoListeners(EdgeLightsController edgeLightsController, GlowController glowController) {
        return new HashSet(Arrays.asList(edgeLightsController, glowController));
    }

    static Set<NgaMessageHandler.CardInfoListener> provideCardInfoListeners(GlowController glowController, ScrimController scrimController, TranscriptionController transcriptionController, LightnessProvider lightnessProvider) {
        return new HashSet(Arrays.asList(glowController, scrimController, transcriptionController, lightnessProvider));
    }

    static Set<NgaMessageHandler.ConfigInfoListener> provideConfigInfoListeners(AssistantPresenceHandler assistantPresenceHandler, TouchInsideHandler touchInsideHandler, TouchOutsideHandler touchOutsideHandler, TaskStackNotifier taskStackNotifier, KeyboardMonitor keyboardMonitor, ColorChangeHandler colorChangeHandler, ConfigurationHandler configurationHandler) {
        return new HashSet(Arrays.asList(assistantPresenceHandler, touchInsideHandler, touchOutsideHandler, taskStackNotifier, keyboardMonitor, colorChangeHandler, configurationHandler));
    }

    static Set<NgaMessageHandler.EdgeLightsInfoListener> bindEdgeLightsInfoListeners(EdgeLightsController edgeLightsController, NgaInputHandler ngaInputHandler) {
        return new HashSet(Arrays.asList(edgeLightsController, ngaInputHandler));
    }

    static NgaMessageHandler.StartActivityInfoListener provideActivityStarter(final Lazy<StatusBar> lazy) {
        return new NgaMessageHandler.StartActivityInfoListener() {
            /* class com.google.android.systemui.assist.uihints.AssistantUIHintsModule.AnonymousClass1 */

            @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.StartActivityInfoListener
            public void onStartActivityInfo(Intent intent, boolean z) {
                if (intent == null) {
                    Log.e("ActivityStarter", "Null intent; cannot start activity");
                } else {
                    ((StatusBar) Lazy.this.get()).startActivity(intent, z);
                }
            }
        };
    }

    static ViewGroup provideParentViewGroup(OverlayUiHost overlayUiHost) {
        return overlayUiHost.getParent();
    }
}

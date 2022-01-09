package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.KeyEvent;
import com.android.internal.logging.UiEventLogger;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ManageMedia.kt */
public final class ManageMedia extends UserAction {
    public static final Companion Companion = new Companion(null);
    private final AudioManager audioManager;
    private final String tag = "Columbus/ManageMedia";
    private final UiEventLogger uiEventLogger;

    @Override // com.google.android.systemui.columbus.actions.UserAction
    public boolean availableOnLockscreen() {
        return true;
    }

    @Override // com.google.android.systemui.columbus.actions.UserAction
    public boolean availableOnScreenOff() {
        return true;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ManageMedia(Context context, AudioManager audioManager2, UiEventLogger uiEventLogger2) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(audioManager2, "audioManager");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        this.audioManager = audioManager2;
        this.uiEventLogger = uiEventLogger2;
        setAvailable(true);
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public String getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig() {
        return this.tag;
    }

    @Override // com.google.android.systemui.columbus.actions.Action
    public void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        boolean z = this.audioManager.isMusicActive() || this.audioManager.isMusicActiveRemotely();
        long uptimeMillis = SystemClock.uptimeMillis();
        injectMediaPlayPauseKey(uptimeMillis, 0);
        injectMediaPlayPauseKey(uptimeMillis, 1);
        if (z) {
            this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_PAUSE_MEDIA);
        } else {
            this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_PLAY_MEDIA);
        }
    }

    private final void injectMediaPlayPauseKey(long j, int i) {
        this.audioManager.dispatchMediaKeyEvent(new KeyEvent(j, j, i, 85, 0));
    }

    /* compiled from: ManageMedia.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

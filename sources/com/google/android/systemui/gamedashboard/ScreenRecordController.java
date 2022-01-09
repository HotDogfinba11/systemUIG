package com.google.android.systemui.gamedashboard;

import android.content.Intent;
import android.os.Handler;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;

public class ScreenRecordController implements LifecycleOwner {
    private final RecordingController mController;
    private final KeyguardDismissUtil mKeyguardDismissUtil;
    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
    private final ToastController mToast;
    private final Handler mUiHandler;
    private final UserContextProvider mUserContextProvider;

    public ScreenRecordController(RecordingController recordingController, Handler handler, KeyguardDismissUtil keyguardDismissUtil, UserContextProvider userContextProvider, ToastController toastController) {
        this.mUserContextProvider = userContextProvider;
        this.mController = recordingController;
        this.mUiHandler = handler;
        handler.post(new ScreenRecordController$$ExternalSyntheticLambda2(this));
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mToast = toastController;
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
    }

    public void handleClick() {
        if (this.mController.isStarting()) {
            cancelCountdown();
        } else if (this.mController.isRecording()) {
            stopRecording();
        } else {
            this.mUiHandler.post(new ScreenRecordController$$ExternalSyntheticLambda1(this));
        }
    }

    public void cancelCountdown() {
        this.mController.cancelCountdown();
    }

    public void stopRecording() {
        this.mController.stopRecording();
        this.mToast.showRecordSaveText();
    }

    public boolean isRecording() {
        return this.mController.isRecording();
    }

    public boolean isStarting() {
        return this.mController.isStarting();
    }

    public RecordingController getController() {
        return this.mController;
    }

    /* renamed from: showPrompt */
    public void lambda$handleClick$1() {
        this.mKeyguardDismissUtil.executeWhenUnlocked(new ScreenRecordController$$ExternalSyntheticLambda0(this, this.mController.getPromptIntent()), false, false);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showPrompt$2(Intent intent) {
        this.mUserContextProvider.getUserContext().startActivity(intent);
        return false;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycle;
    }
}

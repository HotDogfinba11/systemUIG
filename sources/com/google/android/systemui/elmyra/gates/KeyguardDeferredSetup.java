package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import com.google.android.systemui.elmyra.UserContentObserver;
import com.google.android.systemui.elmyra.actions.Action;
import com.google.android.systemui.elmyra.gates.Gate;
import java.util.ArrayList;
import java.util.List;

public class KeyguardDeferredSetup extends Gate {
    private boolean mDeferredSetupComplete;
    private final List<Action> mExceptions;
    private final KeyguardVisibility mKeyguardGate;
    private final Gate.Listener mKeyguardGateListener;
    private final UserContentObserver mSettingsObserver;

    public KeyguardDeferredSetup(Context context, List<Action> list) {
        super(context);
        AnonymousClass1 r0 = new Gate.Listener() {
            /* class com.google.android.systemui.elmyra.gates.KeyguardDeferredSetup.AnonymousClass1 */

            @Override // com.google.android.systemui.elmyra.gates.Gate.Listener
            public void onGateChanged(Gate gate) {
                KeyguardDeferredSetup.this.notifyListener();
            }
        };
        this.mKeyguardGateListener = r0;
        this.mExceptions = new ArrayList(list);
        KeyguardVisibility keyguardVisibility = new KeyguardVisibility(context);
        this.mKeyguardGate = keyguardVisibility;
        keyguardVisibility.setListener(r0);
        this.mSettingsObserver = new UserContentObserver(context, Settings.Secure.getUriFor("assist_gesture_setup_complete"), new KeyguardDeferredSetup$$ExternalSyntheticLambda0(this), false);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Uri uri) {
        updateSetupComplete();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        this.mKeyguardGate.activate();
        this.mDeferredSetupComplete = isDeferredSetupComplete();
        this.mSettingsObserver.activate();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        this.mKeyguardGate.deactivate();
        this.mSettingsObserver.deactivate();
    }

    private void updateSetupComplete() {
        boolean isDeferredSetupComplete = isDeferredSetupComplete();
        if (this.mDeferredSetupComplete != isDeferredSetupComplete) {
            this.mDeferredSetupComplete = isDeferredSetupComplete;
            notifyListener();
        }
    }

    private boolean isDeferredSetupComplete() {
        if (Settings.Secure.getIntForUser(getContext().getContentResolver(), "assist_gesture_setup_complete", 0, -2) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public boolean isBlocked() {
        for (int i = 0; i < this.mExceptions.size(); i++) {
            if (this.mExceptions.get(i).isAvailable()) {
                return false;
            }
        }
        if (this.mDeferredSetupComplete || !this.mKeyguardGate.isBlocking()) {
            return false;
        }
        return true;
    }

    public boolean isSuwComplete() {
        return this.mDeferredSetupComplete;
    }

    @Override // com.google.android.systemui.elmyra.gates.Gate
    public String toString() {
        return super.toString() + " [mDeferredSetupComplete -> " + this.mDeferredSetupComplete + "]";
    }
}

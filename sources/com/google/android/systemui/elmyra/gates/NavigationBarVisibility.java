package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.statusbar.CommandQueue;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.elmyra.actions.Action;
import com.google.android.systemui.elmyra.gates.Gate;
import java.util.ArrayList;
import java.util.List;

public class NavigationBarVisibility extends Gate {
    private final AssistManagerGoogle mAssistManager;
    private final CommandQueue mCommandQueue;
    private final CommandQueue.Callbacks mCommandQueueCallbacks;
    private final int mDisplayId;
    private final List<Action> mExceptions;
    private final Gate.Listener mGateListener;
    private boolean mIsKeyguardShowing;
    private boolean mIsNavigationGestural;
    private boolean mIsNavigationHidden = false;
    private final KeyguardVisibility mKeyguardGate;
    private final NonGesturalNavigation mNavigationModeGate;

    public NavigationBarVisibility(Context context, List<Action> list) {
        super(context);
        AnonymousClass1 r0 = new CommandQueue.Callbacks() {
            /* class com.google.android.systemui.elmyra.gates.NavigationBarVisibility.AnonymousClass1 */

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public void setWindowState(int i, int i2, int i3) {
                if (NavigationBarVisibility.this.mDisplayId == i && i2 == 2) {
                    boolean z = i3 != 0;
                    if (z != NavigationBarVisibility.this.mIsNavigationHidden) {
                        NavigationBarVisibility.this.mIsNavigationHidden = z;
                        NavigationBarVisibility.this.notifyListener();
                    }
                }
            }
        };
        this.mCommandQueueCallbacks = r0;
        AnonymousClass2 r1 = new Gate.Listener() {
            /* class com.google.android.systemui.elmyra.gates.NavigationBarVisibility.AnonymousClass2 */

            @Override // com.google.android.systemui.elmyra.gates.Gate.Listener
            public void onGateChanged(Gate gate) {
                if (gate.equals(NavigationBarVisibility.this.mKeyguardGate)) {
                    NavigationBarVisibility.this.updateKeyguardState();
                } else if (gate.equals(NavigationBarVisibility.this.mNavigationModeGate)) {
                    NavigationBarVisibility.this.updateNavigationModeState();
                }
            }
        };
        this.mGateListener = r1;
        this.mExceptions = new ArrayList(list);
        CommandQueue commandQueue = (CommandQueue) Dependency.get(CommandQueue.class);
        this.mCommandQueue = commandQueue;
        commandQueue.addCallback((CommandQueue.Callbacks) r0);
        this.mDisplayId = context.getDisplayId();
        this.mAssistManager = (AssistManagerGoogle) Dependency.get(AssistManager.class);
        KeyguardVisibility keyguardVisibility = new KeyguardVisibility(context);
        this.mKeyguardGate = keyguardVisibility;
        keyguardVisibility.setListener(r1);
        NonGesturalNavigation nonGesturalNavigation = new NonGesturalNavigation(context);
        this.mNavigationModeGate = nonGesturalNavigation;
        nonGesturalNavigation.setListener(r1);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        this.mKeyguardGate.activate();
        updateKeyguardState();
        this.mNavigationModeGate.activate();
        updateNavigationModeState();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        this.mNavigationModeGate.deactivate();
        updateNavigationModeState();
        this.mKeyguardGate.deactivate();
        updateKeyguardState();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateKeyguardState() {
        this.mIsKeyguardShowing = this.mKeyguardGate.isKeyguardShowing();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateNavigationModeState() {
        this.mIsNavigationGestural = this.mNavigationModeGate.isNavigationGestural();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public boolean isBlocked() {
        if (this.mIsKeyguardShowing) {
            return false;
        }
        if (this.mIsNavigationGestural && isActiveAssistantNga()) {
            return false;
        }
        for (int i = 0; i < this.mExceptions.size(); i++) {
            if (this.mExceptions.get(i).isAvailable()) {
                return false;
            }
        }
        return this.mIsNavigationHidden;
    }

    @Override // com.google.android.systemui.elmyra.gates.Gate
    public String toString() {
        return super.toString() + " [mIsNavigationHidden -> " + this.mIsNavigationHidden + "; mExceptions -> " + this.mExceptions + "; mIsNavigationGestural -> " + this.mIsNavigationGestural + "; isActiveAssistantNga() -> " + isActiveAssistantNga() + "]";
    }

    private boolean isActiveAssistantNga() {
        return this.mAssistManager.isActiveAssistantNga();
    }
}

package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.util.Log;
import com.google.android.systemui.assist.OpaEnabledListener;

/* access modifiers changed from: package-private */
/* compiled from: LaunchOpa.kt */
public final class LaunchOpa$opaEnabledListener$1 implements OpaEnabledListener {
    final /* synthetic */ LaunchOpa this$0;

    LaunchOpa$opaEnabledListener$1(LaunchOpa launchOpa) {
        this.this$0 = launchOpa;
    }

    @Override // com.google.android.systemui.assist.OpaEnabledListener
    public final void onOpaEnabledReceived(Context context, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5 = false;
        boolean z6 = z2 || (this.this$0.enableForAnyAssistant);
        Log.i("Columbus/LaunchOpa", "eligible: " + z + ", supported: " + z6 + ", opa: " + z3);
        LaunchOpa launchOpa = this.this$0;
        if (z && z6 && z3) {
            z5 = true;
        }
        launchOpa.isOpaEnabled = z5;
        this.this$0.updateAvailable();
    }
}

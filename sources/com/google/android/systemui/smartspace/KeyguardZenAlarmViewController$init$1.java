package com.google.android.systemui.smartspace;

import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeyguardZenAlarmViewController.kt */
public final class KeyguardZenAlarmViewController$init$1 implements View.OnAttachStateChangeListener {
    final /* synthetic */ KeyguardZenAlarmViewController this$0;

    KeyguardZenAlarmViewController$init$1(KeyguardZenAlarmViewController keyguardZenAlarmViewController) {
        this.this$0 = keyguardZenAlarmViewController;
    }

    public void onViewAttachedToWindow(View view) {
        Intrinsics.checkNotNullParameter(view, "v");
        this.this$0.setSmartspaceView((BcSmartspaceDataPlugin.SmartspaceView) view);
        this.this$0.zenModeController.addCallback(this.this$0.zenModeCallback);
        this.this$0.nextAlarmController.addCallback(this.this$0.nextAlarmCallback);
        this.this$0.refresh();
    }

    public void onViewDetachedFromWindow(View view) {
        Intrinsics.checkNotNullParameter(view, "v");
        this.this$0.setSmartspaceView(null);
        this.this$0.zenModeController.removeCallback(this.this$0.zenModeCallback);
        this.this$0.nextAlarmController.removeCallback(this.this$0.nextAlarmCallback);
    }
}

package com.google.android.systemui.smartspace;

import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeyguardMediaViewController.kt */
public final class KeyguardMediaViewController$init$1 implements View.OnAttachStateChangeListener {
    final /* synthetic */ KeyguardMediaViewController this$0;

    KeyguardMediaViewController$init$1(KeyguardMediaViewController keyguardMediaViewController) {
        this.this$0 = keyguardMediaViewController;
    }

    public void onViewAttachedToWindow(View view) {
        Intrinsics.checkNotNullParameter(view, "v");
        this.this$0.setSmartspaceView((BcSmartspaceDataPlugin.SmartspaceView) view);
        this.this$0.mediaManager.addCallback(this.this$0.mediaListener);
    }

    public void onViewDetachedFromWindow(View view) {
        Intrinsics.checkNotNullParameter(view, "v");
        this.this$0.setSmartspaceView(null);
        this.this$0.mediaManager.removeCallback(this.this$0.mediaListener);
    }
}

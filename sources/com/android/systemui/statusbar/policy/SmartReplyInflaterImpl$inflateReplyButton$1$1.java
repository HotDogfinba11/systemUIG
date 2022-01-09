package com.android.systemui.statusbar.policy;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.R$string;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartReplyInflaterImpl$inflateReplyButton$1$1 extends View.AccessibilityDelegate {
    final /* synthetic */ SmartReplyView $parent;

    SmartReplyInflaterImpl$inflateReplyButton$1$1(SmartReplyView smartReplyView) {
        this.$parent = smartReplyView;
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        Intrinsics.checkNotNullParameter(view, "host");
        Intrinsics.checkNotNullParameter(accessibilityNodeInfo, "info");
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, this.$parent.getResources().getString(R$string.accessibility_send_smart_reply)));
    }
}

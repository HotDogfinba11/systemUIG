package com.android.systemui.statusbar.policy;

import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.functions.Function0;

/* access modifiers changed from: package-private */
/* compiled from: SmartReplyStateInflater.kt */
public final class SmartReplyStateInflaterKt$sam$com_android_systemui_plugins_ActivityStarter_OnDismissAction$0 implements ActivityStarter.OnDismissAction {
    private final /* synthetic */ Function0 function;

    SmartReplyStateInflaterKt$sam$com_android_systemui_plugins_ActivityStarter_OnDismissAction$0(Function0 function0) {
        this.function = function0;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final /* synthetic */ boolean onDismiss() {
        return ((Boolean) this.function.invoke()).booleanValue();
    }
}

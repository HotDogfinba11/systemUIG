package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VariableDateViewController.kt */
public final class VariableDateViewController$intentReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ VariableDateViewController this$0;

    VariableDateViewController$intentReceiver$1(VariableDateViewController variableDateViewController) {
        this.this$0 = variableDateViewController;
    }

    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Handler handler = ((VariableDateView) this.this$0.mView).getHandler();
        if (handler != null) {
            String action = intent.getAction();
            if (Intrinsics.areEqual("android.intent.action.TIME_TICK", action) || Intrinsics.areEqual("android.intent.action.TIME_SET", action) || Intrinsics.areEqual("android.intent.action.TIMEZONE_CHANGED", action) || Intrinsics.areEqual("android.intent.action.LOCALE_CHANGED", action)) {
                if (Intrinsics.areEqual("android.intent.action.LOCALE_CHANGED", action) || Intrinsics.areEqual("android.intent.action.TIMEZONE_CHANGED", action)) {
                    handler.post(new VariableDateViewController$intentReceiver$1$onReceive$1(this.this$0));
                }
                handler.post(new VariableDateViewController$intentReceiver$1$onReceive$2(this.this$0));
            }
        }
    }
}

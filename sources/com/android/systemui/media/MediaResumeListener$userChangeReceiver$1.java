package com.android.systemui.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$userChangeReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ MediaResumeListener this$0;

    MediaResumeListener$userChangeReceiver$1(MediaResumeListener mediaResumeListener) {
        this.this$0 = mediaResumeListener;
    }

    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (Intrinsics.areEqual("android.intent.action.USER_UNLOCKED", intent.getAction())) {
            this.this$0.loadMediaResumptionControls();
        } else if (Intrinsics.areEqual("android.intent.action.USER_SWITCHED", intent.getAction())) {
            this.this$0.currentUserId = intent.getIntExtra("android.intent.extra.user_handle", -1);
            this.this$0.loadSavedComponents();
        }
    }
}

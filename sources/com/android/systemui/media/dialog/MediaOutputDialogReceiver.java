package com.android.systemui.media.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaOutputDialogReceiver.kt */
public final class MediaOutputDialogReceiver extends BroadcastReceiver {
    private final MediaOutputDialogFactory mediaOutputDialogFactory;

    public MediaOutputDialogReceiver(MediaOutputDialogFactory mediaOutputDialogFactory2) {
        Intrinsics.checkNotNullParameter(mediaOutputDialogFactory2, "mediaOutputDialogFactory");
        this.mediaOutputDialogFactory = mediaOutputDialogFactory2;
    }

    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (TextUtils.equals("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG", intent.getAction())) {
            String stringExtra = intent.getStringExtra("package_name");
            if (!TextUtils.isEmpty(stringExtra)) {
                MediaOutputDialogFactory mediaOutputDialogFactory2 = this.mediaOutputDialogFactory;
                Intrinsics.checkNotNull(stringExtra);
                mediaOutputDialogFactory2.create(stringExtra, false);
            } else if (MediaOutputDialogReceiverKt.access$getDEBUG$p()) {
                Log.e("MediaOutputDlgReceiver", "Unable to launch media output dialog. Package name is empty.");
            }
        }
    }
}

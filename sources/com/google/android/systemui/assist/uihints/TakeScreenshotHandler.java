package com.google.android.systemui.assist.uihints;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.internal.util.ScreenshotHelper;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;

/* access modifiers changed from: package-private */
public final class TakeScreenshotHandler implements NgaMessageHandler.TakeScreenshotListener {
    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ScreenshotHelper mScreenshotHelper;

    public static /* synthetic */ void $r8$lambda$csP2_MYBsYscfEqGKBTE8f7bLf8(TakeScreenshotHandler takeScreenshotHandler, PendingIntent pendingIntent, Uri uri) {
        takeScreenshotHandler.lambda$onTakeScreenshot$0(pendingIntent, uri);
    }

    TakeScreenshotHandler(Context context) {
        this.mContext = context;
        this.mScreenshotHelper = new ScreenshotHelper(context);
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.TakeScreenshotListener
    public void onTakeScreenshot(PendingIntent pendingIntent) {
        this.mScreenshotHelper.takeScreenshot(1, true, true, this.mHandler, new TakeScreenshotHandler$$ExternalSyntheticLambda0(this, pendingIntent));
    }

    private /* synthetic */ void lambda$onTakeScreenshot$0(PendingIntent pendingIntent, Uri uri) {
        if (pendingIntent != null) {
            try {
                Intent intent = new Intent();
                intent.putExtra("success", uri != null);
                intent.putExtra("uri", uri);
                pendingIntent.send(this.mContext, 0, intent);
            } catch (PendingIntent.CanceledException unused) {
                Log.w("TakeScreenshotHandler", "Intent was cancelled");
            }
        }
    }
}

package com.google.android.systemui.columbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: ColumbusStructuredDataManager.kt */
public final class ColumbusStructuredDataManager$broadcastReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ ColumbusStructuredDataManager this$0;

    ColumbusStructuredDataManager$broadcastReceiver$1(ColumbusStructuredDataManager columbusStructuredDataManager) {
        this.this$0 = columbusStructuredDataManager;
    }

    public void onReceive(Context context, Intent intent) {
        String dataString;
        List list = null;
        if (!(intent == null || (dataString = intent.getDataString()) == null)) {
            list = StringsKt__StringsKt.split$default(dataString, new String[]{":"}, false, 0, 6, null);
        }
        if (list != null) {
            if (list.size() != 2) {
                Log.e("Columbus/StructuredData", Intrinsics.stringPlus("Unexpected package name tokens: ", CollectionsKt___CollectionsKt.joinToString$default(list, ",", null, null, 0, null, null, 62, null)));
                return;
            }
            String str = (String) list.get(1);
            if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false) && this.this$0.allowPackageList.contains(str)) {
                this.this$0.removePackage(str);
            }
        }
    }
}

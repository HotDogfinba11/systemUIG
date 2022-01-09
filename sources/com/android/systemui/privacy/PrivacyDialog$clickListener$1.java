package com.android.systemui.privacy;

import android.view.View;
import com.android.systemui.privacy.PrivacyDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: PrivacyDialog.kt */
final class PrivacyDialog$clickListener$1 implements View.OnClickListener {
    final /* synthetic */ Function2<String, Integer, Unit> $activityStarter;

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Integer, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    PrivacyDialog$clickListener$1(Function2<? super String, ? super Integer, Unit> function2) {
        this.$activityStarter = function2;
    }

    public final void onClick(View view) {
        Object tag = view.getTag();
        if (tag != null) {
            PrivacyDialog.PrivacyElement privacyElement = (PrivacyDialog.PrivacyElement) tag;
            this.$activityStarter.invoke(privacyElement.getPackageName(), Integer.valueOf(privacyElement.getUserId()));
        }
    }
}

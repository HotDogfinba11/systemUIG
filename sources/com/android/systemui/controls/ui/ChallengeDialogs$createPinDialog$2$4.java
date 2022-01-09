package com.android.systemui.controls.ui;

import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.android.systemui.R$id;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ChallengeDialogs.kt */
public final class ChallengeDialogs$createPinDialog$2$4 implements DialogInterface.OnShowListener {
    final /* synthetic */ int $instructions;
    final /* synthetic */ ChallengeDialogs$createPinDialog$1 $this_apply;
    final /* synthetic */ boolean $useAlphaNumeric;

    ChallengeDialogs$createPinDialog$2$4(ChallengeDialogs$createPinDialog$1 challengeDialogs$createPinDialog$1, int i, boolean z) {
        this.$this_apply = challengeDialogs$createPinDialog$1;
        this.$instructions = i;
        this.$useAlphaNumeric = z;
    }

    public final void onShow(DialogInterface dialogInterface) {
        final EditText editText = (EditText) this.$this_apply.requireViewById(R$id.controls_pin_input);
        editText.setHint(this.$instructions);
        ChallengeDialogs$createPinDialog$1 challengeDialogs$createPinDialog$1 = this.$this_apply;
        int i = R$id.controls_pin_use_alpha;
        final CheckBox checkBox = (CheckBox) challengeDialogs$createPinDialog$1.requireViewById(i);
        checkBox.setChecked(this.$useAlphaNumeric);
        ChallengeDialogs challengeDialogs = ChallengeDialogs.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(editText, "editText");
        challengeDialogs.setInputType(editText, checkBox.isChecked());
        ((CheckBox) this.$this_apply.requireViewById(i)).setOnClickListener(new View.OnClickListener() {
            /* class com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$4.AnonymousClass1 */

            public final void onClick(View view) {
                ChallengeDialogs challengeDialogs = ChallengeDialogs.INSTANCE;
                EditText editText = editText;
                Intrinsics.checkNotNullExpressionValue(editText, "editText");
                challengeDialogs.setInputType(editText, checkBox.isChecked());
            }
        });
        editText.requestFocus();
    }
}

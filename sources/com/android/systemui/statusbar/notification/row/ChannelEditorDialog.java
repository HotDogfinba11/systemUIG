package com.android.systemui.statusbar.notification.row;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChannelEditorDialogController.kt */
public final class ChannelEditorDialog extends Dialog {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ChannelEditorDialog(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final void updateDoneButtonText(boolean z) {
        int i;
        TextView textView = (TextView) findViewById(R$id.done_button);
        if (textView != null) {
            if (z) {
                i = R$string.inline_ok_button;
            } else {
                i = R$string.inline_done_button;
            }
            textView.setText(i);
        }
    }

    /* compiled from: ChannelEditorDialogController.kt */
    public static final class Builder {
        private Context context;

        public final Builder setContext(Context context2) {
            Intrinsics.checkNotNullParameter(context2, "context");
            this.context = context2;
            return this;
        }

        public final ChannelEditorDialog build() {
            Context context2 = this.context;
            if (context2 != null) {
                return new ChannelEditorDialog(context2);
            }
            Intrinsics.throwUninitializedPropertyAccessException("context");
            throw null;
        }
    }
}

package com.android.systemui.statusbar.policy;

import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import java.text.FieldPosition;
import java.text.ParsePosition;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VariableDateViewController.kt */
public final class VariableDateViewControllerKt$EMPTY_FORMAT$1 extends DateFormat {
    @Override // android.icu.text.DateFormat
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        Intrinsics.checkNotNullParameter(calendar, "cal");
        Intrinsics.checkNotNullParameter(stringBuffer, "toAppendTo");
        Intrinsics.checkNotNullParameter(fieldPosition, "fieldPosition");
        return null;
    }

    public void parse(String str, Calendar calendar, ParsePosition parsePosition) {
        Intrinsics.checkNotNullParameter(str, "text");
        Intrinsics.checkNotNullParameter(calendar, "cal");
        Intrinsics.checkNotNullParameter(parsePosition, "pos");
    }

    VariableDateViewControllerKt$EMPTY_FORMAT$1() {
    }
}

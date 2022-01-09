package com.android.systemui.statusbar.policy;

import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.text.TextUtils;
import java.util.Date;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VariableDateViewController.kt */
public final class VariableDateViewControllerKt {
    private static final DateFormat EMPTY_FORMAT = new VariableDateViewControllerKt$EMPTY_FORMAT$1();

    public static final String getTextForFormat(Date date, DateFormat dateFormat) {
        Intrinsics.checkNotNullParameter(dateFormat, "format");
        if (dateFormat == EMPTY_FORMAT) {
            return "";
        }
        String format = dateFormat.format(date);
        Intrinsics.checkNotNullExpressionValue(format, "format.format(date)");
        return format;
    }

    public static final DateFormat getFormatFromPattern(String str) {
        if (TextUtils.equals(str, "")) {
            return EMPTY_FORMAT;
        }
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(str, Locale.getDefault());
        instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        Intrinsics.checkNotNullExpressionValue(instanceForSkeleton, "format");
        return instanceForSkeleton;
    }
}

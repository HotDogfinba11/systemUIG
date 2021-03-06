package com.google.android.material.datepicker;

import java.util.Calendar;
import java.util.TimeZone;

/* access modifiers changed from: package-private */
public class TimeSource {
    private static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource(null, null);
    private final Long fixedTimeMs;
    private final TimeZone fixedTimeZone;

    private TimeSource(Long l, TimeZone timeZone) {
        this.fixedTimeMs = l;
        this.fixedTimeZone = timeZone;
    }

    static TimeSource system() {
        return SYSTEM_TIME_SOURCE;
    }

    /* access modifiers changed from: package-private */
    public Calendar now() {
        return now(this.fixedTimeZone);
    }

    /* access modifiers changed from: package-private */
    public Calendar now(TimeZone timeZone) {
        Calendar instance = timeZone == null ? Calendar.getInstance() : Calendar.getInstance(timeZone);
        Long l = this.fixedTimeMs;
        if (l != null) {
            instance.setTimeInMillis(l.longValue());
        }
        return instance;
    }
}

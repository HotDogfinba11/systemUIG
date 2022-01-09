package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.time.SystemClock;
import java.util.Date;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VariableDateViewController.kt */
public final class VariableDateViewController extends ViewController<VariableDateView> {
    private final BroadcastDispatcher broadcastDispatcher;
    private Date currentTime = new Date();
    private DateFormat dateFormat;
    private String datePattern;
    private final BroadcastReceiver intentReceiver = new VariableDateViewController$intentReceiver$1(this);
    private String lastText = "";
    private int lastWidth = Integer.MAX_VALUE;
    private final VariableDateViewController$onMeasureListener$1 onMeasureListener = new VariableDateViewController$onMeasureListener$1(this);
    private final SystemClock systemClock;
    private final Handler timeTickHandler;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VariableDateViewController(SystemClock systemClock2, BroadcastDispatcher broadcastDispatcher2, Handler handler, VariableDateView variableDateView) {
        super(variableDateView);
        Intrinsics.checkNotNullParameter(systemClock2, "systemClock");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(handler, "timeTickHandler");
        Intrinsics.checkNotNullParameter(variableDateView, "view");
        this.systemClock = systemClock2;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.timeTickHandler = handler;
        this.datePattern = variableDateView.getLongerPattern();
    }

    private final void setDatePattern(String str) {
        if (!Intrinsics.areEqual(this.datePattern, str)) {
            this.datePattern = str;
            this.dateFormat = null;
            if (isAttachedToWindow()) {
                post(new VariableDateViewController$datePattern$1(this));
            }
        }
    }

    private final String getLongerPattern() {
        return ((VariableDateView) this.mView).getLongerPattern();
    }

    private final String getShorterPattern() {
        return ((VariableDateView) this.mView).getShorterPattern();
    }

    private final Boolean post(Function0<Unit> function0) {
        Handler handler = ((VariableDateView) this.mView).getHandler();
        if (handler == null) {
            return null;
        }
        return Boolean.valueOf(handler.post(new VariableDateViewControllerKt$sam$java_lang_Runnable$0(function0)));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.broadcastDispatcher.registerReceiver(this.intentReceiver, intentFilter, new HandlerExecutor(this.timeTickHandler), UserHandle.SYSTEM);
        post(new VariableDateViewController$onViewAttached$1(this));
        ((VariableDateView) this.mView).onAttach(this.onMeasureListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.dateFormat = null;
        ((VariableDateView) this.mView).onAttach(null);
        this.broadcastDispatcher.unregisterReceiver(this.intentReceiver);
    }

    /* access modifiers changed from: private */
    public final void updateClock() {
        if (this.dateFormat == null) {
            this.dateFormat = VariableDateViewControllerKt.getFormatFromPattern(this.datePattern);
        }
        this.currentTime.setTime(this.systemClock.currentTimeMillis());
        Date date = this.currentTime;
        DateFormat dateFormat2 = this.dateFormat;
        Intrinsics.checkNotNull(dateFormat2);
        String textForFormat = VariableDateViewControllerKt.getTextForFormat(date, dateFormat2);
        if (!Intrinsics.areEqual(textForFormat, this.lastText)) {
            ((VariableDateView) this.mView).setText(textForFormat);
            this.lastText = textForFormat;
        }
    }

    /* access modifiers changed from: private */
    public final void maybeChangeFormat(int i) {
        if (((VariableDateView) this.mView).getFreezeSwitching()) {
            return;
        }
        if (i > this.lastWidth && Intrinsics.areEqual(this.datePattern, getLongerPattern())) {
            return;
        }
        if (i >= this.lastWidth || !Intrinsics.areEqual(this.datePattern, "")) {
            float f = (float) i;
            if (((VariableDateView) this.mView).getDesiredWidthForText(VariableDateViewControllerKt.getTextForFormat(this.currentTime, VariableDateViewControllerKt.getFormatFromPattern(getLongerPattern()))) <= f) {
                changePattern(getLongerPattern());
                return;
            }
            if (((VariableDateView) this.mView).getDesiredWidthForText(VariableDateViewControllerKt.getTextForFormat(this.currentTime, VariableDateViewControllerKt.getFormatFromPattern(getShorterPattern()))) <= f) {
                changePattern(getShorterPattern());
            } else {
                changePattern("");
            }
        }
    }

    private final void changePattern(String str) {
        if (!str.equals(this.datePattern)) {
            setDatePattern(str);
        }
    }

    /* compiled from: VariableDateViewController.kt */
    public static final class Factory {
        private final BroadcastDispatcher broadcastDispatcher;
        private final Handler handler;
        private final SystemClock systemClock;

        public Factory(SystemClock systemClock2, BroadcastDispatcher broadcastDispatcher2, Handler handler2) {
            Intrinsics.checkNotNullParameter(systemClock2, "systemClock");
            Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
            Intrinsics.checkNotNullParameter(handler2, "handler");
            this.systemClock = systemClock2;
            this.broadcastDispatcher = broadcastDispatcher2;
            this.handler = handler2;
        }

        public final VariableDateViewController create(VariableDateView variableDateView) {
            Intrinsics.checkNotNullParameter(variableDateView, "view");
            return new VariableDateViewController(this.systemClock, this.broadcastDispatcher, this.handler, variableDateView);
        }
    }
}

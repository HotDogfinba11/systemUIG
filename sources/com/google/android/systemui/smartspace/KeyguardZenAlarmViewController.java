package com.google.android.systemui.smartspace;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Handler;
import android.text.format.DateFormat;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* compiled from: KeyguardZenAlarmViewController.kt */
public final class KeyguardZenAlarmViewController {
    public static final Companion Companion = new Companion(null);
    private final Drawable alarmImage;
    private final AlarmManager alarmManager;
    private final Context context;
    private final Drawable dndImage = loadDndImage();
    private final Handler handler;
    private final NextAlarmController.NextAlarmChangeCallback nextAlarmCallback = new KeyguardZenAlarmViewController$nextAlarmCallback$1(this);
    private final NextAlarmController nextAlarmController;
    private final BcSmartspaceDataPlugin plugin;
    private final KFunction<Unit> showNextAlarm = new KeyguardZenAlarmViewController$showNextAlarm$1(this);
    private BcSmartspaceDataPlugin.SmartspaceView smartspaceView;
    private final KeyguardZenAlarmViewController$zenModeCallback$1 zenModeCallback = new KeyguardZenAlarmViewController$zenModeCallback$1(this);
    private final ZenModeController zenModeController;

    @VisibleForTesting
    public static /* synthetic */ void getSmartspaceView$annotations() {
    }

    public KeyguardZenAlarmViewController(Context context2, BcSmartspaceDataPlugin bcSmartspaceDataPlugin, ZenModeController zenModeController2, AlarmManager alarmManager2, NextAlarmController nextAlarmController2, Handler handler2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(bcSmartspaceDataPlugin, "plugin");
        Intrinsics.checkNotNullParameter(zenModeController2, "zenModeController");
        Intrinsics.checkNotNullParameter(alarmManager2, "alarmManager");
        Intrinsics.checkNotNullParameter(nextAlarmController2, "nextAlarmController");
        Intrinsics.checkNotNullParameter(handler2, "handler");
        this.context = context2;
        this.plugin = bcSmartspaceDataPlugin;
        this.zenModeController = zenModeController2;
        this.alarmManager = alarmManager2;
        this.nextAlarmController = nextAlarmController2;
        this.handler = handler2;
        this.alarmImage = context2.getResources().getDrawable(R$drawable.ic_access_alarms_big, null);
    }

    public final void setSmartspaceView(BcSmartspaceDataPlugin.SmartspaceView smartspaceView2) {
        this.smartspaceView = smartspaceView2;
    }

    /* compiled from: KeyguardZenAlarmViewController.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final void init() {
        this.plugin.addOnAttachStateChangeListener(new KeyguardZenAlarmViewController$init$1(this));
        updateNextAlarm();
    }

    /* access modifiers changed from: private */
    public final void refresh() {
        updateDnd();
        updateNextAlarm();
    }

    private final Drawable loadDndImage() {
        Drawable drawable = this.context.getResources().getDrawable(R$drawable.stat_sys_dnd, null);
        Objects.requireNonNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.InsetDrawable");
        Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
        Intrinsics.checkNotNullExpressionValue(drawable2, "withInsets.getDrawable()");
        return drawable2;
    }

    @VisibleForTesting
    public final void updateDnd() {
        if (this.zenModeController.getZen() != 0) {
            String string = this.context.getResources().getString(R$string.accessibility_quick_settings_dnd);
            BcSmartspaceDataPlugin.SmartspaceView smartspaceView2 = this.smartspaceView;
            if (smartspaceView2 != null) {
                smartspaceView2.setDnd(this.dndImage, string);
                return;
            }
            return;
        }
        BcSmartspaceDataPlugin.SmartspaceView smartspaceView3 = this.smartspaceView;
        if (smartspaceView3 != null) {
            smartspaceView3.setDnd(null, null);
        }
    }

    /* access modifiers changed from: private */
    public final void updateNextAlarm() {
        this.alarmManager.cancel(new KeyguardZenAlarmViewController$sam$android_app_AlarmManager_OnAlarmListener$0((Function0) this.showNextAlarm));
        long nextAlarm = this.zenModeController.getNextAlarm();
        if (nextAlarm > 0) {
            long millis = nextAlarm - TimeUnit.HOURS.toMillis(12);
            if (millis > 0) {
                this.alarmManager.setExact(1, millis, "lock_screen_next_alarm", new KeyguardZenAlarmViewController$sam$android_app_AlarmManager_OnAlarmListener$0((Function0) this.showNextAlarm), this.handler);
            }
        }
        showAlarm();
    }

    @VisibleForTesting
    public final void showAlarm() {
        long nextAlarm = this.zenModeController.getNextAlarm();
        if (nextAlarm <= 0 || !withinNHours(nextAlarm, 12)) {
            BcSmartspaceDataPlugin.SmartspaceView smartspaceView2 = this.smartspaceView;
            if (smartspaceView2 != null) {
                smartspaceView2.setNextAlarm(null, null);
                return;
            }
            return;
        }
        String obj = DateFormat.format(DateFormat.is24HourFormat(this.context, ActivityManager.getCurrentUser()) ? "HH:mm" : "h:mm", nextAlarm).toString();
        BcSmartspaceDataPlugin.SmartspaceView smartspaceView3 = this.smartspaceView;
        if (smartspaceView3 != null) {
            smartspaceView3.setNextAlarm(this.alarmImage, obj);
        }
    }

    private final boolean withinNHours(long j, long j2) {
        return j <= System.currentTimeMillis() + TimeUnit.HOURS.toMillis(j2);
    }
}

package com.google.android.systemui.columbus;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.settings.UserTracker;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusSettings.kt */
public class ColumbusSettings {
    private static final Uri COLUMBUS_ACTION_URI;
    private static final Uri COLUMBUS_AP_SENSOR_URI;
    private static final Uri COLUMBUS_ENABLED_URI;
    private static final Uri COLUMBUS_LAUNCH_APP_SHORTCUT_URI;
    private static final Uri COLUMBUS_LAUNCH_APP_URI;
    private static final Uri COLUMBUS_LOW_SENSITIVITY_URI;
    private static final Uri COLUMBUS_SILENCE_ALERTS_URI;
    public static final Companion Companion = new Companion(null);
    private static final Set<Uri> MONITORED_URIS;
    private final String backupPackage;
    private final Function1<Uri, Unit> callback = new ColumbusSettings$callback$1(this);
    private final Set<ColumbusContentObserver> contentObservers;
    private final ContentResolver contentResolver;
    private final Set<ColumbusSettingsChangeListener> listeners = new LinkedHashSet();
    private final UserTracker userTracker;

    /* compiled from: ColumbusSettings.kt */
    public interface ColumbusSettingsChangeListener {

        /* compiled from: ColumbusSettings.kt */
        public static final class DefaultImpls {
            public static void onAlertSilenceEnabledChange(ColumbusSettingsChangeListener columbusSettingsChangeListener, boolean z) {
                Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "this");
            }

            public static void onColumbusEnabledChange(ColumbusSettingsChangeListener columbusSettingsChangeListener, boolean z) {
                Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "this");
            }

            public static void onLowSensitivityChange(ColumbusSettingsChangeListener columbusSettingsChangeListener, boolean z) {
                Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "this");
            }

            public static void onSelectedActionChange(ColumbusSettingsChangeListener columbusSettingsChangeListener, String str) {
                Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "this");
                Intrinsics.checkNotNullParameter(str, "selectedAction");
            }

            public static void onSelectedAppChange(ColumbusSettingsChangeListener columbusSettingsChangeListener, String str) {
                Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "this");
                Intrinsics.checkNotNullParameter(str, "selectedApp");
            }

            public static void onSelectedAppShortcutChange(ColumbusSettingsChangeListener columbusSettingsChangeListener, String str) {
                Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "this");
                Intrinsics.checkNotNullParameter(str, "selectedShortcut");
            }

            public static void onUseApSensorChange(ColumbusSettingsChangeListener columbusSettingsChangeListener, boolean z) {
                Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "this");
            }
        }

        void onAlertSilenceEnabledChange(boolean z);

        void onColumbusEnabledChange(boolean z);

        void onLowSensitivityChange(boolean z);

        void onSelectedActionChange(String str);

        void onSelectedAppChange(String str);

        void onSelectedAppShortcutChange(String str);

        void onUseApSensorChange(boolean z);
    }

    public ColumbusSettings(Context context, UserTracker userTracker2, ColumbusContentObserver.Factory factory) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
        Intrinsics.checkNotNullParameter(factory, "contentObserverFactory");
        this.userTracker = userTracker2;
        this.backupPackage = context.getBasePackageName();
        this.contentResolver = context.getContentResolver();
        Set<Uri> set = MONITORED_URIS;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
        for (T t : set) {
            Intrinsics.checkNotNullExpressionValue(t, "it");
            arrayList.add(factory.create(t, this.callback));
        }
        Set<ColumbusContentObserver> set2 = CollectionsKt___CollectionsKt.toSet(arrayList);
        this.contentObservers = set2;
        Iterator<T> it = set2.iterator();
        while (it.hasNext()) {
            it.next().activate();
        }
    }

    public void registerColumbusSettingsChangeListener(ColumbusSettingsChangeListener columbusSettingsChangeListener) {
        Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "listener");
        this.listeners.add(columbusSettingsChangeListener);
    }

    public void unregisterColumbusSettingsChangeListener(ColumbusSettingsChangeListener columbusSettingsChangeListener) {
        Intrinsics.checkNotNullParameter(columbusSettingsChangeListener, "listener");
        this.listeners.remove(columbusSettingsChangeListener);
    }

    public boolean isColumbusEnabled() {
        if (Settings.Secure.getIntForUser(this.contentResolver, "columbus_enabled", 0, this.userTracker.getUserId()) != 0) {
            return true;
        }
        return false;
    }

    public boolean useApSensor() {
        if (Settings.Secure.getIntForUser(this.contentResolver, "columbus_ap_sensor", 0, this.userTracker.getUserId()) != 0) {
            return true;
        }
        return false;
    }

    public String selectedAction() {
        String stringForUser = Settings.Secure.getStringForUser(this.contentResolver, "columbus_action", this.userTracker.getUserId());
        return stringForUser == null ? "" : stringForUser;
    }

    public String selectedApp() {
        String stringForUser = Settings.Secure.getStringForUser(this.contentResolver, "columbus_launch_app", this.userTracker.getUserId());
        return stringForUser == null ? "" : stringForUser;
    }

    public String selectedAppShortcut() {
        String stringForUser = Settings.Secure.getStringForUser(this.contentResolver, "columbus_launch_app_shortcut", this.userTracker.getUserId());
        return stringForUser == null ? "" : stringForUser;
    }

    public boolean useLowSensitivity() {
        if (Settings.Secure.getIntForUser(this.contentResolver, "columbus_low_sensitivity", 0, this.userTracker.getUserId()) != 0) {
            return true;
        }
        return false;
    }

    public boolean silenceAlertsEnabled() {
        if (Settings.Secure.getIntForUser(this.contentResolver, "columbus_silence_alerts", 1, this.userTracker.getUserId()) != 0) {
            return true;
        }
        return false;
    }

    /* compiled from: ColumbusSettings.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Uri uriFor = Settings.Secure.getUriFor("columbus_enabled");
        COLUMBUS_ENABLED_URI = uriFor;
        Uri uriFor2 = Settings.Secure.getUriFor("columbus_ap_sensor");
        COLUMBUS_AP_SENSOR_URI = uriFor2;
        Uri uriFor3 = Settings.Secure.getUriFor("columbus_action");
        COLUMBUS_ACTION_URI = uriFor3;
        Uri uriFor4 = Settings.Secure.getUriFor("columbus_launch_app");
        COLUMBUS_LAUNCH_APP_URI = uriFor4;
        Uri uriFor5 = Settings.Secure.getUriFor("columbus_launch_app_shortcut");
        COLUMBUS_LAUNCH_APP_SHORTCUT_URI = uriFor5;
        Uri uriFor6 = Settings.Secure.getUriFor("columbus_low_sensitivity");
        COLUMBUS_LOW_SENSITIVITY_URI = uriFor6;
        Uri uriFor7 = Settings.Secure.getUriFor("columbus_silence_alerts");
        COLUMBUS_SILENCE_ALERTS_URI = uriFor7;
        MONITORED_URIS = SetsKt__SetsKt.setOf((Object[]) new Uri[]{uriFor, uriFor2, uriFor3, uriFor4, uriFor5, uriFor6, uriFor7});
    }
}

package com.google.android.systemui.columbus;

import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.settings.UserTracker;
import java.time.DateTimeException;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ColumbusStructuredDataManager.kt */
public class ColumbusStructuredDataManager {
    public static final Companion Companion = new Companion(null);
    private final Set<String> allowPackageList;
    private final Executor bgExecutor;
    private final ColumbusStructuredDataManager$broadcastReceiver$1 broadcastReceiver;
    private final ContentResolver contentResolver;
    private final Object lock = new Object();
    private JSONArray packageStats;
    private final UserTracker userTracker;
    private final ColumbusStructuredDataManager$userTrackerCallback$1 userTrackerCallback;

    public ColumbusStructuredDataManager(Context context, UserTracker userTracker2, Executor executor) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
        Intrinsics.checkNotNullParameter(executor, "bgExecutor");
        this.userTracker = userTracker2;
        this.bgExecutor = executor;
        this.contentResolver = context.getContentResolver();
        String[] stringArray = context.getResources().getStringArray(ColumbusResourceHelper.SUMATRA_ALLOW_LIST);
        Intrinsics.checkNotNullExpressionValue(stringArray, "context.resources.getStringArray(ColumbusResourceHelper.SUMATRA_ALLOW_LIST)");
        String[] strArr = new String[stringArray.length];
        System.arraycopy(stringArray, 0, strArr, 0, stringArray.length);
        this.allowPackageList = SetsKt__SetsKt.setOf((Object[]) strArr);
        ColumbusStructuredDataManager$userTrackerCallback$1 columbusStructuredDataManager$userTrackerCallback$1 = new ColumbusStructuredDataManager$userTrackerCallback$1(this);
        this.userTrackerCallback = columbusStructuredDataManager$userTrackerCallback$1;
        ColumbusStructuredDataManager$broadcastReceiver$1 columbusStructuredDataManager$broadcastReceiver$1 = new ColumbusStructuredDataManager$broadcastReceiver$1(this);
        this.broadcastReceiver = columbusStructuredDataManager$broadcastReceiver$1;
        this.packageStats = fetchPackageStats();
        userTracker2.addCallback(columbusStructuredDataManager$userTrackerCallback$1, executor);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(columbusStructuredDataManager$broadcastReceiver$1, intentFilter);
    }

    public final int getPackageShownCount(String str) {
        Intrinsics.checkNotNullParameter(str, "packageName");
        synchronized (this.lock) {
            int length = this.packageStats.length();
            if (length > 0) {
                int i = 0;
                while (true) {
                    int i2 = i + 1;
                    JSONObject jSONObject = this.packageStats.getJSONObject(i);
                    if (Intrinsics.areEqual(str, jSONObject.getString("packageName"))) {
                        return jSONObject.getInt("shownCount");
                    } else if (i2 >= length) {
                        break;
                    } else {
                        i = i2;
                    }
                }
            }
            return 0;
        }
    }

    public final void incrementPackageShownCount(String str) {
        Intrinsics.checkNotNullParameter(str, "packageName");
        synchronized (this.lock) {
            int i = 0;
            int length = this.packageStats.length();
            if (length > 0) {
                while (true) {
                    int i2 = i + 1;
                    JSONObject jSONObject = this.packageStats.getJSONObject(i);
                    if (Intrinsics.areEqual(str, jSONObject.getString("packageName"))) {
                        jSONObject.put("shownCount", jSONObject.getInt("shownCount") + 1);
                        this.packageStats.put(i, jSONObject);
                        storePackageStats();
                        return;
                    } else if (i2 >= length) {
                        break;
                    } else {
                        i = i2;
                    }
                }
            }
            this.packageStats.put(makeJSONObject$default(this, str, 1, 0, 4, null));
            storePackageStats();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final long getTimeSinceLastDeny(String str) {
        long currentTimestamp;
        Intrinsics.checkNotNullParameter(str, "packageName");
        synchronized (this.lock) {
            currentTimestamp = getCurrentTimestamp() - getLastDenyTimestamp(str);
        }
        return currentTimestamp;
    }

    private final long getLastDenyTimestamp(String str) {
        synchronized (this.lock) {
            int i = 0;
            int length = this.packageStats.length();
            if (length > 0) {
                while (true) {
                    int i2 = i + 1;
                    JSONObject jSONObject = this.packageStats.getJSONObject(i);
                    if (Intrinsics.areEqual(str, jSONObject.getString("packageName"))) {
                        return jSONObject.getLong("lastDeny");
                    } else if (i2 >= length) {
                        break;
                    } else {
                        i = i2;
                    }
                }
            }
            return 0;
        }
    }

    public final void setLastDenyTimeToNow(String str) {
        Intrinsics.checkNotNullParameter(str, "packageName");
        synchronized (this.lock) {
            long currentTimestamp = getCurrentTimestamp();
            int i = 0;
            int length = this.packageStats.length();
            if (length > 0) {
                while (true) {
                    int i2 = i + 1;
                    JSONObject jSONObject = this.packageStats.getJSONObject(i);
                    if (Intrinsics.areEqual(str, jSONObject.getString("packageName"))) {
                        jSONObject.put("lastDeny", currentTimestamp);
                        this.packageStats.put(i, jSONObject);
                        storePackageStats();
                        return;
                    } else if (i2 >= length) {
                        break;
                    } else {
                        i = i2;
                    }
                }
            }
            this.packageStats.put(makeJSONObject$default(this, str, 0, currentTimestamp, 2, null));
            storePackageStats();
            Unit unit = Unit.INSTANCE;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void removePackage(String str) {
        synchronized (this.lock) {
            int i = 0;
            int length = this.packageStats.length();
            if (length > 0) {
                while (true) {
                    int i2 = i + 1;
                    if (Intrinsics.areEqual(str, this.packageStats.getJSONObject(i).getString("packageName"))) {
                        this.packageStats.remove(i);
                        storePackageStats();
                        return;
                    } else if (i2 >= length) {
                        break;
                    } else {
                        i = i2;
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* access modifiers changed from: private */
    public final JSONArray fetchPackageStats() {
        JSONArray jSONArray;
        synchronized (this.lock) {
            String stringForUser = Settings.Secure.getStringForUser(this.contentResolver, "columbus_package_stats", this.userTracker.getUserId());
            if (stringForUser == null) {
                stringForUser = "[]";
            }
            try {
                jSONArray = new JSONArray(stringForUser);
            } catch (JSONException e) {
                Log.e("Columbus/StructuredData", "Failed to parse package counts", e);
                jSONArray = new JSONArray();
            }
        }
        return jSONArray;
    }

    private final void storePackageStats() {
        synchronized (this.lock) {
            Settings.Secure.putStringForUser(this.contentResolver, "columbus_package_stats", this.packageStats.toString(), this.userTracker.getUserId());
        }
    }

    private final long getCurrentTimestamp() {
        try {
            return SystemClock.currentNetworkTimeMillis();
        } catch (DateTimeException unused) {
            return System.currentTimeMillis();
        }
    }

    static /* synthetic */ JSONObject makeJSONObject$default(ColumbusStructuredDataManager columbusStructuredDataManager, String str, int i, long j, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                i = 0;
            }
            if ((i2 & 4) != 0) {
                j = 0;
            }
            return columbusStructuredDataManager.makeJSONObject(str, i, j);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: makeJSONObject");
    }

    private final JSONObject makeJSONObject(String str, int i, long j) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("packageName", str);
        jSONObject.put("shownCount", i);
        jSONObject.put("lastDeny", j);
        return jSONObject;
    }

    /* compiled from: ColumbusStructuredDataManager.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

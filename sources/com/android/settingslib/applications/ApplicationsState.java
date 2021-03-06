package com.android.settingslib.applications;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.Application;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.format.Formatter;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.SparseArray;
import androidx.lifecycle.LifecycleObserver;
import com.android.internal.util.ArrayUtils;
import com.android.settingslib.R$string;
import com.android.settingslib.Utils;
import com.android.settingslib.utils.ThreadUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class ApplicationsState {
    public static final Comparator<AppEntry> ALPHA_COMPARATOR = new Comparator<AppEntry>() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass1 */
        private final Collator sCollator = Collator.getInstance();

        public int compare(AppEntry appEntry, AppEntry appEntry2) {
            ApplicationInfo applicationInfo;
            int compare;
            int compare2 = this.sCollator.compare(appEntry.label, appEntry2.label);
            if (compare2 != 0) {
                return compare2;
            }
            ApplicationInfo applicationInfo2 = appEntry.info;
            if (applicationInfo2 == null || (applicationInfo = appEntry2.info) == null || (compare = this.sCollator.compare(applicationInfo2.packageName, applicationInfo.packageName)) == 0) {
                return appEntry.info.uid - appEntry2.info.uid;
            }
            return compare;
        }
    };
    public static final Comparator<AppEntry> EXTERNAL_SIZE_COMPARATOR = new Comparator<AppEntry>() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass4 */

        public int compare(AppEntry appEntry, AppEntry appEntry2) {
            long j = appEntry.externalSize;
            long j2 = appEntry2.externalSize;
            if (j < j2) {
                return 1;
            }
            if (j > j2) {
                return -1;
            }
            return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
        }
    };
    public static final AppFilter FILTER_ALL_ENABLED = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass13 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            ApplicationInfo applicationInfo = appEntry.info;
            return applicationInfo.enabled && !AppUtils.isInstant(applicationInfo);
        }
    };
    public static final AppFilter FILTER_APPS_EXCEPT_GAMES = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass22 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean filterApp;
            synchronized (appEntry) {
                filterApp = ApplicationsState.FILTER_GAMES.filterApp(appEntry);
            }
            return !filterApp;
        }
    };
    public static final AppFilter FILTER_AUDIO = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass18 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry) {
                z = true;
                if (appEntry.info.category != 1) {
                    z = false;
                }
            }
            return z;
        }
    };
    public static final AppFilter FILTER_DISABLED = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass11 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            ApplicationInfo applicationInfo = appEntry.info;
            return !applicationInfo.enabled && !AppUtils.isInstant(applicationInfo);
        }
    };
    public static final AppFilter FILTER_DOWNLOADED_AND_LAUNCHER = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass8 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            if (AppUtils.isInstant(appEntry.info)) {
                return false;
            }
            if (ApplicationsState.hasFlag(appEntry.info.flags, 128) || !ApplicationsState.hasFlag(appEntry.info.flags, 1) || appEntry.hasLauncherEntry) {
                return true;
            }
            if (!ApplicationsState.hasFlag(appEntry.info.flags, 1) || !appEntry.isHomeApp) {
                return false;
            }
            return true;
        }
    };
    public static final AppFilter FILTER_DOWNLOADED_AND_LAUNCHER_AND_INSTANT = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass9 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return AppUtils.isInstant(appEntry.info) || ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER.filterApp(appEntry);
        }
    };
    public static final AppFilter FILTER_EVERYTHING = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass14 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return true;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }
    };
    public static final AppFilter FILTER_GAMES = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass17 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry.info) {
                if (!ApplicationsState.hasFlag(appEntry.info.flags, 33554432)) {
                    if (appEntry.info.category != 0) {
                        z = false;
                    }
                }
                z = true;
            }
            return z;
        }
    };
    public static final AppFilter FILTER_INSTANT = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass12 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return AppUtils.isInstant(appEntry.info);
        }
    };
    public static final AppFilter FILTER_MOVIES = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass19 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry) {
                z = appEntry.info.category == 2;
            }
            return z;
        }
    };
    public static final AppFilter FILTER_NOT_HIDE = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass16 */
        private String[] mHidePackageNames;

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init(Context context) {
            this.mHidePackageNames = context.getResources().getStringArray(17236047);
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            if (!ArrayUtils.contains(this.mHidePackageNames, appEntry.info.packageName)) {
                return true;
            }
            ApplicationInfo applicationInfo = appEntry.info;
            if (applicationInfo.enabled && applicationInfo.enabledSetting != 4) {
                return true;
            }
            return false;
        }
    };
    public static final AppFilter FILTER_OTHER_APPS = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass21 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry) {
                if (!ApplicationsState.FILTER_AUDIO.filterApp(appEntry) && !ApplicationsState.FILTER_GAMES.filterApp(appEntry) && !ApplicationsState.FILTER_MOVIES.filterApp(appEntry)) {
                    if (!ApplicationsState.FILTER_PHOTOS.filterApp(appEntry)) {
                        z = false;
                    }
                }
                z = true;
            }
            return !z;
        }
    };
    public static final AppFilter FILTER_PERSONAL = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass5 */
        private int mCurrentUser;

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
            this.mCurrentUser = ActivityManager.getCurrentUser();
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return UserHandle.getUserId(appEntry.info.uid) == this.mCurrentUser;
        }
    };
    public static final AppFilter FILTER_PHOTOS = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass20 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            boolean z;
            synchronized (appEntry) {
                z = appEntry.info.category == 3;
            }
            return z;
        }
    };
    public static final AppFilter FILTER_THIRD_PARTY = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass10 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            if (!ApplicationsState.hasFlag(appEntry.info.flags, 128) && ApplicationsState.hasFlag(appEntry.info.flags, 1)) {
                return false;
            }
            return true;
        }
    };
    public static final AppFilter FILTER_WITHOUT_DISABLED_UNTIL_USED = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass6 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return appEntry.info.enabledSetting != 4;
        }
    };
    public static final AppFilter FILTER_WITH_DOMAIN_URLS = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass15 */

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return !AppUtils.isInstant(appEntry.info) && ApplicationsState.hasFlag(appEntry.info.privateFlags, 16);
        }
    };
    public static final AppFilter FILTER_WORK = new AppFilter() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass7 */
        private int mCurrentUser;

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public void init() {
            this.mCurrentUser = ActivityManager.getCurrentUser();
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public boolean filterApp(AppEntry appEntry) {
            return UserHandle.getUserId(appEntry.info.uid) != this.mCurrentUser;
        }
    };
    public static final Comparator<AppEntry> INTERNAL_SIZE_COMPARATOR = new Comparator<AppEntry>() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass3 */

        public int compare(AppEntry appEntry, AppEntry appEntry2) {
            long j = appEntry.internalSize;
            long j2 = appEntry2.internalSize;
            if (j < j2) {
                return 1;
            }
            if (j > j2) {
                return -1;
            }
            return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
        }
    };
    private static final Pattern REMOVE_DIACRITICALS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    public static final Comparator<AppEntry> SIZE_COMPARATOR = new Comparator<AppEntry>() {
        /* class com.android.settingslib.applications.ApplicationsState.AnonymousClass2 */

        public int compare(AppEntry appEntry, AppEntry appEntry2) {
            long j = appEntry.size;
            long j2 = appEntry2.size;
            if (j < j2) {
                return 1;
            }
            if (j > j2) {
                return -1;
            }
            return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
        }
    };
    static ApplicationsState sInstance;
    private static final Object sLock = new Object();
    final ArrayList<WeakReference<Session>> mActiveSessions = new ArrayList<>();
    final int mAdminRetrieveFlags;
    final ArrayList<AppEntry> mAppEntries = new ArrayList<>();
    List<ApplicationInfo> mApplications = new ArrayList();
    final BackgroundHandler mBackgroundHandler;
    final Context mContext;
    String mCurComputingSizePkg;
    int mCurComputingSizeUserId;
    UUID mCurComputingSizeUuid;
    long mCurId = 1;
    final IconDrawableFactory mDrawableFactory;
    final SparseArray<HashMap<String, AppEntry>> mEntriesMap = new SparseArray<>();
    boolean mHaveDisabledApps;
    boolean mHaveInstantApps;
    private InterestingConfigChanges mInterestingConfigChanges = new InterestingConfigChanges();
    final IPackageManager mIpm;
    final MainHandler mMainHandler = new MainHandler(Looper.getMainLooper());
    PackageIntentReceiver mPackageIntentReceiver;
    final PackageManager mPm;
    final ArrayList<Session> mRebuildingSessions = new ArrayList<>();
    boolean mResumed;
    final int mRetrieveFlags;
    final ArrayList<Session> mSessions = new ArrayList<>();
    boolean mSessionsChanged;
    final StorageStatsManager mStats;
    final HashMap<String, Boolean> mSystemModules = new HashMap<>();
    final HandlerThread mThread;
    final UserManager mUm;

    public interface Callbacks {
        void onAllSizesComputed();

        void onLauncherInfoChanged();

        void onLoadEntriesCompleted();

        void onPackageIconChanged();

        void onPackageListChanged();

        void onPackageSizeChanged(String str);

        void onRebuildComplete(ArrayList<AppEntry> arrayList);

        void onRunningStateChanged(boolean z);
    }

    public static class SizeInfo {
        public long cacheSize;
        public long codeSize;
        public long dataSize;
        public long externalCacheSize;
        public long externalCodeSize;
        public long externalDataSize;
    }

    public static boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public static ApplicationsState getInstance(Application application) {
        return getInstance(application, AppGlobals.getPackageManager());
    }

    static ApplicationsState getInstance(Application application, IPackageManager iPackageManager) {
        ApplicationsState applicationsState;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new ApplicationsState(application, iPackageManager);
            }
            applicationsState = sInstance;
        }
        return applicationsState;
    }

    public void setInterestingConfigChanges(InterestingConfigChanges interestingConfigChanges) {
        this.mInterestingConfigChanges = interestingConfigChanges;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x00de */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private ApplicationsState(android.app.Application r8, android.content.pm.IPackageManager r9) {
        /*
        // Method dump skipped, instructions count: 226
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.applications.ApplicationsState.<init>(android.app.Application, android.content.pm.IPackageManager):void");
    }

    public void doResumeIfNeededLocked() {
        if (!this.mResumed) {
            this.mResumed = true;
            if (this.mPackageIntentReceiver == null) {
                PackageIntentReceiver packageIntentReceiver = new PackageIntentReceiver();
                this.mPackageIntentReceiver = packageIntentReceiver;
                packageIntentReceiver.registerReceiver();
            }
            List<ApplicationInfo> list = this.mApplications;
            this.mApplications = new ArrayList();
            for (UserInfo userInfo : this.mUm.getProfiles(UserHandle.myUserId())) {
                try {
                    if (this.mEntriesMap.indexOfKey(userInfo.id) < 0) {
                        this.mEntriesMap.put(userInfo.id, new HashMap<>());
                    }
                    this.mApplications.addAll(this.mIpm.getInstalledApplications(userInfo.isAdmin() ? this.mAdminRetrieveFlags : this.mRetrieveFlags, userInfo.id).getList());
                } catch (Exception e) {
                    Log.e("ApplicationsState", "Error during doResumeIfNeededLocked", e);
                }
            }
            int i = 0;
            if (this.mInterestingConfigChanges.applyNewConfig(this.mContext.getResources())) {
                clearEntries();
            } else {
                for (int i2 = 0; i2 < this.mAppEntries.size(); i2++) {
                    this.mAppEntries.get(i2).sizeStale = true;
                }
            }
            this.mHaveDisabledApps = false;
            this.mHaveInstantApps = false;
            while (i < this.mApplications.size()) {
                ApplicationInfo applicationInfo = this.mApplications.get(i);
                if (!applicationInfo.enabled) {
                    if (applicationInfo.enabledSetting != 3) {
                        this.mApplications.remove(i);
                        i--;
                        i++;
                    } else {
                        this.mHaveDisabledApps = true;
                    }
                }
                if (isHiddenModule(applicationInfo.packageName)) {
                    this.mApplications.remove(i);
                    i--;
                } else {
                    if (!this.mHaveInstantApps && AppUtils.isInstant(applicationInfo)) {
                        this.mHaveInstantApps = true;
                    }
                    AppEntry appEntry = this.mEntriesMap.get(UserHandle.getUserId(applicationInfo.uid)).get(applicationInfo.packageName);
                    if (appEntry != null) {
                        appEntry.info = applicationInfo;
                    }
                }
                i++;
            }
            if (anyAppIsRemoved(list, this.mApplications)) {
                clearEntries();
            }
            this.mCurComputingSizePkg = null;
            if (!this.mBackgroundHandler.hasMessages(2)) {
                this.mBackgroundHandler.sendEmptyMessage(2);
            }
        }
    }

    private static boolean anyAppIsRemoved(List<ApplicationInfo> list, List<ApplicationInfo> list2) {
        HashSet hashSet;
        if (list.size() == 0) {
            return false;
        }
        if (list2.size() < list.size()) {
            return true;
        }
        HashMap hashMap = new HashMap();
        for (ApplicationInfo applicationInfo : list2) {
            String valueOf = String.valueOf(UserHandle.getUserId(applicationInfo.uid));
            HashSet hashSet2 = (HashSet) hashMap.get(valueOf);
            if (hashSet2 == null) {
                hashSet2 = new HashSet();
                hashMap.put(valueOf, hashSet2);
            }
            if (hasFlag(applicationInfo.flags, 8388608)) {
                hashSet2.add(applicationInfo.packageName);
            }
        }
        for (ApplicationInfo applicationInfo2 : list) {
            if (hasFlag(applicationInfo2.flags, 8388608) && ((hashSet = (HashSet) hashMap.get(String.valueOf(UserHandle.getUserId(applicationInfo2.uid)))) == null || !hashSet.remove(applicationInfo2.packageName))) {
                return true;
            }
        }
        return false;
    }

    public void clearEntries() {
        for (int i = 0; i < this.mEntriesMap.size(); i++) {
            this.mEntriesMap.valueAt(i).clear();
        }
        this.mAppEntries.clear();
    }

    public boolean isHiddenModule(String str) {
        Boolean bool = this.mSystemModules.get(str);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public void doPauseIfNeededLocked() {
        if (this.mResumed) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                if (this.mSessions.get(i).mResumed) {
                    return;
                }
            }
            doPauseLocked();
        }
    }

    public void doPauseLocked() {
        this.mResumed = false;
        PackageIntentReceiver packageIntentReceiver = this.mPackageIntentReceiver;
        if (packageIntentReceiver != null) {
            packageIntentReceiver.unregisterReceiver();
            this.mPackageIntentReceiver = null;
        }
    }

    public AppEntry getEntry(String str, int i) {
        AppEntry appEntry;
        synchronized (this.mEntriesMap) {
            appEntry = this.mEntriesMap.get(i).get(str);
            if (appEntry == null) {
                ApplicationInfo appInfoLocked = getAppInfoLocked(str, i);
                if (appInfoLocked == null) {
                    try {
                        appInfoLocked = this.mIpm.getApplicationInfo(str, 0, i);
                    } catch (RemoteException e) {
                        Log.w("ApplicationsState", "getEntry couldn't reach PackageManager", e);
                        return null;
                    }
                }
                if (appInfoLocked != null) {
                    appEntry = getEntryLocked(appInfoLocked);
                }
            }
        }
        return appEntry;
    }

    private ApplicationInfo getAppInfoLocked(String str, int i) {
        for (int i2 = 0; i2 < this.mApplications.size(); i2++) {
            ApplicationInfo applicationInfo = this.mApplications.get(i2);
            if (str.equals(applicationInfo.packageName) && i == UserHandle.getUserId(applicationInfo.uid)) {
                return applicationInfo;
            }
        }
        return null;
    }

    public int indexOfApplicationInfoLocked(String str, int i) {
        for (int size = this.mApplications.size() - 1; size >= 0; size--) {
            ApplicationInfo applicationInfo = this.mApplications.get(size);
            if (applicationInfo.packageName.equals(str) && UserHandle.getUserId(applicationInfo.uid) == i) {
                return size;
            }
        }
        return -1;
    }

    public void addPackage(String str, int i) {
        try {
            synchronized (this.mEntriesMap) {
                if (this.mResumed) {
                    if (indexOfApplicationInfoLocked(str, i) < 0) {
                        ApplicationInfo applicationInfo = this.mIpm.getApplicationInfo(str, this.mUm.isUserAdmin(i) ? this.mAdminRetrieveFlags : this.mRetrieveFlags, i);
                        if (applicationInfo != null) {
                            if (!applicationInfo.enabled) {
                                if (applicationInfo.enabledSetting == 3) {
                                    this.mHaveDisabledApps = true;
                                } else {
                                    return;
                                }
                            }
                            if (AppUtils.isInstant(applicationInfo)) {
                                this.mHaveInstantApps = true;
                            }
                            this.mApplications.add(applicationInfo);
                            if (!this.mBackgroundHandler.hasMessages(2)) {
                                this.mBackgroundHandler.sendEmptyMessage(2);
                            }
                            if (!this.mMainHandler.hasMessages(2)) {
                                this.mMainHandler.sendEmptyMessage(2);
                            }
                        }
                    }
                }
            }
        } catch (RemoteException unused) {
        }
    }

    public void removePackage(String str, int i) {
        synchronized (this.mEntriesMap) {
            int indexOfApplicationInfoLocked = indexOfApplicationInfoLocked(str, i);
            if (indexOfApplicationInfoLocked >= 0) {
                AppEntry appEntry = this.mEntriesMap.get(i).get(str);
                if (appEntry != null) {
                    this.mEntriesMap.get(i).remove(str);
                    this.mAppEntries.remove(appEntry);
                }
                ApplicationInfo applicationInfo = this.mApplications.get(indexOfApplicationInfoLocked);
                this.mApplications.remove(indexOfApplicationInfoLocked);
                if (!applicationInfo.enabled) {
                    this.mHaveDisabledApps = false;
                    Iterator<ApplicationInfo> it = this.mApplications.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!it.next().enabled) {
                                this.mHaveDisabledApps = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (AppUtils.isInstant(applicationInfo)) {
                    this.mHaveInstantApps = false;
                    Iterator<ApplicationInfo> it2 = this.mApplications.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (AppUtils.isInstant(it2.next())) {
                                this.mHaveInstantApps = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (!this.mMainHandler.hasMessages(2)) {
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    public void invalidatePackage(String str, int i) {
        removePackage(str, i);
        addPackage(str, i);
    }

    private void addUser(int i) {
        if (ArrayUtils.contains(this.mUm.getProfileIdsWithDisabled(UserHandle.myUserId()), i)) {
            synchronized (this.mEntriesMap) {
                this.mEntriesMap.put(i, new HashMap<>());
                if (this.mResumed) {
                    doPauseLocked();
                    doResumeIfNeededLocked();
                }
                if (!this.mMainHandler.hasMessages(2)) {
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    private void removeUser(int i) {
        synchronized (this.mEntriesMap) {
            HashMap<String, AppEntry> hashMap = this.mEntriesMap.get(i);
            if (hashMap != null) {
                for (AppEntry appEntry : hashMap.values()) {
                    this.mAppEntries.remove(appEntry);
                    this.mApplications.remove(appEntry.info);
                }
                this.mEntriesMap.remove(i);
                if (!this.mMainHandler.hasMessages(2)) {
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    private AppEntry getEntryLocked(ApplicationInfo applicationInfo) {
        int userId = UserHandle.getUserId(applicationInfo.uid);
        AppEntry appEntry = this.mEntriesMap.get(userId).get(applicationInfo.packageName);
        if (appEntry == null) {
            if (isHiddenModule(applicationInfo.packageName)) {
                return null;
            }
            Context context = this.mContext;
            long j = this.mCurId;
            this.mCurId = 1 + j;
            AppEntry appEntry2 = new AppEntry(context, applicationInfo, j);
            this.mEntriesMap.get(userId).put(applicationInfo.packageName, appEntry2);
            this.mAppEntries.add(appEntry2);
            return appEntry2;
        } else if (appEntry.info == applicationInfo) {
            return appEntry;
        } else {
            appEntry.info = applicationInfo;
            return appEntry;
        }
    }

    private long getTotalInternalSize(PackageStats packageStats) {
        if (packageStats != null) {
            return (packageStats.codeSize + packageStats.dataSize) - packageStats.cacheSize;
        }
        return -2;
    }

    private long getTotalExternalSize(PackageStats packageStats) {
        if (packageStats != null) {
            return packageStats.externalCodeSize + packageStats.externalDataSize + packageStats.externalCacheSize + packageStats.externalMediaSize + packageStats.externalObbSize;
        }
        return -2;
    }

    private String getSizeStr(long j) {
        if (j >= 0) {
            return Formatter.formatFileSize(this.mContext, j);
        }
        return null;
    }

    public void rebuildActiveSessions() {
        synchronized (this.mEntriesMap) {
            if (this.mSessionsChanged) {
                this.mActiveSessions.clear();
                for (int i = 0; i < this.mSessions.size(); i++) {
                    Session session = this.mSessions.get(i);
                    if (session.mResumed) {
                        this.mActiveSessions.add(new WeakReference<>(session));
                    }
                }
            }
        }
    }

    public class Session implements LifecycleObserver {
        final Callbacks mCallbacks;
        private int mFlags;
        private final boolean mHasLifecycle;
        ArrayList<AppEntry> mLastAppList;
        boolean mRebuildAsync;
        Comparator<AppEntry> mRebuildComparator;
        AppFilter mRebuildFilter;
        boolean mRebuildForeground;
        boolean mRebuildRequested;
        ArrayList<AppEntry> mRebuildResult;
        final Object mRebuildSync;
        boolean mResumed;
        final /* synthetic */ ApplicationsState this$0;

        public void onResume() {
            synchronized (this.this$0.mEntriesMap) {
                if (!this.mResumed) {
                    this.mResumed = true;
                    ApplicationsState applicationsState = this.this$0;
                    applicationsState.mSessionsChanged = true;
                    applicationsState.doPauseLocked();
                    this.this$0.doResumeIfNeededLocked();
                }
            }
        }

        public void onPause() {
            synchronized (this.this$0.mEntriesMap) {
                if (this.mResumed) {
                    this.mResumed = false;
                    ApplicationsState applicationsState = this.this$0;
                    applicationsState.mSessionsChanged = true;
                    applicationsState.mBackgroundHandler.removeMessages(1, this);
                    this.this$0.doPauseIfNeededLocked();
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0025, code lost:
            if (r1 == null) goto L_0x002e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
            r1.init(r7.this$0.mContext);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
            r3 = r7.this$0.mEntriesMap;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0032, code lost:
            monitor-enter(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r0 = new java.util.ArrayList(r7.this$0.mAppEntries);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
            monitor-exit(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x003d, code lost:
            r3 = new java.util.ArrayList<>();
            r0 = r0.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
            if (r0.hasNext() == false) goto L_0x0072;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
            r4 = (com.android.settingslib.applications.ApplicationsState.AppEntry) r0.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0052, code lost:
            if (r4 == null) goto L_0x0046;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
            if (r1 == null) goto L_0x005c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x005a, code lost:
            if (r1.filterApp(r4) == false) goto L_0x0046;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x005c, code lost:
            r5 = r7.this$0.mEntriesMap;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0060, code lost:
            monitor-enter(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0061, code lost:
            if (r2 == null) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
            r4.ensureLabel(r7.this$0.mContext);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x006a, code lost:
            r3.add(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x006d, code lost:
            monitor-exit(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0072, code lost:
            if (r2 == null) goto L_0x0081;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0074, code lost:
            r0 = r7.this$0.mEntriesMap;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0078, code lost:
            monitor-enter(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
            java.util.Collections.sort(r3, r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x007c, code lost:
            monitor-exit(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x0081, code lost:
            r0 = r7.mRebuildSync;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0083, code lost:
            monitor-enter(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x0086, code lost:
            if (r7.mRebuildRequested != false) goto L_0x00b0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x0088, code lost:
            r7.mLastAppList = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x008c, code lost:
            if (r7.mRebuildAsync != false) goto L_0x0096;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x008e, code lost:
            r7.mRebuildResult = r3;
            r7.mRebuildSync.notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x009f, code lost:
            if (r7.this$0.mMainHandler.hasMessages(1, r7) != false) goto L_0x00b0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x00a1, code lost:
            r7.this$0.mMainHandler.sendMessage(r7.this$0.mMainHandler.obtainMessage(1, r7));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x00b0, code lost:
            monitor-exit(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x00b1, code lost:
            android.os.Process.setThreadPriority(10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x00b6, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleRebuildList() {
            /*
            // Method dump skipped, instructions count: 192
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.applications.ApplicationsState.Session.handleRebuildList():void");
        }

        public void onDestroy() {
            if (!this.mHasLifecycle) {
                onPause();
            }
            synchronized (this.this$0.mEntriesMap) {
                this.this$0.mSessions.remove(this);
            }
        }
    }

    public class MainHandler extends Handler {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public MainHandler(Looper looper) {
            super(looper);
            ApplicationsState.this = r1;
        }

        public void handleMessage(Message message) {
            ApplicationsState.this.rebuildActiveSessions();
            switch (message.what) {
                case 1:
                    Session session = (Session) message.obj;
                    Iterator<WeakReference<Session>> it = ApplicationsState.this.mActiveSessions.iterator();
                    while (it.hasNext()) {
                        Session session2 = it.next().get();
                        if (session2 != null && session2 == session) {
                            session.mCallbacks.onRebuildComplete(session.mLastAppList);
                        }
                    }
                    return;
                case 2:
                    Iterator<WeakReference<Session>> it2 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it2.hasNext()) {
                        Session session3 = it2.next().get();
                        if (session3 != null) {
                            session3.mCallbacks.onPackageListChanged();
                        }
                    }
                    return;
                case 3:
                    Iterator<WeakReference<Session>> it3 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it3.hasNext()) {
                        Session session4 = it3.next().get();
                        if (session4 != null) {
                            session4.mCallbacks.onPackageIconChanged();
                        }
                    }
                    return;
                case 4:
                    Iterator<WeakReference<Session>> it4 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it4.hasNext()) {
                        Session session5 = it4.next().get();
                        if (session5 != null) {
                            session5.mCallbacks.onPackageSizeChanged((String) message.obj);
                        }
                    }
                    return;
                case 5:
                    Iterator<WeakReference<Session>> it5 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it5.hasNext()) {
                        Session session6 = it5.next().get();
                        if (session6 != null) {
                            session6.mCallbacks.onAllSizesComputed();
                        }
                    }
                    return;
                case 6:
                    Iterator<WeakReference<Session>> it6 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it6.hasNext()) {
                        Session session7 = it6.next().get();
                        if (session7 != null) {
                            session7.mCallbacks.onRunningStateChanged(message.arg1 != 0);
                        }
                    }
                    return;
                case 7:
                    Iterator<WeakReference<Session>> it7 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it7.hasNext()) {
                        Session session8 = it7.next().get();
                        if (session8 != null) {
                            session8.mCallbacks.onLauncherInfoChanged();
                        }
                    }
                    return;
                case 8:
                    Iterator<WeakReference<Session>> it8 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it8.hasNext()) {
                        Session session9 = it8.next().get();
                        if (session9 != null) {
                            session9.mCallbacks.onLoadEntriesCompleted();
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public class BackgroundHandler extends Handler {
        boolean mRunning;
        final IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
            /* class com.android.settingslib.applications.ApplicationsState.BackgroundHandler.AnonymousClass1 */

            public void onGetStatsCompleted(PackageStats packageStats, boolean z) {
                boolean z2;
                if (z) {
                    synchronized (ApplicationsState.this.mEntriesMap) {
                        HashMap<String, AppEntry> hashMap = ApplicationsState.this.mEntriesMap.get(packageStats.userHandle);
                        if (hashMap != null) {
                            AppEntry appEntry = hashMap.get(packageStats.packageName);
                            if (appEntry != null) {
                                synchronized (appEntry) {
                                    z2 = false;
                                    appEntry.sizeStale = false;
                                    appEntry.sizeLoadStart = 0;
                                    long j = packageStats.externalCodeSize + packageStats.externalObbSize;
                                    long j2 = packageStats.externalDataSize + packageStats.externalMediaSize;
                                    long totalInternalSize = j + j2 + ApplicationsState.this.getTotalInternalSize(packageStats);
                                    if (!(appEntry.size == totalInternalSize && appEntry.cacheSize == packageStats.cacheSize && appEntry.codeSize == packageStats.codeSize && appEntry.dataSize == packageStats.dataSize && appEntry.externalCodeSize == j && appEntry.externalDataSize == j2 && appEntry.externalCacheSize == packageStats.externalCacheSize)) {
                                        appEntry.size = totalInternalSize;
                                        appEntry.cacheSize = packageStats.cacheSize;
                                        appEntry.codeSize = packageStats.codeSize;
                                        appEntry.dataSize = packageStats.dataSize;
                                        appEntry.externalCodeSize = j;
                                        appEntry.externalDataSize = j2;
                                        appEntry.externalCacheSize = packageStats.externalCacheSize;
                                        appEntry.sizeStr = ApplicationsState.this.getSizeStr(totalInternalSize);
                                        long totalInternalSize2 = ApplicationsState.this.getTotalInternalSize(packageStats);
                                        appEntry.internalSize = totalInternalSize2;
                                        appEntry.internalSizeStr = ApplicationsState.this.getSizeStr(totalInternalSize2);
                                        long totalExternalSize = ApplicationsState.this.getTotalExternalSize(packageStats);
                                        appEntry.externalSize = totalExternalSize;
                                        appEntry.externalSizeStr = ApplicationsState.this.getSizeStr(totalExternalSize);
                                        z2 = true;
                                    }
                                }
                                if (z2) {
                                    ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(4, packageStats.packageName));
                                }
                            }
                            String str = ApplicationsState.this.mCurComputingSizePkg;
                            if (str != null && str.equals(packageStats.packageName)) {
                                BackgroundHandler backgroundHandler = BackgroundHandler.this;
                                ApplicationsState applicationsState = ApplicationsState.this;
                                if (applicationsState.mCurComputingSizeUserId == packageStats.userHandle) {
                                    applicationsState.mCurComputingSizePkg = null;
                                    backgroundHandler.sendEmptyMessage(7);
                                }
                            }
                        }
                    }
                }
            }
        };

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        BackgroundHandler(Looper looper) {
            super(looper);
            ApplicationsState.this = r1;
        }

        public void handleMessage(Message message) {
            ArrayList arrayList;
            int i;
            int i2;
            synchronized (ApplicationsState.this.mRebuildingSessions) {
                if (ApplicationsState.this.mRebuildingSessions.size() > 0) {
                    arrayList = new ArrayList(ApplicationsState.this.mRebuildingSessions);
                    ApplicationsState.this.mRebuildingSessions.clear();
                } else {
                    arrayList = null;
                }
            }
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((Session) it.next()).handleRebuildList();
                }
            }
            int combinedSessionFlags = getCombinedSessionFlags(ApplicationsState.this.mSessions);
            int i3 = message.what;
            int i4 = 0;
            boolean z = true;
            switch (i3) {
                case 2:
                    synchronized (ApplicationsState.this.mEntriesMap) {
                        i = 0;
                        for (int i5 = 0; i5 < ApplicationsState.this.mApplications.size() && i < 6; i5++) {
                            if (!this.mRunning) {
                                this.mRunning = true;
                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                            }
                            ApplicationInfo applicationInfo = ApplicationsState.this.mApplications.get(i5);
                            int userId = UserHandle.getUserId(applicationInfo.uid);
                            if (ApplicationsState.this.mEntriesMap.get(userId).get(applicationInfo.packageName) == null) {
                                i++;
                                ApplicationsState.this.getEntryLocked(applicationInfo);
                            }
                            if (userId != 0) {
                                if (ApplicationsState.this.mEntriesMap.indexOfKey(0) >= 0) {
                                    AppEntry appEntry = ApplicationsState.this.mEntriesMap.get(0).get(applicationInfo.packageName);
                                    if (appEntry != null && !ApplicationsState.hasFlag(appEntry.info.flags, 8388608)) {
                                        ApplicationsState.this.mEntriesMap.get(0).remove(applicationInfo.packageName);
                                        ApplicationsState.this.mAppEntries.remove(appEntry);
                                    }
                                }
                            }
                        }
                    }
                    if (i >= 6) {
                        sendEmptyMessage(2);
                        return;
                    }
                    if (!ApplicationsState.this.mMainHandler.hasMessages(8)) {
                        ApplicationsState.this.mMainHandler.sendEmptyMessage(8);
                    }
                    sendEmptyMessage(3);
                    return;
                case 3:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 1)) {
                        ArrayList<ResolveInfo> arrayList2 = new ArrayList();
                        ApplicationsState.this.mPm.getHomeActivities(arrayList2);
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            int size = ApplicationsState.this.mEntriesMap.size();
                            for (int i6 = 0; i6 < size; i6++) {
                                HashMap<String, AppEntry> valueAt = ApplicationsState.this.mEntriesMap.valueAt(i6);
                                for (ResolveInfo resolveInfo : arrayList2) {
                                    AppEntry appEntry2 = valueAt.get(resolveInfo.activityInfo.packageName);
                                    if (appEntry2 != null) {
                                        appEntry2.isHomeApp = true;
                                    }
                                }
                            }
                        }
                    }
                    sendEmptyMessage(4);
                    return;
                case 4:
                case 5:
                    if ((i3 == 4 && ApplicationsState.hasFlag(combinedSessionFlags, 8)) || (message.what == 5 && ApplicationsState.hasFlag(combinedSessionFlags, 16))) {
                        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                        intent.addCategory(message.what == 4 ? "android.intent.category.LAUNCHER" : "android.intent.category.LEANBACK_LAUNCHER");
                        int i7 = 0;
                        while (i7 < ApplicationsState.this.mEntriesMap.size()) {
                            int keyAt = ApplicationsState.this.mEntriesMap.keyAt(i7);
                            List queryIntentActivitiesAsUser = ApplicationsState.this.mPm.queryIntentActivitiesAsUser(intent, 786944, keyAt);
                            synchronized (ApplicationsState.this.mEntriesMap) {
                                HashMap<String, AppEntry> valueAt2 = ApplicationsState.this.mEntriesMap.valueAt(i7);
                                int size2 = queryIntentActivitiesAsUser.size();
                                int i8 = i4;
                                while (i8 < size2) {
                                    ResolveInfo resolveInfo2 = (ResolveInfo) queryIntentActivitiesAsUser.get(i8);
                                    String str = resolveInfo2.activityInfo.packageName;
                                    AppEntry appEntry3 = valueAt2.get(str);
                                    if (appEntry3 != null) {
                                        appEntry3.hasLauncherEntry = z;
                                        appEntry3.launcherEntryEnabled |= resolveInfo2.activityInfo.enabled;
                                    } else {
                                        Log.w("ApplicationsState", "Cannot find pkg: " + str + " on user " + keyAt);
                                    }
                                    i8++;
                                    z = true;
                                }
                            }
                            i7++;
                            i4 = 0;
                            z = true;
                        }
                        if (!ApplicationsState.this.mMainHandler.hasMessages(7)) {
                            ApplicationsState.this.mMainHandler.sendEmptyMessage(7);
                        }
                    }
                    if (message.what == 4) {
                        sendEmptyMessage(5);
                        return;
                    } else {
                        sendEmptyMessage(6);
                        return;
                    }
                case 6:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 2)) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            i2 = 0;
                            while (i4 < ApplicationsState.this.mAppEntries.size() && i2 < 2) {
                                AppEntry appEntry4 = ApplicationsState.this.mAppEntries.get(i4);
                                if (appEntry4.icon == null || !appEntry4.mounted) {
                                    synchronized (appEntry4) {
                                        if (appEntry4.ensureIconLocked(ApplicationsState.this.mContext)) {
                                            if (!this.mRunning) {
                                                this.mRunning = true;
                                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                                            }
                                            i2++;
                                        }
                                    }
                                }
                                i4++;
                            }
                        }
                        if (i2 > 0 && !ApplicationsState.this.mMainHandler.hasMessages(3)) {
                            ApplicationsState.this.mMainHandler.sendEmptyMessage(3);
                        }
                        if (i2 >= 2) {
                            sendEmptyMessage(6);
                            return;
                        }
                    }
                    sendEmptyMessage(7);
                    return;
                case 7:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 4)) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            if (ApplicationsState.this.mCurComputingSizePkg == null) {
                                long uptimeMillis = SystemClock.uptimeMillis();
                                for (int i9 = 0; i9 < ApplicationsState.this.mAppEntries.size(); i9++) {
                                    AppEntry appEntry5 = ApplicationsState.this.mAppEntries.get(i9);
                                    if (ApplicationsState.hasFlag(appEntry5.info.flags, 8388608) && (appEntry5.size == -1 || appEntry5.sizeStale)) {
                                        long j = appEntry5.sizeLoadStart;
                                        if (j == 0 || j < uptimeMillis - 20000) {
                                            if (!this.mRunning) {
                                                this.mRunning = true;
                                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                                            }
                                            appEntry5.sizeLoadStart = uptimeMillis;
                                            ApplicationsState applicationsState = ApplicationsState.this;
                                            ApplicationInfo applicationInfo2 = appEntry5.info;
                                            applicationsState.mCurComputingSizeUuid = applicationInfo2.storageUuid;
                                            applicationsState.mCurComputingSizePkg = applicationInfo2.packageName;
                                            applicationsState.mCurComputingSizeUserId = UserHandle.getUserId(applicationInfo2.uid);
                                            ApplicationsState.this.mBackgroundHandler.post(new ApplicationsState$BackgroundHandler$$ExternalSyntheticLambda0(this));
                                        }
                                        return;
                                    }
                                }
                                if (!ApplicationsState.this.mMainHandler.hasMessages(5)) {
                                    ApplicationsState.this.mMainHandler.sendEmptyMessage(5);
                                    this.mRunning = false;
                                    ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 0));
                                }
                                return;
                            }
                            return;
                        }
                    }
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: public */
        private /* synthetic */ void lambda$handleMessage$0() {
            try {
                ApplicationsState applicationsState = ApplicationsState.this;
                StorageStats queryStatsForPackage = applicationsState.mStats.queryStatsForPackage(applicationsState.mCurComputingSizeUuid, applicationsState.mCurComputingSizePkg, UserHandle.of(applicationsState.mCurComputingSizeUserId));
                ApplicationsState applicationsState2 = ApplicationsState.this;
                PackageStats packageStats = new PackageStats(applicationsState2.mCurComputingSizePkg, applicationsState2.mCurComputingSizeUserId);
                packageStats.codeSize = queryStatsForPackage.getAppBytes();
                packageStats.dataSize = queryStatsForPackage.getDataBytes();
                packageStats.cacheSize = queryStatsForPackage.getCacheBytes();
                this.mStatsObserver.onGetStatsCompleted(packageStats, true);
            } catch (PackageManager.NameNotFoundException | IOException e) {
                Log.w("ApplicationsState", "Failed to query stats: " + e);
                try {
                    this.mStatsObserver.onGetStatsCompleted((PackageStats) null, false);
                } catch (RemoteException unused) {
                }
            }
        }

        private int getCombinedSessionFlags(List<Session> list) {
            int i;
            synchronized (ApplicationsState.this.mEntriesMap) {
                i = 0;
                for (Session session : list) {
                    i |= session.mFlags;
                }
            }
            return i;
        }
    }

    public class PackageIntentReceiver extends BroadcastReceiver {
        private PackageIntentReceiver() {
            ApplicationsState.this = r1;
        }

        public void registerReceiver() {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme("package");
            ApplicationsState.this.mContext.registerReceiver(this, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
            ApplicationsState.this.mContext.registerReceiver(this, intentFilter2);
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("android.intent.action.USER_ADDED");
            intentFilter3.addAction("android.intent.action.USER_REMOVED");
            ApplicationsState.this.mContext.registerReceiver(this, intentFilter3);
        }

        public void unregisterReceiver() {
            ApplicationsState.this.mContext.unregisterReceiver(this);
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int i = 0;
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState = ApplicationsState.this;
                    applicationsState.addPackage(encodedSchemeSpecificPart, applicationsState.mEntriesMap.keyAt(i));
                    i++;
                }
            } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                String encodedSchemeSpecificPart2 = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState2 = ApplicationsState.this;
                    applicationsState2.removePackage(encodedSchemeSpecificPart2, applicationsState2.mEntriesMap.keyAt(i));
                    i++;
                }
            } else if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                String encodedSchemeSpecificPart3 = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState3 = ApplicationsState.this;
                    applicationsState3.invalidatePackage(encodedSchemeSpecificPart3, applicationsState3.mEntriesMap.keyAt(i));
                    i++;
                }
            } else if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action) || "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action)) {
                String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                if (!(stringArrayExtra == null || stringArrayExtra.length == 0 || !"android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action))) {
                    for (String str : stringArrayExtra) {
                        for (int i2 = 0; i2 < ApplicationsState.this.mEntriesMap.size(); i2++) {
                            ApplicationsState applicationsState4 = ApplicationsState.this;
                            applicationsState4.invalidatePackage(str, applicationsState4.mEntriesMap.keyAt(i2));
                        }
                    }
                }
            } else if ("android.intent.action.USER_ADDED".equals(action)) {
                ApplicationsState.this.addUser(intent.getIntExtra("android.intent.extra.user_handle", -10000));
            } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                ApplicationsState.this.removeUser(intent.getIntExtra("android.intent.extra.user_handle", -10000));
            }
        }
    }

    public boolean isUserAdded(int i) {
        return this.mEntriesMap.contains(i);
    }

    public static class AppEntry extends SizeInfo {
        public final File apkFile;
        public long externalSize;
        public String externalSizeStr;
        public boolean hasLauncherEntry;
        public Drawable icon;
        public final long id;
        public ApplicationInfo info;
        public long internalSize;
        public String internalSizeStr;
        public boolean isHomeApp;
        public String label;
        public String labelDescription;
        public boolean launcherEntryEnabled;
        public boolean mounted;
        public long size = -1;
        public long sizeLoadStart;
        public boolean sizeStale = true;
        public String sizeStr;

        public AppEntry(Context context, ApplicationInfo applicationInfo, long j) {
            this.apkFile = new File(applicationInfo.sourceDir);
            this.id = j;
            this.info = applicationInfo;
            ensureLabel(context);
            ThreadUtils.postOnBackgroundThread(new ApplicationsState$AppEntry$$ExternalSyntheticLambda0(this, context));
        }

        /* access modifiers changed from: public */
        private /* synthetic */ void lambda$new$0(Context context) {
            if (this.icon == null) {
                ensureIconLocked(context);
            }
            if (this.labelDescription == null) {
                ensureLabelDescriptionLocked(context);
            }
        }

        public void ensureLabel(Context context) {
            if (this.label != null && this.mounted) {
                return;
            }
            if (!this.apkFile.exists()) {
                this.mounted = false;
                this.label = this.info.packageName;
                return;
            }
            this.mounted = true;
            CharSequence loadLabel = this.info.loadLabel(context.getPackageManager());
            this.label = loadLabel != null ? loadLabel.toString() : this.info.packageName;
        }

        public boolean ensureIconLocked(Context context) {
            if (this.icon == null) {
                if (this.apkFile.exists()) {
                    this.icon = Utils.getBadgedIcon(context, this.info);
                    return true;
                }
                this.mounted = false;
                this.icon = context.getDrawable(17303671);
            } else if (!this.mounted && this.apkFile.exists()) {
                this.mounted = true;
                this.icon = Utils.getBadgedIcon(context, this.info);
                return true;
            }
            return false;
        }

        public void ensureLabelDescriptionLocked(Context context) {
            if (UserManager.get(context).isManagedProfile(UserHandle.getUserId(this.info.uid))) {
                this.labelDescription = context.getString(R$string.accessibility_work_profile_app_description, this.label);
                return;
            }
            this.labelDescription = this.label;
        }
    }

    public interface AppFilter {
        boolean filterApp(AppEntry appEntry);

        void init();

        default void init(Context context) {
            init();
        }
    }
}

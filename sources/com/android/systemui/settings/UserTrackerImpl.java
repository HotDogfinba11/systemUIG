package com.android.systemui.settings;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.Assert;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;

/* compiled from: UserTrackerImpl.kt */
public final class UserTrackerImpl extends BroadcastReceiver implements UserTracker, Dumpable {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(UserTrackerImpl.class), "userId", "getUserId()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(UserTrackerImpl.class), "userHandle", "getUserHandle()Landroid/os/UserHandle;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(UserTrackerImpl.class), "userContext", "getUserContext()Landroid/content/Context;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(UserTrackerImpl.class), "userProfiles", "getUserProfiles()Ljava/util/List;"))};
    public static final Companion Companion = new Companion(null);
    private final Handler backgroundHandler;
    private final List<DataItem> callbacks;
    private final Context context;
    private final DumpManager dumpManager;
    private boolean initialized;
    private final Object mutex = new Object();
    private final SynchronizedDelegate userContext$delegate;
    private final SynchronizedDelegate userHandle$delegate;
    private final SynchronizedDelegate userId$delegate;
    private final UserManager userManager;
    private final SynchronizedDelegate userProfiles$delegate;

    public UserTrackerImpl(Context context2, UserManager userManager2, DumpManager dumpManager2, Handler handler) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(userManager2, "userManager");
        Intrinsics.checkNotNullParameter(dumpManager2, "dumpManager");
        Intrinsics.checkNotNullParameter(handler, "backgroundHandler");
        this.context = context2;
        this.userManager = userManager2;
        this.dumpManager = dumpManager2;
        this.backgroundHandler = handler;
        this.userId$delegate = new SynchronizedDelegate(Integer.valueOf(context2.getUserId()));
        UserHandle user = context2.getUser();
        Intrinsics.checkNotNullExpressionValue(user, "context.user");
        this.userHandle$delegate = new SynchronizedDelegate(user);
        this.userContext$delegate = new SynchronizedDelegate(context2);
        this.userProfiles$delegate = new SynchronizedDelegate(CollectionsKt__CollectionsKt.emptyList());
        this.callbacks = new ArrayList();
    }

    /* compiled from: UserTrackerImpl.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean getInitialized() {
        return this.initialized;
    }

    private void setUserId(int i) {
        this.userId$delegate.setValue(this, $$delegatedProperties[0], Integer.valueOf(i));
    }

    @Override // com.android.systemui.settings.UserTracker
    public int getUserId() {
        return ((Number) this.userId$delegate.getValue(this, $$delegatedProperties[0])).intValue();
    }

    private void setUserHandle(UserHandle userHandle) {
        this.userHandle$delegate.setValue(this, $$delegatedProperties[1], userHandle);
    }

    @Override // com.android.systemui.settings.UserTracker
    public UserHandle getUserHandle() {
        return (UserHandle) this.userHandle$delegate.getValue(this, $$delegatedProperties[1]);
    }

    private void setUserContext(Context context2) {
        this.userContext$delegate.setValue(this, $$delegatedProperties[2], context2);
    }

    @Override // com.android.systemui.settings.UserContextProvider
    public Context getUserContext() {
        return (Context) this.userContext$delegate.getValue(this, $$delegatedProperties[2]);
    }

    @Override // com.android.systemui.settings.UserContentResolverProvider
    public ContentResolver getUserContentResolver() {
        ContentResolver contentResolver = getUserContext().getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "userContext.contentResolver");
        return contentResolver;
    }

    @Override // com.android.systemui.settings.UserTracker
    public UserInfo getUserInfo() {
        boolean z;
        int userId = getUserId();
        for (T t : getUserProfiles()) {
            if (((UserInfo) t).id == userId) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (z) {
                return t;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private void setUserProfiles(List<? extends UserInfo> list) {
        this.userProfiles$delegate.setValue(this, $$delegatedProperties[3], list);
    }

    @Override // com.android.systemui.settings.UserTracker
    public List<UserInfo> getUserProfiles() {
        return (List) this.userProfiles$delegate.getValue(this, $$delegatedProperties[3]);
    }

    public final void initialize(int i) {
        if (!this.initialized) {
            this.initialized = true;
            setUserIdInternal(i);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
            intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
            this.context.registerReceiverForAllUsers(this, intentFilter, null, this.backgroundHandler);
            this.dumpManager.registerDumpable("UserTrackerImpl", this);
        }
    }

    public void onReceive(Context context2, Intent intent) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String action = intent.getAction();
        if (action != null) {
            int hashCode = action.hashCode();
            if (hashCode != -1238404651) {
                if (hashCode != -864107122) {
                    if (hashCode == 959232034 && action.equals("android.intent.action.USER_SWITCHED")) {
                        handleSwitchUser(intent.getIntExtra("android.intent.extra.user_handle", -10000));
                        return;
                    }
                    return;
                } else if (!action.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                    return;
                }
            } else if (!action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                return;
            }
            handleProfilesChanged();
        }
    }

    private final Pair<Context, List<UserInfo>> setUserIdInternal(int i) {
        List<UserInfo> profiles = this.userManager.getProfiles(i);
        UserHandle userHandle = new UserHandle(i);
        Context createContextAsUser = this.context.createContextAsUser(userHandle, 0);
        synchronized (this.mutex) {
            setUserId(i);
            setUserHandle(userHandle);
            Intrinsics.checkNotNullExpressionValue(createContextAsUser, "ctx");
            setUserContext(createContextAsUser);
            Intrinsics.checkNotNullExpressionValue(profiles, "profiles");
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(profiles, 10));
            for (UserInfo userInfo : profiles) {
                arrayList.add(new UserInfo(userInfo));
            }
            setUserProfiles(arrayList);
            Unit unit = Unit.INSTANCE;
        }
        return TuplesKt.to(createContextAsUser, profiles);
    }

    private final void handleSwitchUser(int i) {
        List<DataItem> list;
        Assert.isNotMainThread();
        if (i == -10000) {
            Log.w("UserTrackerImpl", "handleSwitchUser - Couldn't get new id from intent");
        } else if (i != getUserId()) {
            Log.i("UserTrackerImpl", Intrinsics.stringPlus("Switching to user ", Integer.valueOf(i)));
            Pair<Context, List<UserInfo>> userIdInternal = setUserIdInternal(i);
            Context component1 = userIdInternal.component1();
            List<UserInfo> component2 = userIdInternal.component2();
            synchronized (this.callbacks) {
                list = CollectionsKt___CollectionsKt.toList(this.callbacks);
            }
            for (DataItem dataItem : list) {
                if (dataItem.getCallback().get() != null) {
                    dataItem.getExecutor().execute(new UserTrackerImpl$handleSwitchUser$$inlined$notifySubscribers$1(dataItem, i, component1, component2));
                }
            }
        }
    }

    private final void handleProfilesChanged() {
        List<DataItem> list;
        Assert.isNotMainThread();
        List<UserInfo> profiles = this.userManager.getProfiles(getUserId());
        synchronized (this.mutex) {
            Intrinsics.checkNotNullExpressionValue(profiles, "profiles");
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(profiles, 10));
            for (UserInfo userInfo : profiles) {
                arrayList.add(new UserInfo(userInfo));
            }
            setUserProfiles(arrayList);
            Unit unit = Unit.INSTANCE;
        }
        synchronized (this.callbacks) {
            list = CollectionsKt___CollectionsKt.toList(this.callbacks);
        }
        for (DataItem dataItem : list) {
            if (dataItem.getCallback().get() != null) {
                dataItem.getExecutor().execute(new UserTrackerImpl$handleProfilesChanged$$inlined$notifySubscribers$1(dataItem, profiles));
            }
        }
    }

    @Override // com.android.systemui.settings.UserTracker
    public void addCallback(UserTracker.Callback callback, Executor executor) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(executor, "executor");
        synchronized (this.callbacks) {
            this.callbacks.add(new DataItem(new WeakReference(callback), executor));
        }
    }

    @Override // com.android.systemui.settings.UserTracker
    public void removeCallback(UserTracker.Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        synchronized (this.callbacks) {
            this.callbacks.removeIf(new UserTrackerImpl$removeCallback$1$1(callback));
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        List<DataItem> list;
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println(Intrinsics.stringPlus("Initialized: ", Boolean.valueOf(this.initialized)));
        if (this.initialized) {
            printWriter.println(Intrinsics.stringPlus("userId: ", Integer.valueOf(getUserId())));
            List<UserInfo> userProfiles = getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
            Iterator<T> it = userProfiles.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
            printWriter.println(Intrinsics.stringPlus("userProfiles: ", arrayList));
        }
        synchronized (this.callbacks) {
            list = CollectionsKt___CollectionsKt.toList(this.callbacks);
        }
        printWriter.println("Callbacks:");
        for (DataItem dataItem : list) {
            UserTracker.Callback callback = dataItem.getCallback().get();
            if (callback != null) {
                printWriter.println(Intrinsics.stringPlus("  ", callback));
            }
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: UserTrackerImpl.kt */
    public static final class SynchronizedDelegate<T> {
        private T value;

        public SynchronizedDelegate(T t) {
            Intrinsics.checkNotNullParameter(t, "value");
            this.value = t;
        }

        public T getValue(UserTrackerImpl userTrackerImpl, KProperty<?> kProperty) {
            T t;
            Intrinsics.checkNotNullParameter(userTrackerImpl, "thisRef");
            Intrinsics.checkNotNullParameter(kProperty, "property");
            if (userTrackerImpl.getInitialized()) {
                synchronized (userTrackerImpl.mutex) {
                    t = this.value;
                }
                return t;
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Must initialize before getting ", kProperty.getName()));
        }

        public void setValue(UserTrackerImpl userTrackerImpl, KProperty<?> kProperty, T t) {
            Intrinsics.checkNotNullParameter(userTrackerImpl, "thisRef");
            Intrinsics.checkNotNullParameter(kProperty, "property");
            Intrinsics.checkNotNullParameter(t, "value");
            synchronized (userTrackerImpl.mutex) {
                this.value = t;
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}

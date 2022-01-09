package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.UserHandle;
import android.util.Log;
import com.android.settingslib.applications.ServiceListing;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ControlsListingControllerImpl implements ControlsListingController {
    public static final Companion Companion = new Companion(null);
    private Set<ComponentName> availableComponents;
    private List<? extends ServiceInfo> availableServices;
    private final Executor backgroundExecutor;
    private final Set<ControlsListingController.ControlsListingCallback> callbacks;
    private final Context context;
    private int currentUserId;
    private ServiceListing serviceListing;
    private final Function1<Context, ServiceListing> serviceListingBuilder;
    private final ServiceListing.Callback serviceListingCallback;
    private AtomicInteger userChangeInProgress;

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.Context, ? extends com.android.settingslib.applications.ServiceListing> */
    /* JADX WARN: Multi-variable type inference failed */
    public ControlsListingControllerImpl(Context context2, Executor executor, Function1<? super Context, ? extends ServiceListing> function1, UserTracker userTracker) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(executor, "backgroundExecutor");
        Intrinsics.checkNotNullParameter(function1, "serviceListingBuilder");
        Intrinsics.checkNotNullParameter(userTracker, "userTracker");
        this.context = context2;
        this.backgroundExecutor = executor;
        this.serviceListingBuilder = function1;
        this.serviceListing = (ServiceListing) function1.invoke(context2);
        this.callbacks = new LinkedHashSet();
        this.availableComponents = SetsKt__SetsKt.emptySet();
        this.availableServices = CollectionsKt__CollectionsKt.emptyList();
        this.userChangeInProgress = new AtomicInteger(0);
        this.currentUserId = userTracker.getUserId();
        ControlsListingControllerImpl$serviceListingCallback$1 controlsListingControllerImpl$serviceListingCallback$1 = new ControlsListingControllerImpl$serviceListingCallback$1(this);
        this.serviceListingCallback = controlsListingControllerImpl$serviceListingCallback$1;
        Log.d("ControlsListingControllerImpl", "Initializing");
        this.serviceListing.addCallback(controlsListingControllerImpl$serviceListingCallback$1);
        this.serviceListing.setListening(true);
        this.serviceListing.reload();
    }

    public static final /* synthetic */ Context access$getContext$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.context;
    }

    public static final /* synthetic */ ServiceListing access$getServiceListing$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.serviceListing;
    }

    public static final /* synthetic */ Function1 access$getServiceListingBuilder$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.serviceListingBuilder;
    }

    public static final /* synthetic */ ServiceListing.Callback access$getServiceListingCallback$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.serviceListingCallback;
    }

    public static final /* synthetic */ AtomicInteger access$getUserChangeInProgress$p(ControlsListingControllerImpl controlsListingControllerImpl) {
        return controlsListingControllerImpl.userChangeInProgress;
    }

    public static final /* synthetic */ void access$setCurrentUserId$p(ControlsListingControllerImpl controlsListingControllerImpl, int i) {
        controlsListingControllerImpl.currentUserId = i;
    }

    public static final /* synthetic */ void access$setServiceListing$p(ControlsListingControllerImpl controlsListingControllerImpl, ServiceListing serviceListing2) {
        controlsListingControllerImpl.serviceListing = serviceListing2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ControlsListingControllerImpl(Context context2, Executor executor, UserTracker userTracker) {
        this(context2, executor, AnonymousClass1.INSTANCE, userTracker);
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(userTracker, "userTracker");
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // com.android.systemui.util.UserAwareController
    public int getCurrentUserId() {
        return this.currentUserId;
    }

    @Override // com.android.systemui.util.UserAwareController
    public void changeUser(UserHandle userHandle) {
        Intrinsics.checkNotNullParameter(userHandle, "newUser");
        this.userChangeInProgress.incrementAndGet();
        this.serviceListing.setListening(false);
        this.backgroundExecutor.execute(new ControlsListingControllerImpl$changeUser$1(this, userHandle));
    }

    public void addCallback(ControlsListingController.ControlsListingCallback controlsListingCallback) {
        Intrinsics.checkNotNullParameter(controlsListingCallback, "listener");
        this.backgroundExecutor.execute(new ControlsListingControllerImpl$addCallback$1(this, controlsListingCallback));
    }

    public void removeCallback(ControlsListingController.ControlsListingCallback controlsListingCallback) {
        Intrinsics.checkNotNullParameter(controlsListingCallback, "listener");
        this.backgroundExecutor.execute(new ControlsListingControllerImpl$removeCallback$1(this, controlsListingCallback));
    }

    public List<ControlsServiceInfo> getCurrentServices() {
        List<? extends ServiceInfo> list = this.availableServices;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ControlsServiceInfo(this.context, it.next()));
        }
        return arrayList;
    }

    @Override // com.android.systemui.controls.management.ControlsListingController
    public CharSequence getAppLabel(ComponentName componentName) {
        T t;
        Intrinsics.checkNotNullParameter(componentName, "name");
        Iterator<T> it = getCurrentServices().iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (Intrinsics.areEqual(t.componentName, componentName)) {
                break;
            }
        }
        T t2 = t;
        if (t2 == null) {
            return null;
        }
        return t2.loadLabel();
    }
}

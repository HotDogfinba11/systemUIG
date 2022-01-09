package com.android.systemui.controls.controller;

import android.app.PendingIntent;
import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.UserHandle;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ControlsControllerImpl implements Dumpable, ControlsController {
    public static final Companion Companion = new Companion(null);
    private AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper;
    private final ControlsBindingController bindingController;
    private final BroadcastDispatcher broadcastDispatcher;
    private final Context context;
    private UserHandle currentUser;
    private final DelayableExecutor executor;
    private final ControlsControllerImpl$listingCallback$1 listingCallback;
    private final ControlsListingController listingController;
    private final ControlsFavoritePersistenceWrapper persistenceWrapper;
    private final BroadcastReceiver restoreFinishedReceiver;
    private final List<Consumer<Boolean>> seedingCallbacks = new ArrayList();
    private boolean seedingInProgress;
    private final ContentObserver settingObserver;
    private final ControlsUiController uiController;
    private boolean userChanging = true;
    private UserStructure userStructure;
    private final ControlsControllerImpl$userSwitchReceiver$1 userSwitchReceiver;

    public static /* synthetic */ void getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getRestoreFinishedReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getSettingObserver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public ControlsControllerImpl(Context context2, DelayableExecutor delayableExecutor, ControlsUiController controlsUiController, ControlsBindingController controlsBindingController, ControlsListingController controlsListingController, BroadcastDispatcher broadcastDispatcher2, Optional<ControlsFavoritePersistenceWrapper> optional, DumpManager dumpManager, UserTracker userTracker) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(delayableExecutor, "executor");
        Intrinsics.checkNotNullParameter(controlsUiController, "uiController");
        Intrinsics.checkNotNullParameter(controlsBindingController, "bindingController");
        Intrinsics.checkNotNullParameter(controlsListingController, "listingController");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(optional, "optionalWrapper");
        Intrinsics.checkNotNullParameter(dumpManager, "dumpManager");
        Intrinsics.checkNotNullParameter(userTracker, "userTracker");
        this.context = context2;
        this.executor = delayableExecutor;
        this.uiController = controlsUiController;
        this.bindingController = controlsBindingController;
        this.listingController = controlsListingController;
        this.broadcastDispatcher = broadcastDispatcher2;
        UserHandle userHandle = userTracker.getUserHandle();
        this.currentUser = userHandle;
        this.userStructure = new UserStructure(context2, userHandle);
        ControlsFavoritePersistenceWrapper orElseGet = optional.orElseGet(new Supplier<ControlsFavoritePersistenceWrapper>(this) {
            /* class com.android.systemui.controls.controller.ControlsControllerImpl.AnonymousClass1 */
            final /* synthetic */ ControlsControllerImpl this$0;

            {
                this.this$0 = r1;
            }

            @Override // java.util.function.Supplier
            public final ControlsFavoritePersistenceWrapper get() {
                File file = this.this$0.userStructure.getFile();
                Intrinsics.checkNotNullExpressionValue(file, "userStructure.file");
                return new ControlsFavoritePersistenceWrapper(file, this.this$0.executor, new BackupManager(this.this$0.userStructure.getUserContext()));
            }
        });
        Intrinsics.checkNotNullExpressionValue(orElseGet, "optionalWrapper.orElseGet {\n            ControlsFavoritePersistenceWrapper(\n                    userStructure.file,\n                    executor,\n                    BackupManager(userStructure.userContext)\n            )\n        }");
        this.persistenceWrapper = orElseGet;
        File auxiliaryFile = this.userStructure.getAuxiliaryFile();
        Intrinsics.checkNotNullExpressionValue(auxiliaryFile, "userStructure.auxiliaryFile");
        this.auxiliaryPersistenceWrapper = new AuxiliaryPersistenceWrapper(auxiliaryFile, delayableExecutor);
        ControlsControllerImpl$userSwitchReceiver$1 controlsControllerImpl$userSwitchReceiver$1 = new ControlsControllerImpl$userSwitchReceiver$1(this);
        this.userSwitchReceiver = controlsControllerImpl$userSwitchReceiver$1;
        ControlsControllerImpl$restoreFinishedReceiver$1 controlsControllerImpl$restoreFinishedReceiver$1 = new ControlsControllerImpl$restoreFinishedReceiver$1(this);
        this.restoreFinishedReceiver = controlsControllerImpl$restoreFinishedReceiver$1;
        this.settingObserver = new ControlsControllerImpl$settingObserver$1(this);
        ControlsControllerImpl$listingCallback$1 controlsControllerImpl$listingCallback$1 = new ControlsControllerImpl$listingCallback$1(this);
        this.listingCallback = controlsControllerImpl$listingCallback$1;
        String name = ControlsControllerImpl.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "javaClass.name");
        dumpManager.registerDumpable(name, this);
        resetFavorites();
        this.userChanging = false;
        broadcastDispatcher2.registerReceiver(controlsControllerImpl$userSwitchReceiver$1, new IntentFilter("android.intent.action.USER_SWITCHED"), delayableExecutor, UserHandle.ALL);
        context2.registerReceiver(controlsControllerImpl$restoreFinishedReceiver$1, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), "com.android.systemui.permission.SELF", null);
        controlsListingController.addCallback(controlsControllerImpl$listingCallback$1);
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
        return this.currentUser.getIdentifier();
    }

    public final AuxiliaryPersistenceWrapper getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.auxiliaryPersistenceWrapper;
    }

    private final void setValuesForUser(UserHandle userHandle) {
        Log.d("ControlsControllerImpl", Intrinsics.stringPlus("Changing to user: ", userHandle));
        this.currentUser = userHandle;
        UserStructure userStructure2 = new UserStructure(this.context, userHandle);
        this.userStructure = userStructure2;
        ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = this.persistenceWrapper;
        File file = userStructure2.getFile();
        Intrinsics.checkNotNullExpressionValue(file, "userStructure.file");
        controlsFavoritePersistenceWrapper.changeFileAndBackupManager(file, new BackupManager(this.userStructure.getUserContext()));
        AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper2 = this.auxiliaryPersistenceWrapper;
        File auxiliaryFile = this.userStructure.getAuxiliaryFile();
        Intrinsics.checkNotNullExpressionValue(auxiliaryFile, "userStructure.auxiliaryFile");
        auxiliaryPersistenceWrapper2.changeFile(auxiliaryFile);
        resetFavorites();
        this.bindingController.changeUser(userHandle);
        this.listingController.changeUser(userHandle);
        this.userChanging = false;
    }

    private final void resetFavorites() {
        Favorites favorites = Favorites.INSTANCE;
        favorites.clear();
        favorites.load(this.persistenceWrapper.readFavorites());
    }

    private final boolean confirmAvailability() {
        if (!this.userChanging) {
            return true;
        }
        Log.w("ControlsControllerImpl", "Controls not available while user is changing");
        return false;
    }

    public void loadForComponent(ComponentName componentName, Consumer<ControlsController.LoadData> consumer, Consumer<Runnable> consumer2) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(consumer, "dataCallback");
        Intrinsics.checkNotNullParameter(consumer2, "cancelWrapper");
        if (!confirmAvailability()) {
            if (this.userChanging) {
                this.executor.executeDelayed(new ControlsControllerImpl$loadForComponent$1(this, componentName, consumer, consumer2), 500, TimeUnit.MILLISECONDS);
            }
            consumer.accept(ControlsControllerKt.createLoadDataObject(CollectionsKt__CollectionsKt.emptyList(), CollectionsKt__CollectionsKt.emptyList(), true));
        }
        consumer2.accept(this.bindingController.bindAndLoad(componentName, new ControlsControllerImpl$loadForComponent$2(this, componentName, consumer)));
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public boolean addSeedingFavoritesCallback(Consumer<Boolean> consumer) {
        Intrinsics.checkNotNullParameter(consumer, "callback");
        if (!this.seedingInProgress) {
            return false;
        }
        this.executor.execute(new ControlsControllerImpl$addSeedingFavoritesCallback$1(this, consumer));
        return true;
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void seedFavoritesForComponents(List<ComponentName> list, Consumer<SeedResponse> consumer) {
        Intrinsics.checkNotNullParameter(list, "componentNames");
        Intrinsics.checkNotNullParameter(consumer, "callback");
        if (!this.seedingInProgress && !list.isEmpty()) {
            if (confirmAvailability()) {
                this.seedingInProgress = true;
                startSeeding(list, consumer, false);
            } else if (this.userChanging) {
                this.executor.executeDelayed(new ControlsControllerImpl$seedFavoritesForComponents$1(this, list, consumer), 500, TimeUnit.MILLISECONDS);
            } else {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    String packageName = it.next().getPackageName();
                    Intrinsics.checkNotNullExpressionValue(packageName, "it.packageName");
                    consumer.accept(new SeedResponse(packageName, false));
                }
            }
        }
    }

    /* access modifiers changed from: public */
    private final void startSeeding(List<ComponentName> list, Consumer<SeedResponse> consumer, boolean z) {
        if (list.isEmpty()) {
            endSeedingCall(!z);
            return;
        }
        ComponentName componentName = list.get(0);
        Log.d("ControlsControllerImpl", Intrinsics.stringPlus("Beginning request to seed favorites for: ", componentName));
        this.bindingController.bindAndLoadSuggested(componentName, new ControlsControllerImpl$startSeeding$1(this, consumer, componentName, CollectionsKt___CollectionsKt.drop(list, 1), z));
    }

    private final void endSeedingCall(boolean z) {
        this.seedingInProgress = false;
        Iterator<T> it = this.seedingCallbacks.iterator();
        while (it.hasNext()) {
            it.next().accept(Boolean.valueOf(z));
        }
        this.seedingCallbacks.clear();
    }

    static /* synthetic */ ControlStatus createRemovedStatus$default(ControlsControllerImpl controlsControllerImpl, ComponentName componentName, ControlInfo controlInfo, CharSequence charSequence, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return controlsControllerImpl.createRemovedStatus(componentName, controlInfo, charSequence, z);
    }

    /* access modifiers changed from: public */
    private final ControlStatus createRemovedStatus(ComponentName componentName, ControlInfo controlInfo, CharSequence charSequence, boolean z) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(componentName.getPackageName());
        Control build = new Control.StatelessBuilder(controlInfo.getControlId(), PendingIntent.getActivity(this.context, componentName.hashCode(), intent, 67108864)).setTitle(controlInfo.getControlTitle()).setSubtitle(controlInfo.getControlSubtitle()).setStructure(charSequence).setDeviceType(controlInfo.getDeviceType()).build();
        Intrinsics.checkNotNullExpressionValue(build, "control");
        return new ControlStatus(build, componentName, true, z);
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void subscribeToFavorites(StructureInfo structureInfo) {
        Intrinsics.checkNotNullParameter(structureInfo, "structureInfo");
        if (confirmAvailability()) {
            this.bindingController.subscribe(structureInfo);
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void unsubscribe() {
        if (confirmAvailability()) {
            this.bindingController.unsubscribe();
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void addFavorite(ComponentName componentName, CharSequence charSequence, ControlInfo controlInfo) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(charSequence, "structureName");
        Intrinsics.checkNotNullParameter(controlInfo, "controlInfo");
        if (confirmAvailability()) {
            this.executor.execute(new ControlsControllerImpl$addFavorite$1(componentName, charSequence, controlInfo, this));
        }
    }

    public void replaceFavoritesForStructure(StructureInfo structureInfo) {
        Intrinsics.checkNotNullParameter(structureInfo, "structureInfo");
        if (confirmAvailability()) {
            this.executor.execute(new ControlsControllerImpl$replaceFavoritesForStructure$1(structureInfo, this));
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void refreshStatus(ComponentName componentName, Control control) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(control, "control");
        if (!confirmAvailability()) {
            Log.d("ControlsControllerImpl", "Controls not available");
            return;
        }
        if (control.getStatus() == 1) {
            this.executor.execute(new ControlsControllerImpl$refreshStatus$1(componentName, control, this));
        }
        this.uiController.onRefreshState(componentName, CollectionsKt__CollectionsJVMKt.listOf(control));
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void onActionResponse(ComponentName componentName, String str, int i) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(str, "controlId");
        if (confirmAvailability()) {
            this.uiController.onActionResponse(componentName, str, i);
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public void action(ComponentName componentName, ControlInfo controlInfo, ControlAction controlAction) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(controlInfo, "controlInfo");
        Intrinsics.checkNotNullParameter(controlAction, "action");
        if (confirmAvailability()) {
            this.bindingController.action(componentName, controlInfo, controlAction);
        }
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public List<StructureInfo> getFavorites() {
        return Favorites.INSTANCE.getAllStructures();
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public int countFavoritesForComponent(ComponentName componentName) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        return Favorites.INSTANCE.getControlsForComponent(componentName).size();
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public List<StructureInfo> getFavoritesForComponent(ComponentName componentName) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        return Favorites.INSTANCE.getStructuresForComponent(componentName);
    }

    public List<ControlInfo> getFavoritesForStructure(ComponentName componentName, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(charSequence, "structureName");
        return Favorites.INSTANCE.getControlsForStructure(new StructureInfo(componentName, charSequence, CollectionsKt__CollectionsKt.emptyList()));
    }

    @Override // com.android.systemui.controls.controller.ControlsController
    public StructureInfo getPreferredStructure() {
        return this.uiController.getPreferredStructure(getFavorites());
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println("ControlsController state:");
        printWriter.println(Intrinsics.stringPlus("  Changing users: ", Boolean.valueOf(this.userChanging)));
        printWriter.println(Intrinsics.stringPlus("  Current user: ", Integer.valueOf(this.currentUser.getIdentifier())));
        printWriter.println("  Favorites:");
        for (T t : Favorites.INSTANCE.getAllStructures()) {
            printWriter.println(Intrinsics.stringPlus("    ", t));
            Iterator<T> it = t.getControls().iterator();
            while (it.hasNext()) {
                printWriter.println(Intrinsics.stringPlus("      ", it.next()));
            }
        }
        printWriter.println(this.bindingController.toString());
    }

    /* access modifiers changed from: public */
    private final Set<String> findRemoved(Set<String> set, List<Control> list) {
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getControlId());
        }
        return SetsKt___SetsKt.minus(set, arrayList);
    }
}

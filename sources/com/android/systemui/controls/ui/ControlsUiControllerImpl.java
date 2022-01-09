package com.android.systemui.controls.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Space;
import android.widget.TextView;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsEditingActivity;
import com.android.systemui.controls.management.ControlsFavoritingActivity;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsProviderSelectorActivity;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.RangesKt___RangesKt;

public final class ControlsUiControllerImpl implements ControlsUiController {
    public static final Companion Companion = new Companion(null);
    private static final ComponentName EMPTY_COMPONENT;
    private static final StructureInfo EMPTY_STRUCTURE;
    private Context activityContext;
    private final ActivityStarter activityStarter;
    private List<StructureInfo> allStructures;
    private final DelayableExecutor bgExecutor;
    private final Collator collator;
    private final Context context;
    private final ControlActionCoordinator controlActionCoordinator;
    private final Map<ControlKey, ControlViewHolder> controlViewsById = new LinkedHashMap();
    private final Map<ControlKey, ControlWithState> controlsById = new LinkedHashMap();
    private final Lazy<ControlsController> controlsController;
    private final Lazy<ControlsListingController> controlsListingController;
    private final ControlsMetricsLogger controlsMetricsLogger;
    private boolean hidden = true;
    private final CustomIconCache iconCache;
    private final KeyguardStateController keyguardStateController;
    private ControlsListingController.ControlsListingCallback listingCallback;
    private final Comparator<SelectionItem> localeComparator;
    private Runnable onDismiss;
    private final Consumer<Boolean> onSeedingComplete;
    private ViewGroup parent;
    private ListPopupWindow popup;
    private final ContextThemeWrapper popupThemedContext;
    private boolean retainCache;
    private StructureInfo selectedStructure = EMPTY_STRUCTURE;
    private final ShadeController shadeController;
    private final SharedPreferences sharedPreferences;
    private final DelayableExecutor uiExecutor;

    public ControlsUiControllerImpl(Lazy<ControlsController> lazy, Context context2, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, Lazy<ControlsListingController> lazy2, SharedPreferences sharedPreferences2, ControlActionCoordinator controlActionCoordinator2, ActivityStarter activityStarter2, ShadeController shadeController2, CustomIconCache customIconCache, ControlsMetricsLogger controlsMetricsLogger2, KeyguardStateController keyguardStateController2) {
        Intrinsics.checkNotNullParameter(lazy, "controlsController");
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(delayableExecutor, "uiExecutor");
        Intrinsics.checkNotNullParameter(delayableExecutor2, "bgExecutor");
        Intrinsics.checkNotNullParameter(lazy2, "controlsListingController");
        Intrinsics.checkNotNullParameter(sharedPreferences2, "sharedPreferences");
        Intrinsics.checkNotNullParameter(controlActionCoordinator2, "controlActionCoordinator");
        Intrinsics.checkNotNullParameter(activityStarter2, "activityStarter");
        Intrinsics.checkNotNullParameter(shadeController2, "shadeController");
        Intrinsics.checkNotNullParameter(customIconCache, "iconCache");
        Intrinsics.checkNotNullParameter(controlsMetricsLogger2, "controlsMetricsLogger");
        Intrinsics.checkNotNullParameter(keyguardStateController2, "keyguardStateController");
        this.controlsController = lazy;
        this.context = context2;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlsListingController = lazy2;
        this.sharedPreferences = sharedPreferences2;
        this.controlActionCoordinator = controlActionCoordinator2;
        this.activityStarter = activityStarter2;
        this.shadeController = shadeController2;
        this.iconCache = customIconCache;
        this.controlsMetricsLogger = controlsMetricsLogger2;
        this.keyguardStateController = keyguardStateController2;
        this.popupThemedContext = new ContextThemeWrapper(context2, R$style.Control_ListPopupWindow);
        Collator instance = Collator.getInstance(context2.getResources().getConfiguration().getLocales().get(0));
        this.collator = instance;
        Intrinsics.checkNotNullExpressionValue(instance, "collator");
        this.localeComparator = new ControlsUiControllerImpl$special$$inlined$compareBy$1(instance);
        this.onSeedingComplete = new ControlsUiControllerImpl$onSeedingComplete$1(this);
    }

    public static final /* synthetic */ ContextThemeWrapper access$getPopupThemedContext$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.popupThemedContext;
    }

    public static final /* synthetic */ void access$setPopup$p(ControlsUiControllerImpl controlsUiControllerImpl, ListPopupWindow listPopupWindow) {
        controlsUiControllerImpl.popup = listPopupWindow;
    }

    public final Lazy<ControlsController> getControlsController() {
        return this.controlsController;
    }

    public final DelayableExecutor getUiExecutor() {
        return this.uiExecutor;
    }

    public final DelayableExecutor getBgExecutor() {
        return this.bgExecutor;
    }

    public final Lazy<ControlsListingController> getControlsListingController() {
        return this.controlsListingController;
    }

    public final ControlActionCoordinator getControlActionCoordinator() {
        return this.controlActionCoordinator;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        ComponentName componentName = new ComponentName("", "");
        EMPTY_COMPONENT = componentName;
        EMPTY_STRUCTURE = new StructureInfo(componentName, "", new ArrayList());
    }

    private final ControlsListingController.ControlsListingCallback createCallback(Function1<? super List<SelectionItem>, Unit> function1) {
        return new ControlsUiControllerImpl$createCallback$1(this, function1);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v21, resolved type: java.util.Map<com.android.systemui.controls.ui.ControlKey, com.android.systemui.controls.ui.ControlWithState> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.controls.ui.ControlsUiController
    public void show(ViewGroup viewGroup, Runnable runnable, Context context2) {
        Intrinsics.checkNotNullParameter(viewGroup, "parent");
        Intrinsics.checkNotNullParameter(runnable, "onDismiss");
        Intrinsics.checkNotNullParameter(context2, "activityContext");
        Log.d("ControlsUiController", "show()");
        this.parent = viewGroup;
        this.onDismiss = runnable;
        this.activityContext = context2;
        this.hidden = false;
        this.retainCache = false;
        this.controlActionCoordinator.setActivityContext(context2);
        List<StructureInfo> favorites = this.controlsController.get().getFavorites();
        this.allStructures = favorites;
        if (favorites != null) {
            this.selectedStructure = getPreferredStructure(favorites);
            if (this.controlsController.get().addSeedingFavoritesCallback(this.onSeedingComplete)) {
                this.listingCallback = createCallback(new ControlsUiControllerImpl$show$1(this));
            } else {
                if (this.selectedStructure.getControls().isEmpty()) {
                    List<StructureInfo> list = this.allStructures;
                    if (list == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("allStructures");
                        throw null;
                    } else if (list.size() <= 1) {
                        this.listingCallback = createCallback(new ControlsUiControllerImpl$show$2(this));
                    }
                }
                List<ControlInfo> controls = this.selectedStructure.getControls();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controls, 10));
                Iterator<T> it = controls.iterator();
                while (it.hasNext()) {
                    arrayList.add(new ControlWithState(this.selectedStructure.getComponentName(), it.next(), null));
                }
                Map<ControlKey, ControlWithState> map = this.controlsById;
                for (Object obj : arrayList) {
                    map.put(new ControlKey(this.selectedStructure.getComponentName(), ((ControlWithState) obj).getCi().getControlId()), obj);
                }
                this.listingCallback = createCallback(new ControlsUiControllerImpl$show$5(this));
                this.controlsController.get().subscribeToFavorites(this.selectedStructure);
            }
            ControlsListingController controlsListingController2 = this.controlsListingController.get();
            ControlsListingController.ControlsListingCallback controlsListingCallback = this.listingCallback;
            if (controlsListingCallback != null) {
                controlsListingController2.addCallback(controlsListingCallback);
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("listingCallback");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("allStructures");
            throw null;
        }
    }

    private final void reload(ViewGroup viewGroup) {
        if (!this.hidden) {
            ControlsListingController controlsListingController2 = this.controlsListingController.get();
            ControlsListingController.ControlsListingCallback controlsListingCallback = this.listingCallback;
            if (controlsListingCallback != null) {
                controlsListingController2.removeCallback(controlsListingCallback);
                this.controlsController.get().unsubscribe();
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewGroup, "alpha", 1.0f, 0.0f);
                ofFloat.setInterpolator(new AccelerateInterpolator(1.0f));
                ofFloat.setDuration(200L);
                ofFloat.addListener(new ControlsUiControllerImpl$reload$1(this, viewGroup));
                ofFloat.start();
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("listingCallback");
            throw null;
        }
    }

    private final void showSeedingView(List<SelectionItem> list) {
        LayoutInflater from = LayoutInflater.from(this.context);
        int i = R$layout.controls_no_favorites;
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            from.inflate(i, viewGroup, true);
            ViewGroup viewGroup2 = this.parent;
            if (viewGroup2 != null) {
                ((TextView) viewGroup2.requireViewById(R$id.controls_subtitle)).setText(this.context.getResources().getString(R$string.controls_seeding_in_progress));
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("parent");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            throw null;
        }
    }

    private final void showInitialSetupView(List<SelectionItem> list) {
        startProviderSelectorActivity();
        Runnable runnable = this.onDismiss;
        if (runnable != null) {
            runnable.run();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("onDismiss");
            throw null;
        }
    }

    /* access modifiers changed from: public */
    private final void startFavoritingActivity(StructureInfo structureInfo) {
        startTargetedActivity(structureInfo, ControlsFavoritingActivity.class);
    }

    /* access modifiers changed from: public */
    private final void startEditingActivity(StructureInfo structureInfo) {
        startTargetedActivity(structureInfo, ControlsEditingActivity.class);
    }

    private final void startTargetedActivity(StructureInfo structureInfo, Class<?> cls) {
        Context context2 = this.activityContext;
        if (context2 != null) {
            Intent intent = new Intent(context2, cls);
            putIntentExtras(intent, structureInfo);
            startActivity(intent);
            this.retainCache = true;
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("activityContext");
        throw null;
    }

    private final void putIntentExtras(Intent intent, StructureInfo structureInfo) {
        intent.putExtra("extra_app_label", getControlsListingController().get().getAppLabel(structureInfo.getComponentName()));
        intent.putExtra("extra_structure", structureInfo.getStructure());
        intent.putExtra("android.intent.extra.COMPONENT_NAME", structureInfo.getComponentName());
    }

    private final void startProviderSelectorActivity() {
        Context context2 = this.activityContext;
        if (context2 != null) {
            Intent intent = new Intent(context2, ControlsProviderSelectorActivity.class);
            intent.putExtra("back_should_exit", true);
            startActivity(intent);
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("activityContext");
        throw null;
    }

    private final SelectionItem findSelectionItem(StructureInfo structureInfo, List<SelectionItem> list) {
        T t;
        boolean z;
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            T t2 = t;
            if (!Intrinsics.areEqual(t2.getComponentName(), structureInfo.getComponentName()) || !Intrinsics.areEqual(t2.getStructure(), structureInfo.getStructure())) {
                z = false;
                continue;
            } else {
                z = true;
                continue;
            }
            if (z) {
                break;
            }
        }
        return t;
    }

    private final void startActivity(Intent intent) {
        intent.putExtra("extra_animate", true);
        if (this.keyguardStateController.isShowing()) {
            this.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
            return;
        }
        Context context2 = this.activityContext;
        if (context2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("activityContext");
            throw null;
        } else if (context2 != null) {
            context2.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context2, new Pair[0]).toBundle());
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("activityContext");
            throw null;
        }
    }

    private final void showControlsView(List<SelectionItem> list) {
        this.controlViewsById.clear();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10)), 16));
        for (T t : list) {
            linkedHashMap.put(t.getComponentName(), t);
        }
        ArrayList arrayList = new ArrayList();
        List<StructureInfo> list2 = this.allStructures;
        if (list2 != null) {
            for (T t2 : list2) {
                SelectionItem selectionItem = (SelectionItem) linkedHashMap.get(t2.getComponentName());
                SelectionItem copy$default = selectionItem == null ? null : SelectionItem.copy$default(selectionItem, null, t2.getStructure(), null, null, 0, 29, null);
                if (copy$default != null) {
                    arrayList.add(copy$default);
                }
            }
            CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, this.localeComparator);
            SelectionItem findSelectionItem = findSelectionItem(this.selectedStructure, arrayList);
            if (findSelectionItem == null) {
                findSelectionItem = list.get(0);
            }
            this.controlsMetricsLogger.refreshBegin(findSelectionItem.getUid(), !this.keyguardStateController.isUnlocked());
            createListView(findSelectionItem);
            createDropDown(arrayList, findSelectionItem);
            createMenu();
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("allStructures");
        throw null;
    }

    private final void createMenu() {
        String[] strArr = {this.context.getResources().getString(R$string.controls_menu_add), this.context.getResources().getString(R$string.controls_menu_edit)};
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = (T) new ArrayAdapter(this.context, R$layout.controls_more_item, strArr);
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            ImageView imageView = (ImageView) viewGroup.requireViewById(R$id.controls_more);
            imageView.setOnClickListener(new ControlsUiControllerImpl$createMenu$1(this, imageView, ref$ObjectRef));
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        throw null;
    }

    private final void createListView(SelectionItem selectionItem) {
        LayoutInflater from = LayoutInflater.from(this.context);
        int i = R$layout.controls_with_favorites;
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            from.inflate(i, viewGroup, true);
            ViewGroup viewGroup2 = this.parent;
            if (viewGroup2 != null) {
                ImageView imageView = (ImageView) viewGroup2.requireViewById(R$id.controls_close);
                imageView.setOnClickListener(new ControlsUiControllerImpl$createListView$1$1(this));
                imageView.setVisibility(0);
                int findMaxColumns = findMaxColumns();
                ViewGroup viewGroup3 = this.parent;
                if (viewGroup3 != null) {
                    View requireViewById = viewGroup3.requireViewById(R$id.global_actions_controls_list);
                    Objects.requireNonNull(requireViewById, "null cannot be cast to non-null type android.view.ViewGroup");
                    ViewGroup viewGroup4 = (ViewGroup) requireViewById;
                    Intrinsics.checkNotNullExpressionValue(from, "inflater");
                    ViewGroup createRow = createRow(from, viewGroup4);
                    Iterator<T> it = this.selectedStructure.getControls().iterator();
                    while (it.hasNext()) {
                        ControlKey controlKey = new ControlKey(this.selectedStructure.getComponentName(), it.next().getControlId());
                        ControlWithState controlWithState = this.controlsById.get(controlKey);
                        if (controlWithState != null) {
                            if (createRow.getChildCount() == findMaxColumns) {
                                createRow = createRow(from, viewGroup4);
                            }
                            View inflate = from.inflate(R$layout.controls_base_item, createRow, false);
                            Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
                            ViewGroup viewGroup5 = (ViewGroup) inflate;
                            createRow.addView(viewGroup5);
                            if (createRow.getChildCount() == 1) {
                                ViewGroup.LayoutParams layoutParams = viewGroup5.getLayoutParams();
                                Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
                                ((ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(0);
                            }
                            ControlsController controlsController2 = getControlsController().get();
                            Intrinsics.checkNotNullExpressionValue(controlsController2, "controlsController.get()");
                            ControlViewHolder controlViewHolder = new ControlViewHolder(viewGroup5, controlsController2, getUiExecutor(), getBgExecutor(), getControlActionCoordinator(), this.controlsMetricsLogger, selectionItem.getUid());
                            controlViewHolder.bindData(controlWithState, false);
                            this.controlViewsById.put(controlKey, controlViewHolder);
                        }
                    }
                    int size = this.selectedStructure.getControls().size() % findMaxColumns;
                    int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R$dimen.control_spacing);
                    for (int i2 = size == 0 ? 0 : findMaxColumns - size; i2 > 0; i2--) {
                        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, 0, 1.0f);
                        layoutParams2.setMarginStart(dimensionPixelSize);
                        createRow.addView(new Space(this.context), layoutParams2);
                    }
                    return;
                }
                Intrinsics.throwUninitializedPropertyAccessException("parent");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        throw null;
    }

    private final int findMaxColumns() {
        int i;
        Resources resources = this.context.getResources();
        int integer = resources.getInteger(R$integer.controls_max_columns);
        int integer2 = resources.getInteger(R$integer.controls_max_columns_adjust_below_width_dp);
        TypedValue typedValue = new TypedValue();
        boolean z = true;
        resources.getValue(R$dimen.controls_max_columns_adjust_above_font_scale, typedValue, true);
        float f = typedValue.getFloat();
        Configuration configuration = resources.getConfiguration();
        if (configuration.orientation != 1) {
            z = false;
        }
        return (!z || (i = configuration.screenWidthDp) == 0 || i > integer2 || configuration.fontScale < f) ? integer : integer - 1;
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public StructureInfo getPreferredStructure(List<StructureInfo> list) {
        ComponentName componentName;
        boolean z;
        Intrinsics.checkNotNullParameter(list, "structures");
        if (list.isEmpty()) {
            return EMPTY_STRUCTURE;
        }
        T t = null;
        String string = this.sharedPreferences.getString("controls_component", null);
        if (string == null) {
            componentName = null;
        } else {
            componentName = ComponentName.unflattenFromString(string);
        }
        if (componentName == null) {
            componentName = EMPTY_COMPONENT;
        }
        String string2 = this.sharedPreferences.getString("controls_structure", "");
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            T next = it.next();
            T t2 = next;
            if (!Intrinsics.areEqual(componentName, t2.getComponentName()) || !Intrinsics.areEqual(string2, t2.getStructure())) {
                z = false;
                continue;
            } else {
                z = true;
                continue;
            }
            if (z) {
                t = next;
                break;
            }
        }
        T t3 = t;
        return t3 == null ? list.get(0) : t3;
    }

    private final void updatePreferences(StructureInfo structureInfo) {
        if (!Intrinsics.areEqual(structureInfo, EMPTY_STRUCTURE)) {
            this.sharedPreferences.edit().putString("controls_component", structureInfo.getComponentName().flattenToString()).putString("controls_structure", structureInfo.getStructure().toString()).commit();
        }
    }

    /* access modifiers changed from: public */
    private final void switchAppOrStructure(SelectionItem selectionItem) {
        boolean z;
        List<StructureInfo> list = this.allStructures;
        if (list != null) {
            for (T t : list) {
                if (!Intrinsics.areEqual(t.getStructure(), selectionItem.getStructure()) || !Intrinsics.areEqual(t.getComponentName(), selectionItem.getComponentName())) {
                    z = false;
                    continue;
                } else {
                    z = true;
                    continue;
                }
                if (z) {
                    if (!Intrinsics.areEqual(t, this.selectedStructure)) {
                        this.selectedStructure = t;
                        updatePreferences(t);
                        ViewGroup viewGroup = this.parent;
                        if (viewGroup != null) {
                            reload(viewGroup);
                            return;
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("parent");
                            throw null;
                        }
                    } else {
                        return;
                    }
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        Intrinsics.throwUninitializedPropertyAccessException("allStructures");
        throw null;
    }

    public void closeDialogs(boolean z) {
        if (z) {
            ListPopupWindow listPopupWindow = this.popup;
            if (listPopupWindow != null) {
                listPopupWindow.dismissImmediate();
            }
        } else {
            ListPopupWindow listPopupWindow2 = this.popup;
            if (listPopupWindow2 != null) {
                listPopupWindow2.dismiss();
            }
        }
        this.popup = null;
        for (Map.Entry<ControlKey, ControlViewHolder> entry : this.controlViewsById.entrySet()) {
            entry.getValue().dismiss();
        }
        this.controlActionCoordinator.closeDialogs();
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public void hide() {
        this.hidden = true;
        closeDialogs(true);
        this.controlsController.get().unsubscribe();
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            this.controlsById.clear();
            this.controlViewsById.clear();
            ControlsListingController controlsListingController2 = this.controlsListingController.get();
            ControlsListingController.ControlsListingCallback controlsListingCallback = this.listingCallback;
            if (controlsListingCallback != null) {
                controlsListingController2.removeCallback(controlsListingCallback);
                if (!this.retainCache) {
                    RenderInfo.Companion.clearCache();
                    return;
                }
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("listingCallback");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        throw null;
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public void onRefreshState(ComponentName componentName, List<Control> list) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(list, "controls");
        boolean z = !this.keyguardStateController.isUnlocked();
        for (T t : list) {
            Map<ControlKey, ControlWithState> map = this.controlsById;
            String controlId = t.getControlId();
            Intrinsics.checkNotNullExpressionValue(controlId, "c.getControlId()");
            ControlWithState controlWithState = map.get(new ControlKey(componentName, controlId));
            if (controlWithState != null) {
                Log.d("ControlsUiController", Intrinsics.stringPlus("onRefreshState() for id: ", t.getControlId()));
                CustomIconCache customIconCache = this.iconCache;
                String controlId2 = t.getControlId();
                Intrinsics.checkNotNullExpressionValue(controlId2, "c.controlId");
                customIconCache.store(componentName, controlId2, t.getCustomIcon());
                ControlWithState controlWithState2 = new ControlWithState(componentName, controlWithState.getCi(), t);
                String controlId3 = t.getControlId();
                Intrinsics.checkNotNullExpressionValue(controlId3, "c.getControlId()");
                ControlKey controlKey = new ControlKey(componentName, controlId3);
                this.controlsById.put(controlKey, controlWithState2);
                ControlViewHolder controlViewHolder = this.controlViewsById.get(controlKey);
                if (controlViewHolder != null) {
                    getUiExecutor().execute(new ControlsUiControllerImpl$onRefreshState$1$1$1$1(controlViewHolder, controlWithState2, z));
                }
            }
        }
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public void onActionResponse(ComponentName componentName, String str, int i) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        Intrinsics.checkNotNullParameter(str, "controlId");
        this.uiExecutor.execute(new ControlsUiControllerImpl$onActionResponse$1(this, new ControlKey(componentName, str), i));
    }

    private final ViewGroup createRow(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View inflate = layoutInflater.inflate(R$layout.controls_row, viewGroup, false);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup2 = (ViewGroup) inflate;
        viewGroup.addView(viewGroup2);
        return viewGroup2;
    }

    private final void createDropDown(List<SelectionItem> list, SelectionItem selectionItem) {
        for (T t : list) {
            RenderInfo.Companion.registerComponentIcon(t.getComponentName(), t.getIcon());
        }
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        T t2 = (T) new ItemAdapter(this.context, R$layout.controls_spinner_item);
        t2.addAll(list);
        Unit unit = Unit.INSTANCE;
        ref$ObjectRef.element = t2;
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            TextView textView = (TextView) viewGroup.requireViewById(R$id.app_or_structure_spinner);
            textView.setText(selectionItem.getTitle());
            Drawable background = textView.getBackground();
            Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
            ((LayerDrawable) background).getDrawable(0).setTint(textView.getContext().getResources().getColor(R$color.control_spinner_dropdown, null));
            if (list.size() == 1) {
                textView.setBackground(null);
                return;
            }
            ViewGroup viewGroup2 = this.parent;
            if (viewGroup2 != null) {
                ViewGroup viewGroup3 = (ViewGroup) viewGroup2.requireViewById(R$id.controls_header);
                viewGroup3.setOnClickListener(new ControlsUiControllerImpl$createDropDown$2(this, viewGroup3, ref$ObjectRef));
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        throw null;
    }
}

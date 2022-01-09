package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.systemui.Prefs;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.TooltipManager;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.util.LifecycleActivity;
import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ControlsFavoritingActivity extends LifecycleActivity {
    public static final Companion Companion = new Companion(null);
    private CharSequence appName;
    private final BroadcastDispatcher broadcastDispatcher;
    private Runnable cancelLoadRunnable;
    private Comparator<StructureContainer> comparator;
    private ComponentName component;
    private final ControlsControllerImpl controller;
    private final ControlsFavoritingActivity$controlsModelCallback$1 controlsModelCallback;
    private final ControlsFavoritingActivity$currentUserTracker$1 currentUserTracker;
    private View doneButton;
    private final Executor executor;
    private boolean fromProviderSelector;
    private boolean isPagerLoaded;
    private List<StructureContainer> listOfStructures = CollectionsKt__CollectionsKt.emptyList();
    private final ControlsFavoritingActivity$listingCallback$1 listingCallback;
    private final ControlsListingController listingController;
    private TooltipManager mTooltipManager;
    private View otherAppsButton;
    private ManagementPageIndicator pageIndicator;
    private TextView statusText;
    private CharSequence structureExtra;
    private ViewPager2 structurePager;
    private TextView subtitleView;
    private TextView titleView;
    private final ControlsUiController uiController;

    public ControlsFavoritingActivity(Executor executor2, ControlsControllerImpl controlsControllerImpl, ControlsListingController controlsListingController, BroadcastDispatcher broadcastDispatcher2, ControlsUiController controlsUiController) {
        Intrinsics.checkNotNullParameter(executor2, "executor");
        Intrinsics.checkNotNullParameter(controlsControllerImpl, "controller");
        Intrinsics.checkNotNullParameter(controlsListingController, "listingController");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        Intrinsics.checkNotNullParameter(controlsUiController, "uiController");
        this.executor = executor2;
        this.controller = controlsControllerImpl;
        this.listingController = controlsListingController;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.uiController = controlsUiController;
        this.currentUserTracker = new ControlsFavoritingActivity$currentUserTracker$1(this, broadcastDispatcher2);
        this.listingCallback = new ControlsFavoritingActivity$listingCallback$1(this);
        this.controlsModelCallback = new ControlsFavoritingActivity$controlsModelCallback$1(this);
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public void onBackPressed() {
        if (!this.fromProviderSelector) {
            openControlsOrigin();
        }
        animateExitAndFinish();
    }

    @Override // com.android.systemui.util.LifecycleActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Collator instance = Collator.getInstance(getResources().getConfiguration().getLocales().get(0));
        Intrinsics.checkNotNullExpressionValue(instance, "collator");
        this.comparator = new ControlsFavoritingActivity$onCreate$$inlined$compareBy$1(instance);
        this.appName = getIntent().getCharSequenceExtra("extra_app_label");
        this.structureExtra = getIntent().getCharSequenceExtra("extra_structure");
        this.component = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        this.fromProviderSelector = getIntent().getBooleanExtra("extra_from_provider_selector", false);
        bindViews();
    }

    private final void loadControls() {
        ComponentName componentName = this.component;
        if (componentName != null) {
            TextView textView = this.statusText;
            if (textView != null) {
                textView.setText(getResources().getText(17040507));
                this.controller.loadForComponent(componentName, new ControlsFavoritingActivity$loadControls$1$1(this, getResources().getText(R$string.controls_favorite_other_zone_header)), new ControlsFavoritingActivity$loadControls$1$2(this));
                return;
            }
            Intrinsics.throwUninitializedPropertyAccessException("statusText");
            throw null;
        }
    }

    private final void setUpPager() {
        ViewPager2 viewPager2 = this.structurePager;
        if (viewPager2 != null) {
            viewPager2.setAlpha(0.0f);
            ManagementPageIndicator managementPageIndicator = this.pageIndicator;
            if (managementPageIndicator != null) {
                managementPageIndicator.setAlpha(0.0f);
                ViewPager2 viewPager22 = this.structurePager;
                if (viewPager22 != null) {
                    viewPager22.setAdapter(new StructureAdapter(CollectionsKt__CollectionsKt.emptyList()));
                    viewPager22.registerOnPageChangeCallback(new ControlsFavoritingActivity$setUpPager$1$1(this));
                    return;
                }
                Intrinsics.throwUninitializedPropertyAccessException("structurePager");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("pageIndicator");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("structurePager");
        throw null;
    }

    private final void bindViews() {
        setContentView(R$layout.controls_management);
        Lifecycle lifecycle = getLifecycle();
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        View requireViewById = requireViewById(R$id.controls_management_root);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById<ViewGroup>(R.id.controls_management_root)");
        Window window = getWindow();
        Intrinsics.checkNotNullExpressionValue(window, "window");
        Intent intent = getIntent();
        Intrinsics.checkNotNullExpressionValue(intent, "intent");
        lifecycle.addObserver(controlsAnimations.observerForAnimations((ViewGroup) requireViewById, window, intent));
        ViewStub viewStub = (ViewStub) requireViewById(R$id.stub);
        viewStub.setLayoutResource(R$layout.controls_management_favorites);
        viewStub.inflate();
        View requireViewById2 = requireViewById(R$id.status_message);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(R.id.status_message)");
        this.statusText = (TextView) requireViewById2;
        if (shouldShowTooltip()) {
            TextView textView = this.statusText;
            if (textView != null) {
                Context context = textView.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "statusText.context");
                TooltipManager tooltipManager = new TooltipManager(context, "ControlsStructureSwipeTooltipCount", 2, false, 8, null);
                this.mTooltipManager = tooltipManager;
                addContentView(tooltipManager.getLayout(), new FrameLayout.LayoutParams(-2, -2, 51));
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("statusText");
                throw null;
            }
        }
        View requireViewById3 = requireViewById(R$id.structure_page_indicator);
        ManagementPageIndicator managementPageIndicator = (ManagementPageIndicator) requireViewById3;
        managementPageIndicator.setVisibilityListener(new ControlsFavoritingActivity$bindViews$2$1(this));
        Unit unit = Unit.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById<ManagementPageIndicator>(\n            R.id.structure_page_indicator).apply {\n            visibilityListener = {\n                if (it != View.VISIBLE) {\n                    mTooltipManager?.hide(true)\n                }\n            }\n        }");
        this.pageIndicator = managementPageIndicator;
        CharSequence charSequence = this.structureExtra;
        if (charSequence == null && (charSequence = this.appName) == null) {
            charSequence = getResources().getText(R$string.controls_favorite_default_title);
        }
        View requireViewById4 = requireViewById(R$id.title);
        TextView textView2 = (TextView) requireViewById4;
        textView2.setText(charSequence);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "requireViewById<TextView>(R.id.title).apply {\n            text = title\n        }");
        this.titleView = textView2;
        View requireViewById5 = requireViewById(R$id.subtitle);
        TextView textView3 = (TextView) requireViewById5;
        textView3.setText(textView3.getResources().getText(R$string.controls_favorite_subtitle));
        Intrinsics.checkNotNullExpressionValue(requireViewById5, "requireViewById<TextView>(R.id.subtitle).apply {\n            text = resources.getText(R.string.controls_favorite_subtitle)\n        }");
        this.subtitleView = textView3;
        View requireViewById6 = requireViewById(R$id.structure_pager);
        Intrinsics.checkNotNullExpressionValue(requireViewById6, "requireViewById<ViewPager2>(R.id.structure_pager)");
        ViewPager2 viewPager2 = (ViewPager2) requireViewById6;
        this.structurePager = viewPager2;
        if (viewPager2 != null) {
            viewPager2.registerOnPageChangeCallback(new ControlsFavoritingActivity$bindViews$5(this));
            bindButtons();
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("structurePager");
        throw null;
    }

    /* access modifiers changed from: public */
    private final void animateExitAndFinish() {
        ViewGroup viewGroup = (ViewGroup) requireViewById(R$id.controls_management_root);
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(viewGroup, "rootView");
        ControlsAnimations.exitAnimation(viewGroup, new ControlsFavoritingActivity$animateExitAndFinish$1(this)).start();
    }

    private final void bindButtons() {
        View requireViewById = requireViewById(R$id.other_apps);
        Button button = (Button) requireViewById;
        button.setOnClickListener(new ControlsFavoritingActivity$bindButtons$1$1(this, button));
        Unit unit = Unit.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById<Button>(R.id.other_apps).apply {\n            setOnClickListener {\n                if (doneButton.isEnabled) {\n                    // The user has made changes\n                    Toast.makeText(\n                            applicationContext,\n                            R.string.controls_favorite_toast_no_changes,\n                            Toast.LENGTH_SHORT\n                            ).show()\n                }\n                startActivity(\n                    Intent(context, ControlsProviderSelectorActivity::class.java),\n                    ActivityOptions\n                        .makeSceneTransitionAnimation(this@ControlsFavoritingActivity).toBundle()\n                )\n                animateExitAndFinish()\n            }\n        }");
        this.otherAppsButton = requireViewById;
        View requireViewById2 = requireViewById(R$id.done);
        Button button2 = (Button) requireViewById2;
        button2.setEnabled(false);
        button2.setOnClickListener(new ControlsFavoritingActivity$bindButtons$2$1(this));
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById<Button>(R.id.done).apply {\n            isEnabled = false\n            setOnClickListener {\n                if (component == null) return@setOnClickListener\n                listOfStructures.forEach {\n                    val favoritesForStorage = it.model.favorites\n                    controller.replaceFavoritesForStructure(\n                        StructureInfo(component!!, it.structureName, favoritesForStorage)\n                    )\n                }\n                animateExitAndFinish()\n                openControlsOrigin()\n            }\n        }");
        this.doneButton = requireViewById2;
    }

    /* access modifiers changed from: public */
    private final void openControlsOrigin() {
        startActivity(new Intent(getApplicationContext(), ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, new Pair[0]).toBundle());
    }

    @Override // com.android.systemui.util.LifecycleActivity
    public void onPause() {
        super.onPause();
        TooltipManager tooltipManager = this.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(false);
        }
    }

    @Override // com.android.systemui.util.LifecycleActivity
    public void onStart() {
        super.onStart();
        this.listingController.addCallback(this.listingCallback);
        this.currentUserTracker.startTracking();
    }

    @Override // com.android.systemui.util.LifecycleActivity
    public void onResume() {
        super.onResume();
        if (!this.isPagerLoaded) {
            setUpPager();
            loadControls();
            this.isPagerLoaded = true;
        }
    }

    @Override // com.android.systemui.util.LifecycleActivity
    public void onStop() {
        super.onStop();
        this.listingController.removeCallback(this.listingCallback);
        this.currentUserTracker.stopTracking();
    }

    public void onConfigurationChanged(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "newConfig");
        super.onConfigurationChanged(configuration);
        TooltipManager tooltipManager = this.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(false);
        }
    }

    @Override // com.android.systemui.util.LifecycleActivity
    public void onDestroy() {
        Runnable runnable = this.cancelLoadRunnable;
        if (runnable != null) {
            runnable.run();
        }
        super.onDestroy();
    }

    private final boolean shouldShowTooltip() {
        return Prefs.getInt(getApplicationContext(), "ControlsStructureSwipeTooltipCount", 0) < 2;
    }
}

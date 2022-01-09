package com.android.systemui.controls.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.management.ControlsAnimations;
import com.android.systemui.util.LifecycleActivity;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsActivity.kt */
public final class ControlsActivity extends LifecycleActivity {
    private final BroadcastDispatcher broadcastDispatcher;
    private BroadcastReceiver broadcastReceiver;
    private ViewGroup parent;
    private final ControlsUiController uiController;

    public ControlsActivity(ControlsUiController controlsUiController, BroadcastDispatcher broadcastDispatcher2) {
        Intrinsics.checkNotNullParameter(controlsUiController, "uiController");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        this.uiController = controlsUiController;
        this.broadcastDispatcher = broadcastDispatcher2;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.LifecycleActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.controls_fullscreen);
        Lifecycle lifecycle = getLifecycle();
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        int i = R$id.control_detail_root;
        View requireViewById = requireViewById(i);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById<ViewGroup>(R.id.control_detail_root)");
        Window window = getWindow();
        Intrinsics.checkNotNullExpressionValue(window, "window");
        Intent intent = getIntent();
        Intrinsics.checkNotNullExpressionValue(intent, "intent");
        lifecycle.addObserver(controlsAnimations.observerForAnimations((ViewGroup) requireViewById, window, intent));
        ((ViewGroup) requireViewById(i)).setOnApplyWindowInsetsListener(ControlsActivity$onCreate$1$1.INSTANCE);
        initBroadcastReceiver();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.LifecycleActivity
    public void onResume() {
        super.onResume();
        View requireViewById = requireViewById(R$id.global_actions_controls);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById<ViewGroup>(R.id.global_actions_controls)");
        ViewGroup viewGroup = (ViewGroup) requireViewById;
        this.parent = viewGroup;
        if (viewGroup != null) {
            viewGroup.setAlpha(0.0f);
            ControlsUiController controlsUiController = this.uiController;
            ViewGroup viewGroup2 = this.parent;
            if (viewGroup2 != null) {
                controlsUiController.show(viewGroup2, new ControlsActivity$onResume$1(this), this);
                ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
                ViewGroup viewGroup3 = this.parent;
                if (viewGroup3 != null) {
                    controlsAnimations.enterAnimation(viewGroup3).start();
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("parent");
                    throw null;
                }
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("parent");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            throw null;
        }
    }

    public void onBackPressed() {
        finish();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.LifecycleActivity
    public void onPause() {
        super.onPause();
        this.uiController.hide();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.LifecycleActivity
    public void onDestroy() {
        super.onDestroy();
        BroadcastDispatcher broadcastDispatcher2 = this.broadcastDispatcher;
        BroadcastReceiver broadcastReceiver2 = this.broadcastReceiver;
        if (broadcastReceiver2 != null) {
            broadcastDispatcher2.unregisterReceiver(broadcastReceiver2);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("broadcastReceiver");
            throw null;
        }
    }

    private final void initBroadcastReceiver() {
        this.broadcastReceiver = new ControlsActivity$initBroadcastReceiver$1(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        BroadcastDispatcher broadcastDispatcher2 = this.broadcastDispatcher;
        BroadcastReceiver broadcastReceiver2 = this.broadcastReceiver;
        if (broadcastReceiver2 != null) {
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher2, broadcastReceiver2, intentFilter, null, null, 12, null);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("broadcastReceiver");
            throw null;
        }
    }
}

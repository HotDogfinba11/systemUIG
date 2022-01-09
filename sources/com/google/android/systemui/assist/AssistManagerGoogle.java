package com.google.android.systemui.assist;

import android.content.Context;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.IWindowManager;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import com.android.systemui.assist.PhoneStateMonitor;
import com.android.systemui.assist.ui.DefaultUiController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import com.google.android.systemui.assist.uihints.GoogleDefaultUiController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.NgaUiController;
import dagger.Lazy;
import java.util.Objects;

public class AssistManagerGoogle extends AssistManager {
    private final AssistantPresenceHandler mAssistantPresenceHandler;
    private boolean mCheckAssistantStatus = true;
    private final GoogleDefaultUiController mDefaultUiController;
    private boolean mGoogleIsAssistant;
    private int mNavigationMode;
    private boolean mNgaIsAssistant;
    private final NgaMessageHandler mNgaMessageHandler;
    private final NgaUiController mNgaUiController;
    private final Runnable mOnProcessBundle;
    private final OpaEnabledReceiver mOpaEnabledReceiver;
    private boolean mSqueezeSetUp;
    private AssistManager.UiController mUiController;
    private final Handler mUiHandler;
    private final IWindowManager mWindowManagerService;

    @Override // com.android.systemui.assist.AssistManager
    public boolean shouldShowOrb() {
        return false;
    }

    public AssistManagerGoogle(DeviceProvisionedController deviceProvisionedController, Context context, AssistUtils assistUtils, NgaUiController ngaUiController, CommandQueue commandQueue, OpaEnabledReceiver opaEnabledReceiver, PhoneStateMonitor phoneStateMonitor, OverviewProxyService overviewProxyService, OpaEnabledDispatcher opaEnabledDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, NavigationModeController navigationModeController, ConfigurationController configurationController, AssistantPresenceHandler assistantPresenceHandler, NgaMessageHandler ngaMessageHandler, Lazy<SysUiState> lazy, Handler handler, DefaultUiController defaultUiController, GoogleDefaultUiController googleDefaultUiController, IWindowManager iWindowManager, AssistLogger assistLogger) {
        super(deviceProvisionedController, context, assistUtils, commandQueue, phoneStateMonitor, overviewProxyService, configurationController, lazy, defaultUiController, assistLogger);
        this.mUiHandler = handler;
        this.mOpaEnabledReceiver = opaEnabledReceiver;
        addOpaEnabledListener(opaEnabledDispatcher);
        keyguardUpdateMonitor.registerCallback(new KeyguardUpdateMonitorCallback() {
            /* class com.google.android.systemui.assist.AssistManagerGoogle.AnonymousClass1 */

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onUserSwitching(int i) {
                AssistManagerGoogle.this.mOpaEnabledReceiver.onUserSwitching(i);
            }
        });
        this.mNgaUiController = ngaUiController;
        this.mDefaultUiController = googleDefaultUiController;
        this.mUiController = googleDefaultUiController;
        this.mNavigationMode = navigationModeController.addListener(new AssistManagerGoogle$$ExternalSyntheticLambda0(this));
        this.mAssistantPresenceHandler = assistantPresenceHandler;
        assistantPresenceHandler.registerAssistantPresenceChangeListener(new AssistManagerGoogle$$ExternalSyntheticLambda1(this));
        this.mNgaMessageHandler = ngaMessageHandler;
        this.mOnProcessBundle = new AssistManagerGoogle$$ExternalSyntheticLambda3(this);
        this.mWindowManagerService = iWindowManager;
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$0(int i) {
        this.mNavigationMode = i;
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$1(boolean z, boolean z2) {
        if (!(this.mGoogleIsAssistant == z && this.mNgaIsAssistant == z2)) {
            if (!z2) {
                if (!this.mUiController.equals(this.mDefaultUiController)) {
                    AssistManager.UiController uiController = this.mUiController;
                    this.mUiController = this.mDefaultUiController;
                    Handler handler = this.mUiHandler;
                    Objects.requireNonNull(uiController);
                    handler.post(new AssistManagerGoogle$$ExternalSyntheticLambda2(uiController));
                }
                this.mDefaultUiController.setGoogleAssistant(z);
            } else if (!this.mUiController.equals(this.mNgaUiController)) {
                AssistManager.UiController uiController2 = this.mUiController;
                this.mUiController = this.mNgaUiController;
                Handler handler2 = this.mUiHandler;
                Objects.requireNonNull(uiController2);
                handler2.post(new AssistManagerGoogle$$ExternalSyntheticLambda2(uiController2));
            }
            this.mGoogleIsAssistant = z;
            this.mNgaIsAssistant = z2;
        }
        this.mCheckAssistantStatus = false;
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$2() {
        this.mAssistantPresenceHandler.requestAssistantPresenceUpdate();
        this.mCheckAssistantStatus = false;
    }

    public boolean shouldUseHomeButtonAnimations() {
        return !QuickStepContract.isGesturalMode(this.mNavigationMode);
    }

    @Override // com.android.systemui.assist.AssistManager
    public void registerVoiceInteractionSessionListener() {
        this.mAssistUtils.registerVoiceInteractionSessionListener(new IVoiceInteractionSessionListener.Stub() {
            /* class com.google.android.systemui.assist.AssistManagerGoogle.AnonymousClass2 */

            public void onVoiceSessionShown() {
                ((AssistManager) AssistManagerGoogle.this).mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_UPDATE);
            }

            public void onVoiceSessionHidden() {
                ((AssistManager) AssistManagerGoogle.this).mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_CLOSE);
            }

            public void onSetUiHints(Bundle bundle) {
                String string = bundle.getString("action");
                if ("set_assist_gesture_constrained".equals(string)) {
                    ((SysUiState) ((AssistManager) AssistManagerGoogle.this).mSysUiState.get()).setFlag(8192, bundle.getBoolean("should_constrain", false)).commitUpdate(0);
                } else if ("show_global_actions".equals(string)) {
                    try {
                        AssistManagerGoogle.this.mWindowManagerService.showGlobalActions();
                    } catch (RemoteException e) {
                        Log.e("AssistManagerGoogle", "showGlobalActions failed", e);
                    }
                } else {
                    AssistManagerGoogle.this.mNgaMessageHandler.lambda$processBundle$1(bundle, AssistManagerGoogle.this.mOnProcessBundle);
                }
            }
        });
    }

    @Override // com.android.systemui.assist.AssistManager
    public void onInvocationProgress(int i, float f) {
        if (f == 0.0f || f == 1.0f) {
            this.mCheckAssistantStatus = true;
            if (i == 2) {
                checkSqueezeGestureStatus();
            }
        }
        if (this.mCheckAssistantStatus) {
            this.mAssistantPresenceHandler.requestAssistantPresenceUpdate();
            this.mCheckAssistantStatus = false;
        }
        if (i != 2 || this.mSqueezeSetUp) {
            this.mUiController.onInvocationProgress(i, f);
        }
    }

    @Override // com.android.systemui.assist.AssistManager
    public void onGestureCompletion(float f) {
        this.mCheckAssistantStatus = true;
        this.mUiController.onGestureCompletion(f / this.mContext.getResources().getDisplayMetrics().density);
    }

    @Override // com.android.systemui.assist.AssistManager
    public void logStartAssistLegacy(int i, int i2) {
        MetricsLogger.action(new LogMaker(1716).setType(1).setSubtype(((this.mAssistantPresenceHandler.isNgaAssistant() ? 1 : 0) << 8) | toLoggingSubType(i, i2)));
    }

    public void addOpaEnabledListener(OpaEnabledListener opaEnabledListener) {
        this.mOpaEnabledReceiver.addOpaEnabledListener(opaEnabledListener);
    }

    public boolean isActiveAssistantNga() {
        return this.mNgaIsAssistant;
    }

    public void dispatchOpaEnabledState() {
        this.mOpaEnabledReceiver.dispatchOpaEnabledState();
    }

    private void checkSqueezeGestureStatus() {
        boolean z = false;
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "assist_gesture_setup_complete", 0) == 1) {
            z = true;
        }
        this.mSqueezeSetUp = z;
    }
}

package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.biometrics.PromptInfo;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.UserManager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.android.systemui.Dependency;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.AuthBiometricView;
import com.android.systemui.biometrics.AuthCredentialView;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import java.util.Iterator;
import java.util.List;

public class AuthContainerView extends LinearLayout implements AuthDialog, WakefulnessLifecycle.Observer {
    final ImageView mBackgroundView;
    final BiometricCallback mBiometricCallback;
    final ScrollView mBiometricScrollView;
    AuthBiometricView mBiometricView;
    final Config mConfig;
    int mContainerState = 0;
    byte[] mCredentialAttestation;
    private final CredentialCallback mCredentialCallback;
    AuthCredentialView mCredentialView;
    final int mEffectiveUserId;
    private final List<FaceSensorPropertiesInternal> mFaceProps;
    private final List<FingerprintSensorPropertiesInternal> mFpProps;
    final FrameLayout mFrameLayout;
    private final Handler mHandler;
    private final Injector mInjector;
    private final Interpolator mLinearOutSlowIn;
    private final AuthPanelController mPanelController;
    private final View mPanelView;
    Integer mPendingCallbackReason;
    private final float mTranslationY;
    final WakefulnessLifecycle mWakefulnessLifecycle;
    private final WindowManager mWindowManager;
    private final IBinder mWindowToken = new Binder();

    public static class Config {
        AuthDialogCallback mCallback;
        Context mContext;
        boolean mCredentialAllowed;
        int mMultiSensorConfig;
        String mOpPackageName;
        long mOperationId;
        PromptInfo mPromptInfo;
        long mRequestId;
        boolean mRequireConfirmation;
        int[] mSensorIds;
        boolean mSkipIntro;
        int mUserId;

        Config() {
        }
    }

    public static class Builder {
        Config mConfig;

        public Builder(Context context) {
            Config config = new Config();
            this.mConfig = config;
            config.mContext = context;
        }

        public Builder setCallback(AuthDialogCallback authDialogCallback) {
            this.mConfig.mCallback = authDialogCallback;
            return this;
        }

        public Builder setPromptInfo(PromptInfo promptInfo) {
            this.mConfig.mPromptInfo = promptInfo;
            return this;
        }

        public Builder setRequireConfirmation(boolean z) {
            this.mConfig.mRequireConfirmation = z;
            return this;
        }

        public Builder setUserId(int i) {
            this.mConfig.mUserId = i;
            return this;
        }

        public Builder setOpPackageName(String str) {
            this.mConfig.mOpPackageName = str;
            return this;
        }

        public Builder setSkipIntro(boolean z) {
            this.mConfig.mSkipIntro = z;
            return this;
        }

        public Builder setOperationId(long j) {
            this.mConfig.mOperationId = j;
            return this;
        }

        public Builder setRequestId(long j) {
            this.mConfig.mRequestId = j;
            return this;
        }

        public Builder setMultiSensorConfig(int i) {
            this.mConfig.mMultiSensorConfig = i;
            return this;
        }

        public AuthContainerView build(int[] iArr, boolean z, List<FingerprintSensorPropertiesInternal> list, List<FaceSensorPropertiesInternal> list2) {
            Config config = this.mConfig;
            config.mSensorIds = iArr;
            config.mCredentialAllowed = z;
            return new AuthContainerView(this.mConfig, new Injector(), list, list2);
        }
    }

    public static class Injector {
        public int getAnimateCredentialStartDelayMs() {
            return 300;
        }

        public ScrollView getBiometricScrollView(FrameLayout frameLayout) {
            return (ScrollView) frameLayout.findViewById(R$id.biometric_scrollview);
        }

        public FrameLayout inflateContainerView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            return (FrameLayout) layoutInflater.inflate(R$layout.auth_container_view, viewGroup, false);
        }

        public AuthPanelController getPanelController(Context context, View view) {
            return new AuthPanelController(context, view);
        }

        public ImageView getBackgroundView(FrameLayout frameLayout) {
            return (ImageView) frameLayout.findViewById(R$id.background);
        }

        public View getPanelView(FrameLayout frameLayout) {
            return frameLayout.findViewById(R$id.panel);
        }

        public UserManager getUserManager(Context context) {
            return UserManager.get(context);
        }

        public int getCredentialType(Context context, int i) {
            return Utils.getCredentialType(context, i);
        }
    }

    public final class BiometricCallback implements AuthBiometricView.Callback {
        BiometricCallback() {
            AuthContainerView.this = r1;
        }

        /* access modifiers changed from: public */
        private /* synthetic */ void lambda$onAction$0() {
            AuthContainerView.this.addCredentialView(false, true);
        }

        @Override // com.android.systemui.biometrics.AuthBiometricView.Callback
        public void onAction(int i) {
            switch (i) {
                case 1:
                    AuthContainerView.this.animateAway(4);
                    return;
                case 2:
                    AuthContainerView.this.sendEarlyUserCanceled();
                    AuthContainerView.this.animateAway(1);
                    return;
                case 3:
                    AuthContainerView.this.animateAway(2);
                    return;
                case 4:
                    AuthContainerView.this.mConfig.mCallback.onTryAgainPressed();
                    return;
                case 5:
                    AuthContainerView.this.animateAway(5);
                    return;
                case 6:
                    AuthContainerView.this.mConfig.mCallback.onDeviceCredentialPressed();
                    AuthContainerView.this.mHandler.postDelayed(new AuthContainerView$BiometricCallback$$ExternalSyntheticLambda0(this), (long) AuthContainerView.this.mInjector.getAnimateCredentialStartDelayMs());
                    return;
                case 7:
                    AuthContainerView.this.mConfig.mCallback.onStartFingerprintNow();
                    return;
                default:
                    Log.e("BiometricPrompt/AuthContainerView", "Unhandled action: " + i);
                    return;
            }
        }
    }

    public final class CredentialCallback implements AuthCredentialView.Callback {
        CredentialCallback() {
            AuthContainerView.this = r1;
        }

        @Override // com.android.systemui.biometrics.AuthCredentialView.Callback
        public void onCredentialMatched(byte[] bArr) {
            AuthContainerView authContainerView = AuthContainerView.this;
            authContainerView.mCredentialAttestation = bArr;
            authContainerView.animateAway(7);
        }
    }

    AuthContainerView(Config config, Injector injector, List<FingerprintSensorPropertiesInternal> list, List<FaceSensorPropertiesInternal> list2) {
        super(config.mContext);
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal;
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal2;
        this.mConfig = config;
        this.mInjector = injector;
        this.mFpProps = list;
        this.mFaceProps = list2;
        this.mEffectiveUserId = injector.getUserManager(((LinearLayout) this).mContext).getCredentialOwnerProfile(config.mUserId);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mWindowManager = (WindowManager) ((LinearLayout) this).mContext.getSystemService(WindowManager.class);
        this.mWakefulnessLifecycle = (WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class);
        this.mTranslationY = getResources().getDimension(R$dimen.biometric_dialog_animation_translation_offset);
        this.mLinearOutSlowIn = Interpolators.LINEAR_OUT_SLOW_IN;
        this.mBiometricCallback = new BiometricCallback();
        this.mCredentialCallback = new CredentialCallback();
        LayoutInflater from = LayoutInflater.from(((LinearLayout) this).mContext);
        FrameLayout inflateContainerView = injector.inflateContainerView(from, this);
        this.mFrameLayout = inflateContainerView;
        View panelView = injector.getPanelView(inflateContainerView);
        this.mPanelView = panelView;
        this.mPanelController = injector.getPanelController(((LinearLayout) this).mContext, panelView);
        int length = config.mSensorIds.length;
        if (Utils.isBiometricAllowed(config.mPromptInfo)) {
            if (length == 1) {
                int i = config.mSensorIds[0];
                if (Utils.containsSensorId(list, i)) {
                    Iterator<FingerprintSensorPropertiesInternal> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            fingerprintSensorPropertiesInternal2 = null;
                            break;
                        }
                        fingerprintSensorPropertiesInternal2 = it.next();
                        if (fingerprintSensorPropertiesInternal2.sensorId == i) {
                            break;
                        }
                    }
                    if (fingerprintSensorPropertiesInternal2.isAnyUdfpsType()) {
                        AuthBiometricUdfpsView authBiometricUdfpsView = (AuthBiometricUdfpsView) from.inflate(R$layout.auth_biometric_udfps_view, (ViewGroup) null, false);
                        authBiometricUdfpsView.setSensorProps(fingerprintSensorPropertiesInternal2);
                        this.mBiometricView = authBiometricUdfpsView;
                    } else {
                        this.mBiometricView = (AuthBiometricFingerprintView) from.inflate(R$layout.auth_biometric_fingerprint_view, (ViewGroup) null, false);
                    }
                } else if (Utils.containsSensorId(list2, i)) {
                    this.mBiometricView = (AuthBiometricFaceView) from.inflate(R$layout.auth_biometric_face_view, (ViewGroup) null, false);
                } else {
                    Log.e("BiometricPrompt/AuthContainerView", "Unknown sensorId: " + i);
                    this.mBiometricView = null;
                    this.mBackgroundView = null;
                    this.mBiometricScrollView = null;
                    return;
                }
            } else if (length == 2) {
                int[] findFaceAndFingerprintSensors = findFaceAndFingerprintSensors();
                int i2 = findFaceAndFingerprintSensors[0];
                int i3 = findFaceAndFingerprintSensors[1];
                if (i3 == -1 || i2 == -1) {
                    Log.e("BiometricPrompt/AuthContainerView", "Missing fingerprint or face for dual-sensor config");
                    this.mBiometricView = null;
                    this.mBackgroundView = null;
                    this.mBiometricScrollView = null;
                    return;
                }
                Iterator<FingerprintSensorPropertiesInternal> it2 = list.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        fingerprintSensorPropertiesInternal = null;
                        break;
                    }
                    fingerprintSensorPropertiesInternal = it2.next();
                    if (fingerprintSensorPropertiesInternal.sensorId == i3) {
                        break;
                    }
                }
                if (fingerprintSensorPropertiesInternal != null) {
                    AuthBiometricFaceToFingerprintView authBiometricFaceToFingerprintView = (AuthBiometricFaceToFingerprintView) from.inflate(R$layout.auth_biometric_face_to_fingerprint_view, (ViewGroup) null, false);
                    authBiometricFaceToFingerprintView.setFingerprintSensorProps(fingerprintSensorPropertiesInternal);
                    authBiometricFaceToFingerprintView.setModalityListener(new ModalityListener() {
                        /* class com.android.systemui.biometrics.AuthContainerView.AnonymousClass1 */

                        @Override // com.android.systemui.biometrics.ModalityListener
                        public void onModalitySwitched(int i, int i2) {
                            AuthContainerView.this.maybeUpdatePositionForUdfps(true);
                        }
                    });
                    this.mBiometricView = authBiometricFaceToFingerprintView;
                } else {
                    Log.e("BiometricPrompt/AuthContainerView", "Fingerprint props not found for sensor ID: " + i3);
                    this.mBiometricView = null;
                    this.mBackgroundView = null;
                    this.mBiometricScrollView = null;
                    return;
                }
            } else {
                Log.e("BiometricPrompt/AuthContainerView", "Unsupported sensor array, length: " + length);
                this.mBiometricView = null;
                this.mBackgroundView = null;
                this.mBiometricScrollView = null;
                return;
            }
        }
        this.mBiometricScrollView = this.mInjector.getBiometricScrollView(this.mFrameLayout);
        ImageView backgroundView = this.mInjector.getBackgroundView(this.mFrameLayout);
        this.mBackgroundView = backgroundView;
        addView(this.mFrameLayout);
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.setRequireConfirmation(this.mConfig.mRequireConfirmation);
            this.mBiometricView.setPanelController(this.mPanelController);
            this.mBiometricView.setPromptInfo(this.mConfig.mPromptInfo);
            this.mBiometricView.setCallback(this.mBiometricCallback);
            this.mBiometricView.setBackgroundView(backgroundView);
            this.mBiometricView.setUserId(this.mConfig.mUserId);
            this.mBiometricView.setEffectiveUserId(this.mEffectiveUserId);
        }
        setOnKeyListener(new AuthContainerView$$ExternalSyntheticLambda0(this));
        setImportantForAccessibility(2);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ boolean lambda$new$0(View view, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 1) {
            sendEarlyUserCanceled();
            animateAway(1);
        }
        return true;
    }

    public void sendEarlyUserCanceled() {
        this.mConfig.mCallback.onSystemEvent(1);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public boolean isAllowDeviceCredentials() {
        return Utils.isDeviceCredentialAllowed(this.mConfig.mPromptInfo);
    }

    private void addBiometricView() {
        this.mBiometricScrollView.addView(this.mBiometricView);
    }

    private void addCredentialView(boolean z, boolean z2) {
        LayoutInflater from = LayoutInflater.from(((LinearLayout) this).mContext);
        int credentialType = this.mInjector.getCredentialType(((LinearLayout) this).mContext, this.mEffectiveUserId);
        if (credentialType != 1) {
            if (credentialType == 2) {
                this.mCredentialView = (AuthCredentialView) from.inflate(R$layout.auth_credential_pattern_view, (ViewGroup) null, false);
                this.mBackgroundView.setOnClickListener(null);
                this.mBackgroundView.setImportantForAccessibility(2);
                this.mCredentialView.setContainerView(this);
                this.mCredentialView.setUserId(this.mConfig.mUserId);
                this.mCredentialView.setOperationId(this.mConfig.mOperationId);
                this.mCredentialView.setEffectiveUserId(this.mEffectiveUserId);
                this.mCredentialView.setCredentialType(credentialType);
                this.mCredentialView.setCallback(this.mCredentialCallback);
                this.mCredentialView.setPromptInfo(this.mConfig.mPromptInfo);
                this.mCredentialView.setPanelController(this.mPanelController, z);
                this.mCredentialView.setShouldAnimateContents(z2);
                this.mFrameLayout.addView(this.mCredentialView);
            } else if (credentialType != 3) {
                throw new IllegalStateException("Unknown credential type: " + credentialType);
            }
        }
        this.mCredentialView = (AuthCredentialView) from.inflate(R$layout.auth_credential_password_view, (ViewGroup) null, false);
        this.mBackgroundView.setOnClickListener(null);
        this.mBackgroundView.setImportantForAccessibility(2);
        this.mCredentialView.setContainerView(this);
        this.mCredentialView.setUserId(this.mConfig.mUserId);
        this.mCredentialView.setOperationId(this.mConfig.mOperationId);
        this.mCredentialView.setEffectiveUserId(this.mEffectiveUserId);
        this.mCredentialView.setCredentialType(credentialType);
        this.mCredentialView.setCallback(this.mCredentialCallback);
        this.mCredentialView.setPromptInfo(this.mConfig.mPromptInfo);
        this.mCredentialView.setPanelController(this.mPanelController, z);
        this.mCredentialView.setShouldAnimateContents(z2);
        this.mFrameLayout.addView(this.mCredentialView);
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mPanelController.setContainerDimensions(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onOrientationChanged() {
        maybeUpdatePositionForUdfps(true);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttachedToWindowInternal();
    }

    public void onAttachedToWindowInternal() {
        this.mWakefulnessLifecycle.addObserver(this);
        if (Utils.isBiometricAllowed(this.mConfig.mPromptInfo)) {
            addBiometricView();
        } else if (Utils.isDeviceCredentialAllowed(this.mConfig.mPromptInfo)) {
            addCredentialView(true, false);
        } else {
            throw new IllegalStateException("Unknown configuration: " + this.mConfig.mPromptInfo.getAuthenticators());
        }
        maybeUpdatePositionForUdfps(false);
        if (this.mConfig.mSkipIntro) {
            this.mContainerState = 3;
            return;
        }
        this.mContainerState = 1;
        this.mPanelView.setY(this.mTranslationY);
        this.mBiometricScrollView.setY(this.mTranslationY);
        setAlpha(0.0f);
        postOnAnimation(new AuthContainerView$$ExternalSyntheticLambda2(this));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onAttachedToWindowInternal$1() {
        this.mPanelView.animate().translationY(0.0f).setDuration(250).setInterpolator(this.mLinearOutSlowIn).withLayer().withEndAction(new AuthContainerView$$ExternalSyntheticLambda1(this)).start();
        this.mBiometricScrollView.animate().translationY(0.0f).setDuration(250).setInterpolator(this.mLinearOutSlowIn).withLayer().start();
        AuthCredentialView authCredentialView = this.mCredentialView;
        if (authCredentialView != null && authCredentialView.isAttachedToWindow()) {
            this.mCredentialView.setY(this.mTranslationY);
            this.mCredentialView.animate().translationY(0.0f).setDuration(250).setInterpolator(this.mLinearOutSlowIn).withLayer().start();
        }
        animate().alpha(1.0f).setDuration(250).setInterpolator(this.mLinearOutSlowIn).withLayer().start();
    }

    private static boolean shouldUpdatePositionForUdfps(View view) {
        if (view instanceof AuthBiometricUdfpsView) {
            return true;
        }
        if (!(view instanceof AuthBiometricFaceToFingerprintView)) {
            return false;
        }
        AuthBiometricFaceToFingerprintView authBiometricFaceToFingerprintView = (AuthBiometricFaceToFingerprintView) view;
        if (authBiometricFaceToFingerprintView.getActiveSensorType() != 2 || !authBiometricFaceToFingerprintView.isFingerprintUdfps()) {
            return false;
        }
        return true;
    }

    private boolean maybeUpdatePositionForUdfps(boolean z) {
        Display display = getDisplay();
        if (display == null || !shouldUpdatePositionForUdfps(this.mBiometricView)) {
            return false;
        }
        int rotation = display.getRotation();
        if (rotation == 0) {
            this.mPanelController.setPosition(1);
            setScrollViewGravity(81);
        } else if (rotation == 1) {
            this.mPanelController.setPosition(3);
            setScrollViewGravity(21);
        } else if (rotation != 3) {
            Log.e("BiometricPrompt/AuthContainerView", "Unsupported display rotation: " + rotation);
            this.mPanelController.setPosition(1);
            setScrollViewGravity(81);
        } else {
            this.mPanelController.setPosition(2);
            setScrollViewGravity(19);
        }
        if (z) {
            this.mPanelView.invalidateOutline();
            this.mBiometricView.requestLayout();
        }
        return true;
    }

    private void setScrollViewGravity(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBiometricScrollView.getLayoutParams();
        layoutParams.gravity = i;
        this.mBiometricScrollView.setLayoutParams(layoutParams);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mWakefulnessLifecycle.removeObserver(this);
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedGoingToSleep() {
        animateAway(1);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void show(WindowManager windowManager, Bundle bundle) {
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.restoreState(bundle);
        }
        windowManager.addView(this, getLayoutParams(this.mWindowToken, this.mConfig.mPromptInfo.getTitle()));
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void dismissWithoutCallback(boolean z) {
        if (z) {
            animateAway(false, 0);
        } else {
            removeWindowIfAttached();
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void dismissFromSystemServer() {
        animateAway(false, 0);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onAuthenticationSucceeded() {
        this.mBiometricView.onAuthenticationSucceeded();
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onAuthenticationFailed(int i, String str) {
        this.mBiometricView.onAuthenticationFailed(i, str);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onHelp(int i, String str) {
        this.mBiometricView.onHelp(i, str);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onError(int i, String str) {
        this.mBiometricView.onError(i, str);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onSaveState(Bundle bundle) {
        bundle.putInt("container_state", this.mContainerState);
        boolean z = true;
        bundle.putBoolean("biometric_showing", this.mBiometricView != null && this.mCredentialView == null);
        if (this.mCredentialView == null) {
            z = false;
        }
        bundle.putBoolean("credential_showing", z);
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.onSaveState(bundle);
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public String getOpPackageName() {
        return this.mConfig.mOpPackageName;
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void animateToCredentialUI() {
        this.mBiometricView.startTransitionToCredentialUI();
    }

    public void animateAway(int i) {
        animateAway(true, i);
    }

    private void animateAway(boolean z, int i) {
        int i2 = this.mContainerState;
        if (i2 == 1) {
            Log.w("BiometricPrompt/AuthContainerView", "startDismiss(): waiting for onDialogAnimatedIn");
            this.mContainerState = 2;
        } else if (i2 == 4) {
            Log.w("BiometricPrompt/AuthContainerView", "Already dismissing, sendReason: " + z + " reason: " + i);
        } else {
            this.mContainerState = 4;
            if (z) {
                this.mPendingCallbackReason = Integer.valueOf(i);
            } else {
                this.mPendingCallbackReason = null;
            }
            postOnAnimation(new AuthContainerView$$ExternalSyntheticLambda4(this, new AuthContainerView$$ExternalSyntheticLambda3(this)));
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$animateAway$2() {
        setVisibility(4);
        removeWindowIfAttached();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$animateAway$3(Runnable runnable) {
        this.mPanelView.animate().translationY(this.mTranslationY).setDuration(350).setInterpolator(this.mLinearOutSlowIn).withLayer().withEndAction(runnable).start();
        this.mBiometricScrollView.animate().translationY(this.mTranslationY).setDuration(350).setInterpolator(this.mLinearOutSlowIn).withLayer().start();
        AuthCredentialView authCredentialView = this.mCredentialView;
        if (authCredentialView != null && authCredentialView.isAttachedToWindow()) {
            this.mCredentialView.animate().translationY(this.mTranslationY).setDuration(350).setInterpolator(this.mLinearOutSlowIn).withLayer().start();
        }
        animate().alpha(0.0f).setDuration(350).setInterpolator(this.mLinearOutSlowIn).withLayer().start();
    }

    private void sendPendingCallbackIfNotNull() {
        Log.d("BiometricPrompt/AuthContainerView", "pendingCallback: " + this.mPendingCallbackReason);
        Integer num = this.mPendingCallbackReason;
        if (num != null) {
            this.mConfig.mCallback.onDismissed(num.intValue(), this.mCredentialAttestation);
            this.mPendingCallbackReason = null;
        }
    }

    private void removeWindowIfAttached() {
        sendPendingCallbackIfNotNull();
        if (this.mContainerState != 5) {
            this.mContainerState = 5;
            this.mWindowManager.removeView(this);
        }
    }

    public void onDialogAnimatedIn() {
        if (this.mContainerState == 2) {
            Log.d("BiometricPrompt/AuthContainerView", "onDialogAnimatedIn(): mPendingDismissDialog=true, dismissing now");
            animateAway(1);
            return;
        }
        this.mContainerState = 3;
        if (this.mBiometricView != null) {
            this.mConfig.mCallback.onDialogAnimatedIn();
            this.mBiometricView.onDialogAnimatedIn();
        }
    }

    static WindowManager.LayoutParams getLayoutParams(IBinder iBinder, CharSequence charSequence) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2017, 16785408, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.ime()));
        layoutParams.setTitle("BiometricPrompt");
        layoutParams.accessibilityTitle = charSequence;
        layoutParams.token = iBinder;
        return layoutParams;
    }

    private int[] findFaceAndFingerprintSensors() {
        int[] iArr = this.mConfig.mSensorIds;
        int i = -1;
        int i2 = -1;
        for (int i3 : iArr) {
            if (Utils.containsSensorId(this.mFpProps, i3)) {
                i = i3;
            } else if (Utils.containsSensorId(this.mFaceProps, i3)) {
                i2 = i3;
            }
            if (!(i == -1 || i2 == -1)) {
                break;
            }
        }
        return new int[]{i2, i};
    }
}

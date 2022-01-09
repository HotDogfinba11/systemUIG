package com.google.android.systemui.dreamliner;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.wm.shell.animation.PhysicsAnimator;
import java.util.concurrent.TimeUnit;

public class DockGestureController extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener, StatusBarStateController.StateListener, KeyguardStateController.Callback, ConfigurationController.ConfigurationListener {
    private static final long GEAR_VISIBLE_TIME_MILLIS;
    private static final long PREVIEW_DELAY_MILLIS;
    private final AccessibilityManager mAccessibilityManager;
    private final Context mContext;
    private float mDiffX;
    private float mFirstTouchX;
    private float mFirstTouchY;
    private boolean mFromRight;
    GestureDetector mGestureDetector;
    private final Runnable mHideGearRunnable;
    private final KeyguardStateController.Callback mKeyguardMonitorCallback = new KeyguardStateController.Callback() {
        /* class com.google.android.systemui.dreamliner.DockGestureController.AnonymousClass1 */

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public void onKeyguardShowingChanged() {
            if (DockGestureController.this.mKeyguardStateController.isOccluded()) {
                DockGestureController.this.hidePhotoPreview(false);
            }
        }
    };
    private final KeyguardStateController mKeyguardStateController;
    private float mLastTouchX;
    private boolean mLaunchedPhoto;
    private final int mPhotoDiffThreshold;
    private boolean mPhotoEnabled;
    private final FrameLayout mPhotoPreview;
    private final TextView mPhotoPreviewText;
    private PhysicsAnimator<View> mPreviewTargetAnimator;
    private final ImageView mSettingsGear;
    private final StatusBarStateController mStatusBarStateController;
    private PendingIntent mTapAction;
    private final PhysicsAnimator.SpringConfig mTargetSpringConfig = new PhysicsAnimator.SpringConfig(1500.0f, 1.0f);
    private final View mTouchDelegateView;
    private boolean mTriggerPhoto;
    private VelocityTracker mVelocityTracker;
    private float mVelocityX;

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        GEAR_VISIBLE_TIME_MILLIS = timeUnit.toMillis(15);
        PREVIEW_DELAY_MILLIS = timeUnit.toMillis(1);
    }

    DockGestureController(Context context, ImageView imageView, FrameLayout frameLayout, View view, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController) {
        this.mContext = context;
        this.mHideGearRunnable = new DockGestureController$$ExternalSyntheticLambda4(this);
        this.mGestureDetector = new GestureDetector(context, this);
        this.mTouchDelegateView = view;
        this.mSettingsGear = imageView;
        this.mPhotoPreview = frameLayout;
        TextView textView = (TextView) frameLayout.findViewById(R$id.photo_preview_text);
        this.mPhotoPreviewText = textView;
        textView.setText(context.getResources().getString(R$string.dock_photo_preview_text));
        imageView.setOnClickListener(new DockGestureController$$ExternalSyntheticLambda0(this));
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mPhotoDiffThreshold = context.getResources().getDimensionPixelSize(R$dimen.dock_photo_diff);
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mPreviewTargetAnimator = PhysicsAnimator.getInstance(frameLayout);
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$0(View view) {
        hideGear();
        sendProtectedBroadcast(new Intent("com.google.android.apps.dreamliner.SETTINGS"));
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        initVelocityTracker();
        float x = motionEvent.getX();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mVelocityTracker.clear();
            this.mFirstTouchX = x;
            this.mFirstTouchY = motionEvent.getY();
            this.mLaunchedPhoto = false;
            this.mFromRight = false;
            if (x > ((float) (this.mPhotoPreview.getRight() - this.mPhotoDiffThreshold))) {
                this.mFromRight = true;
                if (this.mPhotoEnabled) {
                    this.mPhotoPreview.setVisibility(0);
                    CrossFadeHelper.fadeIn(this.mPhotoPreview, 100, 0);
                }
            }
        } else if (actionMasked == 1) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            this.mVelocityX = this.mVelocityTracker.getXVelocity();
            if (Math.signum(this.mDiffX) == Math.signum(this.mVelocityX) || this.mLastTouchX > ((float) (this.mPhotoPreview.getRight() - this.mPhotoDiffThreshold))) {
                this.mTriggerPhoto = false;
            }
            if (!this.mTriggerPhoto || this.mPhotoPreview.getVisibility() != 0) {
                hidePhotoPreview(true);
            } else {
                sendProtectedBroadcast(new Intent("com.google.android.systemui.dreamliner.PHOTO_EVENT"));
                this.mPhotoPreview.post(new DockGestureController$$ExternalSyntheticLambda6(this));
                this.mLaunchedPhoto = true;
                this.mTriggerPhoto = false;
            }
        } else if (actionMasked == 2) {
            handleMoveEvent(motionEvent);
        }
        this.mLastTouchX = x;
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onTouch$1() {
        this.mPreviewTargetAnimator.spring(DynamicAnimation.TRANSLATION_X, 0.0f, this.mVelocityX, this.mTargetSpringConfig).start();
    }

    private void handleMoveEvent(MotionEvent motionEvent) {
        if (this.mFromRight && this.mPhotoEnabled) {
            float x = motionEvent.getX();
            float f = this.mLastTouchX;
            this.mPhotoPreview.setTranslationX(f + (x - f));
            this.mVelocityTracker.addMovement(motionEvent);
            this.mDiffX = this.mFirstTouchX - x;
            if (Math.abs(this.mDiffX) > Math.abs(this.mFirstTouchY - motionEvent.getY()) && Math.abs(this.mDiffX) > ((float) this.mPhotoDiffThreshold)) {
                this.mTriggerPhoto = true;
            }
        }
    }

    private void hidePhotoPreview(boolean z) {
        if (this.mPhotoPreview.getVisibility() == 0) {
            if (z) {
                this.mPhotoPreview.post(new DockGestureController$$ExternalSyntheticLambda3(this));
                return;
            }
            this.mPhotoPreview.setAlpha(0.0f);
            this.mPhotoPreview.setVisibility(4);
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$hidePhotoPreview$3() {
        this.mPreviewTargetAnimator.spring(DynamicAnimation.TRANSLATION_X, (float) this.mPhotoPreview.getRight(), this.mVelocityX, this.mTargetSpringConfig).withEndActions(new DockGestureController$$ExternalSyntheticLambda1(this)).start();
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$hidePhotoPreview$2() {
        this.mPhotoPreview.setAlpha(0.0f);
        this.mPhotoPreview.setVisibility(4);
    }

    private void initVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        PendingIntent pendingIntent = this.mTapAction;
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.w("DLGestureController", "Tap action pending intent cancelled", e);
            }
        }
        showGear();
        return false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        sendProtectedBroadcast(new Intent("com.google.android.systemui.dreamliner.TOUCH_EVENT"));
        return false;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozingChanged(boolean z) {
        if (z) {
            this.mTouchDelegateView.setOnTouchListener(this);
            showGear();
            return;
        }
        this.mTouchDelegateView.setOnTouchListener(null);
        hideGear();
        if (!this.mLaunchedPhoto) {
            hidePhotoPreview(true);
        } else {
            this.mPhotoPreview.postDelayed(new DockGestureController$$ExternalSyntheticLambda5(this), PREVIEW_DELAY_MILLIS);
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onDozingChanged$4() {
        hidePhotoPreview(false);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onLocaleListChanged() {
        this.mPhotoPreviewText.setText(this.mContext.getResources().getString(R$string.dock_photo_preview_text));
    }

    public void setPhotoEnabled(boolean z) {
        this.mPhotoEnabled = z;
    }

    public void handlePhotoFailure() {
        hidePhotoPreview(false);
    }

    public void setTapAction(PendingIntent pendingIntent) {
        this.mTapAction = pendingIntent;
    }

    public void startMonitoring() {
        this.mSettingsGear.setVisibility(4);
        onDozingChanged(this.mStatusBarStateController.isDozing());
        this.mStatusBarStateController.addCallback(this);
        this.mKeyguardStateController.addCallback(this.mKeyguardMonitorCallback);
    }

    public void stopMonitoring() {
        this.mStatusBarStateController.removeCallback(this);
        this.mKeyguardStateController.removeCallback(this.mKeyguardMonitorCallback);
        onDozingChanged(false);
        this.mSettingsGear.setVisibility(8);
    }

    private void showGear() {
        if (this.mTapAction == null) {
            if (!this.mSettingsGear.isVisibleToUser()) {
                this.mSettingsGear.setVisibility(0);
                this.mSettingsGear.animate().setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).alpha(1.0f).start();
            }
            this.mSettingsGear.removeCallbacks(this.mHideGearRunnable);
            this.mSettingsGear.postDelayed(this.mHideGearRunnable, getRecommendedTimeoutMillis());
        }
    }

    /* access modifiers changed from: public */
    private void hideGear() {
        if (this.mSettingsGear.isVisibleToUser()) {
            this.mSettingsGear.removeCallbacks(this.mHideGearRunnable);
            this.mSettingsGear.animate().setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).alpha(0.0f).withEndAction(new DockGestureController$$ExternalSyntheticLambda2(this)).start();
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$hideGear$5() {
        this.mSettingsGear.setVisibility(4);
    }

    private void sendProtectedBroadcast(Intent intent) {
        try {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
        } catch (SecurityException e) {
            Log.w("DLGestureController", "Cannot send event", e);
        }
    }

    private long getRecommendedTimeoutMillis() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (accessibilityManager == null) {
            return GEAR_VISIBLE_TIME_MILLIS;
        }
        return (long) accessibilityManager.getRecommendedTimeoutMillis(Math.toIntExact(GEAR_VISIBLE_TIME_MILLIS), 5);
    }
}

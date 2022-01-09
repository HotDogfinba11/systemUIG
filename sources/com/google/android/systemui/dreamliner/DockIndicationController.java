package com.google.android.systemui.dreamliner;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R$anim;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.concurrent.TimeUnit;

public class DockIndicationController implements StatusBarStateController.StateListener, View.OnClickListener, View.OnAttachStateChangeListener, ConfigurationController.ConfigurationListener {
    static final String ACTION_ASSISTANT_POODLE;
    private static final long KEYGUARD_INDICATION_TIMEOUT_MILLIS;
    private static final long PROMO_SHOWING_TIME_MILLIS;
    private final AccessibilityManager mAccessibilityManager;
    private final Context mContext;
    private final Runnable mDisableLiveRegionRunnable = new DockIndicationController$$ExternalSyntheticLambda1(this);
    FrameLayout mDockPromo;
    ImageView mDockedTopIcon;
    private boolean mDocking;
    private boolean mDozing;
    private final Animation mHidePromoAnimation;
    private final Runnable mHidePromoRunnable = new DockIndicationController$$ExternalSyntheticLambda0(this);
    boolean mIconViewsValidated;
    private final KeyguardIndicationController mKeyguardIndicationController;
    private TextView mPromoText;
    private boolean mShowPromo;
    private final Animation mShowPromoAnimation;
    private int mShowPromoTimes;
    private final StatusBar mStatusBar;
    private int mStatusBarState;
    private boolean mTopIconShowing;
    private KeyguardIndicationTextView mTopIndicationView;

    public void onViewAttachedToWindow(View view) {
    }

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        PROMO_SHOWING_TIME_MILLIS = timeUnit.toMillis(2);
        KEYGUARD_INDICATION_TIMEOUT_MILLIS = timeUnit.toMillis(15);
    }

    public DockIndicationController(Context context, KeyguardIndicationController keyguardIndicationController, SysuiStatusBarStateController sysuiStatusBarStateController, StatusBar statusBar) {
        this.mContext = context;
        this.mStatusBar = statusBar;
        this.mKeyguardIndicationController = keyguardIndicationController;
        sysuiStatusBarStateController.addCallback(this);
        Animation loadAnimation = AnimationUtils.loadAnimation(context, R$anim.dock_promo_animation);
        this.mShowPromoAnimation = loadAnimation;
        loadAnimation.setAnimationListener(new PhotoAnimationListener() {
            /* class com.google.android.systemui.dreamliner.DockIndicationController.AnonymousClass1 */

            public void onAnimationEnd(Animation animation) {
                DockIndicationController dockIndicationController = DockIndicationController.this;
                dockIndicationController.mDockPromo.postDelayed(dockIndicationController.mHidePromoRunnable, DockIndicationController.this.getRecommendedTimeoutMillis(DockIndicationController.PROMO_SHOWING_TIME_MILLIS));
            }
        });
        Animation loadAnimation2 = AnimationUtils.loadAnimation(context, R$anim.dock_promo_fade_out);
        this.mHidePromoAnimation = loadAnimation2;
        loadAnimation2.setAnimationListener(new PhotoAnimationListener() {
            /* class com.google.android.systemui.dreamliner.DockIndicationController.AnonymousClass2 */

            public void onAnimationEnd(Animation animation) {
                if (DockIndicationController.this.mShowPromoTimes < 3) {
                    DockIndicationController.this.showPromoInner();
                    return;
                }
                DockIndicationController.this.mKeyguardIndicationController.setVisible(true);
                DockIndicationController.this.mDockPromo.setVisibility(8);
            }
        });
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
    }

    public void onClick(View view) {
        if (view.getId() == R$id.docked_top_icon) {
            Intent intent = new Intent(ACTION_ASSISTANT_POODLE);
            intent.addFlags(1073741824);
            try {
                this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
            } catch (SecurityException e) {
                Log.w("DLIndicator", "Cannot send event for intent= " + intent, e);
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozingChanged(boolean z) {
        this.mDozing = z;
        updateVisibility();
        updateLiveRegionIfNeeded();
        if (!this.mDozing) {
            this.mShowPromo = false;
        } else {
            showPromoInner();
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onStateChanged(int i) {
        this.mStatusBarState = i;
        updateVisibility();
    }

    public void onViewDetachedFromWindow(View view) {
        view.removeOnAttachStateChangeListener(this);
        this.mIconViewsValidated = false;
        this.mDockedTopIcon = null;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onLocaleListChanged() {
        if (!this.mIconViewsValidated) {
            initializeIconViews();
        }
        this.mPromoText.setText(this.mContext.getResources().getString(R$string.dock_promo_text));
    }

    public void setShowing(boolean z) {
        this.mTopIconShowing = z;
        updateVisibility();
    }

    public void setDocking(boolean z) {
        this.mDocking = z;
        if (!z) {
            this.mTopIconShowing = false;
            this.mShowPromo = false;
        }
        updateVisibility();
        updateLiveRegionIfNeeded();
    }

    public void initializeIconViews() {
        NotificationShadeWindowView notificationShadeWindowView = this.mStatusBar.getNotificationShadeWindowView();
        ImageView imageView = (ImageView) notificationShadeWindowView.findViewById(R$id.docked_top_icon);
        this.mDockedTopIcon = imageView;
        imageView.setImageResource(R$drawable.ic_assistant_logo);
        ImageView imageView2 = this.mDockedTopIcon;
        Context context = this.mContext;
        int i = R$string.accessibility_assistant_poodle;
        imageView2.setContentDescription(context.getString(i));
        this.mDockedTopIcon.setTooltipText(this.mContext.getString(i));
        this.mDockedTopIcon.setOnClickListener(this);
        this.mDockPromo = (FrameLayout) notificationShadeWindowView.findViewById(R$id.dock_promo);
        TextView textView = (TextView) notificationShadeWindowView.findViewById(R$id.photo_promo_text);
        this.mPromoText = textView;
        textView.setAutoSizeTextTypeUniformWithConfiguration(10, 16, 1, 2);
        notificationShadeWindowView.findViewById(R$id.ambient_indication).addOnAttachStateChangeListener(this);
        this.mTopIndicationView = (KeyguardIndicationTextView) notificationShadeWindowView.findViewById(R$id.keyguard_indication_text);
        this.mIconViewsValidated = true;
    }

    public void showPromo(ResultReceiver resultReceiver) {
        this.mShowPromoTimes = 0;
        this.mShowPromo = true;
        if (!this.mDozing || !this.mDocking) {
            resultReceiver.send(1, null);
            return;
        }
        showPromoInner();
        resultReceiver.send(0, null);
    }

    public boolean isPromoShowing() {
        return this.mDockPromo.getVisibility() == 0;
    }

    private void showPromoInner() {
        if (this.mDozing && this.mDocking && this.mShowPromo) {
            this.mKeyguardIndicationController.setVisible(false);
            this.mDockPromo.setVisibility(0);
            this.mDockPromo.startAnimation(this.mShowPromoAnimation);
            this.mShowPromoTimes++;
        }
    }

    /* access modifiers changed from: public */
    private void hidePromo() {
        if (this.mDozing && this.mDocking) {
            this.mDockPromo.startAnimation(this.mHidePromoAnimation);
        }
    }

    private void updateVisibility() {
        if (!this.mIconViewsValidated) {
            initializeIconViews();
        }
        boolean z = false;
        if (!this.mDozing || !this.mDocking) {
            this.mDockPromo.setVisibility(8);
            this.mDockedTopIcon.setVisibility(8);
            int i = this.mStatusBarState;
            if (i == 1 || i == 2) {
                z = true;
            }
            this.mKeyguardIndicationController.setVisible(z);
        } else if (!this.mTopIconShowing) {
            this.mDockedTopIcon.setVisibility(8);
        } else {
            this.mDockedTopIcon.setVisibility(0);
        }
    }

    private void updateLiveRegionIfNeeded() {
        int accessibilityLiveRegion = this.mTopIndicationView.getAccessibilityLiveRegion();
        if (this.mDozing && this.mDocking) {
            this.mTopIndicationView.removeCallbacks(this.mDisableLiveRegionRunnable);
            this.mTopIndicationView.postDelayed(this.mDisableLiveRegionRunnable, getRecommendedTimeoutMillis(KEYGUARD_INDICATION_TIMEOUT_MILLIS));
        } else if (accessibilityLiveRegion != 1) {
            this.mTopIndicationView.setAccessibilityLiveRegion(1);
        }
    }

    /* access modifiers changed from: public */
    private void disableLiveRegion() {
        if (this.mDocking && this.mDozing) {
            this.mTopIndicationView.setAccessibilityLiveRegion(0);
        }
    }

    private long getRecommendedTimeoutMillis(long j) {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        return accessibilityManager == null ? j : (long) accessibilityManager.getRecommendedTimeoutMillis(Math.toIntExact(j), 2);
    }

    /* access modifiers changed from: private */
    public static class PhotoAnimationListener implements Animation.AnimationListener {
        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }

        private PhotoAnimationListener() {
        }
    }
}

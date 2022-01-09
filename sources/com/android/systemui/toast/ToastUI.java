package com.android.systemui.toast;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.INotificationManager;
import android.app.ITransientNotificationCallback;
import android.content.Context;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.IAccessibilityManager;
import android.widget.ToastPresenter;
import com.android.systemui.SystemUI;
import com.android.systemui.statusbar.CommandQueue;
import java.util.Objects;

public class ToastUI extends SystemUI implements CommandQueue.Callbacks {
    private final AccessibilityManager mAccessibilityManager;
    private ITransientNotificationCallback mCallback;
    private final CommandQueue mCommandQueue;
    private final IAccessibilityManager mIAccessibilityManager;
    private final INotificationManager mNotificationManager;
    private int mOrientation;
    private ToastPresenter mPresenter;
    SystemUIToast mToast;
    private final ToastFactory mToastFactory;
    private final ToastLogger mToastLogger;
    private ToastOutAnimatorListener mToastOutAnimatorListener;

    public ToastUI(Context context, CommandQueue commandQueue, ToastFactory toastFactory, ToastLogger toastLogger) {
        this(context, commandQueue, INotificationManager.Stub.asInterface(ServiceManager.getService("notification")), IAccessibilityManager.Stub.asInterface(ServiceManager.getService("accessibility")), toastFactory, toastLogger);
    }

    ToastUI(Context context, CommandQueue commandQueue, INotificationManager iNotificationManager, IAccessibilityManager iAccessibilityManager, ToastFactory toastFactory, ToastLogger toastLogger) {
        super(context);
        this.mOrientation = 1;
        this.mCommandQueue = commandQueue;
        this.mNotificationManager = iNotificationManager;
        this.mIAccessibilityManager = iAccessibilityManager;
        this.mToastFactory = toastFactory;
        this.mAccessibilityManager = (AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class);
        this.mToastLogger = toastLogger;
    }

    @Override // com.android.systemui.SystemUI
    public void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback) {
        ToastUI$$ExternalSyntheticLambda0 toastUI$$ExternalSyntheticLambda0 = new ToastUI$$ExternalSyntheticLambda0(this, i, charSequence, str, iTransientNotificationCallback, iBinder, iBinder2, i2);
        ToastOutAnimatorListener toastOutAnimatorListener = this.mToastOutAnimatorListener;
        if (toastOutAnimatorListener != null) {
            toastOutAnimatorListener.setShowNextToastRunnable(toastUI$$ExternalSyntheticLambda0);
        } else if (this.mPresenter != null) {
            hideCurrentToast(toastUI$$ExternalSyntheticLambda0);
        } else {
            toastUI$$ExternalSyntheticLambda0.run();
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$showToast$0(int i, CharSequence charSequence, String str, ITransientNotificationCallback iTransientNotificationCallback, IBinder iBinder, IBinder iBinder2, int i2) {
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
        Context createContextAsUser = this.mContext.createContextAsUser(userHandleForUid, 0);
        SystemUIToast createToast = this.mToastFactory.createToast(this.mContext, charSequence, str, userHandleForUid.getIdentifier(), this.mOrientation);
        this.mToast = createToast;
        if (createToast.getInAnimation() != null) {
            this.mToast.getInAnimation().start();
        }
        this.mCallback = iTransientNotificationCallback;
        ToastPresenter toastPresenter = new ToastPresenter(createContextAsUser, this.mIAccessibilityManager, this.mNotificationManager, str);
        this.mPresenter = toastPresenter;
        toastPresenter.getLayoutParams().setTrustedOverlay();
        this.mToastLogger.logOnShowToast(i, str, charSequence.toString(), iBinder.toString());
        this.mPresenter.show(this.mToast.getView(), iBinder, iBinder2, i2, this.mToast.getGravity().intValue(), this.mToast.getXOffset().intValue(), this.mToast.getYOffset().intValue(), (float) this.mToast.getHorizontalMargin().intValue(), (float) this.mToast.getVerticalMargin().intValue(), this.mCallback, this.mToast.hasCustomAnimation());
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void hideToast(String str, IBinder iBinder) {
        ToastPresenter toastPresenter = this.mPresenter;
        if (toastPresenter == null || !Objects.equals(toastPresenter.getPackageName(), str) || !Objects.equals(this.mPresenter.getToken(), iBinder)) {
            Log.w("ToastUI", "Attempt to hide non-current toast from package " + str);
            return;
        }
        this.mToastLogger.logOnHideToast(str, iBinder.toString());
        hideCurrentToast(null);
    }

    private void hideCurrentToast(Runnable runnable) {
        if (this.mToast.getOutAnimation() != null) {
            Animator outAnimation = this.mToast.getOutAnimation();
            ToastOutAnimatorListener toastOutAnimatorListener = new ToastOutAnimatorListener(this.mPresenter, this.mCallback, runnable);
            this.mToastOutAnimatorListener = toastOutAnimatorListener;
            outAnimation.addListener(toastOutAnimatorListener);
            outAnimation.start();
        } else {
            this.mPresenter.hide(this.mCallback);
            if (runnable != null) {
                runnable.run();
            }
        }
        this.mToast = null;
        this.mPresenter = null;
        this.mCallback = null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.SystemUI
    public void onConfigurationChanged(Configuration configuration) {
        int i = configuration.orientation;
        if (i != this.mOrientation) {
            this.mOrientation = i;
            SystemUIToast systemUIToast = this.mToast;
            if (systemUIToast != null) {
                ToastLogger toastLogger = this.mToastLogger;
                String charSequence = systemUIToast.mText.toString();
                boolean z = true;
                if (this.mOrientation != 1) {
                    z = false;
                }
                toastLogger.logOrientationChange(charSequence, z);
                this.mToast.onOrientationChange(this.mOrientation);
                this.mPresenter.updateLayoutParams(this.mToast.getXOffset().intValue(), this.mToast.getYOffset().intValue(), (float) this.mToast.getHorizontalMargin().intValue(), (float) this.mToast.getVerticalMargin().intValue(), this.mToast.getGravity().intValue());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public class ToastOutAnimatorListener extends AnimatorListenerAdapter {
        final ITransientNotificationCallback mPrevCallback;
        final ToastPresenter mPrevPresenter;
        Runnable mShowNextToastRunnable;

        ToastOutAnimatorListener(ToastPresenter toastPresenter, ITransientNotificationCallback iTransientNotificationCallback, Runnable runnable) {
            this.mPrevPresenter = toastPresenter;
            this.mPrevCallback = iTransientNotificationCallback;
            this.mShowNextToastRunnable = runnable;
        }

        /* access modifiers changed from: package-private */
        public void setShowNextToastRunnable(Runnable runnable) {
            this.mShowNextToastRunnable = runnable;
        }

        public void onAnimationEnd(Animator animator) {
            this.mPrevPresenter.hide(this.mPrevCallback);
            Runnable runnable = this.mShowNextToastRunnable;
            if (runnable != null) {
                runnable.run();
            }
            ToastUI.this.mToastOutAnimatorListener = null;
        }
    }
}

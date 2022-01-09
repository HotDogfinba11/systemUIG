package com.android.systemui.statusbar.notification.row;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.animation.Interpolators;

public abstract class StackScrollerDecorView extends ExpandableView {
    protected View mContent;
    private boolean mContentAnimating;
    private final Runnable mContentVisibilityEndRunnable = new StackScrollerDecorView$$ExternalSyntheticLambda1(this);
    private boolean mContentVisible = true;
    private int mDuration = 260;
    private boolean mIsSecondaryVisible = true;
    private boolean mIsVisible = true;
    private boolean mSecondaryAnimating = false;
    protected View mSecondaryView;
    private final Runnable mSecondaryVisibilityEndRunnable = new StackScrollerDecorView$$ExternalSyntheticLambda0(this);

    /* access modifiers changed from: protected */
    public abstract View findContentView();

    /* access modifiers changed from: protected */
    public abstract View findSecondaryView();

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isTransparent() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean needsClippingToShelf() {
        return false;
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mContentAnimating = false;
        if (getVisibility() != 8 && !this.mIsVisible) {
            setVisibility(8);
            setWillBeGone(false);
            notifyHeightChanged(false);
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        this.mSecondaryAnimating = false;
        if (this.mSecondaryView != null && getVisibility() != 8 && this.mSecondaryView.getVisibility() != 8 && !this.mIsSecondaryVisible) {
            this.mSecondaryView.setVisibility(8);
        }
    }

    public StackScrollerDecorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setClipChildren(false);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContent = findContentView();
        this.mSecondaryView = findSecondaryView();
        setVisible(false, false);
        setSecondaryVisible(false, false);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setOutlineProvider(null);
    }

    public void setContentVisible(boolean z) {
        setContentVisible(z, true, null);
    }

    public void setContentVisible(boolean z, boolean z2, Runnable runnable) {
        if (this.mContentVisible != z) {
            this.mContentAnimating = z2;
            this.mContentVisible = z;
            setViewVisible(this.mContent, z, z2, runnable == null ? this.mContentVisibilityEndRunnable : new StackScrollerDecorView$$ExternalSyntheticLambda2(this, runnable));
        }
        if (!this.mContentAnimating) {
            this.mContentVisibilityEndRunnable.run();
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentVisible$2(Runnable runnable) {
        this.mContentVisibilityEndRunnable.run();
        runnable.run();
    }

    public void setVisible(boolean z, boolean z2) {
        setVisible(z, z2, null);
    }

    public void setVisible(boolean z, boolean z2, Runnable runnable) {
        if (this.mIsVisible != z) {
            this.mIsVisible = z;
            if (z2) {
                if (z) {
                    setVisibility(0);
                    setWillBeGone(false);
                    notifyHeightChanged(false);
                } else {
                    setWillBeGone(true);
                }
                setContentVisible(z, true, runnable);
                return;
            }
            setVisibility(z ? 0 : 8);
            setContentVisible(z, false, runnable);
            setWillBeGone(false);
            notifyHeightChanged(false);
        }
    }

    public void setSecondaryVisible(boolean z, boolean z2) {
        if (this.mIsSecondaryVisible != z) {
            this.mSecondaryAnimating = z2;
            this.mIsSecondaryVisible = z;
            setViewVisible(this.mSecondaryView, z, z2, this.mSecondaryVisibilityEndRunnable);
        }
        if (!this.mSecondaryAnimating) {
            this.mSecondaryVisibilityEndRunnable.run();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isSecondaryVisible() {
        return this.mIsSecondaryVisible;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    private void setViewVisible(View view, boolean z, boolean z2, Runnable runnable) {
        if (view != null) {
            if (view.getVisibility() != 0) {
                view.setVisibility(0);
            }
            view.animate().cancel();
            float f = z ? 1.0f : 0.0f;
            if (!z2) {
                view.setAlpha(f);
                if (runnable != null) {
                    runnable.run();
                    return;
                }
                return;
            }
            view.animate().alpha(f).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT).setDuration((long) this.mDuration).withEndAction(runnable);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public long performRemoveAnimation(long j, long j2, float f, boolean z, float f2, Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        setContentVisible(false);
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void performAddAnimation(long j, long j2, boolean z) {
        setContentVisible(true);
    }
}

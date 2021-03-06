package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.systemui.R$id;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public class HeadsUpStatusBarView extends AlphaOptimizedLinearLayout {
    private final Rect mIconDrawingRect;
    private View mIconPlaceholder;
    private final Rect mLayoutedIconRect;
    private Runnable mOnDrawingRectChangedListener;
    private final NotificationEntry.OnSensitivityChangedListener mOnSensitivityChangedListener;
    private NotificationEntry mShowingEntry;
    private TextView mTextView;
    private final int[] mTmpPosition;

    public HeadsUpStatusBarView(Context context) {
        this(context, null);
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutedIconRect = new Rect();
        this.mTmpPosition = new int[2];
        this.mIconDrawingRect = new Rect();
        this.mOnSensitivityChangedListener = new HeadsUpStatusBarView$$ExternalSyntheticLambda0(this);
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("heads_up_status_bar_view_super_parcelable", super.onSaveInstanceState());
        bundle.putInt("visibility", getVisibility());
        bundle.putFloat("alpha", getAlpha());
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("heads_up_status_bar_view_super_parcelable"));
        if (bundle.containsKey("visibility")) {
            setVisibility(bundle.getInt("visibility"));
        }
        if (bundle.containsKey("alpha")) {
            setAlpha(bundle.getFloat("alpha"));
        }
    }

    public HeadsUpStatusBarView(Context context, View view, TextView textView) {
        this(context);
        this.mIconPlaceholder = view;
        this.mTextView = textView;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mIconPlaceholder = findViewById(R$id.icon_placeholder);
        this.mTextView = (TextView) findViewById(R$id.text);
    }

    public void setEntry(NotificationEntry notificationEntry) {
        NotificationEntry notificationEntry2 = this.mShowingEntry;
        if (notificationEntry2 != null) {
            notificationEntry2.removeOnSensitivityChangedListener(this.mOnSensitivityChangedListener);
        }
        this.mShowingEntry = notificationEntry;
        if (notificationEntry != null) {
            CharSequence charSequence = notificationEntry.headsUpStatusBarText;
            if (notificationEntry.isSensitive()) {
                charSequence = notificationEntry.headsUpStatusBarTextPublic;
            }
            this.mTextView.setText(charSequence);
            this.mShowingEntry.addOnSensitivityChangedListener(this.mOnSensitivityChangedListener);
        }
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$0(NotificationEntry notificationEntry) {
        if (notificationEntry == this.mShowingEntry) {
            setEntry(notificationEntry);
            return;
        }
        throw new IllegalStateException("Got a sensitivity change for " + notificationEntry + " but mShowingEntry is " + this.mShowingEntry);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mIconPlaceholder.getLocationOnScreen(this.mTmpPosition);
        int[] iArr = this.mTmpPosition;
        int i5 = iArr[0];
        int i6 = iArr[1];
        this.mLayoutedIconRect.set(i5, i6, this.mIconPlaceholder.getWidth() + i5, this.mIconPlaceholder.getHeight() + i6);
        updateDrawingRect();
    }

    private void updateDrawingRect() {
        Runnable runnable;
        Rect rect = this.mIconDrawingRect;
        rect.set(this.mLayoutedIconRect);
        if (((float) rect.left) != ((float) this.mIconDrawingRect.left) && (runnable = this.mOnDrawingRectChangedListener) != null) {
            runnable.run();
        }
    }

    public NotificationEntry getShowingEntry() {
        return this.mShowingEntry;
    }

    public Rect getIconDrawingRect() {
        return this.mIconDrawingRect;
    }

    public void onDarkChanged(Rect rect, float f, int i) {
        this.mTextView.setTextColor(DarkIconDispatcher.getTint(rect, this, i));
    }

    public void setOnDrawingRectChangedListener(Runnable runnable) {
        this.mOnDrawingRectChangedListener = runnable;
    }
}

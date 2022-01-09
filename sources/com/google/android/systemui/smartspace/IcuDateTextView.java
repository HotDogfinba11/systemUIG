package com.google.android.systemui.smartspace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import com.android.systemui.bcsmartspace.R$string;
import java.util.Locale;
import java.util.Objects;

public class IcuDateTextView extends DoubleShadowTextView {
    private DateFormat mFormatter;
    private Handler mHandler;
    private final BroadcastReceiver mIntentReceiver;
    private String mText;
    private final Runnable mTicker;

    public IcuDateTextView(Context context) {
        this(context, null);
    }

    public IcuDateTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mTicker = new IcuDateTextView$$ExternalSyntheticLambda0(this);
        this.mIntentReceiver = new BroadcastReceiver() {
            /* class com.google.android.systemui.smartspace.IcuDateTextView.AnonymousClass1 */

            public void onReceive(Context context, Intent intent) {
                IcuDateTextView.this.onTimeChanged(!"android.intent.action.TIME_TICK".equals(intent.getAction()));
            }
        };
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        getContext().registerReceiver(this.mIntentReceiver, intentFilter);
        onTimeChanged(true);
        this.mHandler = new Handler();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mHandler != null) {
            getContext().unregisterReceiver(this.mIntentReceiver);
            this.mHandler = null;
        }
    }

    /* access modifiers changed from: private */
    public void onTimeTick() {
        onTimeChanged(false);
        if (this.mHandler != null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.postAtTime(this.mTicker, uptimeMillis + (1000 - (uptimeMillis % 1000)));
        }
    }

    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mTicker);
            if (z) {
                this.mTicker.run();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onTimeChanged(boolean z) {
        if (isShown()) {
            if (this.mFormatter == null || z) {
                DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(getContext().getString(R$string.smartspace_icu_date_pattern), Locale.getDefault());
                this.mFormatter = instanceForSkeleton;
                instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
            }
            String format = this.mFormatter.format(Long.valueOf(System.currentTimeMillis()));
            if (!Objects.equals(this.mText, format)) {
                this.mText = format;
                setText(format);
                setContentDescription(format);
            }
        }
    }
}

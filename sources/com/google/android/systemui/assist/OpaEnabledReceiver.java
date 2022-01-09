package com.google.android.systemui.assist;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class OpaEnabledReceiver {
    private final Executor mBgExecutor;
    private final BroadcastDispatcher mBroadcastDispatcher;
    private final BroadcastReceiver mBroadcastReceiver = new OpaEnabledBroadcastReceiver();
    private final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final Executor mFgExecutor;
    private boolean mIsAGSAAssistant;
    private boolean mIsLongPressHomeEnabled;
    private boolean mIsOpaEligible;
    private boolean mIsOpaEnabled;
    private final List<OpaEnabledListener> mListeners = new ArrayList();
    private final OpaEnabledSettings mOpaEnabledSettings;

    public OpaEnabledReceiver(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, OpaEnabledSettings opaEnabledSettings) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mContentObserver = new AssistantContentObserver(context);
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mFgExecutor = executor;
        this.mBgExecutor = executor2;
        this.mOpaEnabledSettings = opaEnabledSettings;
        updateOpaEnabledState(false);
        registerContentObserver();
        registerEnabledReceiver(-2);
    }

    public void addOpaEnabledListener(OpaEnabledListener opaEnabledListener) {
        this.mListeners.add(opaEnabledListener);
        opaEnabledListener.onOpaEnabledReceived(this.mContext, this.mIsOpaEligible, this.mIsAGSAAssistant, this.mIsOpaEnabled, this.mIsLongPressHomeEnabled);
    }

    public void onUserSwitching(int i) {
        updateOpaEnabledState(true);
        this.mContentResolver.unregisterContentObserver(this.mContentObserver);
        registerContentObserver();
        this.mBroadcastDispatcher.unregisterReceiver(this.mBroadcastReceiver);
        registerEnabledReceiver(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateOpaEnabledState(boolean z) {
        updateOpaEnabledState(z, null);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateOpaEnabledState(boolean z, BroadcastReceiver.PendingResult pendingResult) {
        this.mBgExecutor.execute(new OpaEnabledReceiver$$ExternalSyntheticLambda2(this, z, pendingResult));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$updateOpaEnabledState$2(boolean z, BroadcastReceiver.PendingResult pendingResult) {
        this.mIsOpaEligible = this.mOpaEnabledSettings.isOpaEligible();
        this.mIsAGSAAssistant = this.mOpaEnabledSettings.isAgsaAssistant();
        this.mIsOpaEnabled = this.mOpaEnabledSettings.isOpaEnabled();
        this.mIsLongPressHomeEnabled = this.mOpaEnabledSettings.isLongPressHomeEnabled();
        if (z) {
            this.mFgExecutor.execute(new OpaEnabledReceiver$$ExternalSyntheticLambda1(this));
        }
        if (pendingResult != null) {
            this.mFgExecutor.execute(new OpaEnabledReceiver$$ExternalSyntheticLambda0(pendingResult));
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$updateOpaEnabledState$0() {
        dispatchOpaEnabledState(this.mContext);
    }

    public void dispatchOpaEnabledState() {
        dispatchOpaEnabledState(this.mContext);
    }

    private void dispatchOpaEnabledState(Context context) {
        Log.i("OpaEnabledReceiver", "Dispatching OPA eligble = " + this.mIsOpaEligible + "; AGSA = " + this.mIsAGSAAssistant + "; OPA enabled = " + this.mIsOpaEnabled);
        for (int i = 0; i < this.mListeners.size(); i++) {
            this.mListeners.get(i).onOpaEnabledReceived(context, this.mIsOpaEligible, this.mIsAGSAAssistant, this.mIsOpaEnabled, this.mIsLongPressHomeEnabled);
        }
    }

    private void registerContentObserver() {
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assistant"), false, this.mContentObserver, -2);
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_long_press_home_enabled"), false, this.mContentObserver, -2);
    }

    private void registerEnabledReceiver(int i) {
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.google.android.systemui.OPA_ENABLED"), this.mBgExecutor, new UserHandle(i));
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.google.android.systemui.OPA_USER_ENABLED"), this.mBgExecutor, new UserHandle(i));
    }

    private class AssistantContentObserver extends ContentObserver {
        public AssistantContentObserver(Context context) {
            super(new Handler(context.getMainLooper()));
        }

        public void onChange(boolean z, Uri uri) {
            OpaEnabledReceiver.this.updateOpaEnabledState(true);
        }
    }

    private class OpaEnabledBroadcastReceiver extends BroadcastReceiver {
        private OpaEnabledBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.google.android.systemui.OPA_ENABLED")) {
                OpaEnabledReceiver.this.mOpaEnabledSettings.setOpaEligible(intent.getBooleanExtra("OPA_ENABLED", false));
            } else if (intent.getAction().equals("com.google.android.systemui.OPA_USER_ENABLED")) {
                OpaEnabledReceiver.this.mOpaEnabledSettings.setOpaEnabled(intent.getBooleanExtra("OPA_USER_ENABLED", false));
            }
            OpaEnabledReceiver.this.updateOpaEnabledState(true, goAsync());
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public BroadcastReceiver getBroadcastReceiver() {
        return this.mBroadcastReceiver;
    }
}

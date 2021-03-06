package com.android.wm.shell.sizecompatui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.IBinder;
import android.view.IWindow;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.ViewGroup;
import android.view.WindowlessWindowManager;
import com.android.wm.shell.R;

/* access modifiers changed from: package-private */
public class SizeCompatUIWindowManager extends WindowlessWindowManager {
    private Context mContext;
    private final SizeCompatUILayout mLayout;
    private SurfaceControl mLeash;
    private SurfaceControlViewHost mViewHost;

    SizeCompatUIWindowManager(Context context, Configuration configuration, SizeCompatUILayout sizeCompatUILayout) {
        super(configuration, (SurfaceControl) null, (IBinder) null);
        this.mContext = context;
        this.mLayout = sizeCompatUILayout;
    }

    public void setConfiguration(Configuration configuration) {
        SizeCompatUIWindowManager.super.setConfiguration(configuration);
        this.mContext = this.mContext.createConfigurationContext(configuration);
    }

    /* access modifiers changed from: protected */
    public void attachToParentSurface(IWindow iWindow, SurfaceControl.Builder builder) {
        SurfaceControl.Builder callsite = new SurfaceControl.Builder(new SurfaceSession()).setContainerLayer().setName("SizeCompatUILeash").setHidden(false).setCallsite("SizeCompatUIWindowManager#attachToParentSurface");
        this.mLayout.attachToParentSurface(callsite);
        SurfaceControl build = callsite.build();
        this.mLeash = build;
        builder.setParent(build);
    }

    /* access modifiers changed from: package-private */
    public SizeCompatRestartButton createSizeCompatButton() {
        if (this.mViewHost == null) {
            Context context = this.mContext;
            this.mViewHost = new SurfaceControlViewHost(context, context.getDisplay(), this);
            SizeCompatRestartButton sizeCompatRestartButton = (SizeCompatRestartButton) LayoutInflater.from(this.mContext).inflate(R.layout.size_compat_ui, (ViewGroup) null);
            sizeCompatRestartButton.inject(this.mLayout);
            this.mViewHost.setView(sizeCompatRestartButton, this.mLayout.getButtonWindowLayoutParams());
            return sizeCompatRestartButton;
        }
        throw new IllegalStateException("A UI has already been created with this window manager.");
    }

    /* access modifiers changed from: package-private */
    public SizeCompatHintPopup createSizeCompatHint() {
        if (this.mViewHost == null) {
            Context context = this.mContext;
            this.mViewHost = new SurfaceControlViewHost(context, context.getDisplay(), this);
            SizeCompatHintPopup sizeCompatHintPopup = (SizeCompatHintPopup) LayoutInflater.from(this.mContext).inflate(R.layout.size_compat_mode_hint, (ViewGroup) null);
            sizeCompatHintPopup.measure(0, 0);
            sizeCompatHintPopup.inject(this.mLayout);
            this.mViewHost.setView(sizeCompatHintPopup, this.mLayout.getHintWindowLayoutParams(sizeCompatHintPopup));
            return sizeCompatHintPopup;
        }
        throw new IllegalStateException("A UI has already been created with this window manager.");
    }

    /* access modifiers changed from: package-private */
    public void release() {
        SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
            this.mViewHost = null;
        }
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl != null) {
            this.mLayout.mSyncQueue.runInSync(new SizeCompatUIWindowManager$$ExternalSyntheticLambda0(surfaceControl));
            this.mLeash = null;
        }
    }

    /* access modifiers changed from: package-private */
    public SurfaceControl getSurfaceControl() {
        return this.mLeash;
    }
}

package com.google.android.systemui.fingerprint;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.IUdfpsHbmListener;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsHbmProvider;
import com.android.systemui.util.Assert;
import com.google.android.systemui.fingerprint.UdfpsHbmRequest;
import java.util.concurrent.Executor;

public class UdfpsHbmController implements UdfpsHbmProvider, DisplayManager.DisplayListener {
    @VisibleForTesting
    static final float REFRESH_RATE_GHBM_HZ = 60.0f;
    private final AuthController mAuthController;
    private final Context mContext;
    private final UdfpsGhbmProvider mGhbmProvider;
    private UdfpsHbmRequest mHbmRequest;
    private final Injector mInjector;
    private final UdfpsLhbmProvider mLhbmProvider;
    private final Handler mMainHandler;
    private final float mPeakRefreshRate;
    private final Executor mUiBgExecutor;

    public void onDisplayAdded(int i) {
    }

    public void onDisplayRemoved(int i) {
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public static class Injector {
        private final DisplayManager mDisplayManager;

        Injector(DisplayManager displayManager) {
            this.mDisplayManager = displayManager;
        }

        /* access modifiers changed from: package-private */
        public void registerDisplayListener(DisplayManager.DisplayListener displayListener, Handler handler) {
            this.mDisplayManager.registerDisplayListener(displayListener, handler);
        }

        /* access modifiers changed from: package-private */
        public void unregisterDisplayListener(DisplayManager.DisplayListener displayListener) {
            this.mDisplayManager.unregisterDisplayListener(displayListener);
        }

        /* access modifiers changed from: package-private */
        public float getPeakRefreshRate(int i) {
            float f = 0.0f;
            for (Display.Mode mode : this.mDisplayManager.getDisplay(i).getSupportedModes()) {
                f = Math.max(f, mode.getRefreshRate());
            }
            return f;
        }

        /* access modifiers changed from: package-private */
        public float getRefreshRate(int i) {
            return this.mDisplayManager.getDisplay(i).getRefreshRate();
        }
    }

    public UdfpsHbmController(Context context, Handler handler, Executor executor, UdfpsGhbmProvider udfpsGhbmProvider, UdfpsLhbmProvider udfpsLhbmProvider, AuthController authController, DisplayManager displayManager) {
        this(context, handler, executor, udfpsGhbmProvider, udfpsLhbmProvider, authController, new Injector(displayManager));
    }

    @VisibleForTesting
    UdfpsHbmController(Context context, Handler handler, Executor executor, UdfpsGhbmProvider udfpsGhbmProvider, UdfpsLhbmProvider udfpsLhbmProvider, AuthController authController, Injector injector) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mUiBgExecutor = executor;
        this.mGhbmProvider = udfpsGhbmProvider;
        this.mLhbmProvider = udfpsLhbmProvider;
        this.mAuthController = authController;
        this.mInjector = injector;
        this.mPeakRefreshRate = injector.getPeakRefreshRate(context.getDisplayId());
        Settings.Secure.putIntForUser(context.getContentResolver(), "com.android.systemui.biometrics.UdfpsSurfaceView.hbmType", !SystemProperties.getBoolean("persist.fingerprint.ghbm", false) ? 1 : 0, -2);
    }

    @Override // com.android.systemui.biometrics.UdfpsHbmProvider
    public void enableHbm(int i, Surface surface, Runnable runnable) {
        Assert.isMainThread();
        Trace.beginSection("UdfpsHbmController.enableHbm");
        Log.v("UdfpsHbmController", "enableHbm");
        if (i != 0 && i != 1) {
            Log.e("UdfpsHbmController", "enableHbm | unsupported hbmType: " + i);
        } else if (i == 0 && surface == null) {
            Log.e("UdfpsHbmController", "enableHbm | surface must be non-null for GHBM");
        } else if (this.mAuthController.getUdfpsHbmListener() == null) {
            Log.e("UdfpsHbmController", "enableHbm | mDisplayManagerCallback is null");
        } else if (this.mHbmRequest != null) {
            Log.e("UdfpsHbmController", "enableHbm | HBM is already requested");
        } else {
            Trace.beginAsyncSection("UdfpsHbmController.e2e.enableHbm", 0);
            this.mHbmRequest = new UdfpsHbmRequest(this.mContext.getDisplayId(), i, surface, runnable);
            this.mInjector.registerDisplayListener(this, this.mMainHandler);
            try {
                IUdfpsHbmListener udfpsHbmListener = this.mAuthController.getUdfpsHbmListener();
                UdfpsHbmRequest.Args args = this.mHbmRequest.args;
                udfpsHbmListener.onHbmEnabled(args.hbmType, args.displayId);
                Log.v("UdfpsHbmController", "enableHbm | requested to freeze the refresh rate for hbmType: " + this.mHbmRequest.args.hbmType);
            } catch (RemoteException e) {
                Log.e("UdfpsHbmController", "enableHbm", e);
            }
            if (this.mInjector.getRefreshRate(this.mHbmRequest.args.displayId) == getRequiredRefreshRate(this.mHbmRequest.args.hbmType)) {
                onDisplayChanged(this.mHbmRequest.args.displayId);
            }
            Trace.endSection();
        }
    }

    private void doEnableHbm(UdfpsHbmRequest.Args args) {
        this.mUiBgExecutor.execute(new UdfpsHbmController$$ExternalSyntheticLambda0(this, args));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$doEnableHbm$1(UdfpsHbmRequest.Args args) {
        int i = args.hbmType;
        if (i == 0) {
            this.mGhbmProvider.enableGhbm(args.surface);
        } else if (i != 1) {
            Log.e("UdfpsHbmController", "doEnableHbm | unsupported HBM type: " + args.hbmType);
        } else {
            this.mLhbmProvider.enableLhbm();
        }
        Trace.endAsyncSection("UdfpsHbmController.e2e.enableHbm", 0);
        if (args.onHbmEnabled != null) {
            this.mMainHandler.post(new UdfpsHbmController$$ExternalSyntheticLambda1(this, args));
        } else {
            Log.w("UdfpsHbmController", "doEnableHbm | onHbmEnabled is null");
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$doEnableHbm$0(UdfpsHbmRequest.Args args) {
        args.onHbmEnabled.run();
        UdfpsHbmRequest udfpsHbmRequest = this.mHbmRequest;
        if (udfpsHbmRequest != null) {
            udfpsHbmRequest.finishedEnablingHbm = true;
        }
    }

    @Override // com.android.systemui.biometrics.UdfpsHbmProvider
    public void disableHbm(Runnable runnable) {
        Assert.isMainThread();
        Trace.beginSection("UdfpsHbmController.disableHbm");
        Log.v("UdfpsHbmController", "disableHbm");
        if (this.mHbmRequest == null) {
            Log.w("UdfpsHbmController", "disableHbm | HBM is already disabled");
            return;
        }
        if (this.mAuthController.getUdfpsHbmListener() == null) {
            Log.e("UdfpsHbmController", "disableHbm | mDisplayManagerCallback is null");
        }
        Trace.beginAsyncSection("UdfpsHbmController.e2e.disableHbm", 0);
        UdfpsHbmRequest udfpsHbmRequest = this.mHbmRequest;
        if (udfpsHbmRequest.beganEnablingHbm) {
            doDisableHbm(udfpsHbmRequest.args, runnable);
        }
        this.mInjector.unregisterDisplayListener(this);
        this.mHbmRequest = null;
        Trace.endSection();
    }

    private void doDisableHbm(UdfpsHbmRequest.Args args, Runnable runnable) {
        this.mUiBgExecutor.execute(new UdfpsHbmController$$ExternalSyntheticLambda3(this, args, runnable));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$doDisableHbm$3(UdfpsHbmRequest.Args args, Runnable runnable) {
        int i = args.hbmType;
        if (i == 0) {
            this.mGhbmProvider.disableGhbm(args.surface);
        } else if (i != 1) {
            Log.e("UdfpsHbmController", "doDisableHbm | unsupported HBM type: " + args.hbmType);
        } else {
            this.mLhbmProvider.disableLhbm();
        }
        Trace.endAsyncSection("UdfpsHbmController.e2e.disableHbm", 0);
        this.mMainHandler.post(new UdfpsHbmController$$ExternalSyntheticLambda2(this, args));
        if (runnable != null) {
            this.mMainHandler.post(runnable);
        } else {
            Log.w("UdfpsHbmController", "doDisableHbm | onHbmDisabled is null");
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$doDisableHbm$2(UdfpsHbmRequest.Args args) {
        try {
            this.mAuthController.getUdfpsHbmListener().onHbmDisabled(args.hbmType, args.displayId);
            Log.v("UdfpsHbmController", "disableHbm | requested to unfreeze the refresh rate");
        } catch (RemoteException e) {
            Log.e("UdfpsHbmController", "disableHbm", e);
        }
    }

    public void onDisplayChanged(int i) {
        Assert.isMainThread();
        UdfpsHbmRequest udfpsHbmRequest = this.mHbmRequest;
        if (udfpsHbmRequest == null) {
            Log.w("UdfpsHbmController", "onDisplayChanged | mHbmRequest is null");
        } else if (i != udfpsHbmRequest.args.displayId) {
            Log.w("UdfpsHbmController", String.format("onDisplayChanged | displayId: %d != %d", Integer.valueOf(i), Integer.valueOf(this.mHbmRequest.args.displayId)));
        } else {
            float refreshRate = this.mInjector.getRefreshRate(i);
            float requiredRefreshRate = getRequiredRefreshRate(this.mHbmRequest.args.hbmType);
            if (refreshRate != requiredRefreshRate) {
                Log.w("UdfpsHbmController", String.format("onDisplayChanged | hz: %f != %f", Float.valueOf(refreshRate), Float.valueOf(requiredRefreshRate)));
                if (this.mHbmRequest.finishedEnablingHbm) {
                    Log.e("UdfpsHbmController", "onDisplayChanged | refresh rate changed while HBM is enabled.");
                }
            } else if (!this.mHbmRequest.beganEnablingHbm) {
                Log.v("UdfpsHbmController", "onDisplayChanged | froze the refresh rate at hz: " + refreshRate);
                UdfpsHbmRequest udfpsHbmRequest2 = this.mHbmRequest;
                udfpsHbmRequest2.beganEnablingHbm = true;
                doEnableHbm(udfpsHbmRequest2.args);
            }
        }
    }

    private float getRequiredRefreshRate(int i) {
        if (i == 0) {
            return REFRESH_RATE_GHBM_HZ;
        }
        if (i != 1) {
            return 0.0f;
        }
        return this.mPeakRefreshRate;
    }
}

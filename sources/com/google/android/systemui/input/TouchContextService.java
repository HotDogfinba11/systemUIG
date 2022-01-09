package com.google.android.systemui.input;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.Display;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.BackgroundThread;
import com.google.input.ContextPacket;
import com.google.input.ITouchContextService;

public class TouchContextService implements DisplayManager.DisplayListener {
    private static final String INTERFACE = (ITouchContextService.DESCRIPTOR + "/default");
    private final DisplayManager mDm;
    private final Handler mHandler;
    private int mLastRotation = -1;
    private final Object mLock = new Object();
    @GuardedBy({"mLock"})
    private ITouchContextService mService;

    public void onDisplayAdded(int i) {
    }

    public void onDisplayRemoved(int i) {
    }

    public TouchContextService(Context context) {
        Handler handler = BackgroundThread.getHandler();
        this.mHandler = handler;
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDm = displayManager;
        if (!ServiceManager.isDeclared(INTERFACE)) {
            Log.d("TouchContextService", "No ITouchContextService declared in manifest, not sending input context");
            return;
        }
        displayManager.registerDisplayListener(this, handler);
        handler.post(new TouchContextService$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        onDisplayChanged(0);
    }

    public void onDisplayChanged(int i) {
        Display display;
        int rotation;
        if (i == 0 && (display = this.mDm.getDisplay(i)) != null && (rotation = display.getRotation()) != this.mLastRotation) {
            Display.Mode mode = display.getMode();
            ContextPacket contextPacket = new ContextPacket();
            contextPacket.orientation = toOrientation(rotation, mode.getPhysicalWidth(), mode.getPhysicalHeight());
            ITouchContextService touchContextService = getTouchContextService();
            if (touchContextService == null) {
                Log.e("TouchContextService", "Failed to get touch context service, dropping context packet.");
                return;
            }
            try {
                touchContextService.updateContext(contextPacket);
                this.mLastRotation = rotation;
            } catch (RemoteException e) {
                Log.e("TouchContextService", "Failed to send input context packet.", e);
            }
        }
    }

    private ITouchContextService getTouchContextService() {
        ITouchContextService iTouchContextService = this.mService;
        if (iTouchContextService != null) {
            return iTouchContextService;
        }
        IBinder service = ServiceManager.getService(INTERFACE);
        if (service == null) {
            Log.e("TouchContextService", "Failed to get ITouchContextService despite being declared.");
            return null;
        }
        try {
            service.linkToDeath(new TouchContextService$$ExternalSyntheticLambda0(this, service), 0);
            ITouchContextService asInterface = ITouchContextService.Stub.asInterface(service);
            this.mService = asInterface;
            return asInterface;
        } catch (RemoteException e) {
            Log.e("TouchContextService", "Failed to link to death on ITouchContextService. Binder is probably dead.", e);
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onBinderDied */
    public void lambda$getTouchContextService$1(IBinder iBinder) {
        synchronized (this.mLock) {
            if (this.mService.asBinder() == iBinder) {
                this.mService = null;
            }
        }
    }

    private static byte toOrientation(int i, int i2, int i3) {
        byte b = (byte) i;
        return i2 > i3 ? (byte) ((b + 1) % 4) : b;
    }
}

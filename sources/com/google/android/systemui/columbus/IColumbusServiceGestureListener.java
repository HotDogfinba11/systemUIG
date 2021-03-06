package com.google.android.systemui.columbus;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IColumbusServiceGestureListener extends IInterface {
    void onTrigger() throws RemoteException;

    public static abstract class Stub extends Binder implements IColumbusServiceGestureListener {
        public static IColumbusServiceGestureListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.systemui.columbus.IColumbusServiceGestureListener");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IColumbusServiceGestureListener)) {
                return new Proxy(iBinder);
            }
            return (IColumbusServiceGestureListener) queryLocalInterface;
        }

        /* access modifiers changed from: private */
        public static class Proxy implements IColumbusServiceGestureListener {
            public static IColumbusServiceGestureListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.google.android.systemui.columbus.IColumbusServiceGestureListener
            public void onTrigger() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.systemui.columbus.IColumbusServiceGestureListener");
                    if (this.mRemote.transact(1, obtain, null, 1) || Stub.getDefaultImpl() == null) {
                        obtain.recycle();
                    } else {
                        Stub.getDefaultImpl().onTrigger();
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static IColumbusServiceGestureListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}

package com.google.hardware.pixel.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDisplay extends IInterface {
    public static final String DESCRIPTOR = "com$google$hardware$pixel$display$IDisplay".replace('$', '.');

    void setLhbmState(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplay {
        public static IDisplay asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDisplay.DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDisplay)) {
                return new Proxy(iBinder);
            }
            return (IDisplay) queryLocalInterface;
        }

        /* access modifiers changed from: private */
        public static class Proxy implements IDisplay {
            public static IDisplay sDefaultImpl;
            private String mCachedHash = "-1";
            private int mCachedVersion = -1;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.google.hardware.pixel.display.IDisplay
            public void setLhbmState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplay.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (this.mRemote.transact(9, obtain, obtain2, 0)) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                    } else if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLhbmState(z);
                    } else {
                        throw new RemoteException("Method setLhbmState is unimplemented.");
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IDisplay getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}

package com.android.framework.protobuf.nano.android;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.framework.protobuf.nano.MessageNano;

public abstract class ParcelableMessageNano extends MessageNano implements Parcelable {
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        ParcelableMessageNanoCreator.writeToParcel(getClass(), this, parcel);
    }
}

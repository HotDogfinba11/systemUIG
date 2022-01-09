package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
@SuppressLint({"BanParcelableUsage"})
public class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator<BackStackState>() {
        /* class androidx.fragment.app.BackStackState.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public BackStackState createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public BackStackState[] newArray(int i) {
            return new BackStackState[i];
        }
    };
    final ArrayList<FragmentState> mFragments;
    final ArrayList<BackStackRecordState> mTransactions;

    public int describeContents() {
        return 0;
    }

    BackStackState(Parcel parcel) {
        this.mFragments = parcel.createTypedArrayList(FragmentState.CREATOR);
        this.mTransactions = parcel.createTypedArrayList(BackStackRecordState.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mFragments);
        parcel.writeTypedList(this.mTransactions);
    }
}

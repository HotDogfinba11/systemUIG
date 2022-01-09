package com.android.systemui.shared.system;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.AnnotationValidations;

public class RemoteTransitionCompat implements Parcelable {
    public static final Parcelable.Creator<RemoteTransitionCompat> CREATOR = new Parcelable.Creator<RemoteTransitionCompat>() {
        /* class com.android.systemui.shared.system.RemoteTransitionCompat.AnonymousClass3 */

        @Override // android.os.Parcelable.Creator
        public RemoteTransitionCompat[] newArray(int i) {
            return new RemoteTransitionCompat[i];
        }

        @Override // android.os.Parcelable.Creator
        public RemoteTransitionCompat createFromParcel(Parcel parcel) {
            return new RemoteTransitionCompat(parcel);
        }
    };
    TransitionFilter mFilter = null;
    final IRemoteTransition mTransition;

    public int describeContents() {
        return 0;
    }

    @VisibleForTesting
    static class RecentsControllerWrap extends RecentsAnimationControllerCompat {
        private IRemoteTransitionFinishedCallback mFinishCB = null;
        private TransitionInfo mInfo = null;
        private ArrayMap<SurfaceControl, SurfaceControl> mLeashMap = null;
        private SurfaceControl mOpeningLeash = null;
        private WindowContainerToken mPausingTask = null;
        private RecentsAnimationControllerCompat mWrapped = null;

        RecentsControllerWrap() {
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mFilter != null ? (byte) 2 : 0);
        parcel.writeStrongInterface(this.mTransition);
        TransitionFilter transitionFilter = this.mFilter;
        if (transitionFilter != null) {
            parcel.writeTypedObject(transitionFilter, i);
        }
    }

    protected RemoteTransitionCompat(Parcel parcel) {
        TransitionFilter transitionFilter;
        byte readByte = parcel.readByte();
        IRemoteTransition asInterface = IRemoteTransition.Stub.asInterface(parcel.readStrongBinder());
        if ((readByte & 2) == 0) {
            transitionFilter = null;
        } else {
            transitionFilter = (TransitionFilter) parcel.readTypedObject(TransitionFilter.CREATOR);
        }
        this.mTransition = asInterface;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, asInterface);
        this.mFilter = transitionFilter;
    }
}

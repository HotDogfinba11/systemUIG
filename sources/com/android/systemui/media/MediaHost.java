package com.android.systemui.media;

import android.graphics.Rect;
import android.util.ArraySet;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaHost.kt */
public final class MediaHost implements MediaHostState {
    private final Rect currentBounds = new Rect();
    public UniqueObjectHostView hostView;
    private boolean inited;
    private final MediaHost$listener$1 listener = new MediaHost$listener$1(this);
    private boolean listeningToMediaData;
    private int location = -1;
    private final MediaDataManager mediaDataManager;
    private final MediaHierarchyManager mediaHierarchyManager;
    private final MediaHostStatesManager mediaHostStatesManager;
    private final MediaHostStateHolder state;
    private final int[] tmpLocationOnScreen = {0, 0};
    private ArraySet<Function1<Boolean, Unit>> visibleChangedListeners = new ArraySet<>();

    @Override // com.android.systemui.media.MediaHostState
    public MediaHostState copy() {
        return this.state.copy();
    }

    @Override // com.android.systemui.media.MediaHostState
    public DisappearParameters getDisappearParameters() {
        return this.state.getDisappearParameters();
    }

    @Override // com.android.systemui.media.MediaHostState
    public float getExpansion() {
        return this.state.getExpansion();
    }

    @Override // com.android.systemui.media.MediaHostState
    public boolean getFalsingProtectionNeeded() {
        return this.state.getFalsingProtectionNeeded();
    }

    @Override // com.android.systemui.media.MediaHostState
    public MeasurementInput getMeasurementInput() {
        return this.state.getMeasurementInput();
    }

    @Override // com.android.systemui.media.MediaHostState
    public boolean getShowsOnlyActiveMedia() {
        return this.state.getShowsOnlyActiveMedia();
    }

    @Override // com.android.systemui.media.MediaHostState
    public boolean getVisible() {
        return this.state.getVisible();
    }

    public void setDisappearParameters(DisappearParameters disappearParameters) {
        Intrinsics.checkNotNullParameter(disappearParameters, "<set-?>");
        this.state.setDisappearParameters(disappearParameters);
    }

    @Override // com.android.systemui.media.MediaHostState
    public void setExpansion(float f) {
        this.state.setExpansion(f);
    }

    public void setFalsingProtectionNeeded(boolean z) {
        this.state.setFalsingProtectionNeeded(z);
    }

    public void setShowsOnlyActiveMedia(boolean z) {
        this.state.setShowsOnlyActiveMedia(z);
    }

    public MediaHost(MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager2, MediaDataManager mediaDataManager2, MediaHostStatesManager mediaHostStatesManager2) {
        Intrinsics.checkNotNullParameter(mediaHostStateHolder, "state");
        Intrinsics.checkNotNullParameter(mediaHierarchyManager2, "mediaHierarchyManager");
        Intrinsics.checkNotNullParameter(mediaDataManager2, "mediaDataManager");
        Intrinsics.checkNotNullParameter(mediaHostStatesManager2, "mediaHostStatesManager");
        this.state = mediaHostStateHolder;
        this.mediaHierarchyManager = mediaHierarchyManager2;
        this.mediaDataManager = mediaDataManager2;
        this.mediaHostStatesManager = mediaHostStatesManager2;
    }

    public final UniqueObjectHostView getHostView() {
        UniqueObjectHostView uniqueObjectHostView = this.hostView;
        if (uniqueObjectHostView != null) {
            return uniqueObjectHostView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("hostView");
        throw null;
    }

    public final void setHostView(UniqueObjectHostView uniqueObjectHostView) {
        Intrinsics.checkNotNullParameter(uniqueObjectHostView, "<set-?>");
        this.hostView = uniqueObjectHostView;
    }

    public final int getLocation() {
        return this.location;
    }

    public final Rect getCurrentBounds() {
        getHostView().getLocationOnScreen(this.tmpLocationOnScreen);
        int i = 0;
        int paddingLeft = this.tmpLocationOnScreen[0] + getHostView().getPaddingLeft();
        int paddingTop = this.tmpLocationOnScreen[1] + getHostView().getPaddingTop();
        int width = (this.tmpLocationOnScreen[0] + getHostView().getWidth()) - getHostView().getPaddingRight();
        int height = (this.tmpLocationOnScreen[1] + getHostView().getHeight()) - getHostView().getPaddingBottom();
        if (width < paddingLeft) {
            paddingLeft = 0;
            width = 0;
        }
        if (height < paddingTop) {
            height = 0;
        } else {
            i = paddingTop;
        }
        this.currentBounds.set(paddingLeft, i, width, height);
        return this.currentBounds;
    }

    public final void addVisibilityChangeListener(Function1<? super Boolean, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "listener");
        this.visibleChangedListeners.add(function1);
    }

    public final void removeVisibilityChangeListener(Function1<? super Boolean, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "listener");
        this.visibleChangedListeners.remove(function1);
    }

    public final void init(int i) {
        if (!this.inited) {
            this.inited = true;
            this.location = i;
            setHostView(this.mediaHierarchyManager.register(this));
            setListeningToMediaData(true);
            getHostView().addOnAttachStateChangeListener(new MediaHost$init$1(this));
            getHostView().setMeasurementManager(new MediaHost$init$2(this, i));
            this.state.setChangedListener(new MediaHost$init$3(this, i));
            updateViewVisibility();
        }
    }

    /* access modifiers changed from: private */
    public final void setListeningToMediaData(boolean z) {
        if (z != this.listeningToMediaData) {
            this.listeningToMediaData = z;
            if (z) {
                this.mediaDataManager.addListener(this.listener);
            } else {
                this.mediaDataManager.removeListener(this.listener);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void updateViewVisibility() {
        boolean z;
        MediaHostStateHolder mediaHostStateHolder = this.state;
        if (getShowsOnlyActiveMedia()) {
            z = this.mediaDataManager.hasActiveMedia();
        } else {
            z = this.mediaDataManager.hasAnyMedia();
        }
        mediaHostStateHolder.setVisible(z);
        int i = getVisible() ? 0 : 8;
        if (i != getHostView().getVisibility()) {
            getHostView().setVisibility(i);
            Iterator<T> it = this.visibleChangedListeners.iterator();
            while (it.hasNext()) {
                it.next().invoke(Boolean.valueOf(getVisible()));
            }
        }
    }

    /* compiled from: MediaHost.kt */
    public static final class MediaHostStateHolder implements MediaHostState {
        private Function0<Unit> changedListener;
        private DisappearParameters disappearParameters = new DisappearParameters();
        private float expansion;
        private boolean falsingProtectionNeeded;
        private int lastDisappearHash = getDisappearParameters().hashCode();
        private MeasurementInput measurementInput;
        private boolean showsOnlyActiveMedia;
        private boolean visible = true;

        @Override // com.android.systemui.media.MediaHostState
        public MeasurementInput getMeasurementInput() {
            return this.measurementInput;
        }

        public void setMeasurementInput(MeasurementInput measurementInput2) {
            if (!Intrinsics.areEqual(measurementInput2 == null ? null : Boolean.valueOf(measurementInput2.equals(this.measurementInput)), Boolean.TRUE)) {
                this.measurementInput = measurementInput2;
                Function0<Unit> function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }

        @Override // com.android.systemui.media.MediaHostState
        public float getExpansion() {
            return this.expansion;
        }

        @Override // com.android.systemui.media.MediaHostState
        public void setExpansion(float f) {
            if (!Float.valueOf(f).equals(Float.valueOf(this.expansion))) {
                this.expansion = f;
                Function0<Unit> function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }

        @Override // com.android.systemui.media.MediaHostState
        public boolean getShowsOnlyActiveMedia() {
            return this.showsOnlyActiveMedia;
        }

        public void setShowsOnlyActiveMedia(boolean z) {
            if (!Boolean.valueOf(z).equals(Boolean.valueOf(this.showsOnlyActiveMedia))) {
                this.showsOnlyActiveMedia = z;
                Function0<Unit> function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }

        @Override // com.android.systemui.media.MediaHostState
        public boolean getVisible() {
            return this.visible;
        }

        public void setVisible(boolean z) {
            if (this.visible != z) {
                this.visible = z;
                Function0<Unit> function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }

        @Override // com.android.systemui.media.MediaHostState
        public boolean getFalsingProtectionNeeded() {
            return this.falsingProtectionNeeded;
        }

        public void setFalsingProtectionNeeded(boolean z) {
            if (this.falsingProtectionNeeded != z) {
                this.falsingProtectionNeeded = z;
                Function0<Unit> function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }

        @Override // com.android.systemui.media.MediaHostState
        public DisappearParameters getDisappearParameters() {
            return this.disappearParameters;
        }

        public void setDisappearParameters(DisappearParameters disappearParameters2) {
            Intrinsics.checkNotNullParameter(disappearParameters2, "value");
            int hashCode = disappearParameters2.hashCode();
            if (!Integer.valueOf(this.lastDisappearHash).equals(Integer.valueOf(hashCode))) {
                this.disappearParameters = disappearParameters2;
                this.lastDisappearHash = hashCode;
                Function0<Unit> function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }

        public final void setChangedListener(Function0<Unit> function0) {
            this.changedListener = function0;
        }

        @Override // com.android.systemui.media.MediaHostState
        public MediaHostState copy() {
            MediaHostStateHolder mediaHostStateHolder = new MediaHostStateHolder();
            mediaHostStateHolder.setExpansion(getExpansion());
            mediaHostStateHolder.setShowsOnlyActiveMedia(getShowsOnlyActiveMedia());
            MeasurementInput measurementInput2 = getMeasurementInput();
            MeasurementInput measurementInput3 = null;
            if (measurementInput2 != null) {
                measurementInput3 = MeasurementInput.copy$default(measurementInput2, 0, 0, 3, null);
            }
            mediaHostStateHolder.setMeasurementInput(measurementInput3);
            mediaHostStateHolder.setVisible(getVisible());
            mediaHostStateHolder.setDisappearParameters(getDisappearParameters().deepCopy());
            mediaHostStateHolder.setFalsingProtectionNeeded(getFalsingProtectionNeeded());
            return mediaHostStateHolder;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MediaHostState)) {
                return false;
            }
            MediaHostState mediaHostState = (MediaHostState) obj;
            if (!Objects.equals(getMeasurementInput(), mediaHostState.getMeasurementInput())) {
                return false;
            }
            if ((getExpansion() == mediaHostState.getExpansion()) && getShowsOnlyActiveMedia() == mediaHostState.getShowsOnlyActiveMedia() && getVisible() == mediaHostState.getVisible() && getFalsingProtectionNeeded() == mediaHostState.getFalsingProtectionNeeded() && getDisappearParameters().equals(mediaHostState.getDisappearParameters())) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            MeasurementInput measurementInput2 = getMeasurementInput();
            return ((((((((((measurementInput2 == null ? 0 : measurementInput2.hashCode()) * 31) + Float.hashCode(getExpansion())) * 31) + Boolean.hashCode(getFalsingProtectionNeeded())) * 31) + Boolean.hashCode(getShowsOnlyActiveMedia())) * 31) + (getVisible() ? 1 : 2)) * 31) + getDisappearParameters().hashCode();
        }
    }
}

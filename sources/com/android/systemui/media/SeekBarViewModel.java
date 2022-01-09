package com.android.systemui.media;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SeekBarViewModel.kt */
public final class SeekBarViewModel {
    private Progress _data = new Progress(false, false, null, 0);
    private final MutableLiveData<Progress> _progress;
    private final RepeatableExecutor bgExecutor;
    private SeekBarViewModel$callback$1 callback;
    private Runnable cancel;
    private MediaController controller;
    private boolean isFalseSeek;
    private boolean listening;
    public Function0<Unit> logSmartspaceClick;
    private PlaybackState playbackState;
    private boolean scrubbing;

    public SeekBarViewModel(RepeatableExecutor repeatableExecutor) {
        Intrinsics.checkNotNullParameter(repeatableExecutor, "bgExecutor");
        this.bgExecutor = repeatableExecutor;
        MutableLiveData<Progress> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.postValue(this._data);
        Unit unit = Unit.INSTANCE;
        this._progress = mutableLiveData;
        this.callback = new SeekBarViewModel$callback$1(this);
        this.listening = true;
    }

    /* access modifiers changed from: private */
    public final void set_data(Progress progress) {
        this._data = progress;
        this._progress.postValue(progress);
    }

    public final LiveData<Progress> getProgress() {
        return this._progress;
    }

    /* access modifiers changed from: private */
    public final void setController(MediaController mediaController) {
        MediaController mediaController2 = this.controller;
        MediaSession.Token token = null;
        MediaSession.Token sessionToken = mediaController2 == null ? null : mediaController2.getSessionToken();
        if (mediaController != null) {
            token = mediaController.getSessionToken();
        }
        if (!Intrinsics.areEqual(sessionToken, token)) {
            MediaController mediaController3 = this.controller;
            if (mediaController3 != null) {
                mediaController3.unregisterCallback(this.callback);
            }
            if (mediaController != null) {
                mediaController.registerCallback(this.callback);
            }
            this.controller = mediaController;
        }
    }

    public final void setListening(boolean z) {
        this.bgExecutor.execute(new SeekBarViewModel$listening$1(this, z));
    }

    /* access modifiers changed from: private */
    public final void setScrubbing(boolean z) {
        if (this.scrubbing != z) {
            this.scrubbing = z;
            checkIfPollingNeeded();
        }
    }

    public final Function0<Unit> getLogSmartspaceClick() {
        Function0<Unit> function0 = this.logSmartspaceClick;
        if (function0 != null) {
            return function0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("logSmartspaceClick");
        throw null;
    }

    public final void setLogSmartspaceClick(Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.logSmartspaceClick = function0;
    }

    public final void onSeekStarting() {
        this.bgExecutor.execute(new SeekBarViewModel$onSeekStarting$1(this));
    }

    public final void onSeekProgress(long j) {
        this.bgExecutor.execute(new SeekBarViewModel$onSeekProgress$1(this, j));
    }

    public final void onSeekFalse() {
        this.bgExecutor.execute(new SeekBarViewModel$onSeekFalse$1(this));
    }

    public final void onSeek(long j) {
        this.bgExecutor.execute(new SeekBarViewModel$onSeek$1(this, j));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006e, code lost:
        if (r0.intValue() != 0) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0070, code lost:
        if (r9 > 0) goto L_0x0073;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateController(android.media.session.MediaController r9) {
        /*
        // Method dump skipped, instructions count: 127
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.SeekBarViewModel.updateController(android.media.session.MediaController):void");
    }

    public final void clearController() {
        this.bgExecutor.execute(new SeekBarViewModel$clearController$1(this));
    }

    public final void onDestroy() {
        this.bgExecutor.execute(new SeekBarViewModel$onDestroy$1(this));
    }

    /* access modifiers changed from: private */
    public final void checkPlaybackPosition() {
        int duration = this._data.getDuration();
        PlaybackState playbackState2 = this.playbackState;
        Integer valueOf = playbackState2 == null ? null : Integer.valueOf((int) SeekBarViewModelKt.access$computePosition(playbackState2, (long) duration));
        if (valueOf != null && !Intrinsics.areEqual(this._data.getElapsedTime(), valueOf)) {
            set_data(Progress.copy$default(this._data, false, false, valueOf, 0, 11, null));
        }
    }

    /* access modifiers changed from: private */
    public final void checkIfPollingNeeded() {
        boolean z = false;
        if (this.listening && !this.scrubbing) {
            PlaybackState playbackState2 = this.playbackState;
            if (playbackState2 == null ? false : SeekBarViewModelKt.access$isInMotion(playbackState2)) {
                z = true;
            }
        }
        if (!z) {
            Runnable runnable = this.cancel;
            if (runnable != null) {
                runnable.run();
            }
            this.cancel = null;
        } else if (this.cancel == null) {
            this.cancel = this.bgExecutor.executeRepeatedly(new SeekBarViewModel$checkIfPollingNeeded$1(this), 0, 100);
        }
    }

    public final SeekBar.OnSeekBarChangeListener getSeekBarListener() {
        return new SeekBarChangeListener(this);
    }

    public final void attachTouchHandlers(SeekBar seekBar) {
        Intrinsics.checkNotNullParameter(seekBar, "bar");
        seekBar.setOnSeekBarChangeListener(getSeekBarListener());
        seekBar.setOnTouchListener(new SeekBarTouchListener(this, seekBar));
    }

    /* access modifiers changed from: private */
    /* compiled from: SeekBarViewModel.kt */
    public static final class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private final SeekBarViewModel viewModel;

        public SeekBarChangeListener(SeekBarViewModel seekBarViewModel) {
            Intrinsics.checkNotNullParameter(seekBarViewModel, "viewModel");
            this.viewModel = seekBarViewModel;
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            Intrinsics.checkNotNullParameter(seekBar, "bar");
            if (z) {
                this.viewModel.onSeekProgress((long) i);
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "bar");
            this.viewModel.onSeekStarting();
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "bar");
            this.viewModel.onSeek((long) seekBar.getProgress());
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: SeekBarViewModel.kt */
    public static final class SeekBarTouchListener implements View.OnTouchListener, GestureDetector.OnGestureListener {
        private final SeekBar bar;
        private final GestureDetectorCompat detector;
        private final int flingVelocity;
        private boolean shouldGoToSeekBar;
        private final SeekBarViewModel viewModel;

        public void onLongPress(MotionEvent motionEvent) {
            Intrinsics.checkNotNullParameter(motionEvent, "event");
        }

        public void onShowPress(MotionEvent motionEvent) {
            Intrinsics.checkNotNullParameter(motionEvent, "event");
        }

        public SeekBarTouchListener(SeekBarViewModel seekBarViewModel, SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBarViewModel, "viewModel");
            Intrinsics.checkNotNullParameter(seekBar, "bar");
            this.viewModel = seekBarViewModel;
            this.bar = seekBar;
            this.detector = new GestureDetectorCompat(seekBar.getContext(), this);
            this.flingVelocity = ViewConfiguration.get(seekBar.getContext()).getScaledMinimumFlingVelocity() * 10;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(motionEvent, "event");
            if (!Intrinsics.areEqual(view, this.bar)) {
                return false;
            }
            this.detector.onTouchEvent(motionEvent);
            return !this.shouldGoToSeekBar;
        }

        public boolean onDown(MotionEvent motionEvent) {
            double d;
            double d2;
            ViewParent parent;
            Intrinsics.checkNotNullParameter(motionEvent, "event");
            int paddingLeft = this.bar.getPaddingLeft();
            int paddingRight = this.bar.getPaddingRight();
            int progress = this.bar.getProgress();
            int max = this.bar.getMax() - this.bar.getMin();
            double min = max > 0 ? ((double) (progress - this.bar.getMin())) / ((double) max) : 0.0d;
            int width = (this.bar.getWidth() - paddingLeft) - paddingRight;
            if (this.bar.isLayoutRtl()) {
                d2 = (double) paddingLeft;
                d = ((double) width) * (((double) 1) - min);
            } else {
                d2 = (double) paddingLeft;
                d = ((double) width) * min;
            }
            double d3 = d2 + d;
            long height = (long) (this.bar.getHeight() / 2);
            int round = (int) (Math.round(d3) - height);
            int round2 = (int) (Math.round(d3) + height);
            int round3 = Math.round(motionEvent.getX());
            boolean z = round3 >= round && round3 <= round2;
            this.shouldGoToSeekBar = z;
            if (z && (parent = this.bar.getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            return this.shouldGoToSeekBar;
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            Intrinsics.checkNotNullParameter(motionEvent, "event");
            this.shouldGoToSeekBar = true;
            return true;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            Intrinsics.checkNotNullParameter(motionEvent, "eventStart");
            Intrinsics.checkNotNullParameter(motionEvent2, "event");
            return this.shouldGoToSeekBar;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            Intrinsics.checkNotNullParameter(motionEvent, "eventStart");
            Intrinsics.checkNotNullParameter(motionEvent2, "event");
            if (Math.abs(f) > ((float) this.flingVelocity) || Math.abs(f2) > ((float) this.flingVelocity)) {
                this.viewModel.onSeekFalse();
            }
            return this.shouldGoToSeekBar;
        }
    }

    /* compiled from: SeekBarViewModel.kt */
    public static final class Progress {
        private final int duration;
        private final Integer elapsedTime;
        private final boolean enabled;
        private final boolean seekAvailable;

        public static /* synthetic */ Progress copy$default(Progress progress, boolean z, boolean z2, Integer num, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                z = progress.enabled;
            }
            if ((i2 & 2) != 0) {
                z2 = progress.seekAvailable;
            }
            if ((i2 & 4) != 0) {
                num = progress.elapsedTime;
            }
            if ((i2 & 8) != 0) {
                i = progress.duration;
            }
            return progress.copy(z, z2, num, i);
        }

        public final Progress copy(boolean z, boolean z2, Integer num, int i) {
            return new Progress(z, z2, num, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Progress)) {
                return false;
            }
            Progress progress = (Progress) obj;
            return this.enabled == progress.enabled && this.seekAvailable == progress.seekAvailable && Intrinsics.areEqual(this.elapsedTime, progress.elapsedTime) && this.duration == progress.duration;
        }

        public int hashCode() {
            boolean z = this.enabled;
            int i = 1;
            if (z) {
                z = true;
            }
            int i2 = z ? 1 : 0;
            int i3 = z ? 1 : 0;
            int i4 = z ? 1 : 0;
            int i5 = i2 * 31;
            boolean z2 = this.seekAvailable;
            if (!z2) {
                i = z2 ? 1 : 0;
            }
            int i6 = (i5 + i) * 31;
            Integer num = this.elapsedTime;
            return ((i6 + (num == null ? 0 : num.hashCode())) * 31) + Integer.hashCode(this.duration);
        }

        public String toString() {
            return "Progress(enabled=" + this.enabled + ", seekAvailable=" + this.seekAvailable + ", elapsedTime=" + this.elapsedTime + ", duration=" + this.duration + ')';
        }

        public Progress(boolean z, boolean z2, Integer num, int i) {
            this.enabled = z;
            this.seekAvailable = z2;
            this.elapsedTime = num;
            this.duration = i;
        }

        public final boolean getEnabled() {
            return this.enabled;
        }

        public final boolean getSeekAvailable() {
            return this.seekAvailable;
        }

        public final Integer getElapsedTime() {
            return this.elapsedTime;
        }

        public final int getDuration() {
            return this.duration;
        }
    }
}

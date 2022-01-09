package com.google.android.systemui.assist.uihints;

import android.app.PendingIntent;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R$id;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.input.TouchActionRegion;
import com.google.android.systemui.assist.uihints.input.TouchInsideRegion;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TranscriptionController implements NgaMessageHandler.CardInfoListener, NgaMessageHandler.TranscriptionInfoListener, NgaMessageHandler.GreetingInfoListener, NgaMessageHandler.ChipsInfoListener, NgaMessageHandler.ClearListener, ConfigurationController.ConfigurationListener, TouchActionRegion, TouchInsideRegion {
    private State mCurrentState;
    private final TouchInsideHandler mDefaultOnTap;
    private final FlingVelocityWrapper mFlingVelocity;
    private boolean mHasAccurateBackground;
    private ListenableFuture<Void> mHideFuture;
    private TranscriptionSpaceListener mListener;
    private PendingIntent mOnGreetingTap;
    private PendingIntent mOnTranscriptionTap;
    private final ViewGroup mParent;
    private Runnable mQueuedCompletion;
    private State mQueuedState;
    private boolean mQueuedStateAnimates;
    private Map<State, TranscriptionSpaceView> mViewMap = new HashMap();

    public enum State {
        TRANSCRIPTION,
        GREETING,
        CHIPS,
        NONE
    }

    public interface TranscriptionSpaceListener {
        void onStateChanged(State state, State state2);
    }

    /* access modifiers changed from: package-private */
    public interface TranscriptionSpaceView {
        void getHitRect(Rect rect);

        ListenableFuture<Void> hide(boolean z);

        void onFontSizeChanged();

        default void setCardVisible(boolean z) {
        }

        void setHasDarkBackground(boolean z);
    }

    TranscriptionController(ViewGroup viewGroup, TouchInsideHandler touchInsideHandler, FlingVelocityWrapper flingVelocityWrapper, ConfigurationController configurationController) {
        State state = State.NONE;
        this.mCurrentState = state;
        this.mHasAccurateBackground = false;
        this.mQueuedStateAnimates = false;
        this.mQueuedState = state;
        this.mParent = viewGroup;
        this.mDefaultOnTap = touchInsideHandler;
        this.mFlingVelocity = flingVelocityWrapper;
        setUpViews();
        configurationController.addCallback(this);
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.CardInfoListener
    public void onCardInfo(boolean z, int i, boolean z2, boolean z3) {
        setCardVisible(z);
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.TranscriptionInfoListener
    public void onTranscriptionInfo(String str, PendingIntent pendingIntent, int i) {
        setTranscription(str, pendingIntent);
        setTranscriptionColor(i);
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.GreetingInfoListener
    public void onGreetingInfo(String str, PendingIntent pendingIntent) {
        if (!TextUtils.isEmpty(str)) {
            this.mOnGreetingTap = pendingIntent;
            Optional<Float> consumeVelocity = this.mFlingVelocity.consumeVelocity();
            if (this.mCurrentState != State.NONE || !consumeVelocity.isPresent()) {
                setQueuedState(State.GREETING, false, new TranscriptionController$$ExternalSyntheticLambda3(this, str));
            } else {
                setQueuedState(State.GREETING, false, new TranscriptionController$$ExternalSyntheticLambda5(this, str, consumeVelocity));
            }
            maybeSetState();
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onGreetingInfo$0(String str, Optional optional) {
        ((GreetingView) this.mViewMap.get(State.GREETING)).setGreetingAnimated(str, ((Float) optional.get()).floatValue());
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onGreetingInfo$1(String str) {
        ((GreetingView) this.mViewMap.get(State.GREETING)).setGreeting(str);
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.ChipsInfoListener
    public void onChipsInfo(List<Bundle> list) {
        if (list == null || list.size() == 0) {
            Log.e("TranscriptionController", "Null or empty chip list received; clearing transcription space");
            onClear(false);
            return;
        }
        Optional<Float> consumeVelocity = this.mFlingVelocity.consumeVelocity();
        if (this.mCurrentState != State.NONE || !consumeVelocity.isPresent()) {
            State state = this.mCurrentState;
            if (state == State.GREETING || state == State.TRANSCRIPTION) {
                setQueuedState(State.CHIPS, false, new TranscriptionController$$ExternalSyntheticLambda6(this, list));
            } else {
                setQueuedState(State.CHIPS, false, new TranscriptionController$$ExternalSyntheticLambda7(this, list));
            }
        } else {
            setQueuedState(State.CHIPS, false, new TranscriptionController$$ExternalSyntheticLambda8(this, list, consumeVelocity));
        }
        maybeSetState();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onChipsInfo$2(List list, Optional optional) {
        ((ChipsContainer) this.mViewMap.get(State.CHIPS)).setChipsAnimatedBounce(list, ((Float) optional.get()).floatValue());
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onChipsInfo$3(List list) {
        ((ChipsContainer) this.mViewMap.get(State.CHIPS)).setChipsAnimatedZoom(list);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onChipsInfo$4(List list) {
        ((ChipsContainer) this.mViewMap.get(State.CHIPS)).setChips(list);
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.ClearListener
    public void onClear(boolean z) {
        setQueuedState(State.NONE, z, null);
        maybeSetState();
    }

    public void setListener(TranscriptionSpaceListener transcriptionSpaceListener) {
        this.mListener = transcriptionSpaceListener;
        if (transcriptionSpaceListener != null) {
            transcriptionSpaceListener.onStateChanged(null, this.mCurrentState);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        for (TranscriptionSpaceView transcriptionSpaceView : this.mViewMap.values()) {
            transcriptionSpaceView.onFontSizeChanged();
        }
    }

    public void setHasDarkBackground(boolean z) {
        for (TranscriptionSpaceView transcriptionSpaceView : this.mViewMap.values()) {
            transcriptionSpaceView.setHasDarkBackground(z);
        }
    }

    public void setCardVisible(boolean z) {
        for (TranscriptionSpaceView transcriptionSpaceView : this.mViewMap.values()) {
            transcriptionSpaceView.setCardVisible(z);
        }
    }

    public void setTranscription(String str, PendingIntent pendingIntent) {
        this.mOnTranscriptionTap = pendingIntent;
        setQueuedState(State.TRANSCRIPTION, false, new TranscriptionController$$ExternalSyntheticLambda4(this, str));
        maybeSetState();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$setTranscription$5(String str) {
        ((TranscriptionView) this.mViewMap.get(State.TRANSCRIPTION)).setTranscription(str);
    }

    public void setTranscriptionColor(int i) {
        ((TranscriptionView) this.mViewMap.get(State.TRANSCRIPTION)).setTranscriptionColor(i);
    }

    public void setHasAccurateBackground(boolean z) {
        if (this.mHasAccurateBackground != z) {
            this.mHasAccurateBackground = z;
            if (z) {
                maybeSetState();
            }
        }
    }

    @Override // com.google.android.systemui.assist.uihints.input.TouchInsideRegion
    public Optional<Region> getTouchInsideRegion() {
        return hasTapAction() ? Optional.empty() : getTouchRegion();
    }

    @Override // com.google.android.systemui.assist.uihints.input.TouchActionRegion
    public Optional<Region> getTouchActionRegion() {
        return hasTapAction() ? getTouchRegion() : Optional.empty();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.google.android.systemui.assist.uihints.TranscriptionController$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$android$systemui$assist$uihints$TranscriptionController$State;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        static {
            /*
                com.google.android.systemui.assist.uihints.TranscriptionController$State[] r0 = com.google.android.systemui.assist.uihints.TranscriptionController.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.google.android.systemui.assist.uihints.TranscriptionController.AnonymousClass1.$SwitchMap$com$google$android$systemui$assist$uihints$TranscriptionController$State = r0
                com.google.android.systemui.assist.uihints.TranscriptionController$State r1 = com.google.android.systemui.assist.uihints.TranscriptionController.State.TRANSCRIPTION     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = com.google.android.systemui.assist.uihints.TranscriptionController.AnonymousClass1.$SwitchMap$com$google$android$systemui$assist$uihints$TranscriptionController$State     // Catch:{ NoSuchFieldError -> 0x001d }
                com.google.android.systemui.assist.uihints.TranscriptionController$State r1 = com.google.android.systemui.assist.uihints.TranscriptionController.State.GREETING     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = com.google.android.systemui.assist.uihints.TranscriptionController.AnonymousClass1.$SwitchMap$com$google$android$systemui$assist$uihints$TranscriptionController$State     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.google.android.systemui.assist.uihints.TranscriptionController$State r1 = com.google.android.systemui.assist.uihints.TranscriptionController.State.CHIPS     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = com.google.android.systemui.assist.uihints.TranscriptionController.AnonymousClass1.$SwitchMap$com$google$android$systemui$assist$uihints$TranscriptionController$State     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.google.android.systemui.assist.uihints.TranscriptionController$State r1 = com.google.android.systemui.assist.uihints.TranscriptionController.State.NONE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.TranscriptionController.AnonymousClass1.<clinit>():void");
        }
    }

    private boolean hasTapAction() {
        int i = AnonymousClass1.$SwitchMap$com$google$android$systemui$assist$uihints$TranscriptionController$State[this.mCurrentState.ordinal()];
        return i != 1 ? i != 2 ? i == 3 : this.mOnGreetingTap != null : this.mOnTranscriptionTap != null;
    }

    private Optional<Region> getTouchRegion() {
        TranscriptionSpaceView transcriptionSpaceView = this.mViewMap.get(this.mCurrentState);
        if (transcriptionSpaceView == null) {
            return Optional.empty();
        }
        Rect rect = new Rect();
        transcriptionSpaceView.getHitRect(rect);
        return Optional.of(new Region(rect));
    }

    private void setQueuedState(State state, boolean z, Runnable runnable) {
        this.mQueuedState = state;
        this.mQueuedStateAnimates = z;
        this.mQueuedCompletion = runnable;
    }

    private void maybeSetState() {
        State state = this.mCurrentState;
        State state2 = this.mQueuedState;
        if (state == state2) {
            Runnable runnable = this.mQueuedCompletion;
            if (runnable != null) {
                runnable.run();
            }
        } else if (this.mHasAccurateBackground || state2 == State.NONE) {
            ListenableFuture<Void> listenableFuture = this.mHideFuture;
            if (listenableFuture == null || listenableFuture.isDone()) {
                updateListener(this.mCurrentState, this.mQueuedState);
                if (State.NONE.equals(this.mCurrentState)) {
                    this.mCurrentState = this.mQueuedState;
                    Runnable runnable2 = this.mQueuedCompletion;
                    if (runnable2 != null) {
                        runnable2.run();
                        return;
                    }
                    return;
                }
                ListenableFuture<Void> hide = this.mViewMap.get(this.mCurrentState).hide(this.mQueuedStateAnimates);
                this.mHideFuture = hide;
                Futures.transform(hide, new TranscriptionController$$ExternalSyntheticLambda2(this), MoreExecutors.directExecutor());
            }
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ Object lambda$maybeSetState$6(Void r1) {
        this.mCurrentState = this.mQueuedState;
        Runnable runnable = this.mQueuedCompletion;
        if (runnable == null) {
            return null;
        }
        runnable.run();
        return null;
    }

    private void updateListener(State state, State state2) {
        TranscriptionSpaceListener transcriptionSpaceListener = this.mListener;
        if (transcriptionSpaceListener != null) {
            transcriptionSpaceListener.onStateChanged(state, state2);
        }
    }

    private void setUpViews() {
        this.mViewMap = new HashMap();
        TranscriptionView transcriptionView = (TranscriptionView) this.mParent.findViewById(R$id.transcription);
        transcriptionView.setOnClickListener(new TranscriptionController$$ExternalSyntheticLambda0(this));
        transcriptionView.setOnTouchListener(this.mDefaultOnTap);
        this.mViewMap.put(State.TRANSCRIPTION, transcriptionView);
        GreetingView greetingView = (GreetingView) this.mParent.findViewById(R$id.greeting);
        greetingView.setOnClickListener(new TranscriptionController$$ExternalSyntheticLambda1(this));
        greetingView.setOnTouchListener(this.mDefaultOnTap);
        this.mViewMap.put(State.GREETING, greetingView);
        this.mViewMap.put(State.CHIPS, (ChipsContainer) this.mParent.findViewById(R$id.chips));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$setUpViews$7(View view) {
        PendingIntent pendingIntent = this.mOnTranscriptionTap;
        if (pendingIntent == null) {
            this.mDefaultOnTap.onTouchInside();
            return;
        }
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException unused) {
            Log.e("TranscriptionController", "Transcription tap PendingIntent cancelled");
            this.mDefaultOnTap.onTouchInside();
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$setUpViews$8(View view) {
        PendingIntent pendingIntent = this.mOnGreetingTap;
        if (pendingIntent == null) {
            this.mDefaultOnTap.onTouchInside();
            return;
        }
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException unused) {
            Log.e("TranscriptionController", "Greeting tap PendingIntent cancelled");
            this.mDefaultOnTap.onTouchInside();
        }
    }
}

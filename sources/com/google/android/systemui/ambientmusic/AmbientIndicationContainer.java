package com.google.android.systemui.ambientmusic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.Dependency;
import com.android.systemui.R$anim;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Objects;

public class AmbientIndicationContainer extends AutoReinflateContainer implements DozeReceiver, StatusBarStateController.StateListener, NotificationMediaManager.MediaListener {
    private Drawable mAmbientIconOverride;
    private int mAmbientIndicationIconSize;
    private Drawable mAmbientMusicAnimation;
    private Drawable mAmbientMusicNoteIcon;
    private int mAmbientMusicNoteIconIconSize;
    private CharSequence mAmbientMusicText;
    private boolean mAmbientSkipUnlock;
    private int mBurnInPreventionOffset;
    private float mDozeAmount;
    private boolean mDozing;
    private PendingIntent mFavoritingIntent;
    private final Handler mHandler;
    private final Rect mIconBounds = new Rect();
    private int mIconOverride = -1;
    private ImageView mIconView;
    private int mIndicationTextMode;
    private int mMediaPlaybackState;
    private boolean mNotificationsHidden;
    private PendingIntent mOpenIntent;
    private Drawable mReverseChargingAnimation;
    private CharSequence mReverseChargingMessage;
    private StatusBar mStatusBar;
    private int mStatusBarState;
    private int mTextColor;
    private ValueAnimator mTextColorAnimator;
    private TextView mTextView;
    private final WakeLock mWakeLock;
    private CharSequence mWirelessChargingMessage;

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePill$2() {
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePill$3() {
    }

    public AmbientIndicationContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mWakeLock = createWakeLock(((FrameLayout) this).mContext, handler);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public WakeLock createWakeLock(Context context, Handler handler) {
        return new DelayedWakeLock(handler, WakeLock.createPartial(context, "AmbientIndication"));
    }

    public void initializeView(StatusBar statusBar) {
        this.mStatusBar = statusBar;
        addInflateListener(new AmbientIndicationContainer$$ExternalSyntheticLambda4(this));
        addOnLayoutChangeListener(new AmbientIndicationContainer$$ExternalSyntheticLambda3(this));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$initializeView$0(View view) {
        this.mTextView = (TextView) findViewById(R$id.ambient_indication_text);
        this.mIconView = (ImageView) findViewById(R$id.ambient_indication_icon);
        this.mAmbientMusicAnimation = ((FrameLayout) this).mContext.getDrawable(R$anim.audioanim_animation);
        this.mAmbientMusicNoteIcon = ((FrameLayout) this).mContext.getDrawable(R$drawable.ic_music_note);
        this.mReverseChargingAnimation = ((FrameLayout) this).mContext.getDrawable(R$anim.reverse_charging_animation);
        this.mTextColor = this.mTextView.getCurrentTextColor();
        this.mAmbientIndicationIconSize = getResources().getDimensionPixelSize(R$dimen.ambient_indication_icon_size);
        this.mAmbientMusicNoteIconIconSize = getResources().getDimensionPixelSize(R$dimen.ambient_indication_note_icon_size);
        this.mBurnInPreventionOffset = getResources().getDimensionPixelSize(R$dimen.default_burn_in_prevention_offset);
        updateColors();
        updatePill();
        this.mTextView.setOnClickListener(new AmbientIndicationContainer$$ExternalSyntheticLambda1(this));
        this.mIconView.setOnClickListener(new AmbientIndicationContainer$$ExternalSyntheticLambda2(this));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$initializeView$1(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        updateBottomPadding();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.AutoReinflateContainer
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((StatusBarStateController) Dependency.get(StatusBarStateController.class)).addCallback(this);
        ((NotificationMediaManager) Dependency.get(NotificationMediaManager.class)).addCallback(this);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.AutoReinflateContainer
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((StatusBarStateController) Dependency.get(StatusBarStateController.class)).removeCallback(this);
        ((NotificationMediaManager) Dependency.get(NotificationMediaManager.class)).removeCallback(this);
        this.mMediaPlaybackState = 0;
    }

    public void setAmbientMusic(CharSequence charSequence, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i, boolean z) {
        if (!Objects.equals(this.mAmbientMusicText, charSequence) || !Objects.equals(this.mOpenIntent, pendingIntent) || !Objects.equals(this.mFavoritingIntent, pendingIntent2) || this.mIconOverride != i || this.mAmbientSkipUnlock != z) {
            this.mAmbientMusicText = charSequence;
            this.mOpenIntent = pendingIntent;
            this.mFavoritingIntent = pendingIntent2;
            this.mAmbientSkipUnlock = z;
            this.mIconOverride = i;
            this.mAmbientIconOverride = getAmbientIconOverride(i, ((FrameLayout) this).mContext);
            updatePill();
        }
    }

    private Drawable getAmbientIconOverride(int i, Context context) {
        switch (i) {
            case 1:
                return context.getDrawable(R$drawable.ic_music_search);
            case 2:
            default:
                return null;
            case 3:
                return context.getDrawable(R$drawable.ic_music_not_found);
            case 4:
                return context.getDrawable(R$drawable.ic_cloud_off);
            case 5:
                return context.getDrawable(R$drawable.ic_favorite);
            case 6:
                return context.getDrawable(R$drawable.ic_favorite_border);
            case 7:
                return context.getDrawable(R$drawable.ic_error);
            case 8:
                return context.getDrawable(R$drawable.ic_favorite_note);
        }
    }

    public void setWirelessChargingMessage(CharSequence charSequence) {
        if (!Objects.equals(this.mWirelessChargingMessage, charSequence) || this.mReverseChargingMessage != null) {
            this.mWirelessChargingMessage = charSequence;
            this.mReverseChargingMessage = null;
            updatePill();
        }
    }

    public void setReverseChargingMessage(CharSequence charSequence) {
        if (!Objects.equals(this.mReverseChargingMessage, charSequence) || this.mWirelessChargingMessage != null) {
            this.mWirelessChargingMessage = null;
            this.mReverseChargingMessage = charSequence;
            updatePill();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x014b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updatePill() {
        /*
        // Method dump skipped, instructions count: 368
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.ambientmusic.AmbientIndicationContainer.updatePill():void");
    }

    private void updateBottomPadding() {
        this.mStatusBar.getPanelController().setAmbientIndicationBottomPadding(this.mTextView.getVisibility() == 0 ? this.mStatusBar.getNotificationScrollLayout().getBottom() - getTop() : 0);
    }

    public void hideAmbientMusic() {
        setAmbientMusic(null, null, null, 0, false);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onTextClick(View view) {
        if (this.mOpenIntent != null) {
            this.mStatusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), view, "AMBIENT_MUSIC_CLICK");
            if (this.mAmbientSkipUnlock) {
                sendBroadcastWithoutDismissingKeyguard(this.mOpenIntent);
            } else {
                this.mStatusBar.startPendingIntentDismissingKeyguard(this.mOpenIntent);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onIconClick(View view) {
        if (this.mFavoritingIntent != null) {
            this.mStatusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), view, "AMBIENT_MUSIC_CLICK");
            sendBroadcastWithoutDismissingKeyguard(this.mFavoritingIntent);
            return;
        }
        onTextClick(view);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozingChanged(boolean z) {
        this.mDozing = z;
        updateVisibility();
        this.mTextView.setEnabled(!z);
        updateColors();
        updateBurnInOffsets();
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public void dozeTimeTick() {
        updatePill();
        updateBurnInOffsets();
    }

    private void updateBurnInOffsets() {
        int burnInOffset = BurnInHelperKt.getBurnInOffset(this.mBurnInPreventionOffset * 2, true);
        int i = this.mBurnInPreventionOffset;
        setTranslationX(((float) (burnInOffset - i)) * this.mDozeAmount);
        setTranslationY(((float) (BurnInHelperKt.getBurnInOffset(i * 2, false) - this.mBurnInPreventionOffset)) * this.mDozeAmount);
    }

    private void updateColors() {
        ValueAnimator valueAnimator = this.mTextColorAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTextColorAnimator.cancel();
        }
        int defaultColor = this.mTextView.getTextColors().getDefaultColor();
        int i = this.mDozing ? -1 : this.mTextColor;
        if (defaultColor == i) {
            this.mTextView.setTextColor(i);
            this.mIconView.setImageTintList(ColorStateList.valueOf(i));
            return;
        }
        ValueAnimator ofArgb = ValueAnimator.ofArgb(defaultColor, i);
        this.mTextColorAnimator = ofArgb;
        ofArgb.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mTextColorAnimator.setDuration(500L);
        this.mTextColorAnimator.addUpdateListener(new AmbientIndicationContainer$$ExternalSyntheticLambda0(this));
        this.mTextColorAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.google.android.systemui.ambientmusic.AmbientIndicationContainer.AnonymousClass3 */

            public void onAnimationEnd(Animator animator) {
                AmbientIndicationContainer.this.mTextColorAnimator = null;
            }
        });
        this.mTextColorAnimator.start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$updateColors$4(ValueAnimator valueAnimator) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mTextView.setTextColor(intValue);
        this.mIconView.setImageTintList(ColorStateList.valueOf(intValue));
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onStateChanged(int i) {
        this.mStatusBarState = i;
        updateVisibility();
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozeAmountChanged(float f, float f2) {
        this.mDozeAmount = f2;
        updateBurnInOffsets();
    }

    private void sendBroadcastWithoutDismissingKeyguard(PendingIntent pendingIntent) {
        if (!pendingIntent.isActivity()) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.w("AmbientIndication", "Sending intent failed: " + e);
            }
        }
    }

    private void updateVisibility() {
        if (this.mStatusBarState == 1) {
            setVisibility(0);
        } else {
            setVisibility(4);
        }
    }

    @Override // com.android.systemui.statusbar.NotificationMediaManager.MediaListener
    public void onPrimaryMetadataOrStateChanged(MediaMetadata mediaMetadata, int i) {
        if (this.mMediaPlaybackState != i) {
            this.mMediaPlaybackState = i;
            if (isMediaPlaying()) {
                hideAmbientMusic();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isMediaPlaying() {
        return NotificationMediaManager.isPlayingState(this.mMediaPlaybackState);
    }
}

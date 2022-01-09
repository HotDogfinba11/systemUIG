package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaMetadata;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeyguardMediaViewController.kt */
public final class KeyguardMediaViewController {
    private CharSequence artist;
    private final BroadcastDispatcher broadcastDispatcher;
    private final Context context;
    private final ComponentName mediaComponent;
    private final KeyguardMediaViewController$mediaListener$1 mediaListener = new KeyguardMediaViewController$mediaListener$1(this);
    private final NotificationMediaManager mediaManager;
    private final BcSmartspaceDataPlugin plugin;
    private BcSmartspaceDataPlugin.SmartspaceView smartspaceView;
    private CharSequence title;
    private final DelayableExecutor uiExecutor;
    private CurrentUserTracker userTracker;

    @VisibleForTesting
    public static /* synthetic */ void getSmartspaceView$annotations() {
    }

    public KeyguardMediaViewController(Context context2, BcSmartspaceDataPlugin bcSmartspaceDataPlugin, DelayableExecutor delayableExecutor, NotificationMediaManager notificationMediaManager, BroadcastDispatcher broadcastDispatcher2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(bcSmartspaceDataPlugin, "plugin");
        Intrinsics.checkNotNullParameter(delayableExecutor, "uiExecutor");
        Intrinsics.checkNotNullParameter(notificationMediaManager, "mediaManager");
        Intrinsics.checkNotNullParameter(broadcastDispatcher2, "broadcastDispatcher");
        this.context = context2;
        this.plugin = bcSmartspaceDataPlugin;
        this.uiExecutor = delayableExecutor;
        this.mediaManager = notificationMediaManager;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.mediaComponent = new ComponentName(context2, KeyguardMediaViewController.class);
    }

    public final DelayableExecutor getUiExecutor() {
        return this.uiExecutor;
    }

    public final BcSmartspaceDataPlugin.SmartspaceView getSmartspaceView() {
        return this.smartspaceView;
    }

    public final void setSmartspaceView(BcSmartspaceDataPlugin.SmartspaceView smartspaceView2) {
        this.smartspaceView = smartspaceView2;
    }

    public final void init() {
        this.plugin.addOnAttachStateChangeListener(new KeyguardMediaViewController$init$1(this));
        this.userTracker = new KeyguardMediaViewController$init$2(this, this.broadcastDispatcher);
    }

    public final void updateMediaInfo(MediaMetadata mediaMetadata, int i) {
        CharSequence charSequence;
        CharSequence charSequence2;
        if (!NotificationMediaManager.isPlayingState(i)) {
            reset();
            return;
        }
        Unit unit = null;
        if (mediaMetadata == null) {
            charSequence = null;
        } else {
            charSequence = mediaMetadata.getText("android.media.metadata.TITLE");
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = this.context.getResources().getString(R$string.music_controls_no_title);
            }
        }
        if (mediaMetadata == null) {
            charSequence2 = null;
        } else {
            charSequence2 = mediaMetadata.getText("android.media.metadata.ARTIST");
        }
        if (!TextUtils.equals(this.title, charSequence) || !TextUtils.equals(this.artist, charSequence2)) {
            this.title = charSequence;
            this.artist = charSequence2;
            if (charSequence != null) {
                SmartspaceAction build = new SmartspaceAction.Builder("deviceMediaTitle", charSequence.toString()).setSubtitle(this.artist).setIcon(this.mediaManager.getMediaIcon()).build();
                CurrentUserTracker currentUserTracker = this.userTracker;
                if (currentUserTracker != null) {
                    SmartspaceTarget build2 = new SmartspaceTarget.Builder("deviceMedia", this.mediaComponent, UserHandle.of(currentUserTracker.getCurrentUserId())).setFeatureType(31).setHeaderAction(build).build();
                    BcSmartspaceDataPlugin.SmartspaceView smartspaceView2 = getSmartspaceView();
                    if (smartspaceView2 != null) {
                        smartspaceView2.setMediaTarget(build2);
                        unit = Unit.INSTANCE;
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("userTracker");
                    throw null;
                }
            }
            if (unit == null) {
                reset();
            }
        }
    }

    /* access modifiers changed from: private */
    public final void reset() {
        this.title = null;
        this.artist = null;
        BcSmartspaceDataPlugin.SmartspaceView smartspaceView2 = this.smartspaceView;
        if (smartspaceView2 != null) {
            smartspaceView2.setMediaTarget(null);
        }
    }
}

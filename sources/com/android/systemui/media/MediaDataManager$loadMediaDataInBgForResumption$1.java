package com.android.systemui.media;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;

/* access modifiers changed from: package-private */
/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$loadMediaDataInBgForResumption$1 implements Runnable {
    final /* synthetic */ PendingIntent $appIntent;
    final /* synthetic */ String $appName;
    final /* synthetic */ Icon $artworkIcon;
    final /* synthetic */ MediaDescription $desc;
    final /* synthetic */ long $lastActive;
    final /* synthetic */ MediaAction $mediaAction;
    final /* synthetic */ String $packageName;
    final /* synthetic */ Runnable $resumeAction;
    final /* synthetic */ MediaSession.Token $token;
    final /* synthetic */ int $userId;
    final /* synthetic */ MediaDataManager this$0;

    MediaDataManager$loadMediaDataInBgForResumption$1(MediaDataManager mediaDataManager, String str, int i, String str2, MediaDescription mediaDescription, Icon icon, MediaAction mediaAction, MediaSession.Token token, PendingIntent pendingIntent, Runnable runnable, long j) {
        this.this$0 = mediaDataManager;
        this.$packageName = str;
        this.$userId = i;
        this.$appName = str2;
        this.$desc = mediaDescription;
        this.$artworkIcon = icon;
        this.$mediaAction = mediaAction;
        this.$token = token;
        this.$appIntent = pendingIntent;
        this.$resumeAction = runnable;
        this.$lastActive = j;
    }

    public final void run() {
        MediaDataManager mediaDataManager = this.this$0;
        String str = this.$packageName;
        int i = this.$userId;
        int i2 = mediaDataManager.bgColor;
        String str2 = this.$appName;
        CharSequence subtitle = this.$desc.getSubtitle();
        CharSequence title = this.$desc.getTitle();
        Icon icon = this.$artworkIcon;
        List list = CollectionsKt__CollectionsJVMKt.listOf(this.$mediaAction);
        List list2 = CollectionsKt__CollectionsJVMKt.listOf(0);
        String str3 = this.$packageName;
        mediaDataManager.onMediaDataLoaded(str, null, new MediaData(i, true, i2, str2, null, subtitle, title, icon, list, list2, str3, this.$token, this.$appIntent, null, false, this.$resumeAction, false, true, str3, true, null, false, this.$lastActive, 3211264, null));
    }
}

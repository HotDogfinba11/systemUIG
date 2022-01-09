package com.android.systemui.media;

import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaResumeListener.kt */
final class MediaResumeListener$onMediaDataLoaded$1 implements Runnable {
    final /* synthetic */ List<ResolveInfo> $inf;
    final /* synthetic */ String $key;
    final /* synthetic */ MediaResumeListener this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: java.util.List<? extends android.content.pm.ResolveInfo> */
    /* JADX WARN: Multi-variable type inference failed */
    MediaResumeListener$onMediaDataLoaded$1(MediaResumeListener mediaResumeListener, String str, List<? extends ResolveInfo> list) {
        this.this$0 = mediaResumeListener;
        this.$key = str;
        this.$inf = list;
    }

    public final void run() {
        MediaResumeListener mediaResumeListener = this.this$0;
        String str = this.$key;
        Intrinsics.checkNotNull(this.$inf);
        ComponentName componentName = this.$inf.get(0).getComponentInfo().getComponentName();
        Intrinsics.checkNotNullExpressionValue(componentName, "!!.get(0).componentInfo.componentName");
        mediaResumeListener.tryUpdateResumptionList(str, componentName);
    }
}

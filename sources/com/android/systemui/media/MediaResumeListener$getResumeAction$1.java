package com.android.systemui.media;

import android.content.ComponentName;

/* access modifiers changed from: package-private */
/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$getResumeAction$1 implements Runnable {
    final /* synthetic */ ComponentName $componentName;
    final /* synthetic */ MediaResumeListener this$0;

    MediaResumeListener$getResumeAction$1(MediaResumeListener mediaResumeListener, ComponentName componentName) {
        this.this$0 = mediaResumeListener;
        this.$componentName = componentName;
    }

    public final void run() {
        MediaResumeListener mediaResumeListener = this.this$0;
        mediaResumeListener.mediaBrowser = mediaResumeListener.mediaBrowserFactory.create(null, this.$componentName);
        ResumeMediaBrowser resumeMediaBrowser = this.this$0.mediaBrowser;
        if (resumeMediaBrowser != null) {
            resumeMediaBrowser.restart();
        }
    }
}

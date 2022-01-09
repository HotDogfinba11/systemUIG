package com.android.systemui.media;

import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.MediaDeviceManager;

/* access modifiers changed from: package-private */
/* compiled from: MediaDeviceManager.kt */
public final class MediaDeviceManager$Entry$current$1 implements Runnable {
    final /* synthetic */ MediaDevice $value;
    final /* synthetic */ MediaDeviceManager this$0;
    final /* synthetic */ MediaDeviceManager.Entry this$1;

    MediaDeviceManager$Entry$current$1(MediaDeviceManager mediaDeviceManager, MediaDeviceManager.Entry entry, MediaDevice mediaDevice) {
        this.this$0 = mediaDeviceManager;
        this.this$1 = entry;
        this.$value = mediaDevice;
    }

    public final void run() {
        this.this$0.processDevice(this.this$1.getKey(), this.this$1.getOldKey(), this.$value);
    }
}

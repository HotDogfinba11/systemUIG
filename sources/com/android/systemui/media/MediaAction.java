package com.android.systemui.media;

import android.graphics.drawable.Icon;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaData.kt */
public final class MediaAction {
    private final Runnable action;
    private final CharSequence contentDescription;
    private final Icon icon;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaAction)) {
            return false;
        }
        MediaAction mediaAction = (MediaAction) obj;
        return Intrinsics.areEqual(this.icon, mediaAction.icon) && Intrinsics.areEqual(this.action, mediaAction.action) && Intrinsics.areEqual(this.contentDescription, mediaAction.contentDescription);
    }

    public int hashCode() {
        Icon icon2 = this.icon;
        int i = 0;
        int hashCode = (icon2 == null ? 0 : icon2.hashCode()) * 31;
        Runnable runnable = this.action;
        int hashCode2 = (hashCode + (runnable == null ? 0 : runnable.hashCode())) * 31;
        CharSequence charSequence = this.contentDescription;
        if (charSequence != null) {
            i = charSequence.hashCode();
        }
        return hashCode2 + i;
    }

    public String toString() {
        return "MediaAction(icon=" + this.icon + ", action=" + this.action + ", contentDescription=" + ((Object) this.contentDescription) + ')';
    }

    public MediaAction(Icon icon2, Runnable runnable, CharSequence charSequence) {
        this.icon = icon2;
        this.action = runnable;
        this.contentDescription = charSequence;
    }

    public final Icon getIcon() {
        return this.icon;
    }

    public final Runnable getAction() {
        return this.action;
    }

    public final CharSequence getContentDescription() {
        return this.contentDescription;
    }
}

package com.android.systemui.media;

import android.app.smartspace.SmartspaceAction;
import android.content.Intent;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public final class SmartspaceMediaData {
    private final int backgroundColor;
    private final SmartspaceAction cardAction;
    private final Intent dismissIntent;
    private final boolean isActive;
    private final boolean isValid;
    private final String packageName;
    private final List<SmartspaceAction> recommendations;
    private final String targetId;

    public static /* synthetic */ SmartspaceMediaData copy$default(SmartspaceMediaData smartspaceMediaData, String str, boolean z, boolean z2, String str2, SmartspaceAction smartspaceAction, List list, Intent intent, int i, int i2, Object obj) {
        return smartspaceMediaData.copy((i2 & 1) != 0 ? smartspaceMediaData.targetId : str, (i2 & 2) != 0 ? smartspaceMediaData.isActive : z, (i2 & 4) != 0 ? smartspaceMediaData.isValid : z2, (i2 & 8) != 0 ? smartspaceMediaData.packageName : str2, (i2 & 16) != 0 ? smartspaceMediaData.cardAction : smartspaceAction, (i2 & 32) != 0 ? smartspaceMediaData.recommendations : list, (i2 & 64) != 0 ? smartspaceMediaData.dismissIntent : intent, (i2 & 128) != 0 ? smartspaceMediaData.backgroundColor : i);
    }

    public final SmartspaceMediaData copy(String str, boolean z, boolean z2, String str2, SmartspaceAction smartspaceAction, List<SmartspaceAction> list, Intent intent, int i) {
        Intrinsics.checkNotNullParameter(str, "targetId");
        Intrinsics.checkNotNullParameter(str2, "packageName");
        Intrinsics.checkNotNullParameter(list, "recommendations");
        return new SmartspaceMediaData(str, z, z2, str2, smartspaceAction, list, intent, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SmartspaceMediaData)) {
            return false;
        }
        SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) obj;
        return Intrinsics.areEqual(this.targetId, smartspaceMediaData.targetId) && this.isActive == smartspaceMediaData.isActive && this.isValid == smartspaceMediaData.isValid && Intrinsics.areEqual(this.packageName, smartspaceMediaData.packageName) && Intrinsics.areEqual(this.cardAction, smartspaceMediaData.cardAction) && Intrinsics.areEqual(this.recommendations, smartspaceMediaData.recommendations) && Intrinsics.areEqual(this.dismissIntent, smartspaceMediaData.dismissIntent) && this.backgroundColor == smartspaceMediaData.backgroundColor;
    }

    public int hashCode() {
        int hashCode = this.targetId.hashCode() * 31;
        boolean z = this.isActive;
        int i = 1;
        if (z) {
            z = true;
        }
        int i2 = z ? 1 : 0;
        int i3 = z ? 1 : 0;
        int i4 = z ? 1 : 0;
        int i5 = (hashCode + i2) * 31;
        boolean z2 = this.isValid;
        if (!z2) {
            i = z2 ? 1 : 0;
        }
        int hashCode2 = (((i5 + i) * 31) + this.packageName.hashCode()) * 31;
        SmartspaceAction smartspaceAction = this.cardAction;
        int i6 = 0;
        int hashCode3 = (((hashCode2 + (smartspaceAction == null ? 0 : smartspaceAction.hashCode())) * 31) + this.recommendations.hashCode()) * 31;
        Intent intent = this.dismissIntent;
        if (intent != null) {
            i6 = intent.hashCode();
        }
        return ((hashCode3 + i6) * 31) + Integer.hashCode(this.backgroundColor);
    }

    public String toString() {
        return "SmartspaceMediaData(targetId=" + this.targetId + ", isActive=" + this.isActive + ", isValid=" + this.isValid + ", packageName=" + this.packageName + ", cardAction=" + this.cardAction + ", recommendations=" + this.recommendations + ", dismissIntent=" + this.dismissIntent + ", backgroundColor=" + this.backgroundColor + ')';
    }

    public SmartspaceMediaData(String str, boolean z, boolean z2, String str2, SmartspaceAction smartspaceAction, List<SmartspaceAction> list, Intent intent, int i) {
        Intrinsics.checkNotNullParameter(str, "targetId");
        Intrinsics.checkNotNullParameter(str2, "packageName");
        Intrinsics.checkNotNullParameter(list, "recommendations");
        this.targetId = str;
        this.isActive = z;
        this.isValid = z2;
        this.packageName = str2;
        this.cardAction = smartspaceAction;
        this.recommendations = list;
        this.dismissIntent = intent;
        this.backgroundColor = i;
    }

    public final String getTargetId() {
        return this.targetId;
    }

    public final boolean isActive() {
        return this.isActive;
    }

    public final boolean isValid() {
        return this.isValid;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final SmartspaceAction getCardAction() {
        return this.cardAction;
    }

    public final List<SmartspaceAction> getRecommendations() {
        return this.recommendations;
    }

    public final Intent getDismissIntent() {
        return this.dismissIntent;
    }

    public final int getBackgroundColor() {
        return this.backgroundColor;
    }
}

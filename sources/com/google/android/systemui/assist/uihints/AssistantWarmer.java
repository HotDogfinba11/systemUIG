package com.google.android.systemui.assist.uihints;

import android.content.Context;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AssistantWarmer.kt */
public final class AssistantWarmer implements NgaMessageHandler.WarmingListener {
    private final Context context;
    private boolean primed;
    private NgaMessageHandler.WarmingRequest request;

    public AssistantWarmer(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.WarmingListener
    public void onWarmingRequest(NgaMessageHandler.WarmingRequest warmingRequest) {
        Intrinsics.checkNotNullParameter(warmingRequest, "request");
        this.request = warmingRequest;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onInvocationProgress(float r4) {
        /*
            r3 = this;
            r0 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            r1 = 1
            r2 = 0
            if (r0 < 0) goto L_0x000b
            r3.primed = r2
            goto L_0x002e
        L_0x000b:
            r0 = 0
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x0017
            boolean r0 = r3.primed
            if (r0 == 0) goto L_0x0017
            r3.primed = r2
            goto L_0x002f
        L_0x0017:
            com.google.android.systemui.assist.uihints.NgaMessageHandler$WarmingRequest r0 = r3.request
            if (r0 != 0) goto L_0x001f
            r0 = 1036831949(0x3dcccccd, float:0.1)
            goto L_0x0023
        L_0x001f:
            float r0 = r0.getThreshold()
        L_0x0023:
            int r4 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r4 <= 0) goto L_0x002e
            boolean r4 = r3.primed
            if (r4 != 0) goto L_0x002e
            r3.primed = r1
            goto L_0x002f
        L_0x002e:
            r1 = r2
        L_0x002f:
            if (r1 == 0) goto L_0x003d
            com.google.android.systemui.assist.uihints.NgaMessageHandler$WarmingRequest r4 = r3.request
            if (r4 != 0) goto L_0x0036
            goto L_0x003d
        L_0x0036:
            android.content.Context r0 = r3.context
            boolean r3 = r3.primed
            r4.notify(r0, r3)
        L_0x003d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.AssistantWarmer.onInvocationProgress(float):void");
    }
}

package com.android.systemui.util;

import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserAwareController.kt */
public interface UserAwareController {
    default void changeUser(UserHandle userHandle) {
        Intrinsics.checkNotNullParameter(userHandle, "newUser");
    }

    int getCurrentUserId();
}

package com.android.keyguard.dagger;

import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardRootViewController;

public interface KeyguardBouncerComponent {

    public interface Factory {
        KeyguardBouncerComponent create();
    }

    KeyguardHostViewController getKeyguardHostViewController();

    KeyguardRootViewController getKeyguardRootViewController();
}

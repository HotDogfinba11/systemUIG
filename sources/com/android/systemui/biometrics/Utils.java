package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.UserManager;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.widget.LockPatternUtils;
import java.util.List;

public class Utils {
    static float dpToPixels(Context context, float f) {
        return f * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    static void notifyAccessibilityContentChanged(AccessibilityManager accessibilityManager, ViewGroup viewGroup) {
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(1);
            viewGroup.sendAccessibilityEventUnchecked(obtain);
            viewGroup.notifySubtreeAccessibilityStateChanged(viewGroup, viewGroup, 1);
        }
    }

    static boolean isDeviceCredentialAllowed(PromptInfo promptInfo) {
        return (promptInfo.getAuthenticators() & 32768) != 0;
    }

    static boolean isBiometricAllowed(PromptInfo promptInfo) {
        return (promptInfo.getAuthenticators() & 255) != 0;
    }

    static int getCredentialType(Context context, int i) {
        int keyguardStoredPasswordQuality = new LockPatternUtils(context).getKeyguardStoredPasswordQuality(i);
        if (keyguardStoredPasswordQuality != 65536) {
            return (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) ? 1 : 3;
        }
        return 2;
    }

    static boolean isManagedProfile(Context context, int i) {
        return ((UserManager) context.getSystemService(UserManager.class)).isManagedProfile(i);
    }

    static boolean containsSensorId(List<? extends SensorPropertiesInternal> list, int i) {
        if (list == null) {
            return false;
        }
        for (SensorPropertiesInternal sensorPropertiesInternal : list) {
            if (sensorPropertiesInternal.sensorId == i) {
                return true;
            }
        }
        return false;
    }

    static boolean isSystem(Context context, String str) {
        return (context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") == 0) && "android".equals(str);
    }
}

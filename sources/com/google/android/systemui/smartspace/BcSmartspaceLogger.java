package com.google.android.systemui.smartspace;

import android.util.Log;
import com.android.systemui.shared.system.SysUiStatsLog;

public class BcSmartspaceLogger {
    private static final boolean IS_VERBOSE = BcSmartSpaceUtil.isLoggable("StatsLog");

    public static void log(EventEnum eventEnum, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        SysUiStatsLog.write(352, eventEnum.getId(), bcSmartspaceCardLoggingInfo.getInstanceId(), 0, bcSmartspaceCardLoggingInfo.getDisplaySurface(), bcSmartspaceCardLoggingInfo.getRank(), bcSmartspaceCardLoggingInfo.getCardinality(), bcSmartspaceCardLoggingInfo.getFeatureType(), -1, 0, 0, bcSmartspaceCardLoggingInfo.getReceivedLatency());
        if (IS_VERBOSE) {
            Log.d("StatsLog", String.format("\nLogged Smartspace event(%s), info(%s):", eventEnum, bcSmartspaceCardLoggingInfo.toString()));
        }
    }
}

package com.android.settingslib.wifi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.SystemClock;
import com.android.settingslib.R$drawable;
import java.util.Iterator;
import java.util.Map;

public class WifiUtils {
    static final int[] NO_INTERNET_WIFI_PIE = {R$drawable.ic_no_internet_wifi_signal_0, R$drawable.ic_no_internet_wifi_signal_1, R$drawable.ic_no_internet_wifi_signal_2, R$drawable.ic_no_internet_wifi_signal_3, R$drawable.ic_no_internet_wifi_signal_4};
    static final int[] WIFI_PIE = {17302885, 17302886, 17302887, 17302888, 17302889};

    static String getVisibilityStatus(AccessPoint accessPoint) {
        String str;
        WifiInfo info = accessPoint.getInfo();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        int i = 0;
        if (!accessPoint.isActive() || info == null) {
            str = null;
        } else {
            str = info.getBSSID();
            if (str != null) {
                sb.append(" ");
                sb.append(str);
            }
            sb.append(" standard = ");
            sb.append(info.getWifiStandard());
            sb.append(" rssi=");
            sb.append(info.getRssi());
            sb.append(" ");
            sb.append(" score=");
            sb.append(info.getScore());
            if (accessPoint.getSpeed() != 0) {
                sb.append(" speed=");
                sb.append(accessPoint.getSpeedLabel());
            }
            sb.append(String.format(" tx=%.1f,", Double.valueOf(info.getSuccessfulTxPacketsPerSecond())));
            sb.append(String.format("%.1f,", Double.valueOf(info.getRetriedTxPacketsPerSecond())));
            sb.append(String.format("%.1f ", Double.valueOf(info.getLostTxPacketsPerSecond())));
            sb.append(String.format("rx=%.1f", Double.valueOf(info.getSuccessfulRxPacketsPerSecond())));
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Iterator<ScanResult> it = accessPoint.getScanResults().iterator();
        int i2 = 0;
        int i3 = -127;
        int i4 = -127;
        int i5 = -127;
        int i6 = 0;
        while (it.hasNext()) {
            ScanResult next = it.next();
            if (next == null) {
                sb = sb;
            } else {
                int i7 = next.frequency;
                if (i7 >= 4900 && i7 <= 5900) {
                    i6++;
                    int i8 = next.level;
                    if (i8 > i4) {
                        i4 = i8;
                    }
                    if (i6 <= 4) {
                        sb3.append(verboseScanResultSummary(accessPoint, next, str, elapsedRealtime));
                    }
                } else if (i7 >= 2400 && i7 <= 2500) {
                    i++;
                    int i9 = next.level;
                    if (i9 > i3) {
                        i3 = i9;
                    }
                    if (i <= 4) {
                        sb2.append(verboseScanResultSummary(accessPoint, next, str, elapsedRealtime));
                    }
                } else if (i7 >= 58320 && i7 <= 70200) {
                    i2++;
                    int i10 = next.level;
                    if (i10 > i5) {
                        i5 = i10;
                    }
                    if (i2 <= 4) {
                        sb4.append(verboseScanResultSummary(accessPoint, next, str, elapsedRealtime));
                    }
                }
                sb = sb;
                it = it;
            }
        }
        sb.append(" [");
        if (i > 0) {
            sb.append("(");
            sb.append(i);
            sb.append(")");
            if (i > 4) {
                sb.append("max=");
                sb.append(i3);
                sb.append(",");
            }
            sb.append(sb2.toString());
        }
        sb.append(";");
        if (i6 > 0) {
            sb.append("(");
            sb.append(i6);
            sb.append(")");
            if (i6 > 4) {
                sb.append("max=");
                sb.append(i4);
                sb.append(",");
            }
            sb.append(sb3.toString());
        }
        sb.append(";");
        if (i2 > 0) {
            sb.append("(");
            sb.append(i2);
            sb.append(")");
            if (i2 > 4) {
                sb.append("max=");
                sb.append(i5);
                sb.append(",");
            }
            sb.append(sb4.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    static String verboseScanResultSummary(AccessPoint accessPoint, ScanResult scanResult, String str, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append(" \n{");
        sb.append(scanResult.BSSID);
        if (scanResult.BSSID.equals(str)) {
            sb.append("*");
        }
        sb.append("=");
        sb.append(scanResult.frequency);
        sb.append(",");
        sb.append(scanResult.level);
        int specificApSpeed = getSpecificApSpeed(scanResult, accessPoint.getScoredNetworkCache());
        if (specificApSpeed != 0) {
            sb.append(",");
            sb.append(accessPoint.getSpeedLabel(specificApSpeed));
        }
        sb.append(",");
        sb.append(((int) (j - (scanResult.timestamp / 1000))) / 1000);
        sb.append("s");
        sb.append("}");
        return sb.toString();
    }

    private static int getSpecificApSpeed(ScanResult scanResult, Map<String, TimestampedScoredNetwork> map) {
        TimestampedScoredNetwork timestampedScoredNetwork = map.get(scanResult.BSSID);
        if (timestampedScoredNetwork == null) {
            return 0;
        }
        return timestampedScoredNetwork.getScore().calculateBadge(scanResult.level);
    }

    public static int getInternetIconResource(int i, boolean z) {
        if (i >= 0) {
            int[] iArr = WIFI_PIE;
            if (i < iArr.length) {
                return z ? NO_INTERNET_WIFI_PIE[i] : iArr[i];
            }
        }
        throw new IllegalArgumentException("No Wifi icon found for level: " + i);
    }

    public static class InternetIconInjector {
        protected final Context mContext;

        public InternetIconInjector(Context context) {
            this.mContext = context;
        }

        public Drawable getIcon(boolean z, int i) {
            return this.mContext.getDrawable(WifiUtils.getInternetIconResource(i, z));
        }
    }

    public static Intent getWifiDetailsSettingsIntent(String str) {
        Intent intent = new Intent("android.settings.WIFI_DETAILS_SETTINGS");
        Bundle bundle = new Bundle();
        bundle.putString("key_chosen_wifientry_key", str);
        intent.putExtra(":settings:show_fragment_args", bundle);
        return intent;
    }
}

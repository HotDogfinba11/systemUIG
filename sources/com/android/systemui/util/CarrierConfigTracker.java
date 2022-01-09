package com.android.systemui.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.util.SparseBooleanArray;
import com.android.systemui.broadcast.BroadcastDispatcher;

public class CarrierConfigTracker extends BroadcastReceiver {
    private final SparseBooleanArray mCallStrengthConfigs = new SparseBooleanArray();
    private final CarrierConfigManager mCarrierConfigManager;
    private final SparseBooleanArray mCarrierProvisionsWifiMergedNetworks = new SparseBooleanArray();
    private boolean mDefaultCallStrengthConfig;
    private boolean mDefaultCallStrengthConfigLoaded;
    private boolean mDefaultCarrierProvisionsWifiMergedNetworks;
    private boolean mDefaultCarrierProvisionsWifiMergedNetworksLoaded;
    private boolean mDefaultNoCallingConfig;
    private boolean mDefaultNoCallingConfigLoaded;
    private final SparseBooleanArray mNoCallingConfigs = new SparseBooleanArray();

    public CarrierConfigTracker(Context context, BroadcastDispatcher broadcastDispatcher) {
        this.mCarrierConfigManager = (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        broadcastDispatcher.registerReceiver(this, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"));
    }

    public void onReceive(Context context, Intent intent) {
        PersistableBundle configForSubId;
        if ("android.telephony.action.CARRIER_CONFIG_CHANGED".equals(intent.getAction())) {
            int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
            if (SubscriptionManager.isValidSubscriptionId(intExtra) && (configForSubId = this.mCarrierConfigManager.getConfigForSubId(intExtra)) != null) {
                synchronized (this.mCallStrengthConfigs) {
                    this.mCallStrengthConfigs.put(intExtra, configForSubId.getBoolean("display_call_strength_indicator_bool"));
                }
                synchronized (this.mNoCallingConfigs) {
                    this.mNoCallingConfigs.put(intExtra, configForSubId.getBoolean("use_ip_for_calling_indicator_bool"));
                }
                synchronized (this.mCarrierProvisionsWifiMergedNetworks) {
                    this.mCarrierProvisionsWifiMergedNetworks.put(intExtra, configForSubId.getBoolean("carrier_provisions_wifi_merged_networks_bool"));
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
        if (r2.mDefaultCallStrengthConfigLoaded != false) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        r2.mDefaultCallStrengthConfig = android.telephony.CarrierConfigManager.getDefaultConfig().getBoolean("display_call_strength_indicator_bool");
        r2.mDefaultCallStrengthConfigLoaded = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        return r2.mDefaultCallStrengthConfig;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean getCallStrengthConfig(int r3) {
        /*
            r2 = this;
            android.util.SparseBooleanArray r0 = r2.mCallStrengthConfigs
            monitor-enter(r0)
            android.util.SparseBooleanArray r1 = r2.mCallStrengthConfigs     // Catch:{ all -> 0x002a }
            int r1 = r1.indexOfKey(r3)     // Catch:{ all -> 0x002a }
            if (r1 < 0) goto L_0x0013
            android.util.SparseBooleanArray r2 = r2.mCallStrengthConfigs     // Catch:{ all -> 0x002a }
            boolean r2 = r2.get(r3)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return r2
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            boolean r3 = r2.mDefaultCallStrengthConfigLoaded
            if (r3 != 0) goto L_0x0027
            android.os.PersistableBundle r3 = android.telephony.CarrierConfigManager.getDefaultConfig()
            java.lang.String r0 = "display_call_strength_indicator_bool"
            boolean r3 = r3.getBoolean(r0)
            r2.mDefaultCallStrengthConfig = r3
            r3 = 1
            r2.mDefaultCallStrengthConfigLoaded = r3
        L_0x0027:
            boolean r2 = r2.mDefaultCallStrengthConfig
            return r2
        L_0x002a:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.CarrierConfigTracker.getCallStrengthConfig(int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
        if (r2.mDefaultNoCallingConfigLoaded != false) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        r2.mDefaultNoCallingConfig = android.telephony.CarrierConfigManager.getDefaultConfig().getBoolean("use_ip_for_calling_indicator_bool");
        r2.mDefaultNoCallingConfigLoaded = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        return r2.mDefaultNoCallingConfig;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean getNoCallingConfig(int r3) {
        /*
            r2 = this;
            android.util.SparseBooleanArray r0 = r2.mNoCallingConfigs
            monitor-enter(r0)
            android.util.SparseBooleanArray r1 = r2.mNoCallingConfigs     // Catch:{ all -> 0x002a }
            int r1 = r1.indexOfKey(r3)     // Catch:{ all -> 0x002a }
            if (r1 < 0) goto L_0x0013
            android.util.SparseBooleanArray r2 = r2.mNoCallingConfigs     // Catch:{ all -> 0x002a }
            boolean r2 = r2.get(r3)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return r2
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            boolean r3 = r2.mDefaultNoCallingConfigLoaded
            if (r3 != 0) goto L_0x0027
            android.os.PersistableBundle r3 = android.telephony.CarrierConfigManager.getDefaultConfig()
            java.lang.String r0 = "use_ip_for_calling_indicator_bool"
            boolean r3 = r3.getBoolean(r0)
            r2.mDefaultNoCallingConfig = r3
            r3 = 1
            r2.mDefaultNoCallingConfigLoaded = r3
        L_0x0027:
            boolean r2 = r2.mDefaultNoCallingConfig
            return r2
        L_0x002a:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.CarrierConfigTracker.getNoCallingConfig(int):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
        if (r2.mDefaultCarrierProvisionsWifiMergedNetworksLoaded != false) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        r2.mDefaultCarrierProvisionsWifiMergedNetworks = android.telephony.CarrierConfigManager.getDefaultConfig().getBoolean("carrier_provisions_wifi_merged_networks_bool");
        r2.mDefaultCarrierProvisionsWifiMergedNetworksLoaded = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        return r2.mDefaultCarrierProvisionsWifiMergedNetworks;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean getCarrierProvisionsWifiMergedNetworksBool(int r3) {
        /*
            r2 = this;
            android.util.SparseBooleanArray r0 = r2.mCarrierProvisionsWifiMergedNetworks
            monitor-enter(r0)
            android.util.SparseBooleanArray r1 = r2.mCarrierProvisionsWifiMergedNetworks     // Catch:{ all -> 0x002a }
            int r1 = r1.indexOfKey(r3)     // Catch:{ all -> 0x002a }
            if (r1 < 0) goto L_0x0013
            android.util.SparseBooleanArray r2 = r2.mCarrierProvisionsWifiMergedNetworks     // Catch:{ all -> 0x002a }
            boolean r2 = r2.get(r3)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return r2
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            boolean r3 = r2.mDefaultCarrierProvisionsWifiMergedNetworksLoaded
            if (r3 != 0) goto L_0x0027
            android.os.PersistableBundle r3 = android.telephony.CarrierConfigManager.getDefaultConfig()
            java.lang.String r0 = "carrier_provisions_wifi_merged_networks_bool"
            boolean r3 = r3.getBoolean(r0)
            r2.mDefaultCarrierProvisionsWifiMergedNetworks = r3
            r3 = 1
            r2.mDefaultCarrierProvisionsWifiMergedNetworksLoaded = r3
        L_0x0027:
            boolean r2 = r2.mDefaultCarrierProvisionsWifiMergedNetworks
            return r2
        L_0x002a:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.CarrierConfigTracker.getCarrierProvisionsWifiMergedNetworksBool(int):boolean");
    }
}

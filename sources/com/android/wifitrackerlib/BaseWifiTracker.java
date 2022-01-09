package com.android.wifitrackerlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.ScoredNetwork;
import android.net.TransportInfo;
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import java.time.Clock;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseWifiTracker implements LifecycleObserver {
    private static boolean sVerboseLogging;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        /* class com.android.wifitrackerlib.BaseWifiTracker.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            if (!BaseWifiTracker.this.mIsStarted) {
                BaseWifiTracker.this.mIsStarted = true;
                BaseWifiTracker.this.handleOnStart();
            }
            String action = intent.getAction();
            if (BaseWifiTracker.isVerboseLoggingEnabled()) {
                String str = BaseWifiTracker.this.mTag;
                Log.v(str, "Received broadcast: " + action);
            }
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                if (BaseWifiTracker.this.mWifiManager.getWifiState() == 3) {
                    BaseWifiTracker.this.mScanner.start();
                } else {
                    BaseWifiTracker.this.mScanner.stop();
                }
                BaseWifiTracker.this.notifyOnWifiStateChanged();
                BaseWifiTracker.this.handleWifiStateChangedAction();
            } else if ("android.net.wifi.SCAN_RESULTS".equals(action)) {
                BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                NetworkScoreManager networkScoreManager = baseWifiTracker.mNetworkScoreManager;
                Stream<R> map = baseWifiTracker.mWifiManager.getScanResults().stream().map(BaseWifiTracker$1$$ExternalSyntheticLambda0.INSTANCE);
                Set set = BaseWifiTracker.this.mRequestedScoreKeys;
                Objects.requireNonNull(set);
                networkScoreManager.requestScores((Collection) map.filter(new BaseWifiTracker$1$$ExternalSyntheticLambda1(set)).collect(Collectors.toList()));
                BaseWifiTracker.this.handleScanResultsAvailableAction(intent);
            } else if ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(action)) {
                BaseWifiTracker.this.handleConfiguredNetworksChangedAction(intent);
            } else if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                BaseWifiTracker.this.handleNetworkStateChangedAction(intent);
            } else if ("android.net.wifi.RSSI_CHANGED".equals(action)) {
                BaseWifiTracker.this.handleRssiChangedAction();
            } else if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                BaseWifiTracker.this.handleDefaultSubscriptionChanged(intent.getIntExtra("subscription", -1));
            }
        }
    };
    protected final ConnectivityManager mConnectivityManager;
    protected final Context mContext;
    private final ConnectivityManager.NetworkCallback mDefaultNetworkCallback = new ConnectivityManager.NetworkCallback() {
        /* class com.android.wifitrackerlib.BaseWifiTracker.AnonymousClass3 */

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            boolean z = true;
            if (!BaseWifiTracker.this.mIsStarted) {
                BaseWifiTracker.this.mIsStarted = true;
                BaseWifiTracker.this.handleOnStart();
            }
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            boolean z2 = baseWifiTracker.mIsWifiDefaultRoute;
            boolean z3 = baseWifiTracker.mIsCellDefaultRoute;
            VcnTransportInfo transportInfo = networkCapabilities.getTransportInfo();
            BaseWifiTracker.this.mIsWifiDefaultRoute = networkCapabilities.hasTransport(1) || ((transportInfo == null || !(transportInfo instanceof VcnTransportInfo) || transportInfo.getWifiInfo() == null) ? false : true);
            BaseWifiTracker baseWifiTracker2 = BaseWifiTracker.this;
            if (baseWifiTracker2.mIsWifiDefaultRoute || !networkCapabilities.hasTransport(0)) {
                z = false;
            }
            baseWifiTracker2.mIsCellDefaultRoute = z;
            BaseWifiTracker baseWifiTracker3 = BaseWifiTracker.this;
            if (baseWifiTracker3.mIsWifiDefaultRoute != z2 || baseWifiTracker3.mIsCellDefaultRoute != z3) {
                if (BaseWifiTracker.isVerboseLoggingEnabled()) {
                    Log.v(BaseWifiTracker.this.mTag, "Wifi is the default route: " + BaseWifiTracker.this.mIsWifiDefaultRoute);
                    Log.v(BaseWifiTracker.this.mTag, "Cell is the default route: " + BaseWifiTracker.this.mIsCellDefaultRoute);
                }
                BaseWifiTracker.this.handleDefaultRouteChanged();
            }
        }

        public void onLost(Network network) {
            if (!BaseWifiTracker.this.mIsStarted) {
                BaseWifiTracker.this.mIsStarted = true;
                BaseWifiTracker.this.handleOnStart();
            }
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            baseWifiTracker.mIsWifiDefaultRoute = false;
            baseWifiTracker.mIsCellDefaultRoute = false;
            if (BaseWifiTracker.isVerboseLoggingEnabled()) {
                Log.v(BaseWifiTracker.this.mTag, "Wifi is the default route: false");
                Log.v(BaseWifiTracker.this.mTag, "Cell is the default route: false");
            }
            BaseWifiTracker.this.handleDefaultRouteChanged();
        }
    };
    protected boolean mIsCellDefaultRoute;
    private boolean mIsStarted;
    protected boolean mIsWifiDefaultRoute;
    protected boolean mIsWifiValidated;
    private final BaseWifiTrackerCallback mListener;
    protected final Handler mMainHandler;
    protected final long mMaxScanAgeMillis;
    private final ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() {
        /* class com.android.wifitrackerlib.BaseWifiTracker.AnonymousClass2 */

        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            if (!BaseWifiTracker.this.mIsStarted) {
                BaseWifiTracker.this.mIsStarted = true;
                BaseWifiTracker.this.handleOnStart();
            }
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            if (baseWifiTracker.isPrimaryWifiNetwork(baseWifiTracker.mConnectivityManager.getNetworkCapabilities(network))) {
                BaseWifiTracker.this.handleLinkPropertiesChanged(linkProperties);
            }
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            if (!BaseWifiTracker.this.mIsStarted) {
                BaseWifiTracker.this.mIsStarted = true;
                BaseWifiTracker.this.handleOnStart();
            }
            if (BaseWifiTracker.this.isPrimaryWifiNetwork(networkCapabilities)) {
                BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
                boolean z = baseWifiTracker.mIsWifiValidated;
                baseWifiTracker.mIsWifiValidated = networkCapabilities.hasCapability(16);
                if (BaseWifiTracker.isVerboseLoggingEnabled()) {
                    BaseWifiTracker baseWifiTracker2 = BaseWifiTracker.this;
                    if (baseWifiTracker2.mIsWifiValidated != z) {
                        String str = baseWifiTracker2.mTag;
                        Log.v(str, "Is Wifi validated: " + BaseWifiTracker.this.mIsWifiValidated);
                    }
                }
                BaseWifiTracker.this.handleNetworkCapabilitiesChanged(networkCapabilities);
            }
        }

        public void onLost(Network network) {
            if (!BaseWifiTracker.this.mIsStarted) {
                BaseWifiTracker.this.mIsStarted = true;
                BaseWifiTracker.this.handleOnStart();
            }
            BaseWifiTracker baseWifiTracker = BaseWifiTracker.this;
            if (baseWifiTracker.isPrimaryWifiNetwork(baseWifiTracker.mConnectivityManager.getNetworkCapabilities(network))) {
                BaseWifiTracker.this.mIsWifiValidated = false;
            }
        }
    };
    private final NetworkRequest mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).build();
    protected final NetworkScoreManager mNetworkScoreManager;
    private final Set<NetworkKey> mRequestedScoreKeys = new HashSet();
    protected final long mScanIntervalMillis;
    protected final ScanResultUpdater mScanResultUpdater;
    private final Scanner mScanner;
    private final String mTag;
    protected final WifiManager mWifiManager;
    protected final WifiNetworkScoreCache mWifiNetworkScoreCache;
    protected final Handler mWorkerHandler;

    public interface BaseWifiTrackerCallback {
        void onWifiStateChanged();
    }

    public void handleConfiguredNetworksChangedAction(Intent intent) {
    }

    public void handleDefaultRouteChanged() {
    }

    public void handleDefaultSubscriptionChanged(int i) {
    }

    public void handleLinkPropertiesChanged(LinkProperties linkProperties) {
    }

    public void handleNetworkCapabilitiesChanged(NetworkCapabilities networkCapabilities) {
    }

    public void handleNetworkScoreCacheUpdated() {
    }

    public void handleNetworkStateChangedAction(Intent intent) {
    }

    public void handleOnStart() {
    }

    public void handleRssiChangedAction() {
    }

    public void handleScanResultsAvailableAction(Intent intent) {
    }

    public void handleWifiStateChangedAction() {
    }

    public static boolean isVerboseLoggingEnabled() {
        return sVerboseLogging;
    }

    private boolean isPrimaryWifiNetwork(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return false;
        }
        TransportInfo transportInfo = networkCapabilities.getTransportInfo();
        if (!(transportInfo instanceof WifiInfo)) {
            return false;
        }
        return ((WifiInfo) transportInfo).isPrimary();
    }

    BaseWifiTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, NetworkScoreManager networkScoreManager, Handler handler, Handler handler2, Clock clock, long j, long j2, BaseWifiTrackerCallback baseWifiTrackerCallback, String str) {
        lifecycle.addObserver(this);
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mConnectivityManager = connectivityManager;
        this.mNetworkScoreManager = networkScoreManager;
        this.mMainHandler = handler;
        this.mWorkerHandler = handler2;
        this.mMaxScanAgeMillis = j;
        this.mScanIntervalMillis = j2;
        this.mListener = baseWifiTrackerCallback;
        this.mTag = str;
        this.mScanResultUpdater = new ScanResultUpdater(clock, j + j2);
        this.mWifiNetworkScoreCache = new WifiNetworkScoreCache(context, new WifiNetworkScoreCache.CacheListener(handler2) {
            /* class com.android.wifitrackerlib.BaseWifiTracker.AnonymousClass4 */

            public void networkCacheUpdated(List<ScoredNetwork> list) {
                BaseWifiTracker.this.handleNetworkScoreCacheUpdated();
            }
        });
        this.mScanner = new Scanner(handler2.getLooper());
        sVerboseLogging = wifiManager.isVerboseLoggingEnabled();
    }

    public void onStart() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, null, this.mWorkerHandler);
        this.mConnectivityManager.registerNetworkCallback(this.mNetworkRequest, this.mNetworkCallback, this.mWorkerHandler);
        this.mConnectivityManager.registerDefaultNetworkCallback(this.mDefaultNetworkCallback, this.mWorkerHandler);
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (networkCapabilities != null) {
            this.mIsWifiDefaultRoute = networkCapabilities.hasTransport(1);
            this.mIsCellDefaultRoute = networkCapabilities.hasTransport(0);
        } else {
            this.mIsWifiDefaultRoute = false;
            this.mIsCellDefaultRoute = false;
        }
        if (isVerboseLoggingEnabled()) {
            String str = this.mTag;
            Log.v(str, "Wifi is the default route: " + this.mIsWifiDefaultRoute);
            String str2 = this.mTag;
            Log.v(str2, "Cell is the default route: " + this.mIsCellDefaultRoute);
        }
        this.mNetworkScoreManager.registerNetworkScoreCache(1, this.mWifiNetworkScoreCache, 2);
        this.mWorkerHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda2(this));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onStart$0() {
        if (!this.mIsStarted) {
            this.mIsStarted = true;
            handleOnStart();
        }
    }

    public void onStop() {
        Handler handler = this.mWorkerHandler;
        Scanner scanner = this.mScanner;
        Objects.requireNonNull(scanner);
        handler.post(new BaseWifiTracker$$ExternalSyntheticLambda1(scanner));
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        this.mConnectivityManager.unregisterNetworkCallback(this.mDefaultNetworkCallback);
        this.mNetworkScoreManager.unregisterNetworkScoreCache(1, this.mWifiNetworkScoreCache);
        Handler handler2 = this.mWorkerHandler;
        Set<NetworkKey> set = this.mRequestedScoreKeys;
        Objects.requireNonNull(set);
        handler2.post(new BaseWifiTracker$$ExternalSyntheticLambda3(set));
        this.mIsStarted = false;
    }

    public class Scanner extends Handler {
        private boolean mIsActive;
        private int mRetry;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        private Scanner(Looper looper) {
            super(looper);
            BaseWifiTracker.this = r1;
            this.mRetry = 0;
        }

        private void start() {
            if (!this.mIsActive) {
                this.mIsActive = true;
                if (BaseWifiTracker.isVerboseLoggingEnabled()) {
                    Log.v(BaseWifiTracker.this.mTag, "Scanner start");
                }
                postScan();
            }
        }

        private void stop() {
            this.mIsActive = false;
            if (BaseWifiTracker.isVerboseLoggingEnabled()) {
                Log.v(BaseWifiTracker.this.mTag, "Scanner stop");
            }
            this.mRetry = 0;
            removeCallbacksAndMessages(null);
        }

        /* access modifiers changed from: public */
        private void postScan() {
            if (BaseWifiTracker.this.mWifiManager.startScan()) {
                this.mRetry = 0;
            } else {
                int i = this.mRetry + 1;
                this.mRetry = i;
                if (i >= 3) {
                    if (BaseWifiTracker.isVerboseLoggingEnabled()) {
                        String str = BaseWifiTracker.this.mTag;
                        Log.v(str, "Scanner failed to start scan " + this.mRetry + " times!");
                    }
                    this.mRetry = 0;
                    return;
                }
            }
            postDelayed(new BaseWifiTracker$Scanner$$ExternalSyntheticLambda0(this), BaseWifiTracker.this.mScanIntervalMillis);
        }
    }

    private void notifyOnWifiStateChanged() {
        BaseWifiTrackerCallback baseWifiTrackerCallback = this.mListener;
        if (baseWifiTrackerCallback != null) {
            Handler handler = this.mMainHandler;
            Objects.requireNonNull(baseWifiTrackerCallback);
            handler.post(new BaseWifiTracker$$ExternalSyntheticLambda0(baseWifiTrackerCallback));
        }
    }
}

package com.android.systemui.qs.tiles.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.UserHandle;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.DeviceInfoUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.net.SignalStrengthUtil;
import com.android.settingslib.wifi.WifiUtils;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.toast.SystemUIToast;
import com.android.systemui.toast.ToastFactory;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InternetDialogController implements WifiEntry.DisconnectCallback, NetworkController.AccessPointController.AccessPointCallback {
    private static final boolean DEBUG = Log.isLoggable("InternetDialogController", 3);
    public static final Drawable EMPTY_DRAWABLE = new ColorDrawable(0);
    static final long SHORT_DURATION_TIMEOUT = 4000;
    private static final int SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE = R$string.all_network_unavailable;
    private static final int SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE = R$string.non_carrier_network_unavailable;
    private static final int SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS = R$string.wifi_empty_list_wifi_on;
    private static final int SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT = R$string.tap_a_network_to_connect;
    private static final int SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS = R$string.unlock_to_view_networks;
    private static final int SUBTITLE_TEXT_WIFI_IS_OFF = R$string.wifi_is_off;
    static final float TOAST_PARAMS_HORIZONTAL_WEIGHT = 1.0f;
    static final float TOAST_PARAMS_VERTICAL_WEIGHT = 1.0f;
    private NetworkController.AccessPointController mAccessPointController;
    protected ActivityStarter mActivityStarter;
    private BroadcastDispatcher mBroadcastDispatcher;
    private InternetDialogCallback mCallback;
    protected boolean mCanConfigWifi;
    private CarrierConfigTracker mCarrierConfigTracker;
    private MobileMappings.Config mConfig = null;
    private WifiEntry mConnectedEntry;
    private IntentFilter mConnectionStateFilter;
    private final BroadcastReceiver mConnectionStateReceiver = new BroadcastReceiver() {
        /* class com.android.systemui.qs.tiles.dialog.InternetDialogController.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                if (InternetDialogController.DEBUG) {
                    Log.d("InternetDialogController", "ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
                }
                InternetDialogController.this.mConfig = MobileMappings.Config.readConfig(context);
                InternetDialogController.this.updateListener();
            } else if ("android.net.wifi.supplicant.CONNECTION_CHANGE".equals(action)) {
                InternetDialogController.this.updateListener();
            }
        }
    };
    private ConnectivityManager mConnectivityManager;
    private ConnectivityManager.NetworkCallback mConnectivityManagerNetworkCallback;
    private Context mContext;
    private int mDefaultDataSubId = -1;
    private Executor mExecutor;
    private GlobalSettings mGlobalSettings;
    private Handler mHandler;
    protected boolean mHasEthernet = false;
    protected InternetTelephonyCallback mInternetTelephonyCallback;
    protected KeyguardStateController mKeyguardStateController;
    private final KeyguardUpdateMonitorCallback mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() {
        /* class com.android.systemui.qs.tiles.dialog.InternetDialogController.AnonymousClass1 */

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onRefreshCarrierInfo() {
            InternetDialogController.this.mCallback.onRefreshCarrierInfo();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onSimStateChanged(int i, int i2, int i3) {
            InternetDialogController.this.mCallback.onSimStateChanged();
        }
    };
    private KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private LocationController mLocationController;
    protected SubscriptionManager.OnSubscriptionsChangedListener mOnSubscriptionsChangedListener;
    private SignalDrawable mSignalDrawable;
    private SubscriptionManager mSubscriptionManager;
    private TelephonyDisplayInfo mTelephonyDisplayInfo = new TelephonyDisplayInfo(0, 0);
    private TelephonyManager mTelephonyManager;
    private ToastFactory mToastFactory;
    private UiEventLogger mUiEventLogger;
    private int mWifiEntriesCount;
    protected WifiUtils.InternetIconInjector mWifiIconInjector;
    private WifiManager mWifiManager;
    private WindowManager mWindowManager;
    private Handler mWorkerHandler;

    /* access modifiers changed from: package-private */
    public interface InternetDialogCallback {
        void dismissDialog();

        void onAccessPointsChanged(List<WifiEntry> list, WifiEntry wifiEntry);

        void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities);

        void onDataConnectionStateChanged(int i, int i2);

        void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo);

        void onLost(Network network);

        void onRefreshCarrierInfo();

        void onServiceStateChanged(ServiceState serviceState);

        void onSignalStrengthsChanged(SignalStrength signalStrength);

        void onSimStateChanged();

        void onSubscriptionsChanged(int i);

        void onUserMobileDataStateChanged(boolean z);
    }

    @Override // com.android.wifitrackerlib.WifiEntry.DisconnectCallback
    public void onDisconnectResult(int i) {
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController.AccessPointCallback
    public void onSettingsActivityTriggered(Intent intent) {
    }

    /* access modifiers changed from: protected */
    public List<SubscriptionInfo> getSubscriptionInfo() {
        return this.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo(false);
    }

    public InternetDialogController(Context context, UiEventLogger uiEventLogger, ActivityStarter activityStarter, NetworkController.AccessPointController accessPointController, SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Executor executor, BroadcastDispatcher broadcastDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, GlobalSettings globalSettings, KeyguardStateController keyguardStateController, WindowManager windowManager, ToastFactory toastFactory, Handler handler2, CarrierConfigTracker carrierConfigTracker, LocationController locationController) {
        if (DEBUG) {
            Log.d("InternetDialogController", "Init InternetDialogController");
        }
        this.mHandler = handler;
        this.mWorkerHandler = handler2;
        this.mExecutor = executor;
        this.mContext = context;
        this.mGlobalSettings = globalSettings;
        this.mWifiManager = wifiManager;
        this.mTelephonyManager = telephonyManager;
        this.mConnectivityManager = connectivityManager;
        this.mSubscriptionManager = subscriptionManager;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        IntentFilter intentFilter = new IntentFilter();
        this.mConnectionStateFilter = intentFilter;
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        this.mConnectionStateFilter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        this.mUiEventLogger = uiEventLogger;
        this.mActivityStarter = activityStarter;
        this.mAccessPointController = accessPointController;
        this.mWifiIconInjector = new WifiUtils.InternetIconInjector(this.mContext);
        this.mConnectivityManagerNetworkCallback = new DataConnectivityListener();
        this.mWindowManager = windowManager;
        this.mToastFactory = toastFactory;
        this.mSignalDrawable = new SignalDrawable(this.mContext);
        this.mLocationController = locationController;
    }

    /* access modifiers changed from: package-private */
    public void onStart(InternetDialogCallback internetDialogCallback, boolean z) {
        boolean z2 = DEBUG;
        if (z2) {
            Log.d("InternetDialogController", "onStart");
        }
        this.mCallback = internetDialogCallback;
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateCallback);
        this.mAccessPointController.addAccessPointCallback(this);
        this.mBroadcastDispatcher.registerReceiver(this.mConnectionStateReceiver, this.mConnectionStateFilter, this.mExecutor);
        InternetOnSubscriptionChangedListener internetOnSubscriptionChangedListener = new InternetOnSubscriptionChangedListener();
        this.mOnSubscriptionsChangedListener = internetOnSubscriptionChangedListener;
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mExecutor, internetOnSubscriptionChangedListener);
        this.mDefaultDataSubId = getDefaultDataSubscriptionId();
        if (z2) {
            Log.d("InternetDialogController", "Init, SubId: " + this.mDefaultDataSubId);
        }
        this.mConfig = MobileMappings.Config.readConfig(this.mContext);
        this.mTelephonyManager = this.mTelephonyManager.createForSubscriptionId(this.mDefaultDataSubId);
        TelephonyCallback internetTelephonyCallback = new InternetTelephonyCallback();
        this.mInternetTelephonyCallback = internetTelephonyCallback;
        this.mTelephonyManager.registerTelephonyCallback(this.mExecutor, internetTelephonyCallback);
        this.mConnectivityManager.registerDefaultNetworkCallback(this.mConnectivityManagerNetworkCallback);
        this.mCanConfigWifi = z;
        scanWifiAccessPoints();
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        if (DEBUG) {
            Log.d("InternetDialogController", "onStop");
        }
        this.mBroadcastDispatcher.unregisterReceiver(this.mConnectionStateReceiver);
        this.mTelephonyManager.unregisterTelephonyCallback(this.mInternetTelephonyCallback);
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener(this.mOnSubscriptionsChangedListener);
        this.mAccessPointController.removeAccessPointCallback(this);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateCallback);
        this.mConnectivityManager.unregisterNetworkCallback(this.mConnectivityManagerNetworkCallback);
    }

    /* access modifiers changed from: package-private */
    public boolean isAirplaneModeEnabled() {
        return this.mGlobalSettings.getInt("airplane_mode_on", 0) != 0;
    }

    /* access modifiers changed from: protected */
    public int getDefaultDataSubscriptionId() {
        return SubscriptionManager.getDefaultDataSubscriptionId();
    }

    /* access modifiers changed from: protected */
    public Intent getSettingsIntent() {
        return new Intent("android.settings.NETWORK_PROVIDER_SETTINGS").addFlags(268435456);
    }

    /* access modifiers changed from: protected */
    public Intent getWifiDetailsSettingsIntent(String str) {
        if (!TextUtils.isEmpty(str)) {
            return WifiUtils.getWifiDetailsSettingsIntent(str);
        }
        if (!DEBUG) {
            return null;
        }
        Log.d("InternetDialogController", "connected entry's key is empty");
        return null;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getDialogTitleText() {
        if (isAirplaneModeEnabled()) {
            return this.mContext.getText(R$string.airplane_mode);
        }
        return this.mContext.getText(R$string.quick_settings_internet_label);
    }

    /* access modifiers changed from: package-private */
    public CharSequence getSubtitleText(boolean z) {
        if (isAirplaneModeEnabled()) {
            return null;
        }
        if (this.mCanConfigWifi && !this.mWifiManager.isWifiEnabled()) {
            if (DEBUG) {
                Log.d("InternetDialogController", "Airplane mode off + Wi-Fi off.");
            }
            return this.mContext.getText(SUBTITLE_TEXT_WIFI_IS_OFF);
        } else if (isDeviceLocked()) {
            if (DEBUG) {
                Log.d("InternetDialogController", "The device is locked.");
            }
            return this.mContext.getText(SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS);
        } else if (this.mConnectedEntry != null || this.mWifiEntriesCount > 0) {
            if (this.mCanConfigWifi) {
                return this.mContext.getText(SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT);
            }
            return null;
        } else if (this.mCanConfigWifi && z) {
            return this.mContext.getText(SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS);
        } else {
            boolean z2 = DEBUG;
            if (z2) {
                Log.d("InternetDialogController", "No Wi-Fi item.");
            }
            if (!hasCarrier() || (!isVoiceStateInService() && !isDataStateInService())) {
                if (z2) {
                    Log.d("InternetDialogController", "No carrier or service is out of service.");
                }
                return this.mContext.getText(SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE);
            } else if (this.mCanConfigWifi && !isMobileDataEnabled()) {
                if (z2) {
                    Log.d("InternetDialogController", "Mobile data off");
                }
                return this.mContext.getText(SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE);
            } else if (!activeNetworkIsCellular()) {
                if (z2) {
                    Log.d("InternetDialogController", "No carrier data.");
                }
                return this.mContext.getText(SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE);
            } else if (this.mCanConfigWifi) {
                return this.mContext.getText(SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE);
            } else {
                return null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Drawable getInternetWifiDrawable(WifiEntry wifiEntry) {
        Drawable icon;
        if (wifiEntry.getLevel() == -1 || (icon = this.mWifiIconInjector.getIcon(wifiEntry.shouldShowXLevelIcon(), wifiEntry.getLevel())) == null) {
            return null;
        }
        icon.setTint(this.mContext.getColor(R$color.connected_network_primary_color));
        return icon;
    }

    /* access modifiers changed from: package-private */
    public Drawable getSignalStrengthDrawable() {
        Drawable drawable = this.mContext.getDrawable(R$drawable.ic_signal_strength_zero_bar_no_internet);
        try {
            if (this.mTelephonyManager == null) {
                if (DEBUG) {
                    Log.d("InternetDialogController", "TelephonyManager is null");
                }
                return drawable;
            }
            if (isDataStateInService() || isVoiceStateInService()) {
                AtomicReference atomicReference = new AtomicReference();
                atomicReference.set(getSignalStrengthDrawableWithLevel());
                drawable = (Drawable) atomicReference.get();
            }
            int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(this.mContext, 16843282);
            if (activeNetworkIsCellular() || isCarrierNetworkActive()) {
                colorAttrDefaultColor = this.mContext.getColor(R$color.connected_network_primary_color);
            }
            drawable.setTint(colorAttrDefaultColor);
            return drawable;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public Drawable getSignalStrengthDrawableWithLevel() {
        int i;
        SignalStrength signalStrength = this.mTelephonyManager.getSignalStrength();
        if (signalStrength == null) {
            i = 0;
        } else {
            i = signalStrength.getLevel();
        }
        int i2 = 5;
        if (this.mSubscriptionManager != null && shouldInflateSignalStrength(this.mDefaultDataSubId)) {
            i++;
            i2 = 6;
        }
        return getSignalStrengthIcon(this.mContext, i, i2, 0, !isMobileDataEnabled());
    }

    /* access modifiers changed from: package-private */
    public Drawable getSignalStrengthIcon(Context context, int i, int i2, int i3, boolean z) {
        Drawable drawable;
        this.mSignalDrawable.setLevel(SignalDrawable.getState(i, i2, z));
        if (i3 == 0) {
            drawable = EMPTY_DRAWABLE;
        } else {
            drawable = context.getResources().getDrawable(i3, context.getTheme());
        }
        Drawable[] drawableArr = {drawable, this.mSignalDrawable};
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.signal_strength_icon_size);
        LayerDrawable layerDrawable = new LayerDrawable(drawableArr);
        layerDrawable.setLayerGravity(0, 51);
        layerDrawable.setLayerGravity(1, 85);
        layerDrawable.setLayerSize(1, dimensionPixelSize, dimensionPixelSize);
        layerDrawable.setTintList(Utils.getColorAttr(context, 16843282));
        return layerDrawable;
    }

    private boolean shouldInflateSignalStrength(int i) {
        return SignalStrengthUtil.shouldInflateSignalStrength(this.mContext, i);
    }

    private CharSequence getUniqueSubscriptionDisplayName(int i, Context context) {
        return getUniqueSubscriptionDisplayNames(context).getOrDefault(Integer.valueOf(i), "");
    }

    private Map<Integer, CharSequence> getUniqueSubscriptionDisplayNames(Context context) {
        InternetDialogController$$ExternalSyntheticLambda12 internetDialogController$$ExternalSyntheticLambda12 = new InternetDialogController$$ExternalSyntheticLambda12(this);
        HashSet hashSet = new HashSet();
        InternetDialogController$$ExternalSyntheticLambda13 internetDialogController$$ExternalSyntheticLambda13 = new InternetDialogController$$ExternalSyntheticLambda13(internetDialogController$$ExternalSyntheticLambda12, (Set) ((Stream) internetDialogController$$ExternalSyntheticLambda12.get()).filter(new InternetDialogController$$ExternalSyntheticLambda9(hashSet)).map(InternetDialogController$$ExternalSyntheticLambda7.INSTANCE).collect(Collectors.toSet()), context);
        hashSet.clear();
        return (Map) ((Stream) internetDialogController$$ExternalSyntheticLambda13.get()).map(new InternetDialogController$$ExternalSyntheticLambda2((Set) ((Stream) internetDialogController$$ExternalSyntheticLambda13.get()).filter(new InternetDialogController$$ExternalSyntheticLambda8(hashSet)).map(InternetDialogController$$ExternalSyntheticLambda5.INSTANCE).collect(Collectors.toSet()))).collect(Collectors.toMap(InternetDialogController$$ExternalSyntheticLambda6.INSTANCE, InternetDialogController$$ExternalSyntheticLambda4.INSTANCE));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ Stream lambda$getUniqueSubscriptionDisplayNames$2() {
        return getSubscriptionInfo().stream().filter(InternetDialogController$$ExternalSyntheticLambda10.INSTANCE).map(new InternetDialogController$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getUniqueSubscriptionDisplayNames$0(SubscriptionInfo subscriptionInfo) {
        return (subscriptionInfo == null || subscriptionInfo.getDisplayName() == null) ? false : true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ AnonymousClass1DisplayInfo lambda$getUniqueSubscriptionDisplayNames$1(SubscriptionInfo subscriptionInfo) {
        AnonymousClass1DisplayInfo r0 = new Object() {
            /* class com.android.systemui.qs.tiles.dialog.InternetDialogController.AnonymousClass1DisplayInfo */
            public CharSequence originalName;
            public SubscriptionInfo subscriptionInfo;
            public CharSequence uniqueName;
        };
        r0.subscriptionInfo = subscriptionInfo;
        r0.originalName = subscriptionInfo.getDisplayName().toString().trim();
        return r0;
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getUniqueSubscriptionDisplayNames$3(Set set, AnonymousClass1DisplayInfo r1) {
        return !set.add(r1.originalName);
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ Stream lambda$getUniqueSubscriptionDisplayNames$6(Supplier supplier, Set set, Context context) {
        return ((Stream) supplier.get()).map(new InternetDialogController$$ExternalSyntheticLambda3(set, context));
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ AnonymousClass1DisplayInfo lambda$getUniqueSubscriptionDisplayNames$5(Set set, Context context, AnonymousClass1DisplayInfo r3) {
        if (set.contains(r3.originalName)) {
            String bidiFormattedPhoneNumber = DeviceInfoUtils.getBidiFormattedPhoneNumber(context, r3.subscriptionInfo);
            if (bidiFormattedPhoneNumber == null) {
                bidiFormattedPhoneNumber = "";
            } else if (bidiFormattedPhoneNumber.length() > 4) {
                bidiFormattedPhoneNumber = bidiFormattedPhoneNumber.substring(bidiFormattedPhoneNumber.length() - 4);
            }
            if (TextUtils.isEmpty(bidiFormattedPhoneNumber)) {
                r3.uniqueName = r3.originalName;
            } else {
                r3.uniqueName = ((Object) r3.originalName) + " " + bidiFormattedPhoneNumber;
            }
        } else {
            r3.uniqueName = r3.originalName;
        }
        return r3;
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getUniqueSubscriptionDisplayNames$7(Set set, AnonymousClass1DisplayInfo r1) {
        return !set.add(r1.uniqueName);
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ AnonymousClass1DisplayInfo lambda$getUniqueSubscriptionDisplayNames$9(Set set, AnonymousClass1DisplayInfo r2) {
        if (set.contains(r2.uniqueName)) {
            r2.uniqueName = ((Object) r2.originalName) + " " + r2.subscriptionInfo.getSubscriptionId();
        }
        return r2;
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$getUniqueSubscriptionDisplayNames$10(AnonymousClass1DisplayInfo r0) {
        return Integer.valueOf(r0.subscriptionInfo.getSubscriptionId());
    }

    /* access modifiers changed from: package-private */
    public CharSequence getMobileNetworkTitle() {
        return getUniqueSubscriptionDisplayName(this.mDefaultDataSubId, this.mContext);
    }

    /* access modifiers changed from: package-private */
    public String getMobileNetworkSummary() {
        return getMobileSummary(this.mContext, getNetworkTypeDescription(this.mContext, this.mConfig, this.mTelephonyDisplayInfo, this.mDefaultDataSubId));
    }

    private String getNetworkTypeDescription(Context context, MobileMappings.Config config, TelephonyDisplayInfo telephonyDisplayInfo, int i) {
        String iconKey = MobileMappings.getIconKey(telephonyDisplayInfo);
        if (MobileMappings.mapIconSets(config) == null || MobileMappings.mapIconSets(config).get(iconKey) == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "The description of network type is empty.");
            }
            return "";
        }
        int i2 = MobileMappings.mapIconSets(config).get(iconKey).dataContentDescription;
        if (isCarrierNetworkActive()) {
            i2 = TelephonyIcons.CARRIER_MERGED_WIFI.dataContentDescription;
        }
        if (i2 != 0) {
            return SubscriptionManager.getResourcesForSubId(context, i).getString(i2);
        }
        return "";
    }

    private String getMobileSummary(Context context, String str) {
        if (!isMobileDataEnabled()) {
            return context.getString(R$string.mobile_data_off_summary);
        }
        if (!isDataStateInService()) {
            return context.getString(R$string.mobile_data_no_connection);
        }
        if (!activeNetworkIsCellular() && !isCarrierNetworkActive()) {
            return str;
        }
        return context.getString(R$string.preference_summary_default_combination, context.getString(R$string.mobile_data_connection_active), str);
    }

    /* access modifiers changed from: package-private */
    public void launchNetworkSetting() {
        this.mCallback.dismissDialog();
        this.mActivityStarter.postStartActivityDismissingKeyguard(getSettingsIntent(), 0);
    }

    /* access modifiers changed from: package-private */
    public void launchWifiNetworkDetailsSetting(String str) {
        Intent wifiDetailsSettingsIntent = getWifiDetailsSettingsIntent(str);
        if (wifiDetailsSettingsIntent != null) {
            this.mCallback.dismissDialog();
            this.mActivityStarter.postStartActivityDismissingKeyguard(wifiDetailsSettingsIntent, 0);
        }
    }

    /* access modifiers changed from: package-private */
    public void launchWifiScanningSetting() {
        this.mCallback.dismissDialog();
        Intent intent = new Intent("android.settings.WIFI_SCANNING_SETTINGS");
        intent.addFlags(268435456);
        this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
    }

    /* access modifiers changed from: package-private */
    public void connectCarrierNetwork() {
        MergedCarrierEntry mergedCarrierEntry = this.mAccessPointController.getMergedCarrierEntry();
        if (mergedCarrierEntry != null && mergedCarrierEntry.canConnect()) {
            mergedCarrierEntry.connect(null, false);
            makeOverlayToast(R$string.wifi_wont_autoconnect_for_now);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isCarrierNetworkActive() {
        MergedCarrierEntry mergedCarrierEntry = this.mAccessPointController.getMergedCarrierEntry();
        return mergedCarrierEntry != null && mergedCarrierEntry.isDefaultNetwork();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: setMergedCarrierWifiEnabledIfNeed */
    public void lambda$setMobileDataEnabled$12(int i, boolean z) {
        if (!this.mCarrierConfigTracker.getCarrierProvisionsWifiMergedNetworksBool(i)) {
            MergedCarrierEntry mergedCarrierEntry = this.mAccessPointController.getMergedCarrierEntry();
            if (mergedCarrierEntry != null) {
                mergedCarrierEntry.setEnabled(z);
            } else if (DEBUG) {
                Log.d("InternetDialogController", "MergedCarrierEntry is null, can not set the status.");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public WifiManager getWifiManager() {
        return this.mWifiManager;
    }

    /* access modifiers changed from: package-private */
    public TelephonyManager getTelephonyManager() {
        return this.mTelephonyManager;
    }

    /* access modifiers changed from: package-private */
    public SubscriptionManager getSubscriptionManager() {
        return this.mSubscriptionManager;
    }

    /* access modifiers changed from: package-private */
    public boolean hasCarrier() {
        if (this.mSubscriptionManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "SubscriptionManager is null, can not check carrier.");
            }
            return false;
        } else if (isAirplaneModeEnabled() || this.mTelephonyManager == null || this.mSubscriptionManager.getActiveSubscriptionIdList().length <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isMobileDataEnabled() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        return telephonyManager != null && telephonyManager.isDataEnabled();
    }

    /* access modifiers changed from: package-private */
    public void setMobileDataEnabled(Context context, int i, boolean z, boolean z2) {
        List<SubscriptionInfo> activeSubscriptionInfoList;
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "TelephonyManager is null, can not set mobile data.");
            }
        } else if (this.mSubscriptionManager != null) {
            telephonyManager.setDataEnabled(z);
            if (z2 && (activeSubscriptionInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList()) != null) {
                for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                    if (subscriptionInfo.getSubscriptionId() != i && !subscriptionInfo.isOpportunistic()) {
                        ((TelephonyManager) context.getSystemService(TelephonyManager.class)).createForSubscriptionId(subscriptionInfo.getSubscriptionId()).setDataEnabled(false);
                    }
                }
            }
            this.mWorkerHandler.post(new InternetDialogController$$ExternalSyntheticLambda0(this, i, z));
        } else if (DEBUG) {
            Log.d("InternetDialogController", "SubscriptionManager is null, can not set mobile data.");
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isDataStateInService() {
        NetworkRegistrationInfo networkRegistrationInfo;
        ServiceState serviceState = this.mTelephonyManager.getServiceState();
        if (serviceState == null) {
            networkRegistrationInfo = null;
        } else {
            networkRegistrationInfo = serviceState.getNetworkRegistrationInfo(2, 1);
        }
        if (networkRegistrationInfo == null) {
            return false;
        }
        return networkRegistrationInfo.isRegistered();
    }

    /* access modifiers changed from: package-private */
    public boolean isVoiceStateInService() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "TelephonyManager is null, can not detect voice state.");
            }
            return false;
        }
        ServiceState serviceState = telephonyManager.getServiceState();
        if (serviceState == null || serviceState.getState() != 0) {
            return false;
        }
        return true;
    }

    public boolean isDeviceLocked() {
        return !this.mKeyguardStateController.isUnlocked();
    }

    /* access modifiers changed from: package-private */
    public boolean activeNetworkIsCellular() {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "ConnectivityManager is null, can not check active network.");
            }
            return false;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null || (networkCapabilities = this.mConnectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
            return false;
        }
        return networkCapabilities.hasTransport(0);
    }

    /* access modifiers changed from: package-private */
    public boolean connect(WifiEntry wifiEntry) {
        if (wifiEntry == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "No Wi-Fi ap to connect.");
            }
            return false;
        }
        if (wifiEntry.getWifiConfiguration() != null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "connect networkId=" + wifiEntry.getWifiConfiguration().networkId);
            }
        } else if (DEBUG) {
            Log.d("InternetDialogController", "connect to unsaved network " + wifiEntry.getTitle());
        }
        wifiEntry.connect(new WifiEntryConnectCallback(this.mActivityStarter, wifiEntry, this));
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isWifiScanEnabled() {
        if (!this.mLocationController.isLocationEnabled()) {
            return false;
        }
        return this.mWifiManager.isScanAlwaysAvailable();
    }

    /* access modifiers changed from: package-private */
    public static class WifiEntryConnectCallback implements WifiEntry.ConnectCallback {
        final ActivityStarter mActivityStarter;
        final InternetDialogController mInternetDialogController;
        final WifiEntry mWifiEntry;

        WifiEntryConnectCallback(ActivityStarter activityStarter, WifiEntry wifiEntry, InternetDialogController internetDialogController) {
            this.mActivityStarter = activityStarter;
            this.mWifiEntry = wifiEntry;
            this.mInternetDialogController = internetDialogController;
        }

        @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
        public void onConnectResult(int i) {
            if (InternetDialogController.DEBUG) {
                Log.d("InternetDialogController", "onConnectResult " + i);
            }
            if (i == 1) {
                Intent putExtra = new Intent("com.android.settings.WIFI_DIALOG").putExtra("key_chosen_wifientry_key", this.mWifiEntry.getKey());
                putExtra.addFlags(268435456);
                this.mActivityStarter.startActivity(putExtra, false);
            } else if (i == 2) {
                this.mInternetDialogController.makeOverlayToast(R$string.wifi_failed_connect_message);
            } else if (InternetDialogController.DEBUG) {
                Log.d("InternetDialogController", "connect failure reason=" + i);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void scanWifiAccessPoints() {
        if (this.mCanConfigWifi) {
            this.mAccessPointController.scanForAccessPoints();
        }
    }

    @Override // com.android.systemui.statusbar.policy.NetworkController.AccessPointController.AccessPointCallback
    public void onAccessPointsChanged(List<WifiEntry> list) {
        boolean z;
        if (this.mCanConfigWifi) {
            int i = 0;
            if (list == null || list.size() == 0) {
                this.mConnectedEntry = null;
                this.mWifiEntriesCount = 0;
                InternetDialogCallback internetDialogCallback = this.mCallback;
                if (internetDialogCallback != null) {
                    internetDialogCallback.onAccessPointsChanged(null, null);
                    return;
                }
                return;
            }
            int size = list.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    z = false;
                    break;
                }
                WifiEntry wifiEntry = list.get(i2);
                if (wifiEntry.isDefaultNetwork() && wifiEntry.hasInternetAccess()) {
                    this.mConnectedEntry = wifiEntry;
                    z = true;
                    break;
                }
                i2++;
            }
            if (!z) {
                this.mConnectedEntry = null;
            }
            int i3 = 4;
            if (this.mHasEthernet) {
                i3 = 3;
            }
            if (hasCarrier()) {
                i3--;
            }
            if (z) {
                i3--;
            }
            List<WifiEntry> list2 = (List) list.stream().filter(InternetDialogController$$ExternalSyntheticLambda11.INSTANCE).limit((long) i3).collect(Collectors.toList());
            if (list2 != null) {
                i = list2.size();
            }
            this.mWifiEntriesCount = i;
            InternetDialogCallback internetDialogCallback2 = this.mCallback;
            if (internetDialogCallback2 != null) {
                internetDialogCallback2.onAccessPointsChanged(list2, this.mConnectedEntry);
            }
        }
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onAccessPointsChanged$13(WifiEntry wifiEntry) {
        return !wifiEntry.isDefaultNetwork() || !wifiEntry.hasInternetAccess();
    }

    /* access modifiers changed from: private */
    public class InternetTelephonyCallback extends TelephonyCallback implements TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.UserMobileDataStateListener {
        private InternetTelephonyCallback() {
        }

        public void onServiceStateChanged(ServiceState serviceState) {
            InternetDialogController.this.mCallback.onServiceStateChanged(serviceState);
        }

        public void onDataConnectionStateChanged(int i, int i2) {
            InternetDialogController.this.mCallback.onDataConnectionStateChanged(i, i2);
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            InternetDialogController.this.mCallback.onSignalStrengthsChanged(signalStrength);
        }

        public void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            InternetDialogController.this.mTelephonyDisplayInfo = telephonyDisplayInfo;
            InternetDialogController.this.mCallback.onDisplayInfoChanged(telephonyDisplayInfo);
        }

        public void onUserMobileDataStateChanged(boolean z) {
            InternetDialogController.this.mCallback.onUserMobileDataStateChanged(z);
        }
    }

    /* access modifiers changed from: private */
    public class InternetOnSubscriptionChangedListener extends SubscriptionManager.OnSubscriptionsChangedListener {
        InternetOnSubscriptionChangedListener() {
        }

        public void onSubscriptionsChanged() {
            InternetDialogController.this.updateListener();
        }
    }

    private class DataConnectivityListener extends ConnectivityManager.NetworkCallback {
        private DataConnectivityListener() {
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            InternetDialogController.this.mHasEthernet = networkCapabilities.hasTransport(3);
            InternetDialogController internetDialogController = InternetDialogController.this;
            if (internetDialogController.mCanConfigWifi && (internetDialogController.mHasEthernet || networkCapabilities.hasTransport(1))) {
                InternetDialogController.this.scanWifiAccessPoints();
            }
            InternetDialogController.this.mCallback.onCapabilitiesChanged(network, networkCapabilities);
        }

        public void onLost(Network network) {
            InternetDialogController internetDialogController = InternetDialogController.this;
            internetDialogController.mHasEthernet = false;
            internetDialogController.mCallback.onLost(network);
        }
    }

    public boolean hasEthernet() {
        return this.mHasEthernet;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateListener() {
        int defaultDataSubscriptionId = getDefaultDataSubscriptionId();
        if (this.mDefaultDataSubId != getDefaultDataSubscriptionId()) {
            this.mDefaultDataSubId = defaultDataSubscriptionId;
            if (DEBUG) {
                Log.d("InternetDialogController", "DDS: defaultDataSubId:" + this.mDefaultDataSubId);
            }
            if (SubscriptionManager.isUsableSubscriptionId(this.mDefaultDataSubId)) {
                this.mTelephonyManager.unregisterTelephonyCallback(this.mInternetTelephonyCallback);
                TelephonyManager createForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(this.mDefaultDataSubId);
                this.mTelephonyManager = createForSubscriptionId;
                Handler handler = this.mHandler;
                Objects.requireNonNull(handler);
                createForSubscriptionId.registerTelephonyCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), this.mInternetTelephonyCallback);
                this.mCallback.onSubscriptionsChanged(this.mDefaultDataSubId);
            }
        } else if (DEBUG) {
            Log.d("InternetDialogController", "DDS: no change");
        }
    }

    public WifiUtils.InternetIconInjector getWifiIconInjector() {
        return this.mWifiIconInjector;
    }

    /* access modifiers changed from: package-private */
    public void makeOverlayToast(int i) {
        Resources resources = this.mContext.getResources();
        final SystemUIToast createToast = this.mToastFactory.createToast(this.mContext, resources.getString(i), this.mContext.getPackageName(), UserHandle.myUserId(), resources.getConfiguration().orientation);
        if (createToast != null) {
            final View view = createToast.getView();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -2;
            layoutParams.format = -3;
            layoutParams.type = 2017;
            layoutParams.y = createToast.getYOffset().intValue();
            int absoluteGravity = Gravity.getAbsoluteGravity(createToast.getGravity().intValue(), resources.getConfiguration().getLayoutDirection());
            layoutParams.gravity = absoluteGravity;
            if ((absoluteGravity & 7) == 7) {
                layoutParams.horizontalWeight = 1.0f;
            }
            if ((absoluteGravity & 112) == 112) {
                layoutParams.verticalWeight = 1.0f;
            }
            this.mWindowManager.addView(view, layoutParams);
            Animator inAnimation = createToast.getInAnimation();
            if (inAnimation != null) {
                inAnimation.start();
            }
            this.mHandler.postDelayed(new Runnable() {
                /* class com.android.systemui.qs.tiles.dialog.InternetDialogController.AnonymousClass3 */

                public void run() {
                    Animator outAnimation = createToast.getOutAnimation();
                    if (outAnimation != null) {
                        outAnimation.start();
                        outAnimation.addListener(new AnimatorListenerAdapter() {
                            /* class com.android.systemui.qs.tiles.dialog.InternetDialogController.AnonymousClass3.AnonymousClass1 */

                            public void onAnimationEnd(Animator animator) {
                                InternetDialogController.this.mWindowManager.removeViewImmediate(view);
                            }
                        });
                    }
                }
            }, SHORT_DURATION_TIMEOUT);
        }
    }
}

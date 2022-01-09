package com.android.systemui.statusbar.policy;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsException;
import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsRegistrationAttributes;
import android.telephony.ims.RegistrationManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.SignalIcon$MobileState;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.net.SignalStrengthUtil;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.statusbar.FeatureFlags;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.util.CarrierConfigTracker;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MobileSignalController extends SignalController<SignalIcon$MobileState, SignalIcon$MobileIconGroup> {
    private static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private MobileStatusTracker.Callback mCallback;
    private final CarrierConfigTracker mCarrierConfigTracker;
    private MobileMappings.Config mConfig;
    private int mDataState = 0;
    private SignalIcon$MobileIconGroup mDefaultIcons;
    private final MobileStatusTracker.SubscriptionDefaults mDefaults;
    private final ImsMmTelManager mImsMmTelManager;
    private int mImsType = 1;
    @VisibleForTesting
    boolean mInflateSignalStrengths = false;
    private int mLastLevel;
    private int mLastWlanCrossSimLevel;
    private int mLastWlanLevel;
    private int mLastWwanLevel;
    private final String[] mMobileStatusHistory = new String[64];
    private int mMobileStatusHistoryIndex;
    @VisibleForTesting
    MobileStatusTracker mMobileStatusTracker;
    private final String mNetworkNameDefault;
    private final String mNetworkNameSeparator;
    Map<String, SignalIcon$MobileIconGroup> mNetworkToIconLookup;
    private final ContentObserver mObserver;
    private final TelephonyManager mPhone;
    private final boolean mProviderModelBehavior;
    private final boolean mProviderModelSetting;
    private final Handler mReceiverHandler;
    private RegistrationManager.RegistrationCallback mRegistrationCallback;
    private ServiceState mServiceState;
    private SignalStrength mSignalStrength;
    final SubscriptionInfo mSubscriptionInfo;
    private TelephonyDisplayInfo mTelephonyDisplayInfo = new TelephonyDisplayInfo(0, 0);
    private final Runnable mTryRegisterIms = new Runnable() {
        /* class com.android.systemui.statusbar.policy.MobileSignalController.AnonymousClass4 */
        private int mRetryCount;

        public void run() {
            try {
                this.mRetryCount++;
                ImsMmTelManager imsMmTelManager = MobileSignalController.this.mImsMmTelManager;
                Handler handler = MobileSignalController.this.mReceiverHandler;
                Objects.requireNonNull(handler);
                imsMmTelManager.registerImsRegistrationCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), MobileSignalController.this.mRegistrationCallback);
                Log.d(MobileSignalController.this.mTag, "registerImsRegistrationCallback succeeded");
            } catch (ImsException | RuntimeException e) {
                if (this.mRetryCount < 12) {
                    Log.e(MobileSignalController.this.mTag, this.mRetryCount + " registerImsRegistrationCallback failed", e);
                    MobileSignalController.this.mReceiverHandler.postDelayed(MobileSignalController.this.mTryRegisterIms, 5000);
                }
            }
        }
    };

    public MobileSignalController(Context context, MobileMappings.Config config, boolean z, TelephonyManager telephonyManager, CallbackHandler callbackHandler, NetworkControllerImpl networkControllerImpl, SubscriptionInfo subscriptionInfo, MobileStatusTracker.SubscriptionDefaults subscriptionDefaults, Looper looper, CarrierConfigTracker carrierConfigTracker, FeatureFlags featureFlags) {
        super("MobileSignalController(" + subscriptionInfo.getSubscriptionId() + ")", context, 0, callbackHandler, networkControllerImpl);
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mConfig = config;
        this.mPhone = telephonyManager;
        this.mDefaults = subscriptionDefaults;
        this.mSubscriptionInfo = subscriptionInfo;
        this.mNetworkNameSeparator = getTextIfExists(R$string.status_bar_network_name_separator).toString();
        String charSequence = getTextIfExists(17040523).toString();
        this.mNetworkNameDefault = charSequence;
        this.mReceiverHandler = new Handler(looper);
        this.mNetworkToIconLookup = MobileMappings.mapIconSets(this.mConfig);
        this.mDefaultIcons = MobileMappings.getDefaultIcons(this.mConfig);
        charSequence = subscriptionInfo.getCarrierName() != null ? subscriptionInfo.getCarrierName().toString() : charSequence;
        T t = this.mLastState;
        T t2 = this.mCurrentState;
        ((SignalIcon$MobileState) t2).networkName = charSequence;
        ((SignalIcon$MobileState) t).networkName = charSequence;
        ((SignalIcon$MobileState) t2).networkNameData = charSequence;
        ((SignalIcon$MobileState) t).networkNameData = charSequence;
        ((SignalIcon$MobileState) t2).enabled = z;
        ((SignalIcon$MobileState) t).enabled = z;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = this.mDefaultIcons;
        ((SignalIcon$MobileState) t2).iconGroup = signalIcon$MobileIconGroup;
        ((SignalIcon$MobileState) t).iconGroup = signalIcon$MobileIconGroup;
        this.mObserver = new ContentObserver(new Handler(looper)) {
            /* class com.android.systemui.statusbar.policy.MobileSignalController.AnonymousClass1 */

            public void onChange(boolean z) {
                MobileSignalController.this.updateTelephony();
            }
        };
        this.mCallback = new MobileStatusTracker.Callback() {
            /* class com.android.systemui.statusbar.policy.MobileSignalController.AnonymousClass2 */
            private String mLastStatus;

            @Override // com.android.settingslib.mobile.MobileStatusTracker.Callback
            public void onMobileStatusChanged(boolean z, MobileStatusTracker.MobileStatus mobileStatus) {
                if (Log.isLoggable(MobileSignalController.this.mTag, 3)) {
                    String str = MobileSignalController.this.mTag;
                    Log.d(str, "onMobileStatusChanged= updateTelephony=" + z + " mobileStatus=" + mobileStatus.toString());
                }
                String mobileStatus2 = mobileStatus.toString();
                if (!mobileStatus2.equals(this.mLastStatus)) {
                    this.mLastStatus = mobileStatus2;
                    MobileSignalController.this.recordLastMobileStatus(MobileSignalController.SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + mobileStatus2);
                }
                MobileSignalController.this.updateMobileStatus(mobileStatus);
                if (z) {
                    MobileSignalController.this.updateTelephony();
                } else {
                    MobileSignalController.this.notifyListenersIfNecessary();
                }
            }
        };
        this.mRegistrationCallback = new RegistrationManager.RegistrationCallback() {
            /* class com.android.systemui.statusbar.policy.MobileSignalController.AnonymousClass3 */

            public void onRegistered(ImsRegistrationAttributes imsRegistrationAttributes) {
                String str = MobileSignalController.this.mTag;
                Log.d(str, "onRegistered: attributes=" + imsRegistrationAttributes);
                int transportType = imsRegistrationAttributes.getTransportType();
                int attributeFlags = imsRegistrationAttributes.getAttributeFlags();
                if (transportType == 1) {
                    MobileSignalController.this.mImsType = 1;
                    MobileSignalController mobileSignalController = MobileSignalController.this;
                    int callStrengthIcon = mobileSignalController.getCallStrengthIcon(mobileSignalController.mLastWwanLevel, false);
                    MobileSignalController mobileSignalController2 = MobileSignalController.this;
                    NetworkController.IconState iconState = new NetworkController.IconState(true, callStrengthIcon, mobileSignalController2.getCallStrengthDescription(mobileSignalController2.mLastWwanLevel, false));
                    MobileSignalController mobileSignalController3 = MobileSignalController.this;
                    mobileSignalController3.notifyCallStateChange(iconState, mobileSignalController3.mSubscriptionInfo.getSubscriptionId());
                } else if (transportType != 2) {
                } else {
                    if (attributeFlags == 0) {
                        MobileSignalController.this.mImsType = 2;
                        MobileSignalController mobileSignalController4 = MobileSignalController.this;
                        int callStrengthIcon2 = mobileSignalController4.getCallStrengthIcon(mobileSignalController4.mLastWlanLevel, true);
                        MobileSignalController mobileSignalController5 = MobileSignalController.this;
                        NetworkController.IconState iconState2 = new NetworkController.IconState(true, callStrengthIcon2, mobileSignalController5.getCallStrengthDescription(mobileSignalController5.mLastWlanLevel, true));
                        MobileSignalController mobileSignalController6 = MobileSignalController.this;
                        mobileSignalController6.notifyCallStateChange(iconState2, mobileSignalController6.mSubscriptionInfo.getSubscriptionId());
                    } else if (attributeFlags == 1) {
                        MobileSignalController.this.mImsType = 3;
                        MobileSignalController mobileSignalController7 = MobileSignalController.this;
                        int callStrengthIcon3 = mobileSignalController7.getCallStrengthIcon(mobileSignalController7.mLastWlanCrossSimLevel, false);
                        MobileSignalController mobileSignalController8 = MobileSignalController.this;
                        NetworkController.IconState iconState3 = new NetworkController.IconState(true, callStrengthIcon3, mobileSignalController8.getCallStrengthDescription(mobileSignalController8.mLastWlanCrossSimLevel, false));
                        MobileSignalController mobileSignalController9 = MobileSignalController.this;
                        mobileSignalController9.notifyCallStateChange(iconState3, mobileSignalController9.mSubscriptionInfo.getSubscriptionId());
                    }
                }
            }

            public void onUnregistered(ImsReasonInfo imsReasonInfo) {
                String str = MobileSignalController.this.mTag;
                Log.d(str, "onDeregistered: info=" + imsReasonInfo);
                MobileSignalController.this.mImsType = 1;
                MobileSignalController mobileSignalController = MobileSignalController.this;
                int callStrengthIcon = mobileSignalController.getCallStrengthIcon(mobileSignalController.mLastWwanLevel, false);
                MobileSignalController mobileSignalController2 = MobileSignalController.this;
                NetworkController.IconState iconState = new NetworkController.IconState(true, callStrengthIcon, mobileSignalController2.getCallStrengthDescription(mobileSignalController2.mLastWwanLevel, false));
                MobileSignalController mobileSignalController3 = MobileSignalController.this;
                mobileSignalController3.notifyCallStateChange(iconState, mobileSignalController3.mSubscriptionInfo.getSubscriptionId());
            }
        };
        this.mImsMmTelManager = ImsMmTelManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId());
        this.mMobileStatusTracker = new MobileStatusTracker(telephonyManager, looper, subscriptionInfo, subscriptionDefaults, this.mCallback);
        this.mProviderModelBehavior = featureFlags.isCombinedStatusBarSignalIconsEnabled();
        this.mProviderModelSetting = featureFlags.isProviderModelSettingEnabled();
    }

    public void setConfiguration(MobileMappings.Config config) {
        this.mConfig = config;
        updateInflateSignalStrength();
        this.mNetworkToIconLookup = MobileMappings.mapIconSets(this.mConfig);
        this.mDefaultIcons = MobileMappings.getDefaultIcons(this.mConfig);
        updateTelephony();
    }

    public void setAirplaneMode(boolean z) {
        ((SignalIcon$MobileState) this.mCurrentState).airplaneMode = z;
        notifyListenersIfNecessary();
    }

    public void setUserSetupComplete(boolean z) {
        ((SignalIcon$MobileState) this.mCurrentState).userSetup = z;
        notifyListenersIfNecessary();
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public void updateConnectivity(BitSet bitSet, BitSet bitSet2) {
        boolean z = bitSet2.get(this.mTransportType);
        ((SignalIcon$MobileState) this.mCurrentState).isDefault = bitSet.get(this.mTransportType);
        T t = this.mCurrentState;
        ((SignalIcon$MobileState) t).inetCondition = (z || !((SignalIcon$MobileState) t).isDefault) ? 1 : 0;
        notifyListenersIfNecessary();
    }

    public void setCarrierNetworkChangeMode(boolean z) {
        ((SignalIcon$MobileState) this.mCurrentState).carrierNetworkChangeMode = z;
        updateTelephony();
    }

    public void registerListener() {
        this.mMobileStatusTracker.setListening(true);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("mobile_data"), true, this.mObserver);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("mobile_data" + this.mSubscriptionInfo.getSubscriptionId()), true, this.mObserver);
        if (this.mProviderModelBehavior) {
            this.mReceiverHandler.post(this.mTryRegisterIms);
        }
    }

    public void unregisterListener() {
        this.mMobileStatusTracker.setListening(false);
        this.mContext.getContentResolver().unregisterContentObserver(this.mObserver);
        this.mImsMmTelManager.unregisterImsRegistrationCallback(this.mRegistrationCallback);
    }

    private void updateInflateSignalStrength() {
        this.mInflateSignalStrengths = SignalStrengthUtil.shouldInflateSignalStrength(this.mContext, this.mSubscriptionInfo.getSubscriptionId());
    }

    private int getNumLevels() {
        if (this.mInflateSignalStrengths) {
            return CellSignalStrength.getNumSignalStrengthLevels() + 1;
        }
        return CellSignalStrength.getNumSignalStrengthLevels();
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public int getCurrentIconId() {
        T t = this.mCurrentState;
        if (((SignalIcon$MobileState) t).iconGroup == TelephonyIcons.CARRIER_NETWORK_CHANGE) {
            return SignalDrawable.getCarrierChangeState(getNumLevels());
        }
        boolean z = false;
        if (((SignalIcon$MobileState) t).connected) {
            int i = ((SignalIcon$MobileState) t).level;
            if (this.mInflateSignalStrengths) {
                i++;
            }
            boolean z2 = ((SignalIcon$MobileState) t).userSetup && (((SignalIcon$MobileState) t).iconGroup == TelephonyIcons.DATA_DISABLED || (((SignalIcon$MobileState) t).iconGroup == TelephonyIcons.NOT_DEFAULT_DATA && ((SignalIcon$MobileState) t).defaultDataOff));
            boolean z3 = ((SignalIcon$MobileState) t).inetCondition == 0;
            if (z2 || z3) {
                z = true;
            }
            return SignalDrawable.getState(i, getNumLevels(), z);
        } else if (((SignalIcon$MobileState) t).enabled) {
            return SignalDrawable.getEmptyState(getNumLevels());
        } else {
            return 0;
        }
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public int getQsCurrentIconId() {
        return getCurrentIconId();
    }

    /* JADX WARNING: Removed duplicated region for block: B:132:0x0214  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x022c  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x0244 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x024d  */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0260  */
    @Override // com.android.systemui.statusbar.policy.SignalController
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyListeners(com.android.systemui.statusbar.policy.NetworkController.SignalCallback r20) {
        /*
        // Method dump skipped, instructions count: 650
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.MobileSignalController.notifyListeners(com.android.systemui.statusbar.policy.NetworkController$SignalCallback):void");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.statusbar.policy.SignalController
    public SignalIcon$MobileState cleanState() {
        return new SignalIcon$MobileState();
    }

    private boolean isCdma() {
        SignalStrength signalStrength = this.mSignalStrength;
        return signalStrength != null && !signalStrength.isGsm();
    }

    public boolean isEmergencyOnly() {
        ServiceState serviceState = this.mServiceState;
        return serviceState != null && serviceState.isEmergencyOnly();
    }

    public boolean isInService() {
        return Utils.isInService(this.mServiceState);
    }

    /* access modifiers changed from: package-private */
    public String getNetworkNameForCarrierWiFi() {
        return this.mPhone.getSimOperatorName();
    }

    private boolean isRoaming() {
        if (isCarrierNetworkChangeActive()) {
            return false;
        }
        if (!isCdma()) {
            ServiceState serviceState = this.mServiceState;
            if (serviceState == null || !serviceState.getRoaming()) {
                return false;
            }
            return true;
        } else if (this.mPhone.getCdmaEnhancedRoamingIndicatorDisplayNumber() != 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isCarrierNetworkChangeActive() {
        return ((SignalIcon$MobileState) this.mCurrentState).carrierNetworkChangeMode;
    }

    public void handleBroadcast(Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.telephony.action.SERVICE_PROVIDERS_UPDATED")) {
            updateNetworkName(intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false), intent.getStringExtra("android.telephony.extra.SPN"), intent.getStringExtra("android.telephony.extra.DATA_SPN"), intent.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false), intent.getStringExtra("android.telephony.extra.PLMN"));
            notifyListenersIfNecessary();
        } else if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
            updateDataSim();
            notifyListenersIfNecessary();
        }
    }

    private void updateDataSim() {
        int activeDataSubId = this.mDefaults.getActiveDataSubId();
        boolean z = true;
        if (SubscriptionManager.isValidSubscriptionId(activeDataSubId)) {
            SignalIcon$MobileState signalIcon$MobileState = (SignalIcon$MobileState) this.mCurrentState;
            if (activeDataSubId != this.mSubscriptionInfo.getSubscriptionId()) {
                z = false;
            }
            signalIcon$MobileState.dataSim = z;
            return;
        }
        ((SignalIcon$MobileState) this.mCurrentState).dataSim = true;
    }

    /* access modifiers changed from: package-private */
    public void updateNetworkName(boolean z, String str, String str2, boolean z2, String str3) {
        if (SignalController.CHATTY) {
            Log.d("CarrierLabel", "updateNetworkName showSpn=" + z + " spn=" + str + " dataSpn=" + str2 + " showPlmn=" + z2 + " plmn=" + str3);
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        if (z2 && str3 != null) {
            sb.append(str3);
            sb2.append(str3);
        }
        if (z && str != null) {
            if (sb.length() != 0) {
                sb.append(this.mNetworkNameSeparator);
            }
            sb.append(str);
        }
        if (sb.length() != 0) {
            ((SignalIcon$MobileState) this.mCurrentState).networkName = sb.toString();
        } else {
            ((SignalIcon$MobileState) this.mCurrentState).networkName = this.mNetworkNameDefault;
        }
        if (z && str2 != null) {
            if (sb2.length() != 0) {
                sb2.append(this.mNetworkNameSeparator);
            }
            sb2.append(str2);
        }
        if (sb2.length() != 0) {
            ((SignalIcon$MobileState) this.mCurrentState).networkNameData = sb2.toString();
            return;
        }
        ((SignalIcon$MobileState) this.mCurrentState).networkNameData = this.mNetworkNameDefault;
    }

    private int getCdmaLevel(SignalStrength signalStrength) {
        List cellSignalStrengths = signalStrength.getCellSignalStrengths(CellSignalStrengthCdma.class);
        if (!cellSignalStrengths.isEmpty()) {
            return ((CellSignalStrengthCdma) cellSignalStrengths.get(0)).getLevel();
        }
        return 0;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateMobileStatus(MobileStatusTracker.MobileStatus mobileStatus) {
        T t = this.mCurrentState;
        ((SignalIcon$MobileState) t).activityIn = mobileStatus.activityIn;
        ((SignalIcon$MobileState) t).activityOut = mobileStatus.activityOut;
        ((SignalIcon$MobileState) t).dataSim = mobileStatus.dataSim;
        ((SignalIcon$MobileState) t).carrierNetworkChangeMode = mobileStatus.carrierNetworkChangeMode;
        this.mDataState = mobileStatus.dataState;
        notifyMobileLevelChangeIfNecessary(mobileStatus.signalStrength);
        this.mSignalStrength = mobileStatus.signalStrength;
        this.mTelephonyDisplayInfo = mobileStatus.telephonyDisplayInfo;
        ServiceState serviceState = this.mServiceState;
        int state = serviceState != null ? serviceState.getState() : -1;
        ServiceState serviceState2 = mobileStatus.serviceState;
        this.mServiceState = serviceState2;
        int state2 = serviceState2 != null ? serviceState2.getState() : -1;
        if (this.mProviderModelBehavior && state != state2) {
            if (state == -1 || state == 0 || state2 == 0) {
                notifyCallStateChange(new NetworkController.IconState((state2 != 0) & (true ^ hideNoCalling()), R$drawable.ic_qs_no_calling_sms, getTextIfExists(AccessibilityContentDescriptions.NO_CALLING).toString()), this.mSubscriptionInfo.getSubscriptionId());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateNoCallingState() {
        ServiceState serviceState = this.mServiceState;
        notifyCallStateChange(new NetworkController.IconState(((serviceState != null ? serviceState.getState() : -1) != 0) & (true ^ hideNoCalling()), R$drawable.ic_qs_no_calling_sms, getTextIfExists(AccessibilityContentDescriptions.NO_CALLING).toString()), this.mSubscriptionInfo.getSubscriptionId());
    }

    private boolean hideNoCalling() {
        return this.mNetworkController.hasDefaultNetwork() && this.mCarrierConfigTracker.getNoCallingConfig(this.mSubscriptionInfo.getSubscriptionId());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getCallStrengthIcon(int i, boolean z) {
        if (z) {
            return TelephonyIcons.WIFI_CALL_STRENGTH_ICONS[i];
        }
        return TelephonyIcons.MOBILE_CALL_STRENGTH_ICONS[i];
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getCallStrengthDescription(int i, boolean z) {
        if (z) {
            return getTextIfExists(AccessibilityContentDescriptions.WIFI_CONNECTION_STRENGTH[i]).toString();
        }
        return getTextIfExists(AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH[i]).toString();
    }

    /* access modifiers changed from: package-private */
    public void refreshCallIndicator(NetworkController.SignalCallback signalCallback) {
        ServiceState serviceState = this.mServiceState;
        NetworkController.IconState iconState = new NetworkController.IconState(((serviceState == null || serviceState.getState() == 0) ? false : true) & (!hideNoCalling()), R$drawable.ic_qs_no_calling_sms, getTextIfExists(AccessibilityContentDescriptions.NO_CALLING).toString());
        signalCallback.setCallIndicator(iconState, this.mSubscriptionInfo.getSubscriptionId());
        int i = this.mImsType;
        if (i == 1) {
            iconState = new NetworkController.IconState(true, getCallStrengthIcon(this.mLastWwanLevel, false), getCallStrengthDescription(this.mLastWwanLevel, false));
        } else if (i == 2) {
            iconState = new NetworkController.IconState(true, getCallStrengthIcon(this.mLastWlanLevel, true), getCallStrengthDescription(this.mLastWlanLevel, true));
        } else if (i == 3) {
            iconState = new NetworkController.IconState(true, getCallStrengthIcon(this.mLastWlanCrossSimLevel, false), getCallStrengthDescription(this.mLastWlanCrossSimLevel, false));
        }
        signalCallback.setCallIndicator(iconState, this.mSubscriptionInfo.getSubscriptionId());
    }

    /* access modifiers changed from: package-private */
    public void notifyWifiLevelChange(int i) {
        if (this.mProviderModelBehavior) {
            this.mLastWlanLevel = i;
            if (this.mImsType == 2) {
                notifyCallStateChange(new NetworkController.IconState(true, getCallStrengthIcon(i, true), getCallStrengthDescription(i, true)), this.mSubscriptionInfo.getSubscriptionId());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyDefaultMobileLevelChange(int i) {
        if (this.mProviderModelBehavior) {
            this.mLastWlanCrossSimLevel = i;
            if (this.mImsType == 3) {
                notifyCallStateChange(new NetworkController.IconState(true, getCallStrengthIcon(i, false), getCallStrengthDescription(i, false)), this.mSubscriptionInfo.getSubscriptionId());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyMobileLevelChangeIfNecessary(SignalStrength signalStrength) {
        int signalLevel;
        if (this.mProviderModelBehavior && (signalLevel = getSignalLevel(signalStrength)) != this.mLastLevel) {
            this.mLastLevel = signalLevel;
            this.mLastWwanLevel = signalLevel;
            if (this.mImsType == 1) {
                notifyCallStateChange(new NetworkController.IconState(true, getCallStrengthIcon(signalLevel, false), getCallStrengthDescription(signalLevel, false)), this.mSubscriptionInfo.getSubscriptionId());
            }
            if (((SignalIcon$MobileState) this.mCurrentState).dataSim) {
                this.mNetworkController.notifyDefaultMobileLevelChange(signalLevel);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getSignalLevel(SignalStrength signalStrength) {
        if (signalStrength == null) {
            return 0;
        }
        if (signalStrength.isGsm() || !this.mConfig.alwaysShowCdmaRssi) {
            return signalStrength.getLevel();
        }
        return getCdmaLevel(signalStrength);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void updateTelephony() {
        ServiceState serviceState;
        ServiceState serviceState2;
        if (Log.isLoggable(this.mTag, 3)) {
            Log.d(this.mTag, "updateTelephonySignalStrength: hasService=" + Utils.isInService(this.mServiceState) + " ss=" + this.mSignalStrength + " displayInfo=" + this.mTelephonyDisplayInfo);
        }
        checkDefaultData();
        boolean z = true;
        ((SignalIcon$MobileState) this.mCurrentState).connected = Utils.isInService(this.mServiceState) && this.mSignalStrength != null;
        T t = this.mCurrentState;
        if (((SignalIcon$MobileState) t).connected) {
            ((SignalIcon$MobileState) t).level = getSignalLevel(this.mSignalStrength);
        }
        String iconKey = MobileMappings.getIconKey(this.mTelephonyDisplayInfo);
        if (this.mNetworkToIconLookup.get(iconKey) != null) {
            ((SignalIcon$MobileState) this.mCurrentState).iconGroup = this.mNetworkToIconLookup.get(iconKey);
        } else {
            ((SignalIcon$MobileState) this.mCurrentState).iconGroup = this.mDefaultIcons;
        }
        T t2 = this.mCurrentState;
        SignalIcon$MobileState signalIcon$MobileState = (SignalIcon$MobileState) t2;
        if (!((SignalIcon$MobileState) t2).connected || this.mDataState != 2) {
            z = false;
        }
        signalIcon$MobileState.dataConnected = z;
        ((SignalIcon$MobileState) t2).roaming = isRoaming();
        if (isCarrierNetworkChangeActive()) {
            ((SignalIcon$MobileState) this.mCurrentState).iconGroup = TelephonyIcons.CARRIER_NETWORK_CHANGE;
        } else if (isDataDisabled() && !this.mConfig.alwaysShowDataRatIcon) {
            if (this.mSubscriptionInfo.getSubscriptionId() != this.mDefaults.getDefaultDataSubId()) {
                ((SignalIcon$MobileState) this.mCurrentState).iconGroup = TelephonyIcons.NOT_DEFAULT_DATA;
            } else {
                ((SignalIcon$MobileState) this.mCurrentState).iconGroup = TelephonyIcons.DATA_DISABLED;
            }
        }
        boolean isEmergencyOnly = isEmergencyOnly();
        T t3 = this.mCurrentState;
        if (isEmergencyOnly != ((SignalIcon$MobileState) t3).isEmergency) {
            ((SignalIcon$MobileState) t3).isEmergency = isEmergencyOnly();
            this.mNetworkController.recalculateEmergency();
        }
        if (((SignalIcon$MobileState) this.mCurrentState).networkName.equals(this.mNetworkNameDefault) && (serviceState2 = this.mServiceState) != null && !TextUtils.isEmpty(serviceState2.getOperatorAlphaShort())) {
            ((SignalIcon$MobileState) this.mCurrentState).networkName = this.mServiceState.getOperatorAlphaShort();
        }
        if (((SignalIcon$MobileState) this.mCurrentState).networkNameData.equals(this.mNetworkNameDefault) && (serviceState = this.mServiceState) != null && ((SignalIcon$MobileState) this.mCurrentState).dataSim && !TextUtils.isEmpty(serviceState.getOperatorAlphaShort())) {
            ((SignalIcon$MobileState) this.mCurrentState).networkNameData = this.mServiceState.getOperatorAlphaShort();
        }
        notifyListenersIfNecessary();
    }

    private void checkDefaultData() {
        T t = this.mCurrentState;
        if (((SignalIcon$MobileState) t).iconGroup != TelephonyIcons.NOT_DEFAULT_DATA) {
            ((SignalIcon$MobileState) t).defaultDataOff = false;
            return;
        }
        ((SignalIcon$MobileState) t).defaultDataOff = this.mNetworkController.isDataControllerDisabled();
    }

    /* access modifiers changed from: package-private */
    public void onMobileDataChanged() {
        checkDefaultData();
        notifyListenersIfNecessary();
    }

    /* access modifiers changed from: package-private */
    public boolean isDataDisabled() {
        return !this.mPhone.isDataConnectionAllowed();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setActivity(int i) {
        T t = this.mCurrentState;
        boolean z = false;
        ((SignalIcon$MobileState) t).activityIn = i == 3 || i == 1;
        SignalIcon$MobileState signalIcon$MobileState = (SignalIcon$MobileState) t;
        if (i == 3 || i == 2) {
            z = true;
        }
        signalIcon$MobileState.activityOut = z;
        notifyListenersIfNecessary();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void recordLastMobileStatus(String str) {
        String[] strArr = this.mMobileStatusHistory;
        int i = this.mMobileStatusHistoryIndex;
        strArr[i] = str;
        this.mMobileStatusHistoryIndex = (i + 1) % 64;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setImsType(int i) {
        this.mImsType = i;
    }

    @Override // com.android.systemui.statusbar.policy.SignalController
    public void dump(PrintWriter printWriter) {
        super.dump(printWriter);
        printWriter.println("  mSubscription=" + this.mSubscriptionInfo + ",");
        printWriter.println("  mServiceState=" + this.mServiceState + ",");
        printWriter.println("  mSignalStrength=" + this.mSignalStrength + ",");
        printWriter.println("  mTelephonyDisplayInfo=" + this.mTelephonyDisplayInfo + ",");
        printWriter.println("  mDataState=" + this.mDataState + ",");
        printWriter.println("  mInflateSignalStrengths=" + this.mInflateSignalStrengths + ",");
        printWriter.println("  isDataDisabled=" + isDataDisabled() + ",");
        printWriter.println("  MobileStatusHistory");
        int i = 0;
        for (int i2 = 0; i2 < 64; i2++) {
            if (this.mMobileStatusHistory[i2] != null) {
                i++;
            }
        }
        int i3 = this.mMobileStatusHistoryIndex + 64;
        while (true) {
            i3--;
            if (i3 >= (this.mMobileStatusHistoryIndex + 64) - i) {
                printWriter.println("  Previous MobileStatus(" + ((this.mMobileStatusHistoryIndex + 64) - i3) + "): " + this.mMobileStatusHistory[i3 & 63]);
            } else {
                return;
            }
        }
    }
}

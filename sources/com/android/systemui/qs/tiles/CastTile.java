package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.internal.app.MediaRouteDialogPresenter;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.NetworkController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class CastTile extends QSTileImpl<QSTile.BooleanState> {
    private static final Intent CAST_SETTINGS = new Intent("android.settings.CAST_SETTINGS");
    private final Callback mCallback;
    private final CastController mController;
    private final CastDetailAdapter mDetailAdapter = new CastDetailAdapter();
    private Dialog mDialog;
    private final HotspotController.Callback mHotspotCallback;
    private boolean mHotspotConnected;
    private final KeyguardStateController mKeyguard;
    private final NetworkController mNetworkController;
    private final NetworkController.SignalCallback mSignalCallback;
    private boolean mWifiConnected;

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.qs.tileimpl.QSTileImpl
    public int getMetricsCategory() {
        return 114;
    }

    public CastTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CastController castController, KeyguardStateController keyguardStateController, NetworkController networkController, HotspotController hotspotController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback();
        this.mCallback = callback;
        AnonymousClass1 r3 = new NetworkController.SignalCallback() {
            /* class com.android.systemui.qs.tiles.CastTile.AnonymousClass1 */

            @Override // com.android.systemui.statusbar.policy.NetworkController.SignalCallback
            public void setWifiIndicators(NetworkController.WifiIndicators wifiIndicators) {
                NetworkController.IconState iconState;
                boolean z = wifiIndicators.enabled && (iconState = wifiIndicators.qsIcon) != null && iconState.visible;
                if (z != CastTile.this.mWifiConnected) {
                    CastTile.this.mWifiConnected = z;
                    if (!CastTile.this.mHotspotConnected) {
                        CastTile.this.refreshState();
                    }
                }
            }
        };
        this.mSignalCallback = r3;
        AnonymousClass2 r4 = new HotspotController.Callback() {
            /* class com.android.systemui.qs.tiles.CastTile.AnonymousClass2 */

            @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
            public void onHotspotChanged(boolean z, int i) {
                boolean z2 = z && i > 0;
                if (z2 != CastTile.this.mHotspotConnected) {
                    CastTile.this.mHotspotConnected = z2;
                    if (!CastTile.this.mWifiConnected) {
                        CastTile.this.refreshState();
                    }
                }
            }
        };
        this.mHotspotCallback = r4;
        this.mController = castController;
        this.mKeyguard = keyguardStateController;
        this.mNetworkController = networkController;
        castController.observe(this, callback);
        keyguardStateController.observe(this, callback);
        networkController.observe(this, r3);
        hotspotController.observe(this, r4);
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.qs.tileimpl.QSTileImpl
    public DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (QSTileImpl.DEBUG) {
            String str = this.TAG;
            Log.d(str, "handleSetListening " + z);
        }
        if (!z) {
            this.mController.setDiscovering(false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
        super.handleUserSwitch(i);
        this.mController.setCurrentUserId(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.CAST_SETTINGS");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleLongClick(View view) {
        handleClick(view);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (((QSTile.BooleanState) getState()).state != 0) {
            List<CastController.CastDevice> activeDevices = getActiveDevices();
            if (willPopDetail()) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new CastTile$$ExternalSyntheticLambda1(this));
            } else {
                this.mController.stopCasting(activeDevices.get(0));
            }
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$handleClick$0() {
        showDetail(true);
    }

    private boolean willPopDetail() {
        List<CastController.CastDevice> activeDevices = getActiveDevices();
        return activeDevices.isEmpty() || (activeDevices.get(0).tag instanceof MediaRouter.RouteInfo);
    }

    private List<CastController.CastDevice> getActiveDevices() {
        ArrayList arrayList = new ArrayList();
        for (CastController.CastDevice castDevice : this.mController.getCastDevices()) {
            int i = castDevice.state;
            if (i == 2 || i == 1) {
                arrayList.add(castDevice);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void showDetail(boolean z) {
        this.mUiHandler.post(new CastTile$$ExternalSyntheticLambda2(this));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$showDetail$3() {
        Dialog createDialog = MediaRouteDialogPresenter.createDialog(this.mContext, 4, new CastTile$$ExternalSyntheticLambda0(this));
        this.mDialog = createDialog;
        createDialog.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(this.mDialog, true);
        SystemUIDialog.registerDismissListener(this.mDialog);
        SystemUIDialog.setWindowOnTop(this.mDialog);
        this.mUiHandler.post(new CastTile$$ExternalSyntheticLambda3(this));
        this.mHost.collapsePanels();
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$showDetail$1(View view) {
        this.mDialog.dismiss();
        this.mActivityStarter.postStartActivityDismissingKeyguard(getLongClickIntent(), 0);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$showDetail$2() {
        this.mDialog.show();
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.qs.tileimpl.QSTileImpl
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_cast_title);
    }

    /* access modifiers changed from: protected */
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        int i;
        int i2;
        String string = this.mContext.getString(R$string.quick_settings_cast_title);
        booleanState.label = string;
        booleanState.contentDescription = string;
        booleanState.stateDescription = "";
        booleanState.value = false;
        List<CastController.CastDevice> castDevices = this.mController.getCastDevices();
        Iterator<CastController.CastDevice> it = castDevices.iterator();
        boolean z = false;
        while (true) {
            i = 2;
            if (!it.hasNext()) {
                break;
            }
            CastController.CastDevice next = it.next();
            int i3 = next.state;
            if (i3 == 2) {
                booleanState.value = true;
                booleanState.secondaryLabel = getDeviceName(next);
                booleanState.stateDescription = ((Object) booleanState.stateDescription) + "," + this.mContext.getString(R$string.accessibility_cast_name, booleanState.label);
                z = false;
                break;
            } else if (i3 == 1) {
                z = true;
            }
        }
        if (z && !booleanState.value) {
            booleanState.secondaryLabel = this.mContext.getString(R$string.quick_settings_connecting);
        }
        if (booleanState.value) {
            i2 = R$drawable.ic_cast_connected;
        } else {
            i2 = R$drawable.ic_cast;
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(i2);
        if (canCastToWifi() || booleanState.value) {
            boolean z2 = booleanState.value;
            if (!z2) {
                i = 1;
            }
            booleanState.state = i;
            if (!z2) {
                booleanState.secondaryLabel = "";
            }
            booleanState.contentDescription = ((Object) booleanState.contentDescription) + "," + this.mContext.getString(R$string.accessibility_quick_settings_open_details);
            booleanState.expandedAccessibilityClassName = Button.class.getName();
            booleanState.forceExpandIcon = willPopDetail();
        } else {
            booleanState.state = 0;
            booleanState.secondaryLabel = this.mContext.getString(R$string.quick_settings_cast_no_wifi);
            booleanState.forceExpandIcon = false;
        }
        booleanState.stateDescription = ((Object) booleanState.stateDescription) + ", " + ((Object) booleanState.secondaryLabel);
        this.mDetailAdapter.updateItems(castDevices);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public String composeChangeAnnouncement() {
        if (!((QSTile.BooleanState) this.mState).value) {
            return this.mContext.getString(R$string.accessibility_casting_turned_off);
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getDeviceName(CastController.CastDevice castDevice) {
        String str = castDevice.name;
        return str != null ? str : this.mContext.getString(R$string.quick_settings_cast_device_default_name);
    }

    private boolean canCastToWifi() {
        return this.mWifiConnected || this.mHotspotConnected;
    }

    private final class Callback implements CastController.Callback, KeyguardStateController.Callback {
        private Callback() {
        }

        @Override // com.android.systemui.statusbar.policy.CastController.Callback
        public void onCastDevicesChanged() {
            CastTile.this.refreshState();
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public void onKeyguardShowingChanged() {
            CastTile.this.refreshState();
        }
    }

    /* access modifiers changed from: private */
    public final class CastDetailAdapter implements DetailAdapter, QSDetailItems.Callback {
        private QSDetailItems mItems;
        private final LinkedHashMap<String, CastController.CastDevice> mVisibleOrder;

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public int getMetricsCategory() {
            return 151;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Boolean getToggleState() {
            return null;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public void setToggleState(boolean z) {
        }

        private CastDetailAdapter() {
            this.mVisibleOrder = new LinkedHashMap<>();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public CharSequence getTitle() {
            return ((QSTileImpl) CastTile.this).mContext.getString(R$string.quick_settings_cast_title);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public Intent getSettingsIntent() {
            return CastTile.CAST_SETTINGS;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public View createDetailView(Context context, View view, ViewGroup viewGroup) {
            QSDetailItems convertOrInflate = QSDetailItems.convertOrInflate(context, view, viewGroup);
            this.mItems = convertOrInflate;
            convertOrInflate.setTagSuffix("Cast");
            if (view == null) {
                if (QSTileImpl.DEBUG) {
                    Log.d(((QSTileImpl) CastTile.this).TAG, "addOnAttachStateChangeListener");
                }
                this.mItems.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    /* class com.android.systemui.qs.tiles.CastTile.CastDetailAdapter.AnonymousClass1 */

                    public void onViewAttachedToWindow(View view) {
                        if (QSTileImpl.DEBUG) {
                            Log.d(((QSTileImpl) CastTile.this).TAG, "onViewAttachedToWindow");
                        }
                    }

                    public void onViewDetachedFromWindow(View view) {
                        if (QSTileImpl.DEBUG) {
                            Log.d(((QSTileImpl) CastTile.this).TAG, "onViewDetachedFromWindow");
                        }
                        CastDetailAdapter.this.mVisibleOrder.clear();
                    }
                });
            }
            this.mItems.setEmptyState(R$drawable.ic_qs_cast_detail_empty, R$string.quick_settings_cast_detail_empty_text);
            this.mItems.setCallback(this);
            updateItems(CastTile.this.mController.getCastDevices());
            CastTile.this.mController.setDiscovering(true);
            return this.mItems;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void updateItems(List<CastController.CastDevice> list) {
            int i;
            if (this.mItems != null) {
                QSDetailItems.Item[] itemArr = null;
                if (list != null && !list.isEmpty()) {
                    Iterator<CastController.CastDevice> it = list.iterator();
                    while (true) {
                        i = 0;
                        if (!it.hasNext()) {
                            break;
                        }
                        CastController.CastDevice next = it.next();
                        if (next.state == 2) {
                            QSDetailItems.Item item = new QSDetailItems.Item();
                            item.iconResId = R$drawable.ic_cast_connected;
                            item.line1 = CastTile.this.getDeviceName(next);
                            item.line2 = ((QSTileImpl) CastTile.this).mContext.getString(R$string.quick_settings_connected);
                            item.tag = next;
                            item.canDisconnect = true;
                            itemArr = new QSDetailItems.Item[]{item};
                            break;
                        }
                    }
                    if (itemArr == null) {
                        for (CastController.CastDevice castDevice : list) {
                            this.mVisibleOrder.put(castDevice.id, castDevice);
                        }
                        itemArr = new QSDetailItems.Item[list.size()];
                        for (String str : this.mVisibleOrder.keySet()) {
                            CastController.CastDevice castDevice2 = this.mVisibleOrder.get(str);
                            if (list.contains(castDevice2)) {
                                QSDetailItems.Item item2 = new QSDetailItems.Item();
                                item2.iconResId = R$drawable.ic_cast;
                                item2.line1 = CastTile.this.getDeviceName(castDevice2);
                                if (castDevice2.state == 1) {
                                    item2.line2 = ((QSTileImpl) CastTile.this).mContext.getString(R$string.quick_settings_connecting);
                                }
                                item2.tag = castDevice2;
                                itemArr[i] = item2;
                                i++;
                            }
                        }
                    }
                }
                this.mItems.setItems(itemArr);
            }
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public void onDetailItemClick(QSDetailItems.Item item) {
            if (item != null && item.tag != null) {
                MetricsLogger.action(((QSTileImpl) CastTile.this).mContext, 157);
                CastTile.this.mController.startCasting((CastController.CastDevice) item.tag);
            }
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public void onDetailItemDisconnect(QSDetailItems.Item item) {
            if (item != null && item.tag != null) {
                MetricsLogger.action(((QSTileImpl) CastTile.this).mContext, 158);
                CastTile.this.mController.stopCasting((CastController.CastDevice) item.tag);
            }
        }
    }
}

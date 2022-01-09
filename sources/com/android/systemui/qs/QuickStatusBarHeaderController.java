package com.android.systemui.qs;

import android.os.Bundle;
import android.view.View;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$id;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.qs.carrier.QSCarrierGroup;
import com.android.systemui.qs.carrier.QSCarrierGroupController;
import com.android.systemui.statusbar.FeatureFlags;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.VariableDateView;
import com.android.systemui.statusbar.policy.VariableDateViewController;
import com.android.systemui.util.ViewController;
import java.util.List;
import java.util.Objects;

/* access modifiers changed from: package-private */
public class QuickStatusBarHeaderController extends ViewController<QuickStatusBarHeader> {
    private final ActivityStarter mActivityStarter;
    private final String mCameraSlot;
    private final Clock mClockView;
    private SysuiColorExtractor mColorExtractor;
    private final DemoModeController mDemoModeController;
    private final DemoMode mDemoModeReceiver;
    private final FeatureFlags mFeatureFlags;
    private final QuickQSPanelController mHeaderQsPanelController;
    private final StatusIconContainer mIconContainer;
    private final StatusBarIconController.TintedIconManager mIconManager;
    private boolean mListening;
    private boolean mLocationIndicatorsEnabled;
    private final String mLocationSlot;
    private boolean mMicCameraIndicatorsEnabled;
    private final String mMicSlot;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        /* class com.android.systemui.qs.QuickStatusBarHeaderController.AnonymousClass2 */

        public void onClick(View view) {
            if (view == QuickStatusBarHeaderController.this.mPrivacyChip) {
                QuickStatusBarHeaderController.this.mUiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_CLICK);
                QuickStatusBarHeaderController.this.mPrivacyDialogController.showDialog(QuickStatusBarHeaderController.this.getContext());
            }
        }
    };
    private ColorExtractor.OnColorsChangedListener mOnColorsChangedListener;
    private PrivacyItemController.Callback mPICCallback = new PrivacyItemController.Callback() {
        /* class com.android.systemui.qs.QuickStatusBarHeaderController.AnonymousClass1 */

        @Override // com.android.systemui.privacy.PrivacyItemController.Callback
        public void onPrivacyItemsChanged(List<PrivacyItem> list) {
            QuickStatusBarHeaderController.this.mPrivacyChip.setPrivacyList(list);
            QuickStatusBarHeaderController.this.setChipVisibility(!list.isEmpty());
        }

        @Override // com.android.systemui.privacy.PrivacyItemController.Callback
        public void onFlagMicCameraChanged(boolean z) {
            if (QuickStatusBarHeaderController.this.mMicCameraIndicatorsEnabled != z) {
                QuickStatusBarHeaderController.this.mMicCameraIndicatorsEnabled = z;
                update();
            }
        }

        @Override // com.android.systemui.privacy.PrivacyItemController.Callback
        public void onFlagLocationChanged(boolean z) {
            if (QuickStatusBarHeaderController.this.mLocationIndicatorsEnabled != z) {
                QuickStatusBarHeaderController.this.mLocationIndicatorsEnabled = z;
                update();
            }
        }

        private void update() {
            QuickStatusBarHeaderController.this.updatePrivacyIconSlots();
            QuickStatusBarHeaderController quickStatusBarHeaderController = QuickStatusBarHeaderController.this;
            quickStatusBarHeaderController.setChipVisibility(!quickStatusBarHeaderController.mPrivacyChip.getPrivacyList().isEmpty());
        }
    };
    private final OngoingPrivacyChip mPrivacyChip;
    private boolean mPrivacyChipLogged;
    private final PrivacyDialogController mPrivacyDialogController;
    private final PrivacyItemController mPrivacyItemController;
    private final PrivacyLogger mPrivacyLogger;
    private final QSCarrierGroupController mQSCarrierGroupController;
    private final QSExpansionPathInterpolator mQSExpansionPathInterpolator;
    private final StatusBarIconController mStatusBarIconController;
    private final UiEventLogger mUiEventLogger;
    private final VariableDateViewController mVariableDateViewControllerClockDateView;
    private final VariableDateViewController mVariableDateViewControllerDateView;

    QuickStatusBarHeaderController(QuickStatusBarHeader quickStatusBarHeader, PrivacyItemController privacyItemController, ActivityStarter activityStarter, UiEventLogger uiEventLogger, StatusBarIconController statusBarIconController, DemoModeController demoModeController, QuickQSPanelController quickQSPanelController, QSCarrierGroupController.Builder builder, PrivacyLogger privacyLogger, SysuiColorExtractor sysuiColorExtractor, PrivacyDialogController privacyDialogController, QSExpansionPathInterpolator qSExpansionPathInterpolator, FeatureFlags featureFlags, VariableDateViewController.Factory factory) {
        super(quickStatusBarHeader);
        this.mPrivacyItemController = privacyItemController;
        this.mActivityStarter = activityStarter;
        this.mUiEventLogger = uiEventLogger;
        this.mStatusBarIconController = statusBarIconController;
        this.mDemoModeController = demoModeController;
        this.mHeaderQsPanelController = quickQSPanelController;
        this.mPrivacyLogger = privacyLogger;
        this.mPrivacyDialogController = privacyDialogController;
        this.mQSExpansionPathInterpolator = qSExpansionPathInterpolator;
        this.mFeatureFlags = featureFlags;
        this.mQSCarrierGroupController = builder.setQSCarrierGroup((QSCarrierGroup) ((QuickStatusBarHeader) this.mView).findViewById(R$id.carrier_group)).build();
        this.mPrivacyChip = (OngoingPrivacyChip) ((QuickStatusBarHeader) this.mView).findViewById(R$id.privacy_chip);
        Clock clock = (Clock) ((QuickStatusBarHeader) this.mView).findViewById(R$id.clock);
        this.mClockView = clock;
        StatusIconContainer statusIconContainer = (StatusIconContainer) ((QuickStatusBarHeader) this.mView).findViewById(R$id.statusIcons);
        this.mIconContainer = statusIconContainer;
        this.mVariableDateViewControllerDateView = factory.create((VariableDateView) ((QuickStatusBarHeader) this.mView).requireViewById(R$id.date));
        this.mVariableDateViewControllerClockDateView = factory.create((VariableDateView) ((QuickStatusBarHeader) this.mView).requireViewById(R$id.date_clock));
        this.mIconManager = new StatusBarIconController.TintedIconManager(statusIconContainer, featureFlags);
        this.mDemoModeReceiver = new ClockDemoModeReceiver(clock);
        this.mColorExtractor = sysuiColorExtractor;
        QuickStatusBarHeaderController$$ExternalSyntheticLambda0 quickStatusBarHeaderController$$ExternalSyntheticLambda0 = new QuickStatusBarHeaderController$$ExternalSyntheticLambda0(this);
        this.mOnColorsChangedListener = quickStatusBarHeaderController$$ExternalSyntheticLambda0;
        sysuiColorExtractor.addOnColorsChangedListener(quickStatusBarHeaderController$$ExternalSyntheticLambda0);
        this.mCameraSlot = getResources().getString(17041433);
        this.mMicSlot = getResources().getString(17041445);
        this.mLocationSlot = getResources().getString(17041443);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ColorExtractor colorExtractor, int i) {
        this.mClockView.onColorsChanged(this.mColorExtractor.getNeutralColors().supportsDarkText());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        List<String> list;
        this.mPrivacyChip.setOnClickListener(this.mOnClickListener);
        this.mMicCameraIndicatorsEnabled = this.mPrivacyItemController.getMicCameraAvailable();
        this.mLocationIndicatorsEnabled = this.mPrivacyItemController.getLocationAvailable();
        updatePrivacyIconSlots();
        this.mIconContainer.addIgnoredSlot(getResources().getString(17041444));
        boolean z = false;
        this.mIconContainer.setShouldRestrictIcons(false);
        this.mStatusBarIconController.addIconGroup(this.mIconManager);
        if (this.mPrivacyChip.getVisibility() == 0) {
            z = true;
        }
        setChipVisibility(z);
        ((QuickStatusBarHeader) this.mView).setIsSingleCarrier(this.mQSCarrierGroupController.isSingleCarrier());
        QSCarrierGroupController qSCarrierGroupController = this.mQSCarrierGroupController;
        QuickStatusBarHeader quickStatusBarHeader = (QuickStatusBarHeader) this.mView;
        Objects.requireNonNull(quickStatusBarHeader);
        qSCarrierGroupController.setOnSingleCarrierChangedListener(new QuickStatusBarHeaderController$$ExternalSyntheticLambda1(quickStatusBarHeader));
        if (this.mFeatureFlags.isCombinedStatusBarSignalIconsEnabled()) {
            list = List.of(getResources().getString(17041449), getResources().getString(17041432));
        } else {
            list = List.of(getResources().getString(17041446));
        }
        ((QuickStatusBarHeader) this.mView).onAttach(this.mIconManager, this.mQSExpansionPathInterpolator, list);
        this.mDemoModeController.addCallback(this.mDemoModeReceiver);
        this.mVariableDateViewControllerDateView.init();
        this.mVariableDateViewControllerClockDateView.init();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mColorExtractor.removeOnColorsChangedListener(this.mOnColorsChangedListener);
        this.mPrivacyChip.setOnClickListener(null);
        this.mStatusBarIconController.removeIconGroup(this.mIconManager);
        this.mQSCarrierGroupController.setOnSingleCarrierChangedListener(null);
        this.mDemoModeController.removeCallback(this.mDemoModeReceiver);
        setListening(false);
    }

    public void setListening(boolean z) {
        this.mQSCarrierGroupController.setListening(z);
        if (z != this.mListening) {
            this.mListening = z;
            this.mHeaderQsPanelController.setListening(z);
            if (this.mHeaderQsPanelController.isListening()) {
                this.mHeaderQsPanelController.refreshAllTiles();
            }
            if (this.mHeaderQsPanelController.switchTileLayout(false)) {
                ((QuickStatusBarHeader) this.mView).updateResources();
            }
            if (z) {
                this.mMicCameraIndicatorsEnabled = this.mPrivacyItemController.getMicCameraAvailable();
                this.mLocationIndicatorsEnabled = this.mPrivacyItemController.getLocationAvailable();
                this.mPrivacyItemController.addCallback(this.mPICCallback);
                return;
            }
            this.mPrivacyItemController.removeCallback(this.mPICCallback);
            this.mPrivacyChipLogged = false;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setChipVisibility(boolean z) {
        if (!z || !getChipEnabled()) {
            this.mPrivacyLogger.logChipVisible(false);
        } else {
            this.mPrivacyLogger.logChipVisible(true);
            if (!this.mPrivacyChipLogged && this.mListening) {
                this.mPrivacyChipLogged = true;
                this.mUiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_VIEW);
            }
        }
        ((QuickStatusBarHeader) this.mView).setChipVisibility(z);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updatePrivacyIconSlots() {
        if (getChipEnabled()) {
            if (this.mMicCameraIndicatorsEnabled) {
                this.mIconContainer.addIgnoredSlot(this.mCameraSlot);
                this.mIconContainer.addIgnoredSlot(this.mMicSlot);
            } else {
                this.mIconContainer.removeIgnoredSlot(this.mCameraSlot);
                this.mIconContainer.removeIgnoredSlot(this.mMicSlot);
            }
            if (this.mLocationIndicatorsEnabled) {
                this.mIconContainer.addIgnoredSlot(this.mLocationSlot);
            } else {
                this.mIconContainer.removeIgnoredSlot(this.mLocationSlot);
            }
        } else {
            this.mIconContainer.removeIgnoredSlot(this.mCameraSlot);
            this.mIconContainer.removeIgnoredSlot(this.mMicSlot);
            this.mIconContainer.removeIgnoredSlot(this.mLocationSlot);
        }
    }

    private boolean getChipEnabled() {
        return this.mMicCameraIndicatorsEnabled || this.mLocationIndicatorsEnabled;
    }

    public void setContentMargins(int i, int i2) {
        this.mHeaderQsPanelController.setContentMargins(i, i2);
    }

    private static class ClockDemoModeReceiver implements DemoMode {
        private Clock mClockView;

        @Override // com.android.systemui.demomode.DemoMode
        public List<String> demoCommands() {
            return List.of("clock");
        }

        ClockDemoModeReceiver(Clock clock) {
            this.mClockView = clock;
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public void dispatchDemoCommand(String str, Bundle bundle) {
            this.mClockView.dispatchDemoCommand(str, bundle);
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public void onDemoModeStarted() {
            this.mClockView.onDemoModeStarted();
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public void onDemoModeFinished() {
            this.mClockView.onDemoModeFinished();
        }
    }
}

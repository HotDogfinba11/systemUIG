package com.android.keyguard;

import com.android.keyguard.CarrierTextManager;
import com.android.systemui.util.ViewController;

public class CarrierTextController extends ViewController<CarrierText> {
    private final CarrierTextManager.CarrierTextCallback mCarrierTextCallback = new CarrierTextManager.CarrierTextCallback() {
        /* class com.android.keyguard.CarrierTextController.AnonymousClass1 */

        @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
        public void updateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
            ((CarrierText) ((ViewController) CarrierTextController.this).mView).setText(carrierTextCallbackInfo.carrierText);
        }

        @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
        public void startedGoingToSleep() {
            ((CarrierText) ((ViewController) CarrierTextController.this).mView).setSelected(false);
        }

        @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
        public void finishedWakingUp() {
            ((CarrierText) ((ViewController) CarrierTextController.this).mView).setSelected(true);
        }
    };
    private final CarrierTextManager mCarrierTextManager;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    public CarrierTextController(CarrierText carrierText, CarrierTextManager.Builder builder, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(carrierText);
        this.mCarrierTextManager = builder.setShowAirplaneMode(((CarrierText) this.mView).getShowAirplaneMode()).setShowMissingSim(((CarrierText) this.mView).getShowMissingSim()).build();
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        super.onInit();
        ((CarrierText) this.mView).setSelected(this.mKeyguardUpdateMonitor.isDeviceInteractive());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        this.mCarrierTextManager.setListening(this.mCarrierTextCallback);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mCarrierTextManager.setListening(null);
    }
}

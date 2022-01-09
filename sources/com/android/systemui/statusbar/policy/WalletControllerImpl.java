package com.android.systemui.statusbar.policy;

import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WalletControllerImpl.kt */
public final class WalletControllerImpl implements WalletController {
    public static final Companion Companion = new Companion(null);
    private final QuickAccessWalletClient quickAccessWalletClient;

    public WalletControllerImpl(QuickAccessWalletClient quickAccessWalletClient2) {
        Intrinsics.checkNotNullParameter(quickAccessWalletClient2, "quickAccessWalletClient");
        this.quickAccessWalletClient = quickAccessWalletClient2;
    }

    /* compiled from: WalletControllerImpl.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // com.android.systemui.statusbar.policy.WalletController
    public Integer getWalletPosition() {
        if (this.quickAccessWalletClient.isWalletServiceAvailable()) {
            Log.i("WalletControllerImpl", "Setting WalletTile position: 3");
            return 3;
        }
        Log.i("WalletControllerImpl", "Setting WalletTile position: null");
        return null;
    }
}

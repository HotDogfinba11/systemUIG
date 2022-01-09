package com.android.systemui.statusbar.notification.interruption;

import android.content.Context;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.tuner.TunerService;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class BypassHeadsUpNotifier_Factory implements Factory<BypassHeadsUpNotifier> {
    private final Provider<KeyguardBypassController> bypassControllerProvider;
    private final Provider<Context> contextProvider;
    private final Provider<NotificationEntryManager> entryManagerProvider;
    private final Provider<HeadsUpManagerPhone> headsUpManagerProvider;
    private final Provider<NotificationMediaManager> mediaManagerProvider;
    private final Provider<NotificationLockscreenUserManager> notificationLockscreenUserManagerProvider;
    private final Provider<StatusBarStateController> statusBarStateControllerProvider;
    private final Provider<TunerService> tunerServiceProvider;

    public BypassHeadsUpNotifier_Factory(Provider<Context> provider, Provider<KeyguardBypassController> provider2, Provider<StatusBarStateController> provider3, Provider<HeadsUpManagerPhone> provider4, Provider<NotificationLockscreenUserManager> provider5, Provider<NotificationMediaManager> provider6, Provider<NotificationEntryManager> provider7, Provider<TunerService> provider8) {
        this.contextProvider = provider;
        this.bypassControllerProvider = provider2;
        this.statusBarStateControllerProvider = provider3;
        this.headsUpManagerProvider = provider4;
        this.notificationLockscreenUserManagerProvider = provider5;
        this.mediaManagerProvider = provider6;
        this.entryManagerProvider = provider7;
        this.tunerServiceProvider = provider8;
    }

    @Override // javax.inject.Provider
    public BypassHeadsUpNotifier get() {
        return newInstance(this.contextProvider.get(), this.bypassControllerProvider.get(), this.statusBarStateControllerProvider.get(), this.headsUpManagerProvider.get(), this.notificationLockscreenUserManagerProvider.get(), this.mediaManagerProvider.get(), this.entryManagerProvider.get(), this.tunerServiceProvider.get());
    }

    public static BypassHeadsUpNotifier_Factory create(Provider<Context> provider, Provider<KeyguardBypassController> provider2, Provider<StatusBarStateController> provider3, Provider<HeadsUpManagerPhone> provider4, Provider<NotificationLockscreenUserManager> provider5, Provider<NotificationMediaManager> provider6, Provider<NotificationEntryManager> provider7, Provider<TunerService> provider8) {
        return new BypassHeadsUpNotifier_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static BypassHeadsUpNotifier newInstance(Context context, KeyguardBypassController keyguardBypassController, StatusBarStateController statusBarStateController, HeadsUpManagerPhone headsUpManagerPhone, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationMediaManager notificationMediaManager, NotificationEntryManager notificationEntryManager, TunerService tunerService) {
        return new BypassHeadsUpNotifier(context, keyguardBypassController, statusBarStateController, headsUpManagerPhone, notificationLockscreenUserManager, notificationMediaManager, notificationEntryManager, tunerService);
    }
}

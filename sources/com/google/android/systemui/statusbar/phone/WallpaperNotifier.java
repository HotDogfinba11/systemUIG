package com.google.android.systemui.statusbar.phone;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.google.android.collect.Sets;
import java.util.HashSet;

public class WallpaperNotifier {
    private static final HashSet<String> NOTIFYABLE_PACKAGES = Sets.newHashSet(new String[]{"com.breel.wallpapers", "com.breel.wallpapers18", "com.google.pixel.livewallpaper"});
    private static final String[] NOTIFYABLE_WALLPAPERS = {"com.breel.wallpapers.imprint", "com.breel.wallpapers18.tactile", "com.breel.wallpapers18.delight", "com.breel.wallpapers18.miniman", "com.google.pixel.livewallpaper.imprint", "com.google.pixel.livewallpaper.tactile", "com.google.pixel.livewallpaper.delight", "com.google.pixel.livewallpaper.miniman"};
    private final Context mContext;
    private final NotificationEntryListener mEntryListener = new NotificationEntryListener() {
        /* class com.google.android.systemui.statusbar.phone.WallpaperNotifier.AnonymousClass2 */

        @Override // com.android.systemui.statusbar.notification.NotificationEntryListener
        public void onNotificationAdded(NotificationEntry notificationEntry) {
            boolean z = WallpaperNotifier.this.mUserTracker.getCurrentUserId() == 0;
            if (WallpaperNotifier.this.mShouldBroadcastNotifications && z) {
                Intent intent = new Intent();
                intent.setPackage(WallpaperNotifier.this.mWallpaperPackage);
                intent.setAction("com.breel.wallpapers.NOTIFICATION_RECEIVED");
                intent.putExtra("notification_color", notificationEntry.getSbn().getNotification().color);
                WallpaperNotifier.this.mContext.sendBroadcast(intent, "com.breel.wallpapers.notifications");
            }
        }
    };
    private final NotificationEntryManager mEntryManager;
    private boolean mShouldBroadcastNotifications;
    private final CurrentUserTracker mUserTracker;
    private BroadcastReceiver mWallpaperChangedReceiver = new BroadcastReceiver() {
        /* class com.google.android.systemui.statusbar.phone.WallpaperNotifier.AnonymousClass3 */

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.WALLPAPER_CHANGED")) {
                WallpaperNotifier.this.checkNotificationBroadcastSupport();
            }
        }
    };
    private String mWallpaperPackage;

    public WallpaperNotifier(Context context, NotificationEntryManager notificationEntryManager, BroadcastDispatcher broadcastDispatcher) {
        this.mContext = context;
        this.mEntryManager = notificationEntryManager;
        this.mUserTracker = new CurrentUserTracker(broadcastDispatcher) {
            /* class com.google.android.systemui.statusbar.phone.WallpaperNotifier.AnonymousClass1 */

            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int i) {
            }
        };
    }

    public void attach() {
        this.mEntryManager.addNotificationEntryListener(this.mEntryListener);
        this.mContext.registerReceiver(this.mWallpaperChangedReceiver, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"));
        checkNotificationBroadcastSupport();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkNotificationBroadcastSupport() {
        WallpaperInfo wallpaperInfo;
        this.mShouldBroadcastNotifications = false;
        WallpaperManager wallpaperManager = (WallpaperManager) this.mContext.getSystemService(WallpaperManager.class);
        if (!(wallpaperManager == null || (wallpaperInfo = wallpaperManager.getWallpaperInfo()) == null)) {
            ComponentName component = wallpaperInfo.getComponent();
            String packageName = component.getPackageName();
            if (NOTIFYABLE_PACKAGES.contains(packageName)) {
                this.mWallpaperPackage = packageName;
                String className = component.getClassName();
                for (String str : NOTIFYABLE_WALLPAPERS) {
                    if (className.startsWith(str)) {
                        this.mShouldBroadcastNotifications = true;
                        return;
                    }
                }
            }
        }
    }
}

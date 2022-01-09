package com.google.android.systemui.statusbar.phone;

import android.os.Handler;
import com.android.systemui.plugins.SensorManagerPlugin;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.util.Assert;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

public class NotificationIconCenteringController implements SensorManagerPlugin.SensorEventListener {
    private NotificationEntry mEntryCentered;
    private final NotificationEntryManager mEntryManager;
    protected boolean mIsSkipGestureEnabled;
    protected Handler mMainThreadHandler;
    protected String mMusicPlayingPkg;
    private NotificationIconAreaController mNotificationIconAreaController;
    private boolean mRegistered;
    protected final Runnable mResetCenteredIconRunnable;

    @Override // com.android.systemui.plugins.SensorManagerPlugin.SensorEventListener
    public void onSensorChanged(SensorManagerPlugin.SensorEvent sensorEvent) {
        this.mMainThreadHandler.post(new NotificationIconCenteringController$$ExternalSyntheticLambda0(this, sensorEvent));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$onSensorChanged$1(SensorManagerPlugin.SensorEvent sensorEvent) {
        boolean z = false;
        if (sensorEvent.getValues()[0] == 1.0f) {
            z = true;
        }
        this.mIsSkipGestureEnabled = z;
        updateCenteredIcon();
    }

    public void updateCenteredIcon() {
        if (isMusicPlaying() && isSkipGestureEnabled()) {
            for (NotificationEntry notificationEntry : this.mEntryManager.getVisibleNotifications()) {
                if (notificationEntry.isMediaNotification() && Objects.equals(notificationEntry.getSbn().getPackageName(), this.mMusicPlayingPkg)) {
                    showIconCentered(notificationEntry);
                    return;
                }
            }
        }
        showIconCentered(null);
    }

    private void showIconCentered(NotificationEntry notificationEntry) {
        Assert.isMainThread();
        this.mMainThreadHandler.removeCallbacks(this.mResetCenteredIconRunnable);
        if (notificationEntry == null) {
            this.mMainThreadHandler.postDelayed(this.mResetCenteredIconRunnable, 250);
            return;
        }
        this.mNotificationIconAreaController.showIconCentered(notificationEntry);
        this.mEntryCentered = notificationEntry;
    }

    private boolean isMusicPlaying() {
        return this.mMusicPlayingPkg != null;
    }

    private boolean isSkipGestureEnabled() {
        return this.mIsSkipGestureEnabled;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        PrintWriter append = printWriter.append("NotifIconCenterContr").append(": ");
        PrintWriter append2 = append.append((CharSequence) ("\nisMusicPlaying: " + isMusicPlaying()));
        PrintWriter append3 = append2.append((CharSequence) ("\nisSkipGestureEnabled: " + isSkipGestureEnabled()));
        PrintWriter append4 = append3.append((CharSequence) ("\nmSkipStatusRegistered: " + this.mRegistered));
        append4.append((CharSequence) ("\nmEntryCentered: " + this.mEntryCentered + "\n"));
    }
}

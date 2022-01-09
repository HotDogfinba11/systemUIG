package com.android.systemui.statusbar;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressLint({"OverrideAbstract"})
public class NotificationListener extends NotificationListenerWithPlugins {
    private final Context mContext;
    private final Handler mMainHandler;
    private final List<NotificationHandler> mNotificationHandlers = new ArrayList();
    private final NotificationManager mNotificationManager;
    private final ArrayList<NotificationSettingsListener> mSettingsListeners = new ArrayList<>();

    public interface NotificationHandler {
        default void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }

        void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap);

        void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap);

        void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i);

        void onNotificationsInitialized();
    }

    public interface NotificationSettingsListener {
        default void onStatusBarIconsBehaviorChanged(boolean z) {
        }
    }

    public NotificationListener(Context context, NotificationManager notificationManager, Handler handler) {
        this.mContext = context;
        this.mNotificationManager = notificationManager;
        this.mMainHandler = handler;
    }

    public void addNotificationHandler(NotificationHandler notificationHandler) {
        if (!this.mNotificationHandlers.contains(notificationHandler)) {
            this.mNotificationHandlers.add(notificationHandler);
            return;
        }
        throw new IllegalArgumentException("Listener is already added");
    }

    public void addNotificationSettingsListener(NotificationSettingsListener notificationSettingsListener) {
        this.mSettingsListeners.add(notificationSettingsListener);
    }

    public void onListenerConnected() {
        onPluginConnected();
        StatusBarNotification[] activeNotifications = getActiveNotifications();
        if (activeNotifications == null) {
            Log.w("NotificationListener", "onListenerConnected unable to get active notifications.");
            return;
        }
        this.mMainHandler.post(new NotificationListener$$ExternalSyntheticLambda4(this, activeNotifications, getCurrentRanking()));
        onSilentStatusBarIconsVisibilityChanged(this.mNotificationManager.shouldHideSilentStatusBarIcons());
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onListenerConnected$0(StatusBarNotification[] statusBarNotificationArr, NotificationListenerService.RankingMap rankingMap) {
        ArrayList arrayList = new ArrayList();
        for (StatusBarNotification statusBarNotification : statusBarNotificationArr) {
            arrayList.add(getRankingOrTemporaryStandIn(rankingMap, statusBarNotification.getKey()));
        }
        NotificationListenerService.RankingMap rankingMap2 = new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0]));
        for (StatusBarNotification statusBarNotification2 : statusBarNotificationArr) {
            for (NotificationHandler notificationHandler : this.mNotificationHandlers) {
                notificationHandler.onNotificationPosted(statusBarNotification2, rankingMap2);
            }
        }
        for (NotificationHandler notificationHandler2 : this.mNotificationHandlers) {
            notificationHandler2.onNotificationsInitialized();
        }
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        if (statusBarNotification != null && !onPluginNotificationPosted(statusBarNotification, rankingMap)) {
            this.mMainHandler.post(new NotificationListener$$ExternalSyntheticLambda1(this, statusBarNotification, rankingMap));
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onNotificationPosted$1(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        RemoteInputController.processForRemoteInput(statusBarNotification.getNotification(), this.mContext);
        for (NotificationHandler notificationHandler : this.mNotificationHandlers) {
            notificationHandler.onNotificationPosted(statusBarNotification, rankingMap);
        }
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        if (statusBarNotification != null && !onPluginNotificationRemoved(statusBarNotification, rankingMap)) {
            this.mMainHandler.post(new NotificationListener$$ExternalSyntheticLambda2(this, statusBarNotification, rankingMap, i));
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onNotificationRemoved$2(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        for (NotificationHandler notificationHandler : this.mNotificationHandlers) {
            notificationHandler.onNotificationRemoved(statusBarNotification, rankingMap, i);
        }
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        onNotificationRemoved(statusBarNotification, rankingMap, 0);
    }

    public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        if (rankingMap != null) {
            this.mMainHandler.post(new NotificationListener$$ExternalSyntheticLambda0(this, onPluginRankingUpdate(rankingMap)));
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onNotificationRankingUpdate$3(NotificationListenerService.RankingMap rankingMap) {
        for (NotificationHandler notificationHandler : this.mNotificationHandlers) {
            notificationHandler.onNotificationRankingUpdate(rankingMap);
        }
    }

    public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        if (!onPluginNotificationChannelModified(str, userHandle, notificationChannel, i)) {
            this.mMainHandler.post(new NotificationListener$$ExternalSyntheticLambda3(this, str, userHandle, notificationChannel, i));
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onNotificationChannelModified$4(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        for (NotificationHandler notificationHandler : this.mNotificationHandlers) {
            notificationHandler.onNotificationChannelModified(str, userHandle, notificationChannel, i);
        }
    }

    public void onSilentStatusBarIconsVisibilityChanged(boolean z) {
        Iterator<NotificationSettingsListener> it = this.mSettingsListeners.iterator();
        while (it.hasNext()) {
            it.next().onStatusBarIconsBehaviorChanged(z);
        }
    }

    public void registerAsSystemService() {
        try {
            registerAsSystemService(this.mContext, new ComponentName(this.mContext.getPackageName(), NotificationListener.class.getCanonicalName()), -1);
        } catch (RemoteException e) {
            Log.e("NotificationListener", "Unable to register notification listener", e);
        }
    }

    private static NotificationListenerService.Ranking getRankingOrTemporaryStandIn(NotificationListenerService.RankingMap rankingMap, String str) {
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        if (rankingMap.getRanking(str, ranking)) {
            return ranking;
        }
        ranking.populate(str, 0, false, 0, 0, 0, null, null, null, new ArrayList(), new ArrayList(), false, 0, false, 0, false, new ArrayList(), new ArrayList(), false, false, false, null, 0, false);
        return ranking;
    }
}

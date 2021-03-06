package com.android.systemui.statusbar.notification.collection.inflation;

import android.content.Context;
import android.view.ViewGroup;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationUiAdjustment;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.RowInflaterTask;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import java.util.Objects;
import javax.inject.Provider;

public class NotificationRowBinderImpl implements NotificationRowBinder {
    private BindRowCallback mBindRowCallback;
    private final Context mContext;
    private final ExpandableNotificationRowComponent.Builder mExpandableNotificationRowComponentBuilder;
    private final IconManager mIconManager;
    private NotificationListContainer mListContainer;
    private final LowPriorityInflationHelper mLowPriorityInflationHelper;
    private final NotificationMessagingUtil mMessagingUtil;
    private final NotifBindPipeline mNotifBindPipeline;
    private NotificationClicker mNotificationClicker;
    private final NotificationLockscreenUserManager mNotificationLockscreenUserManager;
    private final NotificationRemoteInputManager mNotificationRemoteInputManager;
    private NotificationPresenter mPresenter;
    private final RowContentBindStage mRowContentBindStage;
    private final Provider<RowInflaterTask> mRowInflaterTaskProvider;

    public interface BindRowCallback {
        void onBindRow(ExpandableNotificationRow expandableNotificationRow);
    }

    public NotificationRowBinderImpl(Context context, NotificationMessagingUtil notificationMessagingUtil, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage, Provider<RowInflaterTask> provider, ExpandableNotificationRowComponent.Builder builder, IconManager iconManager, LowPriorityInflationHelper lowPriorityInflationHelper) {
        this.mContext = context;
        this.mNotifBindPipeline = notifBindPipeline;
        this.mRowContentBindStage = rowContentBindStage;
        this.mMessagingUtil = notificationMessagingUtil;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mNotificationLockscreenUserManager = notificationLockscreenUserManager;
        this.mRowInflaterTaskProvider = provider;
        this.mExpandableNotificationRowComponentBuilder = builder;
        this.mIconManager = iconManager;
        this.mLowPriorityInflationHelper = lowPriorityInflationHelper;
    }

    public void setUpWithPresenter(NotificationPresenter notificationPresenter, NotificationListContainer notificationListContainer, BindRowCallback bindRowCallback) {
        this.mPresenter = notificationPresenter;
        this.mListContainer = notificationListContainer;
        this.mBindRowCallback = bindRowCallback;
        this.mIconManager.attach();
    }

    public void setNotificationClicker(NotificationClicker notificationClicker) {
        this.mNotificationClicker = notificationClicker;
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinder
    public void inflateViews(NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback) throws InflationException {
        ViewGroup viewParentForNotification = this.mListContainer.getViewParentForNotification(notificationEntry);
        if (notificationEntry.rowExists()) {
            this.mIconManager.updateIcons(notificationEntry);
            ExpandableNotificationRow row = notificationEntry.getRow();
            row.reset();
            updateRow(notificationEntry, row);
            inflateContentViews(notificationEntry, row, inflationCallback);
            return;
        }
        this.mIconManager.createIcons(notificationEntry);
        this.mRowInflaterTaskProvider.get().inflate(this.mContext, viewParentForNotification, notificationEntry, new NotificationRowBinderImpl$$ExternalSyntheticLambda1(this, notificationEntry, inflationCallback));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$inflateViews$0(NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback, ExpandableNotificationRow expandableNotificationRow) {
        ExpandableNotificationRowController expandableNotificationRowController = this.mExpandableNotificationRowComponentBuilder.expandableNotificationRow(expandableNotificationRow).notificationEntry(notificationEntry).onExpandClickListener(this.mPresenter).listContainer(this.mListContainer).build().getExpandableNotificationRowController();
        expandableNotificationRowController.init(notificationEntry);
        notificationEntry.setRowController(expandableNotificationRowController);
        bindRow(notificationEntry, expandableNotificationRow);
        updateRow(notificationEntry, expandableNotificationRow);
        inflateContentViews(notificationEntry, expandableNotificationRow, inflationCallback);
    }

    private void bindRow(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        this.mListContainer.bindRow(expandableNotificationRow);
        this.mNotificationRemoteInputManager.bindRow(expandableNotificationRow);
        expandableNotificationRow.setOnActivatedListener(this.mPresenter);
        notificationEntry.setRow(expandableNotificationRow);
        this.mNotifBindPipeline.manageRow(notificationEntry, expandableNotificationRow);
        this.mBindRowCallback.onBindRow(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinder
    public void onNotificationRankingUpdated(NotificationEntry notificationEntry, Integer num, NotificationUiAdjustment notificationUiAdjustment, NotificationUiAdjustment notificationUiAdjustment2, NotificationRowContentBinder.InflationCallback inflationCallback) {
        if (NotificationUiAdjustment.needReinflate(notificationUiAdjustment, notificationUiAdjustment2)) {
            if (notificationEntry.rowExists()) {
                ExpandableNotificationRow row = notificationEntry.getRow();
                row.reset();
                updateRow(notificationEntry, row);
                inflateContentViews(notificationEntry, row, inflationCallback);
            }
        } else if (num != null && notificationEntry.getImportance() != num.intValue() && notificationEntry.rowExists()) {
            notificationEntry.getRow().onNotificationRankingUpdated();
        }
    }

    private void updateRow(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        int i = notificationEntry.targetSdk;
        expandableNotificationRow.setLegacy(i >= 9 && i < 21);
        NotificationClicker notificationClicker = this.mNotificationClicker;
        Objects.requireNonNull(notificationClicker);
        notificationClicker.register(expandableNotificationRow, notificationEntry.getSbn());
    }

    private void inflateContentViews(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.InflationCallback inflationCallback) {
        boolean isImportantMessaging = this.mMessagingUtil.isImportantMessaging(notificationEntry.getSbn(), notificationEntry.getImportance());
        boolean shouldUseLowPriorityView = this.mLowPriorityInflationHelper.shouldUseLowPriorityView(notificationEntry);
        RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mRowContentBindStage.getStageParams(notificationEntry);
        rowContentBindParams.setUseIncreasedCollapsedHeight(isImportantMessaging);
        rowContentBindParams.setUseLowPriority(shouldUseLowPriorityView);
        expandableNotificationRow.setNeedsRedaction(this.mNotificationLockscreenUserManager.needsRedaction(notificationEntry));
        rowContentBindParams.rebindAllContentViews();
        this.mRowContentBindStage.requestRebind(notificationEntry, new NotificationRowBinderImpl$$ExternalSyntheticLambda0(expandableNotificationRow, isImportantMessaging, shouldUseLowPriorityView, inflationCallback));
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$inflateContentViews$1(ExpandableNotificationRow expandableNotificationRow, boolean z, boolean z2, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationEntry notificationEntry) {
        expandableNotificationRow.setUsesIncreasedCollapsedHeight(z);
        expandableNotificationRow.setIsLowPriority(z2);
        if (inflationCallback != null) {
            inflationCallback.onAsyncInflationFinished(notificationEntry);
        }
    }
}

package com.android.systemui.statusbar;

import android.content.Context;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R$bool;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.DynamicChildBindController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.LowPriorityInflationHelper;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.ForegroundServiceSectionController;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.util.Assert;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class NotificationViewHierarchyManager implements DynamicPrivacyController.Listener {
    private final boolean mAlwaysExpandNonGroupedNotification;
    private AssistantFeedbackController mAssistantFeedbackController;
    private final Optional<Bubbles> mBubblesOptional;
    private final KeyguardBypassController mBypassController;
    private final Context mContext;
    private final DynamicChildBindController mDynamicChildBindController;
    private final DynamicPrivacyController mDynamicPrivacyController;
    private final NotificationEntryManager mEntryManager;
    private final ForegroundServiceSectionController mFgsSectionController;
    protected final NotificationGroupManagerLegacy mGroupManager;
    private final Handler mHandler;
    private boolean mIsHandleDynamicPrivacyChangeScheduled;
    private NotificationListContainer mListContainer;
    protected final NotificationLockscreenUserManager mLockscreenUserManager;
    private final LowPriorityInflationHelper mLowPriorityInflationHelper;
    private boolean mPerformingUpdate;
    private NotificationPresenter mPresenter;
    private final SysuiStatusBarStateController mStatusBarStateController;
    private final HashMap<NotificationEntry, List<NotificationEntry>> mTmpChildOrderMap = new HashMap<>();
    protected final VisualStabilityManager mVisualStabilityManager;

    public NotificationViewHierarchyManager(Context context, Handler handler, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationGroupManagerLegacy notificationGroupManagerLegacy, VisualStabilityManager visualStabilityManager, StatusBarStateController statusBarStateController, NotificationEntryManager notificationEntryManager, KeyguardBypassController keyguardBypassController, Optional<Bubbles> optional, DynamicPrivacyController dynamicPrivacyController, ForegroundServiceSectionController foregroundServiceSectionController, DynamicChildBindController dynamicChildBindController, LowPriorityInflationHelper lowPriorityInflationHelper, AssistantFeedbackController assistantFeedbackController) {
        this.mContext = context;
        this.mHandler = handler;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mBypassController = keyguardBypassController;
        this.mGroupManager = notificationGroupManagerLegacy;
        this.mVisualStabilityManager = visualStabilityManager;
        this.mStatusBarStateController = (SysuiStatusBarStateController) statusBarStateController;
        this.mEntryManager = notificationEntryManager;
        this.mFgsSectionController = foregroundServiceSectionController;
        this.mAlwaysExpandNonGroupedNotification = context.getResources().getBoolean(R$bool.config_alwaysExpandNonGroupedNotifications);
        this.mBubblesOptional = optional;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mDynamicChildBindController = dynamicChildBindController;
        this.mLowPriorityInflationHelper = lowPriorityInflationHelper;
        this.mAssistantFeedbackController = assistantFeedbackController;
    }

    public void setUpWithPresenter(NotificationPresenter notificationPresenter, NotificationListContainer notificationListContainer) {
        this.mPresenter = notificationPresenter;
        this.mListContainer = notificationListContainer;
        this.mDynamicPrivacyController.addListener(this);
    }

    public void updateNotificationViews() {
        Assert.isMainThread();
        beginUpdate();
        List<NotificationEntry> visibleNotifications = this.mEntryManager.getVisibleNotifications();
        ArrayList arrayList = new ArrayList(visibleNotifications.size());
        int size = visibleNotifications.size();
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= size) {
                break;
            }
            NotificationEntry notificationEntry = visibleNotifications.get(i);
            if (!shouldSuppressActiveNotification(notificationEntry)) {
                int userId = notificationEntry.getSbn().getUserId();
                int currentUserId = this.mLockscreenUserManager.getCurrentUserId();
                boolean isLockscreenPublicMode = this.mLockscreenUserManager.isLockscreenPublicMode(currentUserId);
                boolean z2 = isLockscreenPublicMode || this.mLockscreenUserManager.isLockscreenPublicMode(userId);
                if (z2 && this.mDynamicPrivacyController.isDynamicallyUnlocked() && (userId == currentUserId || userId == -1 || !this.mLockscreenUserManager.needsSeparateWorkChallenge(userId))) {
                    z2 = false;
                }
                boolean needsRedaction = this.mLockscreenUserManager.needsRedaction(notificationEntry);
                notificationEntry.setSensitive(z2 && needsRedaction, isLockscreenPublicMode && !this.mLockscreenUserManager.userAllowsPrivateNotificationsInPublic(currentUserId));
                notificationEntry.getRow().setNeedsRedaction(needsRedaction);
                this.mLowPriorityInflationHelper.recheckLowPriorityViewAndInflate(notificationEntry, notificationEntry.getRow());
                boolean isChildInGroup = this.mGroupManager.isChildInGroup(notificationEntry);
                if (!this.mVisualStabilityManager.areGroupChangesAllowed() && notificationEntry.hasFinishedInitialization()) {
                    z = false;
                }
                NotificationEntry groupSummary = this.mGroupManager.getGroupSummary(notificationEntry);
                if (!z) {
                    boolean isChildInGroup2 = notificationEntry.isChildInGroup();
                    if (isChildInGroup && !isChildInGroup2) {
                        this.mVisualStabilityManager.addGroupChangesAllowedCallback(this.mEntryManager, false);
                    } else if (!isChildInGroup && isChildInGroup2 && this.mGroupManager.isLogicalGroupExpanded(notificationEntry.getSbn())) {
                        groupSummary = notificationEntry.getRow().getNotificationParent().getEntry();
                        this.mVisualStabilityManager.addGroupChangesAllowedCallback(this.mEntryManager, false);
                    }
                    isChildInGroup = isChildInGroup2;
                }
                if (isChildInGroup) {
                    List<NotificationEntry> list = this.mTmpChildOrderMap.get(groupSummary);
                    if (list == null) {
                        list = new ArrayList<>();
                        this.mTmpChildOrderMap.put(groupSummary, list);
                    }
                    list.add(notificationEntry);
                } else {
                    if (!this.mTmpChildOrderMap.containsKey(notificationEntry)) {
                        this.mTmpChildOrderMap.put(notificationEntry, null);
                    }
                    arrayList.add(notificationEntry.getRow());
                }
            }
            i++;
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < this.mListContainer.getContainerChildCount(); i2++) {
            View containerChildAt = this.mListContainer.getContainerChildAt(i2);
            if (!arrayList.contains(containerChildAt) && (containerChildAt instanceof ExpandableNotificationRow)) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) containerChildAt;
                if (!expandableNotificationRow.isBlockingHelperShowing()) {
                    arrayList2.add(expandableNotificationRow);
                }
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) it.next();
            NotificationEntry entry = expandableNotificationRow2.getEntry();
            if (this.mEntryManager.getPendingOrActiveNotif(entry.getKey()) != null && !shouldSuppressActiveNotification(entry)) {
                this.mListContainer.setChildTransferInProgress(true);
            }
            if (expandableNotificationRow2.isSummaryWithChildren()) {
                expandableNotificationRow2.removeAllChildren();
            }
            this.mListContainer.removeContainerView(expandableNotificationRow2);
            this.mListContainer.setChildTransferInProgress(false);
        }
        removeNotificationChildren();
        int i3 = 0;
        while (i3 < arrayList.size()) {
            View view = (View) arrayList.get(i3);
            if (view.getParent() == null) {
                this.mVisualStabilityManager.notifyViewAddition(view);
                this.mListContainer.addContainerView(view);
            } else if (!this.mListContainer.containsView(view)) {
                arrayList.remove(view);
                i3--;
            }
            i3++;
        }
        addNotificationChildrenAndSort();
        int i4 = 0;
        for (int i5 = 0; i5 < this.mListContainer.getContainerChildCount(); i5++) {
            View containerChildAt2 = this.mListContainer.getContainerChildAt(i5);
            if ((containerChildAt2 instanceof ExpandableNotificationRow) && !((ExpandableNotificationRow) containerChildAt2).isBlockingHelperShowing()) {
                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) arrayList.get(i4);
                if (containerChildAt2 != expandableNotificationRow3) {
                    if (this.mVisualStabilityManager.canReorderNotification(expandableNotificationRow3)) {
                        this.mListContainer.changeViewPosition(expandableNotificationRow3, i5);
                    } else {
                        this.mVisualStabilityManager.addReorderingAllowedCallback(this.mEntryManager, false);
                    }
                }
                i4++;
            }
        }
        this.mDynamicChildBindController.updateContentViews(this.mTmpChildOrderMap);
        this.mVisualStabilityManager.onReorderingFinished();
        this.mTmpChildOrderMap.clear();
        updateRowStatesInternal();
        this.mListContainer.onNotificationViewUpdateFinished();
        endUpdate();
    }

    private boolean shouldSuppressActiveNotification(NotificationEntry notificationEntry) {
        return notificationEntry.isRowDismissed() || notificationEntry.isRowRemoved() || (this.mBubblesOptional.isPresent() && this.mBubblesOptional.get().isBubbleNotificationSuppressedFromShade(notificationEntry.getKey(), notificationEntry.getSbn().getGroupKey())) || this.mFgsSectionController.hasEntry(notificationEntry);
    }

    private void addNotificationChildrenAndSort() {
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (int i = 0; i < this.mListContainer.getContainerChildCount(); i++) {
            View containerChildAt = this.mListContainer.getContainerChildAt(i);
            if (containerChildAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) containerChildAt;
                List<ExpandableNotificationRow> attachedChildren = expandableNotificationRow.getAttachedChildren();
                List<NotificationEntry> list = this.mTmpChildOrderMap.get(expandableNotificationRow.getEntry());
                if (list != null) {
                    expandableNotificationRow.setUntruncatedChildCount(list.size());
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        ExpandableNotificationRow row = list.get(i2).getRow();
                        if (attachedChildren == null || !attachedChildren.contains(row)) {
                            if (row.getParent() != null) {
                                Log.wtf("NotificationViewHierarchyManager", "trying to add a notification child that already has a parent. class:" + row.getParent().getClass() + "\n child: " + row);
                                ((ViewGroup) row.getParent()).removeView(row);
                            }
                            this.mVisualStabilityManager.notifyViewAddition(row);
                            expandableNotificationRow.addChildNotification(row, i2);
                            this.mListContainer.notifyGroupChildAdded(row);
                        }
                        arrayList.add(row);
                    }
                    z |= expandableNotificationRow.applyChildOrder(arrayList, this.mVisualStabilityManager, this.mEntryManager);
                    arrayList.clear();
                }
            }
        }
        if (z) {
            this.mListContainer.generateChildOrderChangedEvent();
        }
    }

    private void removeNotificationChildren() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mListContainer.getContainerChildCount(); i++) {
            View containerChildAt = this.mListContainer.getContainerChildAt(i);
            if (containerChildAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) containerChildAt;
                List<ExpandableNotificationRow> attachedChildren = expandableNotificationRow.getAttachedChildren();
                List<NotificationEntry> list = this.mTmpChildOrderMap.get(expandableNotificationRow.getEntry());
                if (attachedChildren != null) {
                    arrayList.clear();
                    for (ExpandableNotificationRow expandableNotificationRow2 : attachedChildren) {
                        if ((list == null || !list.contains(expandableNotificationRow2.getEntry())) && !expandableNotificationRow2.keepInParent()) {
                            arrayList.add(expandableNotificationRow2);
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) it.next();
                        expandableNotificationRow.removeChildNotification(expandableNotificationRow3);
                        if (this.mEntryManager.getActiveNotificationUnfiltered(expandableNotificationRow3.getEntry().getSbn().getKey()) == null) {
                            this.mListContainer.notifyGroupChildRemoved(expandableNotificationRow3, expandableNotificationRow.getChildrenContainer());
                        }
                    }
                }
            }
        }
    }

    public void updateRowStates() {
        Assert.isMainThread();
        beginUpdate();
        updateRowStatesInternal();
        endUpdate();
    }

    private void updateRowStatesInternal() {
        NotificationEntry logicalGroupSummary;
        Trace.beginSection("NotificationViewHierarchyManager#updateRowStates");
        int containerChildCount = this.mListContainer.getContainerChildCount();
        boolean z = this.mStatusBarStateController.getCurrentOrUpcomingState() == 1;
        Stack stack = new Stack();
        for (int i = containerChildCount - 1; i >= 0; i--) {
            View containerChildAt = this.mListContainer.getContainerChildAt(i);
            if (containerChildAt instanceof ExpandableNotificationRow) {
                stack.push((ExpandableNotificationRow) containerChildAt);
            }
        }
        int i2 = 0;
        while (!stack.isEmpty()) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) stack.pop();
            NotificationEntry entry = expandableNotificationRow.getEntry();
            boolean isChildInGroup = this.mGroupManager.isChildInGroup(entry);
            if (!z) {
                expandableNotificationRow.setSystemExpanded(this.mAlwaysExpandNonGroupedNotification || (i2 == 0 && !isChildInGroup && !expandableNotificationRow.isLowPriority()));
            }
            int userId = entry.getSbn().getUserId();
            boolean z2 = this.mGroupManager.isSummaryOfSuppressedGroup(entry.getSbn()) && !entry.isRowRemoved();
            boolean shouldShowOnKeyguard = this.mLockscreenUserManager.shouldShowOnKeyguard(entry);
            if (!shouldShowOnKeyguard && this.mGroupManager.isChildInGroup(entry) && (logicalGroupSummary = this.mGroupManager.getLogicalGroupSummary(entry)) != null && this.mLockscreenUserManager.shouldShowOnKeyguard(logicalGroupSummary)) {
                shouldShowOnKeyguard = true;
            }
            if (z2 || this.mLockscreenUserManager.shouldHideNotifications(userId) || (z && !shouldShowOnKeyguard)) {
                entry.getRow().setVisibility(8);
            } else {
                boolean z3 = entry.getRow().getVisibility() == 8;
                if (z3) {
                    entry.getRow().setVisibility(0);
                }
                if (!isChildInGroup && !entry.getRow().isRemoved()) {
                    if (z3) {
                        this.mListContainer.generateAddAnimation(entry.getRow(), !shouldShowOnKeyguard);
                    }
                    i2++;
                }
            }
            if (expandableNotificationRow.isSummaryWithChildren()) {
                List<ExpandableNotificationRow> attachedChildren = expandableNotificationRow.getAttachedChildren();
                for (int size = attachedChildren.size() - 1; size >= 0; size--) {
                    stack.push(attachedChildren.get(size));
                }
            }
            expandableNotificationRow.showFeedbackIcon(this.mAssistantFeedbackController.showFeedbackIndicator(entry), this.mAssistantFeedbackController.getFeedbackResources(entry));
            expandableNotificationRow.setLastAudiblyAlertedMs(entry.getLastAudiblyAlertedMs());
        }
        Trace.beginSection("NotificationPresenter#onUpdateRowStates");
        this.mPresenter.onUpdateRowStates();
        Trace.endSection();
        Trace.endSection();
    }

    @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
    public void onDynamicPrivacyChanged() {
        if (this.mPerformingUpdate) {
            Log.w("NotificationViewHierarchyManager", "onDynamicPrivacyChanged made a re-entrant call");
        }
        if (!this.mIsHandleDynamicPrivacyChangeScheduled) {
            this.mIsHandleDynamicPrivacyChangeScheduled = true;
            this.mHandler.post(new NotificationViewHierarchyManager$$ExternalSyntheticLambda0(this));
        }
    }

    /* access modifiers changed from: private */
    public void onHandleDynamicPrivacyChanged() {
        this.mIsHandleDynamicPrivacyChangeScheduled = false;
        updateNotificationViews();
    }

    private void beginUpdate() {
        if (this.mPerformingUpdate) {
            Log.wtf("NotificationViewHierarchyManager", "Re-entrant code during update", new Exception());
        }
        this.mPerformingUpdate = true;
    }

    private void endUpdate() {
        if (!this.mPerformingUpdate) {
            Log.wtf("NotificationViewHierarchyManager", "Manager state has become desynced", new Exception());
        }
        this.mPerformingUpdate = false;
    }
}

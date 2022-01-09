package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.Coordinator;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GroupExpansionManagerImpl implements GroupExpansionManager, Coordinator {
    private final Set<NotificationEntry> mExpandedGroups = new HashSet();
    private final GroupMembershipManager mGroupMembershipManager;
    private final OnBeforeRenderListListener mNotifTracker = new GroupExpansionManagerImpl$$ExternalSyntheticLambda0(this);
    private final Set<GroupExpansionManager.OnGroupExpansionChangeListener> mOnGroupChangeListeners = new HashSet();

    public GroupExpansionManagerImpl(GroupMembershipManager groupMembershipManager) {
        this.mGroupMembershipManager = groupMembershipManager;
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$1(List list) {
        HashSet hashSet = new HashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof GroupEntry) {
                hashSet.add(listEntry.getRepresentativeEntry());
            }
        }
        this.mExpandedGroups.removeIf(new GroupExpansionManagerImpl$$ExternalSyntheticLambda1(hashSet));
    }

    public static /* synthetic */ boolean lambda$new$0(Set set, NotificationEntry notificationEntry) {
        return !set.contains(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(this.mNotifTracker);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public void registerGroupExpansionChangeListener(GroupExpansionManager.OnGroupExpansionChangeListener onGroupExpansionChangeListener) {
        this.mOnGroupChangeListeners.add(onGroupExpansionChangeListener);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public boolean isGroupExpanded(NotificationEntry notificationEntry) {
        return this.mExpandedGroups.contains(this.mGroupMembershipManager.getGroupSummary(notificationEntry));
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public void setGroupExpanded(NotificationEntry notificationEntry, boolean z) {
        NotificationEntry groupSummary = this.mGroupMembershipManager.getGroupSummary(notificationEntry);
        if (z) {
            this.mExpandedGroups.add(groupSummary);
        } else {
            this.mExpandedGroups.remove(groupSummary);
        }
        sendOnGroupExpandedChange(notificationEntry, z);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public boolean toggleGroupExpansion(NotificationEntry notificationEntry) {
        setGroupExpanded(notificationEntry, !isGroupExpanded(notificationEntry));
        return isGroupExpanded(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public void collapseGroups() {
        for (NotificationEntry notificationEntry : this.mExpandedGroups) {
            setGroupExpanded(notificationEntry, false);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("NotificationEntryExpansion state:");
        printWriter.println("  # expanded groups: " + this.mExpandedGroups.size());
        Iterator<NotificationEntry> it = this.mExpandedGroups.iterator();
        while (it.hasNext()) {
            printWriter.println("    summary key of expanded group: " + it.next().getKey());
        }
    }

    private void sendOnGroupExpandedChange(NotificationEntry notificationEntry, boolean z) {
        for (GroupExpansionManager.OnGroupExpansionChangeListener onGroupExpansionChangeListener : this.mOnGroupChangeListeners) {
            onGroupExpansionChangeListener.onGroupExpansionChange(notificationEntry.getRow(), z);
        }
    }
}

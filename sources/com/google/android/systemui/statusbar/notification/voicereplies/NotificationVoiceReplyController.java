package com.google.android.systemui.statusbar.notification.voicereplies;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.people.Subscription;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyKt__LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyController implements NotificationVoiceReplyManager.Initializer {
    private final Context context;
    private final int ctaContainerId;
    private final int ctaIconId;
    private final int ctaLayout;
    private final int ctaTextId;
    private final HeadsUpManager headsUpManager;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final NotificationVoiceReplyLogger logger;
    private final NotificationShadeWindowController notifShadeWindowController;
    private final NotificationEntryManager notificationEntryManager;
    private final NotificationRemoteInputManager notificationRemoteInputManager;
    private final LockscreenShadeTransitionController shadeTransitionController;
    private final StatusBar statusBar;
    private final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    private final SysuiStatusBarStateController statusBarStateController;

    public NotificationVoiceReplyController(NotificationEntryManager notificationEntryManager2, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationRemoteInputManager notificationRemoteInputManager2, int i, int i2, int i3, int i4, LockscreenShadeTransitionController lockscreenShadeTransitionController, NotificationShadeWindowController notificationShadeWindowController, StatusBarKeyguardViewManager statusBarKeyguardViewManager2, StatusBar statusBar2, SysuiStatusBarStateController sysuiStatusBarStateController, HeadsUpManager headsUpManager2, Context context2, NotificationVoiceReplyLogger notificationVoiceReplyLogger) {
        Intrinsics.checkNotNullParameter(notificationEntryManager2, "notificationEntryManager");
        Intrinsics.checkNotNullParameter(notificationLockscreenUserManager, "lockscreenUserManager");
        Intrinsics.checkNotNullParameter(notificationRemoteInputManager2, "notificationRemoteInputManager");
        Intrinsics.checkNotNullParameter(lockscreenShadeTransitionController, "shadeTransitionController");
        Intrinsics.checkNotNullParameter(notificationShadeWindowController, "notifShadeWindowController");
        Intrinsics.checkNotNullParameter(statusBarKeyguardViewManager2, "statusBarKeyguardViewManager");
        Intrinsics.checkNotNullParameter(statusBar2, "statusBar");
        Intrinsics.checkNotNullParameter(sysuiStatusBarStateController, "statusBarStateController");
        Intrinsics.checkNotNullParameter(headsUpManager2, "headsUpManager");
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(notificationVoiceReplyLogger, "logger");
        this.notificationEntryManager = notificationEntryManager2;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.notificationRemoteInputManager = notificationRemoteInputManager2;
        this.ctaLayout = i;
        this.ctaContainerId = i2;
        this.ctaTextId = i3;
        this.ctaIconId = i4;
        this.shadeTransitionController = lockscreenShadeTransitionController;
        this.notifShadeWindowController = notificationShadeWindowController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager2;
        this.statusBar = statusBar2;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.headsUpManager = headsUpManager2;
        this.context = context2;
        this.logger = notificationVoiceReplyLogger;
    }

    @Override // com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager.Initializer
    public NotificationVoiceReplyManager connect(CoroutineScope coroutineScope) {
        Intrinsics.checkNotNullParameter(coroutineScope, "scope");
        Connection connection = new Connection(null, null, null, null, null, 31, null);
        return new NotificationVoiceReplyController$connect$1(BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new NotificationVoiceReplyController$connect$job$1(this, connection, null), 3, null), this, connection);
    }

    /* access modifiers changed from: private */
    public final Subscription registerHandler(Connection connection, NotificationVoiceReplyHandler notificationVoiceReplyHandler) {
        ((List) NotificationVoiceReplyManagerKt.access$getOrPut(connection.getActiveHandlersByUser(), Integer.valueOf(notificationVoiceReplyHandler.getUserId()), NotificationVoiceReplyController$registerHandler$1.INSTANCE)).add(notificationVoiceReplyHandler);
        return NotificationVoiceReplyManagerKt.Subscription(new NotificationVoiceReplyController$registerHandler$2(connection, notificationVoiceReplyHandler, this));
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object resetStateOnUserChange(com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController.Connection r6, kotlin.coroutines.Continuation r7) {
        /*
        // Method dump skipped, instructions count: 122
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController.resetStateOnUserChange(com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object refreshCandidateOnNotifChanges(com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController.Connection r6, kotlin.coroutines.Continuation r7) {
        /*
        // Method dump skipped, instructions count: 122
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController.refreshCandidateOnNotifChanges(com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public static final void refreshCandidateOnNotifChanges$newCandidate(Connection connection, NotificationVoiceReplyController notificationVoiceReplyController, NotificationEntry notificationEntry, String str) {
        if (!connection.getEntryReinflations().tryEmit(TuplesKt.to(notificationEntry, str))) {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = notificationVoiceReplyController.logger;
            String key = notificationEntry.getKey();
            Intrinsics.checkNotNullExpressionValue(key, "entry.key");
            notificationVoiceReplyLogger.logReinflationDropped(key, str);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object notifyHandlersOfReplyAvailability(Connection connection, Continuation continuation) {
        Object collectLatest = NotificationVoiceReplyManagerKt.collectLatest(NotificationVoiceReplyManagerKt.distinctUntilChanged(new NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$$inlined$map$1(connection.getStateFlow()), NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$3.INSTANCE), new NotificationVoiceReplyController$notifyHandlersOfReplyAvailability$4(this, null), continuation);
        return collectLatest == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collectLatest : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final void addCallToAction(View view) {
        View findViewById = view.findViewById(16909460);
        ColorStateList colorStateList = null;
        ViewGroup viewGroup = (findViewById != null && (findViewById instanceof ViewGroup)) ? (ViewGroup) findViewById : null;
        if (viewGroup != null) {
            viewGroup.setVisibility(0);
            for (View view2 : ConvenienceExtensionsKt.getChildren(viewGroup)) {
                view2.setVisibility(8);
            }
            View inflate = LayoutInflater.from(this.context).inflate(this.ctaLayout, viewGroup, true);
            TextView textView = (TextView) view.findViewById(16909555);
            if (textView != null) {
                colorStateList = textView.getTextColors();
            }
            if (colorStateList != null) {
                int defaultColor = colorStateList.getDefaultColor();
                View requireViewById = inflate.requireViewById(this.ctaIconId);
                Intrinsics.checkNotNullExpressionValue(requireViewById, "cta.requireViewById(ctaIconId)");
                ((ImageView) requireViewById).setColorFilter(defaultColor);
                View requireViewById2 = inflate.requireViewById(this.ctaTextId);
                Intrinsics.checkNotNullExpressionValue(requireViewById2, "cta.requireViewById(ctaTextId)");
                ((TextView) requireViewById2).setTextColor(defaultColor);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void removeCallToAction(View view) {
        View findViewById = view.findViewById(16909460);
        LinearLayout linearLayout = null;
        if (findViewById != null && (findViewById instanceof LinearLayout)) {
            linearLayout = (LinearLayout) findViewById;
        }
        if (linearLayout != null) {
            linearLayout.removeView((ViewGroup) linearLayout.findViewById(this.ctaContainerId));
            for (View view2 : ConvenienceExtensionsKt.getChildren(linearLayout)) {
                view2.setVisibility(0);
            }
            if (linearLayout.getChildCount() == 0) {
                linearLayout.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object logUiEventsForActivatedVoiceReplyInputs(Connection connection, Flow flow, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2(flow, connection, this, null), continuation);
        return coroutineScope == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutineScope : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final VoiceReplyState queryInitialState(Connection connection) {
        HasCandidate hasCandidate;
        List<NotificationEntry> visibleNotifications = this.notificationEntryManager.getVisibleNotifications();
        Intrinsics.checkNotNullExpressionValue(visibleNotifications, "notificationEntryManager.visibleNotifications");
        VoiceReplyTarget voiceReplyTarget = (VoiceReplyTarget) SequencesKt.firstOrNull(SequencesKt___SequencesKt.mapNotNull(CollectionsKt___CollectionsKt.asSequence(visibleNotifications), new NotificationVoiceReplyController$queryInitialState$1(this, connection)));
        if (voiceReplyTarget == null) {
            hasCandidate = null;
        } else {
            hasCandidate = new HasCandidate(voiceReplyTarget);
        }
        return hasCandidate == null ? NoCandidate.INSTANCE : hasCandidate;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object stateMachine(Connection connection, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new NotificationVoiceReplyController$stateMachine$2(connection, this, null), continuation);
        return coroutineScope == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutineScope : Unit.INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object startVoiceReply(Connection connection, NotificationVoiceReplyHandler notificationVoiceReplyHandler, int i, String str, Function0 function0, Function2 function2, Continuation continuation) {
        Object send = connection.getStartSessionRequests().send(new StartSessionRequest(notificationVoiceReplyHandler, i, str, function0, function2), continuation);
        return send == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? send : Unit.INSTANCE;
    }

    private final boolean isMessagingStyle(NotificationEntry notificationEntry) {
        return Intrinsics.areEqual(notificationEntry.getSbn().getNotification().getNotificationStyle(), Notification.MessagingStyle.class);
    }

    /* access modifiers changed from: private */
    public final VoiceReplyState extractNewerCandidate(Connection connection, VoiceReplyTarget voiceReplyTarget, NotificationEntry notificationEntry) {
        VoiceReplyTarget extractCandidate$default;
        HasCandidate hasCandidate = null;
        if (Intrinsics.areEqual(notificationEntry.getKey(), voiceReplyTarget.getNotifKey())) {
            Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(this.context, notificationEntry.getSbn().getNotification());
            VoiceReplyTarget extractCandidate = extractCandidate(connection, notificationEntry, Notification.areStyledNotificationsVisiblyDifferent(recoverBuilder, voiceReplyTarget.getBuilder()) ? notificationEntry.getSbn().getPostTime() : voiceReplyTarget.getPostTime(), LazyKt__LazyKt.lazyOf(recoverBuilder));
            if (extractCandidate != null) {
                hasCandidate = new HasCandidate(extractCandidate);
            }
            if (hasCandidate == null) {
                return queryInitialState(connection);
            }
            return hasCandidate;
        } else if (notificationEntry.getSbn().getPostTime() > voiceReplyTarget.getPostTime() && (extractCandidate$default = extractCandidate$default(this, connection, notificationEntry, 0, null, 6, null)) != null) {
            return new HasCandidate(extractCandidate$default);
        } else {
            return null;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController */
    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ VoiceReplyTarget extractCandidate$default(NotificationVoiceReplyController notificationVoiceReplyController, Connection connection, NotificationEntry notificationEntry, long j, Lazy lazy, int i, Object obj) {
        if ((i & 2) != 0) {
            j = notificationEntry.getSbn().getPostTime();
        }
        if ((i & 4) != 0) {
            lazy = LazyKt__LazyJVMKt.lazy(new NotificationVoiceReplyController$extractCandidate$1(notificationVoiceReplyController, notificationEntry));
        }
        return notificationVoiceReplyController.extractCandidate(connection, notificationEntry, j, lazy);
    }

    private final VoiceReplyTarget extractCandidate(Connection connection, NotificationEntry notificationEntry, long j, Lazy<? extends Notification.Builder> lazy) {
        if (!isMessagingStyle(notificationEntry)) {
            return null;
        }
        int identifier = notificationEntry.getSbn().getUser().getIdentifier();
        List<NotificationVoiceReplyHandler> list = connection.getActiveHandlersByUser().get(Integer.valueOf(identifier));
        if (list == null) {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.logger;
            String key = notificationEntry.getKey();
            Intrinsics.checkNotNullExpressionValue(key, "entry.key");
            notificationVoiceReplyLogger.logRejectCandidate(key, Intrinsics.stringPlus("no handlers for user=", Integer.valueOf(identifier)));
            return null;
        }
        Notification.Action[] actionArr = notificationEntry.getSbn().getNotification().actions;
        if (actionArr != null) {
            return (VoiceReplyTarget) SequencesKt.firstOrNull(SequencesKt___SequencesKt.mapNotNull(ArraysKt___ArraysKt.asSequence(actionArr), new NotificationVoiceReplyController$extractCandidate$2(this, notificationEntry, j, lazy, list)));
        }
        NotificationVoiceReplyLogger notificationVoiceReplyLogger2 = this.logger;
        String key2 = notificationEntry.getKey();
        Intrinsics.checkNotNullExpressionValue(key2, "entry.key");
        notificationVoiceReplyLogger2.logRejectCandidate(key2, "no actions");
        return null;
    }

    /* access modifiers changed from: private */
    public final VoiceReplyTarget tryCreateVoiceReplyTarget(NotificationEntry notificationEntry, Notification.Action action, long j, Lazy<? extends Notification.Builder> lazy, List<? extends NotificationVoiceReplyHandler> list) {
        Object obj;
        Button button;
        PendingIntent pendingIntent = action.actionIntent;
        if (pendingIntent == null) {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.logger;
            String key = notificationEntry.getKey();
            Intrinsics.checkNotNullExpressionValue(key, "entry.key");
            notificationVoiceReplyLogger.logRejectCandidate(key, "no action intent");
            return null;
        }
        RemoteInput[] remoteInputs = action.getRemoteInputs();
        if (remoteInputs == null) {
            return null;
        }
        Iterator it = ArraysKt___ArraysKt.asSequence(remoteInputs).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((RemoteInput) obj).getAllowFreeFormInput()) {
                break;
            }
        }
        RemoteInput remoteInput = (RemoteInput) obj;
        if (remoteInput == null) {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger2 = this.logger;
            String key2 = notificationEntry.getKey();
            Intrinsics.checkNotNullExpressionValue(key2, "entry.key");
            notificationVoiceReplyLogger2.logRejectCandidate(key2, "no freeform input");
            return null;
        }
        ExpandableNotificationRow row = notificationEntry.getRow();
        NotificationContentView showingLayout = row == null ? null : row.getShowingLayout();
        if (showingLayout == null) {
            return null;
        }
        View expandedChild = showingLayout.getExpandedChild();
        if (expandedChild == null) {
            button = null;
        } else {
            button = NotificationVoiceReplyManagerKt.access$getReplyButton(expandedChild, remoteInput);
        }
        if (button != null) {
            return new VoiceReplyTarget(notificationEntry, (Notification.Builder) lazy.getValue(), j, list, pendingIntent, remoteInputs, remoteInput, button, this.notificationRemoteInputManager, this.shadeTransitionController, this.statusBar, this.statusBarStateController, this.logger, this.notifShadeWindowController, this.statusBarKeyguardViewManager);
        }
        NotificationVoiceReplyLogger notificationVoiceReplyLogger3 = this.logger;
        String key3 = notificationEntry.getKey();
        Intrinsics.checkNotNullExpressionValue(key3, "entry.key");
        notificationVoiceReplyLogger3.logRejectCandidate(key3, "no reply button in expanded view");
        return null;
    }

    /* access modifiers changed from: private */
    /* compiled from: NotificationVoiceReplyManager.kt */
    public static final class Connection {
        private final Map<Integer, List<NotificationVoiceReplyHandler>> activeHandlersByUser;
        private final MutableSharedFlow<Pair<NotificationEntry, String>> entryReinflations;
        private final MutableSharedFlow<String> entryRemovals;
        private final Channel<StartSessionRequest> startSessionRequests;
        private final MutableStateFlow<VoiceReplyState> stateFlow;

        public Connection() {
            this(null, null, null, null, null, 31, null);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Connection)) {
                return false;
            }
            Connection connection = (Connection) obj;
            return Intrinsics.areEqual(this.entryReinflations, connection.entryReinflations) && Intrinsics.areEqual(this.entryRemovals, connection.entryRemovals) && Intrinsics.areEqual(this.startSessionRequests, connection.startSessionRequests) && Intrinsics.areEqual(this.activeHandlersByUser, connection.activeHandlersByUser) && Intrinsics.areEqual(this.stateFlow, connection.stateFlow);
        }

        public int hashCode() {
            return (((((((this.entryReinflations.hashCode() * 31) + this.entryRemovals.hashCode()) * 31) + this.startSessionRequests.hashCode()) * 31) + this.activeHandlersByUser.hashCode()) * 31) + this.stateFlow.hashCode();
        }

        public String toString() {
            return "Connection(entryReinflations=" + this.entryReinflations + ", entryRemovals=" + this.entryRemovals + ", startSessionRequests=" + this.startSessionRequests + ", activeHandlersByUser=" + this.activeHandlersByUser + ", stateFlow=" + this.stateFlow + ')';
        }

        public Connection(MutableSharedFlow<Pair<NotificationEntry, String>> mutableSharedFlow, MutableSharedFlow<String> mutableSharedFlow2, Channel<StartSessionRequest> channel, Map<Integer, List<NotificationVoiceReplyHandler>> map, MutableStateFlow<VoiceReplyState> mutableStateFlow) {
            Intrinsics.checkNotNullParameter(mutableSharedFlow, "entryReinflations");
            Intrinsics.checkNotNullParameter(mutableSharedFlow2, "entryRemovals");
            Intrinsics.checkNotNullParameter(channel, "startSessionRequests");
            Intrinsics.checkNotNullParameter(map, "activeHandlersByUser");
            Intrinsics.checkNotNullParameter(mutableStateFlow, "stateFlow");
            this.entryReinflations = mutableSharedFlow;
            this.entryRemovals = mutableSharedFlow2;
            this.startSessionRequests = channel;
            this.activeHandlersByUser = map;
            this.stateFlow = mutableStateFlow;
        }

        public final MutableSharedFlow<Pair<NotificationEntry, String>> getEntryReinflations() {
            return this.entryReinflations;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ Connection(MutableSharedFlow mutableSharedFlow, MutableSharedFlow mutableSharedFlow2, Channel channel, Map map, MutableStateFlow mutableStateFlow, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? SharedFlowKt.MutableSharedFlow$default(0, Integer.MAX_VALUE, null, 5, null) : mutableSharedFlow, (i & 2) != 0 ? SharedFlowKt.MutableSharedFlow$default(0, Integer.MAX_VALUE, null, 5, null) : mutableSharedFlow2, (i & 4) != 0 ? ChannelKt.Channel$default(0, 1, null) : channel, (i & 8) != 0 ? new LinkedHashMap() : map, (i & 16) != 0 ? StateFlowKt.MutableStateFlow(NoCandidate.INSTANCE) : mutableStateFlow);
        }

        public final MutableSharedFlow<String> getEntryRemovals() {
            return this.entryRemovals;
        }

        public final Channel<StartSessionRequest> getStartSessionRequests() {
            return this.startSessionRequests;
        }

        public final Map<Integer, List<NotificationVoiceReplyHandler>> getActiveHandlersByUser() {
            return this.activeHandlersByUser;
        }

        public final MutableStateFlow<VoiceReplyState> getStateFlow() {
            return this.stateFlow;
        }

        public final VoiceReplyTarget getActiveCandidate() {
            VoiceReplyState value = this.stateFlow.getValue();
            HasCandidate hasCandidate = value instanceof HasCandidate ? (HasCandidate) value : null;
            if (hasCandidate == null) {
                return null;
            }
            return hasCandidate.getCandidate();
        }
    }
}

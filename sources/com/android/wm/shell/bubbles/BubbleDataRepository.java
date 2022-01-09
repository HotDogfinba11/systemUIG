package com.android.wm.shell.bubbles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.LocusId;
import android.content.pm.LauncherApps;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import com.android.wm.shell.bubbles.storage.BubblePersistentRepository;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.wm.shell.common.ShellExecutor;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* compiled from: BubbleDataRepository.kt */
public final class BubbleDataRepository {
    private final CoroutineScope ioScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    private Job job;
    private final LauncherApps launcherApps;
    private final ShellExecutor mainExecutor;
    private final BubblePersistentRepository persistentRepository;
    private final BubbleVolatileRepository volatileRepository;

    public BubbleDataRepository(Context context, LauncherApps launcherApps2, ShellExecutor shellExecutor) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(launcherApps2, "launcherApps");
        Intrinsics.checkNotNullParameter(shellExecutor, "mainExecutor");
        this.launcherApps = launcherApps2;
        this.mainExecutor = shellExecutor;
        this.volatileRepository = new BubbleVolatileRepository(launcherApps2);
        this.persistentRepository = new BubblePersistentRepository(context);
        Dispatchers dispatchers = Dispatchers.INSTANCE;
    }

    public final void addBubble(int i, Bubble bubble) {
        Intrinsics.checkNotNullParameter(bubble, "bubble");
        addBubbles(i, CollectionsKt__CollectionsJVMKt.listOf(bubble));
    }

    public final void addBubbles(int i, List<? extends Bubble> list) {
        Intrinsics.checkNotNullParameter(list, "bubbles");
        List<BubbleEntity> transform = transform(list);
        this.volatileRepository.addBubbles(i, transform);
        if (!transform.isEmpty()) {
            persistToDisk();
        }
    }

    public final void removeBubbles(int i, List<? extends Bubble> list) {
        Intrinsics.checkNotNullParameter(list, "bubbles");
        List<BubbleEntity> transform = transform(list);
        this.volatileRepository.removeBubbles(i, transform);
        if (!transform.isEmpty()) {
            persistToDisk();
        }
    }

    private final void persistToDisk() {
        this.job = BuildersKt.launch$default(this.ioScope, null, null, new BubbleDataRepository$persistToDisk$1(this.job, this, null), 3, null);
    }

    @SuppressLint({"WrongConstant"})
    public final Job loadBubbles(int i, Function1<? super List<? extends Bubble>, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "cb");
        return BuildersKt.launch$default(this.ioScope, null, null, new BubbleDataRepository$loadBubbles$1(this, i, function1, null), 3, null);
    }

    private final List<BubbleEntity> transform(List<? extends Bubble> list) {
        ArrayList arrayList = new ArrayList();
        for (T t : list) {
            int identifier = t.getUser().getIdentifier();
            String packageName = t.getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "b.packageName");
            String metadataShortcutId = t.getMetadataShortcutId();
            BubbleEntity bubbleEntity = null;
            if (metadataShortcutId != null) {
                String key = t.getKey();
                Intrinsics.checkNotNullExpressionValue(key, "b.key");
                int rawDesiredHeight = t.getRawDesiredHeight();
                int rawDesiredHeightResId = t.getRawDesiredHeightResId();
                String title = t.getTitle();
                int taskId = t.getTaskId();
                LocusId locusId = t.getLocusId();
                bubbleEntity = new BubbleEntity(identifier, packageName, metadataShortcutId, key, rawDesiredHeight, rawDesiredHeightResId, title, taskId, locusId == null ? null : locusId.getId());
            }
            if (bubbleEntity != null) {
                arrayList.add(bubbleEntity);
            }
        }
        return arrayList;
    }
}

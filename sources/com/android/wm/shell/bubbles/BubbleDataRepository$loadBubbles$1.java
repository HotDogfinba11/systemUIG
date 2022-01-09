package com.android.wm.shell.bubbles;

import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.os.UserHandle;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import com.android.wm.shell.common.ShellExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* access modifiers changed from: package-private */
@DebugMetadata(c = "com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1", f = "BubbleDataRepository.kt", l = {}, m = "invokeSuspend")
/* compiled from: BubbleDataRepository.kt */
public final class BubbleDataRepository$loadBubbles$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function1<List<? extends Bubble>, Unit> $cb;
    final /* synthetic */ int $userId;
    int label;
    private /* synthetic */ CoroutineScope p$;
    final /* synthetic */ BubbleDataRepository this$0;

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: kotlin.jvm.functions.Function1<? super java.util.List<? extends com.android.wm.shell.bubbles.Bubble>, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BubbleDataRepository$loadBubbles$1(BubbleDataRepository bubbleDataRepository, int i, Function1<? super List<? extends Bubble>, Unit> function1, Continuation<? super BubbleDataRepository$loadBubbles$1> continuation) {
        super(2, continuation);
        this.this$0 = bubbleDataRepository;
        this.$userId = i;
        this.$cb = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        BubbleDataRepository$loadBubbles$1 bubbleDataRepository$loadBubbles$1 = new BubbleDataRepository$loadBubbles$1(this.this$0, this.$userId, this.$cb, continuation);
        bubbleDataRepository$loadBubbles$1.p$ = (CoroutineScope) obj;
        return bubbleDataRepository$loadBubbles$1;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BubbleDataRepository$loadBubbles$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        Object unused = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            List<BubbleEntity> list = this.this$0.persistentRepository.readFromDisk().get(this.$userId);
            if (list == null) {
                return Unit.INSTANCE;
            }
            this.this$0.volatileRepository.addBubbles(this.$userId, list);
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            for (T t : list) {
                arrayList.add(new ShortcutKey(t.getUserId(), t.getPackageName()));
            }
            Set<ShortcutKey> set = CollectionsKt___CollectionsKt.toSet(arrayList);
            BubbleDataRepository bubbleDataRepository = this.this$0;
            ArrayList arrayList2 = new ArrayList();
            for (ShortcutKey shortcutKey : set) {
                List<ShortcutInfo> shortcuts = bubbleDataRepository.launcherApps.getShortcuts(new LauncherApps.ShortcutQuery().setPackage(shortcutKey.getPkg()).setQueryFlags(1041), UserHandle.of(shortcutKey.getUserId()));
                if (shortcuts == null) {
                    shortcuts = CollectionsKt__CollectionsKt.emptyList();
                }
                boolean unused2 = CollectionsKt__MutableCollectionsKt.addAll(arrayList2, shortcuts);
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj3 : arrayList2) {
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj3;
                int userId = shortcutInfo.getUserId();
                String str = shortcutInfo.getPackage();
                Intrinsics.checkNotNullExpressionValue(str, "it.`package`");
                ShortcutKey shortcutKey2 = new ShortcutKey(userId, str);
                Object obj4 = linkedHashMap.get(shortcutKey2);
                if (obj4 == null) {
                    obj4 = new ArrayList();
                    linkedHashMap.put(shortcutKey2, obj4);
                }
                ((List) obj4).add(obj3);
            }
            BubbleDataRepository bubbleDataRepository2 = this.this$0;
            final ArrayList arrayList3 = new ArrayList();
            for (T t2 : list) {
                List list2 = (List) linkedHashMap.get(new ShortcutKey(t2.getUserId(), t2.getPackageName()));
                Bubble bubble = null;
                if (list2 != null) {
                    Iterator it = list2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            obj2 = null;
                            break;
                        }
                        obj2 = it.next();
                        if (Boxing.boxBoolean(Intrinsics.areEqual(t2.getShortcutId(), ((ShortcutInfo) obj2).getId())).booleanValue()) {
                            break;
                        }
                    }
                    ShortcutInfo shortcutInfo2 = (ShortcutInfo) obj2;
                    if (shortcutInfo2 != null) {
                        bubble = new Bubble(t2.getKey(), shortcutInfo2, t2.getDesiredHeight(), t2.getDesiredHeightResId(), t2.getTitle(), t2.getTaskId(), t2.getLocus(), bubbleDataRepository2.mainExecutor);
                    }
                }
                if (bubble != null) {
                    arrayList3.add(bubble);
                }
            }
            ShellExecutor shellExecutor = this.this$0.mainExecutor;
            final Function1<List<? extends Bubble>, Unit> function1 = this.$cb;
            shellExecutor.execute(new Runnable() {
                /* class com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1.AnonymousClass1 */

                public final void run() {
                    function1.invoke(arrayList3);
                }
            });
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

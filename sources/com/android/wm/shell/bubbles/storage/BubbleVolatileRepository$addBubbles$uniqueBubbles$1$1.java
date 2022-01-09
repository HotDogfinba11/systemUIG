package com.android.wm.shell.bubbles.storage;

import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: BubbleVolatileRepository.kt */
public final class BubbleVolatileRepository$addBubbles$uniqueBubbles$1$1 implements Predicate<BubbleEntity> {
    final /* synthetic */ BubbleEntity $b;

    BubbleVolatileRepository$addBubbles$uniqueBubbles$1$1(BubbleEntity bubbleEntity) {
        this.$b = bubbleEntity;
    }

    public final boolean test(BubbleEntity bubbleEntity) {
        Intrinsics.checkNotNullParameter(bubbleEntity, "e");
        return Intrinsics.areEqual(this.$b.getKey(), bubbleEntity.getKey());
    }
}

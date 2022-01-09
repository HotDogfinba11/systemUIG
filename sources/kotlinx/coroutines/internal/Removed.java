package kotlinx.coroutines.internal;

import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: LockFreeLinkedList.kt */
public final class Removed {
    public final LockFreeLinkedListNode ref;

    public Removed(LockFreeLinkedListNode lockFreeLinkedListNode) {
        Intrinsics.checkParameterIsNotNull(lockFreeLinkedListNode, "ref");
        this.ref = lockFreeLinkedListNode;
    }

    public String toString() {
        return "Removed[" + this.ref + ']';
    }
}

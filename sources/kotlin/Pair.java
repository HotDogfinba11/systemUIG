package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Tuples.kt */
public final class Pair<A, B> implements Serializable {
    private final A first;
    private final B second;

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return Intrinsics.areEqual(this.first, pair.first) && Intrinsics.areEqual(this.second, pair.second);
    }

    public int hashCode() {
        A a = this.first;
        int i = 0;
        int hashCode = (a != null ? a.hashCode() : 0) * 31;
        B b = this.second;
        if (b != null) {
            i = b.hashCode();
        }
        return hashCode + i;
    }

    public Pair(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public String toString() {
        return '(' + ((Object) this.first) + ", " + ((Object) this.second) + ')';
    }
}

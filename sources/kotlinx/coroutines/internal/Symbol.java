package kotlinx.coroutines.internal;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: Symbol.kt */
public final class Symbol {
    private final String symbol;

    public Symbol(String str) {
        Intrinsics.checkParameterIsNotNull(str, "symbol");
        this.symbol = str;
    }

    public String toString() {
        return this.symbol;
    }
}

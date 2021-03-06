package kotlinx.coroutines;

import kotlinx.coroutines.internal.Symbol;

public final class JobSupportKt {
    private static final Empty EMPTY_ACTIVE = new Empty(true);
    private static final Empty EMPTY_NEW = new Empty(false);
    private static final Symbol SEALED = new Symbol("SEALED");

    public static final Object boxIncomplete(Object obj) {
        return obj instanceof Incomplete ? new IncompleteStateBox((Incomplete) obj) : obj;
    }

    public static final Object unboxState(Object obj) {
        Incomplete incomplete;
        IncompleteStateBox incompleteStateBox = (IncompleteStateBox) (!(obj instanceof IncompleteStateBox) ? null : obj);
        return (incompleteStateBox == null || (incomplete = incompleteStateBox.state) == null) ? obj : incomplete;
    }
}

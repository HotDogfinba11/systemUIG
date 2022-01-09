package kotlin.coroutines.jvm.internal;

/* compiled from: boxing.kt */
public final class Boxing {
    public static final Boolean boxBoolean(boolean z) {
        return Boolean.valueOf(z);
    }

    public static final Integer boxInt(int i) {
        return new Integer(i);
    }
}

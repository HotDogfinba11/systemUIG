package kotlin.internal;

import java.lang.reflect.Method;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PlatformImplementations.kt */
public class PlatformImplementations {

    /* access modifiers changed from: private */
    /* compiled from: PlatformImplementations.kt */
    public static final class ReflectThrowable {
        public static final ReflectThrowable INSTANCE = new ReflectThrowable();
        public static final Method addSuppressed;
        public static final Method getSuppressed;

        /* JADX WARNING: Removed duplicated region for block: B:10:0x0046 A[LOOP:0: B:1:0x0015->B:10:0x0046, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x004a A[EDGE_INSN: B:20:0x004a->B:12:0x004a ?: BREAK  , SYNTHETIC] */
        static {
            /*
            // Method dump skipped, instructions count: 104
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.internal.PlatformImplementations.ReflectThrowable.<clinit>():void");
        }

        private ReflectThrowable() {
        }
    }

    public void addSuppressed(Throwable th, Throwable th2) {
        Intrinsics.checkNotNullParameter(th, "cause");
        Intrinsics.checkNotNullParameter(th2, "exception");
        Method method = ReflectThrowable.addSuppressed;
        if (method != null) {
            method.invoke(th, th2);
        }
    }
}

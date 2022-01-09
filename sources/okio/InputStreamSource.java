package okio;

import java.io.IOException;
import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: JvmOkio.kt */
public final class InputStreamSource implements Source {
    private final InputStream input;
    private final Timeout timeout;

    public InputStreamSource(InputStream inputStream, Timeout timeout2) {
        Intrinsics.checkNotNullParameter(inputStream, "input");
        Intrinsics.checkNotNullParameter(timeout2, "timeout");
        this.input = inputStream;
        this.timeout = timeout2;
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) {
        Intrinsics.checkNotNullParameter(buffer, "sink");
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i == 0) {
            return 0;
        }
        if (i >= 0) {
            try {
                this.timeout.throwIfReached();
                Segment writableSegment$external__okio__android_common__okio_lib = buffer.writableSegment$external__okio__android_common__okio_lib(1);
                int read = this.input.read(writableSegment$external__okio__android_common__okio_lib.data, writableSegment$external__okio__android_common__okio_lib.limit, (int) Math.min(j, (long) (8192 - writableSegment$external__okio__android_common__okio_lib.limit)));
                if (read != -1) {
                    writableSegment$external__okio__android_common__okio_lib.limit += read;
                    long j2 = (long) read;
                    buffer.setSize$external__okio__android_common__okio_lib(buffer.size() + j2);
                    return j2;
                } else if (writableSegment$external__okio__android_common__okio_lib.pos != writableSegment$external__okio__android_common__okio_lib.limit) {
                    return -1;
                } else {
                    buffer.head = writableSegment$external__okio__android_common__okio_lib.pop();
                    SegmentPool segmentPool = SegmentPool.INSTANCE;
                    SegmentPool.recycle(writableSegment$external__okio__android_common__okio_lib);
                    return -1;
                }
            } catch (AssertionError e) {
                if (Okio.isAndroidGetsocknameError(e)) {
                    throw new IOException(e);
                }
                throw e;
            }
        } else {
            throw new IllegalArgumentException(Intrinsics.stringPlus("byteCount < 0: ", Long.valueOf(j)).toString());
        }
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.input.close();
    }

    public String toString() {
        return "source(" + this.input + ')';
    }
}